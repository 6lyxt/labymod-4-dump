// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component;

import java.util.ArrayList;
import net.labymod.api.client.component.builder.StyleableBuilder;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.event.ClickEvent;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.builder.Buildable;

public interface Component extends Buildable<Builder<?, ?>>
{
    default TranslatableComponent translatable(final String key, final Component... arguments) {
        return ComponentService.translatableComponent(key, arguments);
    }
    
    default TranslatableComponent translatable(final String key, final TextColor color) {
        return ComponentService.translatableComponent(key, new Component[0]).color(color);
    }
    
    default TranslatableComponent translatable(final String key, final TextColor color, final TextDecoration... decorations) {
        return ((StyleableBuilder<T, TranslatableComponent.Builder>)((StyleableBuilder<T, TranslatableComponent.Builder>)translatable().key(key)).color(color)).decorate(decorations).build();
    }
    
    default TranslatableComponent translatable(final String key, final TextColor color, final Component... arguments) {
        return ((StyleableBuilder<T, TranslatableComponent.Builder>)translatable().key(key).arguments(arguments)).color(color).build();
    }
    
    default TranslatableComponent translatable(final String key, final Style style) {
        return ComponentService.translatableComponent(key, new Component[0]).style(style);
    }
    
    default TranslatableComponent translatable(final String key, final Style style, final Component... arguments) {
        return ComponentService.translatableComponent(key, new Component[0]).style(style).arguments(arguments);
    }
    
    default ScoreComponent score(final String name, final String objective) {
        return ComponentService.scoreComponent(name, objective);
    }
    
    default ScoreComponent score(final String name, final String objective, final TextColor color) {
        return ComponentService.scoreComponent(name, objective).color(color);
    }
    
    default ScoreComponent score(final String name, final String objective, final Style style) {
        return ComponentService.scoreComponent(name, objective).style(style);
    }
    
    default ScoreComponent score(final String name, final String objective, final TextColor color, final TextDecoration... decorations) {
        return ((StyleableBuilder<T, ScoreComponent.Builder>)((StyleableBuilder<T, ScoreComponent.Builder>)ScoreComponent.builder().name(name).objective(objective)).color(color)).decorate(decorations).build();
    }
    
    default KeybindComponent keybind(final String keybind) {
        return ComponentService.keybindComponent(keybind);
    }
    
    default KeybindComponent keybind(final String keybind, final TextColor color) {
        return ComponentService.keybindComponent(keybind).color(color);
    }
    
    default KeybindComponent keybind(final String keybind, final Style style) {
        return ComponentService.keybindComponent(keybind).style(style);
    }
    
    default KeybindComponent keybind(final String keybind, final TextColor color, final TextDecoration... decorations) {
        return ((StyleableBuilder<T, KeybindComponent.Builder>)((StyleableBuilder<T, KeybindComponent.Builder>)KeybindComponent.builder().keybind(keybind)).color(color)).decorate(decorations).build();
    }
    
    default KeybindComponent keybind(final MinecraftInputMapping keybind) {
        return ComponentService.keybindComponent(keybind);
    }
    
    default KeybindComponent keybind(final MinecraftInputMapping keybind, final TextColor color) {
        return ComponentService.keybindComponent(keybind).color(color);
    }
    
    default KeybindComponent keybind(final MinecraftInputMapping keybind, final Style style) {
        return ComponentService.keybindComponent(keybind).style(style);
    }
    
    default KeybindComponent keybind(final MinecraftInputMapping keybind, final TextColor color, final TextDecoration... decorations) {
        return ((StyleableBuilder<T, KeybindComponent.Builder>)((StyleableBuilder<T, KeybindComponent.Builder>)KeybindComponent.builder().keybind(keybind)).color(color)).decorate(decorations).build();
    }
    
    default TextComponent text(final String text) {
        return ComponentService.textComponent(text);
    }
    
    default TextComponent text(final String text, final TextColor color) {
        return ComponentService.textComponent(text).color(color);
    }
    
    default TextComponent text(final String text, final Style style) {
        return ComponentService.textComponent(text).style(style);
    }
    
