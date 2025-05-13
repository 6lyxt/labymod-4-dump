// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.mojang.vertex;

import java.util.Collection;
import net.labymod.v1_20_4.client.render.vertex.VertexFormatUtil;
import net.labymod.api.client.render.shader.ShaderProgram;
import org.spongepowered.asm.mixin.Shadow;
import com.google.common.collect.ImmutableList;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.vertex.OldVertexFormat;

@Mixin({ eqg.class })
public abstract class MixinVertexFormat implements OldVertexFormat
{
    @Shadow
    public abstract ImmutableList<String> d();
    
    @Override
    public void setupAttributeLocation(final ShaderProgram program) {
        VertexFormatUtil.bindAttributeLocation((eqg)this, program);
    }
    
    @Override
    public Collection<String> getAttributeNames() {
        return (Collection<String>)this.d();
    }
    
    @Override
    public <T> T getMojangVertexFormat() {
        return (T)this;
    }
}
