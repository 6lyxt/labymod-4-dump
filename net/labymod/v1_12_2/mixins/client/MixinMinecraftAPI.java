// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client;

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.client.crash.GameCrashReport;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.entity.EntityRenderDispatcher;
import net.labymod.api.client.render.ItemStackRenderer;
import net.labymod.api.client.world.WorldRenderer;
import net.labymod.api.client.gui.mouse.MouseHandlerAccessor;
import net.labymod.api.profiler.SampleLogger;
import net.labymod.api.client.MinecraftFrameTimer;
import net.labymod.v1_12_2.client.input.KeyBindingHelper;
import org.lwjgl.opengl.Display;
import net.labymod.api.client.scoreboard.TabList;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.session.MinecraftAuthenticator;
import net.minecraft.realms.RealmsSharedConstants;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.v1_12_2.client.resources.VersionedLocale;
import net.labymod.v1_12_2.client.resources.VersionedI18n;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Intrinsic;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.core.main.LabyMod;
import net.labymod.core.client.input.MouseBridge;
import net.labymod.api.client.resources.sound.MinecraftSounds;
import net.labymod.v1_12_2.client.resources.texture.VersionedMinecraftTextures;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.v1_12_2.client.gfx.pipeline.target.VersionedRenderTarget;
import net.labymod.v1_12_2.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.ScreenInstance;
import com.google.common.util.concurrent.ListenableFutureTask;
import java.util.Objects;
import net.labymod.v1_12_2.client.ClientCamera;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.v1_12_2.client.gui.window.VersionedWindow;
import com.google.common.util.concurrent.ListenableFuture;
import net.labymod.api.util.time.ModernTickDeltaTimer;
import net.labymod.api.client.gfx.target.RenderTarget;
import net.labymod.api.client.resources.texture.MinecraftTextures;
import net.labymod.api.client.gui.window.Window;
import java.util.concurrent.FutureTask;
import java.util.Queue;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.Minecraft;

@Mixin({ bib.class })
@Implements({ @Interface(iface = Minecraft.class, prefix = "minecraft$", remap = Interface.Remap.NONE) })
public abstract class MixinMinecraftAPI implements Minecraft
{
    @Shadow
    private static int ar;
    @Mutable
    @Shadow
    @Final
    private bii af;
    @Shadow
    public bud h;
    @Shadow
    private bih Y;
    @Shadow
    public bid t;
    @Final
    @Shadow
    private Queue<FutureTask<?>> aQ;
    @Shadow
    public bsb f;
    @Shadow
    public biq q;
    @Shadow
    @Final
    public rb z;
    @Shadow
    private bvd aJ;
    @Shadow
    public bsa c;
    @Shadow
    private bzw ab;
    @Shadow
    private boolean ag;
    @Shadow
    private bzf aa;
    private Window labyMod$minecraftWindow;
    private MinecraftTextures labyMod$minecraftTextures;
    private long labyMod$startupMillis;
    private RenderTarget labyMod$wrappedMainRenderTarget;
    private final ModernTickDeltaTimer labymod$modernTimer;
    private boolean labymod$isLastItemUsed;
    private boolean labymod$isLastBlockUsed;
    @Shadow
    public buy g;
    @Shadow
    public bhc s;
    @Shadow
    public bic v;
    
    public MixinMinecraftAPI() {
        this.labymod$modernTimer = new ModernTickDeltaTimer();
    }
    
    @Shadow
    public abstract ListenableFuture<Object> shadow$a(final Runnable p0);
    
    @Shadow
    public abstract brz shadow$v();
    
    @Shadow
    public abstract void f();
    
    @Shadow
    public abstract boolean aF();
    
    @Shadow
    public abstract void a(final b p0);
    
    @Shadow
    public abstract void n();
    
    @Shadow
    protected abstract void shadow$b(final boolean p0);
    
    @Override
    public int getFPS() {
        return MixinMinecraftAPI.ar;
    }
    
    @Override
    public Window minecraftWindow() {
        if (this.labyMod$minecraftWindow == null) {
            this.labyMod$minecraftWindow = new VersionedWindow();
        }
        return this.labyMod$minecraftWindow;
    }
    
    @Override
    public ClientPlayer getClientPlayer() {
        return (ClientPlayer)this.h;
    }
    
    @Override
    public Entity getTargetEntity() {
        return (Entity)bib.z().i;
    }
    
    @Override
    public Entity getCameraEntity() {
        return (Entity)bib.z().aa();
    }
    
    @Override
    public MinecraftCamera getCamera() {
        return (bib.z().aa() != null) ? ClientCamera.INSTANCE : null;
    }
    
    @Override
    public void executeOnRenderThread(final Runnable runnable) {
        Objects.requireNonNull(runnable, "runnable");
        this.shadow$a(runnable);
    }
    
