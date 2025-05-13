// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.gui.components.bossbar;

import net.labymod.api.Laby;
import net.labymod.v1_16_5.client.world.VersionedBossBarRegistry;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.render.overlay.IngameOverlayElementRenderEventCaller;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dli.class })
public class MixinBossHealthOverlay
{
    @Inject(method = { "render" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$preDrawBossBar(final dfm poseStack, final CallbackInfo ci) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callBossBarPre(((VanillaStackAccessor)poseStack).stack());
        if (cancelled) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "render" }, at = { @At("TAIL") })
    private void labyMod$postDrawBossBar(final dfm poseStack, final CallbackInfo ci) {
        IngameOverlayElementRenderEventCaller.callBossBarPost(((VanillaStackAccessor)poseStack).stack());
    }
    
    @Inject(method = { "reset" }, at = { @At("TAIL") })
    private void labyMod$resetBossBars(final CallbackInfo ci) {
        ((VersionedBossBarRegistry)Laby.references().bossBarRegistry()).reset();
    }
}
