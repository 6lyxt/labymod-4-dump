// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Dynamic;
import java.io.IOException;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.function.Consumer;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_20_5.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Shadow;
import java.io.File;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_5.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import net.labymod.api.mixin.dynamic.DynamicMixin;

@DynamicMixin(value = "optifine", applier = OptiFineDynamicMixinApplier.class)
@Mixin({ ffq.class })
public abstract class MixinOptiFineScreenshot
{
    @Shadow
    private static File a(final File file) {
        return null;
    }
    
    @Redirect(method = { "_grab" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Screenshot;getFile(Ljava/io/File;)Ljava/io/File;"))
    private static File labyMod$getFile(final File directory) {
        return MinecraftUtil.getLastFileGrab();
    }
    
    @Inject(method = { "_grab" }, at = { @At("HEAD") })
    private static void labyMod$_grab(final File mcHome, final String customName, final eyl renderTarget, final Consumer<xp> messageCallback, final CallbackInfo ci) {
        MinecraftUtil.grabFile(mcHome, customName, MixinOptiFineScreenshot::a);
    }
    
    @Redirect(method = { "lambda$saveScreenshotRaw$2(Lcom/mojang/blaze3d/platform/NativeImage;Ljava/io/File;Ljava/util/function/Consumer;)V" }, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/NativeImage;writeToFile(Ljava/io/File;)V"))
    @Dynamic
    private static <T> void labyMod$writeToFile(final eza image, final File file) throws IOException {
        MinecraftUtil.exportScreenshotToFile(image, file);
    }
}
