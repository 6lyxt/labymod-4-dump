// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.server.packs.resources;

import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_21_3.client.resources.pack.PackUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ avt.class })
public class MixinResource
{
    @WrapOperation(method = { "source" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/server/packs/resources/Resource;source:Lnet/minecraft/server/packs/PackResources;") })
    private aug labyMod$unwrapPackResources(final avt instance, final Operation<aug> original) {
        return PackUtil.unwrap((aug)original.call(new Object[] { instance }));
    }
}
