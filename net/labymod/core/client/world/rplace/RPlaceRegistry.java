// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.rplace;

import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.LabyConnect;
import java.util.Optional;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonMessage;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import java.util.Locale;
import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.api.Laby;
import net.labymod.api.util.io.LabyExecutors;
import java.net.URI;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.inject.Inject;
import java.util.ArrayList;
import com.google.gson.JsonArray;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.core.client.world.rplace.art.ColoredBlock;
import java.util.List;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;
import net.labymod.core.labyconnect.session.message.MessageListener;
import net.labymod.core.client.world.rplace.art.PixelArt;
import net.labymod.api.service.DefaultRegistry;

@Singleton
@Referenceable
public class RPlaceRegistry extends DefaultRegistry<PixelArt> implements MessageListener
{
    private List<ColoredBlock> palette;
    private boolean enabled;
    private int mapMinX;
    private int mapMinZ;
    private int mapMaxX;
    private int mapMaxZ;
    private int mapY;
    private ServerData serverData;
    private String lobbyName;
    private String targetLobbyName;
    private String mapUrl;
    private boolean isOnTargetLobby;
    private JsonArray lastArtworksArray;
    private boolean announced;
    private boolean onTestServer;
    
    @Inject
    public RPlaceRegistry() {
        this.palette = new ArrayList<ColoredBlock>();
        this.announced = false;
        this.onTestServer = false;
    }
    
    public PixelArt registerFromUrl(final URL url, final int x, final int z, final int size, final boolean labyConnect) throws IOException {
        final InputStream in = url.openStream();
        final PixelArt pixelArt = new PixelArt(in, x, this.mapY, z, size, labyConnect);
        this.register(pixelArt);
        in.close();
        return pixelArt;
    }
    
    @Override
    public void register(final PixelArt art) {
        if (art.getMinX() < this.mapMinX || art.getMinZ() < this.mapMinZ || art.getMaxX() > this.mapMaxX || art.getMaxZ() > this.mapMaxZ) {
            throw new IllegalArgumentException("Image of size " + art.getWidth() + "x" + art.getHeight() + " is placed out of map!");
        }
        for (final PixelArt otherArt : this.values()) {
            if (otherArt.intersects(art)) {
                throw new IllegalArgumentException("Image of size " + art.getWidth() + "x" + art.getHeight() + " intersects with other image that is placed at " + otherArt.getCenterX() + ", " + otherArt.getCenterZ());
            }
        }
        art.load();
        super.register(art);
    }
    
    @Override
    public void listen(final String message) {
        try {
            final JsonObject event = (JsonObject)RPlaceRegistry.GSON.fromJson(message, (Class)JsonObject.class);
            if (!event.has("action")) {
                return;
            }
            final String action = event.get("action").getAsString();
            if (action.equals("lobby")) {
                this.targetLobbyName = event.get("targetLobbyName").getAsString();
                this.updateIsOnTargetLobby();
            }
            if (action.equals("update")) {
                this.enabled = event.get("enabled").getAsBoolean();
                this.mapMinX = event.get("minX").getAsInt();
                this.mapMinZ = event.get("minZ").getAsInt();
                this.mapMaxX = event.get("maxX").getAsInt();
                this.mapMaxZ = event.get("maxZ").getAsInt();
                this.mapY = event.get("y").getAsInt();
                if (event.has("mapUrl")) {
                    this.mapUrl = event.get("mapUrl").getAsString();
                }
                try {
                    if (event.has("palette")) {
                        final List<ColoredBlock> list = new ArrayList<ColoredBlock>();
                        final JsonArray paletteArray = event.getAsJsonArray("palette");
                        for (final JsonElement element : paletteArray) {
                            final JsonObject object = element.getAsJsonObject();
                            list.add(new ColoredBlock(object.get("id").getAsString(), object.get("color").getAsInt()));
                        }
                        this.palette = list;
                    }
                }
                catch (final Throwable t) {
                    t.printStackTrace();
                }
                this.lastArtworksArray = event.getAsJsonArray("artworks");
                this.updateIsOnTargetLobby();
            }
        }
        catch (final Throwable t2) {
            t2.printStackTrace();
        }
    }
    
    private void updateArtworks() {
        final List<String> toDelete = new ArrayList<String>();
        for (final PixelArt pixelArt : this.values()) {
            if (pixelArt.isLabyConnect()) {
                toDelete.add(pixelArt.getId());
                pixelArt.dispose();
            }
        }
        for (final String id : toDelete) {
            this.unregister(id);
        }
        LabyExecutors.executeBackgroundTask(() -> {
            try {
                if (this.lastArtworksArray != null) {
                    this.lastArtworksArray.iterator();
                    final Iterator iterator3;
                    while (iterator3.hasNext()) {
                        final JsonElement element = iterator3.next();
                        final JsonObject object = element.getAsJsonObject();
                        final String url = object.get("url").getAsString();
                        final int x = object.get("x").getAsInt();
                        final int z = object.get("z").getAsInt();
                        int size = -1;
                        if (object.has("size")) {
                            size = object.get("size").getAsInt();
                        }
                        this.registerFromUrl(URI.create(url).toURL(), x, z, size, true);
                    }
                }
            }
            catch (final Throwable t) {
                t.printStackTrace();
            }
        });
    }
    
