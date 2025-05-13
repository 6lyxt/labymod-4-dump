// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.component;

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
        return (TextComponent)new oe(text);
    }
    
    @Override
    public TextComponent createTextComponent(final String text, final Style style, final List<Component> children) {
        final oe component = new oe(text);
        return (TextComponent)this.applyStyleAndSiblings((nn)component, style, children);
    }
    
    @Override
    public ScoreComponent createScoreComponent(final String name, final String objective) {
        return (ScoreComponent)new nz(name, objective);
    }
    
    @Override
    public ScoreComponent createScoreComponent(final String name, final String objective, final Style style, final List<Component> children) {
        final nz component = new nz(name, objective);
        return (ScoreComponent)this.applyStyleAndSiblings((nn)component, style, children);
    }
    
    @Override
    public KeybindComponent createKeybindComponent(final String keybind) {
        return (KeybindComponent)new nw(keybind);
    }
    
    @Override
    public KeybindComponent createKeybindComponent(final String keybind, final Style style, final List<Component> children) {
        final nw component = new nw(keybind);
        return (KeybindComponent)this.applyStyleAndSiblings((nn)component, style, children);
    }
    
    @Override
    protected IconComponent createIconComponent(final Icon icon) {
        return (IconComponent)new VersionedIconComponent(icon);
    }
    
    @Override
    protected IconComponent createIconComponent(final Icon icon, final Style style, final List<Component> children) {
        final VersionedIconComponent component = new VersionedIconComponent(icon);
        return (IconComponent)this.applyStyleAndSiblings((nn)component, style, children);
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
            return (HoverEvent<T>)new nv(nv.a.a, (Object)value);
        }
        throw new UnsupportedOperationException("Unsupported action: " + String.valueOf(action));
    }
    
    @Override
    public ClickEvent createClickEvent(@NotNull final ClickEvent.Action action, @NotNull final String value) {
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(value, "Value cannot be null!");
        np.a vanillaAction = null;
        switch (action) {
            case OPEN_URL: {
                vanillaAction = np.a.a;
                break;
            }
            case OPEN_FILE: {
                vanillaAction = np.a.b;
                break;
            }
            case RUN_COMMAND: {
                vanillaAction = np.a.c;
                break;
            }
            case SUGGEST_COMMAND: {
                vanillaAction = np.a.d;
                break;
            }
            case CHANGE_PAGE: {
                vanillaAction = np.a.e;
                break;
            }
            case COPY_TO_CLIPBOARD: {
                vanillaAction = np.a.f;
                break;
            }
        }
        return (ClickEvent)new np(vanillaAction, value);
    }
    
    @Override
    public TextColor createTextColor(final int value) {
        return (TextColor)od.a(value);
    }
    
    @Override
    public TextColor getParsedTextColor(final String value) {
        return (TextColor)od.a(value);
    }
    
    @Override
    public Style getEmptyStyle() {
        ob style = ob.a;
        if (style == null) {
            style = new ob((od)null, (Boolean)null, (Boolean)null, (Boolean)null, (Boolean)null, (Boolean)null, (np)null, (nv)null, (String)null, (vk)null);
        }
        return (Style)style;
    }
    
    @Override
    public TranslatableComponent createTranslatableComponent(final String key, final Component... arguments) {
        return (TranslatableComponent)new of(key, (Object[])arguments);
    }
    
    @Override
    public TranslatableComponent createTranslatableComponent(final String key, final Style style, final List<Component> children, final List<Component> arguments) {
        of component;
        if (arguments == null) {
            component = new of(key);
        }
        else {
            component = new of(key, arguments.toArray(new Object[0]));
        }
        return (TranslatableComponent)this.applyStyleAndSiblings((nn)component, style, children);
    }
    
    @Override
    public Class<?> getActualVersionedClass(final Component component, final Class<?> cls) {
        if (cls == oe.class) {
            return TextComponent.class;
        }
        if (cls == of.class) {
            return TranslatableComponent.class;
        }
        return cls;
    }
    
    @Override
    public Style createStyleFromBuilder(final TextColor color, final Integer shadowColor, final Boolean bold, final Boolean italic, final Boolean underlined, final Boolean strikethrough, final Boolean obfuscated, final ClickEvent clickEvent, final HoverEvent<?> hoverEvent, final String insertion, final ResourceLocation fontLocation) {
        return (Style)new ob((od)color, bold, italic, underlined, strikethrough, obfuscated, (np)clickEvent, (nv)hoverEvent, insertion, (fontLocation == null) ? null : fontLocation.getMinecraftLocation());
    }
    
    @Override
    public Serializer createComponentSerializer() {
        return (Serializer)new nr.a();
    }
    
    @Override
    public Serializer createStyleSerializer() {
        return (Serializer)new ob.a();
    }
    
    @Override
    public void applyGsonTypeAdapters(final GsonBuilder builder) {
        builder.registerTypeAdapterFactory((TypeAdapterFactory)new afl());
    }
    
    private Component applyStyleAndSiblings(final nn component, final Style style, final List<Component> children) {
        if (style != null) {
            component.a((ob)style);
        }
        final Component result = (Component)component;
        if (children != null) {
            result.setChildren(children);
        }
        return result;
    }
}
