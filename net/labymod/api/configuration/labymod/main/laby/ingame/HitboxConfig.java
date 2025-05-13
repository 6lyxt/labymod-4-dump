// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.ingame;

import net.labymod.api.util.Color;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface HitboxConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> enabled();
    
    ConfigProperty<Float> lineSize();
    
    ConfigProperty<Color> eyeLineColor();
    
    ConfigProperty<Color> eyeBoxColor();
    
    ConfigProperty<Color> boxColor();
}
