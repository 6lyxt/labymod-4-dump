// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.util;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ nf.class })
public class MixinResourceLocationHashCode
{
    @Shadow
    @Final
    protected String a;
    @Shadow
    @Final
    protected String b;
    private int labyMod$hashCode;
    
    @Inject(method = { "<init>(I[Ljava/lang/String;)V" }, at = { @At("TAIL") })
    private void labyMod$createHashCode(final int lvt_1_1_, final String[] lvt_2_1_, final CallbackInfo ci) {
        this.labyMod$hashCode = 31 * this.a.hashCode() + this.b.hashCode();
    }
    
    @Overwrite
    @Override
    public int hashCode() {
        return this.labyMod$hashCode;
    }
}