    default TextComponent text(final String text, final TextColor color, final TextDecoration... decorations) {
        return ((StyleableBuilder<T, TextComponent.Builder>)((StyleableBuilder<T, TextComponent.Builder>)text().text(text)).color(color)).decorate(decorations).build();
    }
    
    default TextComponent text(final Object object) {
        return text(String.valueOf(object));
    }
    
    default TextComponent text(final Object object, final TextColor color) {
        return text(String.valueOf(object), color);
    }
    
    default IconComponent icon(@NotNull final Icon icon) {
        return icon(icon, Style.empty(), 16, "");
    }
    
    default IconComponent icon(@NotNull final Icon icon, final int size) {
        return icon(icon, size, size);
    }
    
    default IconComponent icon(@NotNull final Icon icon, final int width, final int height) {
        return icon(icon, Style.empty(), width, height, "");
    }
    
    default IconComponent icon(@NotNull final Icon icon, @NotNull final Style style) {
        return icon(icon, style, 16, "");
    }
    
    default IconComponent icon(@NotNull final Icon icon, @NotNull final Style style, final int size) {
        return icon(icon, style, size, size);
    }
    
    default IconComponent icon(@NotNull final Icon icon, @NotNull final Style style, final int width, final int height) {
        return icon(icon, style, width, height, "");
    }
    
    default IconComponent icon(@NotNull final Icon icon, @NotNull final Style style, final int size, final String placeholder) {
        return icon(icon, style, size, size, placeholder);
    }
    
    default IconComponent icon(@NotNull final Icon icon, @NotNull final Style style, final int width, final int height, final String placeholder) {
        return ComponentService.iconComponent(icon).setWidth(width).setHeight(height).setPlaceholder(placeholder).style(style);
    }
    
    default TextComponent empty() {
        return ComponentService.empty();
    }
    
    default TextComponent newline() {
        return text("\n");
    }
    
    default TextComponent space() {
        return text(" ");
    }
    
    default TextComponent.Builder text() {
        return TextComponent.builder().text("");
    }
    
    default TranslatableComponent.Builder translatable() {
        return TranslatableComponent.builder();
    }
    
    Style style();
    
    List<Component> getChildren();
    
    Component plainCopy();
    
    Component copy();
    
    Component style(final Style p0);
    
    Component append(final Component p0);
    
    Component append(final int p0, final Component p1);
    
    Component remove(final int p0);
    
    Component replace(final int p0, final Component p1);
    
    Component setChildren(final Collection<Component> p0);
    
    Component clickEvent(final ClickEvent p0);
    
    Component hoverEvent(final HoverEvent<?> p0);
    
    Component color(final TextColor p0);
    
    Component colorIfAbsent(final TextColor p0);
    
    Component decorate(final TextDecoration p0);
    
    Component decorate(final TextDecoration... p0);
    
    Component undecorate(final TextDecoration p0);
    
    Component undecorate(final TextDecoration... p0);
    
    Component unsetDecoration(final TextDecoration p0);
    
    Component unsetDecorations(final TextDecoration... p0);
    
    default TextColor getColor() {
        return this.style().getColor();
    }
    
    @Deprecated
    Component decoration(final TextDecoration p0, final boolean p1);
    
    @Deprecated
    Component children(final List<Component> p0);
    
    @Deprecated
    default List<Component> children() {
        return this.getChildren();
    }
    
    @Deprecated
    default TextColor color() {
        return this.style().getColor();
    }
    
    public abstract static class Builder<T extends Component, B extends Builder<T, B>> extends StyleableBuilder<T, B>
    {
        protected List<Component> children;
        
        protected Builder() {
        }
        
        protected Builder(final T component) {
            super(component.style());
            final List<Component> children = component.getChildren();
            if (!children.isEmpty()) {
                this.children = new ArrayList<Component>(children);
            }
        }
        
        public B style(final Style style) {
            return this.applyStyle(style);
        }
        
        public B append(final Component component) {
            if (this.children == null) {
                this.children = new ArrayList<Component>();
            }
            this.children.add(component);
            return (B)this;
        }
        
        public B append(final Collection<Component> components) {
            if (this.children == null) {
                this.children = new ArrayList<Component>();
            }
            this.children.addAll(components);
            return (B)this;
        }
        
        public B append(final String text) {
            return this.append(Component.text(text));
        }
    }
}
