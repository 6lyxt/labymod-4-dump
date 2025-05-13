// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.resources;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ alf.class })
public class MixinResourceLocationHashCode
{
    @Shadow
    @Final
    private String h;
    @Shadow
    @Final
    private String g;
    private int labyMod$hashCode;
    private boolean labyMod$calculateHashCode;
    
    public MixinResourceLocationHashCode() {
        this.labyMod$calculateHashCode = true;
    }
    
    @Overwrite
    @Override
    public int hashCode() {
        if (this.labyMod$calculateHashCode) {
            this.labyMod$hashCode = 31 * this.g.hashCode() + this.h.hashCode();
            this.labyMod$calculateHashCode = false;
        }
        return this.labyMod$hashCode;
    }
}
