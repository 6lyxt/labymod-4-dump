// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.world;

import net.labymod.api.client.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.client.world.block.RenderShape;
import net.labymod.api.client.world.lighting.LightType;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.api.client.world.block.BlockState;

public class VersionedBlockState implements BlockState
{
    private final alz state;
    private final cj.a blockPos;
    private final IntVector3 position;
    
    public VersionedBlockState(final alz state) {
        this.blockPos = new cj.a();
        this.position = new IntVector3();
        this.state = state;
    }
    
    public VersionedBlockState(final alz state, final int x, final int y, final int z) {
        this.blockPos = new cj.a();
        this.position = new IntVector3();
        this.state = state;
        this.setCoordinates(x, y, z);
    }
    
    @Override
    public void setCoordinates(final int x, final int y, final int z) {
        this.blockPos.c(x, y, z);
        this.position.set(x, y, z);
    }
    
    @Override
    public IntVector3 position() {
        return this.position;
    }
    
    @Override
    public Block block() {
        return (Block)this.state.c();
    }
    
    @Override
    public int getTopColor() {
        final bdb level = this.getLevel();
        if (level == null) {
            return 0;
        }
        final afh block = this.state.c();
        final arn mapColor = block.g(this.state);
        return (mapColor == null) ? -1 : mapColor.L;
    }
    
    @Override
    public int getLightLevel() {
        final bdb level = this.getLevel();
        if (level == null) {
            return 0;
        }
        return level.k((cj)this.blockPos);
    }
    
    @Override
    public int getLightLevel(final LightType lightType) {
        final bdb level = this.getLevel();
        if (level == null) {
            return 0;
        }
        return level.b((ads)lightType.toMinecraft(), (cj)this.blockPos);
    }
    
    @Override
    public int getLightLevel(final int x, final int y, final int z) {
        final bdb level = this.getLevel();
        if (level == null) {
            return 0;
        }
        return level.k(new cj(x, y, z));
    }
    
    @Override
    public RenderShape renderShape() {
        final int renderType = this.state.c().b();
        return switch (renderType) {
            case -1 -> RenderShape.INVISIBLE;
            case 2 -> RenderShape.ENTITY_BLOCK_ANIMATED;
            default -> RenderShape.MODEL;
        };
    }
    
    @Override
    public boolean isCollisionShapeSolid() {
        return this.state.c().d();
    }
    
    @Override
    public boolean isCollisionShapeSolid(final int x, final int y, final int z) {
        return this.isCollisionShapeSolid();
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds() {
        final afh block = this.state.c();
        return new AxisAlignedBoundingBox(block.B(), block.D(), block.F(), block.C(), block.E(), block.G());
    }
    
    @Override
    public boolean hasCollision() {
        final bdb level = this.getLevel();
        return this.state.c().a((adm)level, (cj)this.blockPos, this.state) != null;
    }
    
    @Override
    public boolean isFluid() {
        return this.state.c() instanceof ahv;
    }
    
    @Override
    public boolean isWater() {
        return this.state.c().t() == arm.h;
    }
    
    @Override
    public boolean isLava() {
        return this.state.c().t() == arm.i;
    }
    
    @Override
    public float getHardness(final Player player) {
        final wn entityPlayer = (wn)player;
        return this.state.c().a(entityPlayer, entityPlayer.o, (cj)this.blockPos);
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds(final int x, final int y, final int z) {
        return this.bounds();
    }
    
    public alz getState() {
        return this.state;
    }
    
    @Override
    public String toString() {
        return this.state.toString();
    }
    
    private bdb getLevel() {
        return ave.A().f;
    }
}
