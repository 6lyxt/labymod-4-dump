// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.core.packet.serverbound.game.moderation.AddonStateChangedPacket;
import net.labymod.api.Laby;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.type.SettingElement;
import java.util.Optional;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.SettingOverlayInfo;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation.AddonDisablePacketHandler;
import net.labymod.api.configuration.settings.accessor.impl.ConfigPropertySettingAccessor;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import net.labymod.api.event.labymod.config.SettingInitializeEvent;
import net.labymod.api.event.Subscribe;
import java.util.Iterator;
import java.util.ArrayList;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import java.util.Collection;
import java.util.List;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.addon.AddonConfig;
import java.util.Locale;
import net.labymod.api.models.version.Version;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.serverapi.core.model.moderation.InstalledAddon;
import net.labymod.api.addon.LoadedAddon;
import java.util.HashSet;
import java.util.Set;
import net.labymod.api.util.logging.Logging;

public class ServerAPIAddonService
{
    private static final Logging LOGGER;
    private final DefaultAddonService addonService;
    private final Set<String> disabledAddons;
    private Set<String> requestedAddons;
    private boolean requestedAddonStateChanges;
    
    protected ServerAPIAddonService(final DefaultAddonService addonService) {
        this.requestedAddons = new HashSet<String>();
        this.addonService = addonService;
        this.disabledAddons = new HashSet<String>();
    }
    
    public static InstalledAddon addonToInstalledAddon(final LoadedAddon loadedAddon, final boolean enabled) {
        final InstalledAddonInfo info = loadedAddon.info();
        final Version version = loadedAddon.getVersion();
        return new InstalledAddon(info.getNamespace(), new InstalledAddon.AddonVersion(version.getMajor(), version.getMinor(), version.getPatch()), enabled, !info.isFlintAddon());
    }
    
    public boolean forceDisable(String namespace) {
        if (this.isForceDisabled(namespace)) {
            return false;
        }
        namespace = namespace.toLowerCase(Locale.ENGLISH);
        this.disabledAddons.add(namespace);
        final AddonConfig addonConfig = this.addonService.getMainConfiguration(namespace);
        if (addonConfig != null) {
            final ConfigProperty<Boolean> enabled = addonConfig.enabled();
            enabled.withPseudoValue(false);
            return enabled.getActualValue();
        }
        return false;
    }
    
    public boolean revertForceDisable(String namespace) {
        if (!this.isForceDisabled(namespace)) {
            return false;
        }
        namespace = namespace.toLowerCase(Locale.ENGLISH);
        this.disabledAddons.remove(namespace);
        final AddonConfig addonConfig = this.addonService.getMainConfiguration(namespace);
        if (addonConfig != null) {
            final ConfigProperty<Boolean> enabled = addonConfig.enabled();
            enabled.withoutPseudoValue();
            return enabled.get();
        }
        return false;
    }
    
    public void requestAddons(final List<String> addons) {
        if (this.requestedAddonStateChanges) {
            if (this.requestedAddons.isEmpty()) {
                return;
            }
            if (addons.isEmpty()) {
                this.requestedAddons.clear();
                return;
            }
            if (!this.requestedAddons.containsAll(addons)) {
                this.requestedAddons.addAll(addons);
                return;
            }
        }
        this.requestedAddonStateChanges = true;
        this.requestedAddons = new HashSet<String>(addons);
    }
    
    public boolean isForceDisabled(final String namespace) {
        return this.disabledAddons.contains(namespace);
    }
    
    @Subscribe(126)
    public void onServerLeave(final ServerDisconnectEvent event) {
        this.requestedAddonStateChanges = false;
        this.requestedAddons = new HashSet<String>();
        final List<ConfigProperty<Boolean>> enabledProperties = new ArrayList<ConfigProperty<Boolean>>();
        for (final String namespace : this.disabledAddons) {
            final AddonConfig addonConfig = this.addonService.getMainConfiguration(namespace);
            if (addonConfig != null) {
                enabledProperties.add(addonConfig.enabled());
            }
        }
        this.disabledAddons.clear();
        for (final ConfigProperty<Boolean> enabledProperty : enabledProperties) {
            enabledProperty.withoutPseudoValue();
        }
    }
    
    @Subscribe(126)
    public void onSetting(final SettingInitializeEvent event) {
        final Setting setting = event.setting();
        if (setting.hasParent()) {
            final Setting parent = setting.parent();
            if (parent instanceof final RootSettingRegistry rootSettingRegistry) {
                if (setting.isElement()) {
                    final String namespace = rootSettingRegistry.getNamespace();
                    final Optional<LoadedAddon> optionalAddon = this.addonService.getAddon(namespace);
                    if (optionalAddon.isEmpty()) {
                        return;
                    }
                    final AddonConfig addonConfig = this.addonService.getMainConfiguration(namespace);
                    final ConfigProperty<Boolean> enabledProperty = (addonConfig == null) ? null : addonConfig.enabled();
                    if (enabledProperty == null) {
                        return;
                    }
                    final SettingElement settingElement = setting.asElement();
                    final SettingAccessor accessor2 = settingElement.getAccessor();
                    if (accessor2 instanceof final ConfigPropertySettingAccessor accessor) {
                        if (accessor.property() == enabledProperty) {
                            enabledProperty.addChangeListener(enabled -> this.updateEnabledState(namespace, enabledProperty, enabled));
                            this.updateEnabledState(namespace, enabledProperty, enabledProperty.get());
                            if (this.isForceDisabled(namespace)) {
                                AddonDisablePacketHandler.pushDisableNotification(optionalAddon.get().info().getDisplayName());
                            }
                            settingElement.setOverlayInfo(new SettingOverlayInfo(() -> this.isForceDisabled(namespace), Component.translatable("labymod.ui.settings.serverFeatures.disabledAddon", NamedTextColor.RED, Component.text(optionalAddon.get().info().getDisplayName(), NamedTextColor.GRAY)), false));
                        }
                    }
                }
            }
        }
    }
    
    private void updateEnabledState(final String namespace, final ConfigProperty<Boolean> property, final boolean enabled) {
        if (enabled && this.isForceDisabled(namespace)) {
            property.withPseudoValue(false);
            ServerAPIAddonService.LOGGER.warn("Addon {} was disabled by the server.", namespace);
            return;
        }
        if (this.requestedAddon(namespace)) {
            this.addonService.getAddon(namespace).ifPresent(addon -> Laby.references().labyModProtocolService().sendLabyModPacket((Packet)new AddonStateChangedPacket(addonToInstalledAddon(addon, enabled))));
        }
    }
    
    private boolean requestedAddon(final String namespace) {
        return this.requestedAddonStateChanges && (this.requestedAddons.contains(namespace) || this.requestedAddons.isEmpty());
    }
    
    static {
        LOGGER = Logging.create(ServerAPIAddonService.class);
    }
}
