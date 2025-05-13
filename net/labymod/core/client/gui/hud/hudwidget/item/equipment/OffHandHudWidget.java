// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.item.equipment;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.hud.hudwidget.item.EquipmentWidgetConfig;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.Laby;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.item.EquipmentHudWidget;

@SpriteSlot(x = 5, y = 7)
public class OffHandHudWidget extends EquipmentHudWidget<OffHandHudWidgetConfig>
{
    private final ClassicPvPConfig classicPvPConfig;
    
    public OffHandHudWidget() {
        super("off_hand", OffHandHudWidgetConfig.class);
        this.classicPvPConfig = Laby.labyAPI().config().multiplayer().classicPvP();
    }
    
    @Override
    public void initializePreConfigured(final OffHandHudWidgetConfig config) {
        super.initializePreConfigured(config);
        config.setEnabled(true);
        config.setDropzone(NamedHudWidgetDropzones.ITEM_TOP_LEFT);
        config.displayMode().set(EquipmentWidgetConfig.DisplayMode.BAR);
    }
    
    @Override
    public Icon createPlaceholderIcon() {
        return Textures.SpriteHudPlaceholder.OFF_HAND;
    }
    
    @Override
    protected ItemStack getItemStackToDisplay(final ClientPlayer player) {
        final ItemStack itemStack = player.getOffHandItemStack();
        if (this.classicPvPConfig.oldSword().get() && itemStack.isShield() && ((OffHandHudWidgetConfig)this.config).hideShieldWithOldSword().get()) {
            return null;
        }
        return itemStack;
    }
    
    public static class OffHandHudWidgetConfig extends EquipmentWidgetConfig
    {
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> hideShieldWithOldSword;
        
        public OffHandHudWidgetConfig() {
            this.hideShieldWithOldSword = new ConfigProperty<Boolean>(true);
        }
        
        public ConfigProperty<Boolean> hideShieldWithOldSword() {
            return this.hideShieldWithOldSword;
        }
    }
}
