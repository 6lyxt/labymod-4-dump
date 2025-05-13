// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.packet.protocol.neo.translation;

import net.labymod.serverapi.protocol.payload.io.PayloadWriter;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.labymod.serverapi.protocol.payload.exception.PayloadReaderException;
import net.labymod.serverapi.protocol.payload.exception.PayloadException;
import net.labymod.serverapi.protocol.payload.io.PayloadReader;
import net.labymod.serverapi.protocol.packet.protocol.Protocol;
import net.labymod.serverapi.protocol.payload.translation.AbstractPayloadTranslationListener;

@Deprecated(forRemoval = true, since = "4.2.24")
public abstract class AbstractLabyMod3PayloadTranslationListener extends AbstractPayloadTranslationListener
{
    private final String oldMessageKey;
    
    public AbstractLabyMod3PayloadTranslationListener(final Protocol protocol, final String oldMessageKey) {
        super(protocol);
        this.oldMessageKey = oldMessageKey;
    }
    
    @Override
    public byte[] translateIncomingPayload(final byte[] payload) {
        final PayloadReader reader = new PayloadReader(payload);
        try {
            final String messageKey = reader.readString();
            if (!messageKey.equals(this.oldMessageKey)) {
                return null;
            }
        }
        catch (final PayloadReaderException exception) {
            throw new PayloadException("No message key could be read from the buffer.", exception);
        }
        try {
            final String messageContent = reader.readString();
            return this.translateIncomingPayload(new JsonParser().parse(messageContent));
        }
        catch (final PayloadReaderException exception) {
            throw new PayloadException("No message content could be read from the buffer.", exception);
        }
    }
    
    public abstract byte[] translateIncomingPayload(final JsonElement p0);
    
    @Override
    public final byte[] modifyOutgoingPayload(final byte[] payload) {
        final PayloadWriter writer = new PayloadWriter();
        writer.writeString(this.oldMessageKey);
        writer.writeBytes(payload);
        return writer.toByteArray();
    }
}
