// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.appearance;

import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface DynamicBackgroundConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> enabled();
    
    ConfigProperty<Boolean> shader();
    
    ConfigProperty<Boolean> coloredLight();
    
    ConfigProperty<Float> brightness();
    
    ConfigProperty<Float> blur();
    
    ConfigProperty<Float> resolution();
    
    ConfigProperty<Boolean> limitFpsWhenUnfocused();
    
    ConfigProperty<Boolean> animatedTextures();
    
    ConfigProperty<Boolean> snowflakes();
}
