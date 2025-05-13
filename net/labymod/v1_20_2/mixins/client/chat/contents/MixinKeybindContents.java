// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.chat.contents;

import net.labymod.v1_20_2.client.network.chat.MutableComponentAccessor;
import net.labymod.api.client.component.Component;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_2.client.network.chat.contents.KeybindContentsAccessor;

@Mixin({ uo.class })
public abstract class MixinKeybindContents implements KeybindContentsAccessor
{
    @Final
    @Shadow
    @Mutable
    private String b;
    
    @Shadow
    protected abstract tl b();
    
    @Override
    public void setKeybind(final String keybind) {
        this.b = keybind;
    }
    
    @Override
    public Component resolveKeybind() {
        return ((MutableComponentAccessor)this.b()).getLabyComponent();
    }
}
