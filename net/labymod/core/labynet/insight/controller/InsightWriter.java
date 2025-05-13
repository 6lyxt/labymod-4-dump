// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.insight.controller;

import java.util.UUID;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.labynet.insight.model.camera.PivotPointInsight;
import net.labymod.api.client.options.Perspective;
import net.labymod.core.labynet.insight.model.WindowInsight;
import net.labymod.api.event.client.entity.player.FieldOfViewModifierEvent;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.core.labynet.insight.model.ServerInsight;
import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.labynet.insight.model.camera.CameraInsight;
import net.labymod.core.labynet.insight.model.player.PlayerInsight;
import java.util.ArrayList;
import net.labymod.core.main.LabyMod;
import net.labymod.api.Laby;
import net.labymod.core.labynet.insight.util.ImageCodec;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.misc.WriteScreenshotEvent;
import net.labymod.api.event.Subscribe;
import java.io.File;
import net.labymod.api.event.client.misc.CaptureScreenshotEvent;
import java.util.HashMap;
import net.labymod.core.labynet.insight.model.ScreenshotInsight;
import java.util.Map;
import net.labymod.api.util.logging.Logging;

public class InsightWriter
{
    public static final String KEY = "Insight";
    @Deprecated
    public static final String LEGACY_KEY = "Screenshot Metadata";
    protected static final Logging LOGGER;
    private final Map<String, ScreenshotInsight> insights;
    private float fovModifier;
    
    public InsightWriter() {
        this.insights = new HashMap<String, ScreenshotInsight>();
    }
    
    @Subscribe
    public void onCaptureScreenshot(final CaptureScreenshotEvent event) {
        try {
            final File file = event.getDestination();
            this.insights.put(file.getName(), this.captureInsight());
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    @Subscribe
    public void onWriteScreenshot(final WriteScreenshotEvent event) throws Exception {
        if (event.getPhase() != Phase.PRE) {
            return;
        }
        final ImageCodec codec = new ImageCodec(event.getImage());
        codec.set("Minecraft Version", Laby.labyAPI().minecraft().getVersion());
        codec.set("LabyMod Version", LabyMod.getInstance().getVersion());
        final String username = Laby.references().sessionAccessor().getSession().getUsername();
        codec.set("Minecraft Account", username);
        final ScreenshotInsight insight = this.insights.remove(event.getDestination().getName());
        if (insight != null) {
            codec.set("Insight", insight.toJsonObject().toString());
            final CameraInsight camera = insight.getCamera();
            if (camera != null) {
                codec.set("Fov", Double.toString(camera.getFov()));
                final DoubleVector3 position = camera.getPosition();
                final Quaternion rotation = camera.getRotation();
                codec.set("Coordinates", String.format("%.2f, %.2f, %.2f (%s, %s)", position.getX(), position.getY(), position.getZ(), rotation.getYaw(), rotation.getPitch()));
            }
            final ServerInsight server = insight.getServer();
            if (server != null && server.getServerData() != null) {
                final ServerAddress address = server.getServerData().address();
                codec.set("Server Address", address.getHost() + ":" + address.getPort());
            }
            final List<String> list = new ArrayList<String>();
            for (final PlayerInsight playerMeta : insight.getPlayers()) {
                final String name = playerMeta.getName();
                list.add(name);
            }
            if (!list.isEmpty()) {
                final String[] players = list.toArray(new String[0]);
                codec.set("Players", String.join(",", (CharSequence[])players));
            }
        }
        event.setImage(codec.compileWithMeta());
    }
    
    @Subscribe(126)
    public void onFov(final FieldOfViewModifierEvent event) {
        this.fovModifier = event.getFieldOfView();
    }
    
    public Map<String, ScreenshotInsight> getInsights() {
        return this.insights;
    }
    
    private ScreenshotInsight captureInsight() {
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        final MinecraftCamera camera = minecraft.getCamera();
        final ClientWorld world = minecraft.clientWorld();
        final ClientPlayer clientPlayer = minecraft.getClientPlayer();
        final Perspective perspective = minecraft.options().perspective();
        final Window window = minecraft.minecraftWindow();
        final float partialTicks = minecraft.getPartialTicks();
        final ScreenshotInsight insight = new ScreenshotInsight();
        insight.setWindow(new WindowInsight(window.getRawWidth(), window.getRawHeight()));
        if (camera != null && clientPlayer != null) {
            final CameraInsight cameraMeta = new CameraInsight(camera.position(), camera.rotation(), minecraft.options().getFov() * this.fovModifier);
            if (perspective != Perspective.FIRST_PERSON) {
                final Position position = clientPlayer.position();
                cameraMeta.setPivotPoint(new PivotPointInsight(position.getX(), position.getY() + clientPlayer.getEyeHeight(), position.getZ()));
            }
            insight.setCamera(cameraMeta);
        }
        final ServerData serverData = Laby.labyAPI().serverController().getCurrentServerData();
        if (serverData != null) {
            insight.setServerData(new ServerInsight(serverData));
        }
        if (world != null && clientPlayer != null && camera != null) {
            for (final Player player : world.getPlayers()) {
                final UUID uuid = player.getUniqueId();
                if (uuid.getMostSignificantBits() != 0L) {
                    if (uuid.getLeastSignificantBits() == 0L) {
                        continue;
                    }
                    final double distance = player.getDistanceSquared(clientPlayer);
                    if (distance > 256.0) {
                        continue;
                    }
                    if (perspective == Perspective.FIRST_PERSON && clientPlayer == player) {
                        continue;
                    }
                    if (world.isBlockInBetween(camera.position(), player.position().toDoubleVector3()) && world.isBlockInBetween(camera.position(), new DoubleVector3(player.eyePosition()))) {
                        continue;
                    }
                    if (!player.isRendered()) {
                        continue;
                    }
                    insight.addPlayer(player, partialTicks);
                }
            }
        }
        return insight;
    }
    
    static {
        LOGGER = Logging.create(InsightWriter.class);
    }
}
