// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.property.Property;
import java.lang.annotation.Annotation;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.icon.Icon;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;
import net.labymod.api.client.gui.screen.ScreenContext;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.I18n;
import net.labymod.api.util.TextFormat;
import net.labymod.api.client.component.Component;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.widget.action.Selectable;
import java.util.List;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;

@AutoWidget
@SettingWidget
@Link("advanced-selection.lss")
public class AdvancedSelectionWidget<T> extends HorizontalListWidget
{
    private final List<SelectionEntry<T>> entries;
    private Selectable<T> selectable;
    private SelectionEntry<T> selected;
    private String translationKeyPrefix;
    private StringParser stringParser;
    
    private AdvancedSelectionWidget() {
        this.entries = new ArrayList<SelectionEntry<T>>();
        this.stringParser = (object -> {
            if (this.translationKeyPrefix == null) {
                return Component.text(object);
            }
            else {
                final String key = this.translationKeyPrefix + TextFormat.SNAKE_CASE.toCamelCase(object, true);
                return (Component)(I18n.has(key) ? Component.translatable(key, new Component[0]) : Component.text(object));
            }
        });
    }
    
    public static <T> AdvancedSelectionWidget<T> create(final Selectable<T> selectable) {
        return new AdvancedSelectionWidget<T>().selectable(selectable);
    }
    
    public static <T> AdvancedSelectionWidget<T> create(final T defaultValue, final Selectable<T> selectable) {
        final AdvancedSelectionWidget<T> widget = create(selectable);
        widget.setSelected(defaultValue);
        return widget;
    }
    
    private static Widget createTitleWidget(final StringParser stringParser, final SelectionEntry<?> entry) {
        if (entry == null || entry.getValue() == null) {
            return ComponentWidget.empty();
        }
        final Object value = entry.getValue();
        if (value instanceof final Widget widget) {
            return widget;
        }
        Component component;
        if (stringParser != null && (value instanceof String || value instanceof Enum)) {
            component = stringParser.parse(value.toString());
        }
        else if (value instanceof final Component component2) {
            component = component2;
        }
        else {
            component = Component.text(value.toString());
        }
        return ComponentWidget.component(component);
    }
    
    private static Widget createEntryWidget(final StringParser stringParser, final SelectionEntry<?> entry) {
        final SelectionEntryWidget<?> entryWidget = new SelectionEntryWidget<Object>(entry).addId("entry");
        if (entry.thumbnailIcon() != null) {
            final IconWidget thumbnailWidget = new IconWidget(entry.thumbnailIcon()).addId("thumbnail");
            thumbnailWidget.addChild(new DivWidget().addId("overlay"));
            entryWidget.addChild(thumbnailWidget);
        }
        final Widget titleWidget = createTitleWidget(stringParser, entry).addId("title");
        entryWidget.addChild(titleWidget);
        return entryWidget;
    }
    
    @Override
    public void initialize(final Parent parent) {
        for (final SelectionEntry<T> entry : this.entries) {
            super.addEntry(createEntryWidget(this.stringParser, entry));
        }
        super.initialize(parent);
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        for (final HorizontalListEntry child : this.children) {
            final Widget childWidget = child.childWidget();
            if (childWidget instanceof SelectionEntryWidget) {
                final SelectionEntryWidget<?> entryWidget = (SelectionEntryWidget<?>)childWidget;
                child.setSelected(Objects.equals(entryWidget.selectionEntry(), this.selected));
            }
        }
        super.renderWidget(context);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        for (final HorizontalListEntry child : this.children) {
            if (child.childWidget() instanceof SelectionEntryWidget) {
                final SelectionEntryWidget<?> entryWidget = (SelectionEntryWidget<?>)child.childWidget();
                if (child.isHovered() && child.isInteractable()) {
                    this.setSelected(entryWidget.selectionEntry().getValue());
                    return true;
                }
                continue;
            }
        }
        return super.mouseClicked(mouse, mouseButton);
    }
    
