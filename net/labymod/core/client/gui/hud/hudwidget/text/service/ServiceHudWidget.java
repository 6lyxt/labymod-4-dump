// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text.service;

import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.util.TextFormat;
import net.labymod.api.util.I18n;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.event.Subscribe;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.Laby;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.labynet.models.service.ServiceDataType;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.labynet.models.service.ServiceStatus;

public abstract class ServiceHudWidget<T extends ServiceStatus> extends TextHudWidget<TextHudWidgetConfig>
{
    private final ServiceDataType type;
    private final LabyNetController labyNetController;
    protected final int updateInterval;
    private long timeLastUpdate;
    private ServiceStatus.Status errorStatus;
    
    protected ServiceHudWidget(final ServiceDataType type) {
        this(type, 600000);
    }
    
    protected ServiceHudWidget(final ServiceDataType type, final int updateInterval) {
        super(type.getId());
        this.type = type;
        this.labyNetController = Laby.references().labyNetController();
        this.updateInterval = updateInterval;
        this.bindCategory(HudWidgetCategory.SERVICE);
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        super.onTick(isEditorContext);
        if (this.timeLastUpdate + this.updateInterval >= TimeUtil.getMillis()) {
            return;
        }
        this.timeLastUpdate = TimeUtil.getMillis();
        this.labyNetController.loadServiceData(this.type, result -> {
            if (!(!result.isPresent())) {
                final ServiceStatus status = (ServiceStatus)result.get();
                if (status.getStatus() == ServiceStatus.Status.OK) {
                    this.onUpdate((ServiceStatus)result.get());
                }
                else {
                    this.onError(status.getStatus());
                }
            }
        });
    }
    
    @Subscribe
    public void onLabyConnectStateUpdate(final LabyConnectStateUpdateEvent event) {
        if (event.state() == LabyConnectState.HELLO || event.state() == LabyConnectState.LOGIN) {
            return;
        }
        this.timeLastUpdate = 0L;
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return super.mouseClicked(mouse, mouseButton);
    }
    
    protected void onUpdate(final T serviceData) {
        this.errorStatus = null;
    }
    
    protected void onError(final ServiceStatus.Status status) {
        this.errorStatus = status;
    }
    
    protected String getErrorTranslation(final ServiceStatus.Status status) {
        if (status == ServiceStatus.Status.LOADING) {
            return I18n.getTranslation("labymod.misc.loading", new Object[0]);
        }
        return I18n.translate("labymod.hudWidget.service.status." + TextFormat.SNAKE_CASE.toCamelCase(status.name(), true), new Object[0]);
    }
}
