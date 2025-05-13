// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old.animations;

import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.core.main.animation.old.animations.legacy.LegacySwordOldAnimation;
import net.labymod.api.event.client.render.overlay.HudWidgetDropzoneElementShiftEvent;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.event.client.render.model.entity.HumanoidModelAnimateEvent;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.HumanoidModelPoseAnimationEvent;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import java.util.HashMap;
import net.labymod.api.Laby;
import net.labymod.core.main.animation.old.OldAnimation;
import net.labymod.api.client.Minecraft;
import java.util.UUID;
import java.util.Map;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.animation.old.BlockingSwordAccessor;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

public class SwordOldAnimation extends AbstractOldAnimation implements BlockingSwordAccessor
{
    public static final String NAME = "sword";
    public static final FloatVector3 ARM_ROTATION_IDLE;
    public static final FloatVector3 ARM_ROTATION_BLOCKING;
    private final Map<UUID, Boolean> blocking;
    private final Minecraft minecraft;
    private final OldAnimation blockBuildAnimation;
    
    public SwordOldAnimation() {
        super("sword");
        this.minecraft = Laby.labyAPI().minecraft();
        this.blocking = new HashMap<UUID, Boolean>();
        this.blockBuildAnimation = this.getAnimation("block_build");
    }
    
    @Subscribe
    public void injectBlockAnimationInFirstPerson(final RenderFirstPersonItemInHandEvent event) {
        if (!this.isEnabled() || event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.PRE_RENDER) {
            return;
        }
        if (event.hand() != LivingEntity.Hand.MAIN_HAND || !this.isBlockingWithSword(event.player())) {
            return;
        }
        final Stack stack = event.stack();
        final int mirror = (event.side() == LivingEntity.HandSide.RIGHT) ? 1 : -1;
        stack.translate(-0.0375f * mirror, 0.0625f, 0.125f);
        stack.translate(-0.1f * mirror, 0.05f, 0.0f);
        stack.rotate(55.0f, 0.0f, 0.0f, (float)mirror);
        stack.rotate(90.0f, 0.0f, (float)mirror, 0.0f);
        stack.rotate(20.0f, -1.0f, 0.0f, 0.0f);
        stack.rotate(12.5f, 0.0f, (float)mirror, 0.0f);
        stack.rotate(15.0f, 0.0f, 0.0f, (float)(-1 * mirror));
    }
    
    @Subscribe
    public void cancelSwingVanillaAnimationInFirstPerson(final RenderFirstPersonItemInHandEvent event) {
        if (!this.isEnabled() || event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ARM_TRANSFORM) {
            return;
        }
        if (event.hand() != LivingEntity.Hand.MAIN_HAND || !this.isBlockingWithSword(event.player())) {
            return;
        }
        final boolean isRightHand = event.side() == LivingEntity.HandSide.RIGHT;
        final int mirror = isRightHand ? 1 : -1;
        final float attackProgress = event.getAttackProgress();
        final float x = -0.4f * MathHelper.sin(Math.sqrt(attackProgress) * 3.1415927410125732);
        final float y = 0.2f * MathHelper.sin(Math.sqrt(attackProgress) * 6.2831854820251465);
        final float z = -0.2f * MathHelper.sin(attackProgress * 3.1415927f);
        event.stack().translate(mirror * -x, -y, -z);
    }
    
    @Subscribe
    public void cancelShieldInOffHandFirstPerson(final RenderFirstPersonItemInHandEvent event) {
        if (!this.isEnabled() || event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.HEAD) {
            return;
        }
        if (event.hand() == LivingEntity.Hand.OFF_HAND && event.itemStack().isShield()) {
            event.setRenderItem(false);
        }
    }
    
