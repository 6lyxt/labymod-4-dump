// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.screen.components;

import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.accessor.gui.CommandSuggestionsAccessor;

@Mixin({ dlm.class })
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
    
    @Redirect(method = { "render" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiComponent;fill(Lcom/mojang/blaze3d/vertex/PoseStack;IIIII)V"))
    private void labyMod$modifyBackgroundPosition(final dfm stack, final int left, final int top, final int right, final int bottom, final int color) {
        dkw.a(stack, left, (this.bottomY != -1) ? (this.bottomY - 12) : top, right, (this.bottomY != -1) ? (this.bottomY - 12 + (bottom - top)) : bottom, color);
    }
    
    @Redirect(method = { "render" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawShadow(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/util/FormattedCharSequence;FFI)I"))
    private int labyMod$modifyTextPosition(final dku font, final dfm stack, final afa text, final float x, final float y, final int color) {
        return font.a(stack, text, x, (this.bottomY != -1) ? ((float)(this.bottomY - 12 + 2)) : y, color);
    }
    
    @Override
    public void setBottomY(final int bottomY) {
        this.bottomY = bottomY;
    }
}
