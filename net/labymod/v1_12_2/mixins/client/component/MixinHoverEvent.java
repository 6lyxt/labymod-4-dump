// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.component;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.event.HoverEvent;

@Mixin({ hj.class })
public abstract class MixinHoverEvent implements HoverEvent
{
    @Shadow
    public abstract hj.a a();
    
    @Shadow
    public abstract hh b();
    
    @Override
    public Action action() {
        switch (this.a()) {
            case a: {
                return Action.SHOW_TEXT;
            }
            default: {
                return null;
            }
        }
    }
    
    @Override
    public Object value() {
        return this.b();
    }
}
