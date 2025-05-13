// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client.renderer.state;

import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_4.client.util.EntityRenderStateAccessor;

@Mixin({ gyl.class })
public class MixinEntityRenderState implements EntityRenderStateAccessor<bum>
{
    private bum labyMod$entity;
    
    @Override
    public bum labyMod$getEntity() {
        return this.labyMod$entity;
    }
    
    @Override
    public void labyMod$setEntity(final bum entity) {
        this.labyMod$entity = entity;
    }
}
