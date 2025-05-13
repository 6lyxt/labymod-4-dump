// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.translation.game.display;

import java.util.Iterator;
import com.google.gson.JsonObject;
import net.labymod.api.serverapi.LabyModProtocolService;
import net.labymod.serverapi.core.packet.clientbound.game.display.EconomyDisplayPacket;
import net.labymod.serverapi.core.model.display.EconomyDisplay;
import java.util.Map;
import net.labymod.serverapi.api.packet.Packet;
import com.google.gson.JsonElement;
import net.labymod.serverapi.core.LabyModProtocol;
import net.labymod.api.serverapi.KeyedTranslationListener;

public class EconomyTranslationListener extends KeyedTranslationListener
{
    private final LabyModProtocol protocol;
    
    public EconomyTranslationListener(final LabyModProtocol protocol) {
        super("economy");
        this.protocol = protocol;
    }
    
    @Override
    protected Packet translateIncomingMessage(final JsonElement economyElement) {
        if (!economyElement.isJsonObject()) {
            return null;
        }
        EconomyDisplayPacket economyDisplayPacket = null;
        final JsonObject economyObject = economyElement.getAsJsonObject();
        for (final Map.Entry<String, JsonElement> entry : economyObject.entrySet()) {
            final String key = entry.getKey();
            final JsonElement jsonElement = entry.getValue();
            if (!jsonElement.isJsonObject()) {
                continue;
            }
            final JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (!jsonObject.has("visible")) {
                continue;
            }
            if (!jsonObject.has("balance")) {
                continue;
            }
            final boolean visible = jsonObject.get("visible").getAsBoolean();
            final double balance = jsonObject.get("balance").getAsDouble();
            String icon = null;
            if (jsonObject.has("icon")) {
                icon = jsonObject.get("icon").getAsString();
            }
            EconomyDisplay.DecimalFormat decimalFormat = null;
            if (jsonObject.has("decimal") && jsonObject.get("decimal").isJsonObject()) {
                final JsonObject decimalObject = jsonObject.get("decimal").getAsJsonObject();
                if (decimalObject.has("divisor") && decimalObject.has("format")) {
                    final String format = decimalObject.get("format").getAsString();
                    final double divisor = decimalObject.get("divisor").getAsDouble();
                    decimalFormat = new EconomyDisplay.DecimalFormat(format, divisor);
                }
            }
            final EconomyDisplayPacket packet = new EconomyDisplayPacket(new EconomyDisplay(key, visible, balance, icon, decimalFormat));
            if (economyDisplayPacket == null) {
                economyDisplayPacket = packet;
            }
            else {
                this.protocol.handlePacket(LabyModProtocolService.DUMMY_UUID, (Packet)packet);
            }
        }
        return (Packet)economyDisplayPacket;
    }
    
    @Override
    protected JsonElement translateOutgoingMessage(final Packet packet) {
        return null;
    }
}
