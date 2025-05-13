// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi;

import net.labymod.core.main.serverapi.legacy.LabyModPayloadBridge;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import net.labymod.serverapi.api.model.component.ServerAPITextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.serverapi.api.model.component.ServerAPITextDecoration;
import java.util.Map;
import net.labymod.serverapi.api.model.component.ServerAPITextComponent;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.Component;
import net.labymod.serverapi.api.model.component.ServerAPIComponent;
import java.util.Iterator;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.supplement.ServerSwitchTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.supplement.InputPromptTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.moderation.PermissionTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.display.tablist.TablistLanguageFlagTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.display.tablist.TablistServerBannerTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.display.EconomyTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.moderation.AddonRecommendationTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.feature.PlayingGameModeTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.feature.MarkerTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.feature.InteractionMenuApiTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.feature.EmoteApiTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.display.SubtitleTranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.game.discord.DiscordRPCTranslationListener;
import net.labymod.api.serverapi.TranslationListener;
import net.labymod.core.main.serverapi.protocol.neo.translation.login.LoginTranslationListener;
import net.labymod.serverapi.api.Protocol;
import net.labymod.api.serverapi.TranslationProtocol;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation.InstalledAddonsRequestPacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.InstalledAddonsRequestPacket;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation.AddonDisablePacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.AddonDisablePacket;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.display.EconomyDisplayPacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.display.EconomyDisplayPacket;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation.AddonRecommendationPacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.AddonRecommendationPacket;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.feature.PlayingGameModePacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.feature.PlayingGameModePacket;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.supplement.ServerSwitchPromptPacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.supplement.ServerSwitchPromptPacket;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.supplement.InputPromptPacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.supplement.InputPromptPacket;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.feature.AddMarkerPacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.feature.marker.AddMarkerPacket;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.feature.MarkerPacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.feature.marker.MarkerPacket;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation.PermissionPacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.PermissionPacket;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.feature.InteractionMenuPacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.feature.InteractionMenuPacket;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.feature.EmotePacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.feature.EmotePacket;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.display.tablist.TabListFlagPacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.display.TabListFlagPacket;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.display.tablist.TabListBannerPacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.display.TabListBannerPacket;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.display.SubtitlePacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.display.SubtitlePacket;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.discord.DiscordRPCPacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.feature.DiscordRPCPacket;
import net.labymod.serverapi.api.payload.io.PayloadWriter;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.api.client.network.server.payload.PayloadRegistry;
import net.labymod.api.Laby;
import net.labymod.api.serverapi.PayloadTranslationRegistry;
import net.labymod.core.main.serverapi.payload.LabyModPayloadChannelIdentifierSerializer;
import net.labymod.serverapi.api.logger.ProtocolPlatformLogger;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.serverapi.LabyModProtocolService;

@Singleton
@Implements(LabyModProtocolService.class)
public class DefaultLabyModProtocolService extends LabyModProtocolService
{
    private final ProtocolPlatformLogger logger;
    private final LabyModPayloadChannelIdentifierSerializer payloadChannelIdentifierSerializer;
    private final PayloadTranslationRegistry translationRegistry;
    
    public DefaultLabyModProtocolService() {
        this.logger = (ProtocolPlatformLogger)new LabyModProtocolPlatformLogger();
        this.payloadChannelIdentifierSerializer = new LabyModPayloadChannelIdentifierSerializer();
        this.translationRegistry = new DefaultPayloadTranslationRegistry();
        this.initializeLabyModNeoProtocol();
        final PayloadRegistry payloadRegistry = Laby.references().payloadRegistry();
        payloadRegistry.registerPayloadChannel(PayloadRegistry.MINECRAFT_REGISTER);
        payloadRegistry.registerPayloadChannel(PayloadRegistry.MINECRAFT_UNREGISTER);
        this.registry().addRegisterListener(protocol -> Laby.references().payloadRegistry().registerPayloadChannel(protocol.identifier()));
    }
    
    public void send(@NotNull final PayloadChannelIdentifier payloadChannelIdentifier, @NotNull final UUID uuid, @NotNull final PayloadWriter payloadWriter) {
        Laby.labyAPI().serverController().sendPayload(this.payloadChannelIdentifierSerializer.serialize(payloadChannelIdentifier), payloadWriter.toByteArray());
    }
    
