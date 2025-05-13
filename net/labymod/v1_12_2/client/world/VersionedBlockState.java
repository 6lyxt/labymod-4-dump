// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.world;

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
    private final awt state;
    private final et.a blockPos;
    private final IntVector3 position;
    
    public VersionedBlockState(final awt state) {
        this.blockPos = new et.a();
        this.position = new IntVector3();
        this.state = state;
    }
    
    public VersionedBlockState(final awt state, final int x, final int y, final int z) {
        this.blockPos = new et.a();
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
        return (Block)this.state.u();
    }
    
    @Override
    public int getTopColor() {
        final bsb level = this.getLevel();
        if (level == null) {
            return 0;
        }
        final bik blockColors = bib.z().al();
        return blockColors.a(this.state, (amu)level, (et)this.blockPos);
    }
    
    @Override
    public int getLightLevel() {
        final bsb level = this.getLevel();
        if (level == null) {
            return 0;
        }
        return level.j((et)this.blockPos);
    }
    
    @Override
    public int getLightLevel(final LightType lightType) {
        final bsb level = this.getLevel();
        if (level == null) {
            return 0;
        }
        return level.b((ana)lightType.toMinecraft(), (et)this.blockPos);
    }
    
    @Override
    public int getLightLevel(final int x, final int y, final int z) {
        final bsb level = this.getLevel();
        if (level == null) {
            return 0;
        }
        return level.j(new et(x, y, z));
    }
    
    @Override
    public RenderShape renderShape() {
        final atj renderType = this.state.u().a(this.state);
        return switch (renderType) {
            case a,  b -> RenderShape.INVISIBLE;
            case c -> RenderShape.ENTITY_BLOCK_ANIMATED;
            default -> RenderShape.MODEL;
        };
    }
    
    @Override
    public boolean isCollisionShapeSolid() {
        return this.state.g();
    }
    
    @Override
    public boolean isCollisionShapeSolid(final int x, final int y, final int z) {
        return this.isCollisionShapeSolid();
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds() {
        final bsb level = this.getLevel();
        if (level == null) {
            return null;
        }
        final bhb boundingBox = this.state.e((amy)level, (et)this.blockPos);
        if (boundingBox == null) {
            return null;
        }
        return new AxisAlignedBoundingBox(boundingBox.a, boundingBox.b, boundingBox.c, boundingBox.d, boundingBox.e, boundingBox.f);
    }
    
    @Nullable
    @Override
    public AxisAlignedBoundingBox bounds(final int x, final int y, final int z) {
        return this.bounds();
    }
    
    @Override
    public boolean hasCollision() {
        return this.state.d((amy)this.getLevel(), (et)this.blockPos) != null;
    }
    
    @Override
    public boolean isFluid() {
        return this.state.a().d();
    }
    
    @Override
    public boolean isWater() {
        return this.state.a() == bcz.h;
    }
    
    @Override
    public boolean isLava() {
        return this.state.a() == bcz.i;
    }
    
    @Override
    public float getHardness(final Player player) {
        final aed entityPlayer = (aed)player;
        return this.state.a(entityPlayer, entityPlayer.l, (et)this.blockPos);
    }
    
    public awt getState() {
        return this.state;
    }
    
    @Override
    public String toString() {
        return this.state.toString();
    }
    
    private bsb getLevel() {
        return bib.z().f;
    }
}
