// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.entity.boss.enderdragon;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import java.util.ArrayList;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.client.entity.boss.enderdragon.EnderDragonPart;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.boss.enderdragon.EnderDragon;
import net.labymod.v1_20_2.mixins.client.entity.MixinMob;

@Mixin({ bxh.class })
public abstract class MixinEnderDragon extends MixinMob implements EnderDragon
{
    private List<EnderDragonPart> labyMod$parts;
    @Shadow
    @Final
    private bxf[] cf;
    
    @Insert(method = { "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V" }, at = @At("TAIL"))
    private void labyMod$subParts(final biu<? extends bxh> type, final cpv level, final InsertInfo info) {
        this.labyMod$parts = new ArrayList<EnderDragonPart>();
        for (final bxf part : this.cf) {
            this.labyMod$parts.add((EnderDragonPart)part);
        }
    }
    
    @Override
    public List<EnderDragonPart> getParts() {
        return this.labyMod$parts;
    }
}
