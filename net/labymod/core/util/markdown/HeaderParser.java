// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.markdown;

import net.labymod.api.util.markdown.MarkdownHeaderComponent;
import java.util.List;
import net.labymod.api.util.markdown.MarkdownParser;
import net.labymod.api.util.markdown.MarkdownComponent;

public class HeaderParser implements MarkdownComponent.Parser
{
    @Override
    public int parse(final MarkdownParser.MarkdownLine line, final int lineIndex, final int lastLineIndex, final MarkdownParser.MarkdownLine[] lines, final List<MarkdownComponent> components) {
        int level = 0;
        int textIndex = -1;
        final char[] trimmedChars = line.getChars();
        for (int i = 0; i < trimmedChars.length; ++i) {
            final char trimmedChar = trimmedChars[i];
            if (trimmedChar == ' ' && level != 0) {
                textIndex = i;
                break;
            }
            if (trimmedChar == '#') {
                ++level;
            }
        }
        if (level != 0 && textIndex != -1) {
            components.add(new MarkdownHeaderComponent(level, line.getText().substring(textIndex).trim()));
            return 1;
        }
        return 0;
    }
}
