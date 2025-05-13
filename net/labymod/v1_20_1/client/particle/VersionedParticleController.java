// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.particle;

import net.labymod.api.util.math.MathHelper;
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
        final gu position = new gu(MathHelper.ceil(blockPosition.getX()), MathHelper.ceil(blockPosition.getY()), MathHelper.ceil(blockPosition.getZ()));
        enn.N().g.a(position, this.getMinecraftDirection(direction));
    }
    
    public ha getMinecraftDirection(final Direction direction) {
        switch (direction) {
            case DOWN: {
                return ha.a;
            }
            case UP: {
                return ha.b;
            }
            case NORTH: {
                return ha.c;
            }
            case SOUTH: {
                return ha.d;
            }
            case WEST: {
                return ha.e;
            }
            case EAST: {
                return ha.f;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
            }
        }
    }
}
