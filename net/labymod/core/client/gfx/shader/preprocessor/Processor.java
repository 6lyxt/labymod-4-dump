// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.shader.preprocessor;

import net.labymod.api.client.gfx.shader.Shader;

public interface Processor
{
    String process(final String p0, final Shader.Type p1);
    
    default void finished() {
    }
}
