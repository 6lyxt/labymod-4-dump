// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.renderer.skipcolors;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.GlColorAlphaModifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dwn.class })
public class MixinModelPartSkipColors
{
    @ModifyVariable(method = { "compile" }, at = @At("HEAD"), argsOnly = true, index = 8)
    private float labyMod$modifyAlpha(final float alpha) {
        return GlColorAlphaModifier.isModifiedAlpha() ? GlColorAlphaModifier.getAlpha() : alpha;
    }
}
