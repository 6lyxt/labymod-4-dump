// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.EmotesActivity;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;

@AutoWidget
public class EmoteCustomizationSegmentWidget extends WheelWidget.Segment
{
    private final EmotesActivity activity;
    private final EmoteItem emote;
    private final int index;
    private final int wheelIndex;
    
    public EmoteCustomizationSegmentWidget(final EmotesActivity activity, final int index, final int wheelIndex, final EmoteItem emote) {
        this.activity = activity;
        this.emote = emote;
        this.index = index;
        this.wheelIndex = wheelIndex;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.children.clear();
        ((AbstractWidget<ComponentWidget>)this).addChild(ComponentWidget.text(this.emote.getName()));
    }
    
    @Override
    protected void onSelectionChanged() {
        final boolean selected = this.isSegmentSelected();
        if (!selected) {
            this.activity.stopEmote(this.emote);
            return;
        }
        this.activity.playEmote(this.emote);
    }
    
    @Override
    public boolean onPress() {
        return super.onPress();
    }
}
