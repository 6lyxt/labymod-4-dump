// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.util;

import net.labymod.api.util.io.IOUtil;
import net.labymod.core.util.ResourcesUtil;
import java.io.IOException;
import net.labymod.api.util.ide.IdeUtil;
import java.io.InputStream;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.metadata.Metadata;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.resources.ResourceLocation;

@Mixin({ jy.class })
public abstract class MixinResourceLocation implements ResourceLocation
{
    private transient Metadata labyMod$metadata;
    
    public MixinResourceLocation() {
        this.labyMod$metadata = Metadata.create();
    }
    
    @Shadow
    public abstract String a();
    
    @Shadow
    public abstract String b();
    
    @Override
    public String getNamespace() {
        return this.b();
    }
    
    @Override
    public String getPath() {
        return this.a();
    }
    
    @Override
    public InputStream openStream() throws IOException {
        IdeUtil.ensureResourcesLoaded(this);
        return ave.A().Q().a((jy)this.getMinecraftLocation()).b();
    }
    
    @Override
    public boolean exists() {
        return ResourcesUtil.hasResource(this.getMinecraftLocation()) || this.labyMod$hasResource();
    }
    
    @Override
    public <T> T getMinecraftLocation() {
        return (T)this;
    }
    
    @Override
    public void metadata(final Metadata metadata) {
        this.labyMod$metadata = metadata;
    }
    
    @Override
    public Metadata metadata() {
        return this.labyMod$metadata;
    }
    
    private boolean labyMod$hasResource() {
        try {
            final bnh resource = ave.A().Q().a((jy)this.getMinecraftLocation());
            final InputStream stream = resource.b();
            IOUtil.closeSilent(stream);
            return true;
        }
        catch (final IOException exception) {
            return false;
        }
    }
}
