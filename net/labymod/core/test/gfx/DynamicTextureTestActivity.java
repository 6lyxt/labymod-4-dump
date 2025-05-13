// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test.gfx;

import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.texture.DynamicTexture;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Random;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.core.test.TestActivity;

@AutoActivity
public class DynamicTextureTestActivity extends TestActivity
{
    private static final Random RANDOM;
    private static final ResourceLocation TEST_LOCATION;
    private int index;
    private Icon icon;
    private DynamicTexture dynamicTexture;
    private boolean linear;
    
    public DynamicTextureTestActivity() {
        this.index = 3;
    }
    
    @Override
    public void onOpenScreen() {
        super.onOpenScreen();
        this.dynamicTexture = new DynamicTexture(DynamicTextureTestActivity.TEST_LOCATION, 16, 16);
        this.icon = Icon.texture(DynamicTextureTestActivity.TEST_LOCATION);
        Laby.references().textureRepository().register(DynamicTextureTestActivity.TEST_LOCATION, this.dynamicTexture);
    }
    
    @Override
    public void tick() {
        super.tick();
        final GameImage image = this.dynamicTexture.getImage();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final ColorFormat format = ColorFormat.ARGB32;
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final int red = DynamicTextureTestActivity.RANDOM.nextInt(256);
                final int green = DynamicTextureTestActivity.RANDOM.nextInt(256);
                final int blue = DynamicTextureTestActivity.RANDOM.nextInt(256);
                image.setARGB(x, y, format.pack(red, green, blue, 255));
            }
        }
        this.dynamicTexture.upload();
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (key == Key.O) {
            int size = MathHelper.POWER_OF_TWO[this.index];
            if (size > 512) {
                size = 512;
            }
            this.dynamicTexture.setImage(GameImage.IMAGE_PROVIDER.createImage(size, size));
            ++this.index;
            if (this.index > 8) {
                this.index = 0;
            }
            return true;
        }
        if (key == Key.L) {
            this.linear = !this.linear;
            return true;
        }
        return super.keyPressed(key, type);
    }
    
    @Override
    public void render(final ScreenContext context) {
        super.render(context);
        final GameImage image = this.dynamicTexture.getImage();
        final Document document = this.document();
        final float centerX = document.bounds().getWidth() / 2.0f;
        final float centerY = document.bounds().getHeight() / 2.0f;
        if (this.linear) {
            this.icon.makeBlurry();
        }
        final Stack stack = context.stack();
        this.icon.render(stack, centerX - image.getWidth() / 2.0f, centerY - image.getHeight() / 2.0f, (float)image.getWidth(), (float)image.getHeight());
        final TextRenderer renderer = Laby.references().textRendererProvider().getRenderer();
        renderer.text("Change Image Size (Key O): " + image.getWidth() + "x" + image.getHeight()).pos(2.0f, 2.0f).color(-1).render(stack);
        renderer.text("Change Filter (Key L): " + (this.linear ? "LINEAR" : "NEAREST")).pos(2.0f, 12.0f).color(-1).render(stack);
    }
    
    @Override
    public void onCloseScreen() {
        super.onCloseScreen();
        this.dynamicTexture.close();
        Laby.references().textureRepository().releaseTexture(DynamicTextureTestActivity.TEST_LOCATION);
    }
    
    static {
        RANDOM = new Random();
        TEST_LOCATION = ResourceLocation.create("labymod", "test/dynamic/dynamic_texture");
    }
}
