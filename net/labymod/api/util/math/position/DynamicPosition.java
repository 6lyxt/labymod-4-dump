// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math.position;

import net.labymod.api.util.function.DoubleSetter;
import net.labymod.api.util.function.DoubleGetter;

public class DynamicPosition implements Position
{
    private final DoubleGetter xGetter;
    private final DoubleSetter xSetter;
    private final DoubleGetter yGetter;
    private final DoubleSetter ySetter;
    private final DoubleGetter zGetter;
    private final DoubleSetter zSetter;
    
    public DynamicPosition(final DoubleGetter xGetter, final DoubleSetter xSetter, final DoubleGetter yGetter, final DoubleSetter ySetter, final DoubleGetter zGetter, final DoubleSetter zSetter) {
        this.xGetter = xGetter;
        this.xSetter = xSetter;
        this.yGetter = yGetter;
        this.ySetter = ySetter;
        this.zGetter = zGetter;
        this.zSetter = zSetter;
    }
    
    @Override
    public double getX() {
        return this.xGetter.get();
    }
    
    @Override
    public void setX(final double x) {
        this.xSetter.set(x);
    }
    
    @Override
    public double getY() {
        return this.yGetter.get();
    }
    
    @Override
    public void setY(final double y) {
        this.ySetter.set(y);
    }
    
    @Override
    public double getZ() {
        return this.zGetter.get();
    }
    
    @Override
    public void setZ(final double z) {
        this.zSetter.set(z);
    }
}
