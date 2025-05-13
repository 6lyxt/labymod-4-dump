// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bjl.class })
public class MixinGuiLockIconButton extends bja
{
    public MixinGuiLockIconButton(final int p_i195_1_, final int p_i195_2_, final int p_i195_3_, final String p_i195_4_) {
        super(p_i195_1_, p_i195_2_, p_i195_3_, p_i195_4_);
    }
    
    public MixinGuiLockIconButton(final int p_i196_1_, final int p_i196_2_, final int p_i196_3_, final int p_i196_4_, final int p_i196_5_, final String p_i196_6_) {
        super(p_i196_1_, p_i196_2_, p_i196_3_, p_i196_4_, p_i196_5_, p_i196_6_);
    }
    
    public void a(final bib minecraft, final int mouseX, final int mouseY, final float partialTicks) {
        super.a(minecraft, mouseX, mouseY, partialTicks);
    }
}
