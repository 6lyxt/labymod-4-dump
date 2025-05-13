// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bmj.class })
public class MixinGuiScreenBook
{
    @Insert(method = { "drawScreen" }, at = @At("HEAD"))
    public void labyMod$enableVanillaFont(final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        final RenderAttributesStack renderAttributesStack = this.renderAttributesStack();
        final RenderAttributes attributes = renderAttributesStack.pushAndGet();
        attributes.setForceVanillaFont(true);
    }
    
    @Insert(method = { "drawScreen" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;drawScreen(IIF)V", shift = At.Shift.BEFORE))
    public void labyMod$resetFont(final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        final RenderAttributesStack renderAttributesStack = this.renderAttributesStack();
        renderAttributesStack.pop();
    }
    
    private RenderAttributesStack renderAttributesStack() {
        return Laby.references().renderEnvironmentContext().renderAttributesStack();
    }
}
