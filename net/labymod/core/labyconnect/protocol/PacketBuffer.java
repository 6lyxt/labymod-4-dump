// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol;

import net.labymod.core.labyconnect.protocol.model.request.DefaultIncomingFriendRequest;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultServerInfo;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;
import net.labymod.core.labyconnect.protocol.model.DefaultUser;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import io.netty.buffer.ByteBuf;

public class PacketBuffer
{
    private final ByteBuf buffer;
    
    public PacketBuffer(final ByteBuf buffer) {
        this.buffer = buffer;
    }
    
    public int readVarIntFromBuffer() {
        int var1 = 0;
        int var2 = 0;
        byte var3;
        do {
            var3 = this.buffer.readByte();
            var1 |= (var3 & 0x7F) << var2++ * 7;
            if (var2 > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while ((var3 & 0x80) == 0x80);
        return var1;
    }
    
    public void writeVarIntToBuffer(final int input) {
        writeVarIntToBuffer(this.buffer, input);
    }
    
    public byte[] readByteArray() {
        final byte[] b = new byte[this.buffer.readInt()];
        for (int i = 0; i < b.length; ++i) {
            b[i] = this.buffer.readByte();
        }
        return b;
    }
    
    public void writeByteArray(final byte[] data) {
        this.buffer.writeInt(data.length);
        this.buffer.writeBytes(data);
    }
    
    public void writeByte(final int value) {
        this.buffer.writeByte(value);
    }
    
    public byte readByte() {
        return this.buffer.readByte();
    }
    
    public UUID readUUID() {
        return UUID.fromString(this.readString());
    }
    
    public void writeUUID(final UUID uuid) {
        this.writeString(uuid.toString());
    }
    
    public void readBytes(final byte[] data) {
        this.buffer.readBytes(data);
    }
    
    public void writeBytes(final byte[] data) {
        this.buffer.writeBytes(data);
    }
    
    public short readShort() {
        return this.buffer.readShort();
    }
    
    public boolean readBoolean() {
        return this.buffer.readBoolean();
    }
    
    public int readInt() {
        return this.buffer.readInt();
    }
    
    public long readLong() {
        return this.buffer.readLong();
    }
    
    public float readFloat() {
        return this.buffer.readFloat();
    }
    
    public double readDouble() {
        return this.buffer.readDouble();
    }
    
    public void writeShort(final short value) {
        this.buffer.writeShort((int)value);
    }
    
    public void writeBoolean(final boolean value) {
        this.buffer.writeBoolean(value);
    }
    
    public void writeInt(final int value) {
        this.buffer.writeInt(value);
    }
    
    public void writeLong(final long value) {
        this.buffer.writeLong(value);
    }
    
    public void writeFloat(final float value) {
        this.buffer.writeFloat(value);
    }
    
    public void writeDouble(final double value) {
        this.buffer.writeDouble(value);
    }
    
    public String readString() {
        final byte[] a = new byte[this.buffer.readInt()];
        for (int i = 0; i < a.length; ++i) {
            a[i] = this.buffer.readByte();
        }
        return new String(a, StandardCharsets.UTF_8);
    }
    
    public void writeString(final String string) {
        this.buffer.writeInt(string.getBytes(StandardCharsets.UTF_8).length);
        this.buffer.writeBytes(string.getBytes(StandardCharsets.UTF_8));
    }
    
    public void writeUser(final DefaultUser user) {
        this.writeString(user.getName());
        this.writeUUID(user.getUniqueId());
        if (user instanceof final DefaultFriend friend) {
            this.writeString(friend.getStatusMessage());
            this.writeUserStatus(friend.userStatus());
            this.writeBoolean(friend.isFriendRequest());
            this.writeString(friend.getTimeZone());
            this.writeInt(friend.getContactAmount());
            this.writeLong(friend.getLastOnline());
            this.writeLong(friend.getFirstJoined());
            this.writeServerInfo((DefaultServerInfo)friend.getServer());
        }
        else {
            this.writeString("");
            this.writeUserStatus(user.userStatus());
            this.writeBoolean(false);
            this.writeString("");
            this.writeInt(0);
            this.writeLong(TimeUtil.getCurrentTimeMillis());
            this.writeLong(0L);
            this.writeServerInfo(new DefaultServerInfo("", 0));
        }
    }
    
    public void writeServerInfo(DefaultServerInfo info) {
        if (info == null) {
            info = new DefaultServerInfo("", 0);
        }
        this.writeString((info.getAddress() == null) ? "" : info.getAddress());
        this.writeInt(info.getPort());
        if (info.getGameModeName() != null) {
            this.writeBoolean(true);
            this.writeString(info.getGameModeName());
        }
        else {
            this.writeBoolean(false);
        }
    }
    
    public void writeUserStatus(final UserStatus status) {
        this.buffer.writeByte((int)status.getId());
    }
    
    public DefaultFriend readChatUser() {
        final String username = this.readString();
        final UUID uuid = this.readUUID();
        final String statusMessage = this.readString();
        final UserStatus status = this.readUserStatus();
        final boolean request = this.readBoolean();
        final String timeZone = this.readString();
        final int contactsAmound = this.readInt();
        final long lastOnline = this.readLong();
        final long firstJoined = this.readLong();
        final DefaultServerInfo serverInfo = this.readServerInfo();
        if (request) {
            return new DefaultIncomingFriendRequest(uuid, username);
        }
        return new DefaultFriend(uuid, username, status, statusMessage, serverInfo, 0, TimeUtil.getCurrentTimeMillis(), 0L, timeZone, lastOnline, firstJoined, contactsAmound, false);
    }
    
    public DefaultServerInfo readServerInfo() {
        final String serverIp = this.readString();
        final int serverPort = this.readInt();
        if (this.readBoolean()) {
            return new DefaultServerInfo(serverIp, serverPort, this.readString());
        }
        return new DefaultServerInfo(serverIp, serverPort);
    }
    
    public UserStatus readUserStatus() {
        return UserStatus.getById(this.buffer.readByte());
    }
    
    public ByteBuf getBuffer() {
        return this.buffer;
    }
    
    public static int getVarIntSize(final int input) {
        for (int var1 = 1; var1 < 5; ++var1) {
            if ((input & -1 << var1 * 7) == 0x0) {
                return var1;
            }
        }
        return 5;
    }
    
    public static void writeVarIntToBuffer(final ByteBuf buf, int input) {
        while ((input & 0xFFFFFF80) != 0x0) {
            buf.writeByte((input & 0x7F) | 0x80);
            input >>>= 7;
        }
        buf.writeByte(input);
    }
}
