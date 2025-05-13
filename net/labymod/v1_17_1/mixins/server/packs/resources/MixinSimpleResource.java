// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.server.packs.resources;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import java.io.InputStream;
import net.labymod.api.client.resources.transform.ResourceTransformerRegistry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ aea.class })
public class MixinSimpleResource
{
    private ResourceTransformerRegistry labyMod$resourceTransformerRegistry;
    @Mutable
    @Shadow
    @Final
    private InputStream c;
    
    @Redirect(method = { "<init>" }, at = @At(value = "FIELD", target = "Lnet/minecraft/server/packs/resources/SimpleResource;resourceStream:Ljava/io/InputStream;"))
    private void labyMod$transformResourceInputStream(final aea resource, final InputStream stream) {
        if (this.labyMod$resourceTransformerRegistry == null) {
            this.labyMod$resourceTransformerRegistry = Laby.references().resourceTransformerRegistry();
        }
        final ww location = resource.a();
        this.c = ((DefaultResourceTransformerRegistry)this.labyMod$resourceTransformerRegistry).applyTransformers((ResourceLocation)location, stream);
    }
}
