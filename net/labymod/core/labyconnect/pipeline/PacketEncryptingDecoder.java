// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.pipeline;

import javax.crypto.ShortBufferException;
import java.util.List;
import io.netty.channel.ChannelHandlerContext;
import javax.crypto.Cipher;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.MessageToMessageDecoder;

public class PacketEncryptingDecoder extends MessageToMessageDecoder<ByteBuf>
{
    private final EncryptionTranslator decryptionCodec;
    
    public PacketEncryptingDecoder(final Cipher cipher) {
        this.decryptionCodec = new EncryptionTranslator(cipher);
    }
    
    protected void decode(final ChannelHandlerContext context, final ByteBuf byteBuf, final List<Object> list) throws ShortBufferException, Exception {
        list.add(this.decryptionCodec.decipher(context, byteBuf));
    }
}
