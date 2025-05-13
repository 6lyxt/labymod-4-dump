// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.chat;

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

@Mixin({ oo.class })
@Implements({ @Interface(iface = BaseComponent.class, prefix = "component$", remap = Interface.Remap.NONE) })
public abstract class MixinBaseComponent<T extends Component> implements BaseComponent<T>
{
    @Shadow
    private pc f;
    @Shadow
    @Final
    protected List<os> a;
    
    @Shadow
    public abstract oy a(final pc p0);
    
    @Shadow
    public abstract oy shadow$a(final os p0);
    
    @Shadow
    public abstract oy shadow$e();
    
    @Shadow
    public abstract pc c();
    
    @Override
    public Style style() {
        return (Style)this.f;
    }
    
    @Override
    public T style(final Style style) {
        return (T)this.a((pc)style);
    }
    
    @Override
    public List<Component> getChildren() {
        return Collections.unmodifiableList((List<? extends Component>)this.a);
    }
    
    @Override
    public T setChildren(final Collection<Component> children) {
        this.a.clear();
        this.a.addAll((Collection<? extends os>)children);
        return (T)this;
    }
    
    @Override
    public T append(final int index, final Component component) {
        this.a.add(index, (os)component);
        return (T)this;
    }
    
    @Override
    public Component remove(final int index) {
        this.a.remove(index);
        return this;
    }
    
    @Override
    public Component replace(final int index, final Component component) {
        this.a.set(index, (os)component);
        return this;
    }
    
    @Intrinsic
    public T component$copy() {
        return (T)this.shadow$e();
    }
    
    @Intrinsic
    public T component$append(final Component component) {
        return (T)this.shadow$a((os)component);
    }
    
    @Redirect(method = { "copy" }, at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z"))
    public boolean labyMod$copyChildren(final List<os> instance, final Collection<os> children) {
        for (final os child : children) {
            instance.add((os)child.e());
        }
        return true;
    }
}
