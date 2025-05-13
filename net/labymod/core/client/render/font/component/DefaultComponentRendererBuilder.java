// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.component;

import net.labymod.api.client.component.IconComponent;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.client.render.GraphicsColor;
import net.labymod.api.util.color.format.ColorFormat;
import java.util.function.Function;
import net.labymod.api.client.component.format.Style;
import java.util.Iterator;
import net.labymod.api.client.render.font.text.TextBuffer;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.Laby;
import net.labymod.api.client.render.font.ComponentRenderMeta;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.render.font.FontIconRenderListener;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.font.ComponentRendererBuilder;

@Singleton
@Implements(ComponentRendererBuilder.class)
public class DefaultComponentRendererBuilder implements ComponentRendererBuilder
{
    private final LabyAPI labyAPI;
    private RenderableComponent text;
    private float x;
    private float y;
    private Mouse mouse;
    private int textColor;
    private boolean adjustTextColor;
    private int iconColor;
    private TextDrawMode drawMode;
    private FontIconRenderListener iconRenderer;
    private boolean allowColors;
    private boolean centered;
    private float fontWeight;
    private boolean shadow;
    private boolean useFloatingPointPosition;
    private boolean capitalize;
    private int backgroundColor;
    private boolean shouldBatch;
    private float scale;
    private final List<Runnable> postRenderQueue;
    
    @Inject
    public DefaultComponentRendererBuilder(final LabyAPI labyAPI) {
        this.drawMode = TextDrawMode.NORMAL;
        this.iconRenderer = FontIconRenderListener.NORMAL_2D;
        this.postRenderQueue = new ArrayList<Runnable>();
        this.labyAPI = labyAPI;
        this.dispose();
    }
    
    @Override
    public ComponentRendererBuilder text(final RenderableComponent component) {
        this.text = component;
        return this;
    }
    
    @Override
    public ComponentRendererBuilder pos(final float x, final float y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
    @Override
    public ComponentRendererBuilder mousePos(final Mouse mouse) {
        this.mouse = mouse;
        return this;
    }
    
    @Override
    public ComponentRendererBuilder drawMode(final TextDrawMode drawMode) {
        this.drawMode = drawMode;
        return this;
    }
    
    @Override
    public ComponentRendererBuilder iconRenderer(final FontIconRenderListener renderer) {
        this.iconRenderer = renderer;
        return this;
    }
    
    @Override
    public ComponentRendererBuilder textColor(final int textColor, final boolean adjustColor) {
        this.textColor = textColor;
        this.adjustTextColor = adjustColor;
        return this;
    }
    
    @Override
    public ComponentRendererBuilder iconColor(final int iconColor) {
        this.iconColor = iconColor;
        return this;
    }
    
    @Override
    public ComponentRendererBuilder allowColors(final boolean allowColors) {
        this.allowColors = allowColors;
        return this;
    }
    
    @Override
    public ComponentRendererBuilder shadow(final boolean shadow) {
        this.shadow = shadow;
        return this;
    }
    
    @Override
    public ComponentRendererBuilder discrete(final boolean discrete) {
        this.drawMode = (discrete ? TextDrawMode.SEE_THROUGH : TextDrawMode.NORMAL);
        return this;
    }
    
    @Override
    public ComponentRendererBuilder capitalize(final boolean capitalize) {
        this.capitalize = capitalize;
        return this;
    }
    
    @Override
    public ComponentRendererBuilder useFloatingPointPosition(final boolean floatingPoints) {
        this.useFloatingPointPosition = floatingPoints;
        return this;
    }
    
    @Override
    public ComponentRendererBuilder fontWeight(final float weight) {
        this.fontWeight = weight;
        return this;
    }
    
    @Override
    public ComponentRendererBuilder centered(final boolean centered) {
        this.centered = centered;
        return this;
    }
    
    @Override
    public ComponentRendererBuilder backgroundColor(final int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }
    
    @Override
    public ComponentRendererBuilder shouldBatch(final boolean batch) {
        this.shouldBatch = batch;
        return this;
    }
    
    @Override
    public ComponentRendererBuilder scale(final float scale) {
        this.scale = scale;
        return this;
    }
    
    @Override
    public ComponentRenderMeta render(final Stack stack) {
        if (this.text == null) {
            throw new IllegalStateException("Missing component (call text(RenderableComponent component))");
        }
        final ComponentRenderMeta meta = this.renderIntern(stack);
        this.dispose();
        return meta;
    }
    
    @Override
    public void dispose() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.mouse = (Laby.isInitialized() ? this.labyAPI.minecraft().absoluteMouse() : null);
        this.textColor = -1;
        this.adjustTextColor = true;
        this.iconColor = -1;
        this.scale = 1.0f;
        this.allowColors = true;
        this.shouldBatch = true;
        this.backgroundColor = 0;
        this.centered = false;
        this.fontWeight = 0.0f;
        this.shadow = true;
        this.useFloatingPointPosition = false;
        this.capitalize = false;
        this.drawMode = TextDrawMode.NORMAL;
        this.iconRenderer = FontIconRenderListener.NORMAL_2D;
    }
    
