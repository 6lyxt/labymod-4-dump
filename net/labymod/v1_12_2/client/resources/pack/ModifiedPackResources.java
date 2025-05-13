// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.resources.pack;

import net.labymod.api.client.resources.ResourceLocation;
import java.awt.image.BufferedImage;
import org.jetbrains.annotations.Nullable;
import java.util.Set;
import java.io.IOException;
import java.io.InputStream;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.pack.ResourcePack;

public class ModifiedPackResources implements cer
{
    private final ResourcePack resourcePack;
    private final Resources resources;
    
    public ModifiedPackResources(final ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
        this.resources = LabyMod.getInstance().renderPipeline().resources();
    }
    
    public InputStream a(final nf resourceLocation) throws IOException {
        return this.resourcePack.getClientResource(this.getResourceLocation(resourceLocation));
    }
    
    public boolean b(final nf resourceLocation) {
        return this.resourcePack.hasClientResource(this.getResourceLocation(resourceLocation));
    }
    
    public Set<String> c() {
        return this.resourcePack.getClientNamespaces();
    }
    
    @Nullable
    public <T extends cfe> T a(final cfg metadataSerializer, final String s) throws IOException {
        return null;
    }
    
    public BufferedImage a() throws IOException {
        return null;
    }
    
    public String b() {
        return this.resourcePack.getName();
    }
    
    private ResourceLocation getResourceLocation(final nf location) {
        return this.resources.resourceLocationFactory().create(location.b(), location.a());
    }
}
