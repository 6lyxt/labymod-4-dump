// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text;

import net.labymod.api.client.render.font.text.TextBuffer;

public class DefaultTextBuffer implements TextBuffer
{
    private final float width;
    private final boolean needsVanilla;
    private final Runnable uploadToBuffer;
    
    public DefaultTextBuffer(final float width) {
        this(width, false);
    }
    
    public DefaultTextBuffer(final float width, final boolean needsVanilla) {
        this(width, needsVanilla, () -> {});
    }
    
    public DefaultTextBuffer(final float width, final boolean needsVanilla, final Runnable uploadToBuffer) {
        this.width = width;
        this.needsVanilla = needsVanilla;
        this.uploadToBuffer = uploadToBuffer;
    }
    
    @Override
    public float getWidth() {
        return this.width;
    }
    
    @Override
    public boolean needsVanilla() {
        return this.needsVanilla;
    }
    
    @Override
    public void uploadToBuffer() {
        if (this.uploadToBuffer == null) {
            return;
        }
        this.uploadToBuffer.run();
    }
}
