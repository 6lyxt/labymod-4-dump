// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labyconnect.object.marker;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.entity.Entity;
import java.util.UUID;

public interface Marker
{
    UUID getOwner();
    
    @Nullable
    Entity getTargetEntity();
    
    boolean isLarge();
}
