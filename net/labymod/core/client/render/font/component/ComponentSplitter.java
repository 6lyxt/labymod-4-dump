// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.component;

import java.util.ListIterator;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.render.font.text.glyph.GlyphConsumer;
import net.labymod.api.client.render.font.text.StringIterator;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.component.format.TextDecoration;
import java.util.Iterator;
import net.labymod.api.client.component.flattener.ComponentFlattener;
import java.util.Collection;
import net.labymod.api.client.component.flattener.FlattenerListener;
import net.labymod.api.client.render.font.text.TextRenderer;
import java.util.ArrayList;
import java.util.Collections;
import net.labymod.api.Laby;
import net.labymod.api.client.render.font.RenderableComponent;
import java.util.List;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.render.font.TextOverflowStrategy;
import net.labymod.api.client.component.Component;

public class ComponentSplitter
{
    private static final String CLIP_TEXT = " ...";
    
    public static List<RenderableComponent> render(final Component component, final float maxWidth, int maxLines, final float lineSpacing, final TextOverflowStrategy overflowStrategy, final HorizontalAlignment alignment, final boolean useChatOptions, final boolean maxLinesClipText) {
        final TextRenderer renderer = Laby.labyAPI().renderPipeline().textRenderer();
        List<Component> lines = splitText(component, maxWidth, renderer);
        int length = lines.size();
        if (length == 0) {
            return Collections.emptyList();
        }
        if (maxLines > 0) {
            if (length < maxLines) {
                maxLines = length;
            }
            if (maxLinesClipText && length > maxLines) {
                final Component line = lines.get(maxLines - 1);
                lines.set(maxLines - 1, line.append(Component.text("...")));
            }
            final List<Component> newLines = new ArrayList<Component>(maxLines);
            for (int index = 0; index < maxLines; ++index) {
                newLines.add(lines.get(index));
            }
            lines = newLines;
            length = lines.size();
        }
        boolean clipped = false;
        if (overflowStrategy == TextOverflowStrategy.CLIP && maxWidth != -1.0f && length > 1) {
            final Component firstLine = lines.getFirst();
            lines = new ArrayList<Component>(1);
            lines.add(firstLine);
            clipped = true;
        }
        return renderLines(lines, clipped, renderer, lineSpacing, alignment, maxWidth, useChatOptions);
    }
    
    private static List<RenderableComponent> renderLines(final List<Component> lines, final boolean clipped, final TextRenderer renderer, final float lineSpacing, final HorizontalAlignment alignment, final float maxWidth, final boolean useChatOptions) {
        final List<RenderableComponent> result = new ArrayList<RenderableComponent>(lines.size());
        final ComponentFlattener flattener = Laby.labyAPI().renderPipeline().componentRenderer().getFlattener();
        float y = 0.0f;
        float fullWidth = 0.0f;
        final RenderingFlattenerListener listener = new RenderingFlattenerListener(renderer, lineSpacing, useChatOptions);
        for (final Component line : lines) {
            if (line == null) {
                continue;
            }
            flattener.flatten(line, listener);
            final List<RenderableComponent> components = new ArrayList<RenderableComponent>(listener.getComponents());
            listener.reset();
            if (clipped) {
                clip(components, maxWidth, renderer);
            }
            final RenderableComponent merged = RenderableComponent.merge(components);
            merged.setLineYOffset(y, true);
            merged.setClipped(clipped);
            result.add(merged);
            fullWidth = Math.max(fullWidth, merged.getWidth());
            y += renderer.height() + lineSpacing;
        }
        for (final RenderableComponent line2 : result) {
            if (alignment == HorizontalAlignment.CENTER) {
                line2.addXOffsetWithChildren(fullWidth / 2.0f - line2.getWidth() / 2.0f);
            }
            else {
                if (alignment != HorizontalAlignment.RIGHT) {
                    continue;
                }
                line2.addXOffsetWithChildren(fullWidth - line2.getWidth());
            }
        }
        return result;
    }
    
