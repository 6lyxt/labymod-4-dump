// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.component;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ScreenOpenEvent;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.client.component.format.Style;
import java.util.Collection;
import java.util.List;
import java.util.Iterator;
import net.labymod.api.client.component.flattener.FlattenerListener;
import net.labymod.api.client.render.font.TextOverflowStrategy;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.component.Component;
import net.labymod.api.Laby;
import net.labymod.api.client.render.font.text.TextRenderer;
import javax.inject.Inject;
import net.labymod.core.client.render.font.component.mapper.ScoreComponentMapper;
import net.labymod.api.client.component.ScoreComponent;
import net.labymod.core.client.render.font.component.mapper.KeybindComponentMapper;
import net.labymod.api.client.component.KeybindComponent;
import net.labymod.api.client.component.flattener.ComplexMapper;
import net.labymod.core.client.render.font.component.mapper.TranslatableComponentMapper;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.component.flattener.ComponentFlattener;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.client.component.serializer.plain.PlainTextComponentSerializer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.core.util.collection.TimestampedCache;
import net.labymod.api.client.render.font.ComponentFormatter;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffectRenderer;
import net.labymod.api.client.render.font.ComponentRendererBuilder;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.font.ComponentRenderer;

@Singleton
@Implements(ComponentRenderer.class)
public class DefaultComponentRenderer implements ComponentRenderer
{
    private static final float HOVER_MARGIN = 4.0f;
    private final RenderingFlattenerListener flattenerListener;
    private final ComponentRendererBuilder componentRendererBuilder;
    private final HoverBackgroundEffectRenderer hoverBackgroundEffectRenderer;
    private final ComponentFormatter formatter;
    private final TimestampedCache<CacheKey, RenderableComponent> componentCache;
    private PlainTextComponentSerializer plainSerializer;
    private LegacyComponentSerializer legacySerializer;
    private ComponentFlattener flattener;
    private ComponentFlattener colorStrippingFlattener;
    
    @Inject
    public DefaultComponentRenderer(final ComponentRendererBuilder componentRendererBuilder, final HoverBackgroundEffectRenderer hoverBackgroundEffectRenderer, final EventBus eventBus) {
        this.componentCache = new TimestampedCache<CacheKey, RenderableComponent>(256, 60000L, 5000L);
        this.flattener = ComponentFlattener.BASIC;
        this.colorStrippingFlattener = new ColorStrippingComponentFlattener(ComponentFlattener.BASIC);
        this.componentRendererBuilder = componentRendererBuilder;
        this.hoverBackgroundEffectRenderer = hoverBackgroundEffectRenderer;
        this.formatter = new DefaultComponentFormatter(this);
        this.setFlattener(ComponentFlattener.BASIC.toBuilder().withIdentifier("basic_complex").complexMapper(TranslatableComponent.class, new TranslatableComponentMapper()).complexMapper(KeybindComponent.class, new KeybindComponentMapper()).complexMapper(ScoreComponent.class, new ScoreComponentMapper()).build());
        eventBus.registerListener(this);
        this.flattenerListener = new RenderingFlattenerListener(this::textRenderer, 0.0f, false);
    }
    
    private TextRenderer textRenderer() {
        return Laby.labyAPI().renderPipeline().textRenderer();
    }
    
    @Override
    public ComponentRendererBuilder builder() {
        return this.componentRendererBuilder;
    }
    
    @Override
    public ComponentFlattener getFlattener() {
        return this.flattener;
    }
    
    @Override
    public ComponentFlattener getColorStrippingFlattener() {
        return this.colorStrippingFlattener;
    }
    
    @Override
    public void setFlattener(final ComponentFlattener flattener) {
        if (flattener == null) {
            throw new NullPointerException("flattener cannot be null");
        }
        this.flattener = flattener;
        this.colorStrippingFlattener = new ColorStrippingComponentFlattener(flattener);
        this.plainSerializer = PlainTextComponentSerializer.of(this.colorStrippingFlattener);
        this.legacySerializer = LegacyComponentSerializer.legacySection().withFlattener(flattener);
    }
    
    @Override
    public PlainTextComponentSerializer plainSerializer() {
        return this.plainSerializer;
    }
    
    @Override
    public LegacyComponentSerializer legacySectionSerializer() {
        return this.legacySerializer;
    }
    
    @Override
    public float width(final Component component) {
        final CacheKey cacheKey = this.getCacheKey(component, -1.0f, HorizontalAlignment.LEFT, 0.0f, TextOverflowStrategy.CLIP, false);
        final RenderableComponent cachedComponent = this.componentCache.getValue(cacheKey);
        if (cachedComponent != null) {
            return (float)(int)cachedComponent.getWidth();
        }
        final RenderingFlattenerListener listener = this.flattenerListener;
        listener.reset();
        this.getFlattener().flatten(component, this.flattenerListener);
        float width = 0.0f;
        for (final RenderableComponent flattened : listener.getComponents()) {
            width += flattened.getWidth();
        }
        return width;
    }
    