    @NotNull
    public ProtocolPlatformLogger logger() {
        return this.logger;
    }
    
    @NotNull
    @Override
    public PayloadTranslationRegistry translationRegistry() {
        return this.translationRegistry;
    }
    
    public boolean isInitialized() {
        return true;
    }
    
    private void initializeLabyModNeoProtocol() {
        this.registerHandler((Class<Packet>)DiscordRPCPacket.class, (net.labymod.serverapi.api.packet.PacketHandler<Packet>)new DiscordRPCPacketHandler());
        this.registerHandler((Class<Packet>)SubtitlePacket.class, (net.labymod.serverapi.api.packet.PacketHandler<Packet>)new SubtitlePacketHandler());
        this.registerHandler((Class<Packet>)TabListBannerPacket.class, (net.labymod.serverapi.api.packet.PacketHandler<Packet>)new TabListBannerPacketHandler());
        this.registerHandler((Class<Packet>)TabListFlagPacket.class, (net.labymod.serverapi.api.packet.PacketHandler<Packet>)new TabListFlagPacketHandler());
        this.registerHandler((Class<Packet>)EmotePacket.class, (net.labymod.serverapi.api.packet.PacketHandler<Packet>)new EmotePacketHandler());
        this.registerHandler((Class<Packet>)InteractionMenuPacket.class, (net.labymod.serverapi.api.packet.PacketHandler<Packet>)new InteractionMenuPacketHandler());
        this.registerHandler((Class<Packet>)PermissionPacket.class, (net.labymod.serverapi.api.packet.PacketHandler<Packet>)new PermissionPacketHandler());
        this.registerHandler((Class<Packet>)MarkerPacket.class, (net.labymod.serverapi.api.packet.PacketHandler<Packet>)new MarkerPacketHandler());
        this.registerHandler((Class<Packet>)AddMarkerPacket.class, (net.labymod.serverapi.api.packet.PacketHandler<Packet>)new AddMarkerPacketHandler());
        this.registerHandler((Class<Packet>)InputPromptPacket.class, (net.labymod.serverapi.api.packet.PacketHandler<Packet>)new InputPromptPacketHandler());
        this.registerHandler((Class<Packet>)ServerSwitchPromptPacket.class, (net.labymod.serverapi.api.packet.PacketHandler<Packet>)new ServerSwitchPromptPacketHandler());
        this.registerHandler((Class<Packet>)PlayingGameModePacket.class, (net.labymod.serverapi.api.packet.PacketHandler<Packet>)new PlayingGameModePacketHandler());
        this.registerHandler((Class<Packet>)AddonRecommendationPacket.class, (net.labymod.serverapi.api.packet.PacketHandler<Packet>)new AddonRecommendationPacketHandler());
        this.registerHandler((Class<Packet>)EconomyDisplayPacket.class, (net.labymod.serverapi.api.packet.PacketHandler<Packet>)new EconomyDisplayPacketHandler());
        this.registerHandler((Class<Packet>)AddonDisablePacket.class, (net.labymod.serverapi.api.packet.PacketHandler<Packet>)new AddonDisablePacketHandler());
        this.registerHandler((Class<Packet>)InstalledAddonsRequestPacket.class, (net.labymod.serverapi.api.packet.PacketHandler<Packet>)new InstalledAddonsRequestPacketHandler());
        final TranslationProtocol protocol = new TranslationProtocol(PayloadChannelIdentifier.create("labymod3", "main"), (Protocol)this.labyModProtocol);
        this.translationRegistry.register(protocol);
        protocol.registerListener(new LoginTranslationListener());
        protocol.registerListener(new DiscordRPCTranslationListener());
        protocol.registerListener(new SubtitleTranslationListener());
        protocol.registerListener(new EmoteApiTranslationListener());
        protocol.registerListener(new InteractionMenuApiTranslationListener());
        protocol.registerListener(new MarkerTranslationListener());
        protocol.registerListener(new PlayingGameModeTranslationListener());
        protocol.registerListener(new AddonRecommendationTranslationListener());
        protocol.registerListener(new EconomyTranslationListener(this.labyModProtocol));
        protocol.registerListener(new TablistServerBannerTranslationListener());
        protocol.registerListener(new TablistLanguageFlagTranslationListener());
        protocol.registerListener(new PermissionTranslationListener());
        protocol.registerListener(new InputPromptTranslationListener());
        protocol.registerListener(new ServerSwitchTranslationListener());
    }
    
