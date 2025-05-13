// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client;

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.crash.GameCrashReport;
import net.labymod.api.client.entity.EntityRenderDispatcher;
import net.labymod.api.client.render.ItemStackRenderer;
import net.labymod.api.client.world.WorldRenderer;
import net.labymod.v1_20_1.client.gfx.pipeline.target.VersionedRenderTarget;
import net.labymod.api.client.gui.mouse.MouseHandlerAccessor;
import net.labymod.api.profiler.SampleLogger;
import net.labymod.api.client.MinecraftFrameTimer;
import net.labymod.api.client.scoreboard.TabList;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.network.ClientPacketListener;
import org.spongepowered.asm.mixin.Intrinsic;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.core.main.LabyMod;
import net.labymod.v1_20_1.client.resources.texture.VersionedMinecraftTextures;
import net.labymod.v1_20_1.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.Laby;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.v1_20_1.client.gui.window.VersionedWindow;
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
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.Minecraft;

@Mixin({ enn.class })
@Implements({ @Interface(iface = Minecraft.class, prefix = "minecraft$", remap = Interface.Remap.NONE) })
public abstract class MixinMinecraftAPI implements Minecraft
{
    @Shadow
    private static int bb;
    @Mutable
    @Shadow
    @Final
    private eoc W;
    @Final
    @Shadow
    public eno n;
    @Shadow
    public fiy t;
    @Shadow
    private boolean aR;
    @Shadow
    private float aS;
    @Shadow
    @Final
    private eoa R;
    @Shadow
    public few s;
    @Final
    @Shadow
    public enr m;
    @Shadow
    public euq z;
    @Shadow
    @Final
    public eow l;
    @Shadow
    public enm o;
    @Shadow
    @Final
    public aoo q;
    @Shadow
    @Final
    private aku ai;
    @Shadow
    @Final
    private egv ap;
    private ComponentMapper labyMod$componentMapper;
    @Shadow
    @Nullable
    public eeg w;
    @Shadow
    @Nullable
    public ffa r;
    @Shadow
    private fpw U;
    @Shadow
    private fow T;
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
    public fjv f;
    @Shadow
    @Final
    public fjq j;
    @Mutable
    @Shadow
    @Final
    private ekq aL;
    
    @Shadow
    public static enn N() {
        return null;
    }
    
    @Shadow
    public abstract fex I();
    
    @Shadow
    public abstract euk aJ();
    
    @Override
    public int getFPS() {
        return MixinMinecraftAPI.bb;
    }
    
    @Shadow
    public abstract CompletableFuture<Void> j();
    
    @Shadow
    private static void c(final o par1) {
    }
    
    @Shadow
    public abstract void a(@Nullable final euq p0);
    
    @Shadow
    protected abstract void shadow$g(final boolean p0);
    
    @Shadow
    public abstract void l();
    
    @Override
    public Window minecraftWindow() {
        if (this.labyMod$minecraftWindow == null) {
            this.labyMod$minecraftWindow = new VersionedWindow();
        }
        return this.labyMod$minecraftWindow;
    }
    
    @Override
    public ClientPlayer getClientPlayer() {
        return (ClientPlayer)this.t;
    }
    
    @Override
    public Entity getTargetEntity() {
        return (Entity)enn.N().v;
    }
    
    @Override
    public Entity getCameraEntity() {
        return (Entity)enn.N().al();
    }
    
    @Override
    public MinecraftCamera getCamera() {
        return (enn.N().al() != null) ? ((MinecraftCamera)this.j.m()) : null;
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
        N().execute(runnable);
    }
    
    @Override
    public void executeNextTick(final Runnable runnable) {
        N().i(runnable);
    }
    
    @Override
    public boolean isOnRenderThread() {
        return N().bl();
    }
    
    @Override
    public void setSessionRaw(final Object gameSession) {
        this.W = (eoc)gameSession;
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
        return enn.N().R();
    }
    
    @Override
    public boolean isIngame() {
        return enn.N().s != null;
    }
    
    @Override
    public void updateKeyRepeatingMode(final boolean enabled) {
    }
    
    @Override
    public String getTranslation(final String translationKey) {
        return qm.a().a(translationKey);
    }
    
    @Override
    public boolean hasTranslation(final String translationKey) {
        return qm.a().b(translationKey);
    }
    
    @Override
    public float getPartialTicks() {
        return this.aR ? this.aS : this.R.a;
    }
    
    @Override
    public float getTickDelta() {
        return this.R.b;
    }
    
    @Override
    public boolean isPaused() {
        return this.aR;
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
        enn.N().f.r();
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
        return this.shadow$h();
    }
    
    @Shadow
    public abstract String shadow$h();
    
    @Intrinsic
    public boolean minecraft$isDemo() {
        return this.shadow$H();
    }
    
    @Shadow
    public abstract boolean shadow$H();
    
    @Override
    public ClientPacketListener getClientPacketListener() {
        return (ClientPacketListener)this.I();
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
        return (this.s != null) ? ((Scoreboard)this.s.I()) : null;
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
        enn.N().c();
    }
    
    @Override
    public void openChat(final String defaultInput) {
        final eti chatScreen = new eti("");
        enn.N().a((euq)chatScreen);
        this.chatExecutor().insertText(defaultInput, true);
    }
    
    @Override
    public MinecraftFrameTimer frameTimer() {
        return (MinecraftFrameTimer)this.q;
    }
    
    @Override
    public SampleLogger frameTimeLogger() {
        return ((MinecraftFrameTimer)this.q).logger();
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
            this.labyMod$wrappedMainTarget = new VersionedRenderTarget(this.ap);
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
        return this.r != null && this.r.m();
    }
    
    @Override
    public float getDestroyProgress() {
        if (this.r == null) {
            return 0.0f;
        }
        return this.r.f;
    }
    
    @Override
    public void reloadResources() {
        this.j();
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
        return enn.a;
    }
    
    @Override
    public long getRunningMillis() {
        return ac.b();
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
        this.aL = new ekq(eiz.a());
    }
    
    @Override
    public void crashGame(final GameCrashReport report) {
        c(report.crashReport());
    }
    
    @Override
    public void shutdownGame() {
        this.l();
    }
    
    @Override
    public GameMode gameMode() {
        return (this.r == null) ? GameMode.UNKNOWN : GameMode.fromId(this.r.l().a());
    }
    
    @Override
    public boolean isLoadingOverlayPresent() {
        return this.aJ() != null;
    }
    
    @Override
    public HitResult getHitResult() {
        if (this.w == null) {
            return null;
        }
        return (HitResult)this.w;
    }
    
    @Override
    public void updateBlockBreak(final boolean leftClick) {
        this.shadow$g(leftClick);
    }
    
    @Insert(method = { "runTick" }, at = @At("HEAD"))
    private void labyMod$updateMouse(final boolean b, final InsertInfo callback) {
        final ehn window = enn.N().aM();
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
}
