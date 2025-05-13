// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.interaction;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.service.Registry;

@Referenceable
public interface InteractionMenuRegistry extends Registry<BulletPoint>
{
    void unregisterServerBulletPoints();
    
    void registerPlaceHolder(@NotNull final InteractionMenuPlaceholder p0);
}
