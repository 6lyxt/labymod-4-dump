// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.particle;

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
        final gj position = new gj((double)blockPosition.getX(), (double)blockPosition.getY(), (double)blockPosition.getZ());
        dyr.D().g.a(position, this.getMinecraftDirection(direction));
    }
    
    public go getMinecraftDirection(final Direction direction) {
        switch (direction) {
            case DOWN: {
                return go.a;
            }
            case UP: {
                return go.b;
            }
            case NORTH: {
                return go.c;
            }
            case SOUTH: {
                return go.d;
            }
            case WEST: {
                return go.e;
            }
            case EAST: {
                return go.f;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
            }
        }
    }
}
