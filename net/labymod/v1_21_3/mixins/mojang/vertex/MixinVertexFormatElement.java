// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.mojang.vertex;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.core.thirdparty.optifine.vertex.OptiFineVertexFormatElement;

@DynamicMixin("optifine")
@Mixin({ fgy.class })
public abstract class MixinVertexFormatElement implements OptiFineVertexFormatElement
{
    @Shadow
    @Dynamic
    public abstract int getAttributeIndex();
    
    @Override
    public int bridge$optifine$getAttributeIndex() {
        return this.getAttributeIndex();
    }
}
