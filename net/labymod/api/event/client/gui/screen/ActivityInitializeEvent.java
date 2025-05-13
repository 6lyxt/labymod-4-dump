// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen;

import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.event.Event;

record ActivityInitializeEvent(Activity activity) implements Event {
    public String getIdentifier() {
        return this.activity.getIdentifier();
    }
    
    public boolean is(final Class<? extends Activity> clazz) {
        return clazz.isAssignableFrom(this.activity.getClass());
    }
    
    public boolean is(final String namespace, final String name) {
        return this.activity.getNamespace().equals(namespace) && this.activity.getName().equals(name);
    }
}
