// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.chat;

import net.labymod.v1_19_3.client.network.chat.MutableComponentAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.event.HoverEvent;

@Mixin({ sx.class })
@Implements({ @Interface(iface = HoverEvent.class, prefix = "hoverEvent$", remap = Interface.Remap.NONE) })
public abstract class MixinHoverEvent implements HoverEvent
{
    @Shadow
    @Final
    private sx.a<?> b;
    @Shadow
    @Final
    private Object c;
    
    @Override
    public Object value() {
        final Object value = this.c;
        if (value instanceof final MutableComponentAccessor component) {
            return component.getLabyComponent();
        }
        throw new IllegalStateException("Unknown value type: " + value.getClass().getName());
    }
    
    @Override
    public Action action() {
        if (this.b == sx.a.a) {
            return Action.SHOW_TEXT;
        }
        return null;
    }
}
