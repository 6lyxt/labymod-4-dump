// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.component;

import org.spongepowered.asm.mixin.Overwrite;
import java.util.Objects;
import java.util.Collection;
import java.util.Iterator;
import java.util.Collections;
import net.labymod.api.client.component.format.Style;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.Component;

@Mixin({ he.class })
public abstract class MixinChatComponentStyle<T extends Component> implements BaseComponent<T>
{
    @Shadow
    protected List<hh> a;
    @Shadow
    private hn b;
    
    @Shadow
    public abstract hh a(final hn p0);
    
    @Shadow
    public abstract hh a(final hh p0);
    
    @Shadow
    public abstract hn b();
    
    @Override
    public Style style() {
        return (Style)this.b();
    }
    
    @Override
    public List<Component> getChildren() {
        return Collections.unmodifiableList((List<? extends Component>)this.a);
    }
    
    @Override
    public T copy() {
        final T t = this.plainCopy();
        t.style(this.style());
        for (final hh sibling : this.a) {
            t.append(((Component)sibling).copy());
        }
        return t;
    }
    
    @Override
    public T style(final Style style) {
        return (T)this.a((hn)style);
    }
    
    @Override
    public T append(final Component component) {
        return (T)this.a((hh)component);
    }
    
    @Override
    public T append(final int index, final Component component) {
        ((hh)component).b().a(this.b());
        this.a.add(index, (hh)component);
        return (T)this;
    }
    
    @Override
    public Component remove(final int index) {
        this.a.remove(index);
        return this;
    }
    
    @Override
    public Component replace(final int index, final Component component) {
        ((hh)component).b().a(this.b());
        this.a.set(index, (hh)component);
        return this;
    }
    
    @Override
    public T setChildren(final Collection<Component> children) {
        this.a.clear();
        this.a.addAll((Collection<? extends hh>)children);
        return (T)this;
    }
    
    @Overwrite
    @Override
    public int hashCode() {
        return Objects.hash(this.a, this.b);
    }
}
