// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.type;

import org.jetbrains.annotations.Nullable;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.service.DefaultRegistry;

public class SettingGroup extends DefaultRegistry<Setting> implements Setting
{
    private final String[] emptySearchTags;
    private Icon icon;
    private Component displayName;
    private final Component description;
    private boolean experimental;
    private boolean filtered;
    
    public SettingGroup() {
        this.description = Component.empty();
        this.emptySearchTags = new String[0];
    }
    
    public void addSetting(final Setting setting) {
        this.register(setting);
    }
    
    public void addSettings(final List<Setting> settings) {
        this.register(settings);
    }
    
    public SettingGroup of(final List<Setting> settings) {
        this.register(settings);
        return this;
    }
    
    public SettingGroup filtered(final boolean filtered) {
        this.filtered = filtered;
        return this;
    }
    
    @Override
    public Icon getIcon() {
        return this.icon;
    }
    
    @Override
    public Component displayName() {
        return this.displayName;
    }
    
    @Override
    public Component getDescription() {
        return this.description;
    }
    
    @Override
    public boolean hasAdvancedButton() {
        return false;
    }
    
    @Override
    public Setting parent() {
        return null;
    }
    
    @Override
    public String getPath() {
        return this.getId();
    }
    
    @Override
    public String getTranslationKey() {
        return this.getId();
    }
    
    @Override
    public String[] getSearchTags() {
        return this.emptySearchTags;
    }
    
    @Nullable
    @Override
    public String getRequiredPermission() {
        return null;
    }
    
    @Override
    public boolean canForceEnable() {
        return false;
    }
    
    @Override
    public boolean isInitialized() {
        return true;
    }
    
    @Override
    public boolean isExperimental() {
        return this.experimental;
    }
    
    @Override
    public void setExperimental(final boolean experimental) {
        this.experimental = experimental;
    }
    
    public boolean isFiltered() {
        return this.filtered;
    }
    
    @Override
    public String getId() {
        return "group";
    }
    
    public static SettingGroup named(final Component component) {
        final SettingGroup group = new SettingGroup();
        group.displayName = component;
        return group;
    }
    
    public static SettingGroup named(final Component component, final Icon icon) {
        final SettingGroup group = new SettingGroup();
        group.displayName = component;
        group.icon = icon;
        return group;
    }
    
    public static SettingGroup empty() {
        return new SettingGroup();
    }
}
