// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session.message;

import com.google.gson.JsonObject;
import java.util.function.Consumer;

public class DashboardPinMessageListener implements MessageListener
{
    private final Consumer<String> dashboardPinCallback;
    
    public DashboardPinMessageListener(final Consumer<String> dashboardPinCallback) {
        this.dashboardPinCallback = dashboardPinCallback;
    }
    
    @Override
    public void listen(final String message) {
        final JsonObject jsonObject = (JsonObject)DashboardPinMessageListener.GSON.fromJson(message, (Class)JsonObject.class);
        if (jsonObject.has("pin")) {
            final String pin = jsonObject.get("pin").getAsString();
            if (this.dashboardPinCallback != null) {
                this.dashboardPinCallback.accept(pin);
            }
        }
    }
}
