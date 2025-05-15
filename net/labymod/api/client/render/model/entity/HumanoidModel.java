// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model.entity;

import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.Model;

public interface HumanoidModel extends Model
{
    public static final String HEAD_NAME = "head";
    public static final String HAT_NAME = "hat";
    public static final String BODY_NAME = "body";
    public static final String LEFT_LEG_NAME = "left_leg";
    public static final String RIGHT_LEG_NAME = "right_leg";
    public static final String LEFT_ARM_NAME = "left_arm";
    public static final String RIGHT_ARM_NAME = "right_arm";
    
    ModelPart getHead();
    
    ModelPart getHat();
    
    ModelPart getBody();
    
    ModelPart getLeftLeg();
    
    ModelPart getRightLeg();
    
    ModelPart getLeftArm();
    
    ModelPart getRightArm();
    
    default ModelPart getArm(final boolean rightSide) {
        return rightSide ? this.getRightArm() : this.getLeftArm();
    }
    
    default ModelPart getLeg(final boolean rightSide) {
        return rightSide ? this.getRightLeg() : this.getLeftLeg();
    }
    
    default void copyToSecondLayer() {
        this.getHat().copyFrom(this.getHead());
    }
}
