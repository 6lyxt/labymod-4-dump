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
import net.labymod.api.labynet.models.service.TikTokServiceData;

@SpriteSlot(x = 6, y = 3)
public class ServiceTikTokHudWidget extends ServiceHudWidget<TikTokServiceData>
{
    private TextLine followersLine;
    private TextLine likesLine;
    
    public ServiceTikTokHudWidget() {
        super(ServiceDataType.TIKTOK);
    }
    
    @Override
    public void load(final TextHudWidgetConfig config) {
        super.load(config);
        this.followersLine = super.createLine("Followers", "?");
        this.likesLine = super.createLine("Likes", "?");
        this.onError(ServiceStatus.Status.LOADING);
    }
    
    @Override
    protected void onUpdate(final TikTokServiceData serviceData) {
        this.followersLine.updateAndFlush(serviceData.getFollowerCount());
        this.likesLine.updateAndFlush(serviceData.getLikesCount());
        super.onUpdate(serviceData);
    }
    
    @Override
    protected void onError(final ServiceStatus.Status status) {
        this.followersLine.updateAndFlush(this.getErrorTranslation(status));
        this.likesLine.updateAndFlush(this.getErrorTranslation(status));
        super.onError(status);
    }
}