    private TextRenderer textRenderer() {
        return Laby.labyAPI().renderPipeline().textRenderer();
    }
    
    private ComponentRenderMeta renderIntern(final Stack stack) {
        final TextRenderer textRenderer = this.textRenderer();
        textRenderer.scale(1.0f);
        final float x = this.x - (this.centered ? (this.text.getWidth() * this.scale / 2.0f) : 0.0f);
        final float y = this.y;
        stack.push();
        stack.scale(this.scale);
        textRenderer.beginBatch(stack);
        final TextBuffer buffer = this.renderRaw(textRenderer, stack, this.text, x, y, c -> this.textColor, this.allowColors);
        final float maxX = x + this.text.getWidth() * this.scale;
        final float maxY = y + this.text.getHeight() * this.scale;
        textRenderer.endBatch(stack);
        if (!this.postRenderQueue.isEmpty()) {
            this.iconRenderer.preRender();
            for (final Runnable task : this.postRenderQueue) {
                task.run();
            }
            this.iconRenderer.postRender();
            this.postRenderQueue.clear();
        }
        stack.pop();
        return new ComponentRenderMeta(this.findHoveredComponent(this.text, x, y), x, maxX, y, maxY, (buffer == null) ? 0.0f : buffer.getWidth());
    }
    
    private RenderableComponent findHoveredComponent(final RenderableComponent text, final float x, final float y) {
        RenderableComponent component = null;
        for (final RenderableComponent child : text.getChildren()) {
            final RenderableComponent childComponent = this.findHoveredComponent(child, x, y);
            if (childComponent != null) {
                component = childComponent;
                break;
            }
        }
        final Style style = text.style();
        final float textX = x / this.scale + text.getXOffset();
        final float textY = y / this.scale + text.getYOffset() + text.getInnerY();
        if (!style.isEmpty() && component == null && this.mouse != null) {
            final boolean inside = this.mouse.isInside(textX * this.scale, textY * this.scale, text.getSingleWidth() * this.scale, text.getSingleHeight() * this.scale);
            if (inside) {
                component = text;
            }
        }
        return component;
    }
    
    @Nullable
    private TextBuffer renderRaw(final TextRenderer textRenderer, final Stack stack, final RenderableComponent text, final float x, final float y, final Function<RenderableComponent, Integer> baseColor, final boolean allowColors) {
        final float textX = x / this.scale + text.getXOffset();
        final float textY = y / this.scale + text.getYOffset() + text.getInnerY();
        final Style style = text.style();
        final TextColor color = style.getColor();
        final int baseColorRgb = baseColor.apply(text);
        int rgb = (allowColors && color != null) ? color.getValue() : baseColorRgb;
        if (rgb != baseColorRgb) {
            rgb = ColorFormat.ARGB32.pack(rgb, (int)(GraphicsColor.DEFAULT_COLOR.update(baseColorRgb).alpha() * 255.0f));
        }
        rgb = (this.adjustTextColor ? ColorUtil.adjustedColor(rgb) : rgb);
        this.renderIcon(stack, text, x, y, baseColor, allowColors);
        textRenderer.fontWeight(this.fontWeight).capitalize(this.capitalize).text(text).pos(MathHelper.applyFloatingPointPosition(this.useFloatingPointPosition, textX), MathHelper.applyFloatingPointPosition(this.useFloatingPointPosition, textY)).color(rgb, this.adjustTextColor).shadow(this.shadow).drawMode(this.drawMode).useFloatingPointPosition(this.useFloatingPointPosition).style(style).backgroundColor(this.backgroundColor);
        return textRenderer.scale(1.0f).render(stack);
    }
    
    private void renderIcon(final Stack stack, final RenderableComponent text, final float x, final float y, final Function<RenderableComponent, Integer> baseColor, final boolean allowColors) {
        final IconComponent icon = text.getIcon();
        for (final RenderableComponent child : text.getChildren()) {
            this.renderIcon(stack, child, x, y, baseColor, allowColors);
        }
        if (icon == null) {
            return;
        }
        final float textX = x / this.scale + text.getXOffset();
        final float textY = y / this.scale + text.getYOffset() + text.getInnerY();
        final Style style = text.style();
        final TextColor color = style.getColor();
        final int baseColorRgb = baseColor.apply(text);
        int rgb = (allowColors && color != null) ? color.getValue() : baseColorRgb;
        if (rgb != baseColorRgb) {
            rgb = ColorFormat.ARGB32.pack(rgb, (int)(GraphicsColor.DEFAULT_COLOR.update(baseColorRgb).alpha() * 255.0f));
        }
        final int finalRgb;
        rgb = (finalRgb = (this.adjustTextColor ? ColorUtil.adjustedColor(rgb) : rgb));
        this.postRenderQueue.add(() -> this.iconRenderer.render(stack, icon.getIcon(), MathHelper.applyFloatingPointPosition(this.useFloatingPointPosition, textX), MathHelper.applyFloatingPointPosition(this.useFloatingPointPosition, textY), text.getWidth(), text.getHeight(), finalRgb));
    }
}
