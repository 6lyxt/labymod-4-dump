// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.gui.components;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.client.gfx.pipeline.util.Scissor;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.Laby;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ esl.class })
public abstract class MixinAbstractSelectionList
{
    @Shadow
    private boolean s;
    private boolean originalRenderBackground;
    @Shadow
    protected int h;
    @Shadow
    protected int g;
    
    @Insert(method = { "render" }, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderBackground:Z", shift = At.Shift.BEFORE))
    public void labyMod$renderBackground(final esf graphics, final int param1, final int param2, final float param3, final InsertInfo ci) {
        final Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (!theme.metadata().getBoolean("hide_default_background")) {
            if (this.s != this.originalRenderBackground) {
                this.originalRenderBackground = this.s;
            }
            return;
        }
        if (this.s == this.originalRenderBackground) {
            return;
        }
        this.s = this.originalRenderBackground;
    }
    
    @WrapOperation(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderList(Lnet/minecraft/client/gui/GuiGraphics;IIF)V") })
    public void labyMod$renderList(final esl instance, final esf graphics, final int mouseX, final int mouseY, final float partialTicks, final Operation<Void> original) {
        final Scissor scissor = Laby.labyAPI().gfxRenderPipeline().scissor();
        try {
            scissor.push(((VanillaStackAccessor)graphics.c()).stack(), (float)this.g, (float)(this.h - this.g));
            original.call(new Object[] { instance, graphics, mouseX, mouseY, partialTicks });
        }
        finally {
            scissor.pop();
        }
    }
}
