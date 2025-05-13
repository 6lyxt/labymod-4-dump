// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.signobject.object;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.signobject.template.SignObjectMeta;

public interface SignObject3DRenderer<M extends SignObjectMeta<?>>
{
    void render3D(final Stack p0, final SignObject<M> p1, final double p2, final double p3, final double p4, final float p5);
}
