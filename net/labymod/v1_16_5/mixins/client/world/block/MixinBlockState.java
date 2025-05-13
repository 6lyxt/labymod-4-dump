// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.world.block;

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

@Mixin({ ceh.class })
public abstract class MixinBlockState implements BlockState
{
    @Unique
    private final fx.a labyMod$blockPos;
    private final IntVector3 labyMod$position;
    
    public MixinBlockState() {
        this.labyMod$blockPos = new fx.a(0, 0, 0);
        this.labyMod$position = new IntVector3();
    }
    
    @Shadow
    protected abstract ceh p();
    
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
        return (Block)this.p().b();
    }
    
    @Override
    public int getTopColor() {
        final brx level = (brx)djz.C().r;
        if (level == null) {
            return 0;
        }
        final dko blockColors = djz.C().al();
        return blockColors.a(this.p(), level, (fx)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel() {
        final dwt level = djz.C().r;
        if (level == null) {
            return 0;
        }
        return level.e().b((fx)this.labyMod$blockPos, 0);
    }
    
    @Override
    public int getLightLevel(final LightType lightType) {
        final dwt level = djz.C().r;
        if (level == null) {
            return 0;
        }
        return level.a((bsf)lightType.toMinecraft(), (fx)this.labyMod$blockPos);
    }
    
    @Override
    public int getLightLevel(final int x, final int y, final int z) {
        final brx level = (brx)djz.C().r;
        if (level == null) {
            return 0;
        }
        return level.e().b(new fx(x, y, z), 0);
    }
    
    @Override
    public RenderShape renderShape() {
        return switch (this.p().h()) {
            default -> throw new MatchException(null, null);
            case a -> RenderShape.INVISIBLE;
            case b -> RenderShape.ENTITY_BLOCK_ANIMATED;
            case c -> RenderShape.MODEL;
        };
    }
    
    @Override
    public boolean isCollisionShapeSolid() {
        return this.p().r((brc)djz.C().r, (fx)this.labyMod$blockPos);
    }
    
    @Override
    public boolean isCollisionShapeSolid(final int x, final int y, final int z) {
        return this.p().r((brc)djz.C().r, new fx(x, y, z));
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds() {
        final ddh shape = this.p().j((brc)djz.C().r, (fx)this.labyMod$blockPos);
        if (shape.b()) {
            return null;
        }
        final dci bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
    
    @Override
    public boolean hasCollision() {
        return !this.p().k((brc)djz.C().r, (fx)this.labyMod$blockPos).b();
    }
    
    @Override
    public boolean isFluid() {
        return !this.p().m().c();
    }
    
    @Override
    public boolean isWater() {
        final cux fluidState = this.p().m();
        return fluidState.a((ael)aef.b);
    }
    
    @Override
    public boolean isLava() {
        final cux fluidState = this.p().m();
        return fluidState.a((ael)aef.c);
    }
    
    @Override
    public float getHardness(final Player player) {
        final bfw mcPlayer = (bfw)player;
        return this.p().a(mcPlayer, (brc)mcPlayer.l, (fx)this.labyMod$blockPos);
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds(final int x, final int y, final int z) {
        final ddh shape = this.p().j((brc)djz.C().r, new fx(x, y, z));
        if (shape.b()) {
            return null;
        }
        final dci bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }
}
