// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.multiplayer.server;

import net.labymod.core.main.animation.old.animations.HeadRotationOldAnimation;
import net.labymod.api.client.entity.Entity;
import javax.inject.Inject;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.multiplayer.ClientNetworkPacketListener;

@Singleton
@Implements(ClientNetworkPacketListener.class)
public class VersionedClientNetworkPacketListener extends ClientNetworkPacketListener
{
    @Inject
    public VersionedClientNetworkPacketListener(final ServerController serverController, final OldAnimationRegistry oldAnimationRegistry) {
        super(serverController, oldAnimationRegistry);
    }
    
    @Override
    public <T> T getCurrentServer() {
        return (T)fgo.Q().S();
    }
    
    @Override
    public void onEntityRotate(final Entity entity, final float yaw, final int lerpSteps) {
        if (!(entity instanceof bsr)) {
            return;
        }
        final bsr mcEntity = (bsr)entity;
        final HeadRotationOldAnimation animation = this.oldAnimationRegistry.get("head_rotation");
        if (animation == null) {
            return;
        }
        if (animation.isEnabled()) {
            mcEntity.a(yaw, animation.getOldHeadRotationPitchStep());
        }
        else {
            mcEntity.a(yaw, lerpSteps);
        }
    }
}
