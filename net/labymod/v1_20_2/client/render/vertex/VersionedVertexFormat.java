// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.render.vertex;

import net.labymod.api.client.render.shader.ShaderProgram;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashSet;
import java.util.Collection;
import java.util.function.Supplier;
import net.labymod.api.client.render.vertex.OldVertexFormat;

public class VersionedVertexFormat implements OldVertexFormat
{
    private static final Supplier<fos> NULLABLE_SUPPLIER;
    private final elu vertexFormat;
    private final Supplier<fos> shaderSupplier;
    private final Collection<String> attributeNames;
    
    public VersionedVertexFormat(final elu format) {
        this(format, VersionedVertexFormat.NULLABLE_SUPPLIER);
    }
    
    public VersionedVertexFormat(final elu vertexFormat, final Supplier<fos> shaderSupplier) {
        this.vertexFormat = vertexFormat;
        this.shaderSupplier = shaderSupplier;
        (this.attributeNames = new HashSet<String>()).addAll((Collection<? extends String>)this.vertexFormat.d());
    }
    
    public void applyShader() {
        RenderSystem.setShader((Supplier)this.shaderSupplier);
    }
    
    @Override
    public void setupAttributeLocation(final ShaderProgram program) {
        VertexFormatUtil.bindAttributeLocation(this.vertexFormat, program);
    }
    
    @Override
    public Collection<String> getAttributeNames() {
        return this.attributeNames;
    }
    
    @Override
    public <T> T getMojangVertexFormat() {
        return (T)this.vertexFormat;
    }
    
    static {
        NULLABLE_SUPPLIER = (() -> null);
    }
}
