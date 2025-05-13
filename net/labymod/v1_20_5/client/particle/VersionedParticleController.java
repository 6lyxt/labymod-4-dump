// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.particle;

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
        final iz position = new iz(MathHelper.ceil(blockPosition.getX()), MathHelper.ceil(blockPosition.getY()), MathHelper.ceil(blockPosition.getZ()));
        ffg.Q().g.a(position, this.getMinecraftDirection(direction));
    }
    
    public je getMinecraftDirection(final Direction direction) {
        switch (direction) {
            case DOWN: {
                return je.a;
            }
            case UP: {
                return je.b;
            }
            case NORTH: {
                return je.c;
            }
            case SOUTH: {
                return je.d;
            }
            case WEST: {
                return je.e;
            }
            case EAST: {
                return je.f;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
            }
        }
    }
}
