// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.hudwidget;

import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;

public abstract class ResizeableHudWidget<T extends ResizeableHudWidgetConfig> extends SimpleHudWidget<T>
{
    protected float width;
    protected float height;
    
    protected ResizeableHudWidget(final String id, final Class<T> configClass) {
        super(id, configClass);
    }
    
    @Override
    public void load(final T config) {
        super.load(config);
        this.width = config.width().get();
        this.height = config.height().get();
    }
    
    @Override
    public final void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
        size.set(this.width, this.height);
        this.render(stack, mouse, partialTicks, isEditorContext, this.width, this.height);
    }
    
    public abstract void render(final Stack p0, final MutableMouse p1, final float p2, final boolean p3, final float p4, final float p5);
    
    public void setSize(final float width, final float height) {
        this.width = width;
        this.height = height;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public abstract static class ResizeableHudWidgetConfig extends HudWidgetConfig
    {
        @Exclude
        private final float minWidth;
        @Exclude
        private final float minHeight;
        @Exclude
        private final float maxWidth;
        @Exclude
        private final float maxHeight;
        private final ConfigProperty<Float> width;
        private final ConfigProperty<Float> height;
        
        public ResizeableHudWidgetConfig(final float minWidth, final float defaultWidth, final float maxWidth, final float minHeight, final float defaultHeight, final float maxHeight) {
            this.minWidth = minWidth;
            this.minHeight = minHeight;
            this.maxWidth = maxWidth;
            this.maxHeight = maxHeight;
            this.width = new ConfigProperty<Float>(defaultWidth);
            this.height = new ConfigProperty<Float>(defaultHeight);
        }
        
        public ConfigProperty<Float> width() {
            return this.width;
        }
        
        public ConfigProperty<Float> height() {
            return this.height;
        }
        
        public float getMinWidth() {
            return this.minWidth;
        }
        
        public float getMinHeight() {
            return this.minHeight;
        }
        
        public float getMaxWidth() {
            return this.maxWidth;
        }
        
        public float getMaxHeight() {
            return this.maxHeight;
        }
    }
}
