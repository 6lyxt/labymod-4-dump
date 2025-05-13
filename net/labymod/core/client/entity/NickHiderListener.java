// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.PlayerNameTagRenderEvent;
import net.labymod.api.LabyAPI;

public class NickHiderListener
{
    private final LabyAPI labyAPI;
    
    public NickHiderListener(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    @Subscribe
    public void onNameTagRender(final PlayerNameTagRenderEvent event) {
        if (this.labyAPI.config().ingame().hideNametag().get()) {
            event.setCancelled(true);
        }
    }
}
