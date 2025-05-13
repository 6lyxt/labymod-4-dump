// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.network.chat;

import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.Component;

public abstract class VersionedBaseComponent<T extends Component, C> implements BaseComponent<T>
{
    public final yj holder;
    
    public VersionedBaseComponent(final yj holder) {
        this.holder = holder;
    }
    
    @Override
    public Style style() {
        return (Style)this.holder.a();
    }
    
    @Override
    public List<Component> getChildren() {
        return ((MutableComponentAccessor)this.holder).getChildren();
    }
    
    @Override
    public T copy() {
        return (T)((MutableComponentAccessor)this.holder.f()).getLabyComponent();
    }
    
    @Override
    public T style(final Style style) {
        this.holder.b((ys)style);
        return (T)this;
    }
    
    @Override
    public T append(final Component component) {
        this.holder.b((xv)((VersionedBaseComponent)component).holder);
        return (T)this;
    }
    
    @Override
    public T append(final int index, final Component component) {
        this.holder.c().add(index, ((VersionedBaseComponent)component).holder);
        return (T)this;
    }
    
    @Override
    public Component remove(final int index) {
        this.holder.c().remove(index);
        return this;
    }
    
    @Override
    public Component replace(final int index, final Component component) {
        this.holder.c().set(index, ((VersionedBaseComponent)component).holder);
        return this;
    }
    
    @Override
    public T setChildren(final Collection<Component> children) {
        this.holder.c().clear();
        for (final Component child : children) {
            if (child instanceof VersionedBaseComponent) {
                final VersionedBaseComponent<?, ?> versionedChild = (VersionedBaseComponent<?, ?>)child;
                this.holder.b((xv)versionedChild.holder);
            }
        }
        return (T)this;
    }
    
    public C getContents() {
        return (C)this.holder.b();
    }
    
    public yj getHolder() {
        return this.holder;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof VersionedBaseComponent)) {
            return obj instanceof yj && this.holder.equals(obj);
        }
        return ((VersionedBaseComponent)obj).holder.equals((Object)this.holder);
    }
    
    @Override
    public int hashCode() {
        return this.holder.hashCode();
    }
    
    @Override
    public String toString() {
        return this.holder.toString();
    }
}
