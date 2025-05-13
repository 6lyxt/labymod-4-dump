// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.component;

import com.google.gson.JsonObject;
import net.labymod.api.event.client.component.ComponentDeserializeEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.KeybindComponent;
import net.labymod.api.event.client.component.ComponentSerializeEvent;

public class VersionedKeybindComponentSerializer
{
    @Subscribe
    public void serializeKeybindComponent(final ComponentSerializeEvent event) {
        if (event.wasSerialized()) {
            return;
        }
        final Component component = event.component();
        if (!(component instanceof KeybindComponent)) {
            return;
        }
        final KeybindComponent keybindComponent = (KeybindComponent)component;
        final String keybind = keybindComponent.getKeybind();
        event.serialize(json -> json.addProperty("keybind", keybind));
    }
    
    @Subscribe
    public void deserializeKeybindComponent(final ComponentDeserializeEvent event) {
        final JsonObject json = event.json();
        if (!json.has("keybind")) {
            return;
        }
        final String keybind = json.get("keybind").getAsString();
        event.setComponent(Component.keybind(keybind));
    }
}
