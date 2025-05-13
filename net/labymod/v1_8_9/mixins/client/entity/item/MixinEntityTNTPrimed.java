// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.entity.item;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.entity.LivingEntity;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.item.PrimedTnt;
import net.labymod.v1_8_9.mixins.client.entity.MixinEntity;

@Mixin({ vj.class })
public abstract class MixinEntityTNTPrimed extends MixinEntity implements PrimedTnt
{
    @Shadow
    public int a;
    
    @Shadow
    public abstract pr j();
    
    @Override
    public int getFuse() {
        return this.a;
    }
    
    @Nullable
    @Override
    public LivingEntity getOwner() {
        final pr tntPlacedBy = this.j();
        if (tntPlacedBy == null) {
            return null;
        }
        return (LivingEntity)tntPlacedBy;
    }
}
