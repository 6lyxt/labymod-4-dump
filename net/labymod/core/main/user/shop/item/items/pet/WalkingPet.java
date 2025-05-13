// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.util.math.Quaternion;
import net.labymod.core.main.user.shop.item.model.ItemNameTag;
import net.labymod.core.main.user.shop.item.geometry.AnimationStorage;
import net.labymod.core.main.user.shop.item.items.pet.ai.Transform;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.core.client.entity.player.DummyPlayer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.Laby;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.shop.item.items.pet.ai.DefaultPetBehavior;
import net.labymod.core.main.user.shop.item.ItemDetails;
import net.labymod.core.main.user.shop.item.items.pet.ai.PetBehavior;
import net.labymod.core.main.user.shop.item.debug.WalkingPetDebugRenderer;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.main.user.shop.item.AbstractItem;

public class WalkingPet extends AbstractItem
{
    private static final float NAME_TAG_OFFSET = 4.0f;
    private static final DoubleVector3 DEFAULT_CAMERA_POSITION;
    private final WalkingPetDebugRenderer debugRenderer;
    private final PetBehavior behavior;
    private boolean firstTeleported;
    private boolean shouldIdle;
    private boolean moving;
    
    public WalkingPet(final int listId, final ItemDetails itemDetails) {
        super(listId, itemDetails);
        this.debugRenderer = new WalkingPetDebugRenderer(this);
        this.behavior = new DefaultPetBehavior(this, itemDetails.getPetData().getMovementSpeed(), () -> this.player);
    }
    
    @Override
    public void tick() {
        super.tick();
        this.behavior.tick();
        if (this.physicData != null) {
            final int petIndex = this.physicData.getAndIncrementWalkingPetIndex();
            final int maxPets = ((DefaultGameUser)this.user).getUserItemStorage().getMaxPets();
            this.behavior.updatePetIndex(petIndex, maxPets);
        }
        if (Laby.labyAPI().labyModLoader().isLabyModDevelopmentEnvironment() && Laby.labyAPI().minecraft().isKeyPressed(Key.O)) {
            this.testTeleport();
        }
    }
    
    @Override
    protected void render(final Stack stack, final int packedLight, final int packedOverlay, final float partialTicks) {
        stack.push();
        if (!this.firstTeleported) {
            final FloatVector3 position = this.player.position().toFloatVector3();
            this.behavior.setPosition(position.add(1.0f, 0.0f, 1.0f));
            this.firstTeleported = true;
        }
        final MinecraftCamera camera = Laby.labyAPI().minecraft().getCamera();
        final DoubleVector3 cameraPosition = (camera == null) ? WalkingPet.DEFAULT_CAMERA_POSITION : camera.renderPosition();
        final DoubleVector3 currentPosition = this.behavior.position();
        final DoubleVector3 previousPosition = this.behavior.previousPosition();
        final double x = currentPosition.lerpX(previousPosition, partialTicks);
        final double y = currentPosition.lerpY(previousPosition, partialTicks);
        final double z = currentPosition.lerpZ(previousPosition, partialTicks);
        final double renderX = x - cameraPosition.getX();
        final double renderY = y - cameraPosition.getY();
        final double renderZ = z - cameraPosition.getZ();
        final boolean isInGui = this.player instanceof DummyPlayer;
        if (isInGui) {
            stack.rotate(180.0f, 1.0f, 0.0f, 0.0f);
            stack.translate(-0.75f, -1.5f, 0.0f);
            stack.translate(0.0f, 0.0f, 0.0f);
        }
        else {
            stack.translate(renderX, renderY, renderZ);
        }
        stack.scale(-1.0f, -1.0f, -1.0f);
        stack.push();
        final Transform transform = this.behavior.transform();
        if (!isInGui) {
            transform.rotate(stack, partialTicks);
        }
        final AnimationStorage storage = this.getAnimationStorage(this.user);
        final boolean crouching = this.player.isCrouching();
        if (crouching && !storage.isLastSneaking()) {
            this.triggerAnimation(storage, AnimationTrigger.START_SNEAKING);
        }
        else if (!crouching && storage.isLastSneaking()) {
            this.triggerAnimation(storage, AnimationTrigger.STOP_SNEAKING);
        }
        storage.setLastSneaking(crouching);
        final boolean walking = this.behavior.isWalking();
        if (walking && !storage.isLastMoving()) {
            this.triggerAnimation(storage, AnimationTrigger.START_MOVING);
        }
        else if (!walking && storage.isLastMoving()) {
            this.triggerAnimation(storage, AnimationTrigger.STOP_MOVING);
        }
        storage.setLastMoving(walking);
        if (TimeUtil.getMillis() > storage.getLastTriggerMillis() + 500L || !storage.getController().isPlaying()) {
            final AnimationTrigger trigger = AnimationTrigger.getMovingOrIdle(walking, crouching);
            this.triggerAnimation(storage, trigger);
        }
        storage.getController().applyAnimation(this.model, new String[0]);
        this.updateBoundingBox();
        this.renderModel(stack, this.getPackedLight(currentPosition), packedOverlay);
        stack.pop();
        this.renderNameTag(stack, camera);
        this.debugRenderer.render(stack, partialTicks);
        stack.pop();
    }
    