    private static void clip(final List<RenderableComponent> components, final float maxWidth, final TextRenderer renderer) {
        while (true) {
            float space = maxWidth;
            for (final RenderableComponent c : components) {
                space -= c.getWidth();
            }
            final float required = renderer.width(" ...");
            final float missing = required - space;
            if (missing <= 0.0f || components.isEmpty()) {
                break;
            }
            final RenderableComponent current = components.getLast();
            if (current.getIcon() != null) {
                components.removeLast();
            }
            else {
                final float trimWidth = current.getSingleWidth() - missing;
                final String trimmed = (trimWidth <= 0.0f) ? "" : renderer.trimStringToWidth(current.getText(), current.style().hasDecoration(TextDecoration.BOLD), trimWidth);
                if (trimmed.isEmpty()) {
                    components.removeLast();
                }
                else {
                    if (trimmed.equals(current.getText())) {
                        break;
                    }
                    components.set(components.size() - 1, current.copy(trimmed));
                }
            }
        }
        int i = components.size() - 1;
        RenderableComponent last = null;
        while (i >= 0) {
            final RenderableComponent c = components.get(i);
            if (c.getText() != null) {
                last = c;
                break;
            }
            --i;
        }
        if (last != null) {
            components.set(i, last.copy(last.getText() + " ..."));
        }
    }
    
    public static List<Component> splitText(final Component inputComponent, final float maxWidth, final TextRenderer renderer) {
        final CollectingFlattenerListener collector = new CollectingFlattenerListener();
        final ComponentRenderer componentRenderer = Laby.labyAPI().renderPipeline().componentRenderer();
        componentRenderer.getFlattener().flatten(inputComponent, collector);
        final ComponentQueue queue = new ComponentQueue(collector.getComponents());
        if (isMaxWidthGreaterThanZero(maxWidth)) {
            splitComponentsByWidth(renderer, maxWidth, queue);
        }
        else {
            splitComponents(queue);
        }
        return queue.results();
    }
    
    private static void splitComponents(final ComponentQueue queue) {
        TextComponent currentLine = Component.empty();
        for (final Component component : queue.entries()) {
            if (component instanceof IconComponent) {
                currentLine = currentLine.append(component);
            }
            else {
                final String content = ((TextComponent)component).getText();
                if (content.isEmpty()) {
                    continue;
                }
                if (content.contains("\n")) {
                    final String[] entries = content.split("\n");
                    if (entries.length == 0) {
                        queue.addResult(currentLine);
                        currentLine = Component.empty();
                        queue.store();
                    }
                    else {
                        for (final String entry : entries) {
                            currentLine = currentLine.append(Component.text(entry, component.style()));
                            queue.addResult(currentLine);
                            currentLine = Component.empty();
                        }
                    }
                }
                else {
                    final Component lastResult = queue.takeComponent();
                    if (lastResult != null) {
                        currentLine = currentLine.append(lastResult);
                    }
                    currentLine = currentLine.append(component);
                }
            }
        }
        queue.addResult(currentLine);
    }
    
    private static void splitComponentsByWidth(final TextRenderer renderer, final float maxWidth, final ComponentQueue queue) {
        final FlatComponents flatComponents = new FlatComponents(queue);
        for (boolean running = true; running; running = true) {
            running = false;
            final LineBreakGlyphConsumer lineBreakGlyphConsumer = new LineBreakGlyphConsumer(renderer, maxWidth);
            for (final Component component : flatComponents.parts) {
                if (!(component instanceof TextComponent)) {
                    continue;
                }
                final String content = ((TextComponent)component).getText();
                if (content.isEmpty()) {
                    continue;
                }
                final boolean split = StringIterator.iterateFormatted(content, component.style(), false, lineBreakGlyphConsumer);
                if (!split) {
                    final int splitPosition = lineBreakGlyphConsumer.getSplitPosition();
                    final Style splitStyle = lineBreakGlyphConsumer.getSplitStyle();
                    final char c = flatComponents.charAt(splitPosition);
                    final boolean linebreak = c == '\n';
                    final boolean needsSpace = linebreak || c == ' ';
                    queue.addResult(flatComponents.splitAt(splitPosition, (int)(needsSpace ? 1 : 0), splitStyle));
                    break;
                }
                lineBreakGlyphConsumer.addToOffset(content.length());
            }
        }
        queue.addResult(flatComponents.getRemainder());
    }
    
    private static boolean isMaxWidthGreaterThanZero(final float maxWidth) {
        return maxWidth > 0.0f;
    }
    
    static class ComponentQueue
    {
        private final List<Component> entries;
        private final List<Component> result;
        private final List<Component> storage;
        
        public ComponentQueue(final List<Component> queue) {
            this.entries = queue;
            this.result = new ArrayList<Component>();
            this.storage = new ArrayList<Component>();
        }
        
        public void addResult(final Component component) {
            this.result.add(component);
        }
        
        @Nullable
        public Component takeComponent() {
            if (this.result.isEmpty()) {
                return null;
            }
            return this.result.removeLast();
        }
        
