// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.resources;

import net.labymod.core.util.ResourcesUtil;
import java.io.IOException;
import net.labymod.api.util.ide.IdeUtil;
import java.io.InputStream;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.metadata.Metadata;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.resources.ResourceLocation;

@Mixin({ ww.class })
@Implements({ @Interface(iface = ResourceLocation.class, prefix = "rl$") })
public abstract class MixinResourceLocation implements ResourceLocation
{
    private transient Metadata labyMod$metadata;
    
    public MixinResourceLocation() {
        this.labyMod$metadata = Metadata.create();
    }
    
    @Shadow
    public abstract String shadow$b();
    
    @Shadow
    public abstract String shadow$a();
    
    @Intrinsic
    public String rl$getNamespace() {
        return this.shadow$b();
    }
    
    @Intrinsic
    public String rl$getPath() {
        return this.shadow$a();
    }
    
    @Override
    public InputStream openStream() throws IOException {
        IdeUtil.ensureResourcesLoaded(this);
        return dvp.C().N().a((ww)this.getMinecraftLocation()).b();
    }
    
    @Override
    public boolean exists() {
        return ResourcesUtil.hasResource(this.getMinecraftLocation()) || dvp.C().N().b((ww)this.getMinecraftLocation());
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
}
