// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.widget;

import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.lang.annotation.Annotation;

public interface WidgetFactory<T extends Annotation, K extends Widget>
{
    Class<?>[] types();
    
    default K[] create(final Setting setting, final T annotation, final SettingInfo<?> info, final SettingAccessor accessor) {
        return this.create(setting, annotation, accessor);
    }
    
    default K[] create(final Setting setting, final T annotation, final SettingAccessor accessor) {
        throw new UnsupportedOperationException("Not implemented by " + this.getClass().getSimpleName());
    }
}
