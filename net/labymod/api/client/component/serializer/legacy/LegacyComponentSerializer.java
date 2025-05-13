// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.serializer.legacy;

import net.labymod.api.util.ColorUtil;
import java.util.Iterator;
import java.util.LinkedList;
import net.labymod.api.client.component.format.Style;
import java.util.Queue;
import net.labymod.api.client.component.format.NamedTextColor;
import org.spongepowered.include.com.google.common.collect.HashBiMap;
import java.util.HashMap;
import java.util.Collection;
import java.util.List;
import java.util.Collections;
import net.labymod.api.client.component.TextComponent;
import java.util.ArrayList;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.serializer.gson.GsonComponentSerializer;
import com.google.gson.JsonElement;
import net.labymod.api.client.component.flattener.FlattenerListener;
import net.labymod.api.client.component.Component;
import java.util.function.Function;
import net.labymod.api.client.component.flattener.ComponentFlattener;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.TextColor;
import org.spongepowered.include.com.google.common.collect.BiMap;
import java.util.Map;

public class LegacyComponentSerializer
{
    private static final char RESET_CHAR = 'r';
    private static final Map<Character, LegacyComponentSerializer> INSTANCES;
    private static final BiMap<Integer, Character> COLOR_CHARS;
    private static final BiMap<TextColor, Character> TEXT_COLOR_CHARS;
    private static final BiMap<TextDecoration, Character> DECORATION_CHARS;
    private static final LegacyFlattenerListener FLATTENER_LISTENER;
    private final char character;
    private final ComponentFlattener flattener;
    
    private LegacyComponentSerializer(final char character, final ComponentFlattener flattener) {
        this.character = character;
        this.flattener = flattener;
    }
    
    private LegacyComponentSerializer(final char character) {
        this(character, ComponentFlattener.BASIC);
    }
    
    public static LegacyComponentSerializer legacySection() {
        return LegacyComponentSerializer.INSTANCES.computeIfAbsent('§', LegacyComponentSerializer::new);
    }
    
    public static LegacyComponentSerializer legacyAmpersand() {
        return LegacyComponentSerializer.INSTANCES.computeIfAbsent('&', LegacyComponentSerializer::new);
    }
    
    public static LegacyComponentSerializer legacy(final char character) {
        return LegacyComponentSerializer.INSTANCES.computeIfAbsent(character, LegacyComponentSerializer::new);
    }
    
    public String serialize(final Component component) {
        LegacyComponentSerializer.FLATTENER_LISTENER.begin(this.character);
        this.flattener.flatten(component, LegacyComponentSerializer.FLATTENER_LISTENER);
        return LegacyComponentSerializer.FLATTENER_LISTENER.getText();
    }
    
    public JsonElement deserializeToTree(final String input) {
        return GsonComponentSerializer.gson().serializeToTree(this.deserialize(input));
    }
    
    public Component deserialize(@Nullable final String input) {
        if (input == null) {
            return Component.empty();
        }
        int nextSection = input.lastIndexOf(this.character, input.length() - 2);
        if (nextSection == -1) {
            return Component.text(input);
        }
        boolean reset = false;
        int pos = input.length();
        TextComponent.Builder current = null;
        final List<Component> parts = new ArrayList<Component>();
        do {
            final char format = input.charAt(nextSection + 1);
            TextColor textColor = null;
            TextDecoration decoration = null;
            if (format != 'r') {
                textColor = (TextColor)LegacyComponentSerializer.TEXT_COLOR_CHARS.inverse().get((Object)format);
                if (textColor == null) {
                    decoration = (TextDecoration)LegacyComponentSerializer.DECORATION_CHARS.inverse().get((Object)format);
                }
            }
            if (format == 'r' || textColor != null || decoration != null) {
                final int from = nextSection + 2;
                if (from != pos) {
                    if (current != null) {
                        if (reset) {
                            parts.add(current.build());
                            reset = false;
                            current = Component.text();
                        }
                        else {
                            current = ((Component.Builder<T, TextComponent.Builder>)Component.text()).append(current.build());
                        }
                    }
                    else {
                        current = Component.text();
                    }
                    current.text(input.substring(from, pos));
                }
                else if (current == null) {
                    current = Component.text();
                }
                if (!reset) {
                    if (format == 'r') {
                        reset = true;
                    }
                    else if (textColor != null) {
                        current.color(textColor);
                        reset = true;
                    }
                    else {
                        current.decorate(decoration);
                    }
                }
                pos = nextSection;
            }
            nextSection = input.lastIndexOf(this.character, nextSection - 1);
        } while (nextSection != -1);
        if (current != null) {
            parts.add(current.build());
        }
        final String remaining = (pos > 0) ? input.substring(0, pos) : "";
        if (parts.size() == 1 && remaining.isEmpty()) {
            return parts.get(0);
        }
        Collections.reverse(parts);
        return ((Component.Builder<T, TextComponent.Builder>)Component.text().text(remaining)).append(parts).build();
    }
    
