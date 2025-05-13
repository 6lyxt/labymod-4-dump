// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.emote.animation;

import java.util.Objects;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.client.render.model.animation.ModelPartAnimation;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.Model;
import net.labymod.core.client.render.model.animation.DefaultAnimationController;

public class EmoteAnimationApplier extends DefaultAnimationController.DefaultAnimationApplier
{
    @Override
    public void applyPosition(final Model model, final ModelPart modelPart, final FloatVector3 position, final ModelPartAnimation animation) {
        if (model instanceof HumanoidModel && !animation.parent().getMetaDefault(EmoteAnimationMeta.BLOCK_SNEAKING, false)) {
            modelPart.getAnimationTransformation().getTranslation().add(position);
            return;
        }
        super.applyPosition(model, modelPart, position, animation);
    }
    
    @Override
    public void applyRotation(final Model model, final ModelPart modelPart, final FloatVector3 rotation, final ModelPartAnimation animation) {
        if (model instanceof final HumanoidModel humanoidModel) {
            if (!animation.parent().getMetaDefault(EmoteAnimationMeta.BLOCK_SNEAKING, false)) {
                boolean b = false;
                Label_0069: {
                    if (model instanceof final PlayerModel playerModel) {
                        if (Objects.equals(modelPart, playerModel.getCloak())) {
                            b = true;
                            break Label_0069;
                        }
                    }
                    b = false;
                }
                final boolean cloak = b;
                if (humanoidModel.getBody().equals(modelPart) || cloak) {
                    modelPart.getAnimationTransformation().getRotation().add(rotation);
                    return;
                }
            }
        }
        super.applyRotation(model, modelPart, rotation, animation);
    }
}
