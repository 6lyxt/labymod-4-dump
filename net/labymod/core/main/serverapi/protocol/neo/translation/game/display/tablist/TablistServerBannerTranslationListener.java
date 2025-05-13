// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.translation.game.display.tablist;

import com.google.gson.JsonObject;
import net.labymod.serverapi.core.packet.clientbound.game.display.TabListBannerPacket;
import net.labymod.serverapi.api.packet.Packet;
import com.google.gson.JsonElement;
import net.labymod.api.serverapi.KeyedTranslationListener;

public class TablistServerBannerTranslationListener extends KeyedTranslationListener
{
    public TablistServerBannerTranslationListener() {
        super("server_banner");
    }
    
    @Override
    protected Packet translateIncomingMessage(final JsonElement messageContent) {
        if (!messageContent.isJsonObject()) {
            return null;
        }
        final JsonObject serverBannerObject = messageContent.getAsJsonObject();
        String imageUrl = null;
        if (serverBannerObject.has("url")) {
            imageUrl = serverBannerObject.get("url").getAsString();
        }
        return (Packet)new TabListBannerPacket(imageUrl);
    }
    
    @Override
    protected JsonElement translateOutgoingMessage(final Packet packet) {
        return null;
    }
}
