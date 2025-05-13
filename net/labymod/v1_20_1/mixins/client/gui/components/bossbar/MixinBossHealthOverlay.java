// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.gui.components.bossbar;

import net.labymod.api.Laby;
import net.labymod.v1_20_1.client.world.VersionedBossBarRegistry;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.render.overlay.IngameOverlayElementRenderEventCaller;
import net.labymod.v1_20_1.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eph.class })
public class MixinBossHealthOverlay
{
    @Inject(method = { "render" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$preDrawBossBar(final eox graphics, final CallbackInfo ci) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callBossBarPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "render" }, at = { @At("TAIL") })
    private void labyMod$postDrawBossBar(final eox graphics, final CallbackInfo ci) {
        IngameOverlayElementRenderEventCaller.callBossBarPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Inject(method = { "reset" }, at = { @At("TAIL") })
    private void labyMod$resetBossBars(final CallbackInfo ci) {
        ((VersionedBossBarRegistry)Laby.references().bossBarRegistry()).reset();
    }
}
