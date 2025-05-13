// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.world.block;

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

@Mixin({ dse.class })
public abstract class MixinBlockState implements BlockState
{
    @Unique
    private final iz.a labyMod$blockPos;
    private final IntVector3 labyMod$position;
    
    public MixinBlockState() {
        this.labyMod$blockPos = new iz.a(0, 0, 0);
        this.labyMod$position = new IntVector3();
    }
    
    @Shadow
    protected abstract dse x();
    
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
        final dca level = (dca)ffh.Q().r;
        if (level == null) {
            return 0;
        }
        final fgk blockColors = ffh.Q().av();
        return blockColors.a(this.x(), level, (iz)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel() {
        final fxx level = ffh.Q().r;
        if (level == null) {
            return 0;
        }
        return level.y_().a((iz)this.labyMod$blockPos, 0);
    }
    
    @Override
    public int getLightLevel(final LightType lightType) {
        final fxx level = ffh.Q().r;
        if (level == null) {
            return 0;
        }
        return level.a((dcj)lightType.toMinecraft(), (iz)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel(final int x, final int y, final int z) {
        final dca level = (dca)ffh.Q().r;
        if (level == null) {
            return 0;
        }
        return level.y_().a(new iz(x, y, z), 0);
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
        return this.x().r((dbg)ffh.Q().r, (iz)this.labyMod$blockPos);
    }
    
    @Override
    public boolean isCollisionShapeSolid(final int x, final int y, final int z) {
        return this.x().r((dbg)ffh.Q().r, new iz(x, y, z));
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds() {
        final ewm shape = this.x().j((dbg)ffh.Q().r, (iz)this.labyMod$blockPos);
        if (shape.c()) {
            return null;
        }
        final evo bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
    
    @Override
    public boolean hasCollision() {
        return !this.x().k((dbg)ffh.Q().r, (iz)this.labyMod$blockPos).c();
    }
    
    @Override
    public boolean isFluid() {
        return !this.x().u().c();
    }
    
    @Override
    public boolean isWater() {
        final enx fluidState = this.x().u();
        return fluidState.b((enw)eny.c) || fluidState.b((enw)eny.b);
    }
    
    @Override
    public boolean isLava() {
        final enx fluidState = this.x().u();
        return fluidState.b((enw)eny.e) || fluidState.b((enw)eny.d);
    }
    
    @Override
    public float getHardness(final Player player) {
        final cmz mcPlayer = (cmz)player;
        return this.x().a(mcPlayer, (dbg)mcPlayer.dP(), (iz)this.labyMod$blockPos);
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds(final int x, final int y, final int z) {
        final ewm shape = this.x().j((dbg)ffh.Q().r, new iz(x, y, z));
        if (shape.c()) {
            return null;
        }
        final evo bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
}
