// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.type;

import net.labymod.api.addon.LoadedAddon;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.addon.AddonService;
import net.labymod.api.LabyAPI;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.Laby;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;

public class RootSettingRegistry extends AbstractSettingRegistry
{
    private final String namespace;
    private String translationId;
    private Component forcedDisplayName;
    
    protected RootSettingRegistry(final String namespace, final String id) {
        super(id, null);
        this.namespace = namespace;
        this.translationId = id;
    }
    
    public RootSettingRegistry translationId(final String translationId) {
        this.translationId = translationId;
        return this;
    }
    
    public boolean isAddon() {
        return !this.namespace.equals("labymod");
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    @Override
    public String getTranslationId() {
        return this.translationId;
    }
    
    @Override
    public Component displayName() {
        if (this.forcedDisplayName != null) {
            return this.forcedDisplayName;
        }
        return super.displayName();
    }
    
    public static <T extends AddonConfig> RootSettingRegistry addon(final Object instance, final T config) {
        final LabyAPI labyAPI = Laby.labyAPI();
        final AddonService addonService = labyAPI.addonService();
        final String namespace = labyAPI.getNamespace(instance);
        final RootSettingRegistry settingRegistry = new RootSettingRegistry(namespace, namespace).translationId("settings");
        settingRegistry.addSettings(config);
        addonService.getAddon(namespace).ifPresent(addon -> settingRegistry.setForcedDisplayName(addon.info().getDisplayName()));
        return settingRegistry;
    }
    
    public void setForcedDisplayName(final String forcedDisplayName) {
        if (forcedDisplayName == null) {
            this.forcedDisplayName = null;
            return;
        }
        this.forcedDisplayName = Component.text(forcedDisplayName);
    }
    
    @ApiStatus.Internal
    public static RootSettingRegistry labymod(final String id) {
        return new RootSettingRegistry("labymod", id);
    }
    
    @ApiStatus.Internal
    public static RootSettingRegistry custom(final String namespace, final String id) {
        return new RootSettingRegistry(namespace, id);
    }
    
    @Override
    public String getTranslationKey() {
        return this.namespace + "." + this.getTranslationId();
    }
}
