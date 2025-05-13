// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.particle;

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
        final hx position = new hx(MathHelper.ceil(blockPosition.getX()), MathHelper.ceil(blockPosition.getY()), MathHelper.ceil(blockPosition.getZ()));
        evi.O().g.a(position, this.getMinecraftDirection(direction));
    }
    
    public ic getMinecraftDirection(final Direction direction) {
        switch (direction) {
            case DOWN: {
                return ic.a;
            }
            case UP: {
                return ic.b;
            }
            case NORTH: {
                return ic.c;
            }
            case SOUTH: {
                return ic.d;
            }
            case WEST: {
                return ic.e;
            }
            case EAST: {
                return ic.f;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
            }
        }
    }
}
