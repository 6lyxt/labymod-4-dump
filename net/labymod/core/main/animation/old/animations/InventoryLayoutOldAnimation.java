// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old.animations;

import java.util.Iterator;
import java.util.Collection;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

public class InventoryLayoutOldAnimation extends AbstractOldAnimation
{
    public static final String NAME = "inventory_layout";
    private final LabyAPI labyAPI;
    
    public InventoryLayoutOldAnimation() {
        super("inventory_layout");
        this.labyAPI = Laby.labyAPI();
    }
    
    public boolean removeRecipeBook() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.shouldRemoveRecipeBook());
    }
    
    public boolean canUseOldSurvivalInventory() {
        return this.isOldSurvivalLayout() && this.canUseOldInventory();
    }
    
    public boolean canUseOldCreativeLayout() {
        return this.isOldCreativeLayout() && this.canUseOldInventory();
    }
    
    public int getEntityXShift() {
        return 44;
    }
    
    public int getEntityYShift() {
        return 0;
    }
    
    private boolean canUseOldInventory() {
        boolean oldInventory = false;
        final Collection<String> selectedPacks = this.labyAPI.renderPipeline().resourcePackRepository().getSelectedPacks();
        for (final String selectedPack : selectedPacks) {
            oldInventory = (selectedPack.equals("vanilla") || selectedPack.equals("programer_art"));
            if (oldInventory) {
                break;
            }
        }
        return oldInventory;
    }
    
    private boolean isOldSurvivalLayout() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldSurvivalLayout());
    }
    
    private boolean isOldCreativeLayout() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldCreativeLayout());
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}