    private void renderNameTag(final Stack stack, final MinecraftCamera camera) {
        final ItemNameTag nameTag = this.itemMetadata.getNameTag();
        if (!nameTag.isVisible()) {
            return;
        }
        stack.push();
        final Quaternion cameraRotation = camera.rotation();
        stack.rotate(cameraRotation.getYaw(), 0.0f, -1.0f, 0.0f);
        stack.scale(0.0625f);
        stack.scale((float)this.itemDetails.getScale());
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        gfx.enableDepth();
        gfx.depthFunc(515);
        this.renderTag(stack, nameTag, camera.getPitch());
        gfx.restoreBlaze3DStates();
        stack.pop();
    }
    
    private void renderTag(final Stack stack, final ItemNameTag itemNameTag, final float pitch) {
        final ComponentRenderer componentRenderer = Laby.references().componentRenderer();
        final RenderableComponent tagContent = RenderableComponent.of(Component.text(itemNameTag.content()));
        stack.translate(-tagContent.getWidth() / 2.0f, -tagContent.getHeight() - this.model.getHeight() - 4.0f, 0.0f);
        stack.rotate(pitch, 1.0f, 0.0f, 0.0f);
        final float x = -1.0f;
        final float y = -1.0f;
        final float width = tagContent.getWidth() + Math.abs(x);
        final float borderWidth = width + Math.abs(x);
        final int topGradientColor = itemNameTag.backgroundTopColor();
        final int bottomGradientColor = itemNameTag.backgroundBottomColor();
        final int borderColor = itemNameTag.borderColor();
        final RectangleRenderer rectangleRenderer = Laby.references().rectangleRenderer();
        rectangleRenderer.pos(x, y).size(width, tagContent.getHeight() + Math.abs(y)).gradientVertical(topGradientColor, bottomGradientColor).render(stack);
        rectangleRenderer.pos(x - 1.0f, y - 1.0f).size(borderWidth + 1.0f, 1.0f).color(borderColor).render(stack);
        rectangleRenderer.pos(x - 1.0f, tagContent.getHeight()).size(borderWidth + 1.0f, 1.0f).color(borderColor).render(stack);
        rectangleRenderer.pos(x - 1.0f, y).size(1.0f, tagContent.getHeight() + Math.abs(y)).color(borderColor).render(stack);
        rectangleRenderer.pos(width - 1.0f, y).size(1.0f, tagContent.getHeight() + Math.abs(y)).color(borderColor).render(stack);
        componentRenderer.builder().text(tagContent).pos(0.0f, 0.0f).color(-1).drawMode(TextDrawMode.POLYGON_OFFSET).shadow(false).render(stack);
    }
    
    public void testTeleport() {
        final net.labymod.api.util.math.position.Position currentPosition = this.player.position();
        this.behavior.setPosition(currentPosition.getX(), currentPosition.getY() + 2.0, currentPosition.getZ());
    }
    
    @Override
    protected void onModelLoad() {
        this.updateBoundingBox();
    }
    
    @Override
    public AbstractItem copy() {
        return new WalkingPet(this.getListId(), this.itemDetails);
    }
    
    @Override
    public boolean isInvisibleInFirstPersonContext() {
        return false;
    }
    
    @Override
    protected boolean isInRenderDistance() {
        final ClientPlayer player = this.labyAPI.minecraft().getClientPlayer();
        if (player == null) {
            return false;
        }
        final float distance = (float)player.getDistanceSquared(this.behavior.position());
        final float renderDistance = 128.0f;
        return distance < renderDistance * renderDistance;
    }
    
    public PetBehavior behavior() {
        return this.behavior;
    }
    
    private int getPackedLight(final DoubleVector3 position) {
        return this.getPackedLight(position.getX(), position.getY(), position.getZ());
    }
    
    private int getPackedLight(final double x, final double y, final double z) {
        final ClientWorld level = this.labyAPI.minecraft().clientWorld();
        return level.getPackedLight(x, y + this.behavior.boundingBox().getHeight(), z);
    }
    
    private void updateBoundingBox() {
        float width = this.model.getWidth() * 0.0625f;
        float height = this.model.getHeight() * 0.0625f;
        float depth = this.model.getDepth() * 0.0625f;
        width *= (float)this.itemDetails.getScale();
        height *= (float)this.itemDetails.getScale();
        depth *= (float)this.itemDetails.getScale();
        this.behavior.boundingBox().set(0.0, 0.0, 0.0, width, height, depth);
    }
    
    private void triggerAnimation(final AnimationStorage storage, final AnimationTrigger trigger) {
        storage.setLastTriggerMillis(TimeUtil.getMillis());
        this.animationContainer.handleAnimationTrigger(trigger, storage.getController(), this.player);
    }
    
    static {
        DEFAULT_CAMERA_POSITION = new DoubleVector3();
    }
}
