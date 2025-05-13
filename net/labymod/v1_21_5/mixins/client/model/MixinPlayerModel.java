// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.model;

import net.labymod.api.client.render.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.model.entity.player.PlayerModel;

@Mixin({ git.class })
public class MixinPlayerModel<T extends byf> extends MixinHumanoidModel<T> implements PlayerModel
{
    @Shadow
    @Final
    public gkr e;
    @Shadow
    @Final
    public gkr c;
    @Shadow
    @Final
    public gkr d;
    @Shadow
    @Final
    public gkr a;
    @Shadow
    @Final
    public gkr b;
    @Shadow
    @Final
    private boolean A;
    
    @Override
    public ModelPart getJacket() {
        return (ModelPart)this.e;
    }
    
    @Override
    public ModelPart getCloak() {
        return null;
    }
    
    @Override
    public ModelPart getLeftPants() {
        return (ModelPart)this.c;
    }
    
    @Override
    public ModelPart getRightPants() {
        return (ModelPart)this.d;
    }
    
    @Override
    public ModelPart getLeftSleeve() {
        return (ModelPart)this.a;
    }
    
    @Override
    public ModelPart getRightSleeve() {
        return (ModelPart)this.b;
    }
    
    @Override
    public boolean isSlim() {
        return this.A;
    }
}
