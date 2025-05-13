// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.model;

import net.labymod.api.client.render.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.model.entity.player.PlayerModel;

@Mixin({ flc.class })
public class MixinPlayerModel<T extends bml> extends MixinHumanoidModel<T> implements PlayerModel
{
    @Shadow
    @Final
    public fmx z;
    @Shadow
    @Final
    private fmx G;
    @Shadow
    @Final
    public fmx x;
    @Shadow
    @Final
    public fmx y;
    @Shadow
    @Final
    public fmx b;
    @Shadow
    @Final
    public fmx w;
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
