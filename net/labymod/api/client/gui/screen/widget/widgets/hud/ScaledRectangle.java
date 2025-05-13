// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.hud;

import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.bounds.Rectangle;

public class ScaledRectangle implements Rectangle
{
    private final Widget widget;
    private final Bounds bounds;
    
    public ScaledRectangle(final Widget widget) {
        this.widget = widget;
        this.bounds = widget.bounds();
    }
    
    @Override
    public float getLeft() {
        return this.bounds.getLeft();
    }
    
    @Override
    public float getTop() {
        return this.bounds.getTop();
    }
    
    @Override
    public float getRight() {
        return this.bounds.getLeft() + this.bounds.getWidth() * this.widget.getScaleX();
    }
    
    @Override
    public float getBottom() {
        return this.bounds.getTop() + this.bounds.getHeight() * this.widget.getScaleY();
    }
    
    public void setPosition(final float x, final float y, final ModifyReason reason) {
        this.bounds.setPosition(x, y, reason);
    }
    
    public void setX(final float x, final ModifyReason reason) {
        this.bounds.setX(x, reason);
    }
    
    public void setY(final float y, final ModifyReason reason) {
        this.bounds.setY(y, reason);
    }
    
    public void setSize(final float scaledWidth, final float scaledHeight, final ModifyReason reason) {
        this.bounds.setSize(scaledWidth / this.widget.getScaleX(), scaledHeight / this.widget.getScaleY(), reason);
    }
    
    public void checkForChanges() {
        this.bounds.checkForChanges();
    }
}
