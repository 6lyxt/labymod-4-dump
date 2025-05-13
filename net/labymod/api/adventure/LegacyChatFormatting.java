// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.adventure;

import net.labymod.api.util.ColorUtil;
import org.jetbrains.annotations.Nullable;
import javax.inject.Inject;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.NamedTextColor;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.labymod.api.client.component.format.TextColor;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.labymod.api.client.component.format.Style;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Deprecated
@Referenceable
public class LegacyChatFormatting
{
    private static final int INITIAL_CAPACITY = 22;
    private final Int2ObjectMap<Style> styles;
    private final Object2IntMap<TextColor> colorToCharacter;
    
    @Inject
    public LegacyChatFormatting() {
        this.styles = (Int2ObjectMap<Style>)new Int2ObjectOpenHashMap(22);
        this.colorToCharacter = (Object2IntMap<TextColor>)new Object2IntOpenHashMap(22);
        this.registerStyle('a', Style.style(NamedTextColor.GREEN));
        this.registerStyle('b', Style.style(NamedTextColor.AQUA));
        this.registerStyle('c', Style.style(NamedTextColor.RED));
        this.registerStyle('d', Style.style(NamedTextColor.LIGHT_PURPLE));
        this.registerStyle('e', Style.style(NamedTextColor.YELLOW));
        this.registerStyle('f', Style.style(NamedTextColor.WHITE));
        this.registerStyle('0', Style.style(NamedTextColor.BLACK));
        this.registerStyle('1', Style.style(NamedTextColor.DARK_BLUE));
        this.registerStyle('2', Style.style(NamedTextColor.DARK_GREEN));
        this.registerStyle('3', Style.style(NamedTextColor.DARK_AQUA));
        this.registerStyle('4', Style.style(NamedTextColor.DARK_RED));
        this.registerStyle('5', Style.style(NamedTextColor.DARK_PURPLE));
        this.registerStyle('6', Style.style(NamedTextColor.GOLD));
        this.registerStyle('7', Style.style(NamedTextColor.GRAY));
        this.registerStyle('8', Style.style(NamedTextColor.DARK_GRAY));
        this.registerStyle('9', Style.style(NamedTextColor.BLUE));
        this.registerStyle('k', Style.style(TextDecoration.OBFUSCATED));
        this.registerStyle('l', Style.style(TextDecoration.BOLD));
        this.registerStyle('m', Style.style(TextDecoration.STRIKETHROUGH));
        this.registerStyle('n', Style.style(TextDecoration.UNDERLINED));
        this.registerStyle('o', Style.style(TextDecoration.ITALIC));
        this.registerStyle('r', Style.empty());
    }
    
    private void registerStyle(final char key, final Style style) {
        this.styles.put((int)key, (Object)style);
        final TextColor color = style.getColor();
        if (color != null) {
            this.colorToCharacter.put((Object)color, (int)key);
        }
    }
    
    @Nullable
    public Style getStyle(final char key) {
        return (Style)this.styles.get((int)key);
    }
    
    public char getColorCode(final TextColor color) {
        final Integer colorCode = this.colorToCharacter.getOrDefault((Object)color, (Integer)null);
        if (colorCode == null) {
            return this.getColorCode(ColorUtil.getClosestDefaultTextColor(color));
        }
        return (char)(int)colorCode;
    }
}
