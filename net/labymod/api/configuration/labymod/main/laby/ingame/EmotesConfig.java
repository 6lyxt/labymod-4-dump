// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.ingame;

import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface EmotesConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> emotes();
    
    ConfigProperty<Boolean> orderEmotesClockwise();
    
    ConfigProperty<Boolean> showCosmeticsInWheel();
    
    ConfigProperty<Boolean> firstPersonHeadAnimation();
    
    ConfigProperty<Boolean> emotePerspective();
    
    ConfigProperty<Boolean> emoteDebug();
}
