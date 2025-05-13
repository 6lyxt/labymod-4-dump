// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.markdown;

import java.util.Iterator;
import net.labymod.api.util.markdown.MarkdownEmptyLineComponent;
import net.labymod.api.util.markdown.MarkdownRawComponent;
import net.labymod.api.util.markdown.MarkdownDocument;
import java.util.ArrayList;
import net.labymod.api.util.markdown.MarkdownComponent;
import java.util.List;
import net.labymod.api.models.Implements;
import net.labymod.api.util.markdown.MarkdownParser;

@Implements(MarkdownParser.class)
public class DefaultMarkdownParser implements MarkdownParser
{
    private final List<MarkdownComponent.Parser> parsers;
    
    public DefaultMarkdownParser() {
        this.parsers = new ArrayList<MarkdownComponent.Parser>();
        this.registerParser(new AlternateHeaderParser());
        this.registerParser(new HeaderParser());
    }
    
    public void registerParser(final MarkdownComponent.Parser parser) {
        this.parsers.add(parser);
    }
    
    @Override
    public MarkdownDocument parse(final String rawMarkdown) {
        final List<MarkdownComponent> components = new ArrayList<MarkdownComponent>();
        final String[] split = rawMarkdown.split("\n");
        final MarkdownLine[] lines = new MarkdownLine[split.length];
        for (int i = 0; i < split.length; ++i) {
            lines[i] = new MarkdownLine(split[i]);
        }
        for (int lastLineIndex = lines.length - 1, index = 0; index <= lastLineIndex; ++index) {
            final MarkdownLine line = lines[index];
            final int lineIndex = index;
            for (final MarkdownComponent.Parser parser : this.parsers) {
                final int parse = parser.parse(line, index, lastLineIndex, lines, components);
                if (parse != 0) {
                    index += parse;
                    break;
                }
            }
            if (lineIndex == index) {
                this.parseRawComponent(line.getTrimmedText(), line.getTrimmedChars(), components);
            }
        }
        final List<MarkdownComponent> componentsWithParagraph = new ArrayList<MarkdownComponent>();
        for (int lastComponentIndex = components.size() - 1, j = 0; j <= lastComponentIndex; ++j) {
            final MarkdownComponent markdownComponent = components.get(j);
            componentsWithParagraph.add(markdownComponent);
            if (j != lastComponentIndex && markdownComponent instanceof MarkdownRawComponent) {
                final MarkdownRawComponent current = (MarkdownRawComponent)markdownComponent;
                final MarkdownComponent nextComponent = components.get(j + 1);
                if (nextComponent instanceof final MarkdownRawComponent next) {
                    if (!current.fromSameLine(next)) {
                        componentsWithParagraph.add(MarkdownEmptyLineComponent.INSTANCE);
                    }
                }
            }
        }
        return new MarkdownDocument(componentsWithParagraph);
    }
    
    private void parseRawComponent(final String trimmedText, final char[] trimmedChars, final List<MarkdownComponent> components) {
        if (trimmedText.length() == 0) {
            return;
        }
        final int nextAsterisk = trimmedText.indexOf(42);
        if (nextAsterisk == -1) {
            components.add(new MarkdownRawComponent(trimmedText));
            return;
        }
        if (nextAsterisk > 0) {
            components.add(new MarkdownRawComponent(trimmedText, trimmedText.substring(0, nextAsterisk), new MarkdownRawComponent.Formatting[0]));
        }
        StringBuilder builder = new StringBuilder();
        for (int lastIndex = trimmedText.length() - 1, i = nextAsterisk; i <= lastIndex; ++i) {
            final char c = trimmedChars[i];
            if (c != '*') {
                builder.append(c);
            }
            else {
                if (!builder.isEmpty()) {
                    components.add(new MarkdownRawComponent(trimmedText, builder.toString(), new MarkdownRawComponent.Formatting[0]));
                }
                builder = new StringBuilder();
                final boolean bold = this.isNextCharAsterisk(i, lastIndex, trimmedChars);
                final boolean combined = this.isNextCharAsterisk(i + 1, lastIndex, trimmedChars);
                if (bold && combined) {
                    i = this.addRawComponent(i, trimmedText, "***", components, MarkdownRawComponent.Formatting.BOLD, MarkdownRawComponent.Formatting.ITALIC);
                }
                else if (bold) {
                    i = this.addRawComponent(i, trimmedText, "**", components, MarkdownRawComponent.Formatting.BOLD);
                }
                else {
                    i = this.addRawComponent(i, trimmedText, "*", components, MarkdownRawComponent.Formatting.ITALIC);
                }
            }
        }
        if (!builder.isEmpty()) {
            components.add(new MarkdownRawComponent(trimmedText, builder.toString(), new MarkdownRawComponent.Formatting[0]));
        }
    }
    
    private boolean isNextCharAsterisk(final int index, final int lastIndex, final char[] chars) {
        return index + 1 < lastIndex && chars[index + 1] == '*';
    }
    
    private int addRawComponent(int index, final String trimmedText, final String identifier, final List<MarkdownComponent> components, final MarkdownRawComponent.Formatting... formattings) {
        index += identifier.length();
        final int closingIndex = trimmedText.indexOf(identifier, index);
        String text;
        if (closingIndex != -1) {
            text = trimmedText.substring(index, closingIndex);
            index = closingIndex + identifier.length() - 1;
        }
        else {
            text = trimmedText.substring(index);
            index = trimmedText.length() - 1;
        }
        components.add(new MarkdownRawComponent(trimmedText, text, formattings));
        return index;
    }
}
