// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import javax.inject.Inject;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.GameTickProvider;

@Singleton
@Implements(GameTickProvider.class)
public class DefaultGameTickProvider implements GameTickProvider
{
    private final LabyAPI labyAPI;
    private int tickCount;
    private int pausedTickCount;
    
    @Inject
    public DefaultGameTickProvider(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    @Subscribe
    public void onGameTick(final GameTickEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        ++this.tickCount;
        if (this.labyAPI.minecraft().isPaused()) {
            return;
        }
        ++this.pausedTickCount;
    }
    
    @Override
    public float get() {
        return this.tickCount + this.labyAPI.minecraft().getPartialTicks();
    }
    
    @Override
    public float getPaused() {
        return this.pausedTickCount + this.labyAPI.minecraft().getPartialTicks();
    }
    
    @Override
    public int tickCount() {
        return this.tickCount;
    }
    
    @Override
    public int pausedTickCount() {
        return this.pausedTickCount;
    }
}
