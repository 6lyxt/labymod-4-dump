// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.labymod.api.Laby;
import net.labymod.core.client.render.batch.DefaultRenderContexts;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fta.class })
public class MixinGameRendererScreen
{
    private final DefaultRenderContexts renderContexts;
    
    public MixinGameRendererScreen() {
        this.renderContexts = (DefaultRenderContexts)Laby.references().renderContexts();
    }
    
    @WrapOperation(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderWithTooltip(Lnet/minecraft/client/gui/GuiGraphics;IIF)V") })
    private void labyMod$storeStack(final fdb instance, final ewu graphics, final int mouseX, final int mouseY, final float tickDelta, final Operation<Void> original) {
        final Stack stack = ((VanillaStackAccessor)graphics.c()).stack();
        final ScreenCustomFontStack screenCustomFontStack = Laby.references().screenCustomFontStack();
        screenCustomFontStack.push(instance);
        this.renderContexts.setCurrentStack(stack);
        ScreenUtil.wrapRender(stack, tickDelta, () -> original.call(new Object[] { instance, graphics, mouseX, mouseY, tickDelta }));
        this.renderContexts.setCurrentStack(null);
        screenCustomFontStack.pop(instance);
    }
}