    public AdvancedSelectionWidget<T> selectable(final Selectable<T> selectable) {
        this.selectable = selectable;
        return this;
    }
    
    public void add(@NotNull final T value, @Nullable final Icon thumbnailIcon) {
        final SelectionEntry<T> entry = new SelectionEntry<T>(value, thumbnailIcon);
        if (this.selected == null) {
            this.selected = entry;
        }
        this.entries.add(entry);
    }
    
    public void add(@NotNull final T value) {
        this.add(value, null);
    }
    
    public void clear() {
        this.entries.clear();
    }
    
    public void setStringParser(final StringParser stringParser) {
        this.stringParser = stringParser;
    }
    
    public List<SelectionEntry<T>> getEntries() {
        return this.entries;
    }
    
    public T getSelected() {
        return (this.selected == null) ? null : this.selected.getValue();
    }
    
    public void setSelected(final T value) {
        if (value == null) {
            return;
        }
        boolean changed = false;
        this.selected = new SelectionEntry<T>(value, null);
        for (final SelectionEntry<T> entry : this.entries) {
            if (value.equals(entry.getValue())) {
                this.selected = entry;
                changed = true;
                break;
            }
        }
        if (!changed) {
            return;
        }
        if (this.selectable != null) {
            this.selectable.select(value);
        }
        this.callActionListeners();
        this.reInitialize();
    }
    
    public AdvancedSelectionWidget<T> translationKeyPrefix(final String translationKey) {
        this.translationKeyPrefix = ((translationKey == null) ? null : (translationKey.endsWith(".") ? translationKey : translationKey));
        return this;
    }
    
    public static class SelectionEntry<T>
    {
        private final T value;
        private final Icon thumbnailIcon;
        
        public SelectionEntry(final T value, @Nullable final Icon thumbnailIcon) {
            this.value = value;
            this.thumbnailIcon = thumbnailIcon;
        }
        
        @Override
        public boolean equals(final Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return false;
            }
            final SelectionEntry<?> entry = (SelectionEntry<?>)object;
            return Objects.equals(this.value, entry.value);
        }
        
        @Override
        public int hashCode() {
            return (this.value != null) ? this.value.hashCode() : 0;
        }
        
        public T getValue() {
            return this.value;
        }
        
        @Nullable
        public Icon thumbnailIcon() {
            return this.thumbnailIcon;
        }
    }
    
    public static class SelectionEntryWidget<T> extends DivWidget
    {
        private final SelectionEntry<T> selectionEntry;
        
        public SelectionEntryWidget(final SelectionEntry<T> selectionEntry) {
            this.selectionEntry = selectionEntry;
        }
        
        public SelectionEntry<T> selectionEntry() {
            return this.selectionEntry;
        }
    }
    
    @SettingFactory
    public static class Factory implements WidgetFactory<AdvancedSelectionSetting, AdvancedSelectionWidget<?>>
    {
        @Override
        public AdvancedSelectionWidget<?>[] create(final Setting setting, final AdvancedSelectionSetting annotation, final SettingAccessor accessor) {
            final Class<?> type = accessor.getType();
            final String translationKeyPrefix = setting.getTranslationKey() + ".entries";
            final Object value = accessor.get();
            Objects.requireNonNull(accessor);
            final AdvancedSelectionWidget<Object> widget = AdvancedSelectionWidget.create(value, accessor::set).translationKeyPrefix(translationKeyPrefix);
            if (type.isEnum()) {
                for (final Enum<?> enumConstant : (Enum[])type.getEnumConstants()) {
                    widget.add(enumConstant);
                }
            }
            accessor.property().addChangeListener((t, oldValue, newValue) -> widget.setSelected(newValue));
            return new AdvancedSelectionWidget[] { widget };
        }
        
        @Override
        public Class<?>[] types() {
            return new Class[0];
        }
    }
    
    public interface StringParser
    {
        Component parse(final String p0);
    }
    
    @SettingElement(extended = true)
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AdvancedSelectionSetting {
    }
}
