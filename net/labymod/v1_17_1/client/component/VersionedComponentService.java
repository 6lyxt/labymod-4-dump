// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.component;

import java.util.Collection;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.GsonBuilder;
import net.labymod.api.client.component.serializer.Serializer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.component.TranslatableComponent;
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
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.component.ComponentService;

@Singleton
@Implements(ComponentService.class)
public class VersionedComponentService extends ComponentService
{
    @Inject
    public VersionedComponentService() {
    }
    
    @Override
    public TextComponent createTextComponent(final String text) {
        return (TextComponent)new pf(text);
    }
    
    @Override
    public TextComponent createTextComponent(final String text, final Style style, final List<Component> children) {
        final pf component = new pf(text);
        return (TextComponent)this.applyStyleAndSiblings((oo)component, style, children);
    }
    
    @Override
    public ScoreComponent createScoreComponent(final String name, final String objective) {
        return (ScoreComponent)new pa(name, objective);
    }
    
    @Override
    public ScoreComponent createScoreComponent(final String name, final String objective, final Style style, final List<Component> children) {
        final pa component = new pa(name, objective);
        return (ScoreComponent)this.applyStyleAndSiblings((oo)component, style, children);
    }
    
    @Override
    public KeybindComponent createKeybindComponent(final String keybind) {
        return (KeybindComponent)new ox(keybind);
    }
    
    @Override
    public KeybindComponent createKeybindComponent(final String keybind, final Style style, final List<Component> children) {
        final ox component = new ox(keybind);
        return (KeybindComponent)this.applyStyleAndSiblings((oo)component, style, children);
    }
    
    @Override
    protected IconComponent createIconComponent(final Icon icon) {
        return (IconComponent)new VersionedIconComponent(icon);
    }
    
    @Override
    protected IconComponent createIconComponent(final Icon icon, final Style style, final List<Component> children) {
        final VersionedIconComponent component = new VersionedIconComponent(icon);
        return (IconComponent)this.applyStyleAndSiblings((oo)component, style, children);
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
            return (HoverEvent<T>)new ow(ow.a.a, (Object)value);
        }
        throw new UnsupportedOperationException("Unsupported action: " + String.valueOf(action));
    }
    
    @Override
    public ClickEvent createClickEvent(@NotNull final ClickEvent.Action action, @NotNull final String value) {
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(value, "Value cannot be null!");
        final oq.a vanillaAction = switch (action) {
            default -> throw new MatchException(null, null);
            case OPEN_URL -> oq.a.a;
            case OPEN_FILE -> oq.a.b;
            case RUN_COMMAND -> oq.a.c;
            case SUGGEST_COMMAND -> oq.a.d;
            case CHANGE_PAGE -> oq.a.e;
            case COPY_TO_CLIPBOARD -> oq.a.f;
        };
        return (ClickEvent)new oq(vanillaAction, value);
    }
    
    @Override
    public TextColor createTextColor(final int value) {
        return (TextColor)pe.a(value);
    }
    
    @Override
    public TextColor getParsedTextColor(final String value) {
        return (TextColor)pe.a(value);
    }
    
    @Override
    public Style getEmptyStyle() {
        pc style = pc.a;
        if (style == null) {
            style = new pc((pe)null, (Boolean)null, (Boolean)null, (Boolean)null, (Boolean)null, (Boolean)null, (oq)null, (ow)null, (String)null, (ww)null);
        }
        return (Style)style;
    }
    
    @Override
    public TranslatableComponent createTranslatableComponent(final String key, final Component... arguments) {
        return (TranslatableComponent)new pg(key, (Object[])arguments);
    }
    
    @Override
    public TranslatableComponent createTranslatableComponent(final String key, final Style style, final List<Component> children, final List<Component> arguments) {
        pg component;
        if (arguments == null) {
            component = new pg(key);
        }
        else {
            component = new pg(key, arguments.toArray(new Object[0]));
        }
        return (TranslatableComponent)this.applyStyleAndSiblings((oo)component, style, children);
    }
    
    @Override
    public Class<?> getActualVersionedClass(final Component component, final Class<?> cls) {
        if (cls == pf.class) {
            return TextComponent.class;
        }
        if (cls == pg.class) {
            return TranslatableComponent.class;
        }
        return cls;
    }
    
    @Override
    public Style createStyleFromBuilder(final TextColor color, final Integer shadowColor, final Boolean bold, final Boolean italic, final Boolean underlined, final Boolean strikethrough, final Boolean obfuscated, final ClickEvent clickEvent, final HoverEvent<?> hoverEvent, final String insertion, final ResourceLocation fontLocation) {
        return (Style)new pc((pe)color, bold, italic, underlined, strikethrough, obfuscated, (oq)clickEvent, (ow)hoverEvent, insertion, (fontLocation == null) ? null : fontLocation.getMinecraftLocation());
    }
    
    @Override
    public Serializer createComponentSerializer() {
        return (Serializer)new os.a();
    }
    
    @Override
    public Serializer createStyleSerializer() {
        return (Serializer)new pc.a();
    }
    
    @Override
    public void applyGsonTypeAdapters(final GsonBuilder builder) {
        builder.registerTypeAdapterFactory((TypeAdapterFactory)new agz());
    }
    
    private Component applyStyleAndSiblings(final oo component, final Style style, final List<Component> children) {
        if (style != null) {
            component.a((pc)style);
        }
        final Component result = (Component)component;
        if (children != null) {
            result.setChildren(children);
        }
        return result;
    }
}