    public LegacyComponentSerializer withFlattener(final ComponentFlattener flattener) {
        return new LegacyComponentSerializer(this.character, flattener);
    }
    
    private static void registerTextColor(final TextColor textColor, final char colorChar) {
        if (textColor == null) {
            throw new NullPointerException("Invalid default text color: '" + colorChar);
        }
        final int rgb = textColor.getValue();
        LegacyComponentSerializer.COLOR_CHARS.put((Object)rgb, (Object)colorChar);
        LegacyComponentSerializer.TEXT_COLOR_CHARS.put((Object)textColor, (Object)colorChar);
    }
    
    static {
        INSTANCES = new HashMap<Character, LegacyComponentSerializer>();
        COLOR_CHARS = (BiMap)HashBiMap.create(16);
        TEXT_COLOR_CHARS = (BiMap)HashBiMap.create(16);
        DECORATION_CHARS = (BiMap)HashBiMap.create(5);
        FLATTENER_LISTENER = new LegacyFlattenerListener();
        registerTextColor(NamedTextColor.BLACK, '0');
        registerTextColor(NamedTextColor.DARK_BLUE, '1');
        registerTextColor(NamedTextColor.DARK_GREEN, '2');
        registerTextColor(NamedTextColor.DARK_AQUA, '3');
        registerTextColor(NamedTextColor.DARK_RED, '4');
        registerTextColor(NamedTextColor.DARK_PURPLE, '5');
        registerTextColor(NamedTextColor.GOLD, '6');
        registerTextColor(NamedTextColor.GRAY, '7');
        registerTextColor(NamedTextColor.DARK_GRAY, '8');
        registerTextColor(NamedTextColor.BLUE, '9');
        registerTextColor(NamedTextColor.GREEN, 'a');
        registerTextColor(NamedTextColor.AQUA, 'b');
        registerTextColor(NamedTextColor.RED, 'c');
        registerTextColor(NamedTextColor.LIGHT_PURPLE, 'd');
        registerTextColor(NamedTextColor.YELLOW, 'e');
        registerTextColor(NamedTextColor.WHITE, 'f');
        LegacyComponentSerializer.DECORATION_CHARS.put((Object)TextDecoration.BOLD, (Object)'l');
        LegacyComponentSerializer.DECORATION_CHARS.put((Object)TextDecoration.ITALIC, (Object)'o');
        LegacyComponentSerializer.DECORATION_CHARS.put((Object)TextDecoration.UNDERLINED, (Object)'n');
        LegacyComponentSerializer.DECORATION_CHARS.put((Object)TextDecoration.STRIKETHROUGH, (Object)'m');
        LegacyComponentSerializer.DECORATION_CHARS.put((Object)TextDecoration.OBFUSCATED, (Object)'k');
    }
    
    private static final class LegacyFlattenerListener implements FlattenerListener
    {
        private Character character;
        private StringBuilder stringBuilder;
        private final Queue<Style> styleQueue;
        
        private LegacyFlattenerListener() {
            this.styleQueue = new LinkedList<Style>();
        }
        
        public void begin(final char character) {
            this.character = character;
            this.stringBuilder = new StringBuilder();
            this.styleQueue.clear();
        }
        
        @Override
        public void push(final Component source) {
            this.styleQueue.add(source.style());
        }
        
        @Override
        public void component(final String text) {
            Style style = null;
            final Iterator<Object> iterator = this.styleQueue.iterator();
            while (iterator.hasNext()) {
                final Style s = style = iterator.next();
            }
            if (style != null) {
                if (style.getColor() != null) {
                    this.stringBuilder.append(this.character);
                    this.stringBuilder.append(this.getColorChar(style.getColor()));
                }
                for (final Map.Entry<TextDecoration, Character> entry : LegacyComponentSerializer.DECORATION_CHARS.entrySet()) {
                    if (style.hasDecoration(entry.getKey())) {
                        this.stringBuilder.append(this.character);
                        this.stringBuilder.append(entry.getValue());
                    }
                }
            }
            this.stringBuilder.append(text);
        }
        
        @Override
        public void pop(final Component source) {
            this.styleQueue.remove();
        }
        
        public String getText() {
            return this.stringBuilder.toString();
        }
        
        private char getColorChar(final TextColor textColor) {
            final Character character = (Character)LegacyComponentSerializer.COLOR_CHARS.get((Object)textColor.getValue());
            if (character != null) {
                return character;
            }
            final TextColor closestDefaultTextColor = ColorUtil.getClosestDefaultTextColor(textColor);
            return (char)LegacyComponentSerializer.COLOR_CHARS.get((Object)closestDefaultTextColor.getValue());
        }
    }
}
