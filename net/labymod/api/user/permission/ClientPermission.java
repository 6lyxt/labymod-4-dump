// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.user.permission;

import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.serverapi.PermissionStateChangeEvent;
import net.labymod.api.configuration.settings.SettingOverlayInfo;
import net.labymod.api.client.component.Component;
import net.labymod.serverapi.core.model.moderation.Permission;

public class ClientPermission extends Permission
{
    private static final String SERVER_FEATURES_TRANSLATION_KEY = "labymod.ui.settings.serverFeatures";
    private final Component displayName;
    private final boolean defaultEnabled;
    private boolean enabled;
    private boolean stateChangeable;
    private SettingOverlayInfo overlayInfo;
    
    public ClientPermission(final String permissionId, final String translatableKey, final boolean defaultEnabled) {
        this(permissionId, translatableKey, defaultEnabled, defaultEnabled);
    }
    
    public ClientPermission(final String permissionId, final String translatableKey, final boolean enabled, final boolean defaultEnabled) {
        super(permissionId);
        this.stateChangeable = true;
        this.enabled = enabled;
        this.displayName = Component.translatable(translatableKey, new Component[0]);
        this.defaultEnabled = defaultEnabled;
    }
    
    public Component displayName() {
        return this.displayName;
    }
    
    public boolean isDefaultEnabled() {
        return this.defaultEnabled;
    }
    
    public void setDefaultEnabled() {
        this.setEnabled(this.defaultEnabled);
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        if (this.isEnabled() == enabled) {
            return;
        }
        if (!this.stateChangeable) {
            throw new IllegalStateException("You cannot enable/disable this permission in the event context");
        }
        this.enabled = enabled;
        this.stateChangeable = false;
        Laby.fireEvent(new PermissionStateChangeEvent(this, enabled ? PermissionStateChangeEvent.State.ALLOWED : PermissionStateChangeEvent.State.DISALLOWED));
        this.stateChangeable = true;
    }
    
    public SettingOverlayInfo overlayInfo() {
        if (this.overlayInfo == null) {
            final String translation = this.isDefaultEnabled() ? "labymod.ui.settings.serverFeatures.disabled" : "labymod.ui.settings.serverFeatures.disabledDefault";
            this.overlayInfo = new SettingOverlayInfo(() -> !Laby.labyAPI().serverController().isConnected() || this.isEnabled(), Component.translatable(translation, NamedTextColor.RED), true);
        }
        return this.overlayInfo;
    }
}
