// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.gui.components;

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

@Mixin({ enw.class })
public abstract class MixinAbstractSelectionList
{
    @Shadow
    private boolean u;
    private boolean originalRenderBackground;
    @Shadow
    private boolean v;
    @Shadow
    protected int k;
    @Shadow
    protected int f;
    private boolean originalRenderTopAndBottom;
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V" }, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderBackground:Z", shift = At.Shift.BEFORE))
    public void labyMod$renderBackground(final ehe stack, final int param1, final int param2, final float param3, final InsertInfo ci) {
        final Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (!theme.metadata().getBoolean("hide_default_background")) {
            if (this.u != this.originalRenderBackground) {
                this.originalRenderBackground = this.u;
            }
            return;
        }
        if (this.u == this.originalRenderBackground) {
            return;
        }
        this.u = this.originalRenderBackground;
    }
    
    @Insert(method = { "render" }, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderTopAndBottom:Z", shift = At.Shift.BEFORE))
    public void labyMod$renderTopAndBottom(final ehe stack, final int param1, final int param2, final float param3, final InsertInfo ci) {
        final Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (!theme.metadata().getBoolean("hide_default_background")) {
            if (this.v != this.originalRenderTopAndBottom) {
                this.originalRenderTopAndBottom = this.v;
            }
            return;
        }
        if (this.v == this.originalRenderTopAndBottom) {
            return;
        }
        this.v = this.originalRenderTopAndBottom;
    }
    
    @WrapOperation(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderList(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V") })
    public void labyMod$renderList(final enw instance, final ehe stack, final int mouseX, final int mouseY, final float partialTicks, final Operation<Void> original) {
        final Scissor scissor = Laby.labyAPI().gfxRenderPipeline().scissor();
        try {
            scissor.push(((VanillaStackAccessor)stack).stack(), (float)this.f, (float)(this.k - this.f));
            original.call(new Object[] { instance, stack, mouseX, mouseY, partialTicks });
        }
        finally {
            scissor.pop();
        }
    }
}
