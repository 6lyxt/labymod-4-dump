// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon;

import net.labymod.api.models.addon.info.dependency.AddonDependency;
import net.labymod.api.property.Property;
import net.labymod.api.event.addon.lifecycle.GlobalAddonPostEnableEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.addon.lifecycle.GlobalAddonEnableEvent;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.Iterator;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.addon.integration.AddonIntegration;
import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.api.addon.integration.AddonIntegrationMeta;
import java.util.Collection;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.addon.integration.AddonIntegrationService;

@Singleton
@Implements(AddonIntegrationService.class)
public class DefaultAddonIntegrationService implements AddonIntegrationService
{
    private static final Object[] EMPTY_CONSTRUCTION_ARGUMENTS;
    private final Collection<AddonIntegrationMeta> integrations;
    
    public DefaultAddonIntegrationService() {
        this.integrations = new ArrayList<AddonIntegrationMeta>();
        Laby.labyAPI().eventBus().registerListener(this);
    }
    
    @Override
    public void registerIntegration(final String integratedAddonNamespace, final Class<? extends AddonIntegration> integration) {
        this.registerIntegration(integratedAddonNamespace, integration, DefaultAddonIntegrationService.EMPTY_CONSTRUCTION_ARGUMENTS);
    }
    
    @Override
    public void registerIntegration(final String integratedAddonNamespace, final Class<? extends AddonIntegration> integration, final Object... constructionArguments) {
        final LoadedAddon addon = Laby.labyAPI().addonService().getLastCallerAddon();
        if (addon == null) {
            throw new IllegalStateException("This method must be invoked from an addon!");
        }
        if (CollectionHelper.noneMatch(addon.info().getCompatibleAddonDependencies(Laby.labyAPI().labyModLoader().version()), dep -> dep.getNamespace().equals(integratedAddonNamespace))) {
            throw new IllegalArgumentException("Addon " + addon.info().getNamespace() + " must have an addon dependency on " + integratedAddonNamespace + " (dependency can be marked as optional)");
        }
        final AddonIntegrationMeta meta = new AddonIntegrationMeta(integration, addon, integratedAddonNamespace, constructionArguments);
        this.integrations.add(meta);
        Laby.labyAPI().addonService().getAddon(integratedAddonNamespace).ifPresent(integrated -> {
            this.loadIntegration(integrated, meta);
            this.postLoadIntegration(meta);
        });
    }
    
    private void loadIntegrations(final LoadedAddon integratedAddon) {
        for (final AddonIntegrationMeta meta : this.integrations) {
            if (meta.getIntegratedAddonNamespace().equals(integratedAddon.info().getNamespace())) {
                this.loadIntegration(integratedAddon, meta);
            }
        }
    }
    
    private void loadIntegration(final LoadedAddon integratedAddon, final AddonIntegrationMeta meta) {
        final AddonIntegration integration = meta.integration();
        meta.setIntegratedAddon(integratedAddon);
        integration.load();
    }
    
    private void postLoadIntegrations(final InstalledAddonInfo integratedAddon) {
        for (final AddonIntegrationMeta meta : this.integrations) {
            if (meta.getIntegratedAddonNamespace().equals(integratedAddon.getNamespace())) {
                this.postLoadIntegration(meta);
            }
        }
    }
    
    private void postLoadIntegration(final AddonIntegrationMeta meta) {
        final AddonIntegration integration = meta.integration();
        final AddonConfig integratedConfig = DefaultAddonService.getInstance().getMainConfiguration(meta.getIntegratedAddonNamespace());
        if (integratedConfig != null && integratedConfig.enabled() != null) {
            integratedConfig.enabled().addChangeListener((type, oldValue, newValue) -> this.invokeEnableUpdate(integration, newValue != null && newValue));
            this.invokeEnableUpdate(integration, integratedConfig.enabled().getOrDefault(true));
        }
    }
    
    private void invokeEnableUpdate(final AddonIntegration integration, final boolean enabled) {
        if (enabled) {
            integration.onIntegratedAddonEnable();
        }
        else {
            integration.onIntegratedAddonDisable();
        }
    }
    
    @Override
    public Collection<AddonIntegrationMeta> getByIntegrating(final String addonNamespace) {
        return CollectionHelper.filter(this.integrations, meta -> meta.addon().info().getNamespace().equals(addonNamespace));
    }
    
    @Override
    public Collection<AddonIntegrationMeta> getByIntegrated(final String integratedAddonNamespace) {
        return CollectionHelper.filter(this.integrations, meta -> meta.getIntegratedAddonNamespace().equals(integratedAddonNamespace));
    }
    
    @Subscribe
    public void addonEnabled(final GlobalAddonEnableEvent event) {
        this.loadIntegrations(event.addon());
    }
    
    @Subscribe
    public void addonPostEnabled(final GlobalAddonPostEnableEvent event) {
        this.postLoadIntegrations(event.addonInfo());
    }
    
    static {
        EMPTY_CONSTRUCTION_ARGUMENTS = new Object[0];
    }
}
