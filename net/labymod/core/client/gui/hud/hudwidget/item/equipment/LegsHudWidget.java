// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.item.equipment;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.item.EquipmentWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.item.EquipmentHudWidget;

@SpriteSlot(x = 2, y = 7)
public class LegsHudWidget extends EquipmentHudWidget<EquipmentWidgetConfig>
{
    public LegsHudWidget() {
        super("legs");
    }
    
    @Override
    public Icon createPlaceholderIcon() {
        return Textures.SpriteHudPlaceholder.LEGGINGS;
    }
    
    @Override
    public void initializePreConfigured(final EquipmentWidgetConfig config) {
        super.initializePreConfigured(config);
        config.setEnabled(true);
        config.setDropzone(NamedHudWidgetDropzones.ITEM_MIDDLE_RIGHT);
        config.displayMode().set(EquipmentWidgetConfig.DisplayMode.BAR);
    }
    
    @Override
    protected ItemStack getItemStackToDisplay(final ClientPlayer player) {
        return player.getEquipmentItemStack(LivingEntity.EquipmentSpot.LEGS);
    }
}
