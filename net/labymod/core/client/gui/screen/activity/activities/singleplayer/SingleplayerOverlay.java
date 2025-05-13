// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.singleplayer;

import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity;

@AutoActivity
public class SingleplayerOverlay extends AbstractLayerActivity
{
    public SingleplayerOverlay(final ScreenInstance parentScreen) {
        super(parentScreen);
    }
    
    @Override
    public boolean shouldRenderBackground() {
        return true;
    }
}
