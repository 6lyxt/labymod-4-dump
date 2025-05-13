// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;

@Mixin({ avz.class })
public class MixinGuiButtonLanguage extends avs
{
    public MixinGuiButtonLanguage(final int id, final int x, final int y, final String text) {
        super(id, x, y, text);
    }
    
    public void a(final ave minecraft, final int mouseX, final int mouseY) {
        super.a(minecraft, mouseX, mouseY);
    }
}
