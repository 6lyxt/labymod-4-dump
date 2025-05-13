// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.particle;

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
        final jd position = new jd(MathHelper.ceil(blockPosition.getX()), MathHelper.ceil(blockPosition.getY()), MathHelper.ceil(blockPosition.getZ()));
        fgo.Q().g.a(position, this.getMinecraftDirection(direction));
    }
    
    public ji getMinecraftDirection(final Direction direction) {
        switch (direction) {
            case DOWN: {
                return ji.a;
            }
            case UP: {
                return ji.b;
            }
            case NORTH: {
                return ji.c;
            }
            case SOUTH: {
                return ji.d;
            }
            case WEST: {
                return ji.e;
            }
            case EAST: {
                return ji.f;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
            }
        }
    }
}
