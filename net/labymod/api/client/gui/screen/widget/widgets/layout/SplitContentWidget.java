// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

@AutoWidget
public class SplitContentWidget<Left extends Widget, Right extends Widget> extends AbstractWidget<Widget>
{
    private static final ModifyReason ENTRY_POSITION;
    private final LssProperty<Float> splitGapWidth;
    private final LssProperty<Float> splitButtonWidth;
    private final LssProperty<Float> initialPercentage;
    private final LssProperty<Float> minPercentage;
    private final LssProperty<Float> maxPercentage;
    private boolean modified;
    private float percentage;
    private boolean dragging;
    private float percentageBeforeDrag;
    private Left left;
    private Right right;
    
    public SplitContentWidget() {
        this.splitGapWidth = new LssProperty<Float>(5.0f);
        this.splitButtonWidth = new LssProperty<Float>(5.0f);
        this.initialPercentage = new LssProperty<Float>(0.3f);
        this.minPercentage = new LssProperty<Float>(0.2f);
        this.maxPercentage = new LssProperty<Float>(0.8f);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.addChild(this.left);
        this.addChild(this.right);
    }
    
    @Override
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        if (!this.modified) {
            this.percentage = this.initialPercentage.get();
            this.percentage = this.clampPercentage(this.percentage);
        }
        this.updateSplit();
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        this.updateSplit();
    }
    
    @Override
    public void applyMediaRules(final boolean updateState) {
        super.applyMediaRules(updateState);
        this.percentage = this.clampPercentage(this.percentage);
    }
    
    private void updateSplit() {
        final Bounds parentBounds = this.bounds();
        final float leftWidth = parentBounds.getWidth() * this.percentage;
        if (this.left != null) {
            final ReasonableMutableRectangle leftBounds = this.left.bounds().rectangle(BoundsType.OUTER);
            leftBounds.setBounds(parentBounds.getLeft(), parentBounds.getTop(), parentBounds.getLeft() + leftWidth - this.splitGapWidth.get(), parentBounds.getBottom(), SplitContentWidget.ENTRY_POSITION);
            this.left.updateBounds();
        }
        if (this.right != null) {
            final ReasonableMutableRectangle rightBounds = this.right.bounds().rectangle(BoundsType.OUTER);
            rightBounds.setBounds(parentBounds.getLeft() + leftWidth + this.splitGapWidth.get(), parentBounds.getTop(), parentBounds.getRight(), parentBounds.getBottom(), SplitContentWidget.ENTRY_POSITION);
            this.right.updateBounds();
        }
    }
    
    @Override
    public void renderOverlay(final ScreenContext context) {
        super.renderOverlay(context);
        final MutableMouse mouse = context.mouse();
        final Stack stack = context.stack();
        final Bounds bounds = this.bounds();
        final float leftWidth = bounds.getWidth() * this.percentage;
        if ((!this.labyAPI.minecraft().isMouseDown(MouseButton.LEFT) || this.dragging) && mouse.getX() >= bounds.getLeft() + leftWidth - this.splitButtonWidth.get() && mouse.getX() <= bounds.getLeft() + leftWidth + this.splitButtonWidth.get()) {
            if (this.dragging) {
                this.labyAPI.renderPipeline().rectangleRenderer().renderRectangle(stack, bounds.getLeft() + leftWidth - 0.5f, bounds.getTop(), bounds.getLeft() + leftWidth + 0.5f, bounds.getBottom(), -2130706433);
            }
            this.labyAPI.renderPipeline().textRenderer().text("||").pos(bounds.getLeft() + leftWidth - 1.5f, mouse.getY() - 3.0f).render(stack);
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (mouseButton == MouseButton.LEFT) {
            final Bounds bounds = this.bounds();
            final float leftWidth = bounds.getWidth() * this.percentage;
            if (mouse.getX() >= bounds.getLeft() + leftWidth - this.splitButtonWidth.get() && mouse.getX() <= bounds.getLeft() + leftWidth + this.splitButtonWidth.get()) {
                this.dragging = true;
                this.percentageBeforeDrag = this.percentage;
            }
        }
        return super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        if (this.dragging) {
            final Bounds bounds = this.bounds();
            float percentage = 1.0f / bounds.getWidth() * (mouse.getX() - bounds.getLeft());
            percentage = this.clampPercentage(percentage);
            this.percentage = percentage;
            this.modified = true;
            this.updateSplit();
        }
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        if (mouseButton == MouseButton.LEFT) {
            this.dragging = false;
        }
        return super.mouseReleased(mouse, mouseButton);
    }
    
    public void setLeft(final Left left) {
        this.left = left;
    }
    
    public void setRight(final Right right) {
        this.right = right;
    }
    
    public void setPercentage(final float percentage) {
        this.percentage = percentage;
        this.updateSplit();
    }
    
    public float getPercentage() {
        return this.percentage;
    }
    
    public LssProperty<Float> splitButtonWidth() {
        return this.splitButtonWidth;
    }
    
    public LssProperty<Float> splitGapWidth() {
        return this.splitGapWidth;
    }
    
    public LssProperty<Float> initialPercentage() {
        return this.initialPercentage;
    }
    
    public LssProperty<Float> minPercentage() {
        return this.minPercentage;
    }
    
    public LssProperty<Float> maxPercentage() {
        return this.maxPercentage;
    }
    
    private float clampPercentage(final float value) {
        return MathHelper.clamp(value, this.minPercentage.get(), this.maxPercentage.get());
    }
    
    static {
        ENTRY_POSITION = ModifyReason.of("entryPosition");
    }
}
