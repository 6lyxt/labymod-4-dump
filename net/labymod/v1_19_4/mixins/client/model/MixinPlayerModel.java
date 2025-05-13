// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.model;

import net.labymod.api.client.render.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.model.entity.player.PlayerModel;

@Mixin({ fax.class })
public class MixinPlayerModel<T extends bfx> extends MixinHumanoidModel<T> implements PlayerModel
{
    @Shadow
    @Final
    public fcr z;
    @Shadow
    @Final
    private fcr G;
    @Shadow
    @Final
    public fcr x;
    @Shadow
    @Final
    public fcr y;
    @Shadow
    @Final
    public fcr b;
    @Shadow
    @Final
    public fcr w;
    @Shadow
    @Final
    private boolean I;
    
    @Override
    public ModelPart getJacket() {
        return (ModelPart)this.z;
    }
    
    @Override
    public ModelPart getCloak() {
        return (ModelPart)this.G;
    }
    
    @Override
    public ModelPart getLeftPants() {
        return (ModelPart)this.x;
    }
    
    @Override
    public ModelPart getRightPants() {
        return (ModelPart)this.y;
    }
    
    @Override
    public ModelPart getLeftSleeve() {
        return (ModelPart)this.b;
    }
    
    @Override
    public ModelPart getRightSleeve() {
        return (ModelPart)this.w;
    }
    
    @Override
    public boolean isSlim() {
        return this.I;
    }
}
