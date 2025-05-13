// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.entity.boss;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.ArrayList;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.entity.boss.enderdragon.EnderDragonPart;
import java.util.List;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.boss.enderdragon.EnderDragon;
import net.labymod.v1_12_2.mixins.client.entity.MixinMob;

@Mixin({ abd.class })
public abstract class MixinEntityDragon extends MixinMob implements EnderDragon
{
    @Shadow
    public abb[] bv;
    private List<EnderDragonPart> labyMod$parts;
    
    @Inject(method = { "<init>" }, at = { @At("RETURN") })
    private void labyMod$createApiParts(final amu world, final CallbackInfo ci) {
        this.labyMod$parts = new ArrayList<EnderDragonPart>();
        for (final abb entityDragonPart : this.bv) {
            this.labyMod$parts.add((EnderDragonPart)entityDragonPart);
        }
    }
    
    @Override
    public List<EnderDragonPart> getParts() {
        return this.labyMod$parts;
    }
}
