// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component;

import java.util.List;

public class ComponentUtil
{
    private ComponentUtil() {
    }
    
    public static Component join(final Component... components) {
        if (components.length == 0) {
            return Component.empty();
        }
        if (components.length == 1) {
            return components[0];
        }
        final Component joined = Component.empty();
        for (int i = 0; i < components.length; ++i) {
            if (i != 0) {
                if (i == components.length - 1) {
                    joined.append(Component.text(" ")).append(Component.translatable("labymod.misc.and", new Component[0])).append(Component.text(" "));
                }
                else {
                    joined.append(Component.text(",")).append(Component.text(" "));
                }
            }
            joined.append(components[i]);
        }
        return joined;
    }
    
    public static Component join(final List<Component> components) {
        final int size = components.size();
        if (size == 0) {
            return Component.empty();
        }
        if (size == 1) {
            return components.get(0);
        }
        final Component joined = Component.empty();
        for (int i = 0; i < size; ++i) {
            if (i != 0) {
                if (i == size - 1) {
                    joined.append(Component.text(" ")).append(Component.translatable("labymod.misc.and", new Component[0])).append(Component.text(" "));
                }
                else {
                    joined.append(Component.text(",")).append(Component.text(" "));
                }
            }
            joined.append(components.get(i));
        }
        return joined;
    }
}
