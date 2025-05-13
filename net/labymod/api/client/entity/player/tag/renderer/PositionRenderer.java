// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.tag.renderer;

import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.util.KeyValue;
import java.util.List;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.matrix.Stack;

public interface PositionRenderer
{
    public static final int SPACE = 1;
    
    void render(final Stack p0, final Entity p1, final List<KeyValue<TagRenderer>> p2, final float p3, final TagType p4);
}
