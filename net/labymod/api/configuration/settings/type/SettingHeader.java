// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.type;

import net.labymod.api.client.gui.screen.widget.action.Pressable;
import org.spongepowered.include.com.google.common.collect.Lists;
import net.labymod.api.util.I18n;
import net.labymod.api.client.component.Component;
import java.util.List;
import net.labymod.api.client.gui.icon.Icon;

public class SettingHeader extends AbstractSetting
{
    private final String translationId;
    private final String customTranslation;
    private final boolean center;
    
    public SettingHeader(final String id, final boolean center, final String customTranslation, final String translationId) {
        super(id, null);
        this.center = center;
        this.customTranslation = customTranslation;
        this.translationId = translationId;
    }
    
    @Override
    public String getTranslationId() {
        return "header." + this.translationId;
    }
    
    @Override
    public String getTranslationKey() {
        return this.customTranslation.isEmpty() ? super.getTranslationKey() : (this.customTranslation + "." + this.getTranslationId());
    }
    
    public boolean isCenter() {
        return this.center;
    }
    
    public List<Component> getRows() {
        final String translationKey = this.getTranslationKey();
        final String defaultTranslationKey = translationKey + ".name";
        final String defaultTranslation = I18n.getTranslation(defaultTranslationKey, new Object[0]);
        final List<Component> components = Lists.newArrayList();
        if (defaultTranslation != null) {
            final String[] split2;
            final String[] split = split2 = defaultTranslation.split("\\n");
            for (final String row : split2) {
                components.add(Component.text(row));
            }
            return components;
        }
        for (int i = 0; i < Integer.MAX_VALUE; ++i) {
            final String translation = I18n.getTranslation(translationKey + ".row" + i, new Object[0]);
            if (translation == null) {
                break;
            }
            components.add(Component.text(translation));
        }
        if (components.isEmpty()) {
            components.add(Component.translatable(defaultTranslationKey, new Component[0]));
        }
        return components;
    }
    
    public Pressable pressable() {
        return Pressable.NOOP;
    }
}
