// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.serverapi;

import net.labymod.serverapi.api.payload.io.PayloadWriter;
import com.google.gson.JsonElement;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import net.labymod.api.util.GsonUtil;
import com.google.gson.Gson;

public abstract class KeyedTranslationListener extends TranslationListener
{
    private final String key;
    protected final Gson gson;
    
    protected KeyedTranslationListener(final String key) {
        this(key, GsonUtil.DEFAULT_GSON);
    }
    
    protected KeyedTranslationListener(final String key, final Gson gson) {
        this.key = key;
        this.gson = gson;
    }
    
    @Override
    public final Packet translateIncomingPayload(final PayloadReader reader) {
        if (!reader.readString().equals(this.key)) {
            return null;
        }
        return this.translateIncomingMessage((JsonElement)this.gson.fromJson(reader.readString(), (Class)JsonElement.class));
    }
    
    @Override
    public final PayloadWriter translateOutgoingPayload(final Packet packet) {
        final JsonElement jsonElement = this.translateOutgoingMessage(packet);
        if (jsonElement == null) {
            return null;
        }
        final PayloadWriter writer = new PayloadWriter();
        writer.writeString(this.key);
        writer.writeString(this.gson.toJson(jsonElement));
        return writer;
    }
    
    protected abstract Packet translateIncomingMessage(final JsonElement p0);
    
    protected abstract JsonElement translateOutgoingMessage(final Packet p0);
}
