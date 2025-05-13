// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.main.user.shop.item.items.PetItem;

@Deprecated
public class ShoulderPetAI extends AbstractShoulderPetAI
{
    private static final long FAKE_IDLE_DELAY = 0L;
    private static final long FAKE_MOVE_DELAY = 0L;
    
    public ShoulderPetAI(final PetItem pet) {
        super(pet);
    }
    
    @Override
    protected boolean shouldBounce(final PetDataStorage storage) {
        return true;
    }
    
    @Override
    protected boolean isAlwaysAttachedToArm() {
        return true;
    }
    
    @Override
    protected boolean shouldTriggerOnSwing() {
        return false;
    }
    
    @Override
    protected long getFakeIdleDelay() {
        return 0L;
    }
    
    @Override
    protected long getFakeMoveDelay() {
        return 0L;
    }
    
    @Override
    protected void onAbsoluteMovement(final Stack stack, final PetDataStorage storage, final double x, final double y, final double z, final double playerX, final double playerY, final double playerZ, final float renderYaw) {
    }
}
