// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.activity.settings;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.lang.annotation.Annotation;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.api.Textures;
import net.labymod.api.util.I18n;
import net.labymod.api.client.gui.screen.Parent;
import java.util.Map;
import java.util.Collection;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;

@AutoWidget
@SettingWidget
public class CollectionResetWidget extends ButtonWidget
{
    private final String translationKey;
    private final ConfigProperty<?> property;
    
    private CollectionResetWidget(final String translationKey, final ConfigProperty<?> property) {
        this.translationKey = translationKey;
        this.property = property;
        this.setPressable(() -> {
            final Object o = this.property.get();
            if (o instanceof Collection) {
                final Collection<?> collection = (Collection<?>)o;
                collection.clear();
            }
            else if (o instanceof Map) {
                final Map<?, ?> map = (Map<?, ?>)o;
                map.clear();
            }
            else {
                return;
            }
            this.setEnabled(false);
        });
    }
    
    @Override
    public void initialize(final Parent parent) {
        final String translation = I18n.getTranslation(this.translationKey, new Object[0]);
        if (translation == null) {
            this.component = null;
            this.icon().set(Textures.SpriteCommon.TRASH);
        }
        else {
            this.component = Component.text(translation);
            this.icon().set(null);
        }
        boolean empty = true;
        final Object o = this.property.get();
        if (o instanceof Collection) {
            final Collection<?> collection = (Collection<?>)o;
            empty = collection.isEmpty();
        }
        else if (o instanceof Map) {
            final Map<?, ?> map = (Map<?, ?>)o;
            empty = map.isEmpty();
        }
        this.setEnabled(!empty);
        super.initialize(parent);
    }
    
    @SettingFactory
    public static class Factory implements WidgetFactory<CollectionResetSetting, CollectionResetWidget>
    {
        @Override
        public CollectionResetWidget[] create(final Setting setting, final CollectionResetSetting annotation, final SettingInfo<?> info, final SettingAccessor accessor) {
            final CollectionResetWidget collectionResetWidget = new CollectionResetWidget(setting.getTranslationKey() + ".text", accessor.property());
            return new CollectionResetWidget[] { collectionResetWidget };
        }
        
        @Override
        public Class<?>[] types() {
            return new Class[] { Map.class, Collection.class };
        }
    }
    
    @SettingElement
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CollectionResetSetting {
    }
}
