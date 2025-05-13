// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.entity.boss.enderdragon;

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
import net.labymod.v1_18_2.mixins.client.entity.MixinMob;

@Mixin({ bkd.class })
public abstract class MixinEnderDragon extends MixinMob implements EnderDragon
{
    private List<EnderDragonPart> labyMod$parts;
    @Shadow
    @Final
    private bkb[] ch;
    
    @Insert(method = { "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V" }, at = @At("TAIL"))
    private void labyMod$subParts(final axo<? extends bkd> type, final cav level, final InsertInfo info) {
        this.labyMod$parts = new ArrayList<EnderDragonPart>();
        for (final bkb part : this.ch) {
            this.labyMod$parts.add((EnderDragonPart)part);
        }
    }
    
    @Override
    public List<EnderDragonPart> getParts() {
        return this.labyMod$parts;
    }
}
