// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client;

import org.spongepowered.asm.mixin.Overwrite;
import java.util.Date;
import java.text.SimpleDateFormat;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.render.GameRenderEvent;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.core.event.client.lifecycle.GameFpsLimitEventCaller;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.api.event.client.lifecycle.GameShutdownEvent;
import net.labymod.api.event.client.world.WorldLoadEvent;
import net.labymod.api.configuration.labymod.main.laby.AppearanceConfig;
import java.util.Optional;
import net.labymod.v1_17_1.client.overlay.CustomLoadingOverlay;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.api.configuration.labymod.model.FadeOutAnimationType;
import java.util.function.Consumer;
import net.labymod.api.event.client.gui.screen.ScreenResizeEvent;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.ElementActivity;
import net.labymod.v1_17_1.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.event.Phase;
import net.labymod.api.volt.callback.InsertInfo;
import org.lwjgl.glfw.GLFWVidMode;
import net.labymod.core.client.WindowController;
import net.labymod.api.util.Pair;
import org.lwjgl.glfw.GLFW;
import net.labymod.core.main.LabyMod;
import java.io.File;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.event.client.gui.window.WindowResizeEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.Laby;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import java.net.Proxy;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dvp.class })
public abstract class MixinMinecraft
{
    @Shadow
    @Final
    private dpr O;
    @Shadow
    @Nullable
    private faq aG;
    @Shadow
    @Nullable
    private ejn aH;
    @Shadow
    @Nullable
    public emm s;
    @Shadow
    @Final
    public dvt l;
    @Shadow
    private int aK;
    
    @Shadow
    @Nullable
    public abstract ejj w();
    
    @Shadow
    public abstract boolean ah();
    
