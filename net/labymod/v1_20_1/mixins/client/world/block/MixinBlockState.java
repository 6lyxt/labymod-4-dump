// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.world.block;

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

@Mixin({ dcb.class })
public abstract class MixinBlockState implements BlockState
{
    @Unique
    private final gu.a labyMod$blockPos;
    private final IntVector3 labyMod$position;
    
    public MixinBlockState() {
        this.labyMod$blockPos = new gu.a(0, 0, 0);
        this.labyMod$position = new IntVector3();
    }
    
    @Shadow
    protected abstract dcb x();
    
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
        return (Block)this.x().b();
    }
    
    @Override
    public int getTopColor() {
        final cmm level = (cmm)enn.N().s;
        if (level == null) {
            return 0;
        }
        final eoo blockColors = enn.N().ax();
        return blockColors.a(this.x(), level, (gu)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel() {
        final few level = enn.N().s;
        if (level == null) {
            return 0;
        }
        return level.s_().a((gu)this.labyMod$blockPos, 0);
    }
    
    @Override
    public int getLightLevel(final LightType lightType) {
        final few level = enn.N().s;
        if (level == null) {
            return 0;
        }
        return level.a((cmv)lightType.toMinecraft(), (gu)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel(final int x, final int y, final int z) {
        final cmm level = (cmm)enn.N().s;
        if (level == null) {
            return 0;
        }
        return level.s_().a(new gu(x, y, z), 0);
    }
    
    @Override
    public RenderShape renderShape() {
        return switch (this.x().l()) {
            default -> throw new MatchException(null, null);
            case a -> RenderShape.INVISIBLE;
            case b -> RenderShape.ENTITY_BLOCK_ANIMATED;
            case c -> RenderShape.MODEL;
        };
    }
    
    @Override
    public boolean isCollisionShapeSolid() {
        return this.x().r((cls)enn.N().s, (gu)this.labyMod$blockPos);
    }
    
    @Override
    public boolean isCollisionShapeSolid(final int x, final int y, final int z) {
        return this.x().r((cls)enn.N().s, new gu(x, y, z));
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds() {
        final efb shape = this.x().j((cls)enn.N().s, (gu)this.labyMod$blockPos);
        if (shape.b()) {
            return null;
        }
        final eed bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
    
    @Override
    public boolean hasCollision() {
        return !this.x().k((cls)enn.N().s, (gu)this.labyMod$blockPos).b();
    }
    
    @Override
    public boolean isFluid() {
        return !this.x().u().c();
    }
    
    @Override
    public boolean isWater() {
        final dxe fluidState = this.x().u();
        return fluidState.b((dxd)dxf.c) || fluidState.b((dxd)dxf.b);
    }
    
    @Override
    public boolean isLava() {
        final dxe fluidState = this.x().u();
        return fluidState.b((dxd)dxf.e) || fluidState.b((dxd)dxf.d);
    }
    
    @Override
    public float getHardness(final Player player) {
        final byo mcPlayer = (byo)player;
        return this.x().a(mcPlayer, (cls)mcPlayer.dI(), (gu)this.labyMod$blockPos);
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds(final int x, final int y, final int z) {
        final efb shape = this.x().j((cls)enn.N().s, new gu(x, y, z));
        if (shape.b()) {
            return null;
        }
        final eed bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
}
