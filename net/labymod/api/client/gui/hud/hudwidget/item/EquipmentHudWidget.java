// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.hudwidget.item;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.player.GameMode;

public abstract class EquipmentHudWidget<T extends EquipmentWidgetConfig> extends ItemHudWidget<T>
{
    protected EquipmentHudWidget(final String id) {
        super(id, EquipmentWidgetConfig.class);
    }
    
    protected EquipmentHudWidget(final String id, final Class<T> config) {
        super(id, config);
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        this.updateItemStack(isEditorContext);
        this.updateItemName(isEditorContext);
    }
    
    private void updateItemStack(final boolean isEditorContext) {
        if (!this.isVisibleInGame()) {
            return;
        }
        final ClientPlayer player = this.labyAPI.minecraft().getClientPlayer();
        if (player == null || player.gameMode() == GameMode.SPECTATOR) {
            this.updateItemStack(null, isEditorContext);
            return;
        }
        final ItemStack itemStack = this.getItemStackToDisplay(player);
        this.updateItemStack(itemStack, isEditorContext);
    }
    
    private void updateItemName(final boolean isEditorContext) {
        final EquipmentWidgetConfig.DisplayMode displayMode = this.config.displayMode().get();
        if (displayMode == EquipmentWidgetConfig.DisplayMode.OFF || displayMode == EquipmentWidgetConfig.DisplayMode.BAR) {
            this.updateItemName(null, isEditorContext);
            return;
        }
        final ClientPlayer player = this.labyAPI.minecraft().getClientPlayer();
        final ItemStack itemStack = (player == null) ? null : this.getItemStackToDisplay(player);
        if (itemStack == null && !isEditorContext) {
            return;
        }
        final int maxDurability = isEditorContext ? 128 : itemStack.getMaximumDamage();
        final int durability = isEditorContext ? 32 : (maxDurability - itemStack.getCurrentDamageValue());
        final int amount = isEditorContext ? 64 : itemStack.getSize();
        if (maxDurability == 0) {
            this.updateItemName((amount > 1) ? Component.text(amount) : null, false);
            return;
        }
        this.updateItemName(displayMode, maxDurability, durability, isEditorContext);
    }
    
    private void updateItemName(final EquipmentWidgetConfig.DisplayMode displayMode, final int maxDurability, final int durability, final boolean isEditorContext) {
        final Component name = Component.text(displayMode.display(durability, maxDurability));
        this.updateItemName(name, isEditorContext);
    }
    
    @Override
    public boolean isVisibleInGame() {
        final ClientPlayer player = this.labyAPI.minecraft().getClientPlayer();
        if (player == null || player.gameMode() == GameMode.SPECTATOR) {
            return false;
        }
        final ItemStack itemStack = this.getItemStackToDisplay(player);
        return itemStack != null && !itemStack.isAir();
    }
    
    @Override
    protected boolean decorate() {
        return this.config.displayMode().get() == EquipmentWidgetConfig.DisplayMode.BAR;
    }
    
    protected abstract ItemStack getItemStackToDisplay(final ClientPlayer p0);
}
