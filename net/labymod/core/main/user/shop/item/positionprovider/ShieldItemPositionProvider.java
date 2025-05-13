// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.positionprovider;

import net.labymod.api.client.render.model.ModelPart;
import net.labymod.core.client.entity.player.DummyPlayerModel;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.render.matrix.Stack;

public class ShieldItemPositionProvider implements MinecraftItemPositionProvider
{
    @Override
    public void apply(final Stack stack, final PlayerModel playerModel) {
        final ModelPart rightArm = playerModel.getLeftArm();
        rightArm.getModelPartTransform().transform(stack);
        rightArm.getAnimationTransformation().transform(stack);
        stack.translate(0.0f, (playerModel instanceof DummyPlayerModel) ? 0.75f : 0.375f, 0.0f);
        stack.translate(0.21875f, 0.0f, 0.0f);
        stack.rotate(90.0f, 1.0f, 0.0f, 0.0f);
        stack.rotate(90.0f, 0.0f, 1.0f, 0.0f);
    }
}
