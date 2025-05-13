// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.client.particle;

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
        final et position = new et((double)blockPosition.getX(), (double)blockPosition.getY(), (double)blockPosition.getZ());
        bib.z().j.a(position, this.getMinecraftDirection(direction));
    }
    
    public fa getMinecraftDirection(final Direction direction) {
        switch (direction) {
            case DOWN: {
                return fa.a;
            }
            case UP: {
                return fa.b;
            }
            case NORTH: {
                return fa.c;
            }
            case SOUTH: {
                return fa.d;
            }
            case WEST: {
                return fa.e;
            }
            case EAST: {
                return fa.f;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
            }
        }
    }
}
