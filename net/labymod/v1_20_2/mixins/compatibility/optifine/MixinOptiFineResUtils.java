// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.v1_20_2.client.resources.pack.PackUtil;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@DynamicMixin("optifine")
@Mixin(targets = { "net.optifine.util.ResUtils" })
public class MixinOptiFineResUtils
{
    @ModifyVariable(method = { "collectFiles(Lnet/minecraft/server/packs/PackResources;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)[Ljava/lang/String;" }, at = @At("HEAD"), argsOnly = true)
    @Dynamic
    private static amh labyMod$unwrapPackResources(final amh resources) {
        return PackUtil.unwrap(resources);
    }
}
