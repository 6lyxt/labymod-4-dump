// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.translation.game.moderation;

import java.util.Iterator;
import com.google.gson.JsonObject;
import java.util.List;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.PermissionPacket;
import java.util.Locale;
import java.util.Map;
import net.labymod.serverapi.core.model.moderation.Permission;
import java.util.ArrayList;
import net.labymod.serverapi.api.packet.Packet;
import com.google.gson.JsonElement;
import net.labymod.api.serverapi.KeyedTranslationListener;

public class PermissionTranslationListener extends KeyedTranslationListener
{
    public PermissionTranslationListener() {
        super("PERMISSIONS");
    }
    
    @Override
    protected Packet translateIncomingMessage(final JsonElement messageContent) {
        if (!messageContent.isJsonObject()) {
            return null;
        }
        final JsonObject permissionObject = messageContent.getAsJsonObject();
        final List<Permission.StatedPermission> permissions = new ArrayList<Permission.StatedPermission>();
        for (final Map.Entry<String, JsonElement> entry : permissionObject.entrySet()) {
            String key = entry.getKey();
            if (key != null) {
                if (key.isEmpty()) {
                    continue;
                }
                key = key.toLowerCase(Locale.ENGLISH);
                final Permission permission = Permission.of(key);
                permissions.add(entry.getValue().getAsBoolean() ? permission.allow() : permission.deny());
            }
        }
        return (Packet)new PermissionPacket((List)permissions);
    }
    
    @Override
    protected JsonElement translateOutgoingMessage(final Packet packet) {
        return null;
    }
}
