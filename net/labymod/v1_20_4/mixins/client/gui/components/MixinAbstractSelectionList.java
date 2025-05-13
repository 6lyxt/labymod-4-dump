// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.gui.components;

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

@Mixin({ exb.class })
public abstract class MixinAbstractSelectionList extends ewy
{
    @Shadow
    private boolean r;
    private boolean originalRenderBackground;
    
    public MixinAbstractSelectionList(final int $$0, final int $$1, final int $$2, final int $$3, final vf $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }
    
    @Insert(method = { "renderWidget" }, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderBackground:Z", shift = At.Shift.BEFORE))
    public void labyMod$renderBackground(final ewu graphics, final int param1, final int param2, final float param3, final InsertInfo ci) {
        final Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (!theme.metadata().getBoolean("hide_default_background")) {
            if (this.r != this.originalRenderBackground) {
                this.originalRenderBackground = this.r;
            }
            return;
        }
        if (this.r == this.originalRenderBackground) {
            return;
        }
        this.r = this.originalRenderBackground;
    }
    
    @WrapOperation(method = { "renderWidget" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderList(Lnet/minecraft/client/gui/GuiGraphics;IIF)V") })
    public void labyMod$renderList(final exb instance, final ewu graphics, final int mouseX, final int mouseY, final float partialTicks, final Operation<Void> original) {
        final Scissor scissor = Laby.labyAPI().gfxRenderPipeline().scissor();
        try {
            scissor.push(((VanillaStackAccessor)graphics.c()).stack(), (float)this.C(), (float)this.u());
            original.call(new Object[] { instance, graphics, mouseX, mouseY, partialTicks });
        }
        finally {
            scissor.pop();
        }
    }
}
