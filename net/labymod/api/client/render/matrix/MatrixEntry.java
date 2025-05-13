// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.matrix;

import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.util.math.vector.FloatMatrix4;

record MatrixEntry(FloatMatrix4 position, FloatMatrix3 normal) {
    public MatrixEntry {
        this(new FloatMatrix4().identity(), new FloatMatrix3().identity());
    }
    
    public MatrixEntry multiply(final Quaternion quaternion) {
        this.position.multiply(quaternion);
        this.normal.multiply(quaternion);
        return this;
    }
    
    public MatrixEntry store(final MatrixEntry other) {
        this.position.set(other.position);
        this.normal.store(other.normal);
        return this;
    }
    
    public MatrixEntry identity() {
        this.position.identity();
        this.normal.identity();
        return this;
    }
}
