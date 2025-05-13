// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.screenshot;

import java.util.List;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.models.OperatingSystem;
import java.util.Objects;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.screenshots.ScreenshotBrowserActivity;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import net.labymod.core.client.screenshot.meta.ScreenshotMeta;
import net.labymod.api.Laby;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.misc.WriteScreenshotEvent;
import java.util.concurrent.Executors;
import java.nio.file.Path;
import java.util.concurrent.Executor;

public class ScreenshotNotificationListener
{
    private final Executor executor;
    private Path lastScreenshot;
    
    public ScreenshotNotificationListener() {
        this.executor = Executors.newSingleThreadExecutor();
    }
    
    @Subscribe
    public void onWriteScreenshot(final WriteScreenshotEvent event) {
        if (event.getPhase() != Phase.POST) {
            return;
        }
        this.lastScreenshot = event.getDestination().toPath();
        if (!Laby.labyAPI().config().notifications().screenshot().get()) {
            return;
        }
        this.executor.execute(() -> {
            try {
                final Path path = event.getDestination().toPath();
                final ScreenshotMeta meta = new ScreenshotMeta(path);
                final Screenshot screenshot = new Screenshot(path, meta);
                screenshot.updateQuality(Screenshot.QualityType.HIGH);
                final String i18n = "labymod.activity.screenshotBrowser.";
                final Notification.Builder builder = Notification.builder();
                builder.title(Component.translatable(i18n + "save.title", new Component[0])).thumbnail(screenshot.getIcon()).onClick(notification -> ScreenshotBrowserActivity.openScreenshot(this.lastScreenshot));
                Component.translatable(i18n + "context.open", new Component[0]);
                final Object obj;
                Objects.requireNonNull(obj);
                final Notification.Builder builder2;
                final Component text;
                builder2.addButton(Notification.NotificationButton.of(text, obj::openInSystem));
                if (OperatingSystem.getPlatform() == OperatingSystem.WINDOWS) {
                    Component.translatable(i18n + "context.copy", new Component[0]);
                    final Object obj2;
                    Objects.requireNonNull(obj2);
                    final Notification.Builder builder3;
                    final Component text2;
                    builder3.addButton(Notification.NotificationButton.of(text2, obj2::copyToClipboard));
                }
                builder.addButton(Notification.NotificationButton.of(Component.translatable(i18n + "context.upload", new Component[0]), () -> screenshot.upload().thenAccept(url -> {
                    if (url != null) {
                        OperatingSystem.getPlatform().openUrl(url);
                        Laby.labyAPI().minecraft().chatExecutor().copyToClipboard(url);
                    }
                })));
                Component.translatable(i18n + "context.delete", new Component[0]);
                final Object obj3;
                Objects.requireNonNull(obj3);
                final Notification.Builder builder4;
                final Component text3;
                builder4.addButton(Notification.NotificationButton.of(text3, obj3::delete)).build();
                Laby.references().notificationController().push(builder.build());
            }
            catch (final Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    @Subscribe
    public void onChat(final ChatReceiveEvent event) {
        final Component message = event.message();
        if (!(message instanceof TranslatableComponent)) {
            return;
        }
        final TranslatableComponent translatableComponent = (TranslatableComponent)message;
        if (!"screenshot.success".equals(translatableComponent.getKey())) {
            return;
        }
        if (this.lastScreenshot == null) {
            return;
        }
        final List<Component> arguments = translatableComponent.getArguments();
        if (arguments.isEmpty()) {
            return;
        }
        final Component argument = arguments.get(0);
        argument.clickEvent(ClickEvent.runCommand("/screenshot-viewer " + String.valueOf(this.lastScreenshot)));
        event.setMessage(message);
    }
}
