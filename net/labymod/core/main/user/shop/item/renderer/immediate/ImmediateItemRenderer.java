// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.renderer.immediate;

import java.util.Iterator;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.gfx.pipeline.blaze3d.buffer.Blaze3DBufferSource;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.Laby;
import net.labymod.core.main.user.shop.item.ItemRendererContext;
import net.labymod.api.client.render.model.Model;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.main.user.shop.item.AbstractItem;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.matrix.ArrayStackProvider;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.user.shop.RenderMode;
import net.labymod.api.LabyAPI;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.compiler.ImmediateModelCompiler;
import net.labymod.api.client.render.model.compiler.VertexCompiler;
import net.labymod.core.main.user.shop.item.renderer.ItemRenderer;

public class ImmediateItemRenderer extends ItemRenderer
{
    private static final VertexCompiler LEGACY_COMPILER;
    private final ImmediateModelCompiler modelCompiler;
    @Nullable
    private final Stack ancientStack;
    private final boolean ancientOpenGl;
    
    public ImmediateItemRenderer(final LabyAPI labyAPI) {
        super(labyAPI, RenderMode.IMMEDIATE);
        this.ancientOpenGl = PlatformEnvironment.isAncientOpenGL();
        this.modelCompiler = ImmediateModelCompiler.findCompiler(this.ancientOpenGl);
        if (this.ancientOpenGl) {
            this.modelCompiler.setVertexCompiler(ImmediateItemRenderer.LEGACY_COMPILER);
        }
        this.ancientStack = (this.ancientOpenGl ? Stack.create(new ArrayStackProvider(64)) : null);
    }
    
    @Override
    public void apply(final Player player, final PlayerModel playerModel, final ItemMetadata metadata, final float partialTicks, final AbstractItem item, final ResourceLocation resourceLocation) {
        final Model model = item.getModel();
        if (model == null) {
            return;
        }
        item.applyEffects(player, playerModel, metadata, metadata.isRightSide(), GeometryEffect.Type.BUFFER_CREATION);
    }
    
    @Override
    public void render(final AbstractItem item, final ItemRendererContext context) {
        final Model model = item.getModel();
        if (model == null) {
            return;
        }
        final Blaze3DBufferSource bufferSource = Laby.gfx().blaze3DBufferSource();
        final BufferConsumer consumer = bufferSource.getBuffer(Laby.references().standardBlaze3DRenderTypes().entityTranslucent(model.getTextureLocation(), false));
        final Stack stack = (this.ancientStack == null) ? context.stack() : this.ancientStack;
        for (final ModelPart part : model.getChildren().values()) {
            this.modelCompiler.compile(stack, consumer, part, 1.0f, 1.0f, 1.0f, 1.0f);
        }
        if (this.ancientOpenGl) {
            bufferSource.endBuffer();
        }
    }
    
    @Override
    public void finish() {
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
