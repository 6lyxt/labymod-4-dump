// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings;

import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import java.util.ArrayList;
import net.labymod.api.util.io.Filter;
import net.labymod.api.configuration.settings.type.SettingElement;
import java.util.Collection;
import java.util.Iterator;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.KeyValue;
import java.util.Objects;
import net.labymod.api.util.CharSequences;
import java.util.Optional;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.config.SettingInitializeEvent;
import net.labymod.api.client.gui.screen.activity.activities.labymod.child.SettingContentActivity;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.service.Registry;
import net.labymod.api.service.Identifiable;

public interface Setting extends Identifiable, Registry<Setting>
{
    public static final String CONFIG_CHANGE_IDENTIFIER = "LabyModSetting";
    
    Icon getIcon();
    
    Component displayName();
    
    @Nullable
    Component getDescription();
    
    default boolean isEnabled() {
        final SettingHandler handler = this.handler();
        return handler == null || handler.isEnabled(this);
    }
    
    default boolean hasControlButton() {
        return false;
    }
    
    boolean hasAdvancedButton();
    
    @Deprecated
    default List<Setting> getSettings() {
        return this.values();
    }
    
    Setting parent();
    
    String getPath();
    
    String getTranslationKey();
    
    default String getTranslationId() {
        return this.getId();
    }
    
    String[] getSearchTags();
    
    @Nullable
    String getRequiredPermission();
    
    boolean canForceEnable();
    
    boolean isInitialized();
    
    default SettingContentActivity createActivity() {
        return new SettingContentActivity(this, false);
    }
    
    default SettingContentActivity createActivityLazy() {
        return new SettingContentActivity(this, true);
    }
    
    default void initialize() {
        this.forEach(Setting::initialize);
        Laby.fireEvent(new SettingInitializeEvent(this));
        final SettingHandler handler = this.handler();
        if (handler != null) {
            handler.initialized(this);
        }
    }
    
    default Optional<Setting> findSetting(final CharSequence path) {
        return this.findSetting(CharSequences.split(path, "\\."));
    }
    
    default Optional<Setting> findSetting(final CharSequence[] nodes) {
        final String node = CharSequences.toString(nodes[0]);
        if (nodes.length == 1 && Objects.equals(this.getId(), node)) {
            return Optional.of(this);
        }
        for (final KeyValue<Setting> element : this.getElements()) {
            final Setting setting = element.getValue();
            if (Objects.equals(setting.getId(), node)) {
                if (nodes.length == 1) {
                    return Optional.of(setting);
                }
                final CharSequence[] newNode = new CharSequence[nodes.length - 1];
                CollectionHelper.copyOfRange(nodes, newNode, 1, nodes.length);
                return setting.findSetting(newNode);
            }
        }
        return Optional.empty();
    }
    
    @Deprecated
    default Setting findSetting(final String path) {
        return this.findSetting((CharSequence)path).orElse(null);
    }
    
    @Deprecated
    default Setting findSetting(final String[] nodes) {
        final Collection<CharSequence> collection = (Collection<CharSequence>)CollectionHelper.map(nodes, node -> node);
        return this.findSetting(collection.toArray(new CharSequence[0])).orElse(null);
    }
    
    default boolean isElement() {
        return this instanceof SettingElement;
    }
    
    default SettingElement asElement() {
        return (SettingElement)this;
    }
    
    default boolean hasParent() {
        return this.parent() != null;
    }
    
    default boolean isHoldable() {
        return true;
    }
    
    default List<Setting> collect(final Filter<Setting> filter) {
        final List<Setting> settings = new ArrayList<Setting>();
        for (final KeyValue<Setting> element : this.getElements()) {
            final Setting setting = element.getValue();
            if (filter.matches(setting)) {
                settings.add(setting);
            }
            settings.addAll(setting.collect(filter));
        }
        return settings;
    }
    
    boolean isExperimental();
    
    void setExperimental(final boolean p0);
    
    @Nullable
    default SettingHandler handler() {
        return null;
    }
    
    default boolean isResettable() {
        if (!this.isElement()) {
            return false;
        }
        final SettingElement settingElement = this.asElement();
        if (settingElement.getResetListener() != null) {
            return true;
        }
        final SettingAccessor accessor = settingElement.getAccessor();
        return accessor != null && !accessor.property().isDefaultValue();
    }
    
    default void reset() {
        final SettingHandler handler = this.handler();
        if (handler != null) {
            handler.reset(this);
        }
        for (final KeyValue<Setting> subElement : this.getElements()) {
            final Setting subSetting = subElement.getValue();
            if (subSetting.isElement()) {
                subSetting.reset();
            }
        }
        if (!this.isElement()) {
            return;
        }
        final SettingElement settingElement = this.asElement();
        final Runnable resetListener = settingElement.getResetListener();
        if (resetListener != null) {
            resetListener.run();
            return;
        }
        final SettingAccessor accessor = settingElement.getAccessor();
        if (accessor != null) {
            accessor.property().reset();
        }
    }
}
