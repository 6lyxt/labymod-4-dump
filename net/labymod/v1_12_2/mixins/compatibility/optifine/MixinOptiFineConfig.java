// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.models.OperatingSystem;
import java.net.URI;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.DisplayModeConsumer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.lwjgl.opengl.DisplayMode;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@DynamicMixin("optifine")
@Mixin(targets = { "Config" }, remap = false)
public class MixinOptiFineConfig
{
    @Shadow
    @Final
    @Mutable
    private static DisplayMode desktopDisplayMode;
    
    @Inject(method = { "initGameSettings" }, at = { @At("TAIL") })
    @Dynamic
    private static void labyMod$consumeDisplayMod(final bid settings, final CallbackInfo ci) {
        DisplayModeConsumer.setDisplayModeConsumer(displayMode -> MixinOptiFineConfig.desktopDisplayMode = displayMode);
    }
    
    @Inject(method = { "isFastRender" }, at = { @At("TAIL") }, cancellable = true)
    @Dynamic
    private static void labyMod$disableFastRender(final CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue((Object)false);
    }
    
    @Inject(method = { "isDynamicFov" }, at = { @At("TAIL") }, cancellable = true)
    @Dynamic
    private static void labyMod$fixDynamicFovZoom(final CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue((Object)((boolean)info.getReturnValue() || Laby.references().zoomController().isZoomActive()));
    }
    
    @Overwrite
    @Dynamic
    public static boolean openWebLink(final URI uri) {
        try {
            return OperatingSystem.getPlatform().launchUrlProcess(uri.toURL());
        }
        catch (final Exception exception) {
            return false;
        }
    }
}
