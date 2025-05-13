// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.particle;

import net.labymod.v1_16_5.client.util.MinecraftUtil;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.FloatVector3;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.particle.ParticleController;

@Singleton
@Implements(ParticleController.class)
public class VersionedParticleController implements ParticleController
{
    @Inject
    public VersionedParticleController() {
    }
    
    @Override
    public void crackBlock(final FloatVector3 blockPosition, final Direction direction) {
        final fx position = new fx((double)blockPosition.getX(), (double)blockPosition.getY(), (double)blockPosition.getZ());
        djz.C().f.a(position, MinecraftUtil.toMinecraft(direction));
    }
}
