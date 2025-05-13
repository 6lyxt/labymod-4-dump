// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.thirdparty.optifine;

import net.labymod.api.thirdparty.optifine.OptiFineConfig;

public class NOPOptiFineConfig implements OptiFineConfig
{
    @Override
    public boolean hasShaders() {
        return false;
    }
    
    @Override
    public int getActiveProgramId() {
        return -1;
    }
}
