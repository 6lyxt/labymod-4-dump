// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.chat.contents;

import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_3.client.network.chat.contents.LiteralContentsAccessor;

@Mixin({ zc.a.class })
public class MixinLiteralContents implements LiteralContentsAccessor
{
    @Shadow
    @Final
    @Mutable
    private String d;
    
    @Override
    public void setText(final String text) {
        this.d = text;
    }
}
