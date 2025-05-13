// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.packet.protocol;

import org.jetbrains.annotations.Contract;
import net.labymod.serverapi.protocol.payload.io.PayloadWriter;
import java.util.Objects;
import net.labymod.serverapi.protocol.packet.protocol.execption.ProtocolException;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus;
import java.util.Iterator;
import java.util.Optional;
import net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier;
import org.jetbrains.annotations.NotNull;
import net.labymod.serverapi.protocol.api.ProtocolApiBridge;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.ArrayList;
import net.labymod.serverapi.protocol.packet.protocol.neo.NeoProtocol;
import net.labymod.serverapi.protocol.payload.PayloadBridge;
import com.google.gson.Gson;
import net.labymod.serverapi.protocol.payload.translation.AbstractPayloadTranslationListener;
import java.util.List;
import net.labymod.serverapi.protocol.packet.PacketHandler;
import net.labymod.serverapi.protocol.packet.Packet;
import java.util.Map;

@Deprecated(forRemoval = true, since = "4.2.24")
public class ProtocolService
{
    private final ProtocolRegistry protocolRegistry;
    private final ProtocolCodec protocolCodec;
    private final Map<Class<? extends Packet>, PacketHandler<?>> packetHandlers;
    private final List<AbstractPayloadTranslationListener> translationListeners;
    private final Gson gson;
    protected PayloadBridge payloadBridge;
    private NeoProtocol neoProtocol;
    
    public ProtocolService() {
        this.protocolRegistry = new ProtocolRegistry();
        this.protocolCodec = new ProtocolCodec(this.protocolRegistry);
        this.translationListeners = new ArrayList<AbstractPayloadTranslationListener>();
        this.packetHandlers = new HashMap<Class<? extends Packet>, PacketHandler<?>>();
        this.gson = new GsonBuilder().create();
    }
    
    public void initialize() {
        this.neoProtocol = new NeoProtocol();
        this.payloadBridge = ProtocolApiBridge.getProtocolApi().getPayloadBridge();
    }
    
    public void registerAddonProtocol(final String addonId) {
        this.registerAddonProtocol(new AddonProtocol(addonId));
    }
    
    public void registerAddonProtocol(@NotNull final AddonProtocol protocolService) {
        this.protocolRegistry.registerAddonProtocol(protocolService);
    }
    
    public void registerCustomProtocol(@NotNull final Protocol protocol) {
        this.protocolRegistry.registerCustomProtocol(protocol);
    }
    
    public boolean unregisterAddonProtocol(final String addonId) {
        return this.protocolRegistry.unregisterAddonProtocol(addonId);
    }
    
    public boolean unregisterProtocol(final PayloadChannelIdentifier identifier) {
        return this.protocolRegistry.unregisterCustomProtocol(identifier);
    }
    
    public void registerTranslationListener(final AbstractPayloadTranslationListener translationListener) {
        this.translationListeners.add(translationListener);
    }
    
    public <T extends Packet> void registerPacketHandler(final Class<T> packetClass, final PacketHandler<?> packetHandler) {
        if (this.packetHandlers.containsKey(packetClass)) {
            ProtocolApiBridge.getProtocolApi().getPlatformLogger().warn("A handler has already been registered for this packet. ({})", packetClass.getName());
            return;
        }
        this.packetHandlers.put(packetClass, packetHandler);
    }
    
    public <T extends PacketHandler<Packet>> Optional<T> getPacketHandler(final Class<? extends Packet> packetClass) {
        for (final Map.Entry<Class<? extends Packet>, PacketHandler<?>> entry : this.packetHandlers.entrySet()) {
            if (entry.getKey().equals(packetClass)) {
                return Optional.of(entry.getValue());
            }
        }
        return Optional.empty();
    }
    
    @ApiStatus.Internal
    @Nullable
    public <T extends Packet> T readPayload(final PayloadChannelIdentifier identifier, final byte[] payload) {
        return this.decodePayload(identifier, payload, false);
    }
    
    public boolean readCustomPayload(final PayloadChannelIdentifier identifier, final byte[] payload, final boolean shouldTranslate) {
        try {
            final Packet packet = this.decodePayload(identifier, payload, shouldTranslate);
            if (packet == null) {
                return false;
            }
            this.getPacketHandler(packet.getClass()).ifPresent(packetHandler -> packetHandler.handle(packet));
            return true;
        }
        catch (final ProtocolException exception) {
            ProtocolApiBridge.getProtocolApi().getPlatformLogger().error("An error occurred while reading the payload of a custom payload packet.", exception);
            return false;
        }
    }
    
    @Nullable
    private <T extends Packet> T decodePayload(PayloadChannelIdentifier identifier, byte[] payload, final boolean shouldTranslate) {
        if (shouldTranslate) {
            final TranslationData translationData = this.applyTranslationListener(identifier, payload, true);
            identifier = translationData.getIdentifier();
            payload = translationData.getPayload();
        }
        return this.protocolCodec.decode(identifier, payload, (id, binaryPayload) -> {
            if (this.neoProtocol.getIdentifier().equals(id)) {
                return this.neoProtocol.readPacket(binaryPayload);
            }
            else {
                return null;
            }
        });
    }
    
    @Nullable
    private <T extends Packet, P extends Protocol> T readPayload(final PayloadChannelIdentifier identifier, final List<P> protocols, final byte[] payload) {
        for (final P protocol : protocols) {
            if (protocol.getIdentifier().equals(identifier)) {
                return (T)protocol.readPacket(payload);
            }
        }
        return null;
    }
    
