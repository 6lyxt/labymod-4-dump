// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.batch;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface RenderContexts
{
    RectangleRenderContext rectangleRenderContext();
    
    ResourceRenderContext resourceRenderContext();
    
    TriangleRenderContext triangleRenderContext();
    
    LineRenderContext lineRenderContext();
    
    @Nullable
    Stack currentStack();
}
