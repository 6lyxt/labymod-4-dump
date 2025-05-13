// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources;

import net.labymod.api.Textures;
import net.labymod.api.Constants;
import java.util.HashMap;
import java.lang.reflect.Field;
import java.util.Locale;
import net.labymod.api.util.TextFormat;
import net.labymod.api.client.gui.icon.Icon;
import java.util.Map;

public class ThemeResourceRegistry
{
    private static final Map<String, ThemeResourceLocation> RESOURCES;
    private static final Map<String, Icon> ICONS;
    
    private static void registerIcons(final Class<?> declaringClass) {
        for (final Class<?> declaredClass : declaringClass.getDeclaredClasses()) {
            registerIcons(declaredClass);
        }
        for (final Field field : declaringClass.getDeclaredFields()) {
            if (field.getType() == Icon.class) {
                try {
                    final Icon icon = (Icon)field.get(null);
                    final TextFormat nameFormat = TextFormat.SNAKE_CASE;
                    final String name = field.getName();
                    for (final String formattedName : new String[] { nameFormat.toDashCase(name), name }) {
                        ThemeResourceRegistry.ICONS.put(formattedName.toUpperCase(Locale.US), icon);
                    }
                }
                catch (final IllegalAccessException exception) {
                    throw new RuntimeException(exception);
                }
            }
        }
    }
    
    public static ThemeResourceLocation valueOf(final String id) {
        return ThemeResourceRegistry.RESOURCES.get(id.toUpperCase(Locale.US));
    }
    
    public static Icon getIcon(final String id) {
        return ThemeResourceRegistry.ICONS.get(id.toUpperCase(Locale.US));
    }
    
    static {
        RESOURCES = new HashMap<String, ThemeResourceLocation>();
        ICONS = new HashMap<String, Icon>();
        for (final Field field : Constants.NamedThemeResource.class.getDeclaredFields()) {
            final String id = field.getName();
            try {
                ThemeResourceRegistry.RESOURCES.put(id, (ThemeResourceLocation)field.get(null));
            }
            catch (final IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        ThemeResourceRegistry.RESOURCES.put("COMMON", Textures.SpriteCommon.TEXTURE);
        ThemeResourceRegistry.RESOURCES.put("MINECRAFT_ICONS", Textures.SpriteMinecraftIcons.TEXTURE);
        registerIcons(Textures.class);
    }
}
