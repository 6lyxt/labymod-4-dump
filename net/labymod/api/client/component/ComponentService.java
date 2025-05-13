// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component;

import java.util.HashMap;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import com.google.gson.GsonBuilder;
import net.labymod.api.client.component.serializer.Serializer;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.event.ClickEvent;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.util.debug.Preconditions;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.util.logging.Logging;
import java.util.Map;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public abstract class ComponentService
{
    private static final Map<Class<?>, Class<?>> ACTUAL_CLASSES;
    private static final Logging LOGGER;
    private final List<Class<?>> reportedTypes;
    
    protected ComponentService() {
        this.reportedTypes = new ArrayList<Class<?>>();
    }
    
    public static Class<?> getActualClass(final Component component) {
        final Class<?> cls = component.getClass();
        Class<?> actualClass = ComponentService.ACTUAL_CLASSES.get(cls);
        if (actualClass != null) {
            return actualClass;
        }
        actualClass = componentService().getActualVersionedClass(component, cls);
        ComponentService.ACTUAL_CLASSES.put(cls, actualClass);
        return actualClass;
    }
    
    public static TextComponent textComponent(final String text) {
        Preconditions.notNull(text, "text");
        return componentService().createTextComponent(text);
    }
    
    public static TextComponent textComponent(final String text, final Style style, final List<Component> children) {
        Preconditions.notNull(text, "text");
        return componentService().createTextComponent(text, style, children);
    }
    
    public static ScoreComponent scoreComponent(final String name, final String objective) {
        Preconditions.notNull(name, "name");
        Preconditions.notNull(objective, "objective");
        return componentService().createScoreComponent(name, objective);
    }
    
    public static ScoreComponent scoreComponent(final String name, final String objective, final Style style, final List<Component> children) {
        Preconditions.notNull(name, "name");
        Preconditions.notNull(objective, "objective");
        return componentService().createScoreComponent(name, objective, style, children);
    }
    
    public static KeybindComponent keybindComponent(final String keybind) {
        Preconditions.notNull(keybind, "keybind");
        return componentService().createKeybindComponent(keybind);
    }
    
    public static KeybindComponent keybindComponent(final MinecraftInputMapping keybind) {
        Preconditions.notNull(keybind, "keybind");
        return componentService().createKeybindComponent(keybind.getName());
    }
    
    public static KeybindComponent keybindComponent(final String keybind, final Style style, final List<Component> children) {
        Preconditions.notNull(keybind, "keybind");
        return componentService().createKeybindComponent(keybind, style, children);
    }
    
    public static IconComponent iconComponent(final Icon icon) {
        Preconditions.notNull(icon, "icon");
        return componentService().createIconComponent(icon);
    }
    
    public static IconComponent iconComponent(final Icon icon, final Style style, final List<Component> children) {
        Preconditions.notNull(icon, "icon");
        return componentService().createIconComponent(icon, style, children);
    }
    
    public static TextComponent empty() {
        return componentService().createEmptyComponent();
    }
    
    public static TranslatableComponent translatableComponent(final String key, final Component... arguments) {
        Preconditions.notNull(key, "key");
        return componentService().createTranslatableComponent(key, arguments);
    }
    
    public static TranslatableComponent translatableComponent(final String key, final Style style, final List<Component> children, final List<Component> arguments) {
        Preconditions.notNull(key, "key");
        return componentService().createTranslatableComponent(key, style, children, arguments);
    }
    
    public static <T> HoverEvent<T> hoverEvent(@NotNull final HoverEvent.Action<T> action, @NotNull final T value) {
        Preconditions.notNull(action, "action");
        Preconditions.notNull(value, "value");
        return (HoverEvent<T>)componentService().createHoverEvent((HoverEvent.Action<Object>)action, value);
    }
    
    public static ClickEvent clickEvent(@NotNull final ClickEvent.Action action, @NotNull final String value) {
        Preconditions.notNull(action, "action");
        Preconditions.notNull(value, "value");
        return componentService().createClickEvent(action, value);
    }
    
    public static TextColor textColor(final int value) {
        return componentService().createTextColor(value);
    }
    
    public static TextColor parseTextColor(final String value) {
        Preconditions.notNull(value, "value");
        return componentService().getParsedTextColor(value);
    }
    
    public static Style emptyStyle() {
        return componentService().getEmptyStyle();
    }
    
    public static Serializer getComponentSerializer() {
        return componentService().createComponentSerializer();
    }
    
    public static Serializer getStyleSerializer() {
        return componentService().createStyleSerializer();
    }
    
    public static void applyTypeAdapters(final GsonBuilder builder) {
        componentService().applyGsonTypeAdapters(builder);
    }
    
    public static Style buildStyle(final TextColor color, final Integer shadowColor, final Boolean bold, final Boolean italic, final Boolean underlined, final Boolean strikethrough, final Boolean obfuscated, final ClickEvent clickEvent, final HoverEvent<?> hoverEvent, final String insertion, final ResourceLocation fontLocation) {
        return componentService().createStyleFromBuilder(color, shadowColor, bold, italic, underlined, strikethrough, obfuscated, clickEvent, hoverEvent, insertion, fontLocation);
    }
    
    private static ComponentService componentService() {
        return Laby.references().componentService();
    }
    
    public abstract TextComponent createTextComponent(final String p0);
    
    public abstract TextComponent createTextComponent(final String p0, final Style p1, final List<Component> p2);
    
    public abstract ScoreComponent createScoreComponent(final String p0, final String p1);
    
    public abstract ScoreComponent createScoreComponent(final String p0, final String p1, final Style p2, final List<Component> p3);
    
    public abstract KeybindComponent createKeybindComponent(final String p0);
    
    public abstract KeybindComponent createKeybindComponent(final String p0, final Style p1, final List<Component> p2);
    
    protected abstract IconComponent createIconComponent(final Icon p0);
    
    protected abstract IconComponent createIconComponent(final Icon p0, final Style p1, final List<Component> p2);
    
    public abstract TextComponent createEmptyComponent();
    
    public abstract <T> HoverEvent<T> createHoverEvent(@NotNull final HoverEvent.Action<T> p0, @NotNull final T p1);
    
    public abstract ClickEvent createClickEvent(@NotNull final ClickEvent.Action p0, @NotNull final String p1);
    
    public abstract TextColor createTextColor(final int p0);
    
    public abstract TextColor getParsedTextColor(final String p0);
    
    public abstract TranslatableComponent createTranslatableComponent(final String p0, final Component... p1);
    
    public abstract TranslatableComponent createTranslatableComponent(final String p0, final Style p1, final List<Component> p2, final List<Component> p3);
    
    public abstract Style getEmptyStyle();
    
    public abstract Class<?> getActualVersionedClass(final Component p0, final Class<?> p1);
    
    public abstract Style createStyleFromBuilder(final TextColor p0, final Integer p1, final Boolean p2, final Boolean p3, final Boolean p4, final Boolean p5, final Boolean p6, final ClickEvent p7, final HoverEvent<?> p8, final String p9, final ResourceLocation p10);
    
    public abstract Serializer createComponentSerializer();
    
    public abstract Serializer createStyleSerializer();
    
    public abstract void applyGsonTypeAdapters(final GsonBuilder p0);
    
    public void reportMissing(final Object object, final boolean toSentry) {
        final Class<?> clazz = object.getClass();
        if (this.reportedTypes.contains(clazz)) {
            return;
        }
        this.reportedTypes.add(clazz);
        final String message = "Component type " + clazz.getName() + " is not supported (" + String.valueOf(object);
        if (toSentry) {
            ComponentService.LOGGER.error(message, new Object[0]);
        }
        else {
            ComponentService.LOGGER.info(message, new Object[0]);
        }
        if (toSentry) {
            Laby.references().sentryService().capture(new IllegalStateException(message));
        }
    }
    
    static {
        ACTUAL_CLASSES = new HashMap<Class<?>, Class<?>>();
        LOGGER = Logging.create(ComponentService.class);
    }
}
