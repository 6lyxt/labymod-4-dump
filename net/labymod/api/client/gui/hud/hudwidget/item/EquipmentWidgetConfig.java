// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.hudwidget.item;

import java.util.Locale;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;

public class EquipmentWidgetConfig extends HudWidgetConfig
{
    @DropdownWidget.DropdownSetting
    @CustomTranslation("labymod.hudWidget.equipmentCommon.displayMode")
    private final ConfigProperty<DisplayMode> displayMode;
    
    public EquipmentWidgetConfig() {
        this.displayMode = ConfigProperty.createEnum(DisplayMode.FULL);
    }
    
    public ConfigProperty<DisplayMode> displayMode() {
        return this.displayMode;
    }
    
    public enum DisplayMode
    {
        OFF, 
        BAR, 
        PERCENT, 
        AVAILABLE, 
        FULL;
        
        public String display(final int durability, final int maxDurability) {
            switch (this.ordinal()) {
                case 0: {
                    return "";
                }
                case 2: {
                    return "" + MathHelper.floor(durability / (float)maxDurability * 100.0f);
                }
                case 3: {
                    return String.format(Locale.ROOT, "%d", durability);
                }
                case 4: {
                    return durability + "/" + maxDurability;
                }
                default: {
                    throw new IllegalArgumentException("Unknown display mode: " + String.valueOf(this));
                }
            }
        }
    }
}
