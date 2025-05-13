// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component;

import java.util.List;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.event.ClickEvent;
import java.util.Collection;
import net.labymod.api.client.component.format.Style;

public interface BaseComponent<T extends Component> extends Component
{
    T plainCopy();
    
    T copy();
    
    T style(final Style p0);
    
    T append(final Component p0);
    
    T append(final int p0, final Component p1);
    
    Component remove(final int p0);
    
    Component replace(final int p0, final Component p1);
    
    T setChildren(final Collection<Component> p0);
    
    default T clickEvent(final ClickEvent clickEvent) {
        return this.style(this.style().clickEvent(clickEvent));
    }
    
    default T hoverEvent(final HoverEvent<?> hoverEvent) {
        return this.style(this.style().hoverEvent(hoverEvent));
    }
    
    default T color(final TextColor color) {
        return this.style(this.style().color(color));
    }
    
    default T colorIfAbsent(final TextColor color) {
        return this.style(this.style().colorIfAbsent(color));
    }
    
    default T decorate(final TextDecoration decoration) {
        return this.style(this.style().decorate(decoration));
    }
    
    default T decorate(final TextDecoration... decorations) {
        return this.style(this.style().decorate(decorations));
    }
    
    default T undecorate(final TextDecoration decoration) {
        return this.style(this.style().undecorate(decoration));
    }
    
    default T undecorate(final TextDecoration... decorations) {
        return this.style(this.style().undecorate(decorations));
    }
    
    default T unsetDecoration(final TextDecoration decoration) {
        return this.style(this.style().unsetDecoration(decoration));
    }
    
    default T unsetDecorations(final TextDecoration... decorations) {
        return this.style(this.style().unsetDecorations(decorations));
    }
    
    @Deprecated
    default T decoration(final TextDecoration decoration, final boolean flag) {
        return this.style(this.style().decoration(decoration, flag));
    }
    
    @Deprecated
    default T children(final List<Component> children) {
        return this.setChildren(children);
    }
}
