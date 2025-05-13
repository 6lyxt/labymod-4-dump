// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render;

import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface PlayerHeartRenderer
{
    void renderHearts(final Stack p0, final float p1, final float p2, final int p3, final NetworkPlayerInfo p4);
}
