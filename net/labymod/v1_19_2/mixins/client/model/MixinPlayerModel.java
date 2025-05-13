// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.model;

import net.labymod.api.client.render.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.model.entity.player.PlayerModel;

@Mixin({ esn.class })
public class MixinPlayerModel<T extends bcc> extends MixinHumanoidModel<T> implements PlayerModel
{
    @Shadow
    @Final
    public eue y;
    @Shadow
    @Final
    private eue F;
    @Shadow
    @Final
    public eue w;
    @Shadow
    @Final
    public eue x;
    @Shadow
    @Final
    public eue b;
    @Shadow
    @Final
    public eue v;
    @Shadow
    @Final
    private boolean H;
    
    @Override
    public ModelPart getJacket() {
        return (ModelPart)this.y;
    }
    
    @Override
    public ModelPart getCloak() {
        return (ModelPart)this.F;
    }
    
    @Override
    public ModelPart getLeftPants() {
        return (ModelPart)this.w;
    }
    
    @Override
    public ModelPart getRightPants() {
        return (ModelPart)this.x;
    }
    
    @Override
    public ModelPart getLeftSleeve() {
        return (ModelPart)this.b;
    }
    
    @Override
    public ModelPart getRightSleeve() {
        return (ModelPart)this.v;
    }
    
    @Override
    public boolean isSlim() {
        return this.H;
    }
}
