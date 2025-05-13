// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.texture.overlay;

import net.labymod.api.util.color.format.ColorFormat;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.Color;
import net.labymod.api.Laby;
import net.labymod.api.configuration.labymod.main.laby.ingame.DamageConfig;

public final class DynamicOverlayTexture
{
    private static final DamageColor DEFAULT_COLOR;
    private final DamageConfig damageConfig;
    private final GameOverlayTexture texture;
    private final DamageColor color;
    private boolean updateOverlayTexture;
    
    public DynamicOverlayTexture(final GameOverlayTexture texture) {
        this.color = new DamageColor(1.0f, 1.0f, 1.0f, 1.0f).setChangeListener(() -> this.updateOverlayTexture = true);
        this.texture = texture;
        this.damageConfig = Laby.labyAPI().config().ingame().damage();
    }
    
    public void setColorAndUpdate() {
        this.setColor();
        this.update();
    }
    
    public void setColor() {
        if (this.damageConfig.damageColored().get()) {
            final ConfigProperty<Color> damageColorProperty = this.damageConfig.damageColor();
            final Color damageColor = damageColorProperty.get();
            this.setColor(damageColor);
        }
        else {
            this.setDefaultColor();
        }
    }
    
    public void setColor(final Color color) {
        this.setColor(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public void setColor(final float red, final float green, final float blue, final float alpha) {
        this.color.set(red, green, blue, alpha);
        this.updateTexture();
    }
    
    public void setDefaultColor() {
        this.color.set(DynamicOverlayTexture.DEFAULT_COLOR);
        this.updateTexture();
    }
    
    public void update() {
        if (!this.updateOverlayTexture) {
            return;
        }
        this.updateOverlayTexture = false;
        this.texture.upload();
    }
    
    private void updateTexture() {
        final GameImage image = this.texture.image();
        for (int y = 0; y < 16; ++y) {
            for (int x = 0; x < 16; ++x) {
                if (y < 8) {
                    image.setARGB(x, y, this.color.getARGB());
                }
            }
        }
    }
    
    static {
        DEFAULT_COLOR = new DamageColor(1.0f, 0.0f, 0.0f, 0.3f);
    }
    
    static class DamageColor
    {
        private float red;
        private float green;
        private float blue;
        private float alpha;
        @Nullable
        private Runnable changeListener;
        
        public DamageColor(final float red, final float green, final float blue, final float alpha) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }
        
        public DamageColor setChangeListener(@Nullable final Runnable runnable) {
            this.changeListener = runnable;
            return this;
        }
        
        public void set(final DamageColor other) {
            this.set(other.red, other.green, other.blue, other.alpha);
        }
        
        public void set(final float red, final float green, final float blue, final float alpha) {
            if (this.red == red && this.green == green && this.blue == blue && this.alpha == alpha) {
                return;
            }
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
            this.onValueUpdate();
        }
        
        public int getARGB() {
            return ColorFormat.ARGB32.pack(this.red, this.green, this.blue, 1.0f - this.alpha);
        }
        
        private void onValueUpdate() {
            if (this.changeListener == null) {
                return;
            }
            this.changeListener.run();
        }
    }
}
