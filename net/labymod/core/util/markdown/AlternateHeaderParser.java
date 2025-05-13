// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.markdown;

import net.labymod.api.util.markdown.MarkdownAlternateHeaderComponent;
import java.util.List;
import net.labymod.api.util.markdown.MarkdownParser;
import net.labymod.api.util.markdown.MarkdownComponent;

public class AlternateHeaderParser implements MarkdownComponent.Parser
{
    @Override
    public int parse(final MarkdownParser.MarkdownLine line, final int lineIndex, final int lastLineIndex, final MarkdownParser.MarkdownLine[] lines, final List<MarkdownComponent> components) {
        if (lineIndex == lastLineIndex) {
            return 0;
        }
        final MarkdownParser.MarkdownLine nextLine = lines[lineIndex + 1];
        int level = 0;
        for (final char c : nextLine.getTrimmedChars()) {
            if (c == '=') {
                if (level != 0 && level != 1) {
                    return 0;
                }
                level = 1;
            }
            else {
                if (c != '-') {
                    return 0;
                }
                if (level != 0 && level != 2) {
                    return 0;
                }
                level = 2;
            }
        }
        if (level == 0) {
            return 0;
        }
        components.add(new MarkdownAlternateHeaderComponent(level, line.getTrimmedText()));
        return 2;
    }
}
