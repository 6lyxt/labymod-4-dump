// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.chat;

import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.event.ClickEvent;

@Mixin({ np.class })
@Implements({ @Interface(iface = ClickEvent.class, prefix = "clickEvent$", remap = Interface.Remap.NONE) })
public abstract class MixinClickEvent implements ClickEvent
{
    @Shadow
    public abstract np.a a();
    
    @Shadow
    public abstract String shadow$b();
    
    @Override
    public Action action() {
        switch (this.a()) {
            case a: {
                return Action.OPEN_URL;
            }
            case b: {
                return Action.OPEN_FILE;
            }
            case c: {
                return Action.RUN_COMMAND;
            }
            case d: {
                return Action.SUGGEST_COMMAND;
            }
            case e: {
                return Action.CHANGE_PAGE;
            }
            case f: {
                return Action.COPY_TO_CLIPBOARD;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this.a()));
            }
        }
    }
    
    @Intrinsic
    public String clickEvent$getValue() {
        return this.shadow$b();
    }
}
