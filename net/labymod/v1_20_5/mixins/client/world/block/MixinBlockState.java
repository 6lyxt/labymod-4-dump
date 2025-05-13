// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.world.block;

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

@Mixin({ dsd.class })
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
    protected abstract dsd x();
    
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
        final dbz level = (dbz)ffg.Q().r;
        if (level == null) {
            return 0;
        }
        final fgj blockColors = ffg.Q().av();
        return blockColors.a(this.x(), level, (iz)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel() {
        final fxw level = ffg.Q().r;
        if (level == null) {
            return 0;
        }
        return level.y_().a((iz)this.labyMod$blockPos, 0);
    }
    
    @Override
    public int getLightLevel(final LightType lightType) {
        final fxw level = ffg.Q().r;
        if (level == null) {
            return 0;
        }
        return level.a((dci)lightType.toMinecraft(), (iz)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel(final int x, final int y, final int z) {
        final dbz level = (dbz)ffg.Q().r;
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
        return this.x().r((dbf)ffg.Q().r, (iz)this.labyMod$blockPos);
    }
    
    @Override
    public boolean isCollisionShapeSolid(final int x, final int y, final int z) {
        return this.x().r((dbf)ffg.Q().r, new iz(x, y, z));
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds() {
        final ewl shape = this.x().j((dbf)ffg.Q().r, (iz)this.labyMod$blockPos);
        if (shape.c()) {
            return null;
        }
        final evn bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
    
    @Override
    public boolean hasCollision() {
        return !this.x().k((dbf)ffg.Q().r, (iz)this.labyMod$blockPos).c();
    }
    
    @Override
    public boolean isFluid() {
        return !this.x().u().c();
    }
    
    @Override
    public boolean isWater() {
        final enw fluidState = this.x().u();
        return fluidState.b((env)enx.c) || fluidState.b((env)enx.b);
    }
    
    @Override
    public boolean isLava() {
        final enw fluidState = this.x().u();
        return fluidState.b((env)enx.e) || fluidState.b((env)enx.d);
    }
    
    @Override
    public float getHardness(final Player player) {
        final cmy mcPlayer = (cmy)player;
        return this.x().a(mcPlayer, (dbf)mcPlayer.dP(), (iz)this.labyMod$blockPos);
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds(final int x, final int y, final int z) {
        final ewl shape = this.x().j((dbf)ffg.Q().r, new iz(x, y, z));
        if (shape.c()) {
            return null;
        }
        final evn bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
}
