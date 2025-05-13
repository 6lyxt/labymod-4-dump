// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixinplugin;

import net.labymod.core.addon.DefaultAddonService;
import net.labymod.api.mixin.ConfigPlugin;
import net.labymod.api.volt.plugin.VoltMixinConfigPlugin;

@ConfigPlugin
public class LabyModMixinConfigPlugin extends VoltMixinConfigPlugin
{
    @Override
    protected boolean isValid(final String name) {
        final DefaultAddonService addonService = DefaultAddonService.getInstance();
        return addonService.shouldApplyDynamicMixin(name);
    }
}
