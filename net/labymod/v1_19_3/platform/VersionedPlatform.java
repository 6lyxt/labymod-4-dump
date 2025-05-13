// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.platform;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import net.labymod.v1_19_3.client.render.vertex.VersionedVertexFormat;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import net.labymod.api.client.render.vertex.VertexFormatType;
import net.labymod.core.platform.PlatformScreenHandler;
import net.labymod.api.client.Minecraft;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.core.platform.Platform;

@AutoService(value = Platform.class, versionSpecific = true)
public class VersionedPlatform extends Platform
{
    public void onInitialization() {
        this.setMinecraft((Minecraft)ejf.N());
        this.setPlatformScreenHandler(new VersionedPlatformScreenHandler());
        this.registerVertexFormats();
    }
    
    private void registerVertexFormats() {
        this.register(VertexFormatType.POSITION_COLOR, this.createFormat(eeb.n, fdo::q));
        this.register(VertexFormatType.POSITION_TEXTURE_COLOR, this.createFormat(eeb.s, fdo::t));
        this.register(VertexFormatType.POSITION_COLOR_NORMAL, this.createFormat(eeb.o, fdo::aq));
        this.register(VertexFormatType.ENTITY, this.createFormat(eeb.k, fdo::v));
        this.register(VertexFormatType.POSITION_COLOR_TEXTURE_LIGHTMAP, this.createFormat(eeb.t, fdo::y));
    }
    
    @Override
    public void onPostStartup() {
    }
    
    private void register(final VertexFormatType type, final OldVertexFormat format) {
        Laby.references().renderPipeline().vertexFormatRegistry().register(type, format);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    private OldVertexFormat createFormat(final eei format, final Supplier<feg> shaderSupplier) {
        return new VersionedVertexFormat(format, shaderSupplier);
    }
}
