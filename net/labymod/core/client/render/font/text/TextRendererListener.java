// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;

public class TextRendererListener
{
    private final DefaultTextRendererProvider provider;
    
    public TextRendererListener(final DefaultTextRendererProvider provider) {
        this.provider = provider;
    }
    
    @Subscribe
    public void onServerDisconnect(final ServerDisconnectEvent event) {
        this.provider.setUseCustomFont(true);
    }
}
