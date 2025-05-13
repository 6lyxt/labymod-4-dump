// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw.builder;

public interface CircleBuilder<T extends CircleBuilder<T>> extends RendererBuilder<T>
{
    default T radius(final float radius) {
        return this.donutRadius(0.0f, radius);
    }
    
    T donutRadius(final float p0, final float p1);
    
    T partial(final float p0, final float p1);
}
