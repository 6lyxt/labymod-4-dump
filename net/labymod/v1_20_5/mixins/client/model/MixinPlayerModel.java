// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.model;

import net.labymod.api.client.render.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.model.entity.player.PlayerModel;

@Mixin({ fvg.class })
public class MixinPlayerModel<T extends btq> extends MixinHumanoidModel<T> implements PlayerModel
{
    @Shadow
    @Final
    public fxb z;
    @Shadow
    @Final
    private fxb G;
    @Shadow
    @Final
    public fxb x;
    @Shadow
    @Final
    public fxb y;
    @Shadow
    @Final
    public fxb b;
    @Shadow
    @Final
    public fxb w;
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
