// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.v1_17_1.client.font.DynamicFont;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dvp.class })
public class MixinMinecraftDynamicFont
{
    @Mutable
    @Shadow
    @Final
    public dwl h;
    
    @Insert(method = { "<init>" }, at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/gui/font/FontManager;createFont()Lnet/minecraft/client/gui/Font;", shift = At.Shift.AFTER))
    private void labyMod$dynamicFontRenderer(final eey config, final InsertInfo ii) {
        this.h = new DynamicFont(this.h);
    }
}
