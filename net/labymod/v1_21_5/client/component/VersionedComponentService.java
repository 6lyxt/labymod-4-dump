// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.component;

import java.util.Collection;
import java.util.ArrayList;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.GsonBuilder;
import net.labymod.v1_21_5.client.network.chat.serializer.VersionedComponentSerializerWrapper;
import net.labymod.api.client.component.serializer.Serializer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_21_5.client.network.chat.VersionedTranslatableComponent;
import net.labymod.v1_21_5.client.network.chat.VersionedTextComponent;
import net.labymod.api.client.component.TranslatableComponent;
import com.mojang.serialization.DataResult;
import net.labymod.api.client.component.format.TextColor;
import java.nio.file.Paths;
import java.net.URI;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.v1_21_5.client.network.chat.VersionedBaseComponent;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.KeybindComponent;
import com.mojang.datafixers.util.Either;
import net.labymod.api.client.component.ScoreComponent;
import net.labymod.api.client.component.Component;
import java.util.List;
import net.labymod.api.client.component.format.Style;
import net.labymod.v1_21_5.client.network.chat.MutableComponentAccessor;
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
        return (TextComponent)((MutableComponentAccessor)xg.b(text)).getLabyComponent();
    }
    
    @Override
    public TextComponent createTextComponent(final String text, final Style style, final List<Component> children) {
        final yn.a contents = new yn.a(text);
        return (TextComponent)this.applyStyleAndSiblings((xh)contents, style, children);
    }
    
    @Override
    public ScoreComponent createScoreComponent(final String name, final String objective) {
        return (ScoreComponent)((MutableComponentAccessor)xg.b(name, objective)).getLabyComponent();
    }
    
    @Override
    public ScoreComponent createScoreComponent(final String name, final String objective, final Style style, final List<Component> children) {
        final yo contents = new yo(Either.right((Object)name), objective);
        return (ScoreComponent)this.applyStyleAndSiblings((xh)contents, style, children);
    }
    
    @Override
    public KeybindComponent createKeybindComponent(final String keybind) {
        return (KeybindComponent)((MutableComponentAccessor)xg.d(keybind)).getLabyComponent();
    }
    
    @Override
    public KeybindComponent createKeybindComponent(final String keybind, final Style style, final List<Component> children) {
        final yk contents = new yk(keybind);
        return (KeybindComponent)this.applyStyleAndSiblings((xh)contents, style, children);
    }
    
    @Override
    protected IconComponent createIconComponent(final Icon icon) {
        return (IconComponent)((MutableComponentAccessor)xu.a((xh)new VersionedIconContents(icon))).getLabyComponent();
    }
    
    @Override
    protected IconComponent createIconComponent(final Icon icon, final Style style, final List<Component> children) {
        final VersionedIconContents contents = new VersionedIconContents(icon);
        return (IconComponent)this.applyStyleAndSiblings((xh)contents, style, children);
    }
    
    @Override
    public TextComponent createEmptyComponent() {
        return (TextComponent)((MutableComponentAccessor)xg.i()).getLabyComponent();
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
            return (HoverEvent<T>)new xm.e((xg)component.holder);
        }
        throw new IllegalStateException("Value must be a Component!");
    }
    
    @Override
    public ClickEvent createClickEvent(final ClickEvent.Action action, @NotNull final String value) {
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(value, "Value cannot be null!");
        final xe event = switch (action) {
            default -> throw new MatchException(null, null);
            case OPEN_URL -> new xe.e(URI.create(value));
            case OPEN_FILE -> new xe.d(Paths.get(value, new String[0]));
            case RUN_COMMAND -> new xe.f(value);
            case SUGGEST_COMMAND -> new xe.g(value);
            case CHANGE_PAGE -> new xe.b(Integer.parseInt(value));
            case COPY_TO_CLIPBOARD -> new xe.c(value);
        };
        return (ClickEvent)event;
    }
    
    @Override
    public TextColor createTextColor(final int value) {
        return (TextColor)yf.a(value);
    }
    
    @Override
    public TextColor getParsedTextColor(final String value) {
        final DataResult<yf> result = (DataResult<yf>)yf.a(value);
        return result.result().get();
    }
    
    @Override
    public TranslatableComponent createTranslatableComponent(final String key, final Component... arguments) {
        return (TranslatableComponent)((MutableComponentAccessor)xg.a(key, (Object[])arguments)).getLabyComponent();
    }
    
    @Override
    public TranslatableComponent createTranslatableComponent(final String key, final Style style, final List<Component> children, final List<Component> arguments) {
        yr contents;
        if (arguments == null) {
            contents = new yr(key, key, new Object[0]);
        }
        else {
            contents = new yr(key, key, (Object[])arguments.toArray(new Component[0]));
        }
        return (TranslatableComponent)this.applyStyleAndSiblings((xh)contents, style, children);
    }
    
    @Override
    public Style getEmptyStyle() {
        yd style = yd.a;
        if (style == null) {
            style = new yd((yf)null, (Integer)null, (Boolean)null, (Boolean)null, (Boolean)null, (Boolean)null, (Boolean)null, (xe)null, (xm)null, (String)null, (alr)null);
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
        return (Style)new yd((yf)color, shadowColor, bold, italic, underlined, strikethrough, obfuscated, (xe)clickEvent, (xm)hoverEvent, insertion, (fontLocation == null) ? null : fontLocation.getMinecraftLocation());
    }
    
    @Override
    public Serializer createComponentSerializer() {
        return new VersionedComponentSerializerWrapper();
    }
    
    @Override
    public Serializer createStyleSerializer() {
        return (Serializer)new yd.b();
    }
    
    @Override
    public void applyGsonTypeAdapters(final GsonBuilder builder) {
        builder.registerTypeAdapterFactory((TypeAdapterFactory)new azw());
    }
    
    private Component applyStyleAndSiblings(final xh componentContents, final Style style, final List<Component> children) {
        yd vanillaStyle;
        if (style == null) {
            vanillaStyle = yd.a;
        }
        else {
            vanillaStyle = (yd)style;
        }
        final xu component = new xu(componentContents, (List)new ArrayList(), vanillaStyle);
        final Component result = ((MutableComponentAccessor)component).getLabyComponent();
        if (children != null) {
            result.setChildren(children);
        }
        return result;
    }
}
