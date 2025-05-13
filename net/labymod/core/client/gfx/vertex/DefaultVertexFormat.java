// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.vertex;

import java.util.Objects;
import javax.inject.Inject;
import java.util.LinkedHashMap;
import net.labymod.api.models.Implements;
import net.labymod.api.client.gfx.buffer.BufferUsage;
import java.util.Collection;
import java.util.Iterator;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;
import net.labymod.api.client.gfx.vertex.attribute.VertexAttribute;
import java.util.Map;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.vertex.VertexFormat;

public class DefaultVertexFormat implements VertexFormat
{
    private final ShaderProgram shaderProgram;
    private final Map<String, VertexAttribute> namedAttributes;
    private final VertexAttribute[] attributes;
    private final int[] offsets;
    private final int stride;
    private RenderedBuffer immediateBuffer;
    
    private DefaultVertexFormat(final ShaderProgram shaderProgram, final Map<String, VertexAttribute> namedAttributes) {
        this.shaderProgram = shaderProgram;
        this.namedAttributes = namedAttributes;
        this.attributes = new VertexAttribute[this.namedAttributes.size()];
        int index = 0;
        for (final VertexAttribute attribute : this.namedAttributes.values()) {
            this.attributes[index] = attribute;
            ++index;
        }
        this.offsets = new int[index];
        int stride = 0;
        int attributeIndex = 0;
        for (final VertexAttribute attribute2 : this.attributes) {
            this.offsets[attributeIndex] = stride;
            stride += attribute2.getByteSize();
            ++attributeIndex;
        }
        this.stride = stride;
    }
    
    @Override
    public void apply() {
        for (int index = 0; index < this.attributes.length; ++index) {
            final VertexAttribute attribute = this.attributes[index];
            attribute.apply(index, this.stride, this.offsets[index]);
        }
    }
    
    @Override
    public void clear() {
        for (int index = 0; index < this.attributes.length; ++index) {
            final VertexAttribute attribute = this.attributes[index];
            attribute.clear(index);
        }
    }
    
    @Override
    public int getStride() {
        return this.stride;
    }
    
    @Override
    public Collection<String> getAttributeNames() {
        return this.namedAttributes.keySet();
    }
    
    @Override
    public VertexAttribute[] getAttributes() {
        return this.attributes;
    }
    
    @Override
    public ShaderProgram getShader() {
        return this.shaderProgram;
    }
    
    @Override
    public RenderedBuffer getImmediateDrawBuffer() {
        RenderedBuffer buffer = this.immediateBuffer;
        if (buffer == null) {
            buffer = new RenderedBuffer(BufferUsage.DYNAMIC_DRAW);
            this.immediateBuffer = buffer;
        }
        return buffer;
    }
    
    @Implements(Builder.class)
    public static class DefaultVertexFormatBuilder implements Builder
    {
        private final Map<String, VertexAttribute> attributeMap;
        private ShaderProgram shaderProgram;
        
        @Inject
        public DefaultVertexFormatBuilder() {
            this.attributeMap = new LinkedHashMap<String, VertexAttribute>();
        }
        
        @Override
        public Builder addAttribute(final String name, final VertexAttribute attribute) {
            this.attributeMap.put(name, attribute);
            return this;
        }
        
        @Override
        public Builder addShaderProgram(final ShaderProgram shaderProgram) {
            this.shaderProgram = shaderProgram;
            return this;
        }
        
        @Override
        public VertexFormat build() {
            Objects.requireNonNull(this.shaderProgram, "shader must not be null");
            final Map<String, VertexAttribute> attributes = new LinkedHashMap<String, VertexAttribute>(this.attributeMap);
            final VertexFormat format = new DefaultVertexFormat(this.shaderProgram, attributes);
            this.shaderProgram.link(format);
            this.shaderProgram = null;
            this.attributeMap.clear();
            return format;
        }
    }
}
