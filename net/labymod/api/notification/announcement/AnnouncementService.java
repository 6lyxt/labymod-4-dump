// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.notification.announcement;

import org.jetbrains.annotations.Nullable;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface AnnouncementService
{
    void register(@NotNull final Announcement p0);
    
    void unregister(@NotNull final Announcement p0);
    
    void unregister(@NotNull final String p0);
    
    @NotNull
    List<Announcement> getAnnouncements();
    
    @Nullable
    Announcement get(@NotNull final String p0);
}
