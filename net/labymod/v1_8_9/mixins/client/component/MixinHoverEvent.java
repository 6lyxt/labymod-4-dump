// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.component;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.event.HoverEvent;

@Mixin({ ew.class })
public abstract class MixinHoverEvent implements HoverEvent
{
    @Shadow
    public abstract ew.a a();
    
    @Shadow
    public abstract eu b();
    
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
