// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.model;

import net.labymod.api.client.render.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.model.entity.player.PlayerModel;

@Mixin({ ehc.class })
public class MixinPlayerModel<T extends atu> extends MixinHumanoidModel<T> implements PlayerModel
{
    @Shadow
    @Final
    public eir w;
    @Shadow
    @Final
    private eir D;
    @Shadow
    @Final
    public eir u;
    @Shadow
    @Final
    public eir v;
    @Shadow
    @Final
    public eir b;
    @Shadow
    @Final
    public eir t;
    @Shadow
    @Final
    private boolean F;
    
    @Override
    public ModelPart getJacket() {
        return (ModelPart)this.w;
    }
    
    @Override
    public ModelPart getCloak() {
        return (ModelPart)this.D;
    }
    
    @Override
    public ModelPart getLeftPants() {
        return (ModelPart)this.u;
    }
    
    @Override
    public ModelPart getRightPants() {
        return (ModelPart)this.v;
    }
    
    @Override
    public ModelPart getLeftSleeve() {
        return (ModelPart)this.b;
    }
    
    @Override
    public ModelPart getRightSleeve() {
        return (ModelPart)this.t;
    }
    
    @Override
    public boolean isSlim() {
        return this.F;
    }
}
