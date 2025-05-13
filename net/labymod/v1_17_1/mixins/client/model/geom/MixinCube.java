// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.model.geom;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.client.render.model.box.DefaultModelBox;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.render.model.box.ModelBox;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.accessor.model.ModelPartCubeAccessor;

@Mixin({ eir.a.class })
public class MixinCube implements ModelPartCubeAccessor
{
    private ModelBox labyMod$modelBox;
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$constructModelBox(final int param0, final int param1, final float param2, final float param3, final float param4, final float param5, final float param6, final float param7, final float param8, final float param9, final float param10, final boolean mirror, final float param12, final float param13, final CallbackInfo ci) {
        this.labyMod$modelBox = new DefaultModelBox(param0, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, mirror, param12, param13);
    }
    
    @Override
    public ModelBox getModelBox() {
        return this.labyMod$modelBox;
    }
}
