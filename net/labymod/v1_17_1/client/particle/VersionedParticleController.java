// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.particle;

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
        final gg position = new gg((double)blockPosition.getX(), (double)blockPosition.getY(), (double)blockPosition.getZ());
        dvp.C().g.a(position, this.getMinecraftDirection(direction));
    }
    
    public gl getMinecraftDirection(final Direction direction) {
        switch (direction) {
            case DOWN: {
                return gl.a;
            }
            case UP: {
                return gl.b;
            }
            case NORTH: {
                return gl.c;
            }
            case SOUTH: {
                return gl.d;
            }
            case WEST: {
                return gl.e;
            }
            case EAST: {
                return gl.f;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
            }
        }
    }
}
