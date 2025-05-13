// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings;

import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;

public class SwitchableInfo
{
    private final String switchId;
    private final boolean invert;
    private final String requiredValue;
    private SettingAccessor switchAccessor;
    private SwitchableHandler handler;
    
    public SwitchableInfo(final SettingRequires requires) {
        this(requires.value(), requires.invert(), requires.required());
    }
    
    public SwitchableInfo(final String switchId, final boolean invert, final String requiredValue) {
        this.switchId = switchId;
        this.invert = invert;
        this.requiredValue = requiredValue;
    }
    
    public String getSwitchId() {
        return this.switchId;
    }
    
    public boolean isInvert() {
        return this.invert;
    }
    
    public String getRequiredValue() {
        return this.requiredValue;
    }
    
    public SwitchableHandler getHandler() {
        return this.handler;
    }
    
    public SettingAccessor getSwitchAccessor() {
        return this.switchAccessor;
    }
    
    public void setHandler(final SwitchableHandler handler) {
        this.handler = handler;
    }
    
    public void setSwitchAccessor(final SettingAccessor switchAccessor) {
        this.switchAccessor = switchAccessor;
    }
}
