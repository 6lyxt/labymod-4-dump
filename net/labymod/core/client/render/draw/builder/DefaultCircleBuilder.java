// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.builder;

import net.labymod.api.client.render.draw.builder.CircleBuilder;

public class DefaultCircleBuilder<T extends CircleBuilder<T>> extends DefaultRendererBuilder<T> implements CircleBuilder<T>
{
    protected float innerRadius;
    protected float outerRadius;
    protected float startingAngle;
    protected float endingAngle;
    
    protected DefaultCircleBuilder() {
        this.resetBuilder();
    }
    
    @Override
    public T donutRadius(final float innerRadius, final float outerRadius) {
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        return (T)this;
    }
    
    @Override
    public T partial(final float startingAngle, final float endingAngle) {
        this.startingAngle = startingAngle;
        this.endingAngle = endingAngle;
        return (T)this;
    }
    
    @Override
    public void resetBuilder() {
        super.resetBuilder();
        this.innerRadius = 0.0f;
        this.outerRadius = 0.0f;
        this.startingAngle = 0.0f;
        this.endingAngle = 360.0f;
    }
}
