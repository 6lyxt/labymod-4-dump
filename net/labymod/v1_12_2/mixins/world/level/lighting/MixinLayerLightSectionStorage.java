// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.world.level.lighting;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.event.client.world.chunk.LightUpdateEvent;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ axw.class })
public class MixinLayerLightSectionStorage
{
    @Inject(method = { "setLightFor" }, at = { @At("TAIL") })
    private void labyMod$fireLightUpdateEvent(final ana layer, final et blockPos, final int lightLevel, final CallbackInfo ci) {
        Laby.fireEvent(new LightUpdateEvent(Laby.references().lightingLayerMapper().fromMinecraft(layer), new IntVector3(blockPos.p(), blockPos.q(), blockPos.r())));
    }
}
