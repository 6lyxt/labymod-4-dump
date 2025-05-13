// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.renderer.state;

import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_3.client.util.EntityRenderStateAccessor;

@Mixin({ gxv.class })
public class MixinEntityRenderState implements EntityRenderStateAccessor<bvk>
{
    private bvk labyMod$entity;
    
    @Override
    public bvk labyMod$getEntity() {
        return this.labyMod$entity;
    }
    
    @Override
    public void labyMod$setEntity(final bvk entity) {
        this.labyMod$entity = entity;
    }
}
