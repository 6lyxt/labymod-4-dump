// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.world.block;

import net.labymod.api.client.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.client.world.block.RenderShape;
import net.labymod.api.client.world.lighting.LightType;
import net.labymod.api.client.world.block.Block;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.util.math.vector.IntVector3;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.BlockState;

@Mixin({ ebq.class })
public abstract class MixinBlockState implements BlockState
{
    @Unique
    private final iw.a labyMod$blockPos;
    private final IntVector3 labyMod$position;
    
    public MixinBlockState() {
        this.labyMod$blockPos = new iw.a(0, 0, 0);
        this.labyMod$position = new IntVector3();
    }
    
    @Shadow
    protected abstract ebq B();
    
    @Override
    public void setCoordinates(final int x, final int y, final int z) {
        this.labyMod$blockPos.d(x, y, z);
        this.labyMod$position.set(x, y, z);
    }
    
    @Override
    public IntVector3 position() {
        return this.labyMod$position;
    }
    
    @Override
    public Block block() {
        return (Block)this.B().b();
    }
    
    @Override
    public int getTopColor() {
        final dkj level = (dkj)fqq.Q().s;
        if (level == null) {
            return 0;
        }
        final frs blockColors = fqq.Q().aw();
        return blockColors.a(this.B(), level, (iw)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel() {
        final glo level = fqq.Q().s;
        if (level == null) {
            return 0;
        }
        return level.B_().a((iw)this.labyMod$blockPos, 0);
    }
    
    @Override
    public int getLightLevel(final LightType lightType) {
        final glo level = fqq.Q().s;
        if (level == null) {
            return 0;
        }
        return level.a((dks)lightType.toMinecraft(), (iw)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel(final int x, final int y, final int z) {
        final dkj level = (dkj)fqq.Q().s;
        if (level == null) {
            return 0;
        }
        return level.B_().a(new iw(x, y, z), 0);
    }
    
    @Override
    public RenderShape renderShape() {
        return switch (this.B().o()) {
            default -> throw new MatchException(null, null);
            case a -> RenderShape.INVISIBLE;
            case b -> RenderShape.MODEL;
        };
    }
    
    @Override
    public boolean isCollisionShapeSolid() {
        return this.B().m((djn)fqq.Q().s, (iw)this.labyMod$blockPos);
    }
    
    @Override
    public boolean isCollisionShapeSolid(final int x, final int y, final int z) {
        return this.B().m((djn)fqq.Q().s, new iw(x, y, z));
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds() {
        final fgw shape = this.B().f((djn)fqq.Q().s, (iw)this.labyMod$blockPos);
        if (shape.c()) {
            return null;
        }
        final ffx bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
    
    @Override
    public boolean hasCollision() {
        return !this.B().g((djn)fqq.Q().s, (iw)this.labyMod$blockPos).c();
    }
    
    @Override
    public boolean isFluid() {
        return !this.B().y().c();
    }
    
    @Override
    public boolean isWater() {
        final eya fluidState = this.B().y();
        return fluidState.b((exz)eyb.c) || fluidState.b((exz)eyb.b);
    }
    
    @Override
    public boolean isLava() {
        final eya fluidState = this.B().y();
        return fluidState.b((exz)eyb.e) || fluidState.b((exz)eyb.d);
    }
    
    @Override
    public float getHardness(final Player player) {
        final csi mcPlayer = (csi)player;
        return this.B().a(mcPlayer, (djn)mcPlayer.dV(), (iw)this.labyMod$blockPos);
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds(final int x, final int y, final int z) {
        final fgw shape = this.B().f((djn)fqq.Q().s, new iw(x, y, z));
        if (shape.c()) {
            return null;
        }
        final ffx bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
}
