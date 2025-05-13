// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util;

import java.util.Iterator;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.api.event.Event;
import java.util.function.Consumer;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.method.SubscribeMethod;
import net.labymod.api.event.labymod.ServiceLoadEvent;
import net.labymod.api.event.labymod.SubscribeMethodRegisterEvent;
import javax.inject.Inject;
import net.labymod.api.Laby;
import javax.inject.Singleton;

@Singleton
public class ServiceLoadEventHandler
{
    @Inject
    public ServiceLoadEventHandler() {
        this.loadAllServices(Laby::fireEvent);
    }
    
    @Subscribe
    public void loadAllServices(final SubscribeMethodRegisterEvent event) {
        final SubscribeMethod method = event.method();
        if (method.getEventType() != ServiceLoadEvent.class) {
            return;
        }
        this.loadAllServices(e -> event.registry().invokeMethod(method, e));
    }
    
    private void loadAllServices(final Consumer<Event> consumer) {
        consumer.accept(new ServiceLoadEvent(this.getClass().getClassLoader(), ServiceLoadEvent.State.LISTENER_REGISTERED));
        for (final LoadedAddon addon : DefaultAddonService.getInstance().getLoadedAddons()) {
            consumer.accept(new ServiceLoadEvent(addon.getClassLoader(), ServiceLoadEvent.State.LISTENER_REGISTERED));
        }
    }
}
