// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.component;

import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.Component;
import java.util.function.Supplier;
import net.labymod.v1_12_2.client.component.VersionedKeybindComponent;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.KeybindComponent;

@Mixin({ VersionedKeybindComponent.class })
public abstract class MixinKeybindComponent extends MixinChatComponentStyle<KeybindComponent> implements KeybindComponent
{
    private String resolvedText;
    private Supplier<Component> resolvedKeybind;
    
    @NotNull
    @Override
    public String getKeybind() {
        return ((TextComponent)this).getText();
    }
    
    @NotNull
    @Override
    public KeybindComponent keybind(@NotNull final String keybind) {
        ((TextComponent)this).text(keybind);
        return this;
    }
    
    @Override
    public KeybindComponent plainCopy() {
        return (KeybindComponent)new VersionedKeybindComponent(this.getKeybind());
    }
    
    @Override
    public Component resolveKeybind() {
        final String keybind = this.getKeybind();
        if (!keybind.equals(this.resolvedText) || this.resolvedKeybind == null) {
            final Function<String, Supplier<Component>> keyResolver = VersionedKeybindComponent.getKeyResolver();
            final String resolvedText = keybind;
            this.resolvedText = resolvedText;
            this.resolvedKeybind = keyResolver.apply(resolvedText);
        }
        return this.resolvedKeybind.get();
    }
}
