// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.client.chat.contents;

import net.labymod.v1_21_1.client.network.chat.MutableComponentAccessor;
import net.labymod.api.client.component.Component;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_1.client.network.chat.contents.KeybindContentsAccessor;

@Mixin({ yd.class })
public abstract class MixinKeybindContents implements KeybindContentsAccessor
{
    @Final
    @Shadow
    @Mutable
    private String c;
    
    @Shadow
    protected abstract wz c();
    
    @Override
    public void setKeybind(final String keybind) {
        this.c = keybind;
    }
    
    @Override
    public Component resolveKeybind() {
        return ((MutableComponentAccessor)this.c()).getLabyComponent();
    }
}
