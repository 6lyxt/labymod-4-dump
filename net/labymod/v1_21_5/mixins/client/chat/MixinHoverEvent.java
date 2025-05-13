// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.chat;

import net.labymod.v1_21_5.client.network.chat.MutableComponentAccessor;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.event.HoverEvent;

@Mixin({ xm.class })
@Implements({ @Interface(iface = HoverEvent.class, prefix = "hoverEvent$", remap = Interface.Remap.NONE) })
public interface MixinHoverEvent extends HoverEvent
{
    @Shadow
    xm.a shadow$a();
    
    default Object value() {
        final Object obj = this;
        Label_0033: {
            if (!(obj instanceof xm.e)) {
                break Label_0033;
            }
            final xm.e e = (xm.e)obj;
            try {
                final xg text = e.b();
                return ((MutableComponentAccessor)text).getLabyComponent();
                throw new IllegalStateException("Unknown value type: " + obj.getClass().getName());
            }
            catch (final Throwable cause) {
                throw new MatchException(cause.toString(), cause);
            }
        }
    }
    
    default Action<?> action() {
        if (this.shadow$a() == xm.a.a) {
            return Action.SHOW_TEXT;
        }
        return null;
    }
}
