// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui;

import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.world.BossBar;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.v1_12_2.client.world.VersionedBossBarRegistry;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.render.overlay.IngameOverlayElementRenderEventCaller;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ biz.class })
public class MixinGuiBossOverlay
{
    @Inject(method = { "renderBossHealth" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$preDrawBossBar(final CallbackInfo ci) {
        if (IngameOverlayElementRenderEventCaller.callBossBarPre(VersionedStackProvider.DEFAULT_STACK)) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderBossHealth" }, at = { @At("TAIL") })
    private void labyMod$postDrawBossBar(final CallbackInfo ci) {
        IngameOverlayElementRenderEventCaller.callBossBarPost(VersionedStackProvider.DEFAULT_STACK);
    }
    
    @Inject(method = { "clearBossInfos" }, at = { @At("TAIL") })
    private void labyMod$resetBossBars(final CallbackInfo ci) {
        ((VersionedBossBarRegistry)Laby.references().bossBarRegistry()).reset();
    }
    
    @Redirect(method = { "read" }, at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object labyMod$registerBossBar(final Map<Object, Object> instance, final Object k, final Object v) {
        instance.put(k, v);
        Laby.references().bossBarRegistry().registerBossBar((BossBar)v);
        return v;
    }
    
    @Redirect(method = { "read" }, at = @At(value = "INVOKE", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object labyMod$unregisterBossBar(final Map<Object, Object> instance, final Object o) {
        final Object remove = instance.remove(o);
        Laby.references().bossBarRegistry().unregisterBossBar((BossBar)remove);
        return remove;
    }
}
