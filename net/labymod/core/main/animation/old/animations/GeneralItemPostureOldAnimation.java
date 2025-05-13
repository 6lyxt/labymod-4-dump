// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old.animations;

import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.core.main.animation.old.BlockingSwordAccessor;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

public class GeneralItemPostureOldAnimation extends AbstractOldAnimation
{
    public static final String NAME = "general_item_posture";
    public static final boolean LEGACY_PVP;
    private final BlockingSwordAccessor swordAnimation;
    
    public GeneralItemPostureOldAnimation() {
        super("general_item_posture");
        this.swordAnimation = this.getAnimation(GeneralItemPostureOldAnimation.LEGACY_PVP ? "legacy_sword" : "sword");
    }
    
    @Subscribe
    public void onRenderItemInHand(final RenderFirstPersonItemInHandEvent event) {
        if (event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.POST_ATTACK_TRANSFORM || !this.isEnabled()) {
            return;
        }
        final ItemStack itemStack = event.itemStack();
        if (!itemStack.isItem() || itemStack.isFishingTool()) {
            return;
        }
        if (itemStack.isBow()) {
            return;
        }
        if (itemStack.isSword() && this.swordAnimation.isBlockingWithSword(event.player()) && this.swordAnimation.isEnabled()) {
            return;
        }
        if (itemStack.isFood()) {
            return;
        }
        if (itemStack.isFishingTool()) {
            return;
        }
        final Stack stack = event.stack();
        apply(stack, event.hand());
    }
    
    @Override
    public boolean isEnabled() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldItemPosture());
    }
    
    public static void apply(final Stack stack, final LivingEntity.Hand hand) {
        if (GeneralItemPostureOldAnimation.LEGACY_PVP) {
            stack.translate(0.0f, -0.025f, -0.04375f);
            stack.rotate(4.0f, 0.0f, 1.0f, 0.0f);
        }
        else {
            final int position = (hand == LivingEntity.Hand.MAIN_HAND) ? 1 : -1;
            stack.translate(-0.025f * position, -0.005f, 0.0f);
            stack.rotate(2.0f, 0.0f, (float)position, 0.0f);
        }
    }
    
    static {
        LEGACY_PVP = MinecraftVersions.V1_8_9.orOlder();
    }
}
