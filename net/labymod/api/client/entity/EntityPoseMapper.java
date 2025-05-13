// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface EntityPoseMapper
{
    EntityPose fromMinecraft(final Object p0);
    
    @Nullable
    Object toMinecraft(final EntityPose p0);
}
