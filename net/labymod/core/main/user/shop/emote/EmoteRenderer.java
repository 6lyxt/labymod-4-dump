// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.emote;

import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.Blaze3DRenderType;
import java.util.Iterator;
import net.labymod.api.Laby;
import java.util.Map;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import net.labymod.api.client.render.model.ModelBuffer;
import net.labymod.core.main.user.shop.item.model.AttachmentPoint;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.TransformationType;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationMeta;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationStorage;
import net.labymod.api.client.render.RenderConstants;
import net.labymod.api.client.Minecraft;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class EmoteRenderer
{
    private static final float MODEL_ROTATION_SHIFT = 0.4f;
    private static final float SNEAK_OFFSET = 0.125f;
    private static final float LEGACY_SNEAK_OFFSET = 0.2f;
    private static final float LEGACY_LEG_SNEAK_OFFSET = 0.1875f;
    private static final float BODY_HEIGHT = 1.5f;
    private static final float TRANSLUCENT_ALPHA = 0.15f;
    private static final String CHEST_PART = "chest";
    private final Minecraft minecraft;
    private final RenderConstants renderConstants;
    private final ImmediateModelRenderer immediateModelRenderer;
    private EmoteAnimationStorage animationStorage;
    private Player player;
    private HumanoidModel model;
    private boolean firstPerson;
    
    @Inject
    public EmoteRenderer(final LabyAPI labyAPI, final RenderConstants renderConstants) {
        this.minecraft = labyAPI.minecraft();
        this.renderConstants = renderConstants;
        this.immediateModelRenderer = new ImmediateModelRenderer();
    }
    
    public EmoteRenderer animationStorage(final EmoteAnimationStorage animationStorage) {
        this.animationStorage = animationStorage;
        return this;
    }
    
    public EmoteRenderer player(final Player player) {
        this.player = player;
        return this;
    }
    
    public EmoteRenderer model(final HumanoidModel model) {
        this.model = model;
        return this;
    }
    
    public EmoteRenderer firstPerson(final boolean firstPerson) {
        this.firstPerson = firstPerson;
        return this;
    }
    
    public void transformBody(final Stack stack) {
        final AnimationController animationController = this.animationStorage.animationController();
        if (this.player.isCrouching() && this.getMeta(EmoteAnimationMeta.BLOCK_SNEAKING, false)) {
            stack.translate(0.0f, -(PlatformEnvironment.isAncientOpenGL() ? 0.2f : 0.125f), 0.0f);
        }
        animationController.disableTransformation(TransformationType.ROTATION, TransformationType.SCALE).transform(stack, "body").enableTransformation(TransformationType.ROTATION, TransformationType.SCALE);
        stack.translate(0.0f, 0.4f, 0.0f);
        final FloatVector3 rotation = animationController.getCurrentRotation("body");
        if (rotation != null) {
            stack.rotateRadians(rotation.getZ(), 0.0f, 0.0f, 1.0f);
            stack.rotateRadians(rotation.getY(), 0.0f, 1.0f, 0.0f);
            stack.rotateRadians(rotation.getX(), 1.0f, 0.0f, 0.0f);
        }
        final FloatVector3 scale = animationController.getCurrentScale("body");
        if (scale != null) {
            stack.scale(scale.getX(), scale.getY(), scale.getZ());
        }
        stack.translate(0.0f, -0.4f, 0.0f);
    }
    
    public void transformCamera(final Stack stack, final boolean headAnimation) {
        final float playerRotationYaw = this.player.getRotationYaw();
        stack.rotate(-playerRotationYaw, 0.0f, 1.0f, 0.0f);
        final AnimationController animationController = this.animationStorage.animationController();
        animationController.disableTransformation(TransformationType.ROTATION, TransformationType.SCALE).transform(stack, "body").enableTransformation(TransformationType.ROTATION, TransformationType.SCALE);
        stack.translate(0.0f, this.renderConstants.cameraMovementScale(), 0.0f);
        final FloatVector3 rotation = animationController.getCurrentRotation("body");
        if (rotation != null) {
            stack.rotateRadians(rotation.getX(), -1.0f, 0.0f, 0.0f);
            stack.rotateRadians(rotation.getY(), 0.0f, 1.0f, 0.0f);
            stack.rotateRadians(rotation.getZ(), 0.0f, 0.0f, 1.0f);
        }
        stack.translate(0.0f, -this.renderConstants.cameraMovementScale(), 0.0f);
        if (headAnimation && !this.getMeta(EmoteAnimationMeta.BLOCK_HEAD_ANIMATION, false)) {
            animationController.disableTransformation(TransformationType.ROTATION, TransformationType.SCALE).transform(stack, "head").enableTransformation(TransformationType.ROTATION, TransformationType.SCALE);
            final FloatVector3 headRotation = animationController.getCurrentRotation("head");
            if (headRotation != null) {
                stack.rotateRadians(headRotation.getX(), -1.0f, 0.0f, 0.0f);
                stack.rotateRadians(headRotation.getY(), 0.0f, 1.0f, 0.0f);
                stack.rotateRadians(headRotation.getZ(), 0.0f, 0.0f, 1.0f);
            }
        }
        stack.rotate(playerRotationYaw, 0.0f, 1.0f, 0.0f);
        this.minecraft.requestChunkUpdate();
    }
    
    public void transformModel() {
        final AnimationController animationController = this.animationStorage.animationController();
        if (this.firstPerson) {
            animationController.disableTransformation(TransformationType.POSITION);
        }
        animationController.animateStrict(false).applyAnimation(this.model, "body").animateStrict(true).enableTransformation(TransformationType.POSITION);
        this.model.copyToSecondLayer();
    }
    
    public void animateProps() {
        final Model propsModel = this.animationStorage.getPropsModel();
        this.animationStorage.animationController().applyAnimation(propsModel, new String[0]);
    }
    
    public void renderProps(final Stack stack) {
        final Model propsModel = this.animationStorage.getPropsModel();
        final ModelBuffer propsModelBuffer = this.animationStorage.getPropsModelBuffer();
        final EmoteItem emote = this.animationStorage.getEmote();
        emote.updateAnimatedTexture(propsModel, propsModelBuffer);
        stack.push();
        if (this.player.isCrouching()) {
            if (emote.getAttachedTo() == AttachmentPoint.NONE || this.getMeta(EmoteAnimationMeta.BLOCK_SNEAKING, false)) {
                if (!PlatformEnvironment.isAncientOpenGL()) {
                    stack.translate(0.0f, -0.125f, 0.0f);
                }
            }
            else {
                if (PlatformEnvironment.isAncientOpenGL()) {
                    stack.translate(0.0f, (emote.getAttachedTo() == AttachmentPoint.LEG) ? 0.1875f : 0.2f, 0.0f);
                }
                final ModelPart attachedPart = switch (emote.getAttachedTo()) {
                    default -> throw new MatchException(null, null);
                    case BODY,  NONE -> this.model.getBody();
                    case HEAD -> this.model.getHead();
                    case ARM -> this.model.getRightArm();
                    case LEG -> this.model.getRightLeg();
                };
                stack.translate(0.0f, attachedPart.getAnimationTransformation().getTranslation().getY() * 0.0625f, 0.0f);
            }
        }
        propsModelBuffer.setForceProjectionMatrix(true);
        propsModelBuffer.render(stack);
        stack.pop();
    }
    
    public void renderPlayerModel(final Stack stack) {
        final Model playerModel = this.player.isSlim() ? this.animationStorage.getAlexModel() : this.animationStorage.getSteveModel();
        this.animationStorage.animationController().animateStrict(false).applyAnimation(playerModel, "body").animateStrict(true);
        for (final Map.Entry<String, ModelPart> entry : this.model.getParts().entrySet()) {
            final ModelPart originalPart = entry.getValue();
            final ModelPart part = playerModel.getPart(entry.getKey().equals("body") ? "chest" : entry.getKey());
            if (part != null) {
                part.getAnimationTransformation().set(originalPart.getAnimationTransformation());
                part.setVisible(originalPart.isVisible());
            }
        }
        final Blaze3DRenderType renderType = Laby.references().standardBlaze3DRenderTypes().entityTranslucent(this.player.skinTexture());
        stack.push();
        stack.translate(0.0f, 1.5f, 0.0f);
        if (PlatformEnvironment.isAncientOpenGL() && this.player.isCrouching()) {
            stack.translate(0.0f, 0.2f, 0.0f);
        }
        final boolean translucent = this.player.isInvisible() && !this.player.isInvisibleFor(this.minecraft.getClientPlayer());
        this.immediateModelRenderer.render(stack, playerModel, renderType, translucent ? 0.15f : 1.0f);
        stack.pop();
    }
    
    private <T> T getMeta(final AnimationMeta<T> meta, final T defaultValue) {
        final ModelAnimation playing = this.animationStorage.animationController().getPlaying();
        return (playing == null) ? defaultValue : playing.getMetaDefault(meta, defaultValue);
    }
}
