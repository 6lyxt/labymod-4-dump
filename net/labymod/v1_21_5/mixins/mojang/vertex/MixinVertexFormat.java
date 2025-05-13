// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.mojang.vertex;

import java.util.Collection;
import net.labymod.v1_21_5.client.render.vertex.VertexFormatUtil;
import net.labymod.api.client.render.shader.ShaderProgram;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import com.mojang.blaze3d.vertex.VertexFormat;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.vertex.OldVertexFormat;

@Mixin({ VertexFormat.class })
public abstract class MixinVertexFormat implements OldVertexFormat
{
    @Shadow
    public abstract List<String> getElementAttributeNames();
    
    @Override
    public void setupAttributeLocation(final ShaderProgram program) {
        VertexFormatUtil.bindAttributeLocation((VertexFormat)this, program);
    }
    
    @Override
    public Collection<String> getAttributeNames() {
        return this.getElementAttributeNames();
    }
    
    @Override
    public <T> T getMojangVertexFormat() {
        return (T)this;
    }
}
