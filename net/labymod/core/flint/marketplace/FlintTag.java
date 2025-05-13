// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.flint.marketplace;

import net.labymod.core.main.LabyMod;
import java.util.Optional;
import com.google.gson.annotations.SerializedName;
import net.labymod.core.flint.FlintController;

public class FlintTag
{
    private static final FlintController CONTROLLER;
    private FlintTag parent;
    private int id;
    private String tag;
    private String description;
    @SerializedName("parent_category")
    private int parentCategory;
    
    public Optional<FlintTag> getParent() {
        return FlintTag.CONTROLLER.getTag(this.parentCategory);
    }
    
    public String getName() {
        return this.tag;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getParentCategory() {
        return this.parentCategory;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.getId());
    }
    
    static {
        CONTROLLER = LabyMod.references().flintController();
    }
}
