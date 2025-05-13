// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.v1_16_5.client.font.DynamicFont;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ djz.class })
public class MixinMinecraftDynamicFont
{
    @Mutable
    @Shadow
    @Final
    public dku g;
    
    @Insert(method = { "<init>" }, at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/gui/font/FontManager;createFont()Lnet/minecraft/client/gui/Font;", shift = At.Shift.AFTER))
    private void labyMod$dynamicFontRenderer(final dsz config, final InsertInfo ii) {
        this.g = new DynamicFont(this.g);
    }
}
