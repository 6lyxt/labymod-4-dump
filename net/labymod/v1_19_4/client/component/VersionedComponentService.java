// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.component;

import java.util.Collection;
import java.util.ArrayList;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.GsonBuilder;
import net.labymod.v1_19_4.client.network.chat.serializer.VersionedComponentSerializerWrapper;
import net.labymod.api.client.component.serializer.Serializer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_19_4.client.network.chat.VersionedTranslatableComponent;
import net.labymod.v1_19_4.client.network.chat.VersionedTextComponent;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.v1_19_4.client.network.chat.VersionedBaseComponent;
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
import net.labymod.v1_19_4.client.network.chat.MutableComponentAccessor;
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
        return (TextComponent)((MutableComponentAccessor)tj.b(text)).getLabyComponent();
    }
    
    @Override
    public TextComponent createTextComponent(final String text, final Style style, final List<Component> children) {
        final uo contents = new uo(text);
        return (TextComponent)this.applyStyleAndSiblings((tk)contents, style, children);
    }
    
    @Override
    public ScoreComponent createScoreComponent(final String name, final String objective) {
        return (ScoreComponent)((MutableComponentAccessor)tj.b(name, objective)).getLabyComponent();
    }
    
    @Override
    public ScoreComponent createScoreComponent(final String name, final String objective, final Style style, final List<Component> children) {
        final uq contents = new uq(name, objective);
        return (ScoreComponent)this.applyStyleAndSiblings((tk)contents, style, children);
    }
    
    @Override
    public KeybindComponent createKeybindComponent(final String keybind) {
        return (KeybindComponent)((MutableComponentAccessor)tj.d(keybind)).getLabyComponent();
    }
    
    @Override
    public KeybindComponent createKeybindComponent(final String keybind, final Style style, final List<Component> children) {
        final um contents = new um(keybind);
        return (KeybindComponent)this.applyStyleAndSiblings((tk)contents, style, children);
    }
    
    @Override
    protected IconComponent createIconComponent(final Icon icon) {
        return (IconComponent)((MutableComponentAccessor)tw.a((tk)new VersionedIconContents(icon))).getLabyComponent();
    }
    
    @Override
    protected IconComponent createIconComponent(final Icon icon, final Style style, final List<Component> children) {
        final VersionedIconContents contents = new VersionedIconContents(icon);
        return (IconComponent)this.applyStyleAndSiblings((tk)contents, style, children);
    }
    
    @Override
    public TextComponent createEmptyComponent() {
        return (TextComponent)((MutableComponentAccessor)tj.h()).getLabyComponent();
    }
    
    @Override
    public <T> HoverEvent<T> createHoverEvent(@NotNull final HoverEvent.Action<T> action, @NotNull final T value) {
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(value, "Value cannot be null!");
        if (action != HoverEvent.Action.SHOW_TEXT) {
            throw new UnsupportedOperationException("Unsupported action: " + String.valueOf(action));
        }
        if (value instanceof VersionedBaseComponent) {
            final VersionedBaseComponent<?, ?> component = (VersionedBaseComponent<?, ?>)value;
            return (HoverEvent<T>)new to(to.a.a, (Object)component.holder);
        }
        throw new IllegalStateException("Value must be a Component!");
    }
    
    @Override
    public ClickEvent createClickEvent(final ClickEvent.Action action, @NotNull final String value) {
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(value, "Value cannot be null!");
        final th.a vanillaAction = switch (action) {
            default -> throw new MatchException(null, null);
            case OPEN_URL -> th.a.a;
            case OPEN_FILE -> th.a.b;
            case RUN_COMMAND -> th.a.c;
            case SUGGEST_COMMAND -> th.a.d;
            case CHANGE_PAGE -> th.a.e;
            case COPY_TO_CLIPBOARD -> th.a.f;
        };
        return (ClickEvent)new th(vanillaAction, value);
    }
    
    @Override
    public TextColor createTextColor(final int value) {
        return (TextColor)uh.a(value);
    }
    
    @Override
    public TextColor getParsedTextColor(final String value) {
        return (TextColor)uh.a(value);
    }
    
    @Override
    public TranslatableComponent createTranslatableComponent(final String key, final Component... arguments) {
        return (TranslatableComponent)((MutableComponentAccessor)tj.a(key, (Object[])arguments)).getLabyComponent();
    }
    
    @Override
    public TranslatableComponent createTranslatableComponent(final String key, final Style style, final List<Component> children, final List<Component> arguments) {
        ut contents;
        if (arguments == null) {
            contents = new ut(key, key, new Object[0]);
        }
        else {
            contents = new ut(key, key, (Object[])arguments.toArray(new Component[0]));
        }
        return (TranslatableComponent)this.applyStyleAndSiblings((tk)contents, style, children);
    }
    
    @Override
    public Style getEmptyStyle() {
        uf style = uf.a;
        if (style == null) {
            style = new uf((uh)null, (Boolean)null, (Boolean)null, (Boolean)null, (Boolean)null, (Boolean)null, (th)null, (to)null, (String)null, (add)null);
        }
        return (Style)style;
    }
    
    @Override
    public Class<?> getActualVersionedClass(final Component component, final Class<?> cls) {
        if (cls == VersionedTextComponent.class) {
            return TextComponent.class;
        }
        if (cls == VersionedTranslatableComponent.class) {
            return TranslatableComponent.class;
        }
        return cls;
    }
    
    @Override
    public Style createStyleFromBuilder(final TextColor color, final Integer shadowColor, final Boolean bold, final Boolean italic, final Boolean underlined, final Boolean strikethrough, final Boolean obfuscated, final ClickEvent clickEvent, final HoverEvent<?> hoverEvent, final String insertion, final ResourceLocation fontLocation) {
        return (Style)new uf((uh)color, bold, italic, underlined, strikethrough, obfuscated, (th)clickEvent, (to)hoverEvent, insertion, (fontLocation == null) ? null : fontLocation.getMinecraftLocation());
    }
    
    @Override
    public Serializer createComponentSerializer() {
        return new VersionedComponentSerializerWrapper();
    }
    
    @Override
    public Serializer createStyleSerializer() {
        return (Serializer)new uf.b();
    }
    
    @Override
    public void applyGsonTypeAdapters(final GsonBuilder builder) {
        builder.registerTypeAdapterFactory((TypeAdapterFactory)new apg());
    }
    
    private Component applyStyleAndSiblings(final tk componentContents, final Style style, final List<Component> children) {
        uf vanillaStyle;
        if (style == null) {
            vanillaStyle = uf.a;
        }
        else {
            vanillaStyle = (uf)style;
        }
        final tw component = new tw(componentContents, (List)new ArrayList(), vanillaStyle);
        final Component result = ((MutableComponentAccessor)component).getLabyComponent();
        if (children != null) {
            result.setChildren(children);
        }
        return result;
    }
}
