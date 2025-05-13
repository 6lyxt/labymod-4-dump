// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.background.position;

import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import net.labymod.core.util.camera.spline.position.Location;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.client.gui.screen.ActivityOpenEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.game.GameScreenRegistry;
import net.labymod.api.event.client.gui.screen.ScreenOpenEvent;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.screenshots.ScreenshotBrowserActivity;
import net.labymod.core.client.gui.screen.activity.activities.account.AccountManagerActivity;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.ShopActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.WidgetsEditorActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.AddonsActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.SettingsActivity;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.LabyConnectActivity;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.directconnect.DirectConnectActivity;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.PublicServerListActivity;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.PrivateServerListActivity;
import net.labymod.core.client.gui.screen.activity.activities.singleplayer.SingleplayerOverlay;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.screen.activity.activities.menu.MainMenuActivity;
import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.core.util.camera.CinematicCamera;
import java.util.List;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;

public class ScreenPositionRegistry
{
    public static final CubicBezier DEFAULT_SCREEN_SWITCH_CURVE;
    public static final int DEFAULT_SCREEN_SWITCH_DURATION = 500;
    private final List<PositionTransition> screenPositions;
    private final CinematicCamera camera;
    private long timeLastDynamicDurationForce;
    private boolean forceDynamicDuration;
    private long dynamicDuration;
    
    public ScreenPositionRegistry(final CinematicCamera camera) {
        this.screenPositions = new ArrayList<PositionTransition>();
        this.camera = camera;
        Laby.labyAPI().eventBus().registerListener(this);
    }
    
    public void register() {
        this.screenPositions.clear();
        this.register(MainMenuActivity.class, DynamicBackgroundController.POS_TITLE_SCREEN);
        this.register(SingleplayerOverlay.class, 23.0f, 17.0f, 15.0f, -31.0f, 7.0f, 5.0f);
        this.register(PrivateServerListActivity.class, 23.0f, 13.0f, 15.0f, -31.0f, -13.0f, 5.0f);
        this.register(PublicServerListActivity.class, 22.0f, 13.0f, 16.0f, -31.0f, -13.0f, 5.0f);
        this.register(DirectConnectActivity.class, 23.0f, 13.0f, 18.0f, -21.0f, -13.0f, 8.0f);
        this.register(NamedScreen.CONNECTING, DynamicBackgroundController.POS_OPENER_START, 3000L);
        this.register(NamedScreen.DISCONNECTED, 23.0f, 13.0f, 18.0f, -21.0f, -13.0f, 8.0f, 3000L);
        this.register(LabyConnectActivity.class, 23.0f, 13.0f, 15.0f, -25.0f, -13.0f, 1.0f);
        this.register(SettingsActivity.class, 23.0f, 13.0f, 15.0f, -20.0f, -13.0f, -1.0f);
        this.register(AddonsActivity.class, 23.0f, 13.0f, 20.0f, -10.0f, -13.0f, 1.0f);
        this.register(WidgetsEditorActivity.class, 23.0f, 13.0f, 30.0f, -3.0f, -13.0f, 1.0f);
        this.register(PlayerActivity.class, DynamicBackgroundController.CUSTOMIZATION_PLAYER_CAMERA);
        this.register(ShopActivity.class, DynamicBackgroundController.SHOP_PLAYER_CAMERA);
        this.register(ResourceLocation.create("labymod", "shop/leg_focus"), 21.5f, 13.0f, 39.2f, 45.0f, 10.0f, 0.0f);
        this.register(NamedScreen.OPTIONS, 23.0f, 13.0f, 15.0f, -15.0f, -13.0f, -5.0f);
        this.register(AccountManagerActivity.class, 18.0f, 13.0f, 18.0f, 10.0f, 0.0f, 1.0f);
        this.register(ScreenshotBrowserActivity.class, 22.0f, 13.0f, 15.0f, 30.0f, 0.0f, -5.0f);
    }
    
    @Subscribe
    public void onScreenOpen(final ScreenOpenEvent event) {
        final GameScreen gameScreen = GameScreenRegistry.from(event.getScreen());
        if (gameScreen == null) {
            return;
        }
        final PositionTransition transition = this.findPositionTransition(gameScreen);
        if (transition != null) {
            this.executeTransition(transition);
        }
    }
    
    @Subscribe
    public void onDisplayActivity(final ActivityOpenEvent event) {
        final PositionTransition transition = this.findPositionTransition(event.activity().getClass());
        if (transition != null) {
            this.executeTransition(transition);
        }
    }
    
    @Subscribe
    public void onServerDisconnect(final ServerDisconnectEvent event) {
        this.timeLastDynamicDurationForce = TimeUtil.getMillis();
        this.dynamicDuration = 3000L;
    }
    
    public void executeTransition(final ResourceLocation identifier) {
        final PositionTransition transition = this.findPositionTransition(identifier);
        if (transition == null) {
            return;
        }
        this.executeTransition(transition);
    }
    
    public void executeTransition(final PositionTransition transition) {
        final long timePassedSinceLastDisconnect = TimeUtil.getMillis() - this.timeLastDynamicDurationForce;
        final boolean useDynamicDuration = this.forceDynamicDuration || timePassedSinceLastDisconnect < 1000L;
        this.camera.moveTo(useDynamicDuration ? Math.max(this.dynamicDuration, transition.getDuration()) : transition.getDuration(), transition.curve(), transition.position());
        if (this.forceDynamicDuration) {
            this.timeLastDynamicDurationForce = TimeUtil.getMillis();
            this.forceDynamicDuration = false;
        }
        if (transition.getDuration() >= 1000L) {
            this.forceDynamicDuration = true;
            this.dynamicDuration = transition.getDuration();
        }
    }
    
    @Nullable
    private PositionTransition findPositionTransition(final Object screen) {
        for (final PositionTransition screenPosition : this.screenPositions) {
            if (screenPosition.is(screen)) {
                return screenPosition;
            }
        }
        return null;
    }
    
    private void register(final Object identifier, final float x, final float y, final float z, final float yaw, final float pitch, final float roll) {
        this.register(identifier, new Location(x, y, z, yaw, pitch, roll));
    }
    
    private void register(final Object identifier, final float x, final float y, final float z, final float yaw, final float pitch, final float roll, final long duration) {
        this.register(identifier, new Location(x, y, z, yaw, pitch, roll), duration);
    }
    
    private void register(final Object identifier, final Location position) {
        this.register(identifier, position, 500L);
    }
    
    private void register(final Object identifier, final Location position, final long duration) {
        PositionTransition transition;
        if (identifier instanceof final ResourceLocation location) {
            transition = new ResourceLocationPositionTransition(position, ScreenPositionRegistry.DEFAULT_SCREEN_SWITCH_CURVE, duration, location);
        }
        else {
            transition = new ScreenPositionTransition(position, ScreenPositionRegistry.DEFAULT_SCREEN_SWITCH_CURVE, duration, identifier);
        }
        this.screenPositions.add(transition);
    }
    
    static {
        DEFAULT_SCREEN_SWITCH_CURVE = new CubicBezier(0.2, 0.32, 0.2, 1.0);
    }
}
