// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bji.class })
public class MixinGuiButtonLanguage extends bja
{
    public MixinGuiButtonLanguage(final int id, final int x, final int y, final String text) {
        super(id, x, y, text);
    }
    
    public void a(final bib minecraft, final int mouseX, final int mouseY, final float partialTicks) {
        super.a(minecraft, mouseX, mouseY, partialTicks);
    }
}
