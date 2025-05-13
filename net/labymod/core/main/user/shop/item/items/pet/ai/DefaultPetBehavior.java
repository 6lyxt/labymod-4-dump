// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet.ai;

import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.client.render.model.Model;
import net.labymod.core.main.user.shop.item.items.pet.ai.controller.LookController;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.main.user.shop.item.items.pet.ai.goal.LookAtPlayerGoal;
import net.labymod.core.main.user.shop.item.items.pet.ai.goal.TeleportGoal;
import net.labymod.core.main.user.shop.item.items.pet.ai.goal.Goal;
import net.labymod.core.main.user.shop.item.items.pet.ai.goal.FollowOwnerGoal;
import net.labymod.api.client.entity.player.Player;
import java.util.function.Supplier;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.user.shop.item.items.pet.ai.controller.MovementController;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.core.main.user.shop.item.items.pet.WalkingPet;

public class DefaultPetBehavior implements PetBehavior
{
    private final WalkingPet pet;
    private final float movementSpeed;
    private final Brain brain;
    private final AxisAlignedBoundingBox boundingBox;
    private final Transform transform;
    private final MovementController movementController;
    private final FloatVector3 deltaMovement;
    private final IntVector3 blockPosition;
    private final Supplier<Player> ownerSupplier;
    private boolean ground;
    private boolean walking;
    private int petIndex;
    private int maxPets;
    
    public DefaultPetBehavior(final WalkingPet pet, final float movementSpeed, final Supplier<Player> ownerSupplier) {
        this.brain = new Brain();
        this.boundingBox = new AxisAlignedBoundingBox();
        this.transform = new Transform();
        this.movementController = new MovementController(this);
        this.deltaMovement = new FloatVector3();
        this.blockPosition = new IntVector3();
        this.pet = pet;
        this.movementSpeed = movementSpeed;
        this.ownerSupplier = ownerSupplier;
        this.registerGoals();
    }
    
    private void registerGoals() {
        this.brain.addGoal(1, new FollowOwnerGoal(this, this.ownerSupplier, 5.0f, 0.5f));
        this.brain.addGoal(1, new TeleportGoal(this, this.ownerSupplier, 20.0f));
        this.brain.addGoal(2, new LookAtPlayerGoal(this, 8.0f));
    }
    
    @Override
    public void tick() {
        this.transform.update();
        this.brain.tick();
        this.movementController.tick();
        this.move(this.deltaMovement());
        this.setDeltaMovement(FloatVector3.ZERO);
    }
    
    @Override
    public void setPosition(final double x, final double y, final double z) {
        this.transform.set(x, y, z);
        final int blockX = MathHelper.floor(x);
        final int blockY = MathHelper.floor(y);
        final int blockZ = MathHelper.floor(z);
        this.blockPosition.set(blockX, blockY, blockZ);
    }
    
    @Override
    public void addDeltaMovement(final float x, final float y, final float z) {
        this.deltaMovement.add(x, y, z);
    }
    
    @Override
    public void setDeltaMovement(final float x, final float y, final float z) {
        this.deltaMovement.set(x, y, z);
    }
    
    @Override
    public FloatVector3 deltaMovement() {
        return this.deltaMovement;
    }
    
    @Override
    public Transform transform() {
        return this.transform;
    }
    
    @Override
    public LookController lookController() {
        return this.movementController.lookController();
    }
    
    @Override
    public Model petModel() {
        return this.pet.getModel();
    }
    
    @Override
    public AxisAlignedBoundingBox boundingBox() {
        return this.boundingBox;
    }
    
    @Override
    public boolean onGround() {
        return this.ground;
    }
    
    @Override
    public void setOnGround(final boolean ground) {
        this.ground = ground;
    }
    
    @Override
    public void move(final FloatVector3 deltaMovement) {
        this.walking = (deltaMovement.lengthSquared() != 0.0);
        final FloatVector3 newDeltaMovement = this.movementController.move(this.ownerSupplier.get(), deltaMovement.copy());
        final DoubleVector3 newPosition = this.position().copy().add(newDeltaMovement);
        this.setPosition(newPosition);
    }
    
    @Override
    public float getMovementSpeed() {
        return this.movementSpeed;
    }
    
    @Override
    public void teleportTo(final double x, final double y, final double z) {
        this.setPosition(x, y, z);
        this.movementController.resetGravityVelocity();
    }
    
    @Override
    public boolean isWalking() {
        return this.walking;
    }
    
    @Override
    public int getPetIndex() {
        return this.petIndex;
    }
    
    @Override
    public int getMaxPets() {
        return this.maxPets;
    }
    
    @Override
    public void updatePetIndex(final int petIndex, final int maxPets) {
        this.petIndex = petIndex;
        this.maxPets = maxPets;
    }
}
