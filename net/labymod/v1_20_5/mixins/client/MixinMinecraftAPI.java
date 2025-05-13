// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client;

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.crash.GameCrashReport;
import net.labymod.api.client.entity.EntityRenderDispatcher;
import net.labymod.api.client.render.ItemStackRenderer;
import net.labymod.api.client.world.WorldRenderer;
import net.labymod.v1_20_5.client.gfx.pipeline.target.VersionedRenderTarget;
import net.labymod.api.client.gui.mouse.MouseHandlerAccessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.MinecraftFrameTimer;
import net.labymod.api.client.scoreboard.TabList;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.network.ClientPacketListener;
import org.spongepowered.asm.mixin.Intrinsic;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.core.main.LabyMod;
import net.labymod.v1_20_5.client.resources.texture.VersionedMinecraftTextures;
import net.labymod.v1_20_5.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.Laby;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.v1_20_5.client.gui.window.VersionedWindow;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import net.labymod.api.client.gfx.target.RenderTarget;
import net.labymod.core.client.input.MouseBridge;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.resources.sound.MinecraftSounds;
import net.labymod.api.client.resources.texture.MinecraftTextures;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.session.MinecraftAuthenticator;
import net.labymod.api.client.chat.ChatExecutor;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.font.ComponentMapper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.api.profiler.SampleLogger;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.Minecraft;

@Mixin({ ffg.class })
@Implements({ @Interface(iface = Minecraft.class, prefix = "minecraft$", remap = Interface.Remap.NONE) })
public abstract class MixinMinecraftAPI implements Minecraft
{
    @Unique
    private final SampleLogger labyMod$frameTimeLogger;
    @Shadow
    private static int bd;
    @Mutable
    @Shadow
    @Final
    private ffu W;
    @Final
    @Shadow
    public ffh n;
    @Shadow
    public gcr s;
    @Shadow
    private boolean aT;
    @Shadow
    private float aU;
    @Shadow
    @Final
    private ffs R;
    @Shadow
    public fxw r;
    @Final
    @Shadow
    public ffk m;
    @Shadow
    public fne y;
    @Shadow
    @Final
    public fgr l;
    @Shadow
    public fff o;
    @Shadow
    @Final
    private aum ah;
    @Shadow
    @Final
    private eyl ao;
    private ComponentMapper labyMod$componentMapper;
    @Shadow
    @Nullable
    public evq v;
    @Shadow
    @Nullable
    public fyf q;
    @Shadow
    private gjw U;
    @Shadow
    private giw T;
    private ChatExecutor labyMod$chatExecutor;
    private MinecraftAuthenticator labyMod$authenticator;
    private Window labyMod$minecraftWindow;
    private SessionAccessor labyMod$sessionAccessor;
    private MinecraftTextures labyMod$minecraftTextures;
    private MinecraftSounds labyMod$minecraftSounds;
    private ClientWorld labyMod$clientWorld;
    private MouseBridge labyMod$mouseBridge;
    private RenderTarget labyMod$wrappedMainTarget;
    private boolean labymod$isLastItemUsed;
    private boolean labymod$isLastBlockUsed;
    @Shadow
    @Final
    public gdn f;
    @Shadow
    @Final
    public gdi j;
    @Mutable
    @Shadow
    @Final
    private fcf aN;
    
    public MixinMinecraftAPI() {
        this.labyMod$frameTimeLogger = new SampleLogger();
    }
    
    @Shadow
    protected abstract void shadow$d(final boolean p0);
    
    @Shadow
    public static ffg Q() {
        return null;
    }
    
    @Shadow
    public abstract fxx L();
    
    @Shadow
    public abstract fmy aL();
    
    @Override
    public int getFPS() {
        return MixinMinecraftAPI.bd;
    }
    
    @Shadow
    public abstract CompletableFuture<Void> l();
    
    @Shadow
    private static void a(final ffg mc, final File directory, final o par1) {
    }
    
    @Shadow
    public abstract void a(@Nullable final fne p0);
    
    @Shadow
    public abstract void n();
    
    @Override
    public Window minecraftWindow() {
        if (this.labyMod$minecraftWindow == null) {
            this.labyMod$minecraftWindow = new VersionedWindow();
        }
        return this.labyMod$minecraftWindow;
    }
    
    @Override
    public ClientPlayer getClientPlayer() {
        return (ClientPlayer)this.s;
    }
    
    @Override
    public Entity getTargetEntity() {
        return (Entity)ffg.Q().u;
    }
    
    @Override
    public Entity getCameraEntity() {
        return (Entity)ffg.Q().an();
    }
    
