// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.matrix.buffered;

import java.nio.FloatBuffer;

@Deprecated
public class BufferedMatrix<T>
{
    private T matrix;
    private FloatBuffer buffer;
    
    public BufferedMatrix(final T matrix, final FloatBuffer buffer) {
        this.matrix = matrix;
        this.buffer = buffer;
    }
    
    public T matrix() {
        return this.matrix;
    }
    
    public void matrix(final T matrix) {
        this.matrix = matrix;
    }
    
    public FloatBuffer buffer() {
        return this.buffer;
    }
    
    public void buffer(final FloatBuffer buffer) {
        this.buffer = buffer;
    }
}
