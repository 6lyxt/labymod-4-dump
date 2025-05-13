// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.render.system.shards.layering;

import org.joml.Matrix4fStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.vertex.shard.shards.layer.ViewOffsetZLayer;

@Singleton
@Implements(ViewOffsetZLayer.class)
public class VersionedViewOffsetZLayer implements ViewOffsetZLayer
{
    private static final float VIEW_SCALE_Z_EPSILON = 0.99975586f;
    
    @Override
    public void setup() {
        final Matrix4fStack stack = RenderSystem.getModelViewStack();
        stack.pushMatrix();
        stack.scale(0.99975586f, 0.99975586f, 0.99975586f);
    }
    
    @Override
    public void finish() {
        final Matrix4fStack stack = RenderSystem.getModelViewStack();
        stack.popMatrix();
    }
}
