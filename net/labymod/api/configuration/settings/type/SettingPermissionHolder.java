// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.type;

import net.labymod.api.configuration.loader.annotation.PermissionRequired;

public class SettingPermissionHolder
{
    private String permissionId;
    private boolean canForceEnable;
    
    public SettingPermissionHolder() {
    }
    
    public SettingPermissionHolder(final PermissionRequired permissionRequired) {
        this(permissionRequired.value(), permissionRequired.canForceEnable());
    }
    
    public SettingPermissionHolder(final String permissionId, final boolean canForceEnable) {
        this.permissionId = permissionId;
        this.canForceEnable = canForceEnable;
    }
    
    public String getPermissionId() {
        return this.permissionId;
    }
    
    public void setPermissionId(final String permissionId) {
        this.permissionId = permissionId;
    }
    
    public boolean isCanForceEnable() {
        return this.canForceEnable;
    }
    
    public void setCanForceEnable(final boolean canForceEnable) {
        this.canForceEnable = canForceEnable;
    }
    
    public void set(final SettingPermissionHolder other) {
        this.permissionId = other.permissionId;
        this.canForceEnable = other.canForceEnable;
    }
}
