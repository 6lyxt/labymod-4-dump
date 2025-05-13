// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets;

import net.labymod.api.property.Property;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.font.ComponentRendererBuilder;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.Laby;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.lss.LssPropertyException;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.render.font.FontSize;
import net.labymod.api.client.render.font.TextOverflowStrategy;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.render.font.ComponentRenderMeta;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class ComponentWidget extends SimpleWidget
{
    private static final int VISUAL_SHIFT = 1;
    private Component component;
    private RenderableComponent renderableComponent;
    private ComponentRenderMeta renderMeta;
    private ComponentRenderMeta lastRenderMeta;
    private final LssProperty<TextOverflowStrategy> overflowStrategy;
    private final LssProperty<Boolean> renderHover;
    private final LssProperty<Integer> textColor;
    private final LssProperty<Integer> iconColor;
    private final LssProperty<Boolean> allowColors;
    private final LssProperty<Boolean> shadow;
    private final LssProperty<Float> lineSpacing;
    private final LssProperty<FontSize> fontSize;
    private final LssProperty<Boolean> scaleToFit;
    private final LssProperty<Boolean> cache;
    private final LssProperty<Integer> maxLines;
    private final LssProperty<Boolean> maxLinesClipText;
    private final LssProperty<Boolean> leadingSpaces;
    private final LssProperty<Boolean> useChatOptions;
    private final LssProperty<Boolean> clippingTextTooltip;
    private final LssProperty<Boolean> visualShift;
    private final LssProperty<Long> textColorTransitionDuration;
    
    private ComponentWidget(final Component component, final float lineSpacing) {
        this.overflowStrategy = new LssProperty<TextOverflowStrategy>(TextOverflowStrategy.WRAP);
        this.renderHover = new LssProperty<Boolean>(true);
        this.textColor = new LssProperty<Integer>(-1);
        this.iconColor = new LssProperty<Integer>(-1);
        this.allowColors = new LssProperty<Boolean>(true);
        this.shadow = new LssProperty<Boolean>(true);
        this.lineSpacing = new LssProperty<Float>(0.0f);
        this.fontSize = new LssProperty<FontSize>(FontSize.predefined(FontSize.PredefinedFontSize.MEDIUM));
        this.scaleToFit = new LssProperty<Boolean>(false);
        this.cache = new LssProperty<Boolean>(true);
        this.maxLines = new LssProperty<Integer>(0);
        this.maxLinesClipText = new LssProperty<Boolean>(true);
        this.leadingSpaces = new LssProperty<Boolean>(false);
        this.useChatOptions = new LssProperty<Boolean>(false);
        this.clippingTextTooltip = new LssProperty<Boolean>(true);
        this.visualShift = new LssProperty<Boolean>(true);
        this.textColorTransitionDuration = new LssProperty<Long>(0L);
        if (component == null) {
            throw new IllegalArgumentException("Component cannot be null");
        }
        this.component = component;
        this.lineSpacing.set(lineSpacing);
        this.setSize(SizeType.ACTUAL, WidgetSide.WIDTH, WidgetSize.fitContent());
        this.setSize(SizeType.ACTUAL, WidgetSide.HEIGHT, WidgetSize.fitContent());
        this.scaleX().addChangeListener((type, oldValue, newValue) -> {
            if (!Objects.equals(newValue, this.scaleX().defaultValue())) {
                throw new LssPropertyException("Cannot set scale-x (scale) property of a ComponentWidget, use font-size instead");
            }
            else {
                return;
            }
        });
        this.scaleY().addChangeListener((type, oldValue, newValue) -> {
            if (!Objects.equals(newValue, this.scaleY().defaultValue())) {
                throw new LssPropertyException("Cannot set scale-y (scale) property of a ComponentWidget, use font-size instead");
            }
            else {
                return;
            }
        });
        this.updateComponent(true);
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        if (!newRect.isRoundedSizeEqual(previousRect)) {
            this.updateComponent(true);
            this.handleScaleToFit();
        }
    }
    
    public void setText(final String text) {
        this.setComponent(Component.text(text));
    }
    
    public void setComponent(final Component component) {
        this.component = component;
        this.updateComponent(true);
        this.handleAttributes();
        this.updateBounds();
    }
    
    public void updateComponent() {
        this.updateComponent(true);
    }
    
    @Override
    public void handleAttributes() {
        super.handleAttributes();
        this.updateComponent(false);
        this.handleScaleToFit();
    }
    
    private void updateComponent(final boolean force) {
        if (!force && this.renderableComponent != null && !this.fontSize().wasUpdatedThisFrame() && !this.lineSpacing().wasUpdatedThisFrame() && !this.overflowStrategy().wasUpdatedThisFrame() && !this.sizes().wasUpdatedThisFrame() && !this.maxLines().wasUpdatedThisFrame() && !this.leadingSpaces().wasUpdatedThisFrame() && !this.maxLinesClipText().wasUpdatedThisFrame() && !this.bounds().wasUpdatedThisFrame()) {
            return;
        }
        this.renderableComponent = this.createRenderableComponent();
        super.handleAttributes();
    }
    
    private RenderableComponent createRenderableComponent() {
        final boolean forceVanillaFont = super.forceVanillaFont().get();
        final RenderAttributesStack renderAttributesStack = Laby.references().renderEnvironmentContext().renderAttributesStack();
        final RenderAttributes attributes = renderAttributesStack.pushAndGet();
        attributes.setForceVanillaFont(forceVanillaFont);
        attributes.apply();
        final float fontSize = this.fontSize.get().getFontSize();
        final Float maxWidth = this.predictWidth();
        final RenderableComponent component = RenderableComponent.builder().maxWidth((maxWidth != null) ? (maxWidth / fontSize) : -1.0f).alignment(HorizontalAlignment.of(this.alignmentX().get())).lineSpacing(this.lineSpacing.get()).overflow(this.overflowStrategy.get()).cache(this.cache.get()).maxLines(this.maxLines.get()).maxLinesClipText(this.maxLinesClipText.get()).useChatOptions(this.useChatOptions.get()).format(this.component);
        renderAttributesStack.pop();
        return component;
    }
    
    private void handleScaleToFit() {
        if (this.scaleToFit.get() && this.renderableComponent != null && (!this.hasSize(SizeType.ACTUAL, WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT) || !this.hasSize(SizeType.ACTUAL, WidgetSide.HEIGHT, WidgetSize.Type.FIT_CONTENT))) {
            final float fontSize = this.fontSize.get().getFontSize();
            final float componentWidth = this.renderableComponent.getWidth() * fontSize;
            final float componentHeight = this.renderableComponent.getHeight() * fontSize;
            final Bounds parentBounds = super.getParent().bounds();
            if (!parentBounds.hasSize()) {
                return;
            }
            final float parentWidth = parentBounds.getWidth(BoundsType.INNER);
            final float parentHeight = parentBounds.getHeight(BoundsType.INNER);
            float newFontSizeWidth = 1.0f;
            float newFontSizeHeight = 1.0f;
            if (parentWidth != 0.0f && componentWidth > parentWidth) {
                newFontSizeWidth = parentWidth / componentWidth - 0.05f;
            }
            if (parentHeight != 0.0f && componentHeight > parentHeight) {
                newFontSizeHeight = parentHeight / componentHeight - 0.05f;
            }
            final float newValue = Math.min(newFontSizeWidth, newFontSizeHeight);
            final FontSize nearest = FontSize.nextLowerPredefined(newValue);
            this.fontSize.set((nearest != null) ? nearest : FontSize.custom(newValue));
            this.updateComponent(true);
        }
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        if (this.renderableComponent == null) {
            this.updateComponent(true);
            this.handleScaleToFit();
        }
        final ComponentRenderer renderer = this.labyAPI.renderPipeline().componentRenderer();
        final ComponentRendererBuilder builder = renderer.builder();
        final Bounds bounds = this.bounds();
        float x = bounds.getX(BoundsType.INNER);
        float y = bounds.getY(BoundsType.INNER);
        final float fontSize = this.fontSize.get().getFontSize();
        if (this.alignmentX().get() == WidgetAlignment.CENTER) {
            x += (bounds.getWidth(BoundsType.INNER) - this.renderableComponent.getWidth() * fontSize) / 2.0f;
        }
        if (this.alignmentX().get() == WidgetAlignment.RIGHT) {
            x += bounds.getWidth(BoundsType.INNER) - this.renderableComponent.getWidth() * fontSize;
        }
        if (this.visualShift().get()) {
            ++x;
            ++y;
        }
        final boolean forceVanillaFont = super.forceVanillaFont().get();
        final RenderAttributesStack renderAttributesStack = ComponentWidget.RENDER_ENVIRONMENT_CONTEXT.renderAttributesStack();
        final RenderAttributes attributes = renderAttributesStack.pushAndGet();
        attributes.setForceVanillaFont(forceVanillaFont);
        attributes.apply();
        this.renderMeta = builder.useFloatingPointPosition(this.useFloatingPointPosition().get()).shadow(this.shadow.get()).pos(x, y).scale(fontSize).mousePos(context.mouse()).text(this.renderableComponent).allowColors(this.allowColors.get()).fontWeight(this.getFontWeight()).textColor(ColorUtil.lerpedColor(this.textColorTransitionDuration.get(), context.getTickDelta(), this.textColor)).iconColor(this.iconColor.get()).render(context.stack());
        renderAttributesStack.pop();
        super.renderWidget(context);
    }
    
    @Override
    public void renderHoverComponent(final ScreenContext context) {
        super.renderHoverComponent(context);
        if (!this.renderHover.get() || this.renderMeta == null || !this.isHovered()) {
            this.lastRenderMeta = null;
            return;
        }
        this.renderMeta.renderHover(context.stack(), context.mouse());
        this.lastRenderMeta = this.renderMeta;
        this.renderMeta = null;
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.lastRenderMeta == null || mouseButton != MouseButton.LEFT) {
            return super.mouseClicked(mouse, mouseButton);
        }
        return this.lastRenderMeta.interact() || super.mouseClicked(mouse, mouseButton);
    }
    
    public Component component() {
        return this.component;
    }
    
    public RenderableComponent renderable() {
        return this.renderableComponent;
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        float width = this.renderableComponent.getWidth() * this.fontSize().get().getFontSize() + this.bounds().getHorizontalOffset(type);
        if (width > 0.0f) {
            ++width;
        }
        return width;
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        final float contentHeight = this.renderableComponent.getHeight() * this.fontSize.get().getFontSize();
        return contentHeight + this.bounds().getVerticalOffset(type);
    }
    
    @Deprecated(forRemoval = true, since = "4.1.4")
    @Override
    public LssProperty<Boolean> forceVanillaFont() {
        return super.forceVanillaFont();
    }
    
    public LssProperty<TextOverflowStrategy> overflowStrategy() {
        return this.overflowStrategy;
    }
    
    public LssProperty<Boolean> renderHover() {
        return this.renderHover;
    }
    
    public LssProperty<Integer> textColor() {
        return this.textColor;
    }
    
    public LssProperty<Integer> iconColor() {
        return this.iconColor;
    }
    
    public LssProperty<Boolean> allowColors() {
        return this.allowColors;
    }
    
    public LssProperty<Boolean> shadow() {
        return this.shadow;
    }
    
    public LssProperty<Float> lineSpacing() {
        return this.lineSpacing;
    }
    
    public LssProperty<FontSize> fontSize() {
        return this.fontSize;
    }
    
    public LssProperty<Boolean> scaleToFit() {
        return this.scaleToFit;
    }
    
    public LssProperty<Boolean> cache() {
        return this.cache;
    }
    
    public LssProperty<Integer> maxLines() {
        return this.maxLines;
    }
    
    public LssProperty<Boolean> leadingSpaces() {
        return this.leadingSpaces;
    }
    
    public LssProperty<Boolean> useChatOptions() {
        return this.useChatOptions;
    }
    
    public LssProperty<Boolean> clippingTextTooltip() {
        return this.clippingTextTooltip;
    }
    
    public LssProperty<Boolean> maxLinesClipText() {
        return this.maxLinesClipText;
    }
    
    public LssProperty<Boolean> visualShift() {
        return this.visualShift;
    }
    
    public LssProperty<Long> textColorTransitionDuration() {
        return this.textColorTransitionDuration;
    }
    
    public static ComponentWidget text(final String text) {
        return component(Component.text((text == null) ? "" : text), 0.0f);
    }
    
    public static ComponentWidget text(final String text, final TextColor textColor) {
        return component(Component.text((text == null) ? "" : text, textColor), 0.0f);
    }
    
    public static ComponentWidget text(final String text, final Style style) {
        return component(Component.text(text, style));
    }
    
    public static ComponentWidget empty() {
        return new ComponentWidget(Component.empty(), 0.0f);
    }
    
    public static ComponentWidget i18n(final String key) {
        return component(Component.translatable(key, new Component[0]), 0.0f);
    }
    
    public static ComponentWidget i18n(final String key, final Object... args) {
        final Component[] arguments = new Component[args.length];
        for (int i = 0; i < args.length; ++i) {
            arguments[i] = Component.text(String.valueOf(args[i]));
        }
        return component(Component.translatable(key, arguments), 0.0f);
    }
    
    public static ComponentWidget i18n(final String key, final TextColor textColor, final Object... args) {
        final Component[] arguments = new Component[args.length];
        for (int i = 0; i < args.length; ++i) {
            arguments[i] = Component.text(String.valueOf(args[i]));
        }
        return component(Component.translatable(key, textColor, arguments));
    }
    
    public static ComponentWidget i18n(final String key, final Style style, final Object... args) {
        final Component[] arguments = new Component[args.length];
        for (int i = 0; i < args.length; ++i) {
            arguments[i] = Component.text(String.valueOf(args[i]));
        }
        return component(Component.translatable(key, style, arguments));
    }
    
    public static ComponentWidget i18n(final String key, final TextColor textColor) {
        return component(Component.translatable(key, textColor), 0.0f);
    }
    
    public static ComponentWidget component(final Component component) {
        return component(component, 0.0f);
    }
    
    public static ComponentWidget component(final Component component, final float lineSpacing) {
        return new ComponentWidget(component, lineSpacing);
    }
}
