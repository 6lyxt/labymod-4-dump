// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.entity;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.util.LegacyEntityTypeRegistry;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ vi.class })
public class MixinEntityList
{
    @Inject(method = { "init" }, at = { @At("HEAD") })
    private static void labyMod$registerPlayer(final CallbackInfo ci) {
        LegacyEntityTypeRegistry.register(aed.class, "player");
        LegacyEntityTypeRegistry.register(bud.class, "player");
        LegacyEntityTypeRegistry.register(oq.class, "player");
        LegacyEntityTypeRegistry.register(bue.class, "player");
    }
    
    @Inject(method = { "register" }, at = { @At("TAIL") })
    private static void labyMod$registerEntityType(final int id, final String entityId, final Class<? extends vg> entityClass, final String oldEntityId, final CallbackInfo ci) {
        LegacyEntityTypeRegistry.register(entityClass, entityId);
    }
}