    @Override
    public void executeNextTick(final Runnable runnable) {
        Objects.requireNonNull(runnable, "runnable");
        this.aQ.add((FutureTask<?>)ListenableFutureTask.create(runnable, (Object)null));
    }
    
    @Override
    public boolean isOnRenderThread() {
        return this.aF();
    }
    
    @Override
    public void setSessionRaw(final Object gameSession) {
        this.af = (bii)gameSession;
    }
    
    @Override
    public ScreenWrapper wrapScreen(final ScreenInstance screen) {
        if (screen instanceof final ScreenWrapper screenWrapper) {
            return screenWrapper;
        }
        return Laby.references().screenWrapperFactory().create((screen instanceof LabyScreen) ? new LabyScreenRenderer((LabyScreen)screen) : screen);
    }
    
    @Override
    public RenderTarget mainTarget() {
        if (this.labyMod$wrappedMainRenderTarget == null) {
            this.labyMod$wrappedMainRenderTarget = new VersionedRenderTarget(this.aJ);
        }
        return this.labyMod$wrappedMainRenderTarget;
    }
    
    @Override
    public SessionAccessor sessionAccessor() {
        return Laby.references().sessionAccessor();
    }
    
    @Override
    public MinecraftTextures textures() {
        if (this.labyMod$minecraftTextures == null) {
            this.labyMod$minecraftTextures = new VersionedMinecraftTextures();
        }
        return this.labyMod$minecraftTextures;
    }
    
    @Override
    public MinecraftSounds sounds() {
        return Laby.references().minecraftSounds();
    }
    
    private MouseBridge labyMod$mouseBridge() {
        return LabyMod.references().mouseBridge();
    }
    
    @Override
    public MutableMouse mouse() {
        return this.labyMod$mouseBridge().mouse();
    }
    
    @Override
    public Mouse absoluteMouse() {
        return this.labyMod$mouseBridge().absoluteMouse();
    }
    
    @Intrinsic
    public boolean minecraft$isSingleplayer() {
        return bib.z().E();
    }
    
    @Override
    public boolean isIngame() {
        return bib.z().f != null;
    }
    
    @Override
    public void updateKeyRepeatingMode(final boolean enabled) {
        Keyboard.enableRepeatEvents(enabled);
    }
    
    @Override
    public String getTranslation(final String translationKey) {
        final cfb locale = VersionedI18n.getLocale();
        if (locale == null) {
            return translationKey;
        }
        return ((VersionedLocale)locale).getTranslation(translationKey);
    }
    
    @Override
    public boolean hasTranslation(final String translationKey) {
        final cfb locale = VersionedI18n.getLocale();
        return locale != null && ((VersionedLocale)locale).hasTranslation(translationKey);
    }
    
    @Override
    public float getPartialTicks() {
        return this.Y.b;
    }
    
    @Override
    public float getTickDelta() {
        return this.labymod$modernTimer.getTickDelta();
    }
    
    @Override
    public boolean isPaused() {
        return this.ag;
    }
    
    @Override
    public MinecraftOptions options() {
        return (MinecraftOptions)this.t;
    }
    
    @Override
    public ComponentMapper componentMapper() {
        return Laby.references().componentMapper();
    }
    
    @Override
    public ChatExecutor chatExecutor() {
        return Laby.references().chatExecutor();
    }
    
    @Override
    public String getVersion() {
        return RealmsSharedConstants.VERSION_STRING;
    }
    
    @Override
    public String getVersionId() {
        return RealmsSharedConstants.VERSION_STRING;
    }
    
    @Override
    public int getProtocolVersion() {
        return RealmsSharedConstants.NETWORK_PROTOCOL_VERSION;
    }
    
    @Override
    public int getDataVersion() {
        return -1;
    }
    
    @Intrinsic
    public String minecraft$getVersionType() {
        return this.shadow$d();
    }
    
    @Shadow
    public abstract String shadow$d();
    
    @Intrinsic
    public boolean minecraft$isDemo() {
        return this.shadow$u();
    }
    
    @Shadow
    public abstract boolean shadow$u();
    
    @Override
    public void requestChunkUpdate() {
        bib.z().g.o();
    }
    
    @Override
    public MinecraftAuthenticator authenticator() {
        return Laby.references().minecraftAuthenticator();
    }
    
    @Override
    public ClientPacketListener getClientPacketListener() {
        return (ClientPacketListener)this.shadow$v();
    }
    
    @Override
    public ClientWorld clientWorld() {
        return Laby.references().clientWorld();
    }
    
    @Nullable
    @Override
    public Scoreboard getScoreboard() {
        return (this.f != null) ? ((Scoreboard)this.f.af()) : null;
    }
    
    @Nullable
    @Override
    public TabList getTabList() {
        return (this.q == null) ? null : ((TabList)this.q.h());
    }
    
