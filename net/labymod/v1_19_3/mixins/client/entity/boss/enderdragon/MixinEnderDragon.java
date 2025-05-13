// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.entity.boss.enderdragon;

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
import net.labymod.v1_19_3.mixins.client.entity.MixinMob;

@Mixin({ bsc.class })
public abstract class MixinEnderDragon extends MixinMob implements EnderDragon
{
    private List<EnderDragonPart> labyMod$parts;
    @Shadow
    @Final
    private bsa[] cj;
    
    @Insert(method = { "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V" }, at = @At("TAIL"))
    private void labyMod$subParts(final bdv<? extends bsc> type, final cjw level, final InsertInfo info) {
        this.labyMod$parts = new ArrayList<EnderDragonPart>();
        for (final bsa part : this.cj) {
            this.labyMod$parts.add((EnderDragonPart)part);
        }
    }
    
    @Override
    public List<EnderDragonPart> getParts() {
        return this.labyMod$parts;
    }
}
