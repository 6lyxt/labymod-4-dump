// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.world.block;

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

@Mixin({ cov.class })
public abstract class MixinBlockState implements BlockState
{
    @Unique
    private final gj.a labyMod$blockPos;
    private final IntVector3 labyMod$position;
    
    public MixinBlockState() {
        this.labyMod$blockPos = new gj.a(0, 0, 0);
        this.labyMod$position = new IntVector3();
    }
    
    @Shadow
    protected abstract cov r();
    
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
        return (Block)this.r().b();
    }
    
    @Override
    public int getTopColor() {
        final cav level = (cav)dyr.D().r;
        if (level == null) {
            return 0;
        }
        final dzj blockColors = dyr.D().ak();
        return blockColors.a(this.r(), level, (gj)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel() {
        final ems level = dyr.D().r;
        if (level == null) {
            return 0;
        }
        return level.l_().b((gj)this.labyMod$blockPos, 0);
    }
    
    @Override
    public int getLightLevel(final LightType lightType) {
        final ems level = dyr.D().r;
        if (level == null) {
            return 0;
        }
        return level.a((cbe)lightType.toMinecraft(), (gj)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel(final int x, final int y, final int z) {
        final cav level = (cav)dyr.D().r;
        if (level == null) {
            return 0;
        }
        return level.l_().b(new gj(x, y, z), 0);
    }
    
    @Override
    public RenderShape renderShape() {
        return switch (this.r().h()) {
            default -> throw new MatchException(null, null);
            case a -> RenderShape.INVISIBLE;
            case b -> RenderShape.ENTITY_BLOCK_ANIMATED;
            case c -> RenderShape.MODEL;
        };
    }
    
    @Override
    public boolean isCollisionShapeSolid() {
        return this.r().r((cab)dyr.D().r, (gj)this.labyMod$blockPos);
    }
    
    @Override
    public boolean isCollisionShapeSolid(final int x, final int y, final int z) {
        return this.r().r((cab)dyr.D().r, new gj(x, y, z));
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds() {
        final dqh shape = this.r().j((cab)dyr.D().r, (gj)this.labyMod$blockPos);
        if (shape.b()) {
            return null;
        }
        final dpj bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
    
    @Override
    public boolean hasCollision() {
        return !this.r().k((cab)dyr.D().r, (gj)this.labyMod$blockPos).b();
    }
    
    @Override
    public boolean isFluid() {
        return !this.r().o().c();
    }
    
    @Override
    public boolean isWater() {
        final dix fluidState = this.r().o();
        return fluidState.b((diw)diy.c) || fluidState.b((diw)diy.b);
    }
    
    @Override
    public boolean isLava() {
        final dix fluidState = this.r().o();
        return fluidState.b((diw)diy.e) || fluidState.b((diw)diy.d);
    }
    
    @Override
    public float getHardness(final Player player) {
        final boj mcPlayer = (boj)player;
        return this.r().a(mcPlayer, (cab)mcPlayer.s, (gj)this.labyMod$blockPos);
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds(final int x, final int y, final int z) {
        final dqh shape = this.r().j((cab)dyr.D().r, new gj(x, y, z));
        if (shape.b()) {
            return null;
        }
        final dpj bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
}
