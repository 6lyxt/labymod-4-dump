// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session.message;

import net.labymod.api.notification.NotificationController;
import com.google.gson.JsonObject;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import com.google.gson.JsonElement;

public class WebNotificationMessageListener implements MessageListener
{
    @Override
    public void listen(final String packetMessage) {
        final JsonElement element = (JsonElement)WebNotificationMessageListener.GSON.fromJson(packetMessage, (Class)JsonElement.class);
        if (element.isJsonObject()) {
            final JsonObject object = element.getAsJsonObject();
            final String title = object.get("title").getAsString();
            final String message = object.get("message").getAsString();
            if (title == null || title.isEmpty() || message == null || message.isEmpty()) {
                return;
            }
            final Notification.Builder builder = Notification.builder().title(Component.text(title)).text(Component.text(message)).duration(5000L);
            if (object.has("url")) {
                final String url = object.get("url").getAsString();
                if (!url.isEmpty()) {
                    builder.onClick(notification -> Laby.labyAPI().minecraft().chatExecutor().openUrl(url, true));
                }
            }
            if (object.has("icon")) {
                final String iconUrl = object.get("icon").getAsString();
                if (!iconUrl.isEmpty()) {
                    builder.icon(Icon.url(iconUrl));
                }
                if (object.has("secondaryIcon")) {
                    final String secondaryIconUrl = object.get("secondaryIcon").getAsString();
                    if (!secondaryIconUrl.isEmpty()) {
                        builder.secondaryIcon(Icon.url(secondaryIconUrl));
                    }
                }
            }
            ThreadSafe.executeOnRenderThread(() -> {
                final NotificationController notificationController = Laby.labyAPI().notificationController();
                notificationController.push(builder.build());
            });
        }
    }
}
