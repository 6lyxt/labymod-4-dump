// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.accessor;

import java.util.Iterator;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.settings.creator.MemberInspector;
import net.labymod.api.configuration.settings.type.SettingElement;
import java.util.ArrayList;
import net.labymod.api.LabyAPI;
import java.util.List;

public class SettingAccessorFactoryRegistry
{
    private final SettingAccessorFactory fallbackFactory;
    private final List<SettingAccessorFactory> factories;
    private final LabyAPI labyAPI;
    
    public SettingAccessorFactoryRegistry(final LabyAPI labyAPI) {
        this.fallbackFactory = new ReflectionSettingAccessorFactory();
        this.factories = new ArrayList<SettingAccessorFactory>();
        this.labyAPI = labyAPI;
        this.registerDefaults();
    }
    
    private void registerDefaults() {
        this.register(new ConfigPropertySettingAccessorFactory());
    }
    
    public void register(final SettingAccessorFactory factory) {
        this.factories.add(factory);
    }
    
    public SettingAccessor createAccessor(final SettingElement setting, final MemberInspector element, final Config config) {
        final AnnotatedElement member = element.member();
        if (!(member instanceof Field)) {
            return null;
        }
        final Field field = (Field)member;
        final int modifiers = field.getModifiers();
        if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
            return null;
        }
        if (Modifier.isTransient(modifiers)) {
            return null;
        }
        final SettingAccessorContext context = new SettingAccessorContext(element, field);
        for (final SettingAccessorFactory factory : this.factories) {
            if (factory.isAssignableFrom(field.getType())) {
                return factory.create(setting, context, config);
            }
        }
        return this.fallbackFactory.create(setting, context, config);
    }
}
