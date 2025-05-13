// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.legacy.partner;

import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.core.client.render.model.DefaultModel;
import net.labymod.api.client.render.model.Model;
import net.labymod.core.main.user.shop.item.AbstractItem;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.StandardBlaze3DRenderTypes;
import net.labymod.api.client.gfx.pipeline.blaze3d.buffer.Blaze3DBufferSource;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.core.main.user.shop.item.items.legacy.head.EyelidsItem;
import net.labymod.api.util.Color;
import net.labymod.api.Textures;
import net.labymod.api.Laby;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.matrix.ArrayStackProvider;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.core.main.user.shop.item.ItemDetails;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.compiler.ImmediateModelCompiler;
import net.labymod.core.main.user.shop.item.items.legacy.LegacyItem;

public class SnoxhItem extends LegacyItem
{
    public static final int ID = 32;
    private static final int[] INDEX_BUFFER;
    private final Vertex[] vertices;
    private final ImmediateModelCompiler modelCompiler;
    @Nullable
    private final Stack ancientStack;
    private final boolean ancientOpenGl;
    
    public SnoxhItem(final int listId, final ItemDetails itemDetails) {
        super(listId, itemDetails);
        this.vertices = new Vertex[] { new Vertex(), new Vertex(), new Vertex(), new Vertex() };
        this.ancientOpenGl = PlatformEnvironment.isAncientOpenGL();
        this.modelCompiler = ImmediateModelCompiler.findCompiler(this.ancientOpenGl);
        if (this.ancientOpenGl) {
            this.modelCompiler.setVertexCompiler((consumer, x, y, z, red, green, blue, alpha, u, v, packedOverlay, packedLight, normalX, normalY, normalZ) -> {
                consumer.pos(x, y, z);
                consumer.uv(u, v);
                consumer.color(red, green, blue, alpha);
                consumer.normal(normalX, normalY, normalZ);
                consumer.endVertex();
                return;
            });
        }
        this.ancientStack = (this.ancientOpenGl ? Stack.create(new ArrayStackProvider(32)) : null);
    }
    
    @Override
    protected void render(Stack stack, final int packedLight, final int packedOverlay, final float partialTicks) {
        if (this.playerModel.getHead().isInvisible()) {
            return;
        }
        int brightness = this.itemMetadata.getBrightness();
        final int minColor = Math.min(brightness, 0);
        final int maxColor = brightness;
        final float colorCos = MathHelper.cos(this.player.getAgeTick() / 30.0f) * (maxColor - minColor);
        float animation = (minColor + Math.abs(colorCos)) / 255.0f;
        brightness = 2;
        final EyelidsItem.AnimationData data = ((DefaultGameUser)this.user).getUserItemStorage().getEyelidsAnimationData(36);
        if (data != null) {
            animation *= 1.0f - data.getLastRenderedPercentage();
        }
        final FloatVector4 size = this.itemMetadata.getSize();
        final float x = size.getX();
        final float y = size.getY();
        final float width = size.getZ();
        final float height = size.getW();
        final boolean overlappingLeft = x + width - 1.0f == 3.0f;
        stack = ((this.ancientStack == null) ? stack : this.ancientStack);
        stack.push();
        this.playerModel.getHead().getAnimationTransformation().transform(stack);
        final float scaleValue = 0.0625f;
        stack.translate(-4.0f * scaleValue, -8.0f * scaleValue, -0.251f);
        stack.translate(0.0f, 0.0f, (animation == 0.0f) ? 0.001f : 0.0f);
        stack.scale(scaleValue, scaleValue, scaleValue);
        final Blaze3DBufferSource bufferSource = Laby.gfx().blaze3DBufferSource();
        final StandardBlaze3DRenderTypes renderTypes = Laby.references().standardBlaze3DRenderTypes();
        final BufferConsumer consumer = bufferSource.getBuffer(this.ancientOpenGl ? renderTypes.legacySnoxhEyes() : renderTypes.entityTranslucent(Textures.WHITE, false));
        final Color[] colors = this.itemMetadata.getColors();
        Color finalColor = Color.WHITE;
        if (colors != null && colors.length != 0) {
            finalColor = colors[0];
        }
        for (int index = 0; index < 2; ++index) {
            for (int b = 0; b < brightness; ++b) {
                if (index == 0) {
                    if (!this.itemMetadata.isRightVisible()) {
                        continue;
                    }
                }
                else if (!this.itemMetadata.isLeftVisible()) {
                    continue;
                }
                this.renderGlowingEye(consumer, stack.getProvider().getPosition(), (index == 0) ? x : (8.0f - x - width), y, width, height, finalColor, animation, overlappingLeft, index == 0);
            }
        }
        final GFXBridge gfx = Laby.gfx();
        if (this.ancientOpenGl) {
            gfx.blaze3DGlStatePipeline().defaultAlphaFunc();
            gfx.shadeSmooth();
            gfx.blaze3DGlStatePipeline().disableLightMap();
        }
        if (this.itemRendererContext.isDirect() || PlatformEnvironment.isAncientOpenGL()) {
            bufferSource.endBuffer();
        }
        if (this.ancientOpenGl) {
            gfx.blaze3DGlStatePipeline().enableLightMap();
        }
        stack.pop();
    }
    
