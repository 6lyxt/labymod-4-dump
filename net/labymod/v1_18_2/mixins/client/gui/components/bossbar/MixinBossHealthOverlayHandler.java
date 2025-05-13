// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.gui.components.bossbar;

import org.spongepowered.asm.mixin.injection.Inject;
import java.util.Iterator;
import net.labymod.api.client.world.BossBarRegistry;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.UUID;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.world.BossBar;
import net.labymod.api.Laby;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = { "net.minecraft.client.gui.components.BossHealthOverlay$1" })
public class MixinBossHealthOverlayHandler
{
    @Redirect(method = { "add" }, at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    public Object labyMod$registerBossBar(final Map instance, final Object k, final Object v) {
        instance.put(k, v);
        Laby.references().bossBarRegistry().registerBossBar((BossBar)v);
        return v;
    }
    
    @Inject(method = { "remove" }, at = { @At("HEAD") })
    public void labyMod$unregisterBossBar(final UUID id, final CallbackInfo ci) {
        final BossBarRegistry bossBarRegistry = Laby.references().bossBarRegistry();
        BossBar bossBar = null;
        for (final BossBar bar : bossBarRegistry.getBossBars()) {
            if (bar.getIdentifier().equals(id)) {
                bossBar = bar;
                break;
            }
        }
        if (bossBar == null) {
            return;
        }
        bossBarRegistry.unregisterBossBar(bossBar);
    }
}
