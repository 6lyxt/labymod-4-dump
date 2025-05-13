// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.platform;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import net.labymod.v1_21_5.client.render.vertex.VersionedVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.labymod.api.Laby;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.labymod.v1_21_5.client.render.LabyRenderPipelines;
import net.labymod.api.client.render.vertex.VertexFormatType;
import net.labymod.core.platform.PlatformScreenHandler;
import net.labymod.api.client.Minecraft;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.core.platform.Platform;

@AutoService(value = Platform.class, versionSpecific = true)
public class VersionedPlatform extends Platform
{
    public void onInitialization() {
        this.setMinecraft((Minecraft)fqq.Q());
        this.setPlatformScreenHandler(new VersionedPlatformScreenHandler());
        this.registerVertexFormats();
    }
    
    private void registerVertexFormats() {
        this.register(VertexFormatType.POSITION_COLOR, LabyRenderPipelines.POSITION_COLOR);
        this.register(VertexFormatType.POSITION_TEXTURE_COLOR, LabyRenderPipelines.POSITION_TEX_COLOR);
        this.register(VertexFormatType.POSITION_COLOR_NORMAL, this.createFormat(flb.g, grw.W));
        this.register(VertexFormatType.ENTITY, this.createFormat(flb.c, grw.q));
        this.register(VertexFormatType.POSITION_COLOR_TEXTURE_LIGHTMAP, LabyRenderPipelines.POSITION_COLOR_TEX_LIGHTMAP);
    }
    
    @Override
    public void onPostStartup() {
    }
    
    private void register(final VertexFormatType type, final RenderPipeline renderPipeline) {
        this.register(type, this.createFormat(renderPipeline.getVertexFormat(), renderPipeline));
    }
    
    private void register(final VertexFormatType type, final OldVertexFormat format) {
        Laby.references().renderPipeline().vertexFormatRegistry().register(type, format);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    private OldVertexFormat createFormat(final VertexFormat format, final RenderPipeline renderPipeline) {
        return new VersionedVertexFormat(format, renderPipeline);
    }
}
