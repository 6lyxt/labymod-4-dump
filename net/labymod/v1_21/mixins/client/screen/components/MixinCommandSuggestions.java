// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client.screen.components;

import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.accessor.gui.CommandSuggestionsAccessor;

@Mixin({ fip.class })
public class MixinCommandSuggestions implements CommandSuggestionsAccessor
{
    private static final int BOTTOM_OFFSET = 12;
    private int bottomY;
    
    public MixinCommandSuggestions() {
        this.bottomY = -1;
    }
    
    @ModifyVariable(method = { "showSuggestions" }, at = @At("LOAD"), ordinal = 2)
    private int labyMod$modifyListPosition(final int y) {
        return (this.bottomY != -1) ? this.bottomY : y;
    }
    
    @Redirect(method = { "renderUsage" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"))
    private void labyMod$modifyBackgroundPosition(final fhz graphics, final int left, final int top, final int right, final int bottom, final int color) {
        graphics.a(left, (this.bottomY != -1) ? (this.bottomY - 12) : top, right, (this.bottomY != -1) ? (this.bottomY - 12 + (bottom - top)) : bottom, color);
    }
    
    @Redirect(method = { "renderUsage" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;III)I"))
    private int labyMod$modifyTextPosition(final fhz graphics, final fhx font, final aya text, final int x, final int y, final int color) {
        return graphics.b(font, text, x, (this.bottomY != -1) ? (this.bottomY - 12 + 2) : y, color);
    }
    
    @Override
    public void setBottomY(final int bottomY) {
        this.bottomY = bottomY;
    }
}
