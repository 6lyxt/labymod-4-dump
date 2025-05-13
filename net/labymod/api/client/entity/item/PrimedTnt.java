// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.item;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.Entity;

public interface PrimedTnt extends Entity
{
    int getFuse();
    
    @Nullable
    LivingEntity getOwner();
}
