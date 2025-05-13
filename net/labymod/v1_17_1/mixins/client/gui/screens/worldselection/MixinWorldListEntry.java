// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.gui.screens.worldselection;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eel.a.class })
public class MixinWorldListEntry
{
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/LevelSummary;getInfo()Lnet/minecraft/network/chat/Component;", shift = At.Shift.AFTER) })
    private void labyMod$setWhiteShaderColor(final dql $$0, final int $$1, final int $$2, final int $$3, final int $$4, final int $$5, final int $$6, final int $$7, final boolean $$8, final float $$9, final CallbackInfo ci) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
