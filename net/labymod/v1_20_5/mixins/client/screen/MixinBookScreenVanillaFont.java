// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.screen;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ foj.class, foi.class })
public class MixinBookScreenVanillaFont
{
    @Insert(method = { "render" }, at = @At("HEAD"))
    public void labyMod$enableVanillaFont(final fgs graphics, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        final RenderAttributesStack renderAttributesStack = this.renderAttributesStack();
        final RenderAttributes attributes = renderAttributesStack.pushAndGet();
        attributes.setForceVanillaFont(true);
    }
    
    @Insert(method = { "render" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", shift = At.Shift.BEFORE))
    public void labyMod$resetFont(final fgs graphics, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        final RenderAttributesStack renderAttributesStack = this.renderAttributesStack();
        renderAttributesStack.pop();
    }
    
    private RenderAttributesStack renderAttributesStack() {
        return Laby.references().renderEnvironmentContext().renderAttributesStack();
    }
}