    @Subscribe
    public void cancelVanillaArmPoseThirdPerson(final HumanoidModelPoseAnimationEvent event) {
        if (!this.isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        final LivingEntity entity = event.livingEntity();
        if (entity instanceof final Player player) {
            final ItemStack mainHandItemStack = player.getMainHandItemStack();
            final ItemStack offHandItemStack = player.getOffHandItemStack();
            if (this.isMainHand(player, event.handSide())) {
                if (mainHandItemStack.isSword()) {
                    event.setCancelled(true);
                }
                if (this.isBlockingWithShield(player)) {
                    event.setCancelled(true);
                }
            }
            else if (offHandItemStack.isShield()) {
                event.setCancelled(true);
            }
        }
    }
    
    @Subscribe
    public void injectArmPoseThirdPerson(final HumanoidModelAnimateEvent event) {
        if (!this.isEnabled() || event.phase() != Phase.POST) {
            return;
        }
        final LivingEntity entity = event.livingEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        final Player player = (Player)entity;
        if (!player.getMainHandItemStack().isSword() && !this.isBlockingWithShield(player)) {
            return;
        }
        final boolean isMainRight = this.isMainHand(player, LivingEntity.HandSide.RIGHT);
        final HumanoidModel model = event.model();
        final ModelPart mainArm = isMainRight ? model.getRightArm() : model.getLeftArm();
        final FloatVector3 rotation = mainArm.getAnimationTransformation().getRotation();
        rotation.add(this.isBlockingWithSword(player) ? SwordOldAnimation.ARM_ROTATION_BLOCKING : SwordOldAnimation.ARM_ROTATION_IDLE);
    }
    
    @Subscribe
    public void modifyItemsInHandInThirdPerson(final ItemInHandLayerRenderEvent event) {
        if (!this.isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        final LivingEntity entity = event.livingEntity();
        if (entity instanceof final Player player) {
            if (this.isMainHand(player, event.handSide())) {
                if (this.isBlockingWithSword(player)) {
                    final Stack stack = event.stack();
                    final int mirror = (event.handSide() == LivingEntity.HandSide.RIGHT) ? 1 : -1;
                    stack.translate(-0.14f * mirror, -0.12f, 0.14f);
                    stack.translate(-0.1f * mirror, 0.05f, 0.0f);
                    stack.rotate(50.0f, 0.0f, 0.0f, (float)mirror);
                    stack.rotate(10.0f, 1.0f, 0.0f, 0.0f);
                    stack.rotate(60.0f, 0.0f, (float)mirror, 0.0f);
                    stack.rotate(14.96f, 1.0f, 0.0f, 0.0f);
                    stack.rotate(3.9f, 0.0f, (float)(-mirror), 0.0f);
                    stack.rotate(6.23f, 0.0f, 0.0f, (float)mirror);
                }
            }
            else if (event.itemStack().isShield()) {
                event.setCancelled(true);
            }
        }
    }
    
    @Subscribe
    public void onIngameOverlayElementRender(final IngameOverlayElementRenderEvent event) {
        if (!this.isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        final IngameOverlayElementRenderEvent.OverlayElementType type = event.elementType();
        final ClientPlayer player = this.minecraft.getClientPlayer();
        if (player == null) {
            return;
        }
        if ((type == IngameOverlayElementRenderEvent.OverlayElementType.OFFHAND_ITEM || type == IngameOverlayElementRenderEvent.OverlayElementType.OFFHAND_TEXTURE) && player.getOffHandItemStack().isShield()) {
            event.setCancelled(true);
        }
    }
    
    @Subscribe
    public void onHudWidgetDropzoneElementShift(final HudWidgetDropzoneElementShiftEvent event) {
        if (!this.isEnabled() || !event.isOffHandSide()) {
            return;
        }
        if (event.itemStack().isShield()) {
            event.setCancelled(true);
        }
    }
    
    @Override
    public boolean isEnabled() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldSword()) && !LegacySwordOldAnimation.LEGACY_PVP;
    }
    
    @Override
    public boolean isBlockingWithSword(final Player player) {
        if (!player.getMainHandItemStack().isSword()) {
            return false;
        }
        if (this.minecraft.getClientPlayer() == player) {
            final MinecraftOptions options = this.minecraft.options();
            final boolean isUsingItem = options.useItemInput().isDown();
            final boolean isAttacking = options.attackInput().isDown();
            final boolean blockBuild = isAttacking && this.blockBuildAnimation.isEnabled();
            return isUsingItem && (!this.minecraft.isLastBlockUsed() || blockBuild);
        }
        if (player.getItemUseDurationTicks() > 0) {
            return !this.minecraft.isLastBlockUsed();
        }
        final Boolean blocking = this.blocking.get(player.getUniqueId());
        return blocking != null && blocking;
    }
    
    public boolean isBlockingWithShield(final Player player) {
        return player.getOffHandItemStack().isShield() && player.getItemUseDurationTicks() > 0 && player.getUsedItemHand() == LivingEntity.Hand.OFF_HAND;
    }
    
    public boolean isMainHand(final LivingEntity entity, final LivingEntity.HandSide side) {
        return entity.isMainHandRight() == (side == LivingEntity.HandSide.RIGHT);
    }
    
    @ApiStatus.Internal
    public void setBlockingState(final UUID uuid, final boolean blocking) {
        this.blocking.put(uuid, blocking);
    }
    
    static {
        ARM_ROTATION_IDLE = new FloatVector3(-0.31415927f, 0.0f, 0.0f);
        ARM_ROTATION_BLOCKING = SwordOldAnimation.ARM_ROTATION_IDLE.copy().multiply(3.0f);
    }
}
