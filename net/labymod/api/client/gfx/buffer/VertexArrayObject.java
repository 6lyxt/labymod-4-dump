// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.buffer;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.Disposable;

public interface VertexArrayObject extends Disposable
{
    int getId();
    
    void bind();
    
    void unbind();
    
    @Referenceable
    public interface Factory
    {
        @Nullable
        VertexArrayObject create();
    }
}
