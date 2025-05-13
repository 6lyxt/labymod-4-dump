// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.server.packs.resources;

import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_20_4.client.resources.pack.PackUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ aqg.class })
public class MixinResource
{
    @WrapOperation(method = { "source" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/server/packs/resources/Resource;source:Lnet/minecraft/server/packs/PackResources;") })
    private aow labyMod$unwrapPackResources(final aqg instance, final Operation<aow> original) {
        return PackUtil.unwrap((aow)original.call(new Object[] { instance }));
    }
}
