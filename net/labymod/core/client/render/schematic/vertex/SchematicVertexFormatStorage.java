// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.vertex;

import net.labymod.api.client.gfx.vertex.attribute.VertexAttribute;
import net.labymod.api.client.gfx.vertex.attribute.VertexAttribute1F;
import net.labymod.api.client.gfx.vertex.attribute.DefaultVertexAttributes;
import net.labymod.api.client.gfx.shader.ShaderConstants;
import net.labymod.api.Laby;
import java.util.function.BiConsumer;
import java.util.Objects;
import net.labymod.api.client.gfx.vertex.VertexFormat;
import java.util.Map;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.api.client.gfx.vertex.VertexFormatStorage;

@AutoService(VertexFormatStorage.class)
public class SchematicVertexFormatStorage implements VertexFormatStorage
{
    @Override
    public void store(final Map<String, VertexFormat> map) {
        Objects.requireNonNull(map);
        this.prepareDefaultSchematicFormat(map::put);
    }
    
    private void prepareDefaultSchematicFormat(final BiConsumer<String, VertexFormat> consumer) {
        final VertexFormat.Builder builder = Laby.references().vertexFormatBuilder();
        consumer.accept("schematic", builder.addShaderProgram(SchematicShaderPrograms.applyDefaultSchematicProgram(ShaderConstants.builder())).addAttribute("Position", DefaultVertexAttributes.POSITION).addAttribute("UV", DefaultVertexAttributes.UV0).addAttribute("Color", DefaultVertexAttributes.COLOR).addAttribute("MaterialType", new VertexAttribute1F(false)).addAttribute("Normal", DefaultVertexAttributes.NORMAL).addAttribute("Padding", DefaultVertexAttributes.PADDING).build());
    }
}
