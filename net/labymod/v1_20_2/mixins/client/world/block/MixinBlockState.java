// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.world.block;

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

@Mixin({ dfj.class })
public abstract class MixinBlockState implements BlockState
{
    @Unique
    private final gw.a labyMod$blockPos;
    private final IntVector3 labyMod$position;
    
    public MixinBlockState() {
        this.labyMod$blockPos = new gw.a(0, 0, 0);
        this.labyMod$position = new IntVector3();
    }
    
    @Shadow
    protected abstract dfj x();
    
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
        final cpv level = (cpv)eqv.O().r;
        if (level == null) {
            return 0;
        }
        final erw blockColors = eqv.O().aw();
        return blockColors.a(this.x(), level, (gw)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel() {
        final fix level = eqv.O().r;
        if (level == null) {
            return 0;
        }
        return level.x_().a((gw)this.labyMod$blockPos, 0);
    }
    
    @Override
    public int getLightLevel(final LightType lightType) {
        final fix level = eqv.O().r;
        if (level == null) {
            return 0;
        }
        return level.a((cqe)lightType.toMinecraft(), (gw)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel(final int x, final int y, final int z) {
        final cpv level = (cpv)eqv.O().r;
        if (level == null) {
            return 0;
        }
        return level.x_().a(new gw(x, y, z), 0);
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
        return this.x().r((cpb)eqv.O().r, (gw)this.labyMod$blockPos);
    }
    
    @Override
    public boolean isCollisionShapeSolid(final int x, final int y, final int z) {
        return this.x().r((cpb)eqv.O().r, new gw(x, y, z));
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds() {
        final eig shape = this.x().j((cpb)eqv.O().r, (gw)this.labyMod$blockPos);
        if (shape.c()) {
            return null;
        }
        final ehi bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
    
    @Override
    public boolean hasCollision() {
        return !this.x().k((cpb)eqv.O().r, (gw)this.labyMod$blockPos).c();
    }
    
    @Override
    public boolean isFluid() {
        return !this.x().u().c();
    }
    
    @Override
    public boolean isWater() {
        final eam fluidState = this.x().u();
        return fluidState.b((eal)ean.c) || fluidState.b((eal)ean.b);
    }
    
    @Override
    public boolean isLava() {
        final eam fluidState = this.x().u();
        return fluidState.b((eal)ean.e) || fluidState.b((eal)ean.d);
    }
    
    @Override
    public float getHardness(final Player player) {
        final cbu mcPlayer = (cbu)player;
        return this.x().a(mcPlayer, (cpb)mcPlayer.dL(), (gw)this.labyMod$blockPos);
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds(final int x, final int y, final int z) {
        final eig shape = this.x().j((cpb)eqv.O().r, new gw(x, y, z));
        if (shape.c()) {
            return null;
        }
        final ehi bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
}
