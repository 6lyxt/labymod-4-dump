// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.client.particle;

import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.FloatVector3;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import net.labymod.api.client.particle.ParticleController;

@Implements(ParticleController.class)
public class VersionedParticleController implements ParticleController
{
    @Inject
    public VersionedParticleController() {
    }
    
    @Override
    public void crackBlock(final FloatVector3 blockPosition, final Direction direction) {
        final cj position = new cj((double)blockPosition.getX(), (double)blockPosition.getY(), (double)blockPosition.getZ());
        ave.A().j.a(position, this.getMinecraftDirection(direction));
    }
    
    public cq getMinecraftDirection(final Direction direction) {
        switch (direction) {
            case DOWN: {
                return cq.a;
            }
            case UP: {
                return cq.b;
            }
            case NORTH: {
                return cq.c;
            }
            case SOUTH: {
                return cq.d;
            }
            case WEST: {
                return cq.e;
            }
            case EAST: {
                return cq.f;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
            }
        }
    }
}
