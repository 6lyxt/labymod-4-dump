// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.property;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.event.Subscribe;
import java.util.Iterator;
import net.labymod.api.service.CustomServiceLoader;
import net.labymod.api.event.labymod.ServiceLoadEvent;
import javax.inject.Inject;
import java.util.HashMap;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Map;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class PropertyRegistry
{
    private final Map<Class<? extends Widget>, DirectPropertyValueAccessor> directPropertyValueAccessors;
    
    @Inject
    public PropertyRegistry(final EventBus eventBus) {
        this.directPropertyValueAccessors = new HashMap<Class<? extends Widget>, DirectPropertyValueAccessor>();
        eventBus.registerListener(this);
    }
    
    @Subscribe
    public void registerDefaults(final ServiceLoadEvent event) {
        final CustomServiceLoader<DirectPropertyValueAccessorRegisterBulk> directBulks = event.load(DirectPropertyValueAccessorRegisterBulk.class, CustomServiceLoader.ServiceType.ADVANCED);
        for (final DirectPropertyValueAccessorRegisterBulk directBulk : directBulks) {
            this.registerDirectBulk(directBulk);
        }
    }
    
    @Nullable
    public PropertyValueAccessor<?, ?, ?> getValueAccessor(final Widget widget, final String key) {
        final DirectPropertyValueAccessor accessor = widget.getDirectPropertyValueAccessor();
        if (accessor == null) {
            return null;
        }
        return accessor.getPropertyValueAccessor(key);
    }
    
    @Nullable
    public LssPropertyResetter getPropertyResetter(final Widget widget) {
        final DirectPropertyValueAccessor accessor = widget.getDirectPropertyValueAccessor();
        if (accessor == null) {
            return null;
        }
        return accessor.getPropertyResetter();
    }
    
    @Nullable
    public DirectPropertyValueAccessor getDirectPropertyValueAccessor(final Class<? extends Widget> widgetClass) {
        return this.directPropertyValueAccessors.get(widgetClass);
    }
    
    private void registerDirectBulk(final DirectPropertyValueAccessorRegisterBulk directBulk) {
        final Map<Class<? extends Widget>, DirectPropertyValueAccessor> map = new HashMap<Class<? extends Widget>, DirectPropertyValueAccessor>();
        directBulk.register(map);
        this.directPropertyValueAccessors.putAll(map);
    }
}
