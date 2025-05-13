// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.chat;

import java.util.Objects;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.TextComponent;

@Mixin({ qx.class })
@Implements({ @Interface(iface = TextComponent.class, prefix = "textComponent$", remap = Interface.Remap.NONE) })
public abstract class MixinTextComponent extends MixinBaseComponent<TextComponent> implements TextComponent
{
    @Shadow
    @Final
    @Mutable
    private String e;
    
    @Shadow
    public abstract qx shadow$i();
    
    @Shadow
    public abstract String shadow$h();
    
    @Override
    public TextComponent text(final String text) {
        this.e = text;
        return this;
    }
    
    @Intrinsic
    public String textComponent$getText() {
        return this.shadow$h();
    }
    
    @Intrinsic
    public TextComponent textComponent$plainCopy() {
        return (TextComponent)this.shadow$i();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.c(), this.a, this.e);
    }
}
