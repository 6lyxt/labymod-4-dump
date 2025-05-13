// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.world.block;

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

@Mixin({ dxv.class })
public abstract class MixinBlockState implements BlockState
{
    @Unique
    private final jh.a labyMod$blockPos;
    private final IntVector3 labyMod$position;
    
    public MixinBlockState() {
        this.labyMod$blockPos = new jh.a(0, 0, 0);
        this.labyMod$position = new IntVector3();
    }
    
    @Shadow
    protected abstract dxv B();
    
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
        final dhi level = (dhi)fmg.Q().s;
        if (level == null) {
            return 0;
        }
        final fni blockColors = fmg.Q().aw();
        return blockColors.a(this.B(), level, (jh)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel() {
        final gfk level = fmg.Q().s;
        if (level == null) {
            return 0;
        }
        return level.C_().a((jh)this.labyMod$blockPos, 0);
    }
    
    @Override
    public int getLightLevel(final LightType lightType) {
        final gfk level = fmg.Q().s;
        if (level == null) {
            return 0;
        }
        return level.a((dhr)lightType.toMinecraft(), (jh)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel(final int x, final int y, final int z) {
        final dhi level = (dhi)fmg.Q().s;
        if (level == null) {
            return 0;
        }
        return level.C_().a(new jh(x, y, z), 0);
    }
    
    @Override
    public RenderShape renderShape() {
        return switch (this.B().o()) {
            default -> throw new MatchException(null, null);
            case a -> RenderShape.INVISIBLE;
            case b -> RenderShape.ENTITY_BLOCK_ANIMATED;
            case c -> RenderShape.MODEL;
        };
    }
    
    @Override
    public boolean isCollisionShapeSolid() {
        return this.B().m((dgn)fmg.Q().s, (jh)this.labyMod$blockPos);
    }
    
    @Override
    public boolean isCollisionShapeSolid(final int x, final int y, final int z) {
        return this.B().m((dgn)fmg.Q().s, new jh(x, y, z));
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds() {
        final fcs shape = this.B().f((dgn)fmg.Q().s, (jh)this.labyMod$blockPos);
        if (shape.c()) {
            return null;
        }
        final fbt bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
    
    @Override
    public boolean hasCollision() {
        return !this.B().g((dgn)fmg.Q().s, (jh)this.labyMod$blockPos).c();
    }
    
    @Override
    public boolean isFluid() {
        return !this.B().y().c();
    }
    
    @Override
    public boolean isWater() {
        final etx fluidState = this.B().y();
        return fluidState.b((etw)ety.c) || fluidState.b((etw)ety.b);
    }
    
    @Override
    public boolean isLava() {
        final etx fluidState = this.B().y();
        return fluidState.b((etw)ety.e) || fluidState.b((etw)ety.d);
    }
    
    @Override
    public float getHardness(final Player player) {
        final cpx mcPlayer = (cpx)player;
        return this.B().a(mcPlayer, (dgn)mcPlayer.dW(), (jh)this.labyMod$blockPos);
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds(final int x, final int y, final int z) {
        final fcs shape = this.B().f((dgn)fmg.Q().s, new jh(x, y, z));
        if (shape.c()) {
            return null;
        }
        final fbt bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
}
