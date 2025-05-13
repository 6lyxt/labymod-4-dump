// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.gui.components.bossbar;

import java.util.Iterator;
import net.labymod.api.client.world.BossBarRegistry;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.world.BossBar;
import net.labymod.api.Laby;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dli.class })
public class MixinBossHealthOverlayHandler
{
    @Redirect(method = { "update" }, at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    public Object labyMod$update(final Map instance, final Object k, final Object v) {
        instance.put(k, v);
        Laby.references().bossBarRegistry().registerBossBar((BossBar)v);
        return v;
    }
    
    @Redirect(method = { "update" }, at = @At(value = "INVOKE", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;"))
    public Object labyMod$remove(final Map instance, final Object k) {
        final BossBarRegistry bossBarRegistry = Laby.references().bossBarRegistry();
        BossBar bossBar = null;
        for (final BossBar bar : bossBarRegistry.getBossBars()) {
            if (bar.getIdentifier().equals(k)) {
                bossBar = bar;
                break;
            }
        }
        if (bossBar != null) {
            bossBarRegistry.unregisterBossBar(bossBar);
        }
        return instance.remove(k);
    }
}
