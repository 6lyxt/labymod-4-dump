// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.screen;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ elm.class })
public abstract class MixinScreenTooltip
{
    @Shadow
    protected abstract void a(final eaq p0, final List<eny> p1, final int p2, final int p3);
    
    @Redirect(method = { "renderTooltip(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/List;Ljava/util/Optional;II)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderTooltipInternal(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/List;II)V"))
    private void labyMod$renderTooltip(final elm instance, final eaq stack, final List<eny> components, final int x, final int y) {
        this.labyMod$renderTooltipInternal(stack, components, x, y);
    }
    
    @Redirect(method = { "renderTooltip(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/List;II)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderTooltipInternal(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/List;II)V"))
    private void labyMod$renderTooltipCharSequence(final elm instance, final eaq stack, final List<eny> components, final int x, final int y) {
        this.labyMod$renderTooltipInternal(stack, components, x, y);
    }
    
    private void labyMod$renderTooltipInternal(final eaq stack, final List<eny> components, final int x, final int y) {
        Laby.labyAPI().gfxRenderPipeline().gfx().storeAndRestoreBlaze3DStates(() -> this.a(stack, components, x, y));
    }
}
