// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.user.permission;

import net.labymod.api.configuration.loader.property.ConfigProperty;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface PermissionRegistry
{
    default ClientPermission register(final String permissionId) {
        return this.register(permissionId, false);
    }
    
    ClientPermission register(@NotNull final String p0, final boolean p1);
    
    @Nullable
    ClientPermission getPermission(final String p0);
    
    boolean isPermissionEnabled(final String p0);
    
    boolean isPermissionEnabled(final String p0, final boolean p1);
    
    default boolean isPermissionEnabled(final String permissionId, final ConfigProperty<Boolean> property) {
        return this.isPermissionEnabled(permissionId, property.getOrDefault());
    }
}
