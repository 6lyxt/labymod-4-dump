// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.component;

import javax.inject.Inject;
import net.labymod.api.client.chat.ChatSymbolRegistry;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.font.TextColorStripper;

@Singleton
@Implements(TextColorStripper.class)
public class DefaultTextColorStripper implements TextColorStripper
{
    private static final char DEFAULT_COLOR_CHAR = '§';
    private final ChatSymbolRegistry chatSymbolRegistry;
    
    @Inject
    public DefaultTextColorStripper(final ChatSymbolRegistry chatSymbolRegistry) {
        this.chatSymbolRegistry = chatSymbolRegistry;
    }
    
    @Override
    public String stripColorCodes(final String text) {
        return this.stripColorCodes(text, '§');
    }
    
    @Override
    public String stripColorCodes(final String text, final char colorChar) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); ++i) {
            final char c = text.charAt(i);
            if (c == colorChar && i < text.length() - 1) {
                final char next = text.charAt(i + 1);
                if (this.chatSymbolRegistry.getStyle(next) != null) {
                    ++i;
                    continue;
                }
            }
            builder.append(c);
        }
        return builder.toString();
    }
}
