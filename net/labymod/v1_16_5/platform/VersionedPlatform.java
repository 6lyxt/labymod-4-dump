// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.platform;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import net.labymod.v1_16_5.client.render.vertex.VersionedVertexFormat;
import net.labymod.api.Laby;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import com.google.common.collect.ImmutableList;
import net.labymod.api.client.render.vertex.VertexFormatType;
import net.labymod.core.platform.PlatformScreenHandler;
import net.labymod.api.client.Minecraft;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.core.platform.Platform;

@AutoService(value = Platform.class, versionSpecific = true)
public class VersionedPlatform extends Platform
{
    public void onInitialization() {
        this.setMinecraft((Minecraft)djz.C());
        this.setPlatformScreenHandler(new VersionedPlatformScreenHandler());
        this.registerVertexFormats();
    }
    
    private void registerVertexFormats() {
        this.register(VertexFormatType.POSITION_COLOR, this.createFormat(dfk.l));
        this.register(VertexFormatType.POSITION_TEXTURE_COLOR, this.createFormat(dfk.p));
        this.register(VertexFormatType.ENTITY, this.createFormat(dfk.i));
        this.register(VertexFormatType.POSITION_COLOR_NORMAL, this.createFormat(this.createPositionColorNormalFormat()));
        this.register(VertexFormatType.POSITION_COLOR_TEXTURE_LIGHTMAP, this.createFormat(dfk.q));
    }
    
    @Override
    public void onPostStartup() {
    }
    
    private dfr createPositionColorNormalFormat() {
        return new dfr(ImmutableList.of((Object)dfk.a, (Object)dfk.b, (Object)dfk.f));
    }
    
    private void register(final VertexFormatType type, final OldVertexFormat format) {
        Laby.references().renderPipeline().vertexFormatRegistry().register(type, format);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    private OldVertexFormat createFormat(final dfr format) {
        return new VersionedVertexFormat(format);
    }
}
