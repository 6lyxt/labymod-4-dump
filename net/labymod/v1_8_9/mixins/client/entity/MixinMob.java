// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.entity;

import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.Mob;

@Mixin({ ps.class })
public abstract class MixinMob extends MixinEntityLivingBase implements Mob
{
}
