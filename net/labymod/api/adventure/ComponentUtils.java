// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.adventure;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.Component;

@Deprecated
public class ComponentUtils
{
    public static Component mergeStyleRecursive(Component component, final Style style, final Style.Merge.Strategy strategy) {
        final Style mergedStyle = component.style().merge(style, strategy);
        component = component.style(mergedStyle);
        final List<Component> oldChildren = component.children();
        final List<Component> children = new ArrayList<Component>(oldChildren.size());
        for (final Component child : oldChildren) {
            children.add(mergeStyleRecursive(child, mergedStyle, strategy));
        }
        return component.children(children);
    }
}
