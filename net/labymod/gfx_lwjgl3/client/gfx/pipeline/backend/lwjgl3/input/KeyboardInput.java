// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input;

import java.nio.ByteBuffer;
import net.labymod.api.util.Disposable;

public interface KeyboardInput extends Disposable
{
    void create();
    
    void poll(final ByteBuffer p0);
    
    void read(final ByteBuffer p0);
}
