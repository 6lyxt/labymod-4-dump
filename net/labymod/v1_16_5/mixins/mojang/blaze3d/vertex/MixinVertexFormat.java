// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.mojang.blaze3d.vertex;

import com.google.common.collect.ImmutableMap;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_16_5.client.render.vertex.VertexFormatAccessor;

@Mixin({ dfr.class })
public class MixinVertexFormat implements VertexFormatAccessor
{
    @Override
    public ImmutableMap<String, dfs> getElements() {
        return (ImmutableMap<String, dfs>)ImmutableMap.of();
    }
}
