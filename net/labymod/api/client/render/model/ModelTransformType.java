// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model;

public enum ModelTransformType
{
    NONE, 
    THIRD_PERSON_LEFT_HAND, 
    THIRD_PERSON_RIGHT_HAND, 
    FIRST_PERSON_LEFT_HAND, 
    FIRST_PERSON_RIGHT_HAND, 
    HEAD, 
    GUI, 
    GROUND, 
    FIXED;
    
    public boolean isFirstPerson() {
        return this == ModelTransformType.FIRST_PERSON_LEFT_HAND || this == ModelTransformType.FIRST_PERSON_RIGHT_HAND;
    }
}
