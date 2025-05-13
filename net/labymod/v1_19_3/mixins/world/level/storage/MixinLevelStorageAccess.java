// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.world.level.storage;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.world.storage.accessor.LevelStorageAccessor;

@Mixin({ dve.c.class })
public class MixinLevelStorageAccess implements LevelStorageAccessor
{
    @Shadow
    @Final
    private String d;
    
    @Override
    public String getLevelId() {
        return this.d;
    }
}
