// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.platform;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import net.labymod.v1_21_4.client.render.vertex.VersionedVertexFormat;
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
        this.setMinecraft((Minecraft)flk.Q());
        this.setPlatformScreenHandler(new VersionedPlatformScreenHandler());
        this.registerVertexFormats();
    }
    
    private void registerVertexFormats() {
        this.register(VertexFormatType.POSITION_COLOR, this.createFormat(fft.f, glk.e));
        this.register(VertexFormatType.POSITION_TEXTURE_COLOR, this.createFormat(fft.j, glk.i));
        this.register(VertexFormatType.POSITION_COLOR_NORMAL, this.createFormat(fft.g, glk.X));
        this.register(VertexFormatType.ENTITY, this.createFormat(fft.c, glk.v));
        this.register(VertexFormatType.POSITION_COLOR_TEXTURE_LIGHTMAP, this.createFormat(fft.k, glk.g));
    }
    
    @Override
    public void onPostStartup() {
    }
    
    private void register(final VertexFormatType type, final OldVertexFormat format) {
        Laby.references().renderPipeline().vertexFormatRegistry().register(type, format);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    private OldVertexFormat createFormat(final fga format, final gmr shaderProgram) {
        return new VersionedVertexFormat(format, shaderProgram);
    }
}
