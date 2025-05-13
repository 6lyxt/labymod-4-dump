// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.io.ByteArrayInputStream;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import com.google.gson.JsonElement;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketAddonMessage extends Packet
{
    private String key;
    private byte[] data;
    
    public PacketAddonMessage(final String key, final byte[] data) {
        this.key = key;
        this.data = data;
    }
    
    public PacketAddonMessage(final String key, final String json) {
        this.key = key;
        this.data = this.toBytes(json);
    }
    
    public PacketAddonMessage(final String key, final JsonElement element) {
        this(key, element.toString());
    }
    
    public PacketAddonMessage() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.key = buf.readString();
        final byte[] data = new byte[buf.readInt()];
        buf.readBytes(data);
        this.data = data;
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeString(this.key);
        buf.writeInt(this.data.length);
        buf.writeBytes(this.data);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public String getKey() {
        return this.key;
    }
    
    public String getJson() {
        try {
            final StringBuilder outStr = new StringBuilder();
            if (this.data == null || this.data.length == 0) {
                return "";
            }
            if (this.isCompressed(this.data)) {
                final GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(this.data));
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    outStr.append(line);
                }
            }
            else {
                outStr.append(Arrays.toString(this.data));
            }
            return outStr.toString();
        }
        catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private byte[] toBytes(final String in) {
        final byte[] str = in.getBytes(StandardCharsets.UTF_8);
        try {
            if (str.length == 0) {
                return new byte[0];
            }
            final ByteArrayOutputStream obj = new ByteArrayOutputStream();
            final GZIPOutputStream gzip = new GZIPOutputStream(obj);
            gzip.write(str);
            gzip.flush();
            gzip.close();
            return obj.toByteArray();
        }
        catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private boolean isCompressed(final byte[] compressed) {
        return compressed[0] == 31 && compressed[1] == -117;
    }
}
