// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.server.packs.resources;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import java.io.InputStream;
import net.labymod.api.client.resources.transform.ResourceTransformerRegistry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ aho.class })
public abstract class MixinVanillaPackResources
{
    private ResourceTransformerRegistry labyMod$resourceTransformerRegistry;
    
    @Shadow
    @Nullable
    protected abstract InputStream c(final ahm p0, final abb p1);
    
    @Redirect(method = { "getResource" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/VanillaPackResources;getResourceAsStream(Lnet/minecraft/server/packs/PackType;Lnet/minecraft/resources/ResourceLocation;)Ljava/io/InputStream;"))
    private InputStream labyMod$transformResourceInputStream(final aho resources, final ahm packType, final abb location) {
        if (this.labyMod$resourceTransformerRegistry == null) {
            this.labyMod$resourceTransformerRegistry = Laby.references().resourceTransformerRegistry();
        }
        return ((DefaultResourceTransformerRegistry)this.labyMod$resourceTransformerRegistry).applyTransformers((ResourceLocation)location, this.c(packType, location));
    }
}