        public List<Component> entries() {
            return this.entries;
        }
        
        public List<Component> results() {
            if (!this.result.isEmpty()) {
                this.store();
            }
            return this.storage;
        }
        
        public void store() {
            this.storage.addAll(this.result);
            this.result.clear();
        }
    }
    
    static class FlatComponents
    {
        final List<Component> parts;
        String flatPart;
        
        public FlatComponents(final ComponentQueue queue) {
            this.parts = queue.entries();
            final StringBuilder flatPartBuilder = new StringBuilder();
            for (final Component part : this.parts) {
                if (part instanceof TextComponent) {
                    final String content = ((TextComponent)part).getText();
                    flatPartBuilder.append(content);
                }
            }
            this.flatPart = flatPartBuilder.toString();
        }
        
        public char charAt(final int position) {
            return this.flatPart.charAt(position);
        }
        
        public Component splitAt(final int position, final int space, final Style style) {
            TextComponent finalComponent = Component.empty();
            final ListIterator<Component> iterator = this.parts.listIterator();
            int finalPosition = position;
            boolean flag = false;
            while (iterator.hasNext()) {
                final Component part = iterator.next();
                if (!(part instanceof TextComponent)) {
                    continue;
                }
                final String content = ((TextComponent)part).getText();
                final int length = content.length();
                if (!flag) {
                    if (finalPosition > length) {
                        finalComponent = finalComponent.append(part);
                        iterator.remove();
                        finalPosition -= length;
                    }
                    else {
                        final String substring = content.substring(0, finalPosition);
                        if (!substring.isEmpty()) {
                            finalComponent = finalComponent.append(Component.text(substring, part.style()));
                        }
                        finalPosition += space;
                        flag = true;
                    }
                }
                if (!flag) {
                    continue;
                }
                if (finalPosition <= length) {
                    final String substring = content.substring(finalPosition);
                    if (substring.isEmpty()) {
                        iterator.remove();
                        break;
                    }
                    iterator.set(Component.text(substring, style));
                    break;
                }
                else {
                    iterator.remove();
                    finalPosition -= length;
                }
            }
            this.flatPart = this.flatPart.substring(position + space);
            return finalComponent;
        }
        
        public Component getRemainder() {
            Component remainder = Component.empty();
            for (final Component part : this.parts) {
                remainder = remainder.append(part);
            }
            this.parts.clear();
            return remainder;
        }
    }
    
    static class LineBreakGlyphConsumer implements GlyphConsumer
    {
        private final TextRenderer textRenderer;
        private final float maxWidth;
        private int lineBreak;
        private Style lineBreakStyle;
        private boolean hadNonZeroWidthChar;
        private float width;
        private int lastSpace;
        private Style lastSpaceStyle;
        private int nextChar;
        private int offset;
        
        public LineBreakGlyphConsumer(final TextRenderer textRenderer, final float maxWidth) {
            this.lineBreak = -1;
            this.lineBreakStyle = Style.empty();
            this.lastSpace = -1;
            this.lastSpaceStyle = Style.empty();
            this.textRenderer = textRenderer;
            this.maxWidth = Math.max(maxWidth, 1.0f);
        }
        
        @Override
        public boolean acceptGlyph(final int position, final Style style, final int codePoint) {
            final int finalPosition = position + this.offset;
            switch (codePoint) {
                case 10: {
                    return this.finishIteration(finalPosition, style);
                }
                case 32: {
                    this.lastSpace = finalPosition;
                    this.lastSpaceStyle = style;
                    break;
                }
            }
            final float width = this.textRenderer.width((char)codePoint, style);
            this.width += width;
            if (this.hadNonZeroWidthChar && this.width > this.maxWidth) {
                return (this.lastSpace != -1) ? this.finishIteration(this.lastSpace, this.lastSpaceStyle) : this.finishIteration(finalPosition, style);
            }
            this.hadNonZeroWidthChar |= (width != 0.0f);
            this.nextChar = finalPosition + Character.charCount(codePoint);
            return true;
        }
        
        private boolean finishIteration(final int position, final Style style) {
            this.lineBreak = position;
            this.lineBreakStyle = style;
            return false;
        }
        
        private boolean lineBreakFound() {
            return this.lineBreak != -1;
        }
        
        public int getSplitPosition() {
            return this.lineBreakFound() ? this.lineBreak : this.nextChar;
        }
        
        public Style getSplitStyle() {
            return this.lineBreakStyle;
        }
        
        public void addToOffset(final int offset) {
            this.offset += offset;
        }
    }
}