    public void updateIsOnTargetLobby() {
        if (this.serverData == null || this.lobbyName == null) {
            this.isOnTargetLobby = false;
            this.enabled = false;
            return;
        }
        final String currentAddress = this.serverData.address().getHost();
        if (currentAddress == null) {
            this.isOnTargetLobby = false;
            this.enabled = false;
            return;
        }
        final Optional<ServerGroup> server = Laby.references().labyNetController().getServerByIp(this.serverData.address());
        final boolean onGomme = server.isPresent() && server.get().getServerName().equals("gommehd");
        final boolean onTestServer = currentAddress.toLowerCase(Locale.ENGLISH).contains("gomme.laby");
        if (!onGomme && !onTestServer) {
            this.isOnTargetLobby = false;
            this.enabled = false;
            return;
        }
        final boolean isOnTargetLobby = this.targetLobbyName != null && this.lobbyName.equals(this.targetLobbyName);
        if (!isOnTargetLobby) {
            this.isOnTargetLobby = false;
            this.enabled = false;
            return;
        }
        this.isOnTargetLobby = true;
        this.onTestServer = onTestServer;
        if (this.isEnabled() && this.lastArtworksArray != null && !this.lastArtworksArray.isEmpty()) {
            final JsonObject mainArtwork = this.lastArtworksArray.get(0).getAsJsonObject();
            final int mainX = mainArtwork.get("x").getAsInt();
            final int mainZ = mainArtwork.get("z").getAsInt();
            if (!this.announced) {
                this.announced = true;
                Laby.labyAPI().minecraft().chatExecutor().displayClientMessage(Component.translatable("labymod.command.command.rplaceoverlay.welcome", NamedTextColor.GREEN, Component.translatable("/place-overlay", NamedTextColor.YELLOW), Component.translatable(String.valueOf(mainX), NamedTextColor.YELLOW), Component.translatable(String.valueOf(mainZ), NamedTextColor.YELLOW)));
            }
            this.updateArtworks();
        }
        else {
            final LabyConnect labyConnect = Laby.labyAPI().labyConnect();
            final LabyConnectSession session = labyConnect.getSession();
            if (session == null) {
                return;
            }
            final JsonObject payload = new JsonObject();
            payload.addProperty("action", "request");
            ((DefaultLabyConnect)labyConnect).sendPacket(new PacketAddonMessage("rplace", (JsonElement)payload));
        }
    }
    
    public ColoredBlock getNearestBlock(final int argb) {
        ColoredBlock nearest = null;
        double minDistance = Double.MAX_VALUE;
        final int a1 = argb >> 24 & 0xFF;
        final int r1 = argb >> 16 & 0xFF;
        final int g1 = argb >> 8 & 0xFF;
        final int b1 = argb & 0xFF;
        if (a1 < 40) {
            return null;
        }
        for (final ColoredBlock block : this.palette) {
            if (!block.isValid()) {
                continue;
            }
            final int colorRgb = block.getColor();
            final int r2 = colorRgb >> 16 & 0xFF;
            final int g2 = colorRgb >> 8 & 0xFF;
            final int b2 = colorRgb & 0xFF;
            final double distance = Math.pow(r1 - r2, 2.0) + Math.pow(g1 - g2, 2.0) + Math.pow(b1 - b2, 2.0);
            if (distance >= minDistance) {
                continue;
            }
            minDistance = distance;
            nearest = block;
        }
        return nearest;
    }
    
    public void clear(final boolean includingLabyConnect) {
        try {
            final List<PixelArt> toRemove = new ArrayList<PixelArt>();
            for (final PixelArt pixelArt : this.values()) {
                if (pixelArt.isLabyConnect() && !includingLabyConnect) {
                    continue;
                }
                pixelArt.dispose();
                toRemove.add(pixelArt);
            }
            for (final PixelArt pixelArt : toRemove) {
                this.unregister(pixelArt.getId());
            }
        }
        catch (final Throwable t) {
            t.printStackTrace();
        }
    }
    
    public boolean isEnabled() {
        return this.enabled && this.isOnTargetLobby && !PlatformEnvironment.isAncientOpenGL();
    }
    
    public int getMapY() {
        return this.mapY;
    }
    
    public boolean isOnTargetLobby() {
        return this.isOnTargetLobby;
    }
    
    public String getMapUrl() {
        return this.mapUrl;
    }
    
    public void disableOnError() {
        this.enabled = false;
        Laby.labyAPI().minecraft().chatExecutor().displayClientMessage(Component.text("Disabled r/place support because an error occurred", NamedTextColor.RED));
    }
    
    public void setCurrentLobby(final ServerData server, final String lobbyName) {
        try {
            if (server == null) {
                this.announced = false;
            }
            this.serverData = server;
            this.lobbyName = lobbyName;
            this.updateIsOnTargetLobby();
        }
        catch (final Throwable t) {
            t.printStackTrace();
        }
    }
    
    public boolean isOnTestServer() {
        return this.onTestServer;
    }
}
