// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes;

import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;

public class DummySegmentWidget extends WheelWidget.Segment
{
    private final boolean selected;
    
    public DummySegmentWidget(final boolean selected) {
        this.selected = selected;
    }
    
    @Override
    public boolean isSelectable() {
        return this.selected;
    }
    
    @Override
    public boolean isSegmentSelected() {
        return this.selected;
    }
    
    @Override
    public void setSegmentSelected(final boolean selected) {
    }
}
