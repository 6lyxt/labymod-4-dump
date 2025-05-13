// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex;

import java.util.Collection;
import net.labymod.api.client.render.shader.ShaderProgram;

public interface OldVertexFormat
{
    void setupAttributeLocation(final ShaderProgram p0);
    
    Collection<String> getAttributeNames();
    
     <T> T getMojangVertexFormat();
}
