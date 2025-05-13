// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items;

import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.api.user.GameUser;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.user.shop.item.items.pet.PetDataStorage;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.core.main.user.shop.item.ItemUtil;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.main.user.shop.item.items.pet.AbstractShoulderPetAI;
import net.labymod.core.main.user.shop.item.items.pet.ShoulderPetAI;
import net.labymod.core.main.user.shop.item.items.pet.FlyingPetAI;
import net.labymod.core.main.user.shop.item.ItemDetails;
import net.labymod.core.main.user.shop.item.items.pet.PetAI;
import net.labymod.core.main.user.shop.item.AbstractItem;

public class PetItem extends AbstractItem
{
    private final PetAI petAI;
    
    public PetItem(final int listId, final ItemDetails itemDetails) {
        super(listId, itemDetails);
        this.petAI = this.createPetAI(itemDetails);
    }
    
    private PetAI createPetAI(final ItemDetails details) {
        return switch (details.getType()) {
            case FLYING_PET -> new FlyingPetAI(this);
            case SHOULDER_PET -> new ShoulderPetAI(this);
            default -> throw new IllegalStateException("Unexpected value: " + String.valueOf(details.getType()));
        };
    }
    
    public void render(final Stack stack, final int packedLight, final int packedOverlay, final float partialTicks) {
        final float renderTick = this.player.getRenderTick(partialTicks);
        final PetDataStorage petDataStorage = this.getPetDataStorage(this.user);
        if (petDataStorage == null) {
            return;
        }
        petDataStorage.getAnimationController().tickProvider(() -> renderTick);
        petDataStorage.getOwnerPosition().set(this.player.position());
        if (partialTicks == 1.0f && !petDataStorage.isAttachedToOwner()) {
            return;
        }
        if (petDataStorage.getLastPartialTicks() > partialTicks) {
            this.petAI.tick(this.playerModel, this.player, petDataStorage, renderTick, partialTicks);
        }
        petDataStorage.setLastPartialTicks(partialTicks);
        if (ItemUtil.isSkipFlyingPets() && !this.petAI.canAttach()) {
            return;
        }
        stack.push();
        this.petAI.earlyRender(this.playerModel, this.player, petDataStorage, stack, partialTicks);
        if (!petDataStorage.getAnimationController().isPlaying()) {
            this.playPetAnimation((petDataStorage.isMoving() && !petDataStorage.isFakeIdle()) ? AnimationTrigger.MOVING : AnimationTrigger.IDLE, petDataStorage, this.player);
        }
        final FloatVector3 previousRotation = petDataStorage.getPreviousRotation();
        final FloatVector3 rotation = petDataStorage.getRotation();
        final float rotX = MathHelper.lerp(rotation.getX(), previousRotation.getX(), partialTicks);
        final float rotY = MathHelper.lerp(rotation.getY(), previousRotation.getY(), partialTicks);
        final float rotZ = MathHelper.lerp(rotation.getZ(), previousRotation.getZ(), partialTicks);
        if (!petDataStorage.isAttachedToOwner() && this.petAI instanceof FlyingPetAI) {
            stack.rotate(rotX, 1.0f, 0.0f, 0.0f);
            stack.rotate(rotY, 0.0f, 1.0f, 0.0f);
            stack.rotate(rotZ, 0.0f, 0.0f, 1.0f);
        }
        if (this.model != null) {
            this.translateOffset(stack, true);
            petDataStorage.getAnimationController().applyAnimation(this.model, new String[0]);
            this.renderModelWithEffects(stack, packedLight, packedOverlay, petDataStorage.isRightShoulder());
        }
        stack.pop();
    }
    
    @Override
    public AbstractItem copy() {
        return new PetItem(this.getListId(), this.itemDetails);
    }
    
    public void playPetAnimation(final AnimationTrigger trigger, final PetDataStorage petDataStorage, final Entity entity) {
        final ModelAnimation animation = super.animationContainer.handleAnimationTrigger(trigger, petDataStorage.getAnimationController(), entity);
        if (animation != null) {
            petDataStorage.setAttached(this.petAI.canAttach() && trigger.isIdle());
        }
    }
    
    public PetDataStorage getPetDataStorage(final GameUser user) {
        return ((DefaultGameUser)user).getUserItemStorage().getPetDataStorage(this);
    }
    
    @Override
    public String getName() {
        if (this.name == null) {
            this.name = "Pet/" + super.getName();
        }
        return this.name;
    }
}
