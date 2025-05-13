// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.model.type;

public enum MoveType
{
    BOTH(true, true), 
    MOVE_ONLY(true, false), 
    IDLE_ONLY(false, true);
    
    private final boolean canMove;
    private final boolean canIdle;
    
    private MoveType(final boolean canMove, final boolean canIdle) {
        this.canMove = canMove;
        this.canIdle = canIdle;
    }
    
    public boolean canMove() {
        return this.canMove;
    }
    
    public boolean canIdle() {
        return this.canIdle;
    }
}
