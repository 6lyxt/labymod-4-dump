// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.filter;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.Component;
import net.labymod.api.property.Property;
import net.labymod.api.util.function.ChangeListener;
import net.labymod.api.configuration.loader.property.ConvertListener;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TagInputWidget;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import java.util.UUID;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.type.list.ListSettingConfig;
import net.labymod.api.configuration.loader.Config;

public class ChatFilter extends Config implements ListSettingConfig
{
    private final ConfigProperty<UUID> id;
    private final ConfigProperty<UUID> tabId;
    @TextFieldWidget.TextFieldSetting
    @CustomTranslation("labymod.chattab.filters.filter.name")
    private final ConfigProperty<String> name;
    @CustomTranslation("labymod.chattab.filters.filter.includeWords")
    @TagInputWidget.TagInputSetting
    private final ConfigProperty<TagInputWidget.TagCollection> includeTags;
    @CustomTranslation("labymod.chattab.filters.filter.excludeWords")
    @TagInputWidget.TagInputSetting
    private final ConfigProperty<TagInputWidget.TagCollection> excludeTags;
    @Deprecated
    private final ConfigProperty<String> includeWords;
    @Deprecated
    private final ConfigProperty<String> excludeWords;
    @SwitchWidget.SwitchSetting
    @CustomTranslation("labymod.chattab.filters.filter.shouldChangeBackground")
    private final ConfigProperty<Boolean> shouldChangeBackground;
    @SettingRequires("shouldChangeBackground")
    @ColorPickerWidget.ColorPickerSetting
    @CustomTranslation("labymod.chattab.filters.filter.backgroundColor")
    private final ConfigProperty<Integer> backgroundColor;
    @SwitchWidget.SwitchSetting
    @CustomTranslation("labymod.chattab.filters.filter.shouldPlaySound")
    private final ConfigProperty<Boolean> shouldPlaySound;
    @SwitchWidget.SwitchSetting
    @CustomTranslation("labymod.chattab.filters.filter.shouldHideMessage")
    private final ConfigProperty<Boolean> shouldHideMessage;
    @SwitchWidget.SwitchSetting
    @CustomTranslation("labymod.chattab.filters.filter.shouldFilterTooltip")
    private final ConfigProperty<Boolean> shouldFilterTooltip;
    @SwitchWidget.SwitchSetting
    @CustomTranslation("labymod.chattab.filters.filter.caseSensitive")
    private final ConfigProperty<Boolean> caseSensitive;
    private final ConfigProperty<Boolean> advanced;
    private final ConfigProperty<String> includeRegEx;
    private final ConfigProperty<String> excludeRegEx;
    
    public ChatFilter() {
        this(UUID.randomUUID(), UUID.randomUUID());
    }
    
    public ChatFilter(final UUID id, final UUID tabId) {
        this.name = new ConfigProperty<String>("Filter");
        this.includeTags = new ConfigProperty<TagInputWidget.TagCollection>(new TagInputWidget.TagCollection());
        this.excludeTags = new ConfigProperty<TagInputWidget.TagCollection>(new TagInputWidget.TagCollection());
        this.includeWords = new ConfigProperty<Object>("").addChangeListener((ChangeListener<Property<Object>, Object>)ConvertListener.of(this.includeTags, value -> {
            this.convertOldFormat(value, this.includeTags);
            return true;
        }));
        this.excludeWords = new ConfigProperty<Object>("").addChangeListener((ChangeListener<Property<Object>, Object>)ConvertListener.of(this.excludeTags, value -> {
            this.convertOldFormat(value, this.excludeTags);
            return true;
        }));
        this.shouldChangeBackground = new ConfigProperty<Boolean>(false);
        this.backgroundColor = new ConfigProperty<Integer>(0);
        this.shouldPlaySound = new ConfigProperty<Boolean>(false);
        this.shouldHideMessage = new ConfigProperty<Boolean>(false);
        this.shouldFilterTooltip = new ConfigProperty<Boolean>(false);
        this.caseSensitive = new ConfigProperty<Boolean>(false);
        this.advanced = new ConfigProperty<Boolean>(false);
        this.includeRegEx = new ConfigProperty<String>("");
        this.excludeRegEx = new ConfigProperty<String>("");
        this.id = new ConfigProperty<UUID>(id);
        this.tabId = new ConfigProperty<UUID>(tabId);
    }
    
    public UUID id() {
        return this.id.get();
    }
    
    public UUID tabId() {
        return this.tabId.get();
    }
    
    public TagInputWidget.TagCollection getIncludedTags() {
        return this.includeTags.get();
    }
    
    public TagInputWidget.TagCollection getExcludedTags() {
        return this.excludeTags.get();
    }
    
    public ConfigProperty<String> name() {
        return this.name;
    }
    
    public ConfigProperty<Boolean> shouldChangeBackground() {
        return this.shouldChangeBackground;
    }
    
    public ConfigProperty<Integer> backgroundColor() {
        return this.backgroundColor;
    }
    
    public ConfigProperty<Boolean> shouldPlaySound() {
        return this.shouldPlaySound;
    }
    
    public ConfigProperty<Boolean> shouldHideMessage() {
        return this.shouldHideMessage;
    }
    
    public ConfigProperty<Boolean> shouldFilterTooltip() {
        return this.shouldFilterTooltip;
    }
    
    public ConfigProperty<Boolean> caseSensitive() {
        return this.caseSensitive;
    }
    
    public ConfigProperty<Boolean> advanced() {
        return this.advanced;
    }
    
    public ConfigProperty<String> includeRegEx() {
        return this.includeRegEx;
    }
    
    public ConfigProperty<String> excludeRegEx() {
        return this.excludeRegEx;
    }
    
    @NotNull
    @Override
    public Component entryDisplayName() {
        if (this.name.isDefaultValue() || this.name.get().isEmpty()) {
            Component include = null;
            Component exclude = null;
            if (!this.includeWords.isDefaultValue()) {
                include = Component.text(this.includeWords.get(), NamedTextColor.GREEN);
            }
            if (!this.excludeWords.isDefaultValue()) {
                exclude = Component.text(this.excludeWords.get(), NamedTextColor.RED);
            }
            if (include != null && exclude != null) {
                return include.append(Component.text(" | ", NamedTextColor.WHITE)).append(exclude);
            }
            if (include != null) {
                return include;
            }
            if (exclude != null) {
                return exclude;
            }
        }
        return Component.text(this.name.get());
    }
    
    @NotNull
    @Override
    public Component newEntryTitle() {
        return Component.translatable("labymod.chattab.filters.new", new Component[0]);
    }
    
    @Override
    public boolean isInvalid() {
        return this.includeTags.get().isEmpty() && this.excludeTags.get().isEmpty() && this.includeWords.get().isEmpty() && this.excludeWords.get().isEmpty();
    }
    
    private void convertOldFormat(final String value, final ConfigProperty<TagInputWidget.TagCollection> property) {
        if (value.isEmpty()) {
            return;
        }
        final TagInputWidget.TagCollection tags = new TagInputWidget.TagCollection();
        for (final String filter : value.split(";")) {
            if (!filter.trim().isEmpty()) {
                tags.add(filter);
            }
        }
        property.set(tags);
    }
}
