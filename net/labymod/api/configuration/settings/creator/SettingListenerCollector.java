// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator;

import java.lang.reflect.Method;
import net.labymod.api.util.reflection.Reflection;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingListener;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.configuration.loader.Config;

public class SettingListenerCollector
{
    public static List<SettingListenerMethod> collect(final Class<? extends Config> configClass) {
        final List<SettingListenerMethod> listenerMethods = new ArrayList<SettingListenerMethod>();
        Reflection.getMethods(configClass, false, method -> {
            final SettingListener annotation = method.getAnnotation(SettingListener.class);
            if (annotation == null) {
                return;
            }
            else if (method.getReturnType() != Void.TYPE) {
                return;
            }
            else if (method.getParameterCount() != 1 || !method.getParameterTypes()[0].isAssignableFrom(SettingElement.class)) {
                return;
            }
            else {
                listenerMethods.add(new SettingListenerMethod(annotation, method));
                return;
            }
        });
        return listenerMethods;
    }
}
