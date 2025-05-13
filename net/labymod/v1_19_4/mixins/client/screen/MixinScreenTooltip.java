// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.screen;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ etd.class })
public abstract class MixinScreenTooltip
{
    @Shadow
    protected abstract void a(final ehe p0, final List<evu> p1, final int p2, final int p3, final evv p4);
    
    @Redirect(method = { "renderTooltip(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/List;Ljava/util/Optional;II)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderTooltipInternal(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;)V"))
    private void labyMod$renderTooltip(final etd instance, final ehe stack, final List<evu> components, final int x, final int y, final evv positioner) {
        this.labyMod$renderTooltipInternal(stack, components, x, y, positioner);
    }
    
    @Redirect(method = { "renderTooltip(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/List;II)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderTooltipInternal(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;)V"))
    private void labyMod$renderTooltipCharSequence(final etd instance, final ehe stack, final List<evu> components, final int x, final int y, final evv positioner) {
        this.labyMod$renderTooltipInternal(stack, components, x, y, positioner);
    }
    
    private void labyMod$renderTooltipInternal(final ehe stack, final List<evu> components, final int x, final int y, final evv positioner) {
        Laby.labyAPI().gfxRenderPipeline().gfx().storeAndRestoreBlaze3DStates(() -> this.a(stack, components, x, y, positioner));
    }
}
