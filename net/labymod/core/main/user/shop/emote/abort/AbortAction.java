// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.emote.abort;

import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.entity.player.Player;
import java.util.Arrays;
import java.util.Collection;

public enum AbortAction permits AbortAction$1, AbortAction$2, AbortAction$3, AbortAction$4, AbortAction$5, AbortAction$6, AbortAction$7, AbortAction$8
{
    MOVEMENT(new String[] { "left_leg", "right_leg" }) {
        @Override
        public boolean isMet(final Player player, final float partialTicks) {
            return player.getWalkingSpeed(partialTicks) > 0.1f || AbortAction$1.JUMPING.isMet(player, partialTicks) || player.position().getY() < player.previousPosition().getY();
        }
    }, 
    JUMPING(new String[] { "left_leg", "right_leg" }) {
        @Override
        public boolean isMet(final Player player, final float partialTicks) {
            return !player.isOnGround() && player.position().getY() > player.previousPosition().getY();
        }
    }, 
    SPRINTING(new String[0]) {
        @Override
        public boolean isMet(final Player player, final float partialTicks) {
            return player.isSprinting();
        }
    }, 
    SNEAKING(new String[0]) {
        @Override
        public boolean isMet(final Player player, final float partialTicks) {
            return player.isCrouching();
        }
    }, 
    FLYING(new String[0]) {
        @Override
        public boolean isMet(final Player player, final float partialTicks) {
            return player.entityPose() == EntityPose.FALL_FLYING;
        }
    }, 
    SWIMMING(new String[0]) {
        @Override
        public boolean isMet(final Player player, final float partialTicks) {
            return player.entityPose() == EntityPose.SWIMMING;
        }
    }, 
    DAMAGE(new String[0]) {
        @Override
        public boolean isMet(final Player player, final float partialTicks) {
            return player.getHurtTime() != 0;
        }
    }, 
    ATTACKING(new String[0]) {
        @Override
        public boolean isMet(final Player player, final float partialTicks) {
            return player.getArmSwingProgress(partialTicks) != 0.0f;
        }
    };
    
    public static final AbortAction[] VALUES;
    private final Collection<String> affectedParts;
    
    private AbortAction(final String[] affectedParts) {
        this.affectedParts = Arrays.asList(affectedParts);
    }
    
    public abstract boolean isMet(final Player p0, final float p1);
    
    public boolean affectsParts() {
        return !this.affectedParts.isEmpty();
    }
    
    public Collection<String> getAffectedParts() {
        return this.affectedParts;
    }
    
    static {
        VALUES = values();
    }
}
