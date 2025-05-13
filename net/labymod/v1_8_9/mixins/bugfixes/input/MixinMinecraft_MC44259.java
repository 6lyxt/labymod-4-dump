// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.bugfixes.input;

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ave.class })
public abstract class MixinMinecraft_MC44259
{
    @Shadow
    public avh t;
    @Shadow
    public bfk o;
    @Shadow
    public bfr g;
    
    @Shadow
    public abstract pk ac();
    
    @WrapOperation(method = { "runTick" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isPressed()Z") }, slice = { @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;keyBindTogglePerspective:Lnet/minecraft/client/settings/KeyBinding;", shift = At.Shift.BEFORE), to = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;showDebugInfo:Z", shift = At.Shift.BEFORE, ordinal = 2)) })
    private boolean labyMod$disablePerspectiveToggle(final avb instance, final Operation<Boolean> original) {
        return false;
    }
    
    @Inject(method = { "runTick" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;chatVisibility:Lnet/minecraft/entity/player/EntityPlayer$EnumChatVisibility;", shift = At.Shift.BEFORE) })
    private void labyMod$applyMC44259Fix(final CallbackInfo ci) {
        if (this.t.ao.f()) {
            final avh t = this.t;
            ++t.aB;
            if (this.t.aB > 2) {
                this.t.aB = 0;
            }
            if (this.t.aB == 0) {
                this.o.a(this.ac());
            }
            else {
                this.o.a((pk)null);
            }
            this.g.m();
        }
        if (this.t.ap.f()) {
            this.t.aG = !this.t.aG;
        }
    }
}
