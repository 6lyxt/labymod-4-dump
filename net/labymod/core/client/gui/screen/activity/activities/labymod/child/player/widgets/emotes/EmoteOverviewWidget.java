// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes;

import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.EmotesActivity;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class EmoteOverviewWidget extends AbstractWidget<Widget>
{
    private final EmotesActivity activity;
    private final EmoteItem emote;
    
    public EmoteOverviewWidget(final EmotesActivity activity, final EmoteItem emote) {
        this.activity = activity;
        this.emote = emote;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.children.clear();
        ((AbstractWidget<ComponentWidget>)this).addChild(ComponentWidget.text(this.emote.getName()));
    }
    
    @Override
    protected void onAttributeStateChanged(final AttributeState state, final boolean newState) {
        super.onAttributeStateChanged(state, newState);
        if (state != AttributeState.HOVER) {
            return;
        }
        if (newState) {
            this.activity.playEmote(this.emote);
            return;
        }
        this.activity.stopEmote(this.emote);
    }
}