    @Override
    public void updateWindowTitle() {
        Display.setTitle(LabyMod.getInstance().createTitle(this.getVersion(), ""));
    }
    
    @Override
    public void openChat(final String defaultInput) {
        final bkn chat = new bkn("");
        bib.z().a((blk)chat);
        this.chatExecutor().insertText(defaultInput, true);
        this.executeNextTick(KeyBindingHelper::unpressKeybindings);
    }
    
    @Override
    public MinecraftFrameTimer frameTimer() {
        return (MinecraftFrameTimer)this.z;
    }
    
    @Override
    public SampleLogger frameTimeLogger() {
        return ((MinecraftFrameTimer)this.z).logger();
    }
    
    @Override
    public boolean isMouseLocked() {
        return org.lwjgl.input.Mouse.isGrabbed();
    }
    
    @Override
    public MouseHandlerAccessor mouseHandler() {
        return (MouseHandlerAccessor)this.v;
    }
    
    @Override
    public String getClipboard() {
        return Laby.gfx().backend().clipboardController().getClipboardContent(Display.getWindowHandle());
    }
    
    @Override
    public void setClipboard(final String value) {
        Laby.gfx().backend().clipboardController().setClipboardContent(Display.getWindowHandle(), value);
    }
    
    @Override
    public boolean isDestroying() {
        return this.c != null && this.c.m();
    }
    
    @Override
    public float getDestroyProgress() {
        return (this.c == null) ? 0.0f : this.c.e;
    }
    
    @Override
    public void reloadResources() {
        this.f();
    }
    
    @Override
    public WorldRenderer worldRenderer() {
        return (WorldRenderer)this.g;
    }
    
    @Override
    public ItemStackRenderer itemStackRenderer() {
        return (ItemStackRenderer)this.ab;
    }
    
    @Override
    public EntityRenderDispatcher entityRenderDispatcher() {
        return (EntityRenderDispatcher)this.aa;
    }
    
    @Override
    public boolean isMacOS() {
        return bib.a;
    }
    
    @Override
    public long getRunningMillis() {
        return TimeUtil.getMillis() - this.labyMod$startupMillis;
    }
    
    @Override
    public boolean isLastItemUsed() {
        return this.labymod$isLastItemUsed;
    }
    
    @Override
    public boolean isLastBlockUsed() {
        return this.labymod$isLastBlockUsed;
    }
    
    @Override
    public GameMode gameMode() {
        return (this.c == null) ? GameMode.UNKNOWN : GameMode.fromId(this.c.l().a());
    }
    
    @Override
    public void crashGame(final GameCrashReport report) {
        this.a(report.crashReport());
    }
    
    @Override
    public void shutdownGame() {
        this.n();
    }
    
    @Override
    public HitResult getHitResult() {
        if (this.s == null) {
            return null;
        }
        return (HitResult)this.s;
    }
    
    @Override
    public void updateBlockBreak(final boolean leftClick) {
        this.shadow$b(leftClick);
    }
    
    @Insert(method = { "runGameLoop" }, at = @At("HEAD"))
    private void labyMod$updateMouse(final InsertInfo callback) {
        final Window window = this.minecraftWindow();
        final int scaledHeight = window.getScaledHeight();
        final double x = org.lwjgl.input.Mouse.getX() * (double)window.getScaledWidth() / window.getRawWidth();
        final double y = scaledHeight - org.lwjgl.input.Mouse.getY() * (double)scaledHeight / window.getRawHeight();
        this.labyMod$mouseBridge().updateMouse(x, y);
        this.labymod$modernTimer.advanceTime();
    }
    
    @Inject(method = { "createDisplay" }, at = { @At("TAIL") })
    private void labyMod$displayCreated(final CallbackInfo ci) {
        this.labyMod$startupMillis = TimeUtil.getMillis();
    }
    
    @Insert(method = { "rightClickMouse" }, at = @At("HEAD"))
    private void labyMod$fireGameUseItemEvent(final InsertInfo ci) {
        this.labymod$isLastItemUsed = false;
        this.labymod$isLastBlockUsed = false;
    }
    
    @Inject(method = { "rightClickMouse" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;swingArm(Lnet/minecraft/util/EnumHand;)V", shift = At.Shift.AFTER) })
    private void labyMod$fireGameUseItemEventBlock(final CallbackInfo ci) {
        this.labymod$isLastBlockUsed = true;
    }
    
    @Inject(method = { "rightClickMouse" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;processRightClick(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/EnumHand;)Lnet/minecraft/util/EnumActionResult;", shift = At.Shift.AFTER) })
    private void labyMod$fireGameUseItemEventItem(final CallbackInfo ci) {
        this.labymod$isLastItemUsed = true;
    }
}
