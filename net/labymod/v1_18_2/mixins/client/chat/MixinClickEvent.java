// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.chat;

import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.event.ClickEvent;

@Mixin({ qi.class })
@Implements({ @Interface(iface = ClickEvent.class, prefix = "clickEvent$", remap = Interface.Remap.NONE) })
public abstract class MixinClickEvent implements ClickEvent
{
    @Shadow
    public abstract qi.a a();
    
    @Shadow
    public abstract String shadow$b();
    
    @Override
    public Action action() {
        return switch (this.a()) {
            default -> throw new MatchException(null, null);
            case a -> Action.OPEN_URL;
            case b -> Action.OPEN_FILE;
            case c -> Action.RUN_COMMAND;
            case d -> Action.SUGGEST_COMMAND;
            case e -> Action.CHANGE_PAGE;
            case f -> Action.COPY_TO_CLIPBOARD;
        };
    }
    
    @Intrinsic
    public String clickEvent$getValue() {
        return this.shadow$b();
    }
}
