// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.resources;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.transform.ResourceTransformerRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import java.io.InputStream;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ cew.class })
public class MixinSimpleResource
{
    @Mutable
    @Shadow
    @Final
    private InputStream d;
    private ResourceTransformerRegistry labyMod$resourceTransformerRegistry;
    
    @Redirect(method = { "<init>" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/resources/SimpleResource;resourceInputStream:Ljava/io/InputStream;"))
    private void labyMod$transformResourceInputStream(final cew resource, final InputStream stream) {
        if (this.labyMod$resourceTransformerRegistry == null) {
            this.labyMod$resourceTransformerRegistry = Laby.references().resourceTransformerRegistry();
        }
        this.d = ((DefaultResourceTransformerRegistry)this.labyMod$resourceTransformerRegistry).applyTransformers((ResourceLocation)resource.a(), stream);
    }
}