    private void renderGlowingEye(final BufferConsumer consumer, final FloatMatrix4 pose, final float x, final float y, final float width, final float height, final Color color, final float alpha, final boolean overlapping, final boolean isLeftSide) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        final float middleAlpha = 0.4f;
        final float radius = 0.6f;
        final float basis = 0.0f;
        final float middleLeft = x + basis;
        final float middleTop = y + basis;
        final float middleRight = x + width - basis;
        final float middleBottom = y + height - basis;
        this.renderGradient(consumer, pose, middleRight, middleTop - radius, 0.0f, middleLeft, middleTop - radius, 0.0f, middleLeft, middleTop, alpha, middleRight, middleTop, alpha, red, green, blue);
        this.renderGradient(consumer, pose, middleRight, middleBottom, alpha, middleRight + radius, middleBottom, 0.0f, middleRight + radius, middleTop, 0.0f, middleRight, middleTop, alpha, red, green, blue);
        this.renderGradient(consumer, pose, middleLeft, middleTop, alpha, middleLeft - radius, middleTop, 0.0f, middleLeft - radius, middleBottom, 0.0f, middleLeft, middleBottom, alpha, red, green, blue);
        this.renderGradient(consumer, pose, middleLeft, middleBottom + radius, 0.0f, middleRight, middleBottom + radius, 0.0f, middleRight, middleBottom, alpha, middleLeft, middleBottom, alpha, red, green, blue);
        this.renderGradient(consumer, pose, middleRight, middleTop, middleAlpha, middleLeft, middleTop, middleAlpha, middleLeft, middleBottom, middleAlpha, middleRight, middleBottom, middleAlpha, red, green, blue);
        final float cornerRadiusWidth = radius * MathHelper.cos(MathHelper.toRadiansFloat(45.0f));
        final float cornerRadiusHeight = radius * MathHelper.sin(MathHelper.toRadiansFloat(45.0f));
        final float lowCornerRadiusWidth = radius * MathHelper.cos(MathHelper.toRadiansFloat(22.5f));
        final float lowCornerRadiusHeight = radius * MathHelper.sin(MathHelper.toRadiansFloat(22.5f));
        final float highCornerRadiusWidth = radius * MathHelper.cos(MathHelper.toRadiansFloat(67.5f));
        final float highCornerRadiusHeight = radius * MathHelper.sin(MathHelper.toRadiansFloat(67.5f));
        if (!overlapping || isLeftSide == overlapping) {
            this.renderGradient(consumer, pose, middleLeft - radius, middleTop, 0.0f, middleLeft - lowCornerRadiusWidth, middleTop - lowCornerRadiusHeight, 0.0f, middleLeft - cornerRadiusWidth, middleTop - cornerRadiusHeight, 0.0f, middleLeft, middleTop, alpha, red, green, blue);
            this.renderGradient(consumer, pose, middleLeft - cornerRadiusWidth, middleTop - cornerRadiusHeight, 0.0f, middleLeft - highCornerRadiusWidth, middleTop - highCornerRadiusHeight, 0.0f, middleLeft, middleTop - radius, 0.0f, middleLeft, middleTop, alpha, red, green, blue);
        }
        if (!overlapping || isLeftSide != overlapping) {
            this.renderGradient(consumer, pose, middleRight + radius, middleTop, 0.0f, middleRight + lowCornerRadiusWidth, middleTop - lowCornerRadiusHeight, 0.0f, middleRight + cornerRadiusWidth, middleTop - cornerRadiusHeight, 0.0f, middleRight, middleTop, alpha, red, green, blue);
            this.renderGradient(consumer, pose, middleRight + cornerRadiusWidth, middleTop - cornerRadiusHeight, 0.0f, middleRight + highCornerRadiusWidth, middleTop - highCornerRadiusHeight, 0.0f, middleRight, middleTop - radius, 0.0f, middleRight, middleTop, alpha, red, green, blue);
        }
        if (!overlapping || isLeftSide == overlapping) {
            this.renderGradient(consumer, pose, middleLeft - radius, middleBottom, 0.0f, middleLeft - lowCornerRadiusWidth, middleBottom + lowCornerRadiusHeight, 0.0f, middleLeft - cornerRadiusWidth, middleBottom + cornerRadiusHeight, 0.0f, middleLeft, middleBottom, alpha, red, green, blue);
            this.renderGradient(consumer, pose, middleLeft - cornerRadiusWidth, middleBottom + cornerRadiusHeight, 0.0f, middleLeft - highCornerRadiusWidth, middleBottom + highCornerRadiusHeight, 0.0f, middleLeft, middleBottom + radius, 0.0f, middleLeft, middleBottom, alpha, red, green, blue);
        }
        if (!overlapping || isLeftSide != overlapping) {
            this.renderGradient(consumer, pose, middleRight + radius, middleBottom, 0.0f, middleRight + lowCornerRadiusWidth, middleBottom + lowCornerRadiusHeight, 0.0f, middleRight + cornerRadiusWidth, middleBottom + cornerRadiusHeight, 0.0f, middleRight, middleBottom, alpha, red, green, blue);
            this.renderGradient(consumer, pose, middleRight + cornerRadiusWidth, middleBottom + cornerRadiusHeight, 0.0f, middleRight + highCornerRadiusWidth, middleBottom + highCornerRadiusHeight, 0.0f, middleRight, middleBottom + radius, 0.0f, middleRight, middleBottom, alpha, red, green, blue);
        }
    }
    
    @Override
    public AbstractItem copy() {
        return new SnoxhItem(this.getListId(), this.itemDetails);
    }
    
    @Override
    public Model buildGeometry() {
        return new DefaultModel();
    }
    
    private void renderGradient(final BufferConsumer consumer, final FloatMatrix4 pose, final float x1, final float y1, final float alpha1, final float x2, final float y2, final float alpha2, final float x3, final float y3, final float alpha3, final float x4, final float y4, final float alpha4, final float red, final float green, final float blue) {
        this.vertices[0].setVertex(x1, y1, red, green, blue, alpha1);
        this.vertices[1].setVertex(x2, y2, red, green, blue, alpha2);
        this.vertices[2].setVertex(x3, y3, red, green, blue, alpha3);
        this.vertices[3].setVertex(x4, y4, red, green, blue, alpha4);
        if (this.ancientOpenGl) {
            this.renderWithoutIndexBuffer(consumer, pose);
        }
        else {
            this.renderWithIndexBuffer(consumer, pose);
        }
    }
    
    private void renderWithIndexBuffer(final BufferConsumer consumer, final FloatMatrix4 pose) {
        for (final Vertex vertex : this.vertices) {
            vertex.fillBuffer(this.modelCompiler, consumer, pose);
        }
    }
    
    private void renderWithoutIndexBuffer(final BufferConsumer consumer, final FloatMatrix4 pose) {
        for (final int index : SnoxhItem.INDEX_BUFFER) {
            this.vertices[index].fillBuffer(this.modelCompiler, consumer, pose);
        }
    }
    
    static {
        INDEX_BUFFER = new int[] { 0, 1, 2, 2, 3, 0 };
    }
    
    private static class Vertex
    {
        private static final FloatVector2 UV;
        private float x;
        private float y;
        private float red;
        private float green;
        private float blue;
        private float alpha;
        
        public void setVertex(final float x, final float y, final float red, final float green, final float blue, final float alpha) {
            this.x = x;
            this.y = y;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }
        
        public void fillBuffer(final ImmediateModelCompiler compiler, final BufferConsumer consumer, final FloatMatrix4 pose) {
            compiler.addVertex(consumer, pose, this.x, this.y, 0.0f, this.red, this.green, this.blue, this.alpha, Vertex.UV, 15728880, 1.0f, 1.0f, 1.0f);
        }
        
        static {
            UV = new FloatVector2();
        }
    }
}
