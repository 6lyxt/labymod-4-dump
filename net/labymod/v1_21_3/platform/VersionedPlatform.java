// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.platform;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import net.labymod.v1_21_3.client.render.vertex.VersionedVertexFormat;
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
        this.setMinecraft((Minecraft)fmg.Q());
        this.setPlatformScreenHandler(new VersionedPlatformScreenHandler());
        this.registerVertexFormats();
    }
    
    private void registerVertexFormats() {
        this.register(VertexFormatType.POSITION_COLOR, this.createFormat(fgq.f, gkv.e));
        this.register(VertexFormatType.POSITION_TEXTURE_COLOR, this.createFormat(fgq.j, gkv.i));
        this.register(VertexFormatType.POSITION_COLOR_NORMAL, this.createFormat(fgq.g, gkv.X));
        this.register(VertexFormatType.ENTITY, this.createFormat(fgq.c, gkv.v));
        this.register(VertexFormatType.POSITION_COLOR_TEXTURE_LIGHTMAP, this.createFormat(fgq.k, gkv.g));
    }
    
    @Override
    public void onPostStartup() {
    }
    
    private void register(final VertexFormatType type, final OldVertexFormat format) {
        Laby.references().renderPipeline().vertexFormatRegistry().register(type, format);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    private OldVertexFormat createFormat(final fgx format, final gmd shaderProgram) {
        return new VersionedVertexFormat(format, shaderProgram);
    }
}
