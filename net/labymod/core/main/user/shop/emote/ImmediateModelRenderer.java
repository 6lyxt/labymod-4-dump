// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.emote;

import java.util.Iterator;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.gfx.pipeline.blaze3d.buffer.Blaze3DBufferSource;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.Blaze3DRenderType;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.matrix.ArrayStackProvider;
import net.labymod.api.loader.platform.PlatformEnvironment;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.compiler.ImmediateModelCompiler;
import net.labymod.api.client.render.model.compiler.VertexCompiler;

public class ImmediateModelRenderer
{
    private static final VertexCompiler LEGACY_COMPILER;
    private final ImmediateModelCompiler modelCompiler;
    @Nullable
    private final Stack ancientStack;
    private final boolean ancientOpenGl;
    
    public ImmediateModelRenderer() {
        this.ancientOpenGl = PlatformEnvironment.isAncientOpenGL();
        this.modelCompiler = ImmediateModelCompiler.findCompiler(this.ancientOpenGl);
        if (this.ancientOpenGl) {
            this.modelCompiler.setVertexCompiler(ImmediateModelRenderer.LEGACY_COMPILER);
        }
        this.ancientStack = (this.ancientOpenGl ? Stack.create(new ArrayStackProvider(64)) : null);
    }
    
    public void render(Stack stack, final Model model, final Blaze3DRenderType renderType, final float alpha) {
        if (model == null) {
            return;
        }
        final Blaze3DBufferSource bufferSource = Laby.gfx().blaze3DBufferSource();
        final BufferConsumer consumer = bufferSource.getBuffer(renderType);
        stack = ((this.ancientStack == null) ? stack : this.ancientStack);
        for (final ModelPart part : model.getChildren().values()) {
            this.modelCompiler.compile(stack, consumer, part, 1.0f, 1.0f, 1.0f, alpha);
        }
        if (this.ancientOpenGl) {
            bufferSource.endBuffer();
        }
    }
    
    static {
        LEGACY_COMPILER = ((consumer, x, y, z, red, green, blue, alpha, u, v, packedOverlay, packedLight, normalX, normalY, normalZ) -> {
            consumer.pos(x, y, z);
            consumer.uv(u, v);
            consumer.color(red, green, blue, alpha);
            consumer.normal(normalX, normalY, normalZ);
            consumer.endVertex();
        });
    }
}
