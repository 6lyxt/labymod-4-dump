// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.world.block;

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

@Mixin({ djh.class })
public abstract class MixinBlockState implements BlockState
{
    @Unique
    private final hx.a labyMod$blockPos;
    private final IntVector3 labyMod$position;
    
    public MixinBlockState() {
        this.labyMod$blockPos = new hx.a(0, 0, 0);
        this.labyMod$position = new IntVector3();
    }
    
    @Shadow
    protected abstract djh x();
    
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
        final ctp level = (ctp)evi.O().r;
        if (level == null) {
            return 0;
        }
        final ewl blockColors = evi.O().au();
        return blockColors.a(this.x(), level, (hx)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel() {
        final fns level = evi.O().r;
        if (level == null) {
            return 0;
        }
        return level.z_().a((hx)this.labyMod$blockPos, 0);
    }
    
    @Override
    public int getLightLevel(final LightType lightType) {
        final fns level = evi.O().r;
        if (level == null) {
            return 0;
        }
        return level.a((cty)lightType.toMinecraft(), (hx)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel(final int x, final int y, final int z) {
        final ctp level = (ctp)evi.O().r;
        if (level == null) {
            return 0;
        }
        return level.z_().a(new hx(x, y, z), 0);
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
        return this.x().r((csv)evi.O().r, (hx)this.labyMod$blockPos);
    }
    
    @Override
    public boolean isCollisionShapeSolid(final int x, final int y, final int z) {
        return this.x().r((csv)evi.O().r, new hx(x, y, z));
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds() {
        final emm shape = this.x().j((csv)evi.O().r, (hx)this.labyMod$blockPos);
        if (shape.c()) {
            return null;
        }
        final elo bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
    
    @Override
    public boolean hasCollision() {
        return !this.x().k((csv)evi.O().r, (hx)this.labyMod$blockPos).c();
    }
    
    @Override
    public boolean isFluid() {
        return !this.x().u().c();
    }
    
    @Override
    public boolean isWater() {
        final eer fluidState = this.x().u();
        return fluidState.b((eeq)ees.c) || fluidState.b((eeq)ees.b);
    }
    
    @Override
    public boolean isLava() {
        final eer fluidState = this.x().u();
        return fluidState.b((eeq)ees.e) || fluidState.b((eeq)ees.d);
    }
    
    @Override
    public float getHardness(final Player player) {
        final cfi mcPlayer = (cfi)player;
        return this.x().a(mcPlayer, (csv)mcPlayer.dM(), (hx)this.labyMod$blockPos);
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds(final int x, final int y, final int z) {
        final emm shape = this.x().j((csv)evi.O().r, new hx(x, y, z));
        if (shape.c()) {
            return null;
        }
        final elo bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
}
