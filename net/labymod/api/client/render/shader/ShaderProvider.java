// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.shader;

import net.labymod.api.client.render.vertex.OldVertexFormat;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ShaderProvider
{
    ShaderProgram createProgram();
    
    ShaderProgram createProgram(final OldVertexFormat p0);
}
