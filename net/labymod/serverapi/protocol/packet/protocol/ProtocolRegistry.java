// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.packet.protocol;

import org.jetbrains.annotations.Nullable;
import java.util.function.Predicate;
import net.labymod.serverapi.protocol.api.ProtocolApiBridge;
import java.util.Collections;
import java.util.Iterator;
import net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier;
import net.labymod.serverapi.protocol.packet.Packet;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

@Deprecated(forRemoval = true, since = "4.2.24")
public class ProtocolRegistry
{
    public static final int UNKNOWN_PACKET_ID = -1;
    private final List<PacketIdLookup> packetIdLookups;
    private final List<Protocol> allProtocols;
    private final List<Protocol> customProtocols;
    private final List<AddonProtocol> addonProtocols;
    
    public ProtocolRegistry() {
        this.packetIdLookups = new ArrayList<PacketIdLookup>();
        this.allProtocols = new ArrayList<Protocol>();
        this.customProtocols = new ArrayList<Protocol>();
        this.addonProtocols = new ArrayList<AddonProtocol>();
        this.packetIdLookups.add(packetClass -> this.findPacketId(this.addonProtocols, (Class<Packet>)packetClass));
        this.packetIdLookups.add(packetClass -> this.findPacketId(this.customProtocols, (Class<Packet>)packetClass));
    }
    
    public void registerAddonProtocol(final AddonProtocol protocol) {
        if (this.isProtocolRegistered(this.addonProtocols, protocol)) {
            this.warn("AddonProtocol with identifier {} is already registered", protocol.getIdentifier());
            return;
        }
        this.registerProtocol(protocol);
    }
    
    public boolean unregisterAddonProtocol(final String addonId) {
        return this.addonProtocols.removeIf(protocol -> {
            if (protocol.getAddonId().equals(addonId)) {
                this.allProtocols.remove(protocol);
                return true;
            }
            else {
                return false;
            }
        });
    }
    
    public void registerCustomProtocol(final Protocol protocol) {
        if (this.isProtocolRegistered(this.customProtocols, protocol)) {
            this.warn("Protocol with identifier {} is already registered", protocol.getIdentifier());
            return;
        }
        this.registerProtocol(protocol);
    }
    
    public boolean unregisterCustomProtocol(final PayloadChannelIdentifier id) {
        return this.customProtocols.removeIf(protocol -> {
            if (protocol.getIdentifier().equals(id)) {
                this.allProtocols.remove(protocol);
                return true;
            }
            else {
                return false;
            }
        });
    }
    
    public <T extends AddonProtocol> T findAddonProtocol(final String addonId) {
        return this.findProtocol((Collection<T>)this.addonProtocols, protocol -> protocol.getAddonId().equals(addonId));
    }
    
    public <T extends AddonProtocol> T findAddonProtocol(final Class<T> addonProtocolClass) {
        return this.findProtocol((Collection<T>)this.addonProtocols, protocol -> protocol.getClass().equals(addonProtocolClass));
    }
    
    public <T extends Protocol> T findCustomProtocol(final PayloadChannelIdentifier id) {
        return this.findProtocol((Collection<T>)this.customProtocols, protocol -> protocol.getIdentifier().equals(id));
    }
    
    public <T extends Protocol> T findCustomProtocol(final Class<T> protocolClass) {
        return this.findProtocol((Collection<T>)this.customProtocols, protocol -> protocol.getClass().equals(protocolClass));
    }
    
    public <P extends Packet> int findPacketId(final Class<P> packetClass, final PacketIdLookup defaultLookup) {
        int packetId = defaultLookup.lookup(packetClass);
        if (packetId == -1) {
            for (final PacketIdLookup lookup : this.packetIdLookups) {
                packetId = lookup.lookup(packetClass);
                if (packetId > -1) {
                    break;
                }
            }
        }
        return packetId;
    }
    
    public List<Protocol> getAllProtocols() {
        return Collections.unmodifiableList((List<? extends Protocol>)this.allProtocols);
    }
    
    public List<Protocol> getCustomProtocols() {
        return Collections.unmodifiableList((List<? extends Protocol>)this.customProtocols);
    }
    
    public List<AddonProtocol> getAddonProtocols() {
        return Collections.unmodifiableList((List<? extends AddonProtocol>)this.addonProtocols);
    }
    
    private boolean isProtocolRegistered(final Collection<? extends Protocol> protocols, final Protocol protocol) {
        return this.isProtocolRegistered(protocols, protocol.getIdentifier());
    }
    
    private boolean isProtocolRegistered(final Collection<? extends Protocol> protocols, final PayloadChannelIdentifier id) {
        for (final Protocol protocol : protocols) {
            if (protocol.getIdentifier().equals(id)) {
                return true;
            }
        }
        return false;
    }
    
    private void warn(final String message, final Object... args) {
        ProtocolApiBridge.getProtocolApi().getPlatformLogger().warn(message, args);
    }
    
    private <PACKET extends Packet, PROTOCOL extends Protocol> PACKET decode(final Collection<PROTOCOL> protocols, final PayloadChannelIdentifier id, final byte[] binaryPayload) {
        for (final PROTOCOL protocol : protocols) {
            if (protocol.getIdentifier().equals(id)) {
                return (PACKET)protocol.readPacket(binaryPayload);
            }
        }
        return null;
    }
    
    private <P extends Packet> int findAddonPacketId(final Class<P> packetClass) {
        return this.findPacketId(this.addonProtocols, packetClass);
    }
    
    private <P extends Packet> int findCustomPacketId(final Class<P> packetClass) {
        return this.findPacketId(this.customProtocols, packetClass);
    }
    
    @Nullable
    private <T extends Protocol> T findProtocol(final Collection<T> protocols, final Predicate<T> filter) {
        for (final T protocol : protocols) {
            if (filter.test(protocol)) {
                return protocol;
            }
        }
        return null;
    }
    
    private <PACKET extends Packet, PROTOCOL extends Protocol> int findPacketId(final Collection<PROTOCOL> protocols, final Class<PACKET> packetClass) {
        for (final PROTOCOL protocol : protocols) {
            final int packetId = protocol.getPacketId(packetClass);
            if (packetId >= 0) {
                return packetId;
            }
        }
        return -1;
    }
    
    private void registerProtocol(final Protocol protocol) {
        if (protocol instanceof final AddonProtocol addonProtocol) {
            this.addonProtocols.add(addonProtocol);
        }
        else {
            this.customProtocols.add(protocol);
        }
        this.allProtocols.add(protocol);
    }
    
    @FunctionalInterface
    public interface PacketIdLookup
    {
        int lookup(final Class<? extends Packet> p0);
    }
}
