// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model;

import net.labymod.api.util.math.vector.FloatVector3;

public final class ModelUtil
{
    public static float getScale(final ModelPart part, final ScaleConsumer consumer) {
        return consumer.consume(part.getModelPartTransform().getScale()) * consumer.consume(part.getAnimationTransformation().getScale());
    }
    
    @FunctionalInterface
    public interface ScaleConsumer
    {
        float consume(final FloatVector3 p0);
    }
}
