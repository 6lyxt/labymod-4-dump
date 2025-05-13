// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.chat;

import net.labymod.v1_19_2.client.network.chat.MutableComponentAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.event.HoverEvent;

@Mixin({ rv.class })
@Implements({ @Interface(iface = HoverEvent.class, prefix = "hoverEvent$", remap = Interface.Remap.NONE) })
public abstract class MixinHoverEvent implements HoverEvent
{
    @Shadow
    @Final
    private rv.a<?> b;
    @Shadow
    @Final
    private Object c;
    
    @Override
    public Object value() {
        final Object value = this.c;
        if (value instanceof final MutableComponentAccessor component) {
            return component.getLabyComponent();
        }
        if (value instanceof final rv.c itemStackInfo) {
            return itemStackInfo.a();
        }
        if (value instanceof final rv.b entityTooltipInfo) {
            return entityTooltipInfo.b;
        }
        return value;
    }
    
    @Override
    public Action action() {
        if (this.b == rv.a.a) {
            return Action.SHOW_TEXT;
        }
        if (this.b == rv.a.b) {
            return Action.SHOW_ITEM;
        }
        if (this.b == rv.a.c) {
            return Action.SHOW_ENTITY;
        }
        return null;
    }
}
