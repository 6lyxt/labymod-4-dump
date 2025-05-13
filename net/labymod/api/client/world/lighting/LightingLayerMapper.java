// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.lighting;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface LightingLayerMapper
{
    LightType fromMinecraft(final Object p0);
    
    Object toMinecraft(final LightType p0);
}
