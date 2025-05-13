// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.chat;

import java.util.Objects;
import net.labymod.api.client.component.Component;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.KeybindComponent;

@Mixin({ qp.class })
@Implements({ @Interface(iface = KeybindComponent.class, prefix = "keybindComponent$", remap = Interface.Remap.NONE) })
public abstract class MixinKeybindComponent extends MixinBaseComponent<KeybindComponent> implements KeybindComponent
{
    @Shadow
    @Final
    @Mutable
    private String e;
    
    @Shadow
    public abstract qp shadow$h();
    
    @Shadow
    public abstract String shadow$i();
    
    @Shadow
    protected abstract qk j();
    
    @Override
    public KeybindComponent keybind(final String keybind) {
        this.e = keybind;
        return this;
    }
    
    @Intrinsic
    public String keybindComponent$getKeybind() {
        return this.shadow$i();
    }
    
    @Intrinsic
    public KeybindComponent keybindComponent$plainCopy() {
        return (KeybindComponent)this.shadow$h();
    }
    
    @Override
    public Component resolveKeybind() {
        return (Component)this.j();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.c(), this.a, this.e);
    }
}
