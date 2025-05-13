// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.signobject.object;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.client.world.signobject.template.SignObjectMeta;

public interface SignObject<M extends SignObjectMeta<?>>
{
    M meta();
    
    SignObjectPosition position();
    
    long creationTimestamp();
    
    long lastRenderTimestamp();
    
    boolean isEnabled();
    
    void addListener(final SignObjectListener<M> p0);
    
    void render(final Stack p0, final double p1, final double p2, final double p3, final float p4);
    
    void dispose();
    
    boolean hasRendering();
    
    default <M extends SignObjectMeta<?>> SignObject<M> createDummy(final M meta, final SignObjectPosition position) {
        return new DummySignObject<M>(meta, position);
    }
    
    default <M extends SignObjectMeta<?>> SignObject<M> create3D(final M meta, final SignObjectPosition position, final SignObject3DRenderer<M> renderer) {
        final SignObject3D<M> object = new SignObject3D<M>(meta, position);
        object.set3DRenderer(renderer);
        return object;
    }
}
