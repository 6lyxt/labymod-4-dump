// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client.entity;

import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.Mob;

@Mixin({ btp.class })
public abstract class MixinMob extends MixinLivingEntity implements Mob
{
}
