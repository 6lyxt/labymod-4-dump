// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.signobject.template;

import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.Laby;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.client.resources.ResourceLocation;

public class SignObjectTemplate
{
    private final ResourceLocation location;
    private final InstalledAddonInfo addon;
    
    protected SignObjectTemplate(final ResourceLocation location, final InstalledAddonInfo addon) {
        this.location = location;
        this.addon = addon;
    }
    
    public static SignObjectTemplate create(final ResourceLocation location) {
        return create(location, location.getNamespace());
    }
    
    public static SignObjectTemplate create(final ResourceLocation location, final String addonNamespace) {
        return new SignObjectTemplate(location, getAddon(addonNamespace));
    }
    
    public static InstalledAddonInfo getAddon(final String addonNamespace) {
        LoadedAddon addon = Laby.labyAPI().addonService().getLastCallerAddon();
        if (addonNamespace.equals("labymod")) {
            if (addon != null) {
                throw new IllegalArgumentException("Using the LabyMod namespace for a sign object is not allowed for addons!");
            }
            return null;
        }
        else {
            if (addon == null) {
                addon = Laby.labyAPI().addonService().getAddon(addonNamespace).orElse(null);
            }
            if (addon == null) {
                throw new IllegalArgumentException("Cannot find addon from classpath and by namespace '" + addonNamespace + "', use the correct namespace!");
            }
            return addon.info();
        }
    }
    
    public ResourceLocation location() {
        return this.location;
    }
    
    public InstalledAddonInfo getAddon() {
        return this.addon;
    }
    
    public SignObjectMeta<?> parseMeta(final String[] meta) {
        return new SignObjectMeta<Object>(this, meta);
    }
}
