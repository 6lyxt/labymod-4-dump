// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.chat.contents;

import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_6.client.network.chat.contents.ScoreContentsAccessor;

@Mixin({ yx.class })
public class MixinScoreContents implements ScoreContentsAccessor
{
    @Shadow
    @Final
    @Mutable
    private String d;
    @Shadow
    @Final
    @Mutable
    private String f;
    
    @Override
    public void setName(final String name) {
        this.d = name;
    }
    
    @Override
    public void setObjective(final String objective) {
        this.f = objective;
    }
}
