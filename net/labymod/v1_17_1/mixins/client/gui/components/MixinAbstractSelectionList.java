// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.gui.components;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.client.gfx.pipeline.util.Scissor;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dww.class })
public abstract class MixinAbstractSelectionList
{
    @Shadow
    private boolean t;
    private boolean originalRenderBackground;
    @Shadow
    private boolean u;
    @Shadow
    protected int j;
    @Shadow
    protected int i;
    @Shadow
    protected int e;
    @Shadow
    protected int d;
    private boolean originalRenderTopAndBottom;
    private dql labyMod$poseStack;
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V" }, at = @At("HEAD"))
    private void labyMod$getStack(final dql stack, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        this.labyMod$poseStack = stack;
    }
    
    @Redirect(method = { "render", "renderList" }, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;vertex(DDD)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private dqp labyMod$addStack(final dqg instance, final double x, final double y, final double z) {
        return instance.a(this.labyMod$poseStack.c().a(), (float)x, (float)y, (float)z);
    }
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V" }, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderBackground:Z", shift = At.Shift.BEFORE))
    public void labyMod$renderBackground(final dql stack, final int param1, final int param2, final float param3, final InsertInfo ci) {
        final Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (!theme.metadata().getBoolean("hide_default_background")) {
            if (this.t != this.originalRenderBackground) {
                this.originalRenderBackground = this.t;
            }
            return;
        }
        if (this.t == this.originalRenderBackground) {
            return;
        }
        this.t = this.originalRenderBackground;
    }
    
    @Insert(method = { "render" }, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderTopAndBottom:Z", shift = At.Shift.BEFORE))
    public void labyMod$renderTopAndBottom(final dql stack, final int param1, final int param2, final float param3, final InsertInfo ci) {
        final Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (!theme.metadata().getBoolean("hide_default_background")) {
            if (this.u != this.originalRenderTopAndBottom) {
                this.originalRenderTopAndBottom = this.u;
            }
            return;
        }
        if (this.u == this.originalRenderTopAndBottom) {
            return;
        }
        this.u = this.originalRenderTopAndBottom;
    }
    
    @WrapOperation(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderList(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIF)V") })
    public void labyMod$renderList(final dww instance, final dql stack, final int left, final int top, final int mouseX, final int mouseY, final float partialTicks, final Operation<Void> original) {
        final Scissor scissor = Laby.labyAPI().gfxRenderPipeline().scissor();
        try {
            scissor.push(((VanillaStackAccessor)stack).stack(), (float)this.i, (float)(this.j - this.i));
            original.call(new Object[] { instance, stack, left, top, mouseX, mouseY, partialTicks });
        }
        finally {
            scissor.pop();
        }
    }
}
