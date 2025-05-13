// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font;

import java.util.Collection;
import java.util.Collections;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.TranslatableComponent;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ComponentMapper
{
    default Component fromMinecraftComponent(final Object component) {
        if (component instanceof final Component labyComponent) {
            return labyComponent;
        }
        return null;
    }
    
    default List<Component> fromMinecraftComponents(final List<?> components) {
        final List<Component> result = new ArrayList<Component>(components.size());
        for (final Object component : components) {
            result.add(this.fromMinecraftComponent(component));
        }
        return result;
    }
    
    default List<?> toMinecraftComponents(final List<Component> components) {
        final List<Object> result = new ArrayList<Object>(components.size());
        for (final Component component : components) {
            result.add(this.toMinecraftComponent(component));
        }
        return result;
    }
    
    default Object toMinecraftComponent(final Component component) {
        return component;
    }
    
    @Nullable
    default String getTranslationKeyOfComponent(final Object minecraftComponent) {
        final Component component = this.fromMinecraftComponent(minecraftComponent);
        if (component == null) {
            return null;
        }
        if (component instanceof final TranslatableComponent translatableComponent) {
            return translatableComponent.getKey();
        }
        return null;
    }
    
    default List<String> getTranslationKeysOfComponent(final Object minecraftComponent) {
        final Component component = this.fromMinecraftComponent(minecraftComponent);
        if (component == null) {
            return Collections.emptyList();
        }
        return this.getTranslationKeysOfComponent(component);
    }
    
    default List<String> getTranslationKeysOfComponent(final Component component) {
        final List<String> keys = new ArrayList<String>();
        final String translationKey = this.getTranslationKey(component);
        if (translationKey != null) {
            keys.add(translationKey);
        }
        for (final Component child : component.getChildren()) {
            keys.addAll(this.getTranslationKeysOfComponent(child));
        }
        if (component instanceof final TranslatableComponent translatableComponent) {
            for (final Component argument : translatableComponent.getArguments()) {
                keys.addAll(this.getTranslationKeysOfComponent(argument));
            }
        }
        return keys;
    }
    
    @Nullable
    default String getTranslationKey(final Component component) {
        if (component instanceof final TranslatableComponent translatableComponent) {
            return translatableComponent.getKey();
        }
        return null;
    }
    
    default String translateColorCodes(final String textToTranslate) {
        return this.translateColorCodes('&', '§', textToTranslate);
    }
    
    default String translateColorCodes(final char oldColorChar, final char newColorChar, final String textToTranslate) {
        final char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; ++i) {
            if (b[i] == oldColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = newColorChar;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }
}
