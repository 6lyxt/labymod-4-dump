// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.render.vertex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public interface CustomVertexFormat
{
    void setCustom();
    
    boolean isCustom();
    
    void addElements(final ImmutableMap<String, bmv> p0);
    
    ImmutableList<String> getAttributeNames();
    
    ImmutableMap<String, bmv> getNamedElements();
}
