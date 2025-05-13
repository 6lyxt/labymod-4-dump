// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets;

import net.labymod.api.client.render.model.DefaultModelBuffer;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.model.ModelBuffer;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class ModelWidget extends SimpleWidget
{
    protected final AnimationController animationController;
    protected final float modelWidth;
    protected final float modelHeight;
    protected final FloatVector3 translation;
    protected final FloatVector3 rotation;
    protected final FloatVector3 scale;
    protected final FloatVector3 pivotPoint;
    protected final LssProperty<Integer> modelColor;
    protected Model model;
    protected ModelBuffer modelBuffer;
    private boolean immediate;
    
    public ModelWidget(final Model model, final AnimationController animationController, final float modelWidth, final float modelHeight) {
        this.modelColor = new LssProperty<Integer>(-1);
        this.animationController = animationController;
        this.modelWidth = modelWidth;
        this.modelHeight = modelHeight;
        this.translation = new FloatVector3();
        this.rotation = new FloatVector3();
        this.scale = new FloatVector3(1.0f);
        this.pivotPoint = new FloatVector3();
        this.setModel(model);
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        super.renderWidget(context);
        final FloatVector4 colorModulator = Laby.gfx().blaze3DGlStatePipeline().shaderUniformPipeline().colorModulator();
        final int modelColor = ColorUtil.lerpedColor(this.backgroundColorTransitionDuration().get(), context.getTickDelta(), this.modelColor);
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        colorModulator.set(colorFormat.normalizedRed(modelColor), colorFormat.normalizedGreen(modelColor), colorFormat.normalizedBlue(modelColor), colorFormat.normalizedAlpha(modelColor));
        final Stack stack = context.stack();
        stack.push();
        final Bounds bounds = this.bounds();
        stack.translate(bounds.getX(BoundsType.INNER) + bounds.getWidth(BoundsType.INNER) / 2.0f, bounds.getBottom(BoundsType.INNER), 0.0f);
        stack.scale(-(bounds.getWidth(BoundsType.INNER) / this.modelWidth), bounds.getHeight(BoundsType.INNER) / this.modelHeight, 1.0f);
        stack.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        this.renderModel(stack, modelColor, context.getTickDelta());
        stack.pop();
        colorModulator.set(1.0f, 1.0f, 1.0f, 1.0f);
        Laby.gfx().clearDepth();
    }
    
    public void renderModel(final Stack stack, final int color, final float tickDelta) {
        stack.push();
        final float modelScale = Laby.labyAPI().renderPipeline().renderConstants().modelScale();
        stack.scale(modelScale, modelScale, modelScale);
        this.animationController.applyAnimation(this.model, new String[0]);
        final float depth = (PlatformEnvironment.isNoShader() || this.labyAPI.minecraft().isIngame()) ? -1.0f : 0.0f;
        stack.rotateRadians(0.15f, 1.0f, 0.0f, 0.0f);
        stack.translate(this.pivotPoint.getX() / modelScale, this.pivotPoint.getY() / modelScale, this.pivotPoint.getZ() / modelScale + depth);
        stack.rotateRadians(this.rotation.getZ(), 0.0f, 0.0f, 1.0f);
        stack.rotateRadians(this.rotation.getY(), 0.0f, 1.0f, 0.0f);
        stack.rotateRadians(this.rotation.getX(), 1.0f, 0.0f, 0.0f);
        stack.scale(this.scale.getX(), this.scale.getY(), this.scale.getZ());
        stack.translate((-this.pivotPoint.getX() + this.translation.getX()) / modelScale, (-this.pivotPoint.getY() + this.translation.getY()) / modelScale, (-this.pivotPoint.getZ() + this.translation.getZ()) / modelScale);
        this.modelBuffer.setForceProjectionMatrix(true);
        this.modelBuffer.setARGB(color);
        this.modelBuffer.render(stack);
        this.renderModelAttachments(stack, color, tickDelta);
        stack.pop();
    }
    
    protected void renderModelAttachments(final Stack stack, final int color, final float tickDelta) {
    }
    
    public FloatVector3 translation() {
        return this.translation;
    }
    
    public void setTranslation(final FloatVector3 translation) {
        this.translation.set(translation);
    }
    
    public FloatVector3 rotation() {
        return this.rotation;
    }
    
    public void setRotation(final FloatVector3 rotation) {
        this.rotation.set(rotation);
    }
    
    public FloatVector3 scale() {
        return this.scale;
    }
    
    public void setScale(final FloatVector3 scale) {
        this.scale.set(scale);
    }
    
    public FloatVector3 pivotPoint() {
        return this.pivotPoint;
    }
    
    public void setPivotPoint(final FloatVector3 pivotPoint) {
        this.pivotPoint.set(pivotPoint);
    }
    
    @Override
    public void dispose() {
        super.dispose();
        if (this.modelBuffer != null) {
            this.modelBuffer.dispose();
        }
    }
    
    public void rebuildModel() {
        ThreadSafe.ensureRenderThread();
        this.modelBuffer.rebuildModel();
    }
    
    public Model model() {
        return this.model;
    }
    
    public void setModel(final Model model) {
        ThreadSafe.ensureRenderThread();
        if (this.modelBuffer != null) {
            this.modelBuffer.dispose();
        }
        this.model = model;
        (this.modelBuffer = new DefaultModelBuffer(model)).setImmediate(this.immediate);
    }
    
    public AnimationController animationController() {
        return this.animationController;
    }
    
    public boolean isImmediate() {
        return this.immediate;
    }
    
    public void setImmediate(final boolean immediate) {
        this.immediate = immediate;
        if (this.modelBuffer != null) {
            this.modelBuffer.setImmediate(immediate);
        }
    }
    
    public LssProperty<Integer> modelColor() {
        return this.modelColor;
    }
}
