// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.entity;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.util.LegacyEntityTypeRegistry;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ pm.class })
public class MixinEntityList
{
    @Inject(method = { "<clinit>" }, at = { @At("HEAD") })
    private static void labyMod$registerPlayer(final CallbackInfo ci) {
        LegacyEntityTypeRegistry.register(wn.class, "player");
        LegacyEntityTypeRegistry.register(bew.class, "player");
        LegacyEntityTypeRegistry.register(lf.class, "player");
        LegacyEntityTypeRegistry.register(bex.class, "player");
    }
    
    @Inject(method = { "addMapping(Ljava/lang/Class;Ljava/lang/String;I)V" }, at = { @At("TAIL") })
    private static void labyMod$registerEntityType(final Class<? extends pk> entityClass, final String entityId, final int id, final CallbackInfo ci) {
        LegacyEntityTypeRegistry.register(entityClass, LegacyEntityTypeRegistry.getLegacyId(entityId));
    }
}
