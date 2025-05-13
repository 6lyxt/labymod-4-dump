// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.os;

import java.io.InputStream;
import net.labymod.api.client.resources.texture.GameImage;

public interface OperatingSystemAccessor
{
    void copyToClipboard(final GameImage p0);
    
    GameImage getImageInClipboard();
    
    void setWindowIcon(final long p0, final InputStream p1);
}
