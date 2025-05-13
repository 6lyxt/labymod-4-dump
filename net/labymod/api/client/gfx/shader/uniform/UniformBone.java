// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

import net.labymod.api.client.gfx.shader.util.UniformUtil;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.util.Buffers;
import java.nio.FloatBuffer;

public class UniformBone extends AbstractUniform
{
    private final FloatBuffer buffer;
    private final int maxBones;
    
    public UniformBone(final String name) {
        this(name, 254);
    }
    
    public UniformBone(final String name, final int maxBones) {
        super(name);
        this.buffer = Buffers.createFloatBuffer(16 * maxBones);
        this.maxBones = maxBones;
        this.resetBoneMatrix();
    }
    
    public void setBoneMatrix(final int id, final FloatMatrix4 boneMatrix) {
        boneMatrix.store(id * 16, this.buffer);
        this.shouldUpload = true;
    }
    
    public FloatMatrix4 getBoneMatrix(final int id) {
        return UniformUtil.getBoneMatrix(id, this.buffer);
    }
    
    public void resetBoneMatrix() {
        this.put(UniformUtil.getDefaultBoneMatrices(this.maxBones * 16));
        this.shouldUpload = true;
    }
    
    public void store(final float[] boneMatrices) {
        this.put(boneMatrices);
        this.shouldUpload = true;
    }
    
    public float[] read(float[] boneMatrices) {
        this.buffer.rewind();
        if (boneMatrices == null) {
            boneMatrices = new float[this.buffer.capacity()];
        }
        this.buffer.get(boneMatrices);
        return boneMatrices;
    }
    
    @Override
    protected void updateUniform() {
        this.buffer.rewind();
        this.bridge.uniformMatrix4fv(this.getLocation(), false, this.buffer);
    }
    
    private void put(final float[] value) {
        this.buffer.rewind();
        this.buffer.put(value);
    }
}
