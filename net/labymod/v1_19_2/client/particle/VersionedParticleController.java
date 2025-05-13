// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.particle;

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
        final gt position = new gt((double)blockPosition.getX(), (double)blockPosition.getY(), (double)blockPosition.getZ());
        efu.I().g.a(position, this.getMinecraftDirection(direction));
    }
    
    public gy getMinecraftDirection(final Direction direction) {
        switch (direction) {
            case DOWN: {
                return gy.a;
            }
            case UP: {
                return gy.b;
            }
            case NORTH: {
                return gy.c;
            }
            case SOUTH: {
                return gy.d;
            }
            case WEST: {
                return gy.e;
            }
            case EAST: {
                return gy.f;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
            }
        }
    }
}
