// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.type;

import net.labymod.api.client.gui.icon.Icon;

public abstract class AbstractSettingRegistry extends AbstractSetting
{
    private boolean holdable;
    
    protected AbstractSettingRegistry(final String id, final Icon icon) {
        super(id, icon);
        this.holdable = true;
    }
    
    public AbstractSettingRegistry holdable(final boolean holdable) {
        this.holdable = holdable;
        return this;
    }
    
    @Override
    public boolean isHoldable() {
        return this.holdable;
    }
}
