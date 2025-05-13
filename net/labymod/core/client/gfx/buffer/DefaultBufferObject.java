// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.buffer;

import java.lang.invoke.CallSite;
import java.lang.reflect.UndeclaredThrowableException;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.SwitchBootstraps;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.buffer.MapBufferAccess;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryBlock;
import java.nio.FloatBuffer;
import java.nio.DoubleBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import java.util.Objects;
import net.labymod.core.main.profiler.RenderProfiler;
import java.nio.Buffer;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.buffer.BufferUsage;
import net.labymod.api.client.gfx.buffer.BufferTarget;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.buffer.BufferObject;

public class DefaultBufferObject implements BufferObject
{
    private final GFXBridge gfx;
    private final BufferTarget bufferTarget;
    private final BufferUsage usage;
    private final int id;
    private boolean disposed;
    
    private DefaultBufferObject(final BufferTarget bufferTarget, final BufferUsage usage) {
        this.gfx = Laby.gfx();
        this.bufferTarget = bufferTarget;
        this.usage = usage;
        this.id = this.gfx.genBuffers();
    }
    
    @Override
    public int getId() {
        return this.id;
    }
    
    @Override
    public void bind() {
        this.gfx.bindBuffer(this.bufferTarget, this.id);
    }
    
    @Override
    public void bindBufferBase(final int index) {
        this.gfx.bindBufferBase(this.bufferTarget, index, this.id);
    }
    
    @Override
    public void bindBufferRange(final int index, final long offset, final long size) {
        this.gfx.bindBufferRange(this.bufferTarget, index, this.id, offset, size);
    }
    
    @Override
    public void unbind() {
        this.gfx.unbindBuffer(this.bufferTarget);
    }
    
    @Override
    public void upload(final Buffer data) {
        RenderProfiler.setBufferMemory(this.id, this.bufferTarget.getId(), data.limit() - data.position());
        Objects.requireNonNull(data);
        switch (/* invokedynamic(!) */ProcyonInvokeDynamicHelper_6.invoke(data, false)) {
            case 0: {
                final ByteBuffer byteBuffer = (ByteBuffer)data;
                this.gfx.bufferData(this.bufferTarget, byteBuffer, this.usage);
                break;
            }
            case 1: {
                final IntBuffer intBuffer = (IntBuffer)data;
                this.gfx.bufferData(this.bufferTarget, intBuffer, this.usage);
                break;
            }
            case 2: {
                final ShortBuffer shortBuffer = (ShortBuffer)data;
                this.gfx.bufferData(this.bufferTarget, shortBuffer, this.usage);
                break;
            }
            case 3: {
                final LongBuffer longBuffer = (LongBuffer)data;
                this.gfx.bufferData(this.bufferTarget, longBuffer, this.usage);
                break;
            }
            case 4: {
                final DoubleBuffer doubleBuffer = (DoubleBuffer)data;
                this.gfx.bufferData(this.bufferTarget, doubleBuffer, this.usage);
                break;
            }
            case 5: {
                final FloatBuffer floatBuffer = (FloatBuffer)data;
                this.gfx.bufferData(this.bufferTarget, floatBuffer, this.usage);
                break;
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }
    
    @Override
    public void upload(final long value) {
        RenderProfiler.setBufferMemory(this.id, this.bufferTarget.getId(), value);
        this.gfx.bufferData(this.bufferTarget, value, this.usage);
    }
    
    @Override
    public void upload(final MemoryBlock block) {
        RenderProfiler.setBufferMemory(this.id, this.bufferTarget.getId(), block.size());
        this.gfx.bufferData(this.bufferTarget, block, this.usage);
    }
    
    @Override
    public ByteBuffer map(final MapBufferAccess access) {
        return this.gfx.mapBuffer(this.bufferTarget, access);
    }
    
    @Override
    public boolean unmap() {
        return this.gfx.unmapBuffer(this.bufferTarget);
    }
    
    @Override
    public boolean isDisposed() {
        return this.disposed;
    }
    
    @Override
    public void dispose() {
        this.gfx.deleteBuffers(this.id);
        RenderProfiler.deleteBuffer(this.id);
        this.disposed = true;
    }
    
    @Singleton
    @Implements(Factory.class)
    public static class DefaultBufferObjectFactory implements Factory
    {
        @Inject
        public DefaultBufferObjectFactory() {
        }
        
        @Override
        public BufferObject create(final BufferTarget target, final BufferUsage usage) {
            return new DefaultBufferObject(target, usage);
        }
    }
    
    // This helper class was generated by Procyon to approximate the behavior of an
    // 'invokedynamic' instruction that it doesn't know how to interpret.
    private static final class ProcyonInvokeDynamicHelper_6
    {
        private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
        private static MethodHandle handle;
        private static volatile int fence;
        
        private static MethodHandle handle() {
            final MethodHandle handle = ProcyonInvokeDynamicHelper_6.handle;
            if (handle != null)
                return handle;
            return ProcyonInvokeDynamicHelper_6.ensureHandle();
        }
        
        private static MethodHandle ensureHandle() {
            ProcyonInvokeDynamicHelper_6.fence = 0;
            MethodHandle handle = ProcyonInvokeDynamicHelper_6.handle;
            if (handle == null) {
                MethodHandles.Lookup lookup = ProcyonInvokeDynamicHelper_6.LOOKUP;
                try {
                    handle = ((CallSite)SwitchBootstraps.typeSwitch(lookup, "typeSwitch", MethodType.methodType(int.class, Object.class, int.class), ByteBuffer.class, IntBuffer.class, ShortBuffer.class, LongBuffer.class, DoubleBuffer.class, FloatBuffer.class)).dynamicInvoker();
                }
                catch (Throwable t) {
                    throw new UndeclaredThrowableException(t);
                }
                ProcyonInvokeDynamicHelper_6.fence = 1;
                ProcyonInvokeDynamicHelper_6.handle = handle;
                ProcyonInvokeDynamicHelper_6.fence = 0;
            }
            return handle;
        }
        
        private static int invoke(Object p0, int p1) {
            try {
                return ProcyonInvokeDynamicHelper_6.handle().invokeExact(p0, p1);
            }
            catch (Throwable t) {
                throw new UndeclaredThrowableException(t);
            }
        }
    }
}
