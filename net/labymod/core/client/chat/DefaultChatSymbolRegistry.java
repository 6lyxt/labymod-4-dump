// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat;

import net.labymod.api.client.component.builder.StyleableBuilder;
import java.io.Reader;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import net.labymod.api.Constants;
import java.util.Iterator;
import javax.inject.Inject;
import net.labymod.api.client.component.format.NamedTextColor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.util.collection.map.BiMap;
import net.labymod.api.client.component.format.Style;
import java.util.Map;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.chat.ChatSymbolRegistry;

@Singleton
@Implements(ChatSymbolRegistry.class)
public class DefaultChatSymbolRegistry implements ChatSymbolRegistry
{
    private final Map<Character, Style> styles;
    private final BiMap<Character, TextColor> colors;
    private final BiMap<Character, TextDecoration> decorations;
    private final List<Character> symbols;
    private boolean symbolsLoaded;
    
    @Inject
    public DefaultChatSymbolRegistry() {
        this.styles = new HashMap<Character, Style>();
        this.colors = new BiMap<Character, TextColor>();
        this.decorations = new BiMap<Character, TextDecoration>();
        this.symbols = new ArrayList<Character>();
        this.registerStyle('a', this.createStyleWithColor(NamedTextColor.GREEN));
        this.registerStyle('b', this.createStyleWithColor(NamedTextColor.AQUA));
        this.registerStyle('c', this.createStyleWithColor(NamedTextColor.RED));
        this.registerStyle('d', this.createStyleWithColor(NamedTextColor.LIGHT_PURPLE));
        this.registerStyle('e', this.createStyleWithColor(NamedTextColor.YELLOW));
        this.registerStyle('f', this.createStyleWithColor(NamedTextColor.WHITE));
        this.registerStyle('0', this.createStyleWithColor(NamedTextColor.BLACK));
        this.registerStyle('1', this.createStyleWithColor(NamedTextColor.DARK_BLUE));
        this.registerStyle('2', this.createStyleWithColor(NamedTextColor.DARK_GREEN));
        this.registerStyle('3', this.createStyleWithColor(NamedTextColor.DARK_AQUA));
        this.registerStyle('4', this.createStyleWithColor(NamedTextColor.DARK_RED));
        this.registerStyle('5', this.createStyleWithColor(NamedTextColor.DARK_PURPLE));
        this.registerStyle('6', this.createStyleWithColor(NamedTextColor.GOLD));
        this.registerStyle('7', this.createStyleWithColor(NamedTextColor.GRAY));
        this.registerStyle('8', this.createStyleWithColor(NamedTextColor.DARK_GRAY));
        this.registerStyle('9', this.createStyleWithColor(NamedTextColor.BLUE));
        this.registerStyle('k', this.createStyleWithDecoration(TextDecoration.OBFUSCATED));
        this.registerStyle('l', this.createStyleWithDecoration(TextDecoration.BOLD));
        this.registerStyle('m', this.createStyleWithDecoration(TextDecoration.STRIKETHROUGH));
        this.registerStyle('n', this.createStyleWithDecoration(TextDecoration.UNDERLINED));
        this.registerStyle('o', this.createStyleWithDecoration(TextDecoration.ITALIC));
        this.registerStyle('r', Style.empty());
    }
    
    private void registerStyle(final char c, final Style style) {
        this.styles.put(c, style);
        if (style.getColor() != null) {
            this.colors.put(c, style.getColor());
        }
        for (final TextDecoration decoration : TextDecoration.getValues()) {
            if (style.hasDecoration(decoration)) {
                this.decorations.put(c, decoration);
            }
        }
    }
    
    @Override
    public List<Character> getSymbols() {
        if (!this.symbolsLoaded) {
            this.reloadSymbols();
        }
        return this.symbols;
    }
    
    @Override
    public void addSymbol(final char c) {
        this.symbols.add(c);
    }
    
    @Override
    public void reloadSymbols() {
        this.symbols.clear();
        try (final InputStream inputStream = Constants.Resources.SYMBOLS.openStream();
             final Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            while (reader.ready()) {
                final char c = (char)reader.read();
                this.addSymbol(c);
            }
        }
        catch (final IOException exception) {
            exception.printStackTrace();
        }
        this.symbolsLoaded = true;
    }
    
    @Override
    public Map<Character, TextColor> getTextColors() {
        return this.colors.getKeysToValues();
    }
    
    @Override
    public Map<TextColor, Character> getInverseTextColors() {
        return this.colors.getValuesToKeys();
    }
    
    @Override
    public Map<Character, TextDecoration> getTextDecorations() {
        return this.decorations.getKeysToValues();
    }
    
    @Override
    public Map<TextDecoration, Character> getInverseTextDecorations() {
        return this.decorations.getValuesToKeys();
    }
    
    @Override
    public Style getStyle(final char c) {
        return this.styles.get(c);
    }
    
    private Style createStyleWithColor(final TextColor color) {
        return ((StyleableBuilder<T, Style.Builder>)Style.builder()).color(color).build();
    }
    
    private Style createStyleWithDecoration(final TextDecoration decoration) {
        return ((StyleableBuilder<T, Style.Builder>)Style.builder()).decorate(decoration).build();
    }
}
