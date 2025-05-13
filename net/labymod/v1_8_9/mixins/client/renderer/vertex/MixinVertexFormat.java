// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer.vertex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import org.spongepowered.asm.mixin.Shadow;
import com.google.common.collect.ImmutableMap;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_8_9.client.render.vertex.CustomVertexFormat;

@Mixin({ bmu.class })
public abstract class MixinVertexFormat implements CustomVertexFormat
{
    private ImmutableMap<String, bmv> labyMod$namedElements;
    private boolean labyMod$custom;
    
    public MixinVertexFormat() {
        this.labyMod$namedElements = (ImmutableMap<String, bmv>)ImmutableMap.of();
    }
    
    @Shadow
    public abstract bmu a(final bmv p0);
    
    @Override
    public void setCustom() {
        this.labyMod$custom = true;
    }
    
    @Override
    public boolean isCustom() {
        return this.labyMod$custom;
    }
    
    @Override
    public void addElements(final ImmutableMap<String, bmv> elements) {
        this.labyMod$namedElements = elements;
        for (final bmv element : elements.values()) {
            this.a(element);
        }
    }
    
    @Override
    public ImmutableList<String> getAttributeNames() {
        return (ImmutableList<String>)this.labyMod$namedElements.keySet().asList();
    }
    
    @Override
    public ImmutableMap<String, bmv> getNamedElements() {
        return this.labyMod$namedElements;
    }
}
