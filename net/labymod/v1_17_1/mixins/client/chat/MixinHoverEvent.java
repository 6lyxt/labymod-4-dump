// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.chat;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.event.HoverEvent;

@Mixin({ ow.class })
@Implements({ @Interface(iface = HoverEvent.class, prefix = "hoverEvent$", remap = Interface.Remap.NONE) })
public abstract class MixinHoverEvent implements HoverEvent
{
    @Shadow
    public abstract ow.a<?> a();
    
    @Shadow
    @Nullable
    public abstract <T> T a(final ow.a<T> p0);
    
    @Override
    public Object value() {
        return this.a(this.a());
    }
    
    @Override
    public Action action() {
        final ow.a<?> action = this.a();
        if (action == ow.a.a) {
            return Action.SHOW_TEXT;
        }
        return null;
    }
}
