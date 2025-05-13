// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.scoreboard;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.scoreboard.ScoreboardScore;

@Mixin({ dql.class })
public class MixinScore implements ScoreboardScore
{
    @Final
    @Shadow
    private String d;
    @Shadow
    private int e;
    
    @Override
    public String getName() {
        return this.d;
    }
    
    @Override
    public int getValue() {
        return this.e;
    }
}
