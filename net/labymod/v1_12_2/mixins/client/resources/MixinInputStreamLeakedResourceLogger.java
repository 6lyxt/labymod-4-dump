// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.resources;

import java.io.IOException;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import java.io.InputStream;

@Mixin(targets = { "net.minecraft.client.resources.FallbackResourceManager$InputStreamLeakedResourceLogger" })
public abstract class MixinInputStreamLeakedResourceLogger extends InputStream
{
    @Shadow
    @Final
    private InputStream a;
    @Shadow
    private boolean c;
    
    @Override
    public int available() throws IOException {
        if (this.c) {
            return 0;
        }
        return this.a.available();
    }
}
