// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.signobject;

import net.labymod.api.client.world.signobject.object.SignObject;
import net.labymod.api.client.world.block.BlockPosition;
import net.labymod.api.client.blockentity.SignBlockEntity;

public interface PlacedSignObject
{
    public static final SignBlockEntity.SignSide[] SIDES = SignBlockEntity.SignSide.values();
    
    SignBlockEntity sign();
    
    default BlockPosition position() {
        return this.sign().position();
    }
    
    SignObject<?>[] objects();
    
    SignObject<?> object(final SignBlockEntity.SignSide p0);
}
