// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player;

import net.labymod.api.client.component.Component;

public enum PlayerClothes
{
    CAPE(0, "cape"), 
    JACKET(1, "jacket"), 
    LEFT_SLEEVE(2, "left_sleeve"), 
    RIGHT_SLEEVE(3, "right_sleeve"), 
    LEFT_PANTS_LEG(4, "left_pants_leg"), 
    RIGHT_PANTS_LEG(5, "right_pants_leg"), 
    HAT(6, "hat");
    
    private static final PlayerClothes[] CLOTHES;
    @Deprecated
    private final int bit;
    @Deprecated
    private final int mask;
    @Deprecated
    private final String id;
    @Deprecated
    private final Component name;
    
    private PlayerClothes(final int bit, final String id) {
        this.bit = bit;
        this.mask = 1 << bit;
        this.id = id;
        this.name = Component.translatable("options.modelPart." + id, new Component[0]);
    }
    
    @Deprecated
    public int getBit() {
        return this.bit;
    }
    
    @Deprecated
    public int getMask() {
        return this.mask;
    }
    
    @Deprecated
    public String getId() {
        return this.id;
    }
    
    @Deprecated
    public Component partName() {
        return this.name;
    }
    
    static {
        CLOTHES = values();
    }
}
