// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.settings.widget;

import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import net.labymod.api.service.CustomServiceLoader;
import net.labymod.api.configuration.settings.widget.WidgetStorage;
import java.util.ArrayList;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.ServiceLoadEvent;
import javax.inject.Inject;
import java.util.HashMap;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.lang.annotation.Annotation;
import java.util.Map;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.configuration.settings.widget.WidgetRegistry;

@Singleton
@Implements(WidgetRegistry.class)
public class DefaultWidgetRegistry implements WidgetRegistry
{
    private final Logging logger;
    private final Map<Class<? extends Annotation>, Class<? extends Widget>> registry;
    
    @Inject
    public DefaultWidgetRegistry(final EventBus eventBus) {
        this.registry = new HashMap<Class<? extends Annotation>, Class<? extends Widget>>();
        eventBus.registerListener(this);
        this.logger = Logging.create(WidgetRegistry.class);
    }
    
    @Subscribe
    public void loadWidgetStorages(final ServiceLoadEvent event) {
        this.loadWidgetStorage(event.classLoader());
    }
    
    @Override
    public void loadWidgetStorage(final ClassLoader loader) {
        final List<Class<? extends Widget>> storage = new ArrayList<Class<? extends Widget>>();
        final CustomServiceLoader<WidgetStorage> storageLoader = CustomServiceLoader.load(WidgetStorage.class, loader, CustomServiceLoader.ServiceType.CROSS_VERSION);
        for (final WidgetStorage widgetStorage : storageLoader) {
            widgetStorage.store(storage);
        }
        storage.forEach(this::register);
        storage.clear();
    }
    
    @Override
    public void register(final Class<? extends Widget> clazz) {
        for (final Class<?> subClass : clazz.getClasses()) {
            if (subClass.getAnnotation(SettingElement.class) != null) {
                this.registry.put((Class<? extends Annotation>)subClass, clazz);
                return;
            }
        }
    }
    
    @Override
    public Widget[] createWidgets(final Setting setting, final Annotation annotation, final SettingInfo<?> info, final SettingAccessor accessor) {
        try {
            final Class<? extends Widget> clazz = this.getWidgetTypeByAnnotation(annotation);
            if (clazz == null) {
                return null;
            }
            for (final Class<?> declaredClass : clazz.getDeclaredClasses()) {
                if (WidgetFactory.class.isAssignableFrom(declaredClass)) {
                    final WidgetFactory factory = (WidgetFactory)declaredClass.getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
                    return this.createWidgets(factory, setting, annotation, info, accessor);
                }
            }
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public Class<? extends Widget> getWidgetTypeByAnnotation(final Annotation annotation) {
        return this.registry.get(annotation.annotationType());
    }
    
    private Widget[] createWidgets(final WidgetFactory<Annotation, Widget> widgetFactory, final Setting setting, final Annotation annotation, final SettingInfo<?> info, final SettingAccessor accessor) {
        final Class<?>[] types = widgetFactory.types();
        if (types.length != 0) {
            final String[] typeString = new String[types.length];
            boolean typesMatch = false;
            for (int i = 0; i < types.length; ++i) {
                final Class<?> type = types[i];
                if (accessor.getType() == type || type.isAssignableFrom(accessor.getType())) {
                    typesMatch = true;
                }
                typeString[i] = type.getSimpleName();
            }
            if (!typesMatch) {
                this.logger.error("The setting field \"{}\" has the type {} but has to be {}", setting.getTranslationKey(), accessor.getType().getSimpleName(), (typeString.length == 1) ? (": " + typeString[0]) : (" one of these types: " + String.join(", ", (CharSequence[])typeString)));
                return null;
            }
        }
        return widgetFactory.create(setting, annotation, info, accessor);
    }
}
