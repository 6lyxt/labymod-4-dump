// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model.entity.player;

import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.HumanoidModel;

public interface PlayerModel extends HumanoidModel
{
    public static final String JACKET_NAME = "jacket";
    public static final String LEFT_PANTS_NAME = "left_pants";
    public static final String RIGHT_PANTS_NAME = "right_pants";
    public static final String LEFT_SLEEVE_NAME = "left_sleeve";
    public static final String RIGHT_SLEEVE_NAME = "right_sleeve";
    public static final String CLOAK_NAME = "cloak";
    
    ModelPart getJacket();
    
    ModelPart getCloak();
    
    ModelPart getLeftPants();
    
    ModelPart getRightPants();
    
    ModelPart getLeftSleeve();
    
    ModelPart getRightSleeve();
    
    default ModelPart getSleeve(final boolean rightSide) {
        return rightSide ? this.getRightSleeve() : this.getLeftSleeve();
    }
    
    default ModelPart getPants(final boolean rightSide) {
        return rightSide ? this.getRightPants() : this.getLeftPants();
    }
    
    boolean isSlim();
    
    default void copyToSecondLayer() {
        super.copyToSecondLayer();
        this.getJacket().copyFrom(this.getBody());
        this.getLeftPants().copyFrom(this.getLeftLeg());
        this.getRightPants().copyFrom(this.getRightLeg());
        this.getLeftSleeve().copyFrom(this.getLeftArm());
        this.getRightSleeve().copyFrom(this.getRightArm());
    }
}
