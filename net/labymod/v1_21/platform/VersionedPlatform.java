// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.platform;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import net.labymod.v1_21.client.render.vertex.VersionedVertexFormat;
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
        this.setMinecraft((Minecraft)fgo.Q());
        this.setPlatformScreenHandler(new VersionedPlatformScreenHandler());
        this.registerVertexFormats();
    }
    
    private void registerVertexFormats() {
        this.register(VertexFormatType.POSITION_COLOR, this.createFormat(fbg.f, ges::p));
        this.register(VertexFormatType.POSITION_TEXTURE_COLOR, this.createFormat(fbg.j, ges::r));
        this.register(VertexFormatType.POSITION_COLOR_NORMAL, this.createFormat(fbg.g, ges::an));
        this.register(VertexFormatType.ENTITY, this.createFormat(fbg.c, ges::H));
        this.register(VertexFormatType.POSITION_COLOR_TEXTURE_LIGHTMAP, this.createFormat(fbg.k, ges::u));
    }
    
    @Override
    public void onPostStartup() {
    }
    
    private void register(final VertexFormatType type, final OldVertexFormat format) {
        Laby.references().renderPipeline().vertexFormatRegistry().register(type, format);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    private OldVertexFormat createFormat(final fbn format, final Supplier<gfn> shaderSupplier) {
        return new VersionedVertexFormat(format, shaderSupplier);
    }
}
