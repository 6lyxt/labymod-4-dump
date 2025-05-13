// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.matrix;

import net.labymod.api.util.math.vector.FloatMatrix4;

public class NOPStack extends Stack
{
    public NOPStack() {
        super(new ArrayStackProvider());
    }
    
    @Override
    public void push() {
    }
    
    @Override
    public void pop() {
    }
    
    @Override
    public void pushAndPop(final Runnable runnable) {
    }
    
    @Override
    public void translate(final float x, final float y, final float z) {
    }
    
    @Override
    public void rotate(final float angle, final float x, final float y, final float z) {
    }
    
    @Override
    public void rotateRadians(final float radians, final float x, final float y, final float z) {
    }
    
    @Override
    public void scale(final float scale) {
    }
    
    @Override
    public void scale(final float x, final float y, final float z) {
    }
    
    @Override
    public void multiply(final FloatMatrix4 matrix) {
    }
    
    @Override
    public void identity() {
    }
}
