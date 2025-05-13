// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.client.chat;

import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.util.Color;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.format.TextColor;

@Mixin({ xy.class })
@Implements({ @Interface(iface = TextColor.class, prefix = "textColor$", remap = Interface.Remap.NONE) })
public abstract class MixinTextColor implements TextColor
{
    private Color labyMod$color;
    
    @Shadow
    public abstract int shadow$a();
    
    @Shadow
    public abstract String shadow$b();
    
    @Intrinsic
    public int textColor$getValue() {
        return this.shadow$a();
    }
    
    @Intrinsic
    public String textColor$serialize() {
        return this.shadow$b();
    }
    
    @Override
    public Color color() {
        if (this.labyMod$color == null) {
            this.labyMod$color = Color.of(this.textColor$getValue());
        }
        return this.labyMod$color;
    }
}
