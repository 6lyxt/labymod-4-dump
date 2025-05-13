// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text.service;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.util.I18n;
import net.labymod.api.labynet.models.service.ServiceStatus;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.labynet.models.service.ServiceDataType;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.labynet.models.service.TwitchServiceData;

@SpriteSlot(x = 4, y = 3)
public class ServiceTwitchHudWidget extends ServiceHudWidget<TwitchServiceData>
{
    private TextLine viewerCountLine;
    
    public ServiceTwitchHudWidget() {
        super(ServiceDataType.TWITCH, 60000);
    }
    
    @Override
    public void load(final TextHudWidgetConfig config) {
        super.load(config);
        this.viewerCountLine = super.createLine("Live Viewers", "?");
        this.onError(ServiceStatus.Status.LOADING);
    }
    
    @Override
    protected void onUpdate(final TwitchServiceData serviceData) {
        if (serviceData.getStream() != null) {
            this.viewerCountLine.updateAndFlush(serviceData.getStream().getViewerCount());
        }
        else {
            this.viewerCountLine.updateAndFlush(I18n.translate("labymod.hudWidget.service_twitch.offline", new Object[0]));
        }
        super.onUpdate(serviceData);
    }
    
    @Override
    protected void onError(final ServiceStatus.Status status) {
        this.viewerCountLine.updateAndFlush(this.getErrorTranslation(status));
        super.onError(status);
    }
}
