// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes;

import net.labymod.api.client.gui.VerticalAlignment;
import java.util.function.Supplier;
import net.labymod.api.client.gui.HorizontalAlignment;

public class ObjectPosition
{
    private HorizontalAlignment horizontalAlignment;
    private Supplier<Float> horizontalOffset;
    private VerticalAlignment verticalAlignment;
    private Supplier<Float> verticalOffset;
    
    public HorizontalAlignment getHorizontalAlignment() {
        return (this.horizontalAlignment != null) ? this.horizontalAlignment : HorizontalAlignment.CENTER;
    }
    
    public void setHorizontalAlignment(final HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }
    
    public float getHorizontalOffset() {
        return (this.horizontalOffset != null) ? this.horizontalOffset.get() : 0.0f;
    }
    
    public void setHorizontalOffset(final Supplier<Float> horizontalOffset) {
        this.horizontalOffset = horizontalOffset;
    }
    
    public VerticalAlignment getVerticalAlignment() {
        return (this.verticalAlignment != null) ? this.verticalAlignment : VerticalAlignment.CENTER;
    }
    
    public void setVerticalAlignment(final VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }
    
    public float getVerticalOffset() {
        return (this.verticalOffset != null) ? this.verticalOffset.get() : 0.0f;
    }
    
    public void setVerticalOffset(final Supplier<Float> verticalOffset) {
        this.verticalOffset = verticalOffset;
    }
}
