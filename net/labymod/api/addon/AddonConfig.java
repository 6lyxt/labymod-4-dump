// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.addon;

import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.loader.Config;

@SettingRequires("enabled")
public abstract class AddonConfig extends Config
{
    public abstract ConfigProperty<Boolean> enabled();
}
