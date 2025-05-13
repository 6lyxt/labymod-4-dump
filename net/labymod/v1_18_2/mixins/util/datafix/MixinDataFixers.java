// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.util.datafix;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_18_2.client.util.datafix.FastDataFixerBuilder;
import com.mojang.datafixers.DataFixerBuilder;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ake.class })
public abstract class MixinDataFixers
{
    @Redirect(method = { "createFixerUpper" }, at = @At(remap = false, value = "NEW", target = "com/mojang/datafixers/DataFixerBuilder"))
    private static DataFixerBuilder labyMod$useFastDataFixBuilder(final int dataVersion) {
        return new FastDataFixerBuilder();
    }
}
