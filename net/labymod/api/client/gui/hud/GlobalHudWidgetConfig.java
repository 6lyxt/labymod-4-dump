// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud;

import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.Formatting;
import net.labymod.api.util.Color;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface GlobalHudWidgetConfig extends ConfigAccessor
{
    ConfigProperty<Float> scale();
    
    ConfigProperty<Color> valueColor();
    
    ConfigProperty<Color> bracketColor();
    
    ConfigProperty<Color> labelColor();
    
    ConfigProperty<Integer> lineHeight();
    
    ConfigProperty<Formatting> formatting();
    
    BackgroundConfig background();
    
    ConfigProperty<Boolean> smoothMovement();
    
    ConfigProperty<Boolean> itemGravity();
    
    @Deprecated
    default ConfigProperty<Color> primaryColor() {
        return this.valueColor();
    }
    
    @Deprecated
    default ConfigProperty<Color> secondaryColor() {
        return this.bracketColor();
    }
    
    @Deprecated
    default ConfigProperty<Color> accentColor() {
        return this.labelColor();
    }
}
