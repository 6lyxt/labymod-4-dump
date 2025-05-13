// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model;

import java.util.Map;

public interface ModelPartHolder
{
    void addChild(final String p0, final ModelPart p1);
    
    ModelPart getChild(final String p0);
    
    Map<String, ModelPart> getChildren();
}
