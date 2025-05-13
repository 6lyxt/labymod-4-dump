// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.api.util.Disposable;

public interface MouseInput extends Disposable
{
    void create();
    
    void poll(final IntBuffer p0, final ByteBuffer p1);
    
    void read(final ByteBuffer p0);
    
    void setCursorPosition(final int p0, final int p1);
    
    void grab(final boolean p0);
    
    boolean isInsideWindow();
    
    int getButtonCount();
    
    void setRawMouseInput(final boolean p0);
}
