// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.screen;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.Laby;
import java.util.List;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fdb.class })
public abstract class MixinScreenTooltip
{
    @Shadow
    protected ews i;
    
    @Redirect(method = { "renderWithTooltip" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;Lnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;II)V"))
    private void labyMod$renderTooltip(final ewu graphics, final ews font, final List<aua> values, final ffu positioner, final int mouseX, final int mouseY) {
        Laby.gfx().storeAndRestoreBlaze3DStates(() -> graphics.a(font, values, positioner, mouseX, mouseY));
    }
}
