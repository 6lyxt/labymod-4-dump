// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx;

import net.labymod.api.Constants;
import net.labymod.api.Laby;

public final class GFXBridgeAccessor
{
    public static GFXBridge get() {
        return Laby.references().gfxBridge(Constants.SystemProperties.getBoolean(Constants.SystemProperties.OPENGL) ? "gfx_bridge_development" : "gfx_bridge_production");
    }
}
