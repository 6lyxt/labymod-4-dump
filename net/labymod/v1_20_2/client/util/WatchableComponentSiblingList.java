// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.util;

import net.labymod.v1_20_2.client.network.chat.MutableComponentAccessor;
import net.labymod.api.client.component.Component;
import java.util.List;
import net.labymod.core.watcher.list.WatchableList;

public class WatchableComponentSiblingList implements WatchableList<tl>
{
    private final List<Component> children;
    
    public WatchableComponentSiblingList(final List<Component> children) {
        this.children = children;
    }
    
    @Override
    public void onAdd(final tl component) {
        if (component instanceof final MutableComponentAccessor accessor) {
            this.children.add(accessor.getLabyComponent());
        }
    }
    
    @Override
    public void onAdd(final int index, final tl component) {
        if (component instanceof final MutableComponentAccessor accessor) {
            this.children.add(index, accessor.getLabyComponent());
        }
    }
    
    @Override
    public void onRemove(final tl component) {
        if (component instanceof final MutableComponentAccessor accessor) {
            this.children.remove(accessor.getLabyComponent());
        }
    }
    
    @Override
    public void onClear() {
        this.children.clear();
    }
}
