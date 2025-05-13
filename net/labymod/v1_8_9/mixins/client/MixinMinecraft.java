// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client;

import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.client.entity.player.ClientHotbarSlotChangeEvent;
import net.labymod.api.util.math.MathHelper;
import org.lwjgl.input.Mouse;
import net.labymod.api.event.client.gui.window.WindowResizeEvent;
import net.labymod.core.event.client.lifecycle.GameFpsLimitEventCaller;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.core.client.gui.window.DefaultAbstractWindow;
import net.labymod.api.event.client.render.GameRenderEvent;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.world.WorldLoadEvent;
import org.spongepowered.asm.mixin.Overwrite;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.File;
import net.labymod.api.event.client.lifecycle.GameShutdownEvent;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.event.client.gui.screen.ScreenResizeEvent;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import net.labymod.api.Laby;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_8_9.client.input.InputHandler;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.v1_8_9.client.VersionedMinecraft;
import net.labymod.v1_8_9.client.gui.screen.LabyScreenRenderer;
import org.lwjgl.input.Keyboard;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.event.Phase;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.event.client.chat.ChatScreenUpdateEvent;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.client.Minecraft;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ave.class })
@Implements({ @Interface(iface = Minecraft.class, prefix = "minecraft$", remap = Interface.Remap.NONE) })
public abstract class MixinMinecraft
{
    @Shadow
    private boolean T;
    @Shadow
    public axu m;
    @Shadow
    public bew h;
    @Shadow
    public bdb f;
    @Shadow
    public avh t;
    @Shadow
    public int d;
    @Shadow
    public int e;
    @Shadow
    private int ah;
    @Shadow
    private int ai;
    private final ChatScreenUpdateEvent tooltipChatScreenUpdateEvent;
    
