// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw.batch;

import net.labymod.api.client.render.draw.builder.RectangleBuilder;

public interface BatchRenderer<T extends RectangleBuilder<T>> extends RectangleBuilder<T>
{
    void upload();
    
    T build();
}
