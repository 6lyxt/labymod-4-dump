// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.entity.boss;

import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.boss.enderdragon.EnderDragonPart;
import net.labymod.v1_8_9.mixins.client.entity.MixinEntity;

@Mixin({ ue.class })
public abstract class MixinEntityDragonPart extends MixinEntity implements EnderDragonPart
{
}
