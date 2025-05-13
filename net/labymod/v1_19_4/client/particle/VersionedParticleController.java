// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.particle;

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
        final gt position = new gt(MathHelper.ceil(blockPosition.getX()), MathHelper.ceil(blockPosition.getY()), MathHelper.ceil(blockPosition.getZ()));
        emh.N().g.a(position, this.getMinecraftDirection(direction));
    }
    
    public gz getMinecraftDirection(final Direction direction) {
        switch (direction) {
            case DOWN: {
                return gz.a;
            }
            case UP: {
                return gz.b;
            }
            case NORTH: {
                return gz.c;
            }
            case SOUTH: {
                return gz.d;
            }
            case WEST: {
                return gz.e;
            }
            case EAST: {
                return gz.f;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
            }
        }
    }
}
