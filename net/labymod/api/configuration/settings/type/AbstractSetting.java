// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.type;

import net.labymod.api.service.Registry;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import net.labymod.api.configuration.loader.ConfigAccessor;
import java.util.List;
import net.labymod.api.util.I18n;
import java.util.Locale;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.service.DefaultRegistry;

public abstract class AbstractSetting extends DefaultRegistry<Setting> implements Setting
{
    private final String id;
    private final Icon icon;
    private final String[] emptySearchTags;
    protected Setting parent;
    private boolean initialized;
    private boolean experimental;
    
    protected AbstractSetting(final String id, final Icon icon) {
        if (id.contains(".")) {
            throw new RuntimeException("The id of a setting cannot contain a '.' because it is used as a separator for the tree structure: " + id);
        }
        this.id = id;
        this.icon = icon;
        this.emptySearchTags = new String[0];
    }
    
    @Override
    public void initialize() {
        if (this.initialized) {
            return;
        }
        super.initialize();
        this.initialized = true;
    }
    
    @Override
    public String getId() {
        return this.id;
    }
    
    @Override
    public Icon getIcon() {
        return this.icon;
    }
    
    @Override
    public Component displayName() {
        Component displayName = Component.empty();
        displayName = displayName.append(Component.translatable(String.format(Locale.ROOT, "%s.%s", this.getTranslationKey(), "name"), new Component[0]));
        if (this.isExperimental()) {
            displayName = displayName.append(Component.space()).append(Component.text(String.format(Locale.ROOT, "(%s)", I18n.translate("labymod.misc.experimental", new Object[0]))));
        }
        return displayName;
    }
    
    @Override
    public Component getDescription() {
        final String descriptionPath = String.format(Locale.ROOT, "%s.%s", this.getTranslationKey(), "description");
        final String translatedDescription = I18n.translate(descriptionPath, new Object[0]);
        return translatedDescription.equalsIgnoreCase(descriptionPath) ? null : Component.text(translatedDescription);
    }
    
    @Override
    public boolean hasAdvancedButton() {
        return !this.getElements().isEmpty();
    }
    
    @Override
    public List<Setting> getSettings() {
        return this.values();
    }
    
    public void addSetting(final AbstractSetting setting) {
        setting.setParent(this);
        ((Registry<AbstractSetting>)this).register(setting);
        if (this.initialized && setting instanceof AbstractSettingRegistry) {
            setting.initialize();
        }
    }
    
    public void addSettings(final ConfigAccessor config) {
        this.addSettings(config.toSettings(this));
    }
    
    public void addSettings(final List<Setting> settings) {
        for (final Setting setting : settings) {
            this.addSetting((AbstractSetting)setting);
        }
    }
    
    public void setParent(final Setting parent) {
        this.parent = parent;
    }
    
    @Override
    public Setting parent() {
        return this.parent;
    }
    
    @Override
    public String getPath() {
        final StringBuilder path = new StringBuilder(this.id);
        for (Setting parent = this.parent; parent != null; parent = parent.parent()) {
            final String parentId = parent.getId();
            path.insert(0, parentId).insert(parentId.length(), ".");
        }
        if (path.toString().startsWith("labymod")) {
            throw new RuntimeException("Namespaces are not allowed in the settings path: " + String.valueOf(path));
        }
        return path.toString();
    }
    
    @Override
    public String getTranslationKey() {
        final StringBuilder path = new StringBuilder();
        final Setting parent = this.parent;
        if (parent != null) {
            path.append(parent.getTranslationKey()).append(".");
        }
        path.append(this.getTranslationId());
        return path.toString();
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
        return this.initialized;
    }
    
    @Override
    public boolean isExperimental() {
        return this.experimental;
    }
    
    @Override
    public void setExperimental(final boolean experimental) {
        this.experimental = experimental;
    }
}
