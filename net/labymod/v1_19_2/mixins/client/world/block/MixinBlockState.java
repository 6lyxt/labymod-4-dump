// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.world.block;

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

@Mixin({ cvo.class })
public abstract class MixinBlockState implements BlockState
{
    @Unique
    private final gt.a labyMod$blockPos;
    private final IntVector3 labyMod$position;
    
    public MixinBlockState() {
        this.labyMod$blockPos = new gt.a(0, 0, 0);
        this.labyMod$position = new IntVector3();
    }
    
    @Shadow
    protected abstract cvo s();
    
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
        return (Block)this.s().b();
    }
    
    @Override
    public int getTopColor() {
        final cgx level = (cgx)efu.I().s;
        if (level == null) {
            return 0;
        }
        final egu blockColors = efu.I().ao();
        return blockColors.a(this.s(), level, (gt)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel() {
        final euv level = efu.I().s;
        if (level == null) {
            return 0;
        }
        return level.l_().b((gt)this.labyMod$blockPos, 0);
    }
    
    @Override
    public int getLightLevel(final LightType lightType) {
        final euv level = efu.I().s;
        if (level == null) {
            return 0;
        }
        return level.a((chg)lightType.toMinecraft(), (gt)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel(final int x, final int y, final int z) {
        final cgx level = (cgx)efu.I().s;
        if (level == null) {
            return 0;
        }
        return level.l_().b(new gt(x, y, z), 0);
    }
    
    @Override
    public RenderShape renderShape() {
        return switch (this.s().i()) {
            default -> throw new MatchException(null, null);
            case a -> RenderShape.INVISIBLE;
            case b -> RenderShape.ENTITY_BLOCK_ANIMATED;
            case c -> RenderShape.MODEL;
        };
    }
    
    @Override
    public boolean isCollisionShapeSolid() {
        return this.s().r((cgd)efu.I().s, (gt)this.labyMod$blockPos);
    }
    
    @Override
    public boolean isCollisionShapeSolid(final int x, final int y, final int z) {
        return this.s().r((cgd)efu.I().s, new gt(x, y, z));
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds() {
        final dxj shape = this.s().j((cgd)efu.I().s, (gt)this.labyMod$blockPos);
        if (shape.b()) {
            return null;
        }
        final dwl bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
    
    @Override
    public boolean hasCollision() {
        return !this.s().k((cgd)efu.I().s, (gt)this.labyMod$blockPos).b();
    }
    
    @Override
    public boolean isFluid() {
        return !this.s().p().c();
    }
    
    @Override
    public boolean isWater() {
        final dpv fluidState = this.s().p();
        return fluidState.b((dpu)dpw.c) || fluidState.b((dpu)dpw.b);
    }
    
    @Override
    public boolean isLava() {
        final dpv fluidState = this.s().p();
        return fluidState.b((dpu)dpw.e) || fluidState.b((dpu)dpw.d);
    }
    
    @Override
    public float getHardness(final Player player) {
        final buc mcPlayer = (buc)player;
        return this.s().a(mcPlayer, (cgd)mcPlayer.s, (gt)this.labyMod$blockPos);
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds(final int x, final int y, final int z) {
        final dxj shape = this.s().j((cgd)efu.I().s, new gt(x, y, z));
        if (shape.b()) {
            return null;
        }
        final dwl bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
}
