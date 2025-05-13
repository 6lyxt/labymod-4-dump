// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.block;

import net.labymod.api.client.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.client.world.lighting.LightType;
import net.labymod.api.util.math.vector.IntVector3;
import org.jetbrains.annotations.ApiStatus;

public interface BlockState
{
    @ApiStatus.Internal
    void setCoordinates(final int p0, final int p1, final int p2);
    
    IntVector3 position();
    
    Block block();
    
    int getTopColor();
    
    int getLightLevel();
    
    int getLightLevel(final LightType p0);
    
    RenderShape renderShape();
    
    boolean isCollisionShapeSolid();
    
    @Nullable
    AxisAlignedBoundingBox bounds();
    
    boolean hasCollision();
    
    boolean isFluid();
    
    boolean isWater();
    
    boolean isLava();
    
    float getHardness(final Player p0);
    
    @Deprecated(forRemoval = true, since = "4.1.18")
    int getLightLevel(final int p0, final int p1, final int p2);
    
    @Deprecated(forRemoval = true, since = "4.1.18")
    boolean isCollisionShapeSolid(final int p0, final int p1, final int p2);
    
    @Deprecated(forRemoval = true, since = "4.1.18")
    @Nullable
    AxisAlignedBoundingBox bounds(final int p0, final int p1, final int p2);
}
