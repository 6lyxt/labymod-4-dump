// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer.state;

import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_5.client.util.EntityRenderStateAccessor;

@Mixin({ hec.class })
public class MixinEntityRenderState implements EntityRenderStateAccessor<bxe>
{
    private bxe labyMod$entity;
    
    @Override
    public bxe labyMod$getEntity() {
        return this.labyMod$entity;
    }
    
    @Override
    public void labyMod$setEntity(final bxe entity) {
        this.labyMod$entity = entity;
    }
}
