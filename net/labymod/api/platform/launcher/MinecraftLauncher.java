// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.platform.launcher;

import java.io.IOException;
import java.io.File;

public interface MinecraftLauncher
{
    File getDirectory();
    
    LauncherVendorType currentType();
    
    boolean kill() throws IOException;
}
