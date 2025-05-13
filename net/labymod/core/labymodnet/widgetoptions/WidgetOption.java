// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.widgetoptions;

import net.labymod.core.main.LabyMod;
import net.labymod.api.util.Debounce;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import net.labymod.api.Laby;
import net.labymod.core.labymodnet.models.ChangeResponse;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.core.labymodnet.LabyModNetService;
import net.labymod.api.util.logging.Logging;

public abstract class WidgetOption
{
    protected static final Logging LOGGER;
    protected static final LabyModNetService LABYMOD_NET_SERVICE;
    protected final String optionName;
    protected final int optionIndex;
    protected Cosmetic cosmetic;
    protected String translationKeyPrefix;
    protected String dataAtStart;
    protected CosmeticSettingsWidget.CosmeticSettingsListener listener;
    
    protected WidgetOption(final String optionName, final int optionIndex) {
        this.optionName = optionName;
        this.optionIndex = optionIndex;
    }
    
    public static void handleUpdateResponse(final ChangeResponse changeResponse) {
        if (changeResponse != null && changeResponse.isDone()) {
            return;
        }
        final LabyAPI labyAPI = Laby.labyAPI();
        labyAPI.minecraft().executeOnRenderThread(() -> labyAPI.notificationController().push(Notification.builder().title(Component.translatable("labymod.misc.error", new Component[0])).text(Component.translatable("labymod.activity.customization.cosmetics.updateFailed", new Component[0])).duration(3000L).build()));
    }
    
    public void begin(final Cosmetic cosmetic, final CosmeticSettingsWidget.CosmeticSettingsListener listener, final String translationKeyPrefix) {
        this.cosmetic = cosmetic;
        this.listener = listener;
        this.translationKeyPrefix = translationKeyPrefix;
        this.dataAtStart = this.cosmetic.getData()[this.optionIndex];
    }
    
    public final void create(final Consumer<Widget> consumer) {
        try {
            this.create(this.cosmetic.getData()[this.optionIndex], consumer);
        }
        catch (final Exception exception) {
            WidgetOption.LOGGER.error("Failed to create widget option " + this.optionName, (Throwable)exception);
        }
    }
    
    protected abstract void create(final String p0, final Consumer<Widget> p1);
    
    protected void debounce(final String id, final Runnable runnable) {
        final long debounceLength = this.getDebounceLength();
        if (debounceLength == 0L) {
            runnable.run();
            return;
        }
        Debounce.of(id + this.cosmetic.getItemId(), debounceLength, runnable);
    }
    
    protected void setData(final String value) {
        this.cosmetic.getData()[this.optionIndex] = value;
    }
    
    protected void setData(final String debounceId, final String value) {
        this.setData(value);
        if (this.listener != null) {
            this.listener.onDataUpdate(this.cosmetic);
        }
        if (this.listener == null || !this.listener.shouldSendRemoteRequest()) {
            return;
        }
        this.debounce(debounceId, () -> {
            if (!value.equals(this.dataAtStart)) {
                this.dataAtStart = value;
                WidgetOption.LABYMOD_NET_SERVICE.updateCosmetic(this.cosmetic, WidgetOption::handleUpdateResponse);
            }
        });
    }
    
    protected long getDebounceLength() {
        return 1000L;
    }
    
    static {
        LOGGER = Logging.create(WidgetOption.class);
        LABYMOD_NET_SERVICE = LabyMod.references().labyModNetService();
    }
    
    public enum Type
    {
        COLOR, 
        SLIDER, 
        DROPDOWN, 
        TOGGLE;
    }
}