    private <T extends Packet> void registerHandler(@NotNull final Class<T> packetClass, @NotNull final PacketHandler<T> packetHandler) {
        this.labyModProtocol.registerHandler((Class)packetClass, (PacketHandler)packetHandler);
    }
    
    public void afterPacketSent(@NotNull final Protocol protocol, @NotNull final Packet packet, @NotNull final UUID recipient) {
        for (final TranslationProtocol translationProtocol : this.translationRegistry.getProtocols()) {
            if (!translationProtocol.targetProtocol().equals(protocol)) {
                continue;
            }
            translationProtocol.forEachListener(listener -> {
                try {
                    final PayloadWriter writer = listener.translateOutgoingPayload(packet);
                    if (writer != null) {
                        this.send(translationProtocol.identifier(), recipient, writer);
                        return true;
                    }
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
                return false;
            });
        }
    }
    
    @Override
    public Component mapComponent(final ServerAPIComponent serverAPIComponent) {
        if (serverAPIComponent == null) {
            return null;
        }
        final ServerAPITextColor color = serverAPIComponent.getColor();
        final TextColor textColor = (color == null) ? null : TextColor.color(color.getValue());
        Component component;
        if (serverAPIComponent instanceof final ServerAPITextComponent textComponent) {
            final String text = textComponent.getText();
            component = (text.isEmpty() ? Component.empty() : Component.text(text));
        }
        else {
            component = Component.empty();
        }
        if (textColor != null) {
            component = component.color(textColor);
        }
        for (final Map.Entry<ServerAPITextDecoration, Boolean> entry : serverAPIComponent.getDecorations().entrySet()) {
            final TextDecoration textDecoration = switch (entry.getKey()) {
                default -> throw new MatchException(null, null);
                case BOLD -> TextDecoration.BOLD;
                case ITALIC -> TextDecoration.ITALIC;
                case UNDERLINED -> TextDecoration.UNDERLINED;
                case STRIKETHROUGH -> TextDecoration.STRIKETHROUGH;
                case OBFUSCATED -> TextDecoration.OBFUSCATED;
            };
            final Boolean state = entry.getValue();
            if (state == null) {
                component.unsetDecoration(textDecoration);
            }
            else if (state) {
                component.decorate(textDecoration);
            }
            else {
                component.undecorate(textDecoration);
            }
        }
        for (final ServerAPIComponent child : serverAPIComponent.getChildren()) {
            component.append(this.mapComponent(child));
        }
        return component;
    }
    
    @Override
    public boolean readPayload(final PayloadChannelIdentifier identifier, final byte[] payload) {
        boolean handled = false;
        final Iterator iterator = this.registry().getProtocols().iterator();
        Protocol protocol = null;
        while (iterator.hasNext()) {
            protocol = (Protocol)iterator.next();
            if (protocol.identifier().equals((Object)identifier)) {
                try {
                    protocol.handleIncomingPayload(DefaultLabyModProtocolService.DUMMY_UUID, new PayloadReader(payload));
                    handled = true;
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }
        final PayloadReader reader = new PayloadReader(payload);
        for (final TranslationProtocol protocol2 : this.translationRegistry.getProtocols()) {
            if (!protocol2.identifier().equals((Object)identifier)) {
                continue;
            }
            final boolean translated = protocol2.forEachListener(listener -> {
                try {
                    final Packet packet = listener.translateIncomingPayload(reader);
                    if (packet != null) {
                        protocol.targetProtocol().handlePacket(DefaultLabyModProtocolService.DUMMY_UUID, packet);
                        return true;
                    }
                }
                catch (final Exception e2) {
                    e2.printStackTrace();
                }
                finally {
                    reader.reset();
                }
                return false;
            });
            if (!translated) {
                continue;
            }
            handled = true;
        }
        if (!handled) {
            return Laby.references().labyProtocolApi().getProtocolService().readCustomPayload(LabyModPayloadBridge.newToOldIdentifier(identifier), payload, true);
        }
        return handled;
    }
}
