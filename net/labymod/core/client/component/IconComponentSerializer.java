// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.component;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.labymod.api.event.client.component.ComponentDeserializeEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.event.client.component.ComponentSerializeEvent;

public class IconComponentSerializer
{
    @Subscribe
    public void serializeIconComponent(final ComponentSerializeEvent event) {
        if (event.wasSerialized()) {
            return;
        }
        final Component component = event.component();
        if (!(component instanceof IconComponent)) {
            return;
        }
        final IconComponent iconComponent = (IconComponent)component;
        final Icon icon = iconComponent.getIcon();
        if (icon.getUrl() == null) {
            throw new IllegalArgumentException("Don't know how to serialize icon without a url");
        }
        String url = icon.getUrl();
        if (url.startsWith("labyproxy://")) {
            url = url.replaceFirst("labyproxy://", "http://");
        }
        else if (url.startsWith("labyproxys://")) {
            url = url.replaceFirst("labyproxys://", "https://");
        }
        else if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("data:base64://")) {
            throw new IllegalArgumentException("Don't know how to serialize icon from " + url);
        }
        final String finalUrl = url;
        event.serialize(json -> {
            json.addProperty("icon", finalUrl);
            if (iconComponent.getWidth() != 16) {
                json.addProperty("width", (Number)iconComponent.getWidth());
            }
            if (iconComponent.getHeight() != 16) {
                json.addProperty("height", (Number)iconComponent.getHeight());
            }
            if (!iconComponent.getPlaceholder().isEmpty()) {
                json.addProperty("text", iconComponent.getPlaceholder());
            }
        });
    }
    
    @Subscribe
    public void deserializeIconComponent(final ComponentDeserializeEvent event) {
        final JsonObject json = event.json();
        if (!json.has("icon")) {
            return;
        }
        String url = json.get("icon").getAsString();
        if (url.startsWith("https://")) {
            url = url.replaceFirst("https://", "labyproxys://");
        }
        else if (url.startsWith("http://")) {
            url = url.replaceFirst("http://", "labyproxy://");
        }
        else if (!url.startsWith("data:base64://")) {
            throw new JsonParseException("Illegal icon url for an icon component: " + url);
        }
        final IconComponent component = Component.icon(Icon.url(url));
        if (json.has("width")) {
            try {
                component.setWidth(json.get("width").getAsInt());
            }
            catch (final NumberFormatException exception) {
                throw new JsonParseException("Illegal icon width for an icon component: " + json.get("width").getAsString(), (Throwable)exception);
            }
        }
        if (json.has("height")) {
            try {
                component.setHeight(json.get("height").getAsInt());
            }
            catch (final NumberFormatException exception) {
                throw new JsonParseException("Illegal icon height for an icon component: " + json.get("height").getAsString(), (Throwable)exception);
            }
        }
        if (json.has("text")) {
            component.setPlaceholder(json.get("text").getAsString());
        }
        event.setComponent(component);
    }
}
