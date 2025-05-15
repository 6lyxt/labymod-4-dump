// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.object.lootbox.content;

import net.labymod.api.Constants;
import java.util.Locale;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.resources.ResourceLocation;

public enum PoolCategory
{
    COMMON(-5723992, Constants.Resources.SOUND_LOOTBOX_COMMON), 
    SPECIAL(-11834881, Constants.Resources.SOUND_LOOTBOX_SPECIAL), 
    LEGENDARY(-10496, Constants.Resources.SOUND_LOOTBOX_LEGENDARY);
    
    private final int color;
    private final ResourceLocation sound;
    
    private PoolCategory(final int color, final ResourceLocation sound) {
        this.color = color;
        this.sound = sound;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public TextColor getTextColor() {
        return TextColor.color(this.color);
    }
    
    public Component getNameComponent() {
        final String key = "labymod.activity.lootBox.category." + this.name().toLowerCase(Locale.ENGLISH);
        return Component.translatable(key, new Component[0]);
    }
    
    public ResourceLocation getSound() {
        return this.sound;
    }
}
