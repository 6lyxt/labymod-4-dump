// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.platform;

import net.labymod.v1_8_9.client.render.vertex.VersionedVertexFormat;
import net.labymod.v1_8_9.client.render.vertex.CustomVertexFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
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
        this.setMinecraft((Minecraft)ave.A());
        this.setPlatformScreenHandler(new VersionedPlatformScreenHandler());
        this.registerVertexFormats();
    }
    
    private void registerVertexFormats() {
        this.register(VertexFormatType.POSITION_COLOR, this.createFormat(bms.f));
        this.register(VertexFormatType.POSITION_TEXTURE_COLOR, this.createFormat(bms.i));
        this.register(VertexFormatType.ENTITY, this.createFormat(this.createEntityFormat()));
        this.register(VertexFormatType.POSITION_COLOR_NORMAL, this.createFormat(this.createPositionColorNormalFormat()));
        this.register(VertexFormatType.POSITION_COLOR_TEXTURE_LIGHTMAP, this.createFormat(this.createPositionTextureLightmapFormat()));
    }
    
    @Override
    public void onPostStartup() {
        ((DefaultRenderContexts)Laby.references().renderContexts()).setCurrentStack(VersionedStackProvider.DEFAULT_STACK);
    }
    
    private bmu createPositionColorNormalFormat() {
        final bmu format = new bmu();
        format.a(bms.m);
        format.a(bms.n);
        format.a(bms.q);
        return format;
    }
    
    private bmu createPositionTextureLightmapFormat() {
        final bmu format = new bmu();
        format.a(bms.m);
        format.a(bms.n);
        format.a(bms.o);
        format.a(bms.p);
        return format;
    }
    
    private bmu createEntityFormat() {
        final bmu format = new bmu();
        format.a(bms.m);
        format.a(bms.n);
        format.a(bms.o);
        format.a(bms.p);
        return format;
    }
    
    private void register(final VertexFormatType type, final OldVertexFormat format) {
        Laby.references().oldVertexFormatRegistry().register(type, format);
    }
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    private OldVertexFormat createFormat(final bmu format) {
        return this.createFormat(format, false);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    private OldVertexFormat createFormat(final bmu format, final boolean custom) {
        if (custom) {
            ((CustomVertexFormat)format).setCustom();
        }
        return new VersionedVertexFormat(format);
    }
}
