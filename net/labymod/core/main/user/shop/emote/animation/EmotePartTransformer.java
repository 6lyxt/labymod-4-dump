// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.emote.animation;

import net.labymod.api.client.render.model.animation.ModelPartTransformation;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.model.animation.ModelPartAnimation;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.render.model.animation.AnimationController;

public class EmotePartTransformer implements AnimationController.PartTransformer
{
    private static final String CHEST_PART = "chest";
    private final FloatVector3 originalLastRotation;
    private boolean transformed;
    
    public EmotePartTransformer() {
        this.originalLastRotation = new FloatVector3();
        this.transformed = false;
    }
    
    @Override
    public String remapName(final Model model, final String partName) {
        return (model instanceof HumanoidModel && partName.equals("chest")) ? "body" : partName;
    }
    
    @Override
    public void preAnimationApply(final Model model, final String partName, final ModelPart modelPart, final ModelPartAnimation animation) {
        if (!(model instanceof HumanoidModel)) {
            return;
        }
        final HumanoidModel humanoidModel = (HumanoidModel)model;
        if (animation.parent().getMetaDefault(EmoteAnimationMeta.TRIGGER_EMOTE, false)) {
            return;
        }
        final ModelPartTransformation position = animation.position();
        final ModelPartTransformation rotation = animation.rotation();
        final ModelPartTransformation scale = animation.scale();
        position.last().set(0.0f, 0.0f, 0.0f);
        this.originalLastRotation.set(rotation.last());
        if (humanoidModel.getBody().equals(modelPart) || "cloak".equals(partName)) {
            rotation.last().set(0.0f, 0.0f, 0.0f);
        }
        else {
            final FloatVector3 playerPartRotation = modelPart.getAnimationTransformation().getRotation();
            final float rotX = MathHelper.convertRadians(this.originalLastRotation.getX(), playerPartRotation.getX());
            final float rotY = MathHelper.convertRadians(this.originalLastRotation.getY(), playerPartRotation.getY());
            final float rotZ = MathHelper.convertRadians(this.originalLastRotation.getZ(), playerPartRotation.getZ());
            rotation.last().set(rotX, rotY, rotZ);
        }
        scale.last().set(modelPart.getAnimationTransformation().getScale());
        this.transformed = true;
    }
    
    @Override
    public void postAnimationApply(final Model model, final String partName, final ModelPart modelPart, final ModelPartAnimation animation) {
        if (this.transformed) {
            animation.rotation().last().set(this.originalLastRotation);
            this.transformed = false;
        }
    }
}
