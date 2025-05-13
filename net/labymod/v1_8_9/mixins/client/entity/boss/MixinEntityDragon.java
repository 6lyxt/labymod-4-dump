// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.entity.boss;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.ArrayList;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.entity.boss.enderdragon.EnderDragonPart;
import java.util.List;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.boss.enderdragon.EnderDragon;
import net.labymod.v1_8_9.mixins.client.entity.MixinMob;

@Mixin({ ug.class })
public abstract class MixinEntityDragon extends MixinMob implements EnderDragon
{
    @Shadow
    public ue[] bm;
    private List<EnderDragonPart> labyMod$parts;
    
    @Inject(method = { "<init>" }, at = { @At("RETURN") })
    private void labyMod$createApiParts(final adm world, final CallbackInfo ci) {
        this.labyMod$parts = new ArrayList<EnderDragonPart>();
        for (final ue entityDragonPart : this.bm) {
            this.labyMod$parts.add((EnderDragonPart)entityDragonPart);
        }
    }
    
    @Override
    public List<EnderDragonPart> getParts() {
        return this.labyMod$parts;
    }
}
