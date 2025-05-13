// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.mojang.blaze3d.opengl;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import com.google.common.collect.ImmutableList;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_5.client.renderer.CompositeStateAppender;

@Mixin({ gry.b.class })
public class MixinCompositeState implements CompositeStateAppender
{
    @Mutable
    @Shadow
    @Final
    private ImmutableList<grx> d;
    
    @Override
    public void append(final grx shard) {
        this.d = (ImmutableList<grx>)ImmutableList.builder().add((Object[])this.d.toArray((Object[])new grx[0])).add((Object)shard).build();
    }
}
