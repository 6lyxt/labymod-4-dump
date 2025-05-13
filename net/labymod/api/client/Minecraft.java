// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.chat.Title;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.crash.GameCrashReport;
import net.labymod.api.client.entity.EntityRenderDispatcher;
import net.labymod.api.client.render.ItemStackRenderer;
import net.labymod.api.client.world.WorldRenderer;
import net.labymod.api.client.gui.mouse.MouseHandlerAccessor;
import net.labymod.api.profiler.SampleLogger;
import net.labymod.api.client.scoreboard.TabList;
import net.labymod.api.client.session.MinecraftAuthenticator;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.Laby;
import java.util.function.Predicate;
import net.labymod.api.client.gui.mouse.MutableMouse;
import java.util.function.Consumer;
import net.labymod.api.client.resources.sound.MinecraftSounds;
import net.labymod.api.client.resources.texture.MinecraftTextures;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.entity.Entity;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gfx.target.RenderTarget;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.session.SessionAccessor;

public interface Minecraft
{
    int getFPS();
    
    SessionAccessor sessionAccessor();
    
    Window minecraftWindow();
    
    RenderTarget mainTarget();
    
    @Deprecated
    default ClientPlayer clientPlayer() {
        return this.getClientPlayer();
    }
    
    @Nullable
    ClientPlayer getClientPlayer();
    
    Entity getTargetEntity();
    
    @Deprecated
    default Entity cameraEntity() {
        return this.getCameraEntity();
    }
    
    @Nullable
    Entity getCameraEntity();
    
    @Nullable
    MinecraftCamera getCamera();
    
    void executeOnRenderThread(final Runnable p0);
    
    void executeNextTick(final Runnable p0);
    
    boolean isOnRenderThread();
    
    void setSessionRaw(final Object p0);
    
    @Deprecated
    default ScreenWrapper toLabyScreenRenderer(final LabyScreen screen) {
        return this.wrapScreen(screen);
    }
    
    ScreenWrapper wrapScreen(final ScreenInstance p0);
    
    MinecraftTextures textures();
    
    MinecraftSounds sounds();
    
    default void updateMouse(final double x, final double y, final Consumer<MutableMouse> consumer) {
        final MutableMouse mouse = this.mouse();
        mouse.set(x, y, () -> consumer.accept(mouse));
    }
    
    default boolean updateMouse(final double x, final double y, final Predicate<MutableMouse> predicate) {
        final MutableMouse mouse = this.mouse();
        return mouse.set(x, y, () -> predicate.test(mouse));
    }
    
    default void setCursorPosition(final double x, final double y) {
        Laby.labyAPI().gfxRenderPipeline().gfx().setCursorPosition(x, y);
    }
    
    MutableMouse mouse();
    
    Mouse absoluteMouse();
    
    boolean isSingleplayer();
    
    boolean isIngame();
    
    default String getTranslationOrDefault(final String translationKey, final String def) {
        if (def == null) {
            return this.getTranslation(translationKey);
        }
        return this.hasTranslation(translationKey) ? this.getTranslation(translationKey) : def;
    }
    
    String getTranslation(final String p0);
    
    boolean hasTranslation(final String p0);
    
    float getTickDelta();
    
    float getPartialTicks();
    
    boolean isPaused();
    
    MinecraftOptions options();
    
    void updateKeyRepeatingMode(final boolean p0);
    
    ComponentMapper componentMapper();
    
    ChatExecutor chatExecutor();
    
    default boolean isKeyPressed(final Key key) {
        return KeyMapper.isPressed(key);
    }
    
    default boolean isMouseDown(final Key button) {
        return KeyMapper.isPressed(button);
    }
    
    String getVersion();
    
    String getVersionId();
    
    String getVersionType();
    
    int getProtocolVersion();
    
    void requestChunkUpdate();
    
    int getDataVersion();
    
    boolean isDemo();
    
    @Nullable
    ClientPacketListener getClientPacketListener();
    
    ClientWorld clientWorld();
    
    @Nullable
    Scoreboard getScoreboard();
    
    MinecraftAuthenticator authenticator();
    
    @Nullable
    TabList getTabList();
    
    void updateWindowTitle();
    
    void openChat(final String p0);
    
    @Deprecated(forRemoval = true, since = "4.1.10")
    MinecraftFrameTimer frameTimer();
    
    SampleLogger frameTimeLogger();
    
    boolean isMouseLocked();
    
    MouseHandlerAccessor mouseHandler();
    
    String getClipboard();
    
    void setClipboard(final String p0);
    
    boolean isDestroying();
    
    float getDestroyProgress();
    
    void reloadResources();
    
    WorldRenderer worldRenderer();
    
    ItemStackRenderer itemStackRenderer();
    
    EntityRenderDispatcher entityRenderDispatcher();
    
    boolean isMacOS();
    
    long getRunningMillis();
    
    default boolean isLoadingOverlayPresent() {
        return false;
    }
    
    boolean isLastItemUsed();
    
    boolean isLastBlockUsed();
    
    default void crashGame(final String message, final Throwable throwable) {
        this.crashGame(GameCrashReport.forThrowable(message, throwable));
    }
    
    void crashGame(final GameCrashReport p0);
    
    void shutdownGame();
    
    GameMode gameMode();
    
    HitResult getHitResult();
    
    default void refreshRealmsClient() {
    }
    
    void updateBlockBreak(final boolean p0);
    
    @Deprecated
    default Entity targetEntity() {
        return this.getTargetEntity();
    }
    
    @Deprecated
    @Nullable
    default ClientPacketListener clientPacketListener() {
        return this.getClientPacketListener();
    }
    
    @Nullable
    default Title getTitle() {
        return this.chatExecutor().getTitle();
    }
    
    default void showTitle(@NotNull final Title title) {
        this.chatExecutor().showTitle(title);
    }
    
    default void clearTitle() {
        this.chatExecutor().clearTitle();
    }
}
