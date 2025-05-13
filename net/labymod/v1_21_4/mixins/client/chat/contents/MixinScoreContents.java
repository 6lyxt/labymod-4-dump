// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client.chat.contents;

import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import com.mojang.datafixers.util.Either;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_4.client.network.chat.contents.ScoreContentsAccessor;

@Mixin({ xx.class })
public class MixinScoreContents implements ScoreContentsAccessor
{
    @Shadow
    @Final
    @Mutable
    private Either<ho, String> d;
    @Shadow
    @Final
    @Mutable
    private String e;
    
    @Override
    public void setName(final String name) {
        this.d = (Either<ho, String>)Either.right((Object)name);
    }
    
    @Override
    public void setObjective(final String objective) {
        this.e = objective;
    }
}
