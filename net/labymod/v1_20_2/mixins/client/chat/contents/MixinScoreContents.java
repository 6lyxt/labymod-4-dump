// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.chat.contents;

import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_2.client.network.chat.contents.ScoreContentsAccessor;

@Mixin({ us.class })
public class MixinScoreContents implements ScoreContentsAccessor
{
    @Shadow
    @Final
    @Mutable
    private String c;
    @Shadow
    @Final
    @Mutable
    private String e;
    
    @Override
    public void setName(final String name) {
        this.c = name;
    }
    
    @Override
    public void setObjective(final String objective) {
        this.e = objective;
    }
}
