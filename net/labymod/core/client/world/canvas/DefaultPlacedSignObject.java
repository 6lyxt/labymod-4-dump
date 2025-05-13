// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.canvas;

import net.labymod.api.client.world.signobject.object.SignObject;
import net.labymod.api.client.blockentity.SignBlockEntity;
import net.labymod.api.client.world.signobject.PlacedSignObject;

record DefaultPlacedSignObject(SignBlockEntity sign, SignObject<?>[] objects) implements PlacedSignObject {
    @Override
    public SignObject<?> object(final SignBlockEntity.SignSide side) {
        return this.objects[side.ordinal()];
    }
}
