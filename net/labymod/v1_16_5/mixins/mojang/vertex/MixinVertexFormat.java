// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.mojang.vertex;

import java.util.Collections;
import java.util.Collection;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Map;
import net.labymod.v1_16_5.client.render.vertex.VertexFormatAccessor;
import net.labymod.api.client.render.shader.ShaderProgram;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.vertex.OldVertexFormat;

@Mixin({ dfr.class })
public abstract class MixinVertexFormat implements OldVertexFormat
{
    @Override
    public void setupAttributeLocation(final ShaderProgram program) {
        int attributeLocation = 0;
        final VertexFormatAccessor formatAccessor = (VertexFormatAccessor)this;
        for (final Map.Entry<String, dfs> entry : formatAccessor.getElements().entrySet()) {
            program.bindAttributeLocation(attributeLocation, entry.getKey());
            ++attributeLocation;
        }
    }
    
    @Override
    public Collection<String> getAttributeNames() {
        return (Collection<String>)Collections.emptyList();
    }
    
    @Override
    public <T> T getMojangVertexFormat() {
        return (T)this;
    }
}
