// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.server.packs.resources;

import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_21_1.client.resources.pack.PackUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ auc.class })
public class MixinResource
{
    @WrapOperation(method = { "source" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/server/packs/resources/Resource;source:Lnet/minecraft/server/packs/PackResources;") })
    private asq labyMod$unwrapPackResources(final auc instance, final Operation<asq> original) {
        return PackUtil.unwrap((asq)original.call(new Object[] { instance }));
    }
}
