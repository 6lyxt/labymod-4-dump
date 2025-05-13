// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.chat;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.Iterator;
import org.spongepowered.asm.mixin.Intrinsic;
import java.util.Collection;
import java.util.Collections;
import net.labymod.api.client.component.format.Style;
import org.spongepowered.asm.mixin.Final;
import java.util.List;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.Component;

@Mixin({ nn.class })
@Implements({ @Interface(iface = BaseComponent.class, prefix = "component$", remap = Interface.Remap.NONE) })
public abstract class MixinBaseComponent<T extends Component> implements BaseComponent<T>
{
    @Shadow
    private ob f;
    @Shadow
    @Final
    protected List<nr> a;
    
    @Shadow
    public abstract nx a(final ob p0);
    
    @Shadow
    public abstract nx shadow$a(final nr p0);
    
    @Shadow
    public abstract nx shadow$e();
    
    @Shadow
    public abstract ob c();
    
    @Override
    public Style style() {
        return (Style)this.f;
    }
    
    @Override
    public T style(final Style style) {
        return (T)this.a((ob)style);
    }
    
    @Override
    public List<Component> getChildren() {
        return Collections.unmodifiableList((List<? extends Component>)this.a);
    }
    
    @Override
    public T setChildren(final Collection<Component> children) {
        this.a.clear();
        this.a.addAll((Collection<? extends nr>)children);
        return (T)this;
    }
    
    @Override
    public T append(final int index, final Component component) {
        this.a.add(index, (nr)component);
        return (T)this;
    }
    
    @Override
    public Component remove(final int index) {
        this.a.remove(index);
        return this;
    }
    
    @Override
    public Component replace(final int index, final Component component) {
        this.a.set(index, (nr)component);
        return this;
    }
    
    @Intrinsic
    public T component$copy() {
        return (T)this.shadow$e();
    }
    
    @Intrinsic
    public T component$append(final Component component) {
        return (T)this.shadow$a((nr)component);
    }
    
    @Redirect(method = { "copy" }, at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z"))
    public boolean labyMod$copyChildren(final List<nr> instance, final Collection<nr> children) {
        for (final nr child : children) {
            instance.add((nr)child.e());
        }
        return true;
    }
}
