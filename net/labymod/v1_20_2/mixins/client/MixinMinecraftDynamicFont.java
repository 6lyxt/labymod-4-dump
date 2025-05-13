// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.v1_20_2.client.font.DynamicFont;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eqv.class })
public class MixinMinecraftDynamicFont
{
    @Mutable
    @Shadow
    @Final
    public esd h;
    @Mutable
    @Shadow
    @Final
    public esd i;
    
    @Insert(method = { "<init>" }, at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/gui/font/FontManager;createFontFilterFishy()Lnet/minecraft/client/gui/Font;", shift = At.Shift.AFTER))
    private void labyMod$dynamicFontRenderer(final fdw config, final InsertInfo ii) {
        this.h = new DynamicFont(this.h);
        this.i = new DynamicFont(this.i);
    }
}
