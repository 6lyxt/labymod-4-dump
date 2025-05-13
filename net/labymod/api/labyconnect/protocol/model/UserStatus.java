// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labyconnect.protocol.model;

import net.labymod.api.client.component.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.I18n;
import net.labymod.api.client.component.Component;
import net.labymod.api.util.TextFormat;
import java.util.Locale;
import net.labymod.api.client.component.TranslatableComponent;
import java.awt.Color;
import net.labymod.api.client.component.format.TextColor;

public enum UserStatus
{
    ONLINE("online", (byte)0, NamedTextColor.DARK_GREEN), 
    AWAY("away", (byte)1, NamedTextColor.GOLD), 
    BUSY("busy", (byte)2, NamedTextColor.RED), 
    OFFLINE("invisible", (byte)(-1), NamedTextColor.GRAY);
    
    public static final String TRANSLATION_KEY_PREFIX = "labymod.activity.labyconnect.chat.status.%s";
    private static final UserStatus[] VALUES;
    private final byte id;
    private final TextColor textColor;
    private final Color color;
    private final String translationKey;
    private final TranslatableComponent component;
    
    private UserStatus(final String key, final byte id, final TextColor textColor) {
        this.id = id;
        this.textColor = textColor;
        this.color = new Color(textColor.value());
        this.translationKey = String.format(Locale.ROOT, "labymod.activity.labyconnect.chat.status.%s", key);
        this.component = Component.translatable(String.format(Locale.ROOT, "labymod.activity.labyconnect.chat.status.%s", TextFormat.DASH_CASE.toCamelCase(this.name(), true)), textColor);
    }
    
    public byte getId() {
        return this.id;
    }
    
    @Override
    public String toString() {
        return I18n.translate(this.translationKey, new Object[0]);
    }
    
    @NotNull
    public Component component() {
        return this.component;
    }
    
    public String getLocalTranslationKey() {
        return this.translationKey;
    }
    
    public String getRemoteTranslationKey(final boolean self) {
        final String key = this.component.getKey();
        if (this == UserStatus.OFFLINE && self) {
            return this.translationKey;
        }
        return key;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public TextColor textColor() {
        return this.textColor;
    }
    
    @Deprecated
    @NotNull
    public TextColor chatColor() {
        return this.textColor();
    }
    
    @Deprecated
    @NotNull
    public String getName() {
        return this.name();
    }
    
    public static UserStatus getById(final int id) {
        for (final UserStatus status : UserStatus.VALUES) {
            if (status.id == id) {
                return status;
            }
        }
        return UserStatus.OFFLINE;
    }
    
    static {
        VALUES = values();
    }
}
