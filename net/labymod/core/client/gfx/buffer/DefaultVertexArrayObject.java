// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.buffer;

import net.labymod.api.client.gfx.opengl.NamedOpenGLVersion;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.buffer.VertexArrayObject;

public class DefaultVertexArrayObject implements VertexArrayObject
{
    private final GFXBridge gfx;
    private final int id;
    private boolean disposed;
    
    private DefaultVertexArrayObject() {
        this.gfx = Laby.gfx();
        this.id = this.gfx.genVertexArrays();
    }
    
    @Override
    public void bind() {
        this.gfx.bindVertexArray(this.id);
    }
    
    @Override
    public void unbind() {
        this.gfx.unbindVertexArray();
    }
    
    @Override
    public boolean isDisposed() {
        return this.disposed;
    }
    
    @Override
    public void dispose() {
        this.gfx.deleteVertexArrays(this.id);
        this.disposed = true;
    }
    
    @Override
    public int getId() {
        return this.id;
    }
    
    @Singleton
    @Implements(Factory.class)
    public static class DefaultVertexArrayObjectFactory implements Factory
    {
        @Override
        public VertexArrayObject create() {
            if (!NamedOpenGLVersion.GL30.isSupported() && !Laby.gfx().capabilities().isArbVertexArrayObjectSupported()) {
                return null;
            }
            return new DefaultVertexArrayObject();
        }
    }
}
