// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client.world.block;

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

@Mixin({ dtc.class })
public abstract class MixinBlockState implements BlockState
{
    @Unique
    private final jd.a labyMod$blockPos;
    private final IntVector3 labyMod$position;
    
    public MixinBlockState() {
        this.labyMod$blockPos = new jd.a(0, 0, 0);
        this.labyMod$position = new IntVector3();
    }
    
    @Shadow
    protected abstract dtc x();
    
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
        final dcw level = (dcw)fgo.Q().r;
        if (level == null) {
            return 0;
        }
        final fhq blockColors = fgo.Q().au();
        return blockColors.a(this.x(), level, (jd)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel() {
        final fzf level = fgo.Q().r;
        if (level == null) {
            return 0;
        }
        return level.y_().a((jd)this.labyMod$blockPos, 0);
    }
    
    @Override
    public int getLightLevel(final LightType lightType) {
        final fzf level = fgo.Q().r;
        if (level == null) {
            return 0;
        }
        return level.a((ddf)lightType.toMinecraft(), (jd)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel(final int x, final int y, final int z) {
        final dcw level = (dcw)fgo.Q().r;
        if (level == null) {
            return 0;
        }
        return level.y_().a(new jd(x, y, z), 0);
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
        return this.x().r((dcc)fgo.Q().r, (jd)this.labyMod$blockPos);
    }
    
    @Override
    public boolean isCollisionShapeSolid(final int x, final int y, final int z) {
        return this.x().r((dcc)fgo.Q().r, new jd(x, y, z));
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds() {
        final exv shape = this.x().j((dcc)fgo.Q().r, (jd)this.labyMod$blockPos);
        if (shape.c()) {
            return null;
        }
        final ewx bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
    
    @Override
    public boolean hasCollision() {
        return !this.x().k((dcc)fgo.Q().r, (jd)this.labyMod$blockPos).c();
    }
    
    @Override
    public boolean isFluid() {
        return !this.x().u().c();
    }
    
    @Override
    public boolean isWater() {
        final epe fluidState = this.x().u();
        return fluidState.b((epd)epf.c) || fluidState.b((epd)epf.b);
    }
    
    @Override
    public boolean isLava() {
        final epe fluidState = this.x().u();
        return fluidState.b((epd)epf.e) || fluidState.b((epd)epf.d);
    }
    
    @Override
    public float getHardness(final Player player) {
        final cmx mcPlayer = (cmx)player;
        return this.x().a(mcPlayer, (dcc)mcPlayer.dO(), (jd)this.labyMod$blockPos);
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds(final int x, final int y, final int z) {
        final exv shape = this.x().j((dcc)fgo.Q().r, new jd(x, y, z));
        if (shape.c()) {
            return null;
        }
        final ewx bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
}
