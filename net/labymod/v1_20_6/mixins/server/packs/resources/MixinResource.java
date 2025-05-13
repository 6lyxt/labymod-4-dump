// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.server.packs.resources;

import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_20_6.client.resources.pack.PackUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ aun.class })
public class MixinResource
{
    @WrapOperation(method = { "source" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/server/packs/resources/Resource;source:Lnet/minecraft/server/packs/PackResources;") })
    private atb labyMod$unwrapPackResources(final aun instance, final Operation<atb> original) {
        return PackUtil.unwrap((atb)original.call(new Object[] { instance }));
    }
}
