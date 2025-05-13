// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

import java.nio.Buffer;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryWriter;
import net.labymod.api.client.gfx.buffer.BufferUsage;
import net.labymod.api.client.gfx.buffer.BufferTarget;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryBlock;
import net.labymod.api.client.gfx.buffer.BufferObject;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.util.Disposable;

public class UniformBuffer<T extends UniformBufferProvider> implements Disposable
{
    private final GFXBridge gfx;
    private final BufferObject ubo;
    private final CharSequence name;
    private final int index;
    private final MemoryBlock memoryBlock;
    private final T provider;
    
    public UniformBuffer(final int index, final CharSequence name, final T provider) {
        this.gfx = Laby.gfx();
        this.index = index;
        this.name = name;
        this.provider = provider;
        this.ubo = this.gfx.createBuffer(BufferTarget.UNIFORM, BufferUsage.DYNAMIC_DRAW);
        (this.memoryBlock = this.gfx.backend().memoryHandler().mallocBlock(provider.getByteSize())).clear();
    }
    
    public void bind() {
        this.ubo.bind();
    }
    
    public void update() {
        final MemoryWriter writer = this.memoryBlock.getOrCreateWriter();
        writer.start();
        this.provider.write(writer);
        writer.finish();
        this.bind();
        this.ubo.upload(this.memoryBlock);
        this.unbind();
    }
    
    public void bindBufferBase() {
        this.ubo.bindBufferBase(this.getIndex());
    }
    
    public void upload(final Buffer data) {
        this.ubo.upload(data);
    }
    
    public void unbind() {
        this.ubo.unbind();
    }
    
    public CharSequence getName() {
        return this.name;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public int getId() {
        return this.ubo.getId();
    }
    
    @Override
    public void dispose() {
        if (this.ubo.isDisposed()) {
            return;
        }
        this.ubo.dispose();
        this.memoryBlock.free();
    }
}
