// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.localization.Internationalization;

public class I18n
{
    private static final Internationalization INSTANCE;
    
    private I18n() {
    }
    
    @NotNull
    public static String translate(@NotNull final String key, final Object... args) {
        return I18n.INSTANCE.translate(key, args);
    }
    
    @Nullable
    public static String getTranslation(@NotNull final String key, final Object... args) {
        return I18n.INSTANCE.getTranslation(key, args);
    }
    
    public static boolean has(@NotNull final String key) {
        return I18n.INSTANCE.has(key);
    }
    
    static {
        INSTANCE = Laby.references().internationalization();
    }
}
