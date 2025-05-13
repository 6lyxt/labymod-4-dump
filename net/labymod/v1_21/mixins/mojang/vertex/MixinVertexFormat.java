// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.mojang.vertex;

import java.util.Collection;
import net.labymod.v1_21.client.render.vertex.VertexFormatUtil;
import net.labymod.api.client.render.shader.ShaderProgram;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.vertex.OldVertexFormat;

@Mixin({ fbn.class })
public abstract class MixinVertexFormat implements OldVertexFormat
{
    @Shadow
    public abstract List<String> d();
    
    @Override
    public void setupAttributeLocation(final ShaderProgram program) {
        VertexFormatUtil.bindAttributeLocation((fbn)this, program);
    }
    
    @Override
    public Collection<String> getAttributeNames() {
        return this.d();
    }
    
    @Override
    public <T> T getMojangVertexFormat() {
        return (T)this;
    }
}
