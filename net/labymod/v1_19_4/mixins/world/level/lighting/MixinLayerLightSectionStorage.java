// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.world.level.lighting;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.event.client.world.chunk.LightUpdateEvent;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dvz.class })
public class MixinLayerLightSectionStorage
{
    @Shadow
    @Final
    private cmr o;
    
    @Inject(method = { "setStoredLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/DataLayer;set(IIII)V", shift = At.Shift.AFTER) })
    private void labyMod$fireLightUpdateEvent(final long levelPos, final int lightLevel, final CallbackInfo ci) {
        Laby.fireEvent(new LightUpdateEvent(Laby.references().lightingLayerMapper().fromMinecraft(this.o), new IntVector3(gt.a(levelPos), gt.b(levelPos), gt.c(levelPos))));
    }
}
