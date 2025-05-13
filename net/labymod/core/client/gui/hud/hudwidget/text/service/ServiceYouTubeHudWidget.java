// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text.service;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.labynet.models.service.ServiceStatus;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.labynet.models.service.ServiceDataType;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.labynet.models.service.YouTubeServiceData;

@SpriteSlot(x = 5, y = 3)
public class ServiceYouTubeHudWidget extends ServiceHudWidget<YouTubeServiceData>
{
    private TextLine subscribersLine;
    private TextLine viewsLine;
    
    public ServiceYouTubeHudWidget() {
        super(ServiceDataType.YOUTUBE);
    }
    
    @Override
    public void load(final TextHudWidgetConfig config) {
        super.load(config);
        this.subscribersLine = super.createLine("Subscribers", "?");
        this.viewsLine = super.createLine("Views", "?");
        this.onError(ServiceStatus.Status.LOADING);
    }
    
    @Override
    protected void onUpdate(final YouTubeServiceData serviceData) {
        this.subscribersLine.updateAndFlush(serviceData.getSubscriberCount());
        this.viewsLine.updateAndFlush(serviceData.getViewCount());
        super.onUpdate(serviceData);
    }
    
    @Override
    protected void onError(final ServiceStatus.Status status) {
        this.subscribersLine.updateAndFlush(this.getErrorTranslation(status));
        this.viewsLine.updateAndFlush(this.getErrorTranslation(status));
        super.onError(status);
    }
}
