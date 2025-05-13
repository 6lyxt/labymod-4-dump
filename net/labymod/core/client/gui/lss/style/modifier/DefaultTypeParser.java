// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier;

import net.labymod.api.util.StringUtil;
import javax.inject.Inject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ThemeResourceRegistry;
import java.util.HashMap;
import net.labymod.api.client.gui.screen.theme.ThemeRendererParser;
import java.util.Map;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.lss.style.modifier.TypeParser;

@Singleton
@Implements(TypeParser.class)
public class DefaultTypeParser implements TypeParser
{
    private final Map<Class<?>, Parser> parsers;
    
    @Inject
    public DefaultTypeParser(final ThemeRendererParser themeRendererParser) {
        this.parsers = new HashMap<Class<?>, Parser>();
        this.register((type, value) -> ThemeResourceRegistry.valueOf(value).resource(), ResourceLocation.class);
        this.register((type, value) -> Float.parseFloat(value), Float.TYPE, Float.class);
        this.register((type, value) -> Double.parseDouble(value), Double.TYPE, Double.class);
        this.register((type, value) -> Integer.parseInt(value), Integer.TYPE, Integer.class);
        this.register((type, value) -> Byte.parseByte(value), Byte.TYPE, Byte.class);
        this.register((type, value) -> Long.parseLong(value), Long.TYPE, Long.class);
        this.register((type, value) -> Boolean.parseBoolean(value), Boolean.TYPE, Boolean.class);
        this.register((type, value) -> Short.parseShort(value), Short.TYPE, Short.class);
        this.register((type, value) -> themeRendererParser.parse(value), ThemeRenderer.class);
    }
    
    @SafeVarargs
    @Override
    public final <T> void register(final Parser<T> parser, final Class<T>... types) {
        for (final Class<?> type : types) {
            if (this.parsers.containsKey(type)) {
                throw new IllegalArgumentException("Parser for type " + type.getName() + " is already registered: " + this.parsers.get(type).getClass().getName());
            }
            this.parsers.put(type, parser);
        }
    }
    
    @Override
    public Object parseValue(final Class<?> type, final String value) throws Exception {
        if (type == null || type == String.class) {
            return value;
        }
        if (value.equalsIgnoreCase("null")) {
            return null;
        }
        if (type.isEnum()) {
            return Enum.valueOf(type, StringUtil.toUppercase(value).replace("-", "_"));
        }
        final Parser parser = this.parsers.get(type);
        return (parser != null) ? parser.parseValue(type, value) : null;
    }
}
