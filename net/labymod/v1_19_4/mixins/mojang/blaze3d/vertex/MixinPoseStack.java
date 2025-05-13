// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.mojang.blaze3d.vertex;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.render.matrix.Stack;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;

@Mixin({ ehe.class })
public class MixinPoseStack implements VanillaStackAccessor
{
    private Stack labyMod$stack;
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$createApiStack(final CallbackInfo ci) {
        this.labyMod$stack = Stack.create(this);
    }
    
    @Override
    public Stack stack(final Object bufferSource) {
        return this.labyMod$stack.multiBufferSource(bufferSource);
    }
}