    @Shadow
    public static dvp C() {
        return null;
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/main/GameConfig$UserData;proxy:Ljava/net/Proxy;"))
    private Proxy labyMod$callGameInitializeResource(final eey.d userData) {
        Laby.fireEvent(new GameInitializeEvent(GameInitializeEvent.Lifecycle.RESOURCE_INITIALIZATION));
        return userData.d;
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/User;getSessionId()Ljava/lang/String;"))
    private String labyMod$censorSessionId(final dwd instance) {
        return "********************************";
    }
    
    @Inject(method = { "resizeDisplay" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;setGuiScale(D)V", shift = At.Shift.AFTER) })
    private void labyMod$updateSizeOfAreas(final CallbackInfo ci) {
        Laby.fireEventNextTick(new WindowResizeEvent());
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;gameDirectory:Ljava/io/File;", shift = At.Shift.BEFORE, ordinal = 0))
    private File labyMod$onPreGameStarted(final eey.a instance) {
        LabyMod.getInstance().onPreGameStarted();
        return instance.a;
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/VirtualScreen;newWindow(Lcom/mojang/blaze3d/platform/DisplayData;Ljava/lang/String;Ljava/lang/String;)Lcom/mojang/blaze3d/platform/Window;"))
    private dpr labyMod$adjustWindowSize(final enx instance, dpd displayData, final String $$1, final String $$2) {
        final Pair<Integer, Integer> sizePair = WindowController.calculateNewScreenSize(displayData.a, displayData.b, () -> {
            final GLFWVidMode glfwVidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            if (glfwVidMode == null) {
                return null;
            }
            else {
                return Pair.of(glfwVidMode.width(), glfwVidMode.height());
            }
        });
        if (sizePair != null) {
            displayData = new dpd((int)sizePair.getFirst(), (int)sizePair.getSecond(), displayData.c, displayData.d, displayData.e);
        }
        return instance.a(displayData, $$1, $$2);
    }
    
    @Inject(method = { "<init>" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;resizeDisplay()V", shift = At.Shift.BEFORE) })
    private void labyMod$maximizeDisplay(final eey $$0, final CallbackInfo ci) {
        WindowController.maximize(() -> {
            GLFW.glfwWindowHint(131075, 1);
            GLFW.glfwMaximizeWindow(this.O.i());
        });
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$firePostGameStartedInitializeEvent(final eey param0, final CallbackInfo ci) {
        Laby.fireEvent(new GameInitializeEvent(GameInitializeEvent.Lifecycle.POST_GAME_STARTED));
    }
    
    @Insert(method = { "tick()V" }, at = @At("HEAD"))
    private void labyMod$firePreTickEvent(final InsertInfo ci) {
        this.labyMod$fireTickEvent(Phase.PRE);
    }
    
    @Insert(method = { "tick()V" }, at = @At("TAIL"))
    private void labyMod$firePostTickEvent(final InsertInfo ci) {
        this.labyMod$fireTickEvent(Phase.POST);
    }
    
    @Redirect(method = { "createTitle" }, at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;toString()Ljava/lang/String;"))
    private String labyMod$modifiedTitle(final StringBuilder stringBuilder) {
        final StringBuilder extraBuilder = new StringBuilder();
        final ejj listener = this.w();
        if (listener != null && listener.a().h()) {
            extraBuilder.append(" - ");
            if (this.aG != null && !this.aG.o()) {
                extraBuilder.append(eyh.a("title.singleplayer", new Object[0]));
            }
            else if (this.ah()) {
                extraBuilder.append(eyh.a("title.multiplayer.realms", new Object[0]));
            }
            else if (this.aG == null && (this.aH == null || !this.aH.d())) {
                extraBuilder.append(eyh.a("title.multiplayer.other", new Object[0]));
            }
            else {
                extraBuilder.append(eyh.a("title.multiplayer.lan", new Object[0]));
            }
        }
        return LabyMod.getInstance().createTitle("1.17.1", extraBuilder.toString());
    }
    
    @Redirect(method = { "setScreen" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;removed()V"))
    private void labyMod$cancelRemovedScreen(final eaq screen) {
    }
    
    @ModifyVariable(method = { "setScreen" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;", ordinal = 2, shift = At.Shift.BEFORE))
    private eaq labyMod$fireScreenOpenEvent(eaq newScreen) {
        final eaq previousScreen = dvp.C().y;
        try {
            final ScreenInstance instance = Laby.fireEvent(new ScreenDisplayEvent(this.labyMod$createScreenWrapper(previousScreen), this.labyMod$createScreenWrapper(newScreen))).getScreen();
            newScreen = ((instance == null) ? null : ((eaq)instance.wrap().getVersionedScreen()));
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        if (previousScreen != null && newScreen != previousScreen) {
            previousScreen.e();
        }
        if (newScreen instanceof final LabyScreenRenderer labyScreenRenderer) {
            final LabyScreen screen = labyScreenRenderer.screen();
            if (screen instanceof final ElementActivity activity) {
                activity.onOpenScreen();
            }
        }
        return newScreen;
    }
    
    private ScreenWrapper labyMod$createScreenWrapper(final eaq screen) {
        return (screen == null) ? null : Laby.references().screenWrapperFactory().create(screen);
    }
    
    @Insert(method = { "resizeDisplay()V" }, at = @At("TAIL"))
    private void labyMod$fireScreenResizeEvent(final InsertInfo ci) {
        Laby.fireEvent(new ScreenResizeEvent(this.O.o(), this.O.p(), this.O.k(), this.O.l()));
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "NEW", target = "net/minecraft/client/gui/screens/LoadingOverlay"))
    private eae labyMod$createLoadingOverlay(final dvp minecraft, final adq reloadInstance, final Consumer onFinish, final boolean fadeIn) {
        final AppearanceConfig appearance = Laby.labyAPI().config().appearance();
        final FadeOutAnimationType type = appearance.fadeOutAnimation().get();
        return (type == FadeOutAnimationType.FADING && !DynamicBackgroundController.isEnabled()) ? new eae(minecraft, reloadInstance, onFinish, fadeIn) : new CustomLoadingOverlay(minecraft, reloadInstance, onFinish, fadeIn);
    }
    
    @Insert(method = { "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V" }, at = @At("HEAD"))
    private void labyMod$firePreNetworkDisconnectEvent(final eaq screen, final InsertInfo ci) {
        Laby.labyAPI().serverController().disconnect(Phase.PRE);
    }
    
    @Insert(method = { "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V" }, at = @At("TAIL"))
    private void labyMod$firePostNetworkDisconnectEvent(final eaq screen, final InsertInfo ci) {
        Laby.labyAPI().serverController().disconnect(Phase.POST);
    }
    
    @Insert(method = { "setLevel(Lnet/minecraft/client/multiplayer/ClientLevel;)V" }, at = @At("HEAD"))
    private void labyMod$firePreWorldLoadEvent(final eji level, final InsertInfo ci) {
        if (level != null) {
            Laby.fireEvent(new WorldLoadEvent(Phase.PRE));
        }
    }
    
    @Insert(method = { "setLevel(Lnet/minecraft/client/multiplayer/ClientLevel;)V" }, at = @At("TAIL"))
    private void labyMod$firePostWorldLoadEvent(final eji level, final InsertInfo ci) {
        if (level != null) {
            Laby.fireEvent(new WorldLoadEvent(Phase.POST));
        }
    }
    
    @Insert(method = { "destroy()V" }, at = @At("HEAD"))
    private void labyMod$fireGameShutdownEvent(final InsertInfo ci) {
        Laby.fireEvent(new GameShutdownEvent());
    }
    
    @Insert(method = { "getFramerateLimit" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireGameFpsLimitEvent(final InsertInfoReturnable<Integer> ci) {
        GameFpsLimitEventCaller.callEvent(ci);
    }
    
    @Redirect(method = { "runTick" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;render(FJZ)V"))
    private void labyMod$fireGameRenderEvent(final enb gameRenderer, final float partialTicks, final long nanoTime, final boolean running) {
        final dql poseStack = new dql();
        final Stack stack = ((VanillaStackAccessor)poseStack).stack();
        this.labyMod$fireGameRenderEvent(Phase.PRE, stack, partialTicks);
        gameRenderer.a(partialTicks, nanoTime, running);
        this.labyMod$fireGameRenderEvent(Phase.POST, stack, partialTicks);
    }
    
    private void labyMod$fireGameRenderEvent(final Phase phase, final Stack stack, final float partialTicks) {
        Laby.fireEvent(new GameRenderEvent(phase, stack, partialTicks));
        Laby.labyAPI().gfxRenderPipeline().gfx().blaze3DBufferSource().endBuffer();
    }
    
    private void labyMod$fireTickEvent(final Phase phase) {
        Laby.fireEvent(new GameTickEvent(phase));
    }
    
    @Overwrite
    public static void b(final q report) {
        final File dir = new File(C().o, "crash-reports");
        final File crashFile = new File(dir, "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
        Laby.fireEvent(new GameShutdownEvent(report.b(), crashFile));
        wy.a(report.e());
        if (report.f() != null) {
            wy.a("#@!@# Game crashed! Crash report saved to: #@!@# " + String.valueOf(report.f()));
            System.exit(-1);
        }
        else if (report.a(crashFile)) {
            wy.a("#@!@# Game crashed! Crash report saved to: #@!@# " + crashFile.getAbsolutePath());
            System.exit(-1);
        }
        else {
            wy.a("#@?@# Game crashed! Crash report could not be saved. #@?@#");
            System.exit(-2);
        }
    }
}
