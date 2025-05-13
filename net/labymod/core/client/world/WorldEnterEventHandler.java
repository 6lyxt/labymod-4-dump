// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world;

import net.labymod.api.client.Minecraft;
import net.labymod.api.event.client.world.WorldEnterEvent;
import net.labymod.api.Laby;
import net.labymod.api.event.client.world.WorldLeaveEvent;
import javax.inject.Inject;
import net.labymod.api.LabyAPI;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class WorldEnterEventHandler
{
    private boolean lastIngame;
    private final LabyAPI labyAPI;
    
    @Inject
    public WorldEnterEventHandler(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    public void tick() {
        final Minecraft minecraft = this.labyAPI.minecraft();
        final boolean ingame = minecraft.isIngame();
        if (this.lastIngame == ingame) {
            return;
        }
        if (!(this.lastIngame = ingame)) {
            Laby.fireEvent(new WorldLeaveEvent());
            ((DefaultClientWorld)minecraft.clientWorld()).resetEntityList();
            return;
        }
        final boolean singleplayer = minecraft.isSingleplayer();
        final WorldEnterEvent.Type type = singleplayer ? WorldEnterEvent.Type.SINGLEPLAYER : WorldEnterEvent.Type.MULTIPLAYER;
        Laby.fireEvent(new WorldEnterEvent(type));
    }
}
