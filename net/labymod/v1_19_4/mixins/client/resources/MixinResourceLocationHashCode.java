// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.resources;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ add.class })
public class MixinResourceLocationHashCode
{
    @Shadow
    @Final
    private String g;
    @Shadow
    @Final
    private String f;
    private int labyMod$hashCode;
    private boolean labyMod$calculateHashCode;
    
    public MixinResourceLocationHashCode() {
        this.labyMod$calculateHashCode = true;
    }
    
    @Overwrite
    @Override
    public int hashCode() {
        if (this.labyMod$calculateHashCode) {
            this.labyMod$hashCode = 31 * this.f.hashCode() + this.g.hashCode();
            this.labyMod$calculateHashCode = false;
        }
        return this.labyMod$hashCode;
    }
}
