// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client.world.block;

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

@Mixin({ dwy.class })
public abstract class MixinBlockState implements BlockState
{
    @Unique
    private final ji.a labyMod$blockPos;
    private final IntVector3 labyMod$position;
    
    public MixinBlockState() {
        this.labyMod$blockPos = new ji.a(0, 0, 0);
        this.labyMod$position = new IntVector3();
    }
    
    @Shadow
    protected abstract dwy B();
    
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
        final dgj level = (dgj)flk.Q().s;
        if (level == null) {
            return 0;
        }
        final fmm blockColors = flk.Q().aw();
        return blockColors.a(this.B(), level, (ji)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel() {
        final gga level = flk.Q().s;
        if (level == null) {
            return 0;
        }
        return level.C_().a((ji)this.labyMod$blockPos, 0);
    }
    
    @Override
    public int getLightLevel(final LightType lightType) {
        final gga level = flk.Q().s;
        if (level == null) {
            return 0;
        }
        return level.a((dgs)lightType.toMinecraft(), (ji)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel(final int x, final int y, final int z) {
        final dgj level = (dgj)flk.Q().s;
        if (level == null) {
            return 0;
        }
        return level.C_().a(new ji(x, y, z), 0);
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
        return this.B().m((dfo)flk.Q().s, (ji)this.labyMod$blockPos);
    }
    
    @Override
    public boolean isCollisionShapeSolid(final int x, final int y, final int z) {
        return this.B().m((dfo)flk.Q().s, new ji(x, y, z));
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds() {
        final fbv shape = this.B().f((dfo)flk.Q().s, (ji)this.labyMod$blockPos);
        if (shape.c()) {
            return null;
        }
        final faw bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
    
    @Override
    public boolean hasCollision() {
        return !this.B().g((dfo)flk.Q().s, (ji)this.labyMod$blockPos).c();
    }
    
    @Override
    public boolean isFluid() {
        return !this.B().y().c();
    }
    
    @Override
    public boolean isWater() {
        final eta fluidState = this.B().y();
        return fluidState.b((esz)etb.c) || fluidState.b((esz)etb.b);
    }
    
    @Override
    public boolean isLava() {
        final eta fluidState = this.B().y();
        return fluidState.b((esz)etb.e) || fluidState.b((esz)etb.d);
    }
    
    @Override
    public float getHardness(final Player player) {
        final coy mcPlayer = (coy)player;
        return this.B().a(mcPlayer, (dfo)mcPlayer.dV(), (ji)this.labyMod$blockPos);
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds(final int x, final int y, final int z) {
        final fbv shape = this.B().f((dfo)flk.Q().s, new ji(x, y, z));
        if (shape.c()) {
            return null;
        }
        final faw bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
}
