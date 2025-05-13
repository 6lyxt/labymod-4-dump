// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.TextComponent;
import java.util.List;
import net.labymod.api.client.component.Component;

public class ComponentUtil
{
    public static final Component COMMA;
    public static final Component AND;
    
    public static Component join(final List<Component> elements) {
        return join(ComponentUtil.COMMA, ComponentUtil.AND, elements);
    }
    
    public static Component join(final Component delimiter, final List<Component> elements) {
        return join(delimiter, delimiter, elements);
    }
    
    public static Component join(final Component delimiter, final Component lastDelimiter, final List<Component> elements) {
        if (elements.isEmpty()) {
            return Component.empty();
        }
        if (elements.size() == 1) {
            return elements.get(0);
        }
        final Component joined = Component.empty();
        for (int i = 0; i < elements.size(); ++i) {
            if (i != 0) {
                if (i == elements.size() - 1) {
                    joined.append(lastDelimiter);
                }
                else {
                    joined.append(delimiter);
                }
            }
            joined.append(elements.get(i));
        }
        return joined;
    }
    
    static {
        COMMA = Component.text(", ");
        AND = ((BaseComponent<Component>)Component.space().append(Component.translatable("labymod.misc.and", new Component[0]))).append(Component.space());
    }
}
