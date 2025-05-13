// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client.chat;

import net.labymod.v1_21.client.network.chat.MutableComponentAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.event.HoverEvent;

@Mixin({ xf.class })
@Implements({ @Interface(iface = HoverEvent.class, prefix = "hoverEvent$", remap = Interface.Remap.NONE) })
public abstract class MixinHoverEvent implements HoverEvent
{
    @Shadow
    @Final
    private xf.e<?> b;
    
    @Override
    public Object value() {
        final Object value = this.b.b();
        if (value instanceof final MutableComponentAccessor component) {
            return component.getLabyComponent();
        }
        throw new IllegalStateException("Unknown value type: " + value.getClass().getName());
    }
    
    @Override
    public Action action() {
        if (this.b.a() == xf.a.a) {
            return Action.SHOW_TEXT;
        }
        return null;
    }
}