    @Override
    public MinecraftCamera getCamera() {
        return (ffg.Q().an() != null) ? ((MinecraftCamera)this.j.l()) : null;
    }
    
    @Override
    public SessionAccessor sessionAccessor() {
        if (this.labyMod$sessionAccessor == null) {
            this.labyMod$sessionAccessor = Laby.references().sessionAccessor();
        }
        return this.labyMod$sessionAccessor;
    }
    
    @Override
    public void executeOnRenderThread(final Runnable runnable) {
        Q().execute(runnable);
    }
    
    @Override
    public void executeNextTick(final Runnable runnable) {
        Q().i(runnable);
    }
    
    @Override
    public boolean isOnRenderThread() {
        return Q().bw();
    }
    
    @Override
    public void setSessionRaw(final Object gameSession) {
        this.W = (ffu)gameSession;
    }
    
    @Override
    public ScreenWrapper wrapScreen(final ScreenInstance screen) {
        if (screen instanceof final ScreenWrapper screenWrapper) {
            return screenWrapper;
        }
        return Laby.references().screenWrapperFactory().create((screen instanceof LabyScreen) ? new LabyScreenRenderer((LabyScreen)screen) : screen);
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
        if (this.labyMod$minecraftSounds == null) {
            this.labyMod$minecraftSounds = Laby.references().minecraftSounds();
        }
        return this.labyMod$minecraftSounds;
    }
    
    private MouseBridge labyMod$mouseBridge() {
        if (this.labyMod$mouseBridge == null) {
            this.labyMod$mouseBridge = LabyMod.references().mouseBridge();
        }
        return this.labyMod$mouseBridge;
    }
    
    @Override
    public MutableMouse mouse() {
        return this.labyMod$mouseBridge().mouse();
    }
    
    @Override
    public Mouse absoluteMouse() {
        return this.labyMod$mouseBridge().absoluteMouse();
    }
    
    @Override
    public boolean isSingleplayer() {
        return ffg.Q().U();
    }
    
    @Override
    public boolean isIngame() {
        return ffg.Q().r != null;
    }
    
    @Override
    public void updateKeyRepeatingMode(final boolean enabled) {
    }
    
    @Override
    public String getTranslation(final String translationKey) {
        return un.a().a(translationKey);
    }
    
    @Override
    public boolean hasTranslation(final String translationKey) {
        return un.a().b(translationKey);
    }
    
    @Override
    public float getPartialTicks() {
        return this.aT ? this.aU : this.R.a;
    }
    
    @Override
    public float getTickDelta() {
        return this.R.b;
    }
    
    @Override
    public boolean isPaused() {
        return this.aT;
    }
    
    @Override
    public MinecraftOptions options() {
        return (MinecraftOptions)this.m;
    }
    
    @Override
    public ComponentMapper componentMapper() {
        if (this.labyMod$componentMapper == null) {
            this.labyMod$componentMapper = Laby.references().componentMapper();
        }
        return this.labyMod$componentMapper;
    }
    
    @Override
    public ChatExecutor chatExecutor() {
        if (this.labyMod$chatExecutor == null) {
            this.labyMod$chatExecutor = Laby.references().chatExecutor();
        }
        return this.labyMod$chatExecutor;
    }
    
    @Override
    public void requestChunkUpdate() {
        ffg.Q().f.r();
    }
    
    @Override
    public String getVersion() {
        return aa.b().c();
    }
    
    @Override
    public String getVersionId() {
        return aa.b().b();
    }
    
    @Override
    public int getProtocolVersion() {
        return aa.c();
    }
    
    @Override
    public int getDataVersion() {
        return aa.b().d().c();
    }
    
    @Intrinsic
    public String minecraft$getVersionType() {
        return this.shadow$j();
    }
    
    @Shadow
    public abstract String shadow$j();
    
    @Intrinsic
    public boolean minecraft$isDemo() {
        return this.shadow$K();
    }
    
    @Shadow
    public abstract boolean shadow$K();
    
    @Override
    public ClientPacketListener getClientPacketListener() {
        return (ClientPacketListener)this.L();
    }
    
    @Override
    public ClientWorld clientWorld() {
        if (this.labyMod$clientWorld == null) {
            this.labyMod$clientWorld = Laby.references().clientWorld();
        }
        return this.labyMod$clientWorld;
    }
    
    @Nullable
    @Override
    public Scoreboard getScoreboard() {
        return (this.r != null) ? ((Scoreboard)this.r.M()) : null;
    }
    
