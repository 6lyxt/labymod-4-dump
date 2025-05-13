// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.resources;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ yt.class })
public class MixinResourceLocationHashCode
{
    @Shadow
    @Final
    protected String f;
    @Shadow
    @Final
    protected String e;
    private int labyMod$hashCode;
    
    @Inject(method = { "<init>([Ljava/lang/String;)V" }, at = { @At("TAIL") })
    private void labyMod$createHashCode(final String[] $$0, final CallbackInfo ci) {
        this.labyMod$hashCode = 31 * this.e.hashCode() + this.f.hashCode();
    }
    
    @Overwrite
    @Override
    public int hashCode() {
        return this.labyMod$hashCode;
    }
}
