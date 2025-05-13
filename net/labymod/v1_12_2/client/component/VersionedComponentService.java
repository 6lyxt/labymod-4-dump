// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.component;

import java.util.Collection;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.GsonBuilder;
import net.labymod.api.client.component.serializer.Serializer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.util.Color;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.event.ClickEvent;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.KeybindComponent;
import net.labymod.api.client.component.ScoreComponent;
import net.labymod.api.client.component.Component;
import java.util.List;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.event.EventBus;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.component.ComponentService;

@Singleton
@Implements(ComponentService.class)
public class VersionedComponentService extends ComponentService
{
    private static final hn EMPTY_STYLE;
    
    public VersionedComponentService(final EventBus eventBus) {
        eventBus.registerListener(new VersionedKeybindComponentSerializer());
    }
    
    @Override
    public TextComponent createTextComponent(final String text) {
        return (TextComponent)new ho(text);
    }
    
    @Override
    public TextComponent createTextComponent(final String text, final Style style, final List<Component> children) {
        final ho component = new ho(text);
        return (TextComponent)this.applyStyleAndSiblings((he)component, style, children);
    }
    
    @Override
    public ScoreComponent createScoreComponent(final String name, final String objective) {
        return (ScoreComponent)new hl(name, objective);
    }
    
    @Override
    public ScoreComponent createScoreComponent(final String name, final String objective, final Style style, final List<Component> children) {
        final hl component = new hl(name, objective);
        return (ScoreComponent)this.applyStyleAndSiblings((he)component, style, children);
    }
    
    @Override
    public KeybindComponent createKeybindComponent(final String keybind) {
        return (KeybindComponent)new VersionedKeybindComponent(keybind);
    }
    
    @Override
    public KeybindComponent createKeybindComponent(final String keybind, final Style style, final List<Component> children) {
        final VersionedKeybindComponent component = new VersionedKeybindComponent(keybind);
        return (KeybindComponent)this.applyStyleAndSiblings((he)component, style, children);
    }
    
    @Override
    protected IconComponent createIconComponent(final Icon icon) {
        return (IconComponent)new VersionedIconComponent(icon);
    }
    
    @Override
    protected IconComponent createIconComponent(final Icon icon, final Style style, final List<Component> children) {
        final VersionedIconComponent component = new VersionedIconComponent(icon);
        return (IconComponent)this.applyStyleAndSiblings((he)component, style, children);
    }
    
    @Override
    public TextComponent createEmptyComponent() {
        return this.createTextComponent("");
    }
    
    @Override
    public <T> HoverEvent<T> createHoverEvent(@NotNull final HoverEvent.Action<T> action, @NotNull final T value) {
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(value, "Value cannot be null!");
        if (action == HoverEvent.Action.SHOW_TEXT) {
            return (HoverEvent<T>)new hj(hj.a.a, (hh)value);
        }
        throw new UnsupportedOperationException("Unsupported action: " + String.valueOf(action));
    }
    
    @Override
    public ClickEvent createClickEvent(final ClickEvent.Action action, @NotNull final String value) {
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(value, "Value cannot be null!");
        hg.a vanillaAction = null;
        switch (action) {
            case OPEN_URL: {
                vanillaAction = hg.a.a;
                break;
            }
            case OPEN_FILE: {
                vanillaAction = hg.a.b;
                break;
            }
            case RUN_COMMAND: {
                vanillaAction = hg.a.c;
                break;
            }
            case CHANGE_PAGE: {
                vanillaAction = hg.a.e;
                break;
            }
            default: {
                vanillaAction = hg.a.d;
                break;
            }
        }
        final ClickEvent clickEvent = (ClickEvent)new hg(vanillaAction, value);
        ((VersionedClickEvent)clickEvent).labyMod$setAction(action);
        return clickEvent;
    }
    
    @Override
    public TextColor createTextColor(final int value) {
        final VersionedTextColor versionedTextColor = VersionedNamedTextColors.byValue(value);
        if (versionedTextColor != null) {
            return versionedTextColor;
        }
        return new VersionedTextColor(Color.of(value));
    }
    
    @Override
    public TextColor getParsedTextColor(final String value) {
        if (!value.startsWith("#")) {
            return VersionedNamedTextColors.byName(value);
        }
        final VersionedTextColor versionedTextColor = VersionedNamedTextColors.byHex(value);
        if (versionedTextColor != null) {
            return versionedTextColor;
        }
        return new VersionedTextColor(value);
    }
    
    @Override
    public TranslatableComponent createTranslatableComponent(final String key, final Component... arguments) {
        return (TranslatableComponent)new hp(key, (Object[])arguments);
    }
    
    @Override
    public TranslatableComponent createTranslatableComponent(final String key, final Style style, final List<Component> children, final List<Component> arguments) {
        hp component;
        if (arguments == null) {
            component = new hp(key, new Object[0]);
        }
        else {
            component = new hp(key, arguments.toArray(new Object[0]));
        }
        return (TranslatableComponent)this.applyStyleAndSiblings((he)component, style, children);
    }
    
    @Override
    public Style getEmptyStyle() {
        return (Style)VersionedComponentService.EMPTY_STYLE;
    }
    
    @Override
    public Class<?> getActualVersionedClass(final Component component, final Class<?> cls) {
        if (cls == ho.class) {
            return TextComponent.class;
        }
        if (cls == hp.class) {
            return TranslatableComponent.class;
        }
        return cls;
    }
    
    @Override
    public Style createStyleFromBuilder(final TextColor color, final Integer shadowColor, final Boolean bold, final Boolean italic, final Boolean underlined, final Boolean strikethrough, final Boolean obfuscated, final ClickEvent clickEvent, final HoverEvent<?> hoverEvent, final String insertion, final ResourceLocation fontLocation) {
        final hn chatStyle = new hn();
        chatStyle.a(bold);
        chatStyle.b(italic);
        chatStyle.d(underlined);
        chatStyle.c(strikethrough);
        chatStyle.e(obfuscated);
        chatStyle.a((hj)hoverEvent);
        chatStyle.a((hg)clickEvent);
        chatStyle.a(insertion);
        return ((VersionedStyle)chatStyle).setLabyColor((VersionedTextColor)color);
    }
    
    @Override
    public Serializer createComponentSerializer() {
        return (Serializer)new hh.a();
    }
    
    @Override
    public Serializer createStyleSerializer() {
        return (Serializer)new hn.a();
    }
    
    @Override
    public void applyGsonTypeAdapters(final GsonBuilder builder) {
        builder.registerTypeAdapterFactory((TypeAdapterFactory)new rj());
    }
    
    private Component applyStyleAndSiblings(final he component, final Style style, final List<Component> children) {
        if (style != null) {
            component.a((hn)style);
        }
        final Component result = (Component)component;
        if (children != null) {
            result.setChildren(children);
        }
        return result;
    }
    
    static {
        EMPTY_STYLE = new hn();
    }
}
