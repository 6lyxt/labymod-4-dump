// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.interaction;

import net.labymod.core.client.gui.screen.widget.widgets.interaction.bullet.BulletPointWidget;

public interface InteractionAnimationController
{
    float getOpenProgress();
    
    float getSocialTransitionProgress();
    
    float getBulletRotationOffset(final BulletPointWidget.Side p0);
}
