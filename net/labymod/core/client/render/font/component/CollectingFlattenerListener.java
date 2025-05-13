// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.component;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.client.component.format.Style;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import net.labymod.api.client.component.Component;
import java.util.List;
import net.labymod.api.client.component.flattener.FlattenerListener;

public class CollectingFlattenerListener implements FlattenerListener
{
    private final List<Component> components;
    private final List<Component> styleQueue;
    
    public CollectingFlattenerListener() {
        this.components = new ArrayList<Component>();
        this.styleQueue = new ArrayList<Component>();
    }
    
    public List<Component> getComponents() {
        return this.components;
    }
    
    @Override
    public void push(@NotNull final Component source) {
        this.styleQueue.add(source);
    }
    
    @Override
    public void component(@NotNull final String text) {
        Style mergedStyle = Style.empty();
        for (int i = this.styleQueue.size() - 1; i >= 0; --i) {
            mergedStyle = mergedStyle.merge(this.styleQueue.get(i).style(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
        }
        final Component lastComponent = this.styleQueue.isEmpty() ? null : this.styleQueue.get(this.styleQueue.size() - 1);
        if (lastComponent instanceof final IconComponent icon) {
            this.components.add(Component.icon(icon.getIcon(), mergedStyle, icon.getWidth(), icon.getHeight(), icon.getPlaceholder()));
        }
        else {
            this.components.add(((BaseComponent<Component>)Component.text(text)).style(mergedStyle));
        }
    }
    
    @Override
    public void pop(@NotNull final Component source) {
        this.styleQueue.remove(source);
    }
}
