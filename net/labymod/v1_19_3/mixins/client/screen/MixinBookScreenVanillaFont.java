// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.screen;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eqg.class, eqf.class })
public class MixinBookScreenVanillaFont
{
    @Insert(method = { "render" }, at = @At("HEAD"))
    public void labyMod$enableVanillaFont(final eed stack, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        final RenderAttributesStack renderAttributesStack = this.renderAttributesStack();
        final RenderAttributes attributes = renderAttributesStack.pushAndGet();
        attributes.setForceVanillaFont(true);
        attributes.apply();
    }
    
    @Insert(method = { "render" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V", shift = At.Shift.BEFORE))
    public void labyMod$resetFont(final eed stack, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        final RenderAttributesStack renderAttributesStack = this.renderAttributesStack();
        renderAttributesStack.pop();
    }
    
    private RenderAttributesStack renderAttributesStack() {
        return Laby.references().renderEnvironmentContext().renderAttributesStack();
    }
}
