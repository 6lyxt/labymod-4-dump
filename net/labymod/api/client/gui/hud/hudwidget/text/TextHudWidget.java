// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundConfig;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import java.util.Iterator;
import net.labymod.api.Laby;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundHudWidget;

public class TextHudWidget<T extends TextHudWidgetConfig> extends BackgroundHudWidget<T>
{
    private final String label;
    protected List<TextLine> lines;
    
    public TextHudWidget(final String id) {
        this(id, "label", TextHudWidgetConfig.class);
    }
    
    public TextHudWidget(final String id, final Class<T> configClass) {
        this(id, "label", configClass);
    }
    
    public TextHudWidget(final String id, final String label) {
        this(id, label, TextHudWidgetConfig.class);
    }
    
    public TextHudWidget(final String id, final String label, final Class<T> configClass) {
        super(id, configClass);
        this.lines = new ArrayList<TextLine>();
        this.label = label;
    }
    
    @Override
    public void load(final T config) {
        this.lines.clear();
        super.load(config);
        this.flushAll();
    }
    
    @Override
    public void reloadConfig() {
        super.reloadConfig();
        this.flushAll();
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        this.flushAll();
        final boolean floatingPointPosition = Laby.references().themeService().currentTheme().metadata().get("hud_widget_floating_point_position", false);
        for (final TextLine line : this.lines) {
            line.setFloatingPointPosition(floatingPointPosition);
        }
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
        this.updateSize(isEditorContext, size);
        super.render(stack, mouse, partialTicks, isEditorContext, size);
        if (stack != null) {
            this.renderLines(stack, isEditorContext, size);
        }
    }
    
    private void renderLines(final Stack stack, final boolean isEditorContext, final HudSize size) {
        final float space = this.getTotalSpace();
        final float widthWithoutSpace = size.getActualWidth() - space * 2.0f;
        final float lineHeightPercentage = this.config.lineHeight().get() / 100.0f;
        stack.push();
        stack.translate(space, space, 0.0f);
        float y = 0.0f;
        for (final TextLine line : this.lines) {
            final RenderableComponent renderableComponent = line.getRenderableComponent();
            if (this.isLineVisible(line, isEditorContext)) {
                if (renderableComponent == null) {
                    continue;
                }
                final float offsetX = this.anchor.getGapX(widthWithoutSpace, renderableComponent.getWidth());
                final float lineHeight = renderableComponent.getHeight() * lineHeightPercentage;
                final float lineHeightDelta = lineHeight - renderableComponent.getHeight();
                line.renderLine(stack, offsetX, y + lineHeightDelta / 2.0f, space, size);
                y += renderableComponent.getHeight() * lineHeightPercentage;
            }
        }
        stack.pop();
    }
    
    private void updateSize(final boolean isEditorContext, final HudSize size) {
        int maxWidth = 0;
        int height = 0;
        final float lineHeightPercentage = this.config.lineHeight().get() / 100.0f;
        for (final TextLine line : this.lines) {
            final RenderableComponent component = line.getRenderableComponent();
            if (this.isLineVisible(line, isEditorContext)) {
                if (component == null) {
                    continue;
                }
                maxWidth = (int)Math.max((float)maxWidth, line.getWidth());
                height += (int)(line.getHeight() * lineHeightPercentage);
            }
        }
        final float space = this.getTotalSpace();
        size.set(maxWidth + space * 2.0f, height + space * 2.0f);
    }
    
    private float getTotalSpace() {
        final BackgroundConfig backgroundConfig = this.config.background();
        return backgroundConfig.getPadding() + backgroundConfig.getMargin();
    }
    
    private boolean isLineVisible(final TextLine line, final boolean isEditorContext) {
        return line.state() != TextLine.State.DISABLED && (line.state() != TextLine.State.HIDDEN || isEditorContext);
    }
    
    protected TextLine createLine(final Object value) {
        return this.createLine(this.label, value);
    }
    
    protected TextLine createLine(final String key, final Object value) {
        return this.createLine(Component.text(key), value);
    }
    
    protected <K extends TextLine> K createLine(final String key, final Object value, final TextLineSupplier supplier) {
        return this.createLine(Component.text(key), value, supplier);
    }
    
    protected TextLine createLine(final Component key, final Object value) {
        return this.createLine(key, value, TextLine::new);
    }
    
    protected <K extends TextLine> K createLine(Component key, final Object value, final TextLineSupplier supplier) {
        if (this.config == null) {
            throw new RuntimeException("You can't create a line before the config is loaded! Use the load method!");
        }
        final String customLabel = this.config.customLabel().get();
        if (!customLabel.isEmpty()) {
            final int lineIndex = this.lines.size();
            final String[] split = customLabel.split(";");
            if (lineIndex < split.length) {
                key = Component.text(split[lineIndex]);
            }
        }
        final TextLine textLine = supplier.get(this, key, value);
        this.lines.add(textLine);
        return (K)textLine;
    }
    
    @Deprecated
    protected void flushAll() {
        for (final TextLine line : this.lines) {
            line.flush();
        }
    }
    
    @Override
    public boolean isVisibleInGame() {
        int visibleLines = 0;
        for (final TextLine line : this.lines) {
            if (line.state() == TextLine.State.VISIBLE) {
                ++visibleLines;
            }
        }
        return this.lines.isEmpty() || visibleLines > 0;
    }
    
    @Deprecated
    protected boolean displayInvisibleLinesInEditor() {
        throw new RuntimeException("Method is deprecated");
    }
    
    protected interface TextLineSupplier
    {
        TextLine get(final TextHudWidget<?> p0, final Component p1, final Object p2);
    }
}
