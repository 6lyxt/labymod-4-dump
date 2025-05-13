// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets;

import java.util.List;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.util.Color;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
@Deprecated
public class MultilineTextFieldWidget extends AbstractWidget<Widget>
{
    protected static final char PARAGRAPH = '§';
    private static final TextRenderer TEXT_RENDERER;
    protected String text;
    protected String[] lines;
    protected int cursorX;
    protected int cursorY;
    protected int maxWidth;
    private final LssProperty<Integer> textColor;
    
    public MultilineTextFieldWidget() {
        this.text = """
                    Sweet little bumblebee
                    I know what you want from me
                    Doo bi doo bi doo da da
                    Doo bi doo bi doo da da""";
        this.textColor = new LssProperty<Integer>(Color.WHITE.get());
        this.lazy = true;
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        super.renderWidget(context);
        final Stack stack = context.stack();
        final RenderPipeline renderPipeline = Laby.labyAPI().renderPipeline();
        final TextRenderer textRenderer = renderPipeline.textRenderer();
        final float lineHeight = textRenderer.height();
        final Bounds bounds = this.bounds();
        final float x;
        final float startX = x = bounds.getX();
        float y = bounds.getY();
        final int textColor = this.textColor.get();
        for (int i = 0; i < this.lines.length; ++i) {
            try {
                String line = this.lines[i];
                if (line.charAt(line.length() - 1) == '\n') {
                    line = line.substring(0, line.length() - 1);
                }
                textRenderer.text(line).pos(x, y).color(textColor).render(stack);
                if (i == this.cursorY) {
                    final String substring = line.substring(0, Math.max(0, Math.min(line.length(), this.cursorX)));
                    renderPipeline.rectangleRenderer().pos(x + textRenderer.width(substring) - 1.0f, y).size(1.0f, lineHeight).color(Color.RED.get()).render(stack);
                }
            }
            catch (final Exception e) {
                renderPipeline.rectangleRenderer().pos(x, y).size(bounds.getWidth(), lineHeight).color(Color.PINK.get()).render(stack);
                e.printStackTrace();
            }
            y += lineHeight;
        }
    }
    
    @Override
    public void updateBounds() {
        super.updateBounds();
        this.recalculateLines();
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (!this.isFocused()) {
            return false;
        }
        try {
            final int cursorIndex = this.getCursorIndex();
            if (key == Key.ENTER) {
                this.insert("\n");
                this.cursorX = 0;
                ++this.cursorY;
                return true;
            }
            if (key == Key.ARROW_LEFT && cursorIndex > 0) {
                if (this.cursorX > 0) {
                    --this.cursorX;
                }
                else if (this.cursorY > 0) {
                    --this.cursorY;
                    this.cursorX = this.lines[this.cursorY].length();
                }
                return true;
            }
            if (key == Key.ARROW_RIGHT && cursorIndex < this.text.length()) {
                final int maxLineIndex = this.lines[this.cursorY].length();
                if (this.cursorX < maxLineIndex) {
                    ++this.cursorX;
                }
                else if (this.cursorY < this.lines.length) {
                    ++this.cursorY;
                    this.cursorX = 0;
                }
                return true;
            }
            if (key == Key.ARROW_UP && this.cursorY > 0) {
                --this.cursorY;
                this.cursorX = Math.min(this.cursorX, this.lines[this.cursorY].length() - 1);
                return true;
            }
            if (key == Key.ARROW_DOWN && this.cursorY < this.lines.length) {
                ++this.cursorY;
                this.cursorX = Math.min(this.cursorX, this.lines[this.cursorY].length() - 1);
                return true;
            }
            if (key == Key.BACK) {
                this.delete(-1);
                return true;
            }
            if (key == Key.DELETE) {
                this.delete(1);
                return true;
            }
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        return super.keyPressed(key, type);
    }
    
    private int getCursorIndex() {
        int cursorIndex = 0;
        for (int i = 0; i < this.lines.length; ++i) {
            final String line = this.lines[i];
            if (i < this.cursorY) {
                cursorIndex += line.length();
            }
            else if (i == this.cursorY) {
                cursorIndex += this.cursorX;
                break;
            }
        }
        return cursorIndex;
    }
    
    @Override
    public boolean charTyped(final Key key, final char character) {
        if (!this.isFocused()) {
            return false;
        }
        if (character == '§') {
            return false;
        }
        try {
            this.insert(Character.toString(character));
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
    public void delete(final int offset) {
        final int cursorIndex = this.getCursorIndex();
        if (cursorIndex < 0 || cursorIndex > this.text.length()) {
            return;
        }
        final int beforeIndex = (offset < 0) ? (cursorIndex + offset) : cursorIndex;
        final int afterIndex = (offset < 0) ? cursorIndex : (cursorIndex + offset);
        final String textBuilder = this.text.substring(0, beforeIndex) + this.text.substring(afterIndex);
        this.text = textBuilder;
        if (offset < 0) {
            final int cursorX = this.cursorX + offset;
            if (cursorX < 0 && this.cursorY > 0) {
                --this.cursorY;
                this.cursorX = this.lines[this.cursorY].length() - 1;
            }
            else {
                this.cursorX = cursorX;
            }
            this.cursorX += offset;
        }
        this.recalculateLines();
    }
    
    public void insert(final String string) {
        this.insertAt(string, this.cursorX, this.cursorY);
    }
    
    public void insertAt(final String string, final int x, final int y) {
        final int length = this.text.length();
        if (length == 0) {
            this.text = string;
            return;
        }
        final StringBuilder textBuilder = new StringBuilder();
        final int cursorIndex = this.getCursorIndex();
        textBuilder.append(this.text, 0, cursorIndex);
        textBuilder.append(string);
        textBuilder.append(this.text, cursorIndex, length);
        this.text = textBuilder.toString();
        ++this.cursorX;
        this.recalculateLines();
    }
    
    protected void recalculateLines() {
        final Bounds bounds = this.bounds();
        this.maxWidth = (int)bounds.getWidth();
        final List<String> lines = new ArrayList<String>();
        final String[] artificialLines = this.text.split("\\n");
        StringBuilder line = new StringBuilder();
        for (int lastLineIndex = artificialLines.length - 1, l = 0; l <= lastLineIndex; ++l) {
            final String artificialLine = artificialLines[l];
            final String[] words = artificialLine.split(" ");
            for (int lastWordIndex = words.length - 1, w = 0; w <= lastWordIndex; ++w) {
                final String word = words[w];
                if (MultilineTextFieldWidget.TEXT_RENDERER.width(String.valueOf(line) + word) > this.maxWidth) {
                    lines.add(line.toString());
                    if (w != lastWordIndex) {
                        line = new StringBuilder();
                    }
                }
                line.append(word);
                if (w != lastWordIndex) {
                    line.append(" ");
                }
            }
            if (line.length() > 0) {
                String lineString = line.toString();
                if (l != lastLineIndex) {
                    lineString = lineString;
                    line = new StringBuilder();
                }
                lines.add(lineString);
            }
        }
        this.lines = lines.toArray(new String[0]);
    }
    
    public LssProperty<Integer> textColor() {
        return this.textColor;
    }
    
    static {
        TEXT_RENDERER = Laby.labyAPI().renderPipeline().textRenderer();
    }
}
