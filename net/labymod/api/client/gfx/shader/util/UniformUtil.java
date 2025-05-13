// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.nio.FloatBuffer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.labymod.api.util.math.vector.FloatMatrix4;

public final class UniformUtil
{
    public static final int MAX_BONES = 254;
    public static final int MATRIX4_SIZE = 16;
    private static final FloatMatrix4 BONE_MATRIX;
    private static final Int2ObjectMap<float[]> DEFAULT_BONE_MATRICES_HOLDER;
    
    public static float[] getDefaultBoneMatrices(final int index) {
        float[] boneMatrices = (float[])UniformUtil.DEFAULT_BONE_MATRICES_HOLDER.get(index);
        if (boneMatrices == null) {
            final int boneCount = index / 16;
            UniformUtil.BONE_MATRIX.identity();
            boneMatrices = new float[index];
            for (int i = 0; i < boneCount; ++i) {
                UniformUtil.BONE_MATRIX.store(i * 16, boneMatrices);
            }
            UniformUtil.DEFAULT_BONE_MATRICES_HOLDER.put(index, (Object)boneMatrices);
        }
        return boneMatrices;
    }
    
    public static FloatMatrix4 getBoneMatrix(final int index, final FloatBuffer buffer) {
        UniformUtil.BONE_MATRIX.identity();
        UniformUtil.BONE_MATRIX.load(index * 16, buffer);
        return UniformUtil.BONE_MATRIX;
    }
    
    static {
        BONE_MATRIX = FloatMatrix4.newIdentity();
        DEFAULT_BONE_MATRICES_HOLDER = (Int2ObjectMap)new Int2ObjectOpenHashMap();
    }
}
