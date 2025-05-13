// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.client.entity.item;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.entity.LivingEntity;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.item.PrimedTnt;
import net.labymod.v1_21_1.mixins.client.entity.MixinEntity;

@Mixin({ cji.class })
@Implements({ @Interface(iface = PrimedTnt.class, prefix = "primedTnt$", remap = Interface.Remap.NONE) })
public abstract class MixinPrimedTnt extends MixinEntity implements PrimedTnt
{
    @Shadow
    public abstract int shadow$t();
    
    @Shadow
    public abstract btn shadow$p();
    
    @Intrinsic
    public int primedTnt$getFuse() {
        return this.shadow$t();
    }
    
    @Intrinsic
    @Nullable
    public LivingEntity primedTnt$getOwner() {
        final btn livingEntity = this.shadow$p();
        if (livingEntity == null) {
            return null;
        }
        return (LivingEntity)livingEntity;
    }
}
