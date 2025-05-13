// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.renderer;

import net.labymod.api.property.Property;
import net.labymod.api.client.gui.VerticalAlignment;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.bounds.DefaultRectangle;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.client.gui.screen.widget.attributes.ObjectFitType;
import net.labymod.api.client.gui.screen.widget.attributes.ObjectPosition;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class IconWidget extends SimpleWidget
{
    private static final String UPDATE_LISTENER_KEY;
    private final LssProperty<Icon> icon;
    private final LssProperty<Integer> color;
    private final LssProperty<Long> colorTransitionDuration;
    private final LssProperty<Boolean> clickable;
    private final LssProperty<Boolean> cleanupOnDispose;
    private final ObjectPosition objectPosition;
    private final LssProperty<ObjectFitType> objectFit;
    private final LssProperty<Float> zoom;
    @Deprecated
    private final LssProperty<Boolean> blurry;
    private final MutableRectangle iconBounds;
    
    public IconWidget(final Icon icon) {
        this.icon = new LssProperty<Icon>(null);
        this.color = new LssProperty<Integer>(-1);
        this.colorTransitionDuration = new LssProperty<Long>(0L);
        this.clickable = new LssProperty<Boolean>(false);
        this.cleanupOnDispose = new LssProperty<Boolean>(false);
        this.objectPosition = new ObjectPosition();
        this.objectFit = new LssProperty<ObjectFitType>(ObjectFitType.FILL);
        this.zoom = new LssProperty<Float>(0.0f);
        this.blurry = new LssProperty<Boolean>(false);
        this.iconBounds = new DefaultRectangle();
        this.icon.addChangeListener((type, oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.setUpdateListener(IconWidget.UPDATE_LISTENER_KEY, null);
            }
            if (newValue != null) {
                newValue.setUpdateListener(IconWidget.UPDATE_LISTENER_KEY, this::updateIconBounds);
            }
            this.updateIconBounds();
            return;
        });
        this.icon.updateDefaultValue(icon);
        this.icon.set(icon);
    }
    
    @Override
    public void dispose() {
        if (this.cleanupOnDispose != null && this.icon != null && this.cleanupOnDispose.get()) {
            final Icon icon = this.icon.get();
            if (icon != null) {
                final ResourceLocation location = icon.getResourceLocation();
                if (location != null) {
                    this.labyAPI.renderPipeline().resources().textureRepository().queueTextureRelease(location);
                }
            }
        }
        super.dispose();
    }
    
    @Override
    public void handleAttributes() {
        super.handleAttributes();
        this.updateIconBounds();
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        final Icon icon = this.icon.get();
        final float aspectRatio = (icon != null) ? icon.getAspectRatio() : 1.0f;
        return (aspectRatio != 0.0f) ? (this.bounds().getHeight(type) * aspectRatio) : 0.0f;
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        final Icon icon = this.icon.get();
        final float aspectRatio = (icon != null) ? icon.getAspectRatio() : 1.0f;
        return (aspectRatio != 0.0f) ? (this.bounds().getWidth(type) / aspectRatio) : 0.0f;
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        final Icon icon = this.icon.get();
        if (icon == null) {
            return;
        }
        icon.setBorderRadius(this.getBorderRadius());
        final float zoom = (this.zoom.get() != 0.0f) ? this.zoom.get() : ((this.clickable.get() && this.isHovered()) ? 1.0f : 0.0f);
        final Bounds bounds = this.bounds();
        float left = bounds.getLeft(BoundsType.INNER) + this.iconBounds.getLeft();
        float top = bounds.getTop(BoundsType.INNER) + this.iconBounds.getTop();
        final float right = bounds.getRight(BoundsType.INNER) - this.iconBounds.getRight();
        final float bottom = bounds.getBottom(BoundsType.INNER) - this.iconBounds.getBottom();
        final boolean useFloatingPointPosition = this.useFloatingPointPosition().get();
        final float width = MathHelper.applyFloatingPointPosition(useFloatingPointPosition, right - left);
        final float height = MathHelper.applyFloatingPointPosition(useFloatingPointPosition, bottom - top);
        final ObjectFitType objectFitType = this.objectFit.get();
        if (objectFitType != ObjectFitType.FILL) {
            final HorizontalAlignment alignment = this.objectPosition.getHorizontalAlignment();
            float offset = this.objectPosition.getHorizontalOffset();
            if (objectFitType == ObjectFitType.COVER || objectFitType == ObjectFitType.NONE) {
                switch (alignment) {
                    case RIGHT: {
                        left += offset;
                        break;
                    }
                    case CENTER: {
                        left -= bounds.getWidth(BoundsType.INNER) / 2.0f - width / 2.0f - offset;
                        break;
                    }
                    case LEFT: {
                        left -= bounds.getWidth(BoundsType.INNER) - width + offset;
                        break;
                    }
                }
            }
            else {
                switch (alignment) {
                    case LEFT: {
                        left += offset;
                        break;
                    }
                    case CENTER: {
                        left += bounds.getWidth(BoundsType.INNER) / 2.0f - width / 2.0f + offset;
                        break;
                    }
                    case RIGHT: {
                        left += bounds.getWidth(BoundsType.INNER) - width - offset;
                        break;
                    }
                }
            }
            final VerticalAlignment alignment2 = this.objectPosition.getVerticalAlignment();
            offset = this.objectPosition.getVerticalOffset();
            if (objectFitType == ObjectFitType.COVER || objectFitType == ObjectFitType.NONE) {
                switch (alignment2) {
                    case BOTTOM: {
                        top += offset;
                        break;
                    }
                    case CENTER: {
                        top -= bounds.getHeight(BoundsType.INNER) / 2.0f - height / 2.0f - offset;
                        break;
                    }
                    case TOP: {
                        top -= bounds.getHeight(BoundsType.INNER) - height + offset;
                        break;
                    }
                }
            }
            else {
                switch (alignment2) {
                    case TOP: {
                        top += offset;
                        break;
                    }
                    case CENTER: {
                        top += bounds.getHeight(BoundsType.INNER) / 2.0f - height / 2.0f + offset;
                        break;
                    }
                    case BOTTOM: {
                        top += bounds.getHeight(BoundsType.INNER) - height - offset;
                        break;
                    }
                }
            }
        }
        if (this.blurry.getOrDefault(false)) {
            icon.makeBlurry();
        }
        final boolean floating = this.useFloatingPointPosition().get();
        float bX = left - zoom;
        float bY = top - zoom;
        float bWidth = width + zoom * 2.0f;
        float bHeight = height + zoom * 2.0f;
        Rectangle stencil;
        if (this.objectFit.get() == ObjectFitType.SCALE_DOWN) {
            stencil = Rectangle.absolute(bX, bY, bX + bWidth, bY + bHeight);
        }
        else {
            stencil = bounds.rectangle(BoundsType.INNER);
        }
        if (!floating) {
            bX = (float)(int)bX;
            bY = (float)(int)bY;
            bWidth = (float)(int)bWidth;
            bHeight = (float)(int)bHeight;
            stencil = Rectangle.absolute((float)(int)stencil.getLeft(), (float)(int)stencil.getTop(), (float)(int)stencil.getRight(), (float)(int)stencil.getBottom());
        }
        icon.render(context.stack(), bX, bY, bWidth, bHeight, this.isHovered(), ColorUtil.lerpedColor(this.colorTransitionDuration.get(), context.getTickDelta(), this.color), stencil);
        super.renderWidget(context);
    }
    
    public LssProperty<Icon> icon() {
        return this.icon;
    }
    
    public LssProperty<Integer> color() {
        return this.color;
    }
    
    public LssProperty<Long> colorTransitionDuration() {
        return this.colorTransitionDuration;
    }
    
    public LssProperty<Boolean> clickable() {
        return this.clickable;
    }
    
    public LssProperty<ObjectFitType> objectFit() {
        return this.objectFit;
    }
    
    @Deprecated
    public LssProperty<Boolean> cleanupOnDispose() {
        return this.cleanupOnDispose;
    }
    
    public LssProperty<Float> zoom() {
        return this.zoom;
    }
    
    public LssProperty<Boolean> blurry() {
        return this.blurry;
    }
    
    public ObjectPosition objectPosition() {
        return this.objectPosition;
    }
    
    public boolean isCleanupOnDispose() {
        return this.cleanupOnDispose.get();
    }
    
    public IconWidget setCleanupOnDispose(final boolean value) {
        this.cleanupOnDispose.set(value);
        return this;
    }
    
    private void updateIconBounds() {
        final Icon icon = this.icon.get();
        if (icon == null) {
            return;
        }
        this.handleSizeAttributes();
        final Bounds bounds = this.bounds();
        final float width = bounds.getWidth(BoundsType.INNER);
        final float height = bounds.getHeight(BoundsType.INNER);
        final float aspectRatio = icon.getAspectRatio();
        icon.setBorderRadius(this.getBorderRadius());
        this.iconBounds.setBounds(0.0f, 0.0f, 0.0f, 0.0f);
        final ObjectFitType objectFitType = this.objectFit.get();
        if (aspectRatio > 0.0f) {
            switch (objectFitType) {
                case SCALE_DOWN:
                case CONTAIN: {
                    final float targetHeight = width / aspectRatio;
                    if (height > targetHeight) {
                        final float verticalOffset = height - targetHeight;
                        this.iconBounds.setBottom(verticalOffset);
                        break;
                    }
                    final float targetWidth = height * aspectRatio;
                    final float horizontalOffset = width - targetWidth;
                    this.iconBounds.setRight(horizontalOffset);
                    break;
                }
                case NONE:
                case COVER: {
                    final float aspectHeight = bounds.getWidth(BoundsType.INNER) / aspectRatio;
                    final float verticalOverflow = aspectHeight - bounds.getHeight(BoundsType.INNER);
                    if (verticalOverflow > 0.0f) {
                        this.iconBounds.setTop(-verticalOverflow);
                    }
                    else {
                        final float aspectWidth = bounds.getHeight(BoundsType.INNER) * aspectRatio;
                        final float horizontalOverflow = aspectWidth - bounds.getWidth(BoundsType.INNER);
                        if (horizontalOverflow > 0.0f) {
                            this.iconBounds.setLeft(-horizontalOverflow);
                        }
                    }
                    this.setStencil(true);
                    break;
                }
            }
        }
    }
    
    public Rectangle iconBounds() {
        return this.iconBounds;
    }
    
    static {
        UPDATE_LISTENER_KEY = IconWidget.class.getName() + "#IconUpdateListener";
    }
}
