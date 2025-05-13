// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.particle;

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
        final jh position = new jh(MathHelper.ceil(blockPosition.getX()), MathHelper.ceil(blockPosition.getY()), MathHelper.ceil(blockPosition.getZ()));
        fmg.Q().g.a(position, this.getMinecraftDirection(direction));
    }
    
    public jm getMinecraftDirection(final Direction direction) {
        switch (direction) {
            case DOWN: {
                return jm.a;
            }
            case UP: {
                return jm.b;
            }
            case NORTH: {
                return jm.c;
            }
            case SOUTH: {
                return jm.d;
            }
            case WEST: {
                return jm.e;
            }
            case EAST: {
                return jm.f;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
            }
        }
    }
}
