// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.model;

import com.google.gson.annotations.SerializedName;

public class PetData
{
    @SerializedName(value = "movementSpeed", alternate = { "movement_speed" })
    private float movementSpeed;
    
    public PetData() {
        this.movementSpeed = 0.175f;
    }
    
    public float getMovementSpeed() {
        return this.movementSpeed;
    }
}
