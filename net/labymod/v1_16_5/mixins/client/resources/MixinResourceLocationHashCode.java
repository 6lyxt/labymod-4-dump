// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.resources;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ vk.class })
public class MixinResourceLocationHashCode
{
    @Shadow
    @Final
    protected String c;
    @Shadow
    @Final
    protected String b;
    private int labyMod$hashCode;
    
    @Inject(method = { "<init>([Ljava/lang/String;)V" }, at = { @At("TAIL") })
    private void labyMod$createHashCode(final String[] $$0, final CallbackInfo ci) {
        this.labyMod$hashCode = 31 * this.b.hashCode() + this.c.hashCode();
    }
    
    @Overwrite
    @Override
    public int hashCode() {
        return this.labyMod$hashCode;
    }
}
