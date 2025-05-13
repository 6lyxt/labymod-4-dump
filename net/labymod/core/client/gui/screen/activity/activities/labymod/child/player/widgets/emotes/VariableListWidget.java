// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes;

import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget;

@AutoWidget
public class VariableListWidget extends ListWidget<AbstractWidget<?>>
{
    private static final ModifyReason ENTRY_BOUNDS;
    private AbstractWidget<?> flexible;
    
    public void setFlexibleChild(final AbstractWidget<?> child) {
        super.addChild(this.flexible = child);
    }
    
    @Override
    protected void updateContentBounds() {
        final Bounds bounds = this.bounds();
        final float x = bounds.getX();
        float y = bounds.getY();
        final float width = bounds.getWidth();
        final float height = bounds.getHeight();
        final float centerX = x + width / 2.0f;
        float flexibleHeight = height;
        for (final AbstractWidget<?> child : this.children) {
            if (this.flexible == child) {
                continue;
            }
            final Bounds childBounds = child.bounds();
            flexibleHeight -= childBounds.getHeight(BoundsType.OUTER);
        }
        for (final AbstractWidget<?> child : this.children) {
            final ReasonableMutableRectangle rectangle = child.bounds().rectangle(BoundsType.OUTER);
            if (this.flexible == child) {
                final WidgetSize maxSize = child.sizes().getSize(SizeType.MAX, WidgetSide.WIDTH);
                float size = (maxSize == null) ? 0.0f : maxSize.value();
                if (size == 0.0f) {
                    size = flexibleHeight;
                }
                else {
                    size = Math.min(size, flexibleHeight);
                }
                rectangle.setSize(size, VariableListWidget.ENTRY_BOUNDS);
                rectangle.setPosition(centerX - size / 2.0f, y, VariableListWidget.ENTRY_BOUNDS);
                y += size;
            }
            else {
                final float childX = centerX - rectangle.getWidth() / 2.0f;
                rectangle.setPosition(childX, y, VariableListWidget.ENTRY_BOUNDS);
                y += rectangle.getHeight();
            }
        }
    }
    
    @Override
    public boolean hasAutoBounds(final Widget child, final AutoAlignType type) {
        return type == AutoAlignType.POSITION || child == this.flexible;
    }
    
    static {
        ENTRY_BOUNDS = ModifyReason.of("entryBounds");
    }
}
