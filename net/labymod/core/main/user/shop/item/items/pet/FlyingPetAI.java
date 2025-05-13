// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.main.user.shop.item.items.PetItem;

@Deprecated
public class FlyingPetAI extends AbstractShoulderPetAI
{
    private static final long FAKE_IDLE_DELAY = 7000L;
    private static final long FAKE_MOVE_DELAY = 1000L;
    
    public FlyingPetAI(final PetItem pet) {
        super(pet);
    }
    
    @Override
    protected long getFakeIdleDelay() {
        return 7000L;
    }
    
    @Override
    protected long getFakeMoveDelay() {
        return 1000L;
    }
    
    @Override
    protected void onAbsoluteMovement(final Stack stack, final PetDataStorage storage, final double x, final double y, final double z, final double playerX, final double playerY, final double playerZ, final float renderYaw) {
        if (!storage.isAttachedToOwner() && this.pet.itemDetails().getMoveType().canMove()) {
            stack.rotate(renderYaw, 0.0f, -1.0f, 0.0f);
            final float f = 1.0659f;
            stack.scale(f, f, f);
            stack.translate(x - playerX, -y + playerY, -z + playerZ);
        }
    }
}
