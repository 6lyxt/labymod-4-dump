// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.position;

import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;

public class HudSize
{
    private final HudWidgetConfig config;
    private float width;
    private float height;
    
    public HudSize(final HudWidgetConfig config) {
        this(0.0f, 0.0f, config);
    }
    
    @Deprecated
    public HudSize(final int width, final int height, final HudWidgetConfig config) {
        this((float)width, (float)height, config);
    }
    
    public HudSize(final float width, final float height, final HudWidgetConfig config) {
        this.width = width;
        this.height = height;
        this.config = config;
    }
    
    public float getScaledWidth() {
        return this.width * this.getScale();
    }
    
    public float getScaledHeight() {
        return this.height * this.getScale();
    }
    
    public float getActualWidth() {
        return this.width;
    }
    
    public float getActualHeight() {
        return this.height;
    }
    
    public void setWidth(final float width) {
        this.width = width;
    }
    
    public void setHeight(final float height) {
        this.height = height;
    }
    
    public float getScale() {
        return this.config.scale().get();
    }
    
    public void set(final float width, final float height) {
        this.width = width;
        this.height = height;
    }
    
    public HudSize copy() {
        return new HudSize(this.width, this.height, this.config);
    }
    
    public Rectangle toRectangle() {
        return Rectangle.relative(0.0f, 0.0f, this.width, this.height);
    }
    
    @Deprecated
    public int getWidth() {
        return (int)this.width;
    }
    
    @Deprecated
    public int getHeight() {
        return (int)this.height;
    }
    
    @Deprecated
    public void setWidth(final int width) {
        this.width = (float)width;
    }
    
    @Deprecated
    public void setHeight(final int height) {
        this.height = (float)height;
    }
    
    @Deprecated
    public void set(final int width, final int height) {
        this.width = (float)width;
        this.height = (float)height;
    }
}
