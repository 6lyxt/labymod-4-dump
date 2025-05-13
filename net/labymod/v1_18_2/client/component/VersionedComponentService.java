// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.component;

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
        return (TextComponent)new qx(text);
    }
    
    @Override
    public TextComponent createTextComponent(final String text, final Style style, final List<Component> children) {
        final qx component = new qx(text);
        return (TextComponent)this.applyStyleAndSiblings((qg)component, style, children);
    }
    
    @Override
    public ScoreComponent createScoreComponent(final String name, final String objective) {
        return (ScoreComponent)new qs(name, objective);
    }
    
    @Override
    public ScoreComponent createScoreComponent(final String name, final String objective, final Style style, final List<Component> children) {
        final qs component = new qs(name, objective);
        return (ScoreComponent)this.applyStyleAndSiblings((qg)component, style, children);
    }
    
    @Override
    public KeybindComponent createKeybindComponent(final String keybind) {
        return (KeybindComponent)new qp(keybind);
    }
    
    @Override
    public KeybindComponent createKeybindComponent(final String keybind, final Style style, final List<Component> children) {
        final qp component = new qp(keybind);
        return (KeybindComponent)this.applyStyleAndSiblings((qg)component, style, children);
    }
    
    @Override
    protected IconComponent createIconComponent(final Icon icon) {
        return (IconComponent)new VersionedIconComponent(icon);
    }
    
    @Override
    protected IconComponent createIconComponent(final Icon icon, final Style style, final List<Component> children) {
        final VersionedIconComponent iconComponent = new VersionedIconComponent(icon);
        return (IconComponent)this.applyStyleAndSiblings((qg)iconComponent, style, children);
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
            return (HoverEvent<T>)new qo(qo.a.a, (Object)value);
        }
        throw new UnsupportedOperationException("Unsupported action: " + String.valueOf(action));
    }
    
    @Override
    public ClickEvent createClickEvent(@NotNull final ClickEvent.Action action, @NotNull final String value) {
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(value, "Value cannot be null!");
        final qi.a vanillaAction = switch (action) {
            default -> throw new MatchException(null, null);
            case OPEN_URL -> qi.a.a;
            case OPEN_FILE -> qi.a.b;
            case RUN_COMMAND -> qi.a.c;
            case SUGGEST_COMMAND -> qi.a.d;
            case CHANGE_PAGE -> qi.a.e;
            case COPY_TO_CLIPBOARD -> qi.a.f;
        };
        return (ClickEvent)new qi(vanillaAction, value);
    }
    
    @Override
    public TextColor createTextColor(final int value) {
        return (TextColor)qw.a(value);
    }
    
    @Override
    public TextColor getParsedTextColor(final String value) {
        return (TextColor)qw.a(value);
    }
    
    @Override
    public Style getEmptyStyle() {
        qu style = qu.a;
        if (style == null) {
            style = new qu((qw)null, (Boolean)null, (Boolean)null, (Boolean)null, (Boolean)null, (Boolean)null, (qi)null, (qo)null, (String)null, (yt)null);
        }
        return (Style)style;
    }
    
    @Override
    public TranslatableComponent createTranslatableComponent(final String key, final Component... arguments) {
        return (TranslatableComponent)new qy(key, (Object[])arguments);
    }
    
    @Override
    public TranslatableComponent createTranslatableComponent(final String key, final Style style, final List<Component> children, final List<Component> arguments) {
        qy component;
        if (arguments == null) {
            component = new qy(key);
        }
        else {
            component = new qy(key, arguments.toArray(new Object[0]));
        }
        return (TranslatableComponent)this.applyStyleAndSiblings((qg)component, style, children);
    }
    
    @Override
    public Class<?> getActualVersionedClass(final Component component, final Class<?> cls) {
        if (cls == qx.class) {
            return TextComponent.class;
        }
        if (cls == qy.class) {
            return TranslatableComponent.class;
        }
        return cls;
    }
    
    @Override
    public Style createStyleFromBuilder(final TextColor color, final Integer shadowColor, final Boolean bold, final Boolean italic, final Boolean underlined, final Boolean strikethrough, final Boolean obfuscated, final ClickEvent clickEvent, final HoverEvent<?> hoverEvent, final String insertion, final ResourceLocation fontLocation) {
        return (Style)new qu((qw)color, bold, italic, underlined, strikethrough, obfuscated, (qi)clickEvent, (qo)hoverEvent, insertion, (fontLocation == null) ? null : fontLocation.getMinecraftLocation());
    }
    
    @Override
    public Serializer createComponentSerializer() {
        return (Serializer)new qk.a();
    }
    
    @Override
    public Serializer createStyleSerializer() {
        return (Serializer)new qu.a();
    }
    
    @Override
    public void applyGsonTypeAdapters(final GsonBuilder builder) {
        builder.registerTypeAdapterFactory((TypeAdapterFactory)new aji());
    }
    
    private Component applyStyleAndSiblings(final qg component, final Style style, final List<Component> children) {
        if (style != null) {
            component.a((qu)style);
        }
        final Component result = (Component)component;
        if (children != null) {
            result.setChildren(children);
        }
        return result;
    }
}
