// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw.cubemap;

import java.util.function.Supplier;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.render.vertex.BufferBuilder;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.vertex.phase.RenderPhases;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.LabyAPI;
import javax.inject.Singleton;

@Singleton
public class CompactCubeMapRenderer
{
    private final LabyAPI labyAPI;
    
    @Inject
    public CompactCubeMapRenderer(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    public void render(final Stack stack, final ResourceLocation location, final float xRot, final float yRot, final float delta) {
        final RenderPipeline renderPipeline = this.labyAPI.renderPipeline();
        final BufferBuilder builder = renderPipeline.createBufferBuilder();
        builder.begin(RenderPhases.createTexturePhase(location, true));
        for (int index = 0; index < 4; ++index) {
            stack.push();
            final float x = (index % 2 / 2.0f - 0.5f) / 256.0f;
            final float y = (index / 2 / 2.0f - 0.5f) / 256.0f;
            final float z = 0.0f;
            stack.translate(x, y, z);
            stack.rotate(xRot, 1.0f, 0.0f, 0.0f);
            stack.rotate(yRot, 0.0f, 1.0f, 0.0f);
            final int alpha = Math.round(255.0f * delta) / (index + 1);
            for (final CubeMapSide side : CubeMapSide.values()) {
                side.putData(builder, stack, alpha);
            }
            stack.pop();
        }
        builder.uploadToBuffer();
    }
    
    public enum CubeMapSide
    {
        LEFT(0.0f, 0.0f, 0.5f, 0.33333334f, () -> new FloatVector3(-1.0f, -1.0f, 1.0f), () -> new FloatVector3(-1.0f, 1.0f, 1.0f), () -> new FloatVector3(1.0f, 1.0f, 1.0f), () -> new FloatVector3(1.0f, -1.0f, 1.0f)), 
        RIGHT(0.0f, 0.33333334f, 0.5f, 0.6666667f, () -> new FloatVector3(1.0f, -1.0f, -1.0f), () -> new FloatVector3(1.0f, 1.0f, -1.0f), () -> new FloatVector3(-1.0f, 1.0f, -1.0f), () -> new FloatVector3(-1.0f, -1.0f, -1.0f)), 
        CENTER(0.5f, 0.0f, 1.0f, 0.33333334f, () -> new FloatVector3(1.0f, -1.0f, 1.0f), () -> new FloatVector3(1.0f, 1.0f, 1.0f), () -> new FloatVector3(1.0f, 1.0f, -1.0f), () -> new FloatVector3(1.0f, -1.0f, -1.0f)), 
        BACK(0.5f, 0.33333334f, 1.0f, 0.6666667f, () -> new FloatVector3(-1.0f, -1.0f, -1.0f), () -> new FloatVector3(-1.0f, 1.0f, -1.0f), () -> new FloatVector3(-1.0f, 1.0f, 1.0f), () -> new FloatVector3(-1.0f, -1.0f, 1.0f)), 
        TOP(0.0f, 0.6666667f, 0.5f, 1.0f, () -> new FloatVector3(-1.0f, -1.0f, -1.0f), () -> new FloatVector3(-1.0f, -1.0f, 1.0f), () -> new FloatVector3(1.0f, -1.0f, 1.0f), () -> new FloatVector3(1.0f, -1.0f, -1.0f)), 
        BOTTOM(0.5f, 0.6666667f, 1.0f, 1.0f, () -> new FloatVector3(-1.0f, 1.0f, 1.0f), () -> new FloatVector3(-1.0f, 1.0f, -1.0f), () -> new FloatVector3(1.0f, 1.0f, -1.0f), () -> new FloatVector3(1.0f, 1.0f, 1.0f));
        
        private final float x;
        private final float y;
        private final float width;
        private final float height;
        private final FloatVector3 leftTopPoint;
        private final FloatVector3 leftBottomPoint;
        private final FloatVector3 rightBottomPoint;
        private final FloatVector3 rightTopPoint;
        
        private CubeMapSide(final float x, final float y, final float width, final float height, final Supplier<FloatVector3> leftTopPoint, final Supplier<FloatVector3> leftBottomPoint, final Supplier<FloatVector3> rightBottomPoint, final Supplier<FloatVector3> rightTopPoint) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.leftTopPoint = leftTopPoint.get();
            this.leftBottomPoint = leftBottomPoint.get();
            this.rightBottomPoint = rightBottomPoint.get();
            this.rightTopPoint = rightTopPoint.get();
        }
        
        public void putData(final BufferBuilder builder, final Stack stack, final int intAlpha) {
            final float alpha = intAlpha / 255.0f;
            builder.vertex(stack, this.leftTopPoint.getX(), this.leftTopPoint.getY(), this.leftTopPoint.getZ()).color(1.0f, 1.0f, 1.0f, alpha).texture(this.x, this.y).lightMap(15728880).next();
            builder.vertex(stack, this.leftBottomPoint.getX(), this.leftBottomPoint.getY(), this.leftBottomPoint.getZ()).color(1.0f, 1.0f, 1.0f, alpha).texture(this.x, this.height).lightMap(15728880).next();
            builder.vertex(stack, this.rightBottomPoint.getX(), this.rightBottomPoint.getY(), this.rightBottomPoint.getZ()).color(1.0f, 1.0f, 1.0f, alpha).texture(this.width, this.height).lightMap(15728880).next();
            builder.vertex(stack, this.rightTopPoint.getX(), this.rightTopPoint.getY(), this.rightTopPoint.getZ()).color(1.0f, 1.0f, 1.0f, alpha).texture(this.width, this.y).lightMap(15728880).next();
        }
    }
}