    public <T extends Packet> byte[] writePacketToBinary(@NotNull final T packet) {
        final Class<? extends Packet> packetClass = packet.getClass();
        this.neoProtocol.getPacketId(packetClass);
        final ProtocolRegistry protocolRegistry = this.protocolRegistry;
        final Class<? extends Packet> packetClass2 = packetClass;
        final NeoProtocol neoProtocol = this.neoProtocol;
        Objects.requireNonNull(neoProtocol);
        final int packetId = protocolRegistry.findPacketId(packetClass2, neoProtocol::getPacketId);
        if (packetId <= -1) {
            return null;
        }
        final PayloadWriter writer = new PayloadWriter();
        writer.writeVarInt(packetId);
        writer.writeString(this.gson.toJson((Object)packet));
        return writer.toByteArray();
    }
    
    public void sendPacket(PayloadChannelIdentifier identifier, byte[] payload) {
        final TranslationData translationData = this.applyTranslationListener(identifier, payload, false);
        if (translationData.hasTranslation()) {
            this.payloadBridge.sendPayload(identifier, payload);
        }
        identifier = translationData.getIdentifier();
        payload = translationData.getPayload();
        this.payloadBridge.sendPayload(identifier, payload);
    }
    
    public NeoProtocol getNeoProtocol() {
        return this.neoProtocol;
    }
    
    @Nullable
    public <T extends Protocol> T findCustomProtocol(final Class<T> protocolClass) {
        return this.protocolRegistry.findCustomProtocol(protocolClass);
    }
    
    @Nullable
    public <T extends Protocol> T findCustomProtocol(final PayloadChannelIdentifier id) {
        return this.protocolRegistry.findCustomProtocol(id);
    }
    
    @Nullable
    public <T extends AddonProtocol> T findAddonProtocol(final Class<T> addonProtocolClass) {
        return this.protocolRegistry.findAddonProtocol(addonProtocolClass);
    }
    
    @Nullable
    public <T extends AddonProtocol> T findAddonProtocol(final String addonId) {
        return this.protocolRegistry.findAddonProtocol(addonId);
    }
    
    public List<Protocol> getAllProtocols() {
        return this.protocolRegistry.getAllProtocols();
    }
    
    @NotNull
    public List<AddonProtocol> getAddonProtocols() {
        return this.protocolRegistry.getAddonProtocols();
    }
    
    @NotNull
    public List<Protocol> getCustomProtocols() {
        return this.protocolRegistry.getCustomProtocols();
    }
    
    @Deprecated
    public <T extends AddonProtocol> Optional<T> getAddonProtocol(final String addonId) {
        return Optional.ofNullable(this.findAddonProtocol(addonId));
    }
    
    @Deprecated
    public <T extends AddonProtocol> Optional<T> getAddonProtocol(final Class<T> addonProtocolClass) {
        return Optional.ofNullable((T)this.findAddonProtocol((Class<T>)addonProtocolClass));
    }
    
    @Deprecated
    public <T extends Protocol> T getProtocol(final PayloadChannelIdentifier identifier) {
        return (T)this.findCustomProtocol(identifier);
    }
    
    @Deprecated
    public <T extends Protocol> T getProtocol(final Class<T> protocolClass) {
        return (T)this.findCustomProtocol((Class<Protocol>)protocolClass);
    }
    
    @Deprecated
    public <T extends Protocol> Optional<T> getOptionalProtocol(final PayloadChannelIdentifier identifier) {
        final T protocol = this.getProtocol(identifier);
        return (protocol == null) ? Optional.empty() : Optional.of(protocol);
    }
    
    @Deprecated
    public <T extends Protocol> Optional<T> getOptionalProtocol(final Class<T> protocolClass) {
        final T protocol = this.getProtocol(protocolClass);
        return (protocol == null) ? Optional.empty() : Optional.of(protocol);
    }
    
    @Contract("_, _, _ -> new")
    @NotNull
    private TranslationData applyTranslationListener(final PayloadChannelIdentifier identifier, final byte[] payload, final boolean incoming) {
        for (final AbstractPayloadTranslationListener translationListener : this.translationListeners) {
            final PayloadChannelIdentifier oldIdentifier = incoming ? translationListener.getOldIdentifier() : translationListener.getNewIdentifier();
            if (!oldIdentifier.equals(identifier)) {
                continue;
            }
            final byte[] newPayload = incoming ? translationListener.readIncomingPayload(payload) : translationListener.readOutgoingPayload(identifier, payload);
            if (newPayload == null) {
                continue;
            }
            final TranslationData translationData = new TranslationData(incoming ? translationListener.getNewIdentifier() : translationListener.getOldIdentifier(), incoming ? newPayload : translationListener.modifyOutgoingPayload(newPayload));
            translationData.setHasTranslation(true);
            return translationData;
        }
        return new TranslationData(identifier, payload);
    }
    
    private static class TranslationData
    {
        private final PayloadChannelIdentifier identifier;
        private final byte[] payload;
        private boolean hasTranslation;
        
        public TranslationData(@NotNull final PayloadChannelIdentifier identifier, final byte[] payload) {
            this.identifier = identifier;
            this.payload = payload;
            this.hasTranslation = false;
        }
        
        @NotNull
        public PayloadChannelIdentifier getIdentifier() {
            return this.identifier;
        }
        
        public byte[] getPayload() {
            return this.payload;
        }
        
        public boolean hasTranslation() {
            return this.hasTranslation;
        }
        
        public void setHasTranslation(final boolean hasTranslation) {
            this.hasTranslation = hasTranslation;
        }
    }
}
