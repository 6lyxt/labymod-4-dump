// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.world.block;

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

@Mixin({ dbq.class })
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
    protected abstract dbq u();
    
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
        return (Block)this.u().b();
    }
    
    @Override
    public int getTopColor() {
        final cmi level = (cmi)emh.N().s;
        if (level == null) {
            return 0;
        }
        final eni blockColors = emh.N().ax();
        return blockColors.a(this.u(), level, (gt)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel() {
        final fdj level = emh.N().s;
        if (level == null) {
            return 0;
        }
        return level.l_().b((gt)this.labyMod$blockPos, 0);
    }
    
    @Override
    public int getLightLevel(final LightType lightType) {
        final fdj level = emh.N().s;
        if (level == null) {
            return 0;
        }
        return level.a((cmr)lightType.toMinecraft(), (gt)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel(final int x, final int y, final int z) {
        final cmi level = (cmi)emh.N().s;
        if (level == null) {
            return 0;
        }
        return level.l_().b(new gt(x, y, z), 0);
    }
    
    @Override
    public RenderShape renderShape() {
        return switch (this.u().i()) {
            default -> throw new MatchException(null, null);
            case a -> RenderShape.INVISIBLE;
            case b -> RenderShape.ENTITY_BLOCK_ANIMATED;
            case c -> RenderShape.MODEL;
        };
    }
    
    @Override
    public boolean isCollisionShapeSolid() {
        return this.u().r((clo)emh.N().s, (gt)this.labyMod$blockPos);
    }
    
    @Override
    public boolean isCollisionShapeSolid(final int x, final int y, final int z) {
        return this.u().r((clo)emh.N().s, new gt(x, y, z));
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds() {
        final edx shape = this.u().j((clo)emh.N().s, (gt)this.labyMod$blockPos);
        if (shape.b()) {
            return null;
        }
        final ecz bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
    
    @Override
    public boolean hasCollision() {
        return !this.u().k((clo)emh.N().s, (gt)this.labyMod$blockPos).b();
    }
    
    @Override
    public boolean isFluid() {
        return !this.u().r().c();
    }
    
    @Override
    public boolean isWater() {
        final dwj fluidState = this.u().r();
        return fluidState.b((dwi)dwk.c) || fluidState.b((dwi)dwk.b);
    }
    
    @Override
    public boolean isLava() {
        final dwj fluidState = this.u().r();
        return fluidState.b((dwi)dwk.e) || fluidState.b((dwi)dwk.d);
    }
    
    @Override
    public float getHardness(final Player player) {
        final bym mcPlayer = (bym)player;
        return this.u().a(mcPlayer, (clo)mcPlayer.H, (gt)this.labyMod$blockPos);
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds(final int x, final int y, final int z) {
        final edx shape = this.u().j((clo)emh.N().s, new gt(x, y, z));
        if (shape.b()) {
            return null;
        }
        final ecz bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
}
