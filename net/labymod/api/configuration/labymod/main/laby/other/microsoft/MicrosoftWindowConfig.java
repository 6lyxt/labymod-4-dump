// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.other.microsoft;

import net.labymod.api.util.Color;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface MicrosoftWindowConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> immersiveDarkMode();
    
    ConfigProperty<WindowMaterial> windowMaterial();
    
    ConfigProperty<CornerCurvatures> cornerCurvatures();
    
    ConfigProperty<Boolean> defaultWindowBorderColor();
    
    ConfigProperty<Boolean> hideWindowBorder();
    
    ConfigProperty<Color> windowBorderColor();
    
    ConfigProperty<Boolean> defaultWindowTitleBarColor();
    
    ConfigProperty<Color> windowTitleBarColor();
    
    ConfigProperty<Boolean> defaultTitleTextColor();
    
    ConfigProperty<Color> titleTextColor();
    
    public enum WindowMaterial implements IdProvider
    {
        AUTO(0), 
        NONE(1), 
        MICA(2), 
        ACRYLIC(3), 
        TABBED(4);
        
        private final int id;
        
        private WindowMaterial(final int id) {
            this.id = id;
        }
        
        @Override
        public int getId() {
            return this.id;
        }
    }
    
    public enum CornerCurvatures implements IdProvider
    {
        DEFAULT(0), 
        ROUND(2), 
        DO_NOT_ROUND(1), 
        ROUND_SMALL(3);
        
        private final int id;
        
        private CornerCurvatures(final int id) {
            this.id = id;
        }
        
        @Override
        public int getId() {
            return this.id;
        }
    }
    
    public interface IdProvider
    {
        int getId();
    }
}
