// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.badge.renderer;

import net.labymod.api.util.KeyValue;
import java.util.List;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.matrix.Stack;

public interface PositionRenderer
{
    public static final int SPACE = 1;
    public static final int SIZE = 8;
    
    int render(final Stack p0, final float p1, final float p2, final NetworkPlayerInfo p3, final List<KeyValue<BadgeRenderer>> p4);
    
    int getWidth(final NetworkPlayerInfo p0, final List<KeyValue<BadgeRenderer>> p1, final boolean p2);
}
