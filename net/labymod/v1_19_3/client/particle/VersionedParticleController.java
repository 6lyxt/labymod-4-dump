// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.particle;

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
        final gp position = new gp((double)blockPosition.getX(), (double)blockPosition.getY(), (double)blockPosition.getZ());
        ejf.N().g.a(position, this.getMinecraftDirection(direction));
    }
    
    public gv getMinecraftDirection(final Direction direction) {
        switch (direction) {
            case DOWN: {
                return gv.a;
            }
            case UP: {
                return gv.b;
            }
            case NORTH: {
                return gv.c;
            }
            case SOUTH: {
                return gv.d;
            }
            case WEST: {
                return gv.e;
            }
            case EAST: {
                return gv.f;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
            }
        }
    }
}
