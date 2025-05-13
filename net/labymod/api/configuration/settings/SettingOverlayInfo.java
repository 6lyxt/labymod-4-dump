// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings;

import java.util.Objects;
import net.labymod.api.client.component.Component;
import java.util.function.BooleanSupplier;

public final class SettingOverlayInfo
{
    private final BooleanSupplier activeSupplier;
    private final Component component;
    private final boolean infoAlwaysVisible;
    
    public SettingOverlayInfo(final BooleanSupplier activeSupplier, final Component component, final boolean infoAlwaysVisible) {
        this.activeSupplier = activeSupplier;
        this.component = component;
        this.infoAlwaysVisible = infoAlwaysVisible;
    }
    
    public boolean isActive() {
        return this.activeSupplier.getAsBoolean();
    }
    
    public Component component() {
        return this.component;
    }
    
    public boolean isExclamationMarkAlwaysVisible() {
        return this.infoAlwaysVisible;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        final SettingOverlayInfo that = (SettingOverlayInfo)obj;
        return Objects.equals(this.activeSupplier, that.activeSupplier) && Objects.equals(this.component, that.component) && this.infoAlwaysVisible == that.infoAlwaysVisible;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.activeSupplier, this.component, this.infoAlwaysVisible);
    }
    
    @Override
    public String toString() {
        return "SettingOverlayInfo[activeSupplier=" + String.valueOf(this.activeSupplier) + ", component=" + String.valueOf(this.component) + ", infoAlwaysVisible=" + this.infoAlwaysVisible;
    }
}
