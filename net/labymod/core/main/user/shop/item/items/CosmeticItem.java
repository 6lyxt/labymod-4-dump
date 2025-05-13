// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.core.main.user.shop.item.geometry.AnimationStorage;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.core.main.user.shop.item.model.MirrorType;
import net.labymod.core.client.entity.player.DummyPlayerModel;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.core.main.user.shop.item.model.AttachmentPoint;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.main.user.shop.item.ItemDetails;
import net.labymod.core.main.user.shop.item.AbstractItem;

public class CosmeticItem extends AbstractItem
{
    public CosmeticItem(final int listId, final ItemDetails itemDetails) {
        super(listId, itemDetails);
    }
    
    public void render(final Stack stack, final int packedLight, final int packedOverlay, final float partialTicks) {
        final MainHand hand = (this.hand == null) ? this.labyAPI.minecraft().options().mainHand() : this.hand;
        final boolean leftHand = hand == MainHand.LEFT;
        if (this.itemDetails.isMirror() && this.itemDetails.getAttachmentPoint().canBeMirrored()) {
            for (int index = 0; index < 2; ++index) {
                if (!this.firstPerson || index != (leftHand ? 1 : 0)) {
                    final boolean rightSide = index == 1;
                    this.setPosition(rightSide ? Position.RIGHT : Position.LEFT);
                    this.renderSidedGeometry(stack, packedLight, packedOverlay, partialTicks, rightSide);
                }
            }
        }
        else if (!this.firstPerson || this.itemMetadata.isRightSide() != leftHand) {
            final boolean rightSide = this.itemMetadata.isRightSide();
            this.setPosition(rightSide ? Position.RIGHT : Position.LEFT);
            this.renderSidedGeometry(stack, packedLight, packedOverlay, partialTicks, rightSide);
        }
    }
    
    @Override
    public AbstractItem copy() {
        return new CosmeticItem(this.getListId(), this.itemDetails);
    }
    
    @Override
    public String getName() {
        if (this.name == null) {
            this.name = "Cosmetic/" + super.getName();
        }
        return this.name;
    }
    
    @Override
    public boolean isInvisibleInFirstPersonContext() {
        return this.itemDetails.getAttachmentPoint() != AttachmentPoint.ARM;
    }
    
    private ModelPart getArmOrLeg(final AttachmentPoint point, final boolean rightSide) {
        if (!point.isArmOrLeg()) {
            return null;
        }
        final boolean arm = point == AttachmentPoint.ARM;
        return arm ? this.playerModel.getArm(rightSide) : this.playerModel.getLeg(rightSide);
    }
    
    private ModelPart getHeadOrBody(final AttachmentPoint point) {
        if (!point.isHeadOrBody()) {
            return null;
        }
        return (point == AttachmentPoint.BODY) ? this.playerModel.getBody() : this.playerModel.getHead();
    }
    
    private void renderSidedGeometry(final Stack stack, final int packedLight, final int packedOverlay, final float partialTicks, final boolean rightSide) {
        final AttachmentPoint attachmentPoint = this.itemDetails.getAttachmentPoint();
        final ModelPart armOrLeg = this.getArmOrLeg(attachmentPoint, rightSide);
        if (armOrLeg != null && armOrLeg.isInvisible()) {
            return;
        }
        final ModelPart headOrBody = this.getHeadOrBody(attachmentPoint);
        if (headOrBody != null && headOrBody.isInvisible()) {
            return;
        }
        stack.push();
        final float renderTick = this.player.getRenderTick(partialTicks);
        final float walkingSpeed = this.player.getWalkingSpeed(partialTicks);
        if (armOrLeg != null) {
            final boolean isArm = attachmentPoint == AttachmentPoint.ARM;
            final boolean slim = this.playerModel.isSlim();
            final FloatVector3 translation = armOrLeg.getModelPartTransform().getTranslation();
            final float translationY = translation.getY() + ((this.playerModel instanceof DummyPlayerModel) ? 6.0f : 0.0f);
            stack.translate(translation.getX() * 0.0625f, translationY * 0.0625f, translation.getZ() * 0.0625f);
            armOrLeg.getAnimationTransformation().transform(stack);
            if (rightSide) {
                if (this.itemDetails.getMirrorType() == MirrorType.DUPLICATE) {
                    if (isArm) {
                        stack.translate(slim ? -0.0625f : -0.125f, 0.0f, 0.0f);
                    }
                }
                else if (this.itemDetails.getMirrorType() == MirrorType.MIRROR) {
                    stack.scale(-1.0f, 1.0f, 1.0f);
                }
                else {
                    stack.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
            }
            if (isArm) {
                stack.translate(slim ? 0.03125f : 0.0625f, -0.125f, 0.0f);
            }
        }
        if (headOrBody != null) {
            headOrBody.getAnimationTransformation().transform(stack);
        }
        final AnimationStorage animationStorage = this.getAnimationStorage(this.user);
        if (animationStorage != null) {
            final AnimationController animationController = animationStorage.getController();
            animationController.tickProvider(() -> renderTick);
            final boolean crouching = this.player.isCrouching();
            final boolean moving = walkingSpeed > 0.1;
            if (this.firstPerson && !animationStorage.isLastFirstPerson()) {
                this.handleAnimationTrigger(AnimationTrigger.START_FIRST, animationController);
            }
            else if (!this.firstPerson && animationStorage.isLastFirstPerson()) {
                this.handleAnimationTrigger(AnimationTrigger.STOP_FIRST, animationController);
            }
            animationStorage.setLastFirstPerson(this.firstPerson);
            final boolean firstPerson = animationStorage.isLastFirstPerson();
            if (crouching && !animationStorage.isLastSneaking()) {
                this.handleAnimationTrigger(AnimationTrigger.getSneakingToggle(firstPerson, true), animationController);
            }
            else if (!crouching && animationStorage.isLastSneaking()) {
                this.handleAnimationTrigger(AnimationTrigger.getSneakingToggle(firstPerson, false), animationController);
            }
            animationStorage.setLastSneaking(crouching);
            if (moving && !animationStorage.isLastMoving()) {
                this.handleAnimationTrigger(AnimationTrigger.getMovingToggle(firstPerson, true), animationController);
            }
            else if (!moving && animationStorage.isLastMoving()) {
                this.handleAnimationTrigger(AnimationTrigger.getMovingToggle(firstPerson, false), animationController);
            }
            animationStorage.setLastMoving(moving);
            if (TimeUtil.getMillis() > animationStorage.getLastTriggerMillis() + 500L || !animationController.isPlaying()) {
                animationStorage.setLastTriggerMillis(TimeUtil.getMillis());
                final AnimationTrigger trigger = AnimationTrigger.getMovingOrIdle(moving, crouching, firstPerson);
                this.handleAnimationTrigger(trigger, animationController);
            }
            if (this.model != null) {
                this.translateOffset(stack);
                animationController.applyAnimation(this.model, new String[0]);
                this.renderModelWithEffects(stack, packedLight, packedOverlay, rightSide);
            }
        }
        stack.pop();
    }
    
    private void handleAnimationTrigger(final AnimationTrigger trigger, final AnimationController controller) {
        this.animationContainer.handleAnimationTrigger(trigger, controller, this.player);
    }
}
