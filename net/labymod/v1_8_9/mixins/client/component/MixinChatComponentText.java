// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.component;

import net.labymod.api.client.component.Component;
import java.util.Objects;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.TextComponent;

@Mixin({ fa.class })
public abstract class MixinChatComponentText extends MixinChatComponentStyle<TextComponent> implements TextComponent
{
    @Shadow
    @Final
    @Mutable
    private String b;
    
    @Override
    public String getText() {
        return this.b;
    }
    
    @Override
    public TextComponent text(final String text) {
        this.b = text;
        return this;
    }
    
    @Override
    public TextComponent plainCopy() {
        return (TextComponent)new fa(this.b);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.b(), this.a, this.b);
    }
}
