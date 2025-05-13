// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.world.block;

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

@Mixin({ ckt.class })
public abstract class MixinBlockState implements BlockState
{
    @Unique
    private final gg.a labyMod$blockPos;
    private final IntVector3 labyMod$position;
    
    public MixinBlockState() {
        this.labyMod$blockPos = new gg.a(0, 0, 0);
        this.labyMod$position = new IntVector3();
    }
    
    @Shadow
    protected abstract ckt q();
    
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
        return (Block)this.q().b();
    }
    
    @Override
    public int getTopColor() {
        final bwq level = (bwq)dvp.C().r;
        if (level == null) {
            return 0;
        }
        final dwf blockColors = dvp.C().al();
        return blockColors.a(this.q(), level, (gg)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel() {
        final eji level = dvp.C().r;
        if (level == null) {
            return 0;
        }
        return level.k_().b((gg)this.labyMod$blockPos, 0);
    }
    
    @Override
    public int getLightLevel(final LightType lightType) {
        final eji level = dvp.C().r;
        if (level == null) {
            return 0;
        }
        return level.a((bwz)lightType.toMinecraft(), (gg)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel(final int x, final int y, final int z) {
        final bwq level = (bwq)dvp.C().r;
        if (level == null) {
            return 0;
        }
        return level.k_().b(new gg(x, y, z), 0);
    }
    
    @Override
    public RenderShape renderShape() {
        return switch (this.q().h()) {
            default -> throw new MatchException(null, null);
            case a -> RenderShape.INVISIBLE;
            case b -> RenderShape.ENTITY_BLOCK_ANIMATED;
            case c -> RenderShape.MODEL;
        };
    }
    
    @Override
    public boolean isCollisionShapeSolid() {
        return this.q().r((bvu)dvp.C().r, (gg)this.labyMod$blockPos);
    }
    
    @Override
    public boolean isCollisionShapeSolid(final int x, final int y, final int z) {
        return this.q().r((bvu)dvp.C().r, new gg(x, y, z));
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds() {
        final dnt shape = this.q().j((bvu)dvp.C().r, (gg)this.labyMod$blockPos);
        if (shape.b()) {
            return null;
        }
        final dmv bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
    
    @Override
    public boolean hasCollision() {
        return !this.q().k((bvu)dvp.C().r, (gg)this.labyMod$blockPos).b();
    }
    
    @Override
    public boolean isFluid() {
        return !this.q().n().c();
    }
    
    @Override
    public boolean isWater() {
        final des fluidState = this.q().n();
        return fluidState.a((aga)aft.b);
    }
    
    @Override
    public boolean isLava() {
        final des fluidState = this.q().n();
        return fluidState.a((aga)aft.c);
    }
    
    @Override
    public float getHardness(final Player player) {
        final bke mcPlayer = (bke)player;
        return this.q().a(mcPlayer, (bvu)mcPlayer.t, (gg)this.labyMod$blockPos);
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds(final int x, final int y, final int z) {
        final dnt shape = this.q().j((bvu)dvp.C().r, new gg(x, y, z));
        if (shape.b()) {
            return null;
        }
        final dmv bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
}
