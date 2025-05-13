// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw.builder;

import net.labymod.api.client.gfx.pipeline.texture.data.Sprite;
import net.labymod.api.client.resources.ResourceLocation;

public interface ResourceBuilder<T extends ResourceBuilder<T>> extends RectangleBuilder<T>
{
    T texture(final ResourceLocation p0);
    
    default T sprite16(final float spriteX, final float spriteY) {
        return this.sprite(spriteX, spriteY, 16.0f);
    }
    
    default T sprite32(final float spriteX, final float spriteY) {
        return this.sprite(spriteX, spriteY, 32.0f);
    }
    
    default T sprite(final float spriteX, final float spriteY, final float spriteSize) {
        return this.sprite(spriteX, spriteY, spriteSize, spriteSize);
    }
    
    default T sprite(final Sprite sprite) {
        return this.sprite(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }
    
    T sprite(final float p0, final float p1, final float p2, final float p3);
    
    T resolution(final float p0, final float p1);
    
    T offset(final float p0);
    
    T roundedData(final RoundedGeometryBuilder.RoundedData p0);
    
    T blur(final boolean p0);
}
