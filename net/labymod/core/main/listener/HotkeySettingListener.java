// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.listener;

import net.labymod.api.service.Registry;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import java.util.Collections;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.api.client.component.Component;
import java.util.List;
import net.labymod.api.configuration.settings.type.SettingHeader;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.KeyValue;
import java.util.Iterator;
import java.util.Collection;
import net.labymod.api.event.Subscribe;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.event.labymod.config.SettingInitializeEvent;
import java.util.HashMap;
import net.labymod.api.util.collection.map.HashMultimap;
import java.util.Map;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.util.collection.map.Multimap;
import net.labymod.api.util.logging.Logging;

public class HotkeySettingListener
{
    private static final Logging LOGGER;
    private static final String ROOT_HOTKEYS_KEY = "settings.hotkeys";
    private final Multimap<String, Setting> hotkeysSettings;
    private final Map<String, DynamicSettingHeader> headers;
    private Setting rootHotkeysSettings;
    
    public HotkeySettingListener() {
        this.hotkeysSettings = new HashMultimap<String, Setting>();
        this.headers = new HashMap<String, DynamicSettingHeader>();
    }
    
    @Subscribe
    public void onSettingInitialize(final SettingInitializeEvent event) {
        final Setting setting = event.setting();
        if (!(setting instanceof SettingElement)) {
            return;
        }
        final SettingElement settingElement = (SettingElement)setting;
        final RootSettingRegistry registry = this.findSettingRegistry(setting);
        if (registry == null) {
            HotkeySettingListener.LOGGER.warn("Could not find root setting registry for setting {}", setting.getPath());
            return;
        }
        final SettingAccessor accessor = settingElement.getAccessor();
        if (accessor != null) {
            final boolean hasKeybindWidget = this.hasKeybindWidget(settingElement.getWidgets());
            if (hasKeybindWidget) {
                this.hotkeysSettings.put(registry.getNamespace(), setting);
            }
        }
        final String path = setting.getPath();
        if ("labymod".equals(registry.getNamespace()) && "settings.hotkeys".equals(path)) {
            this.rootHotkeysSettings = settingElement;
        }
        if (this.rootHotkeysSettings != null) {
            this.registerLabyModSettings();
            this.hotkeysSettings.forEach(this::registerSetting);
        }
    }
    
    private void registerLabyModSettings() {
        final Collection<Setting> settings = this.hotkeysSettings.get("labymod");
        if (settings.isEmpty()) {
            return;
        }
        for (final Setting setting : settings) {
            final Setting parent = setting.parent();
            if (parent == this.rootHotkeysSettings) {
                continue;
            }
            this.rootHotkeysSettings.register(setting);
        }
        this.hotkeysSettings.remove("labymod");
    }
    
    private void registerSetting(final Map.Entry<String, Setting> entry) {
        final String namespace = entry.getKey();
        if ("labymod".equals(namespace)) {
            return;
        }
        final Setting setting = entry.getValue();
        final KeyValue<Setting> node = this.rootHotkeysSettings.getElementById(setting.getId());
        if (node == null) {
            DynamicSettingHeader dynamicSettingHeader = this.headers.get(namespace);
            if (dynamicSettingHeader == null) {
                dynamicSettingHeader = new DynamicSettingHeader(namespace);
                ((Registry<DynamicSettingHeader>)this.rootHotkeysSettings).register(dynamicSettingHeader);
                this.headers.put(namespace, dynamicSettingHeader);
            }
            this.rootHotkeysSettings.register(setting);
        }
    }
    
    private boolean hasKeybindWidget(final Widget[] widgets) {
        if (widgets == null) {
            return false;
        }
        for (final Widget widget : widgets) {
            if (widget instanceof KeybindWidget) {
                return true;
            }
        }
        return false;
    }
    
    @Nullable
    private RootSettingRegistry findSettingRegistry(final Setting setting) {
        Setting parent = setting;
        while (!(parent instanceof RootSettingRegistry)) {
            parent = parent.parent();
            if (parent == null) {
                break;
            }
        }
        RootSettingRegistry rootSettingRegistry2;
        if (parent instanceof final RootSettingRegistry rootSettingRegistry3) {
            final RootSettingRegistry rootSettingRegistry = rootSettingRegistry2 = rootSettingRegistry3;
        }
        else {
            rootSettingRegistry2 = null;
        }
        return rootSettingRegistry2;
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
    
    static class DynamicSettingHeader extends SettingHeader
    {
        private static final String UNKNOWN_NAME = "Unknown";
        private final String namespace;
        private final List<Component> rows;
        private final boolean unknown;
        
        public DynamicSettingHeader(final String namespace) {
            super(namespace, false, namespace, namespace);
            final DefaultAddonService addonService = DefaultAddonService.getInstance();
            final String displayName = addonService.getAddon(namespace).map(loadedAddon -> loadedAddon.info().getDisplayName()).orElse("Unknown");
            this.namespace = namespace;
            this.unknown = "Unknown".equals(displayName);
            this.rows = (List<Component>)Collections.singletonList(Component.text(displayName));
        }
        
        @Override
        public List<Component> getRows() {
            return this.rows;
        }
        
        @Override
        public Pressable pressable() {
            return () -> {
                final Setting setting = Laby.labyAPI().coreSettingRegistry().getById(this.namespace);
                if (setting != null) {
                    Laby.labyAPI().showSetting(setting);
                }
            };
        }
        
        public boolean isUnknown() {
            return this.unknown;
        }
    }
}