    @Override
    public float height() {
        return this.textRenderer().height();
    }
    
    @Override
    public RenderableComponent realignedMerge(final List<RenderableComponent> components) {
        this.resetYOffset(components);
        if (components.size() == 1) {
            return components.get(0);
        }
        for (int index = 1; index < components.size(); ++index) {
            final RenderableComponent previous = components.get(index - 1);
            final RenderableComponent current = components.get(index);
            final float difference = previous.getYOffset() + previous.getHeight() - current.getYOffset();
            this.recursiveAddY(current, difference);
        }
        return RenderableComponent.of("", null, Style.EMPTY, 0.0f, 0.0f, components, 0.0f);
    }
    
    @Override
    public ComponentFormatter formatter() {
        return this.formatter;
    }
    
    private void recursiveAddY(final RenderableComponent component, final float delta) {
        component.setYOffset(component.getYOffset() + delta);
        for (final RenderableComponent child : component.getChildren()) {
            this.recursiveAddY(child, delta);
        }
    }
    
    @Override
    public RenderableComponent formatComponent(final Component component, final float maxWidth, final HorizontalAlignment alignment, final float lineSpacing, final TextOverflowStrategy overflowStrategy, final boolean noCache, final int maxLines, final boolean removeLeadingSpaces, final boolean useChatOptions, final boolean maxLinesClipText) {
        CacheKey cacheKey = null;
        if (!noCache) {
            cacheKey = this.getCacheKey(component, maxWidth, alignment, lineSpacing, overflowStrategy, useChatOptions && Laby.labyAPI().minecraft().options().isChatColorsEnabled());
            final RenderableComponent cachedComponent = this.componentCache.getValue(cacheKey);
            if (cachedComponent != null) {
                return cachedComponent;
            }
        }
        final RenderableComponent result = RenderableComponent.merge(ComponentSplitter.render(component, maxWidth, maxLines, lineSpacing, overflowStrategy, alignment, useChatOptions, maxLinesClipText));
        if (!noCache) {
            this.componentCache.putTimestamped(cacheKey, result);
        }
        return result;
    }
    
    @Override
    public void renderHoverComponent(final Stack stack, final Mouse mouse, final RenderableComponent component, final int color, final boolean allowColors, final Rectangle windowBounds) {
        final Theme currentTheme = Laby.labyAPI().themeService().currentTheme();
        final HoverBackgroundEffect effect = currentTheme.getHoverBackgroundRenderer();
        final float scale = effect.getScale();
        final float padding = (effect != null) ? effect.getPadding() : 0.0f;
        final float width = (component.getWidth() + padding * 2.0f) * scale;
        final float height = (component.getHeight() + padding * 2.0f) * scale;
        final float screenWidth = windowBounds.getWidth();
        final float screenHeight = windowBounds.getHeight();
        final float cursorMargin = 3.0f;
        float x = mouse.getX() + padding;
        float y = mouse.getY() - height + padding;
        if (y < 0.0f) {
            y += height + cursorMargin;
            x += cursorMargin;
        }
        if (x + width > screenWidth) {
            x -= width;
        }
        x = MathHelper.clamp(x, 0.0f, screenWidth - width);
        y = MathHelper.clamp(y, 0.0f, screenHeight - height);
        stack.push();
        stack.translate(0.0f, 0.0f, 200.0f);
        this.hoverBackgroundEffectRenderer.hoverEffect(effect).pos(x, y, component.getWidth() * scale, component.getCeilHeight() * scale).component(component).render(stack);
        stack.pop();
    }
    
    @Override
    public void invalidate() {
        this.componentCache.clear();
    }
    
    @Subscribe
    public void invalidateCache(final ScreenOpenEvent event) {
        this.invalidate();
    }
    
    @Override
    public List<Component> split(final Component component, final float width) {
        final TextRenderer renderer = Laby.labyAPI().renderPipeline().textRenderer();
        return ComponentSplitter.splitText(component, width, renderer);
    }
    
    private void resetYOffset(final Collection<RenderableComponent> components) {
        for (final RenderableComponent component : components) {
            component.setYOffset(0.0f);
            this.resetYOffset(component.getChildren());
        }
    }
    
    private CacheKey getCacheKey(final Component component, final float maxWidth, final HorizontalAlignment alignment, final float lineSpacing, final TextOverflowStrategy overflowStrategy, final boolean chatOptions) {
        return new CacheKey(component, maxWidth, alignment, lineSpacing, overflowStrategy, chatOptions);
    }
    
    record CacheKey(Component component, float maxWidth, HorizontalAlignment alignment, float lineSpacing, TextOverflowStrategy overflowStrategy, boolean chatOptions) {}
}
