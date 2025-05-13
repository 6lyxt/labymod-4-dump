// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry;

import java.util.Objects;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.resources.texture.GameImage;

public class DepthMap
{
    private final GameImage image;
    private final int width;
    private final int height;
    
    public DepthMap(final GameImage image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }
    
    public boolean shouldRenderFace(final Rectangle bounds, final int x, final int y, final int face) {
        return this.hasDepthAt(bounds, x, y) && !this.hasDepthInFacing(bounds, x, y, face);
    }
    
    public boolean hasDepthInFacing(final Rectangle bounds, final int x, final int y, final int face) {
        switch (face) {
            case 0: {
                return this.hasDepthAt(bounds, x + 1, y);
            }
            case 1: {
                return this.hasDepthAt(bounds, x - 1, y);
            }
            case 2: {
                return this.hasDepthAt(bounds, x, y - 1);
            }
            case 3: {
                return this.hasDepthAt(bounds, x, y + 1);
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean hasDepthAt(final Rectangle bounds, final int x, final int y) {
        return x >= bounds.getX() && y >= bounds.getY() && x < bounds.getX() + bounds.getWidth() && y < bounds.getY() + bounds.getHeight() && this.hasDepth(this.image.getARGB(x, y));
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    private boolean hasDepth(final int color) {
        return ColorFormat.ARGB32.alpha(color) > 0;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DepthMap depthMap = (DepthMap)o;
        return this.width == depthMap.width && this.height == depthMap.height && Objects.equals(this.image, depthMap.image);
    }
    
    @Override
    public int hashCode() {
        int result = (this.image != null) ? this.image.hashCode() : 0;
        result = 31 * result + this.width;
        result = 31 * result + this.height;
        return result;
    }
}