    @Override
    public MinecraftAuthenticator authenticator() {
        if (this.labyMod$authenticator == null) {
            this.labyMod$authenticator = Laby.references().minecraftAuthenticator();
        }
        return this.labyMod$authenticator;
    }
    
    @Override
    public TabList getTabList() {
        return (this.l == null) ? null : ((TabList)this.l.h());
    }
    
    @Override
    public void updateWindowTitle() {
        ffg.Q().d();
    }
    
    @Override
    public void openChat(final String defaultInput) {
        final flu chatScreen = new flu("");
        ffg.Q().a((fne)chatScreen);
        this.chatExecutor().insertText(defaultInput, true);
    }
    
    @Override
    public MinecraftFrameTimer frameTimer() {
        return null;
    }
    
    @Redirect(method = { "runTick" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/DebugScreenOverlay;logFrameDuration(J)V"))
    private void labyMod$logFrameTime(final fhn overlay, final long sample) {
        overlay.a(sample);
        this.labyMod$frameTimeLogger.log(sample);
    }
    
    @Override
    public SampleLogger frameTimeLogger() {
        return this.labyMod$frameTimeLogger;
    }
    
    @Override
    public boolean isMouseLocked() {
        return this.n.h();
    }
    
    @Override
    public MouseHandlerAccessor mouseHandler() {
        return (MouseHandlerAccessor)this.n;
    }
    
    @Override
    public RenderTarget mainTarget() {
        if (this.labyMod$wrappedMainTarget == null) {
            this.labyMod$wrappedMainTarget = new VersionedRenderTarget(this.ao);
        }
        return this.labyMod$wrappedMainTarget;
    }
    
    @Override
    public String getClipboard() {
        return this.o.a();
    }
    
    @Override
    public void setClipboard(final String value) {
        this.o.a(value);
    }
    
    @Override
    public boolean isDestroying() {
        return this.q != null && this.q.k();
    }
    
    @Override
    public float getDestroyProgress() {
        if (this.q == null) {
            return 0.0f;
        }
        return this.q.f;
    }
    
    @Override
    public void reloadResources() {
        this.l();
    }
    
    @Override
    public WorldRenderer worldRenderer() {
        return (WorldRenderer)this.f;
    }
    
    @Override
    public ItemStackRenderer itemStackRenderer() {
        return (ItemStackRenderer)this.U;
    }
    
    @Override
    public EntityRenderDispatcher entityRenderDispatcher() {
        return (EntityRenderDispatcher)this.T;
    }
    
    @Override
    public boolean isMacOS() {
        return ffg.a;
    }
    
    @Override
    public long getRunningMillis() {
        return ac.c();
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
    public void refreshRealmsClient() {
        this.aN = new fcf(fap.a());
    }
    
    @Override
    public void crashGame(final GameCrashReport report) {
        a(ffg.Q(), ffg.Q().p, report.crashReport());
    }
    
    @Override
    public void shutdownGame() {
        this.n();
    }
    
    @Override
    public GameMode gameMode() {
        return (this.q == null) ? GameMode.UNKNOWN : GameMode.fromId(this.q.j().a());
    }
    
    @Override
    public boolean isLoadingOverlayPresent() {
        return this.aL() != null;
    }
    
    @Override
    public HitResult getHitResult() {
        if (this.v == null) {
            return null;
        }
        return (HitResult)this.v;
    }
    
    @Insert(method = { "runTick" }, at = @At("HEAD"))
    private void labyMod$updateMouse(final boolean b, final InsertInfo callback) {
        final ezd window = ffg.Q().aO();
        final double x = this.n.e() * window.o() / window.m();
        final double y = this.n.f() * window.p() / window.n();
        this.labyMod$mouseBridge().updateMouse(x, y);
    }
    
    @Insert(method = { "startUseItem" }, at = @At("HEAD"))
    private void labyMod$fireGameUseItemEvent(final InsertInfo ci) {
        this.labymod$isLastItemUsed = false;
        this.labymod$isLastBlockUsed = false;
    }
    
    @Inject(method = { "startUseItem" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;swing(Lnet/minecraft/world/InteractionHand;)V", ordinal = 1, shift = At.Shift.AFTER) })
    private void labyMod$fireGameUseItemEventBlock(final CallbackInfo ci) {
        this.labymod$isLastBlockUsed = true;
    }
    
    @Inject(method = { "startUseItem" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;useItem(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;", shift = At.Shift.AFTER) })
    private void labyMod$fireGameUseItemEventItem(final CallbackInfo ci) {
        this.labymod$isLastItemUsed = true;
    }
    
    @Override
    public void updateBlockBreak(final boolean leftClick) {
        this.shadow$d(leftClick);
    }
}
