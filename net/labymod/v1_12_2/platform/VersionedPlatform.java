// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.platform;

import net.labymod.v1_12_2.client.render.vertex.VersionedVertexFormat;
import net.labymod.v1_12_2.client.render.vertex.CustomVertexFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import net.labymod.api.Laby;
import net.labymod.core.client.render.batch.DefaultRenderContexts;
import net.labymod.api.client.render.vertex.VertexFormatType;
import net.labymod.core.platform.PlatformScreenHandler;
import net.labymod.api.client.Minecraft;
import net.labymod.api.service.annotation.AutoService;
import javax.inject.Singleton;
import net.labymod.core.platform.Platform;

@Singleton
@AutoService(value = Platform.class, versionSpecific = true)
public class VersionedPlatform extends Platform
{
    public void onInitialization() {
        this.setMinecraft((Minecraft)bib.z());
        this.setPlatformScreenHandler(new VersionedPlatformScreenHandler());
        this.registerVertexFormats();
    }
    
    private void registerVertexFormats() {
        this.register(VertexFormatType.POSITION_COLOR, this.createFormat(cdy.f));
        this.register(VertexFormatType.POSITION_TEXTURE_COLOR, this.createFormat(cdy.i));
        this.register(VertexFormatType.ENTITY, this.createFormat(this.createEntityFormat()));
        this.register(VertexFormatType.POSITION_COLOR_NORMAL, this.createFormat(this.createPositionColorNormalFormat()));
        this.register(VertexFormatType.POSITION_COLOR_TEXTURE_LIGHTMAP, this.createFormat(this.createPositionTextureLightmapFormat()));
    }
    
    @Override
    public void onPostStartup() {
        ((DefaultRenderContexts)Laby.references().renderContexts()).setCurrentStack(VersionedStackProvider.DEFAULT_STACK);
    }
    
    private cea createPositionColorNormalFormat() {
        final cea format = new cea();
        format.a(cdy.m);
        format.a(cdy.n);
        format.a(cdy.q);
        return format;
    }
    
    private cea createPositionTextureLightmapFormat() {
        final cea format = new cea();
        format.a(cdy.m);
        format.a(cdy.n);
        format.a(cdy.o);
        format.a(cdy.p);
        return format;
    }
    
    private cea createEntityFormat() {
        final cea format = new cea();
        format.a(cdy.m);
        format.a(cdy.n);
        format.a(cdy.o);
        format.a(cdy.p);
        return format;
    }
    
    private void register(final VertexFormatType type, final OldVertexFormat format) {
        Laby.references().oldVertexFormatRegistry().register(type, format);
    }
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    private OldVertexFormat createFormat(final cea format) {
        return this.createFormat(format, false);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    private OldVertexFormat createFormat(final cea format, final boolean custom) {
        if (custom) {
            ((CustomVertexFormat)format).setCustom();
        }
        return new VersionedVertexFormat(format);
    }
}
