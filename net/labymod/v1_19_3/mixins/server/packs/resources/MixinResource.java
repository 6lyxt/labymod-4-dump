// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.server.packs.resources;

import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_19_3.client.resources.pack.PackUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ akc.class })
public class MixinResource
{
    @WrapOperation(method = { "source" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/server/packs/resources/Resource;source:Lnet/minecraft/server/packs/PackResources;") })
    private ais labyMod$unwrapPackResources(final akc instance, final Operation<ais> original) {
        return PackUtil.unwrap((ais)original.call(new Object[] { instance }));
    }
}
