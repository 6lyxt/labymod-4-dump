// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.world.block;

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

@Mixin({ cyt.class })
public abstract class MixinBlockState implements BlockState
{
    @Unique
    private final gp.a labyMod$blockPos;
    private final IntVector3 labyMod$position;
    
    public MixinBlockState() {
        this.labyMod$blockPos = new gp.a(0, 0, 0);
        this.labyMod$position = new IntVector3();
    }
    
    @Shadow
    protected abstract cyt t();
    
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
        return (Block)this.t().b();
    }
    
    @Override
    public int getTopColor() {
        final cjw level = (cjw)ejf.N().s;
        if (level == null) {
            return 0;
        }
        final ekg blockColors = ejf.N().ax();
        return blockColors.a(this.t(), level, (gp)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel() {
        final eyz level = ejf.N().s;
        if (level == null) {
            return 0;
        }
        return level.m_().b((gp)this.labyMod$blockPos, 0);
    }
    
    @Override
    public int getLightLevel(final LightType lightType) {
        final eyz level = ejf.N().s;
        if (level == null) {
            return 0;
        }
        return level.a((ckf)lightType.toMinecraft(), (gp)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel(final int x, final int y, final int z) {
        final cjw level = (cjw)ejf.N().s;
        if (level == null) {
            return 0;
        }
        return level.m_().b(new gp(x, y, z), 0);
    }
    
    @Override
    public RenderShape renderShape() {
        return switch (this.t().i()) {
            default -> throw new MatchException(null, null);
            case a -> RenderShape.INVISIBLE;
            case b -> RenderShape.ENTITY_BLOCK_ANIMATED;
            case c -> RenderShape.MODEL;
        };
    }
    
    @Override
    public boolean isCollisionShapeSolid() {
        return this.t().r((cjc)ejf.N().s, (gp)this.labyMod$blockPos);
    }
    
    @Override
    public boolean isCollisionShapeSolid(final int x, final int y, final int z) {
        return this.t().r((cjc)ejf.N().s, new gp(x, y, z));
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds() {
        final eax shape = this.t().j((cjc)ejf.N().s, (gp)this.labyMod$blockPos);
        if (shape.b()) {
            return null;
        }
        final dzz bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
    
    @Override
    public boolean hasCollision() {
        return !this.t().k((cjc)ejf.N().s, (gp)this.labyMod$blockPos).b();
    }
    
    @Override
    public boolean isFluid() {
        return !this.t().q().c();
    }
    
    @Override
    public boolean isWater() {
        final dtj fluidState = this.t().q();
        return fluidState.b((dti)dtk.c) || fluidState.b((dti)dtk.b);
    }
    
    @Override
    public boolean isLava() {
        final dtj fluidState = this.t().q();
        return fluidState.b((dti)dtk.e) || fluidState.b((dti)dtk.d);
    }
    
    @Override
    public float getHardness(final Player player) {
        final bwp mcPlayer = (bwp)player;
        return this.t().a(mcPlayer, (cjc)mcPlayer.s, (gp)this.labyMod$blockPos);
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds(final int x, final int y, final int z) {
        final eax shape = this.t().j((cjc)ejf.N().s, new gp(x, y, z));
        if (shape.b()) {
            return null;
        }
        final dzz bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
}
