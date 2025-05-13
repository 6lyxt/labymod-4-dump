// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.type.list;

import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.util.KeyValue;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.client.component.Component;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import net.labymod.api.configuration.settings.type.SettingPermissionHolder;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.SwitchableInfo;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.configuration.loader.Config;
import java.util.List;
import net.labymod.api.configuration.settings.type.SettingElement;

public class ListSetting extends SettingElement
{
    private final Class<?> type;
    private final List<? extends Config> list;
    
    public ListSetting(final String id, final Icon icon, final String customTranslation, final String[] searchTags, final String requiredPermission, final boolean canForceEnable, final SwitchableInfo switchableInfo, final byte orderValue, final SettingAccessor accessor) {
        this(id, icon, customTranslation, searchTags, new SettingPermissionHolder(requiredPermission, canForceEnable), switchableInfo, orderValue, accessor);
    }
    
    public ListSetting(final String id, final Icon icon, final String customTranslation, final String[] searchTags, final SettingPermissionHolder permissionHolder, final SwitchableInfo switchableInfo, final byte orderValue, final SettingAccessor accessor) {
        super(id, icon, customTranslation, searchTags, permissionHolder, switchableInfo, orderValue);
        final Type parameter = accessor.getGenericType();
        if (!(parameter instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Cannot determine type for config field " + id);
        }
        this.type = (Class)((ParameterizedType)parameter).getActualTypeArguments()[0];
        this.list = accessor.get();
    }
    
    public static Component defaultEntryName() {
        return Component.translatable("labymod.ui.settings.list.entry", new Component[0]);
    }
    
    public static Component defaultNewEntryName() {
        return Component.translatable("labymod.ui.settings.list.newEntry", new Component[0]);
    }
    
    public ListSettingEntry createNew() {
        final ListSettingEntry entry = new ListSettingEntry(this, Component.empty(), this.list.size());
        try {
            final Config config = (Config)this.type.getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            this.list.add((Config)config);
            if (config instanceof final ListSettingConfig listSettingConfig) {
                entry.displayName().append(listSettingConfig.newEntryTitle());
            }
            else {
                entry.displayName().append(defaultNewEntryName());
            }
            entry.addSettings(config);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        return entry;
    }
    
    public void remove(final ListSettingEntry entry) {
        this.list.remove(entry.listIndex());
    }
    
    @Override
    public List<KeyValue<Setting>> getElements() {
        final List<KeyValue<Setting>> list = new ArrayList<KeyValue<Setting>>();
        for (int i = 0; i < this.list.size(); ++i) {
            final Object object = this.list.get(i);
            if (object instanceof final Config config) {
                if (this.isInvalid(config)) {
                    this.list.remove(i--);
                }
                else {
                    try {
                        final ListSettingEntry entry = new ListSettingEntry(this, this.displayName(config), i);
                        entry.addSettings(config);
                        list.add(new KeyValue<Setting>(entry.getId(), entry));
                    }
                    catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return list;
    }
    
    @Override
    public boolean hasAdvancedButton() {
        return true;
    }
    
    private boolean isInvalid(final Config config) {
        return config instanceof ListSettingConfig && ((ListSettingConfig)config).isInvalid();
    }
    
    private Component displayName(final Config config) {
        if (!(config instanceof ListSettingConfig)) {
            return defaultEntryName();
        }
        final ListSettingConfig listSettingConfig = (ListSettingConfig)config;
        final Component component = listSettingConfig.entryDisplayName();
        if (component != null) {
            return component;
        }
        if (Laby.labyAPI().labyModLoader().isAddonDevelopmentEnvironment()) {
            throw new NullPointerException("ListSettingConfig#entryDisplayName() must not return null");
        }
        return defaultEntryName();
    }
}
