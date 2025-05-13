// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.permission;

import java.util.Iterator;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import java.util.Locale;
import net.labymod.core.addon.AddonClassLoader;
import org.jetbrains.annotations.NotNull;
import javax.inject.Inject;
import java.util.HashMap;
import net.labymod.api.user.permission.ClientPermission;
import java.util.Map;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.user.permission.PermissionRegistry;

@Singleton
@Implements(PermissionRegistry.class)
public class DefaultPermissionRegistry implements PermissionRegistry
{
    private static final StackWalker WALKER;
    private final Map<String, ClientPermission> permissions;
    
    @Inject
    public DefaultPermissionRegistry() {
        this.permissions = new HashMap<String, ClientPermission>();
    }
    
    @Override
    public ClientPermission register(@NotNull final String permissionId, final boolean defaultEnabled) {
        final Class<?> callerClass = DefaultPermissionRegistry.WALKER.getCallerClass();
        final ClassLoader classLoader = callerClass.getClassLoader();
        String addonId = "labymod";
        if (classLoader instanceof final AddonClassLoader addonClassLoader) {
            addonId = addonClassLoader.getAddonInfo().getNamespace();
        }
        final ClientPermission permission = new ClientPermission(permissionId, String.format(Locale.ROOT, "%s.permissions.%s.name", addonId, permissionId.replace("_", "-")), defaultEnabled);
        this.permissions.put(permissionId, permission);
        return permission;
    }
    
    @Nullable
    @Override
    public ClientPermission getPermission(final String permissionId) {
        return this.permissions.get(permissionId);
    }
    
    @Override
    public boolean isPermissionEnabled(final String permissionId) {
        final ClientPermission permission = this.getPermission(permissionId);
        return permission != null && (permission.isEnabled() || !Laby.labyAPI().serverController().isConnected());
    }
    
    @Override
    public boolean isPermissionEnabled(final String permissionId, final boolean condition) {
        return this.isPermissionEnabled(permissionId) && condition;
    }
    
    public void registerDefaultPermissions() {
        this.register("improved_lava", false);
        this.register("crosshair_sync", false);
        this.register("refill_fix", false);
        this.register("range", false);
        this.register("slowdown", false);
        this.register("entity_marker", false);
        this.register("gui_all", true);
        this.register("gui_potion_effects", true);
        this.register("gui_armor_hud", true);
        this.register("gui_item_hud", true);
        this.register("chat_autotext", true);
        this.register("blockbuild", true);
        this.register("sneaking", true);
        this.register("sneaking_visual", true);
        this.register("tags", true);
        this.register("chat", true);
        this.register("animations", true);
        this.register("saturation_bar", true);
    }
    
    public void disableAllPermissions() {
        for (final ClientPermission value : this.permissions.values()) {
            value.setEnabled(false);
        }
    }
    
    public void resetPermissions() {
        for (final ClientPermission clientPermission : this.permissions.values()) {
            clientPermission.setDefaultEnabled();
        }
    }
    
    public void setPermission(final String id, final boolean enabled) {
        final ClientPermission permission = this.getPermission(id);
        if (permission != null) {
            permission.setEnabled(enabled);
        }
    }
    
    static {
        WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    }
}
