// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.component;

import java.util.Collection;
import java.util.ArrayList;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.GsonBuilder;
import net.labymod.v1_21_1.client.network.chat.serializer.VersionedComponentSerializerWrapper;
import net.labymod.api.client.component.serializer.Serializer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_21_1.client.network.chat.VersionedTranslatableComponent;
import net.labymod.v1_21_1.client.network.chat.VersionedTextComponent;
import net.labymod.api.client.component.TranslatableComponent;
import com.mojang.serialization.DataResult;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.v1_21_1.client.network.chat.VersionedBaseComponent;
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
import net.labymod.v1_21_1.client.network.chat.MutableComponentAccessor;
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
        return (TextComponent)((MutableComponentAccessor)wz.b(text)).getLabyComponent();
    }
    
    @Override
    public TextComponent createTextComponent(final String text, final Style style, final List<Component> children) {
        final yg.a contents = new yg.a(text);
        return (TextComponent)this.applyStyleAndSiblings((xa)contents, style, children);
    }
    
    @Override
    public ScoreComponent createScoreComponent(final String name, final String objective) {
        return (ScoreComponent)((MutableComponentAccessor)wz.b(name, objective)).getLabyComponent();
    }
    
    @Override
    public ScoreComponent createScoreComponent(final String name, final String objective, final Style style, final List<Component> children) {
        final yh contents = new yh(name, objective);
        return (ScoreComponent)this.applyStyleAndSiblings((xa)contents, style, children);
    }
    
    @Override
    public KeybindComponent createKeybindComponent(final String keybind) {
        return (KeybindComponent)((MutableComponentAccessor)wz.d(keybind)).getLabyComponent();
    }
    
    @Override
    public KeybindComponent createKeybindComponent(final String keybind, final Style style, final List<Component> children) {
        final yd contents = new yd(keybind);
        return (KeybindComponent)this.applyStyleAndSiblings((xa)contents, style, children);
    }
    
    @Override
    protected IconComponent createIconComponent(final Icon icon) {
        return (IconComponent)((MutableComponentAccessor)xn.a((xa)new VersionedIconContents(icon))).getLabyComponent();
    }
    
    @Override
    protected IconComponent createIconComponent(final Icon icon, final Style style, final List<Component> children) {
        final VersionedIconContents contents = new VersionedIconContents(icon);
        return (IconComponent)this.applyStyleAndSiblings((xa)contents, style, children);
    }
    
    @Override
    public TextComponent createEmptyComponent() {
        return (TextComponent)((MutableComponentAccessor)wz.i()).getLabyComponent();
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
            return (HoverEvent<T>)new xf(xf.a.a, (Object)component.holder);
        }
        throw new IllegalStateException("Value must be a Component!");
    }
    
    @Override
    public ClickEvent createClickEvent(final ClickEvent.Action action, @NotNull final String value) {
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(value, "Value cannot be null!");
        final wx.a vanillaAction = switch (action) {
            default -> throw new MatchException(null, null);
            case OPEN_URL -> wx.a.a;
            case OPEN_FILE -> wx.a.b;
            case RUN_COMMAND -> wx.a.c;
            case SUGGEST_COMMAND -> wx.a.d;
            case CHANGE_PAGE -> wx.a.e;
            case COPY_TO_CLIPBOARD -> wx.a.f;
        };
        return (ClickEvent)new wx(vanillaAction, value);
    }
    
    @Override
    public TextColor createTextColor(final int value) {
        return (TextColor)xy.a(value);
    }
    
    @Override
    public TextColor getParsedTextColor(final String value) {
        final DataResult<xy> result = (DataResult<xy>)xy.a(value);
        return result.result().get();
    }
    
    @Override
    public TranslatableComponent createTranslatableComponent(final String key, final Component... arguments) {
        return (TranslatableComponent)((MutableComponentAccessor)wz.a(key, (Object[])arguments)).getLabyComponent();
    }
    
    @Override
    public TranslatableComponent createTranslatableComponent(final String key, final Style style, final List<Component> children, final List<Component> arguments) {
        yk contents;
        if (arguments == null) {
            contents = new yk(key, key, new Object[0]);
        }
        else {
            contents = new yk(key, key, (Object[])arguments.toArray(new Component[0]));
        }
        return (TranslatableComponent)this.applyStyleAndSiblings((xa)contents, style, children);
    }
    
    @Override
    public Style getEmptyStyle() {
        xw style = xw.a;
        if (style == null) {
            style = new xw((xy)null, (Boolean)null, (Boolean)null, (Boolean)null, (Boolean)null, (Boolean)null, (wx)null, (xf)null, (String)null, (akr)null);
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
        return (Style)new xw((xy)color, bold, italic, underlined, strikethrough, obfuscated, (wx)clickEvent, (xf)hoverEvent, insertion, (fontLocation == null) ? null : fontLocation.getMinecraftLocation());
    }
    
    @Override
    public Serializer createComponentSerializer() {
        return new VersionedComponentSerializerWrapper();
    }
    
    @Override
    public Serializer createStyleSerializer() {
        return (Serializer)new xw.b();
    }
    
    @Override
    public void applyGsonTypeAdapters(final GsonBuilder builder) {
        builder.registerTypeAdapterFactory((TypeAdapterFactory)new ayl());
    }
    
    private Component applyStyleAndSiblings(final xa componentContents, final Style style, final List<Component> children) {
        xw vanillaStyle;
        if (style == null) {
            vanillaStyle = xw.a;
        }
        else {
            vanillaStyle = (xw)style;
        }
        final xn component = new xn(componentContents, (List)new ArrayList(), vanillaStyle);
        final Component result = ((MutableComponentAccessor)component).getLabyComponent();
        if (children != null) {
            result.setChildren(children);
        }
        return result;
    }
}
