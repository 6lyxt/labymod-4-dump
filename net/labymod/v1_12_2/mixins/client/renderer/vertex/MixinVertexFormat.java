// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer.vertex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import org.spongepowered.asm.mixin.Shadow;
import com.google.common.collect.ImmutableMap;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_12_2.client.render.vertex.CustomVertexFormat;

@Mixin({ cea.class })
public abstract class MixinVertexFormat implements CustomVertexFormat
{
    private ImmutableMap<String, ceb> labyMod$namedElements;
    private boolean labyMod$custom;
    
    public MixinVertexFormat() {
        this.labyMod$namedElements = (ImmutableMap<String, ceb>)ImmutableMap.of();
    }
    
    @Shadow
    public abstract cea a(final ceb p0);
    
    @Override
    public void setCustom() {
        this.labyMod$custom = true;
    }
    
    @Override
    public boolean isCustom() {
        return this.labyMod$custom;
    }
    
    @Override
    public void addElements(final ImmutableMap<String, ceb> elements) {
        this.labyMod$namedElements = elements;
        for (final ceb element : elements.values()) {
            this.a(element);
        }
    }
    
    @Override
    public ImmutableList<String> getAttributeNames() {
        return (ImmutableList<String>)this.labyMod$namedElements.keySet().asList();
    }
    
    @Override
    public ImmutableMap<String, ceb> getNamedElements() {
        return this.labyMod$namedElements;
    }
}