    public MixinMinecraft() {
        this.tooltipChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.ITEM_TOOLTIPS);
    }
    
    @Shadow
    public static ave A() {
        return null;
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Session;getSessionID()Ljava/lang/String;"))
    private String labyMod$censorSessionId(final avm instance) {
        return "********************************";
    }
    
    @Insert(method = { "runTick()V" }, at = @At("HEAD"))
    private void labyMod$firePreTickEvent(final InsertInfo ci) {
        this.labyMod$fireTickEvent(Phase.PRE);
    }
    
    @Insert(method = { "runTick()V" }, at = @At("TAIL"))
    private void labyMod$firePostTickEvent(final InsertInfo ci) {
        this.labyMod$fireTickEvent(Phase.POST);
    }
    
    @Redirect(method = { "runTick()V" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;next()Z", remap = false))
    private boolean labyMod$fireKeyInputEvent() {
        if (!Keyboard.next()) {
            return false;
        }
        final LabyScreenRenderer screenRenderer = (this.m instanceof LabyScreenRenderer) ? ((LabyScreenRenderer)this.m) : null;
        return !((VersionedMinecraft)this).dispatchKeyboardInput(screenRenderer) || this.labyMod$fireKeyInputEvent();
    }
    
    @Inject(method = { "runTick()V" }, at = { @At(remap = false, value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventButton()I", shift = At.Shift.BEFORE) })
    private void labyMod$fireMouseKeyInputEvent(final CallbackInfo ci) {
        InputHandler.fireMouseInput(false);
    }
    
    @Inject(method = { "startGame" }, at = { @At("HEAD") })
    private void labyMod$firePreGameStartedInitializeEvent(final CallbackInfo ci) {
        LabyMod.getInstance().onPreGameStarted();
    }
    
    @Inject(method = { "runTick" }, at = { @At(value = "FIELD", opcode = 181, target = "Lnet/minecraft/client/settings/GameSettings;advancedItemTooltips:Z", shift = At.Shift.AFTER) })
    private void labyMod$refreshChatForAdvancedItemTooltips(final CallbackInfo ci) {
        Laby.fireEvent(this.tooltipChatScreenUpdateEvent);
    }
    
    @Inject(method = { "startGame" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;", shift = At.Shift.AFTER, ordinal = 0) })
    private void labyMod$fireResourceInitializationInitializeEvent(final CallbackInfo ci) {
        Laby.fireEvent(new GameInitializeEvent(GameInitializeEvent.Lifecycle.RESOURCE_INITIALIZATION));
    }
    
    @Inject(method = { "checkGLError" }, at = { @At("TAIL") })
    private void labyMod$firePostStartupInitializeEvent(final String s, final CallbackInfo ci) {
        if (!s.equalsIgnoreCase("Post startup")) {
            return;
        }
        LabyMod.getInstance().eventBus().fire(new GameInitializeEvent(GameInitializeEvent.Lifecycle.POST_STARTUP));
    }
    
    @Inject(method = { "startGame" }, at = { @At("TAIL") })
    private void labyMod$firePostGameStartedInitializeEvent(final CallbackInfo ci) {
        Laby.fireEvent(new GameInitializeEvent(GameInitializeEvent.Lifecycle.POST_GAME_STARTED));
    }
    
    @Redirect(method = { "createDisplay" }, at = @At(remap = false, value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"))
    private void labyMod$modifiedTitle(final String newTitle) {
        ((Minecraft)ave.A()).updateWindowTitle();
    }
    
    @Redirect(method = { "displayGuiScreen" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;onGuiClosed()V"))
    private void labyMod$cancelOnGuiClosed(final axu screen) {
    }
    
    @ModifyVariable(method = { "displayGuiScreen" }, index = 1, at = @At(value = "FIELD", opcode = 181, shift = At.Shift.BEFORE, ordinal = 1))
    public axu labyMod$fireScreenOpenEvent(axu newScreen) {
        final axu previousScreen = ave.A().m;
        try {
            final ScreenInstance instance = Laby.fireEvent(new ScreenDisplayEvent(this.labyMod$createScreenWrapper(previousScreen), this.labyMod$createScreenWrapper(newScreen))).getScreen();
            newScreen = ((instance == null) ? null : ((axu)instance.wrap().getVersionedScreen()));
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        if (previousScreen != null && newScreen != previousScreen) {
            previousScreen.m();
        }
        if (newScreen instanceof LabyScreenRenderer && ((LabyScreenRenderer)newScreen).screen() instanceof Activity) {
            ((LabyScreenRenderer)newScreen).screen().onOpenScreen();
        }
        return newScreen;
    }
    
    private ScreenWrapper labyMod$createScreenWrapper(final axu screen) {
        return (screen == null) ? null : Laby.references().screenWrapperFactory().create(screen);
    }
    
    @Redirect(method = { "displayGuiScreen" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;setWorldAndResolution(Lnet/minecraft/client/Minecraft;II)V"))
    public void labyMod$overrideInitScreen(final axu screen, final ave mc, final int width, final int height) {
        this.m.a(mc, width, height);
    }
    
    @Inject(method = { "resize" }, at = { @At("TAIL") })
    private void labyMod$fireScreenResizeEvent(final int rawWidth, final int rawHeight, final CallbackInfo ci) {
        final Window minecraftWindow = this.labyMod$getMinecraft().minecraftWindow();
        Laby.fireEvent(new ScreenResizeEvent(minecraftWindow.getScaledWidth(), minecraftWindow.getScaledHeight(), rawWidth, rawHeight));
    }
    
    @Inject(method = { "launchIntegratedServer" }, at = { @At("HEAD") })
    private void labyMod$disconnectFromServer(final String p_launchIntegratedServer_1_, final String p_launchIntegratedServer_2_, final adp p_launchIntegratedServer_3_, final CallbackInfo ci) {
        final bdb world = this.f;
        if (world != null) {
            world.H();
        }
    }
    
    @Inject(method = { "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V" }, at = { @At("HEAD") })
    private void labyMod$fireLoadWorldEvent(final bdb world, final String name, final CallbackInfo ci) {
        this.labyMod$fireLoadWorldEventPhase(world, Phase.PRE);
    }
    
    @Inject(method = { "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V" }, at = { @At("TAIL") })
    private void labyMod$firePostNetworkDisconnectEvent(final bdb world, final String name, final CallbackInfo ci) {
        this.labyMod$fireLoadWorldEventPhase(world, Phase.POST);
    }
    
    @Inject(method = { "shutdownMinecraftApplet" }, at = { @At(remap = false, value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;)V", shift = At.Shift.AFTER) })
    private void labyMod$fireGameShutdownEvent(final CallbackInfo ci) {
        Laby.fireEvent(new GameShutdownEvent());
    }
    
    @Overwrite
    public void c(final b report) {
        final File dir = new File(A().v, "crash-reports");
        final File crashFile = new File(dir, "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
        Laby.fireEvent(new GameShutdownEvent(report.b(), crashFile));
        kb.a(report.e());
        if (report.f() != null) {
            kb.a("#@!@# Game crashed! Crash report saved to: #@!@# " + String.valueOf(report.f()));
            System.exit(-1);
        }
        else if (report.a(crashFile)) {
            kb.a("#@!@# Game crashed! Crash report saved to: #@!@# " + crashFile.getAbsolutePath());
            System.exit(-1);
        }
        else {
            kb.a("#@?@# Game crashed! Crash report could not be saved. #@?@#");
            System.exit(-2);
        }
    }
    
    private void labyMod$fireLoadWorldEventPhase(final bdb worldClient, final Phase phase) {
        if (worldClient == null) {
            Laby.labyAPI().serverController().disconnect(phase);
        }
        else {
            Laby.fireEvent(new WorldLoadEvent(phase));
        }
    }
    
    private void labyMod$fireTickEvent(final Phase phase) {
        Laby.fireEvent(new GameTickEvent(phase));
    }
    
    @Inject(method = { "clickMouse" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$handleClickMouse(final CallbackInfo callbackInfo) {
        if (this.labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType.ATTACK)) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "rightClickMouse" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$handleRightClickMouse(final CallbackInfo callbackInfo) {
        if (this.labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType.INTERACT)) {
            callbackInfo.cancel();
        }
    }
    
    @Redirect(method = { "sendClickBlockToController" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;onPlayerDamageBlock(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z"))
    private boolean labyMod$handleSendClickBlockToController(final bda instance, final cj blockPos, final cq facing) {
        return !this.labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType.ATTACK) && instance.c(blockPos, facing);
    }
    
    @Redirect(method = { "runGameLoop" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;updateCameraAndRender(FJ)V"))
    private void labyMod$fireGameRenderEvent(final bfk gameRenderer, final float partialTicks, final long nanoTime) {
        bfl.a(516, 0.003921569f);
        this.labyMod$fireGameRenderEvent(Phase.PRE, VersionedStackProvider.DEFAULT_STACK, partialTicks);
        gameRenderer.a(partialTicks, nanoTime);
        bfl.a(516, 0.003921569f);
        this.labyMod$fireGameRenderEvent(Phase.POST, VersionedStackProvider.DEFAULT_STACK, partialTicks);
    }
    
    private void labyMod$fireGameRenderEvent(final Phase phase, final Stack stack, final float partialTicks) {
        Laby.fireEvent(new GameRenderEvent(phase, stack, partialTicks));
        Laby.labyAPI().gfxRenderPipeline().gfx().blaze3DBufferSource().endBuffer();
    }
    
    @Inject(method = { "toggleFullscreen" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;updateDisplay()V", shift = At.Shift.AFTER) })
    private void labyMod$toggleFullScreen(final CallbackInfo ci) {
        if (this.m != null) {
            return;
        }
        final Window minecraftWindow = this.labyMod$getMinecraft().minecraftWindow();
        ((DefaultAbstractWindow)minecraftWindow).resetBounds();
        Laby.fireEvent(new ScreenResizeEvent((int)(this.d / minecraftWindow.getScale()), (int)(this.e / minecraftWindow.getScale()), this.d, this.e));
    }
    
    @Insert(method = { "getLimitFramerate" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireGameFpsLimitEvent(final InsertInfoReturnable<Integer> ci) {
        GameFpsLimitEventCaller.callEvent(ci);
        if (ci.isCancelled()) {
            return;
        }
        ci.setReturnValue((this.f == null && this.m != null) ? 60 : this.t.g);
    }
    
    @Insert(method = { "resize" }, at = @At("TAIL"))
    private void labyMod$fireWindowResizeEvent(final int width, final int height, final InsertInfo ci) {
        Laby.fireEventNextTick(new WindowResizeEvent());
    }
    
    @Redirect(method = { "runTick" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventDWheel()I", remap = false))
    private int labyMod$fireHotbarSlotChangeEvent() {
        final int delta = Mouse.getEventDWheel();
        if (delta == 0) {
            return 0;
        }
        final int slotDelta = MathHelper.clamp(delta, -1, 1);
        final ClientHotbarSlotChangeEvent event = new ClientHotbarSlotChangeEvent(this.h.bi.c, slotDelta);
        final int originalToSlot = event.toSlot();
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            return 0;
        }
        if (event.toSlot() != originalToSlot && !this.h.v()) {
            this.h.bi.c = MathHelper.clamp(event.toSlot(), 0, 8);
            return 0;
        }
        return delta;
    }
    
    private boolean labyMod$fireClientPlayerInteractEvent(final ClientPlayerInteractEvent.InteractionType interactionType) {
        return Laby.fireEvent(new ClientPlayerInteractEvent((ClientPlayer)this.h, interactionType)).isCancelled();
    }
    
    private Minecraft labyMod$getMinecraft() {
        return (Minecraft)ave.A();
    }
}
