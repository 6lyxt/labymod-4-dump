// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.compatibility.optifine;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.client.entity.player.OptiFinePlayer;

@DynamicMixin("optifine")
@Mixin({ gqj.class })
public abstract class MixinOptiFineAbstractClientPlayer implements OptiFinePlayer
{
    @Shadow
    @Dynamic
    private alr locationOfCape;
    
    @Shadow
    public abstract void setReloadCapeTimeMs(final long p0);
    
    @Nullable
    @Override
    public ResourceLocation getOptiFineCapeLocation() {
        final alr location = this.locationOfCape;
        return (location == null) ? null : ((ResourceLocation)location);
    }
    
    @Override
    public void bridge$optifine$setReloadCapeTime(final long millis) {
        this.setReloadCapeTimeMs(millis);
    }
}
