// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.other;

import java.util.Map;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface AdvancedConfig extends ConfigAccessor
{
    boolean debugger();
    
    boolean refreshHotkey();
    
    Map<String, Boolean> debugWindows();
}
