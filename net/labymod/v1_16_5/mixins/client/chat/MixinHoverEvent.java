// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.chat;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.event.HoverEvent;

@Mixin({ nv.class })
@Implements({ @Interface(iface = HoverEvent.class, prefix = "hoverEvent$", remap = Interface.Remap.NONE) })
public abstract class MixinHoverEvent implements HoverEvent
{
    @Shadow
    public abstract nv.a<?> a();
    
    @Shadow
    @Nullable
    public abstract <T> T a(final nv.a<T> p0);
    
    @Override
    public Object value() {
        return this.a(this.a());
    }
    
    @Override
    public Action action() {
        final nv.a<?> action = this.a();
        if (action == nv.a.a) {
            return Action.SHOW_TEXT;
        }
        return null;
    }
}
