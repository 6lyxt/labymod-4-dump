// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.component;

import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.v1_8_9.client.component.VersionedClickEvent;

@Mixin({ et.class })
@Implements({ @Interface(iface = ClickEvent.class, prefix = "clickEvent$", remap = Interface.Remap.NONE) })
public abstract class MixinClickEvent implements VersionedClickEvent, ClickEvent
{
    private Action labyMod$action;
    
    @Override
    public void labyMod$setAction(final Action action) {
        this.labyMod$action = action;
    }
    
    @Shadow
    public abstract et.a a();
    
    @Shadow
    public abstract String shadow$b();
    
    @Override
    public Action action() {
        if (this.labyMod$action != null) {
            return this.labyMod$action;
        }
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
            case f: {
                return Action.CHANGE_PAGE;
            }
            default: {
                return Action.SUGGEST_COMMAND;
            }
        }
    }
    
    @Intrinsic
    public String clickEvent$getValue() {
        return this.shadow$b();
    }
}
