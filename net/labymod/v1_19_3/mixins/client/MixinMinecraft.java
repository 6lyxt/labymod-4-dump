// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client;

import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.render.GameRenderEvent;
import net.labymod.core.event.client.lifecycle.GameFpsLimitEventCaller;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.event.client.lifecycle.GameShutdownEvent;
import net.labymod.api.event.client.world.WorldLoadEvent;
import net.labymod.api.configuration.labymod.main.laby.AppearanceConfig;
import java.util.Optional;
import net.labymod.v1_19_3.client.overlay.CustomLoadingOverlay;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.api.configuration.labymod.model.FadeOutAnimationType;
import java.util.function.Consumer;
import net.labymod.api.event.client.gui.screen.ScreenResizeEvent;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.ElementActivity;
import net.labymod.v1_19_3.client.gui.screen.LabyScreenRenderer;
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
import net.labymod.api.Laby;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import java.net.Proxy;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ejf.class })
public abstract class MixinMinecraft
{
    @Shadow
    @Final
    private edh Q;
    @Shadow
    @Nullable
    private fse aN;
    @Shadow
    @Nullable
    public fcz t;
    @Shadow
    @Final
    public ejj m;
    @Shadow
    private int aQ;
    
    @Shadow
    @Nullable
    public abstract eza I();
    
    @Shadow
    public abstract boolean ar();
    
    @Shadow
    @Nullable
    public abstract ezg P();
    
    @Shadow
    public static ejf N() {
        return null;
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/User;getSessionId()Ljava/lang/String;"))
    private String labyMod$censorSessionId(final ejv instance) {
        return "********************************";
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/main/GameConfig$UserData;proxy:Ljava/net/Proxy;"))
    private Proxy labyMod$callGameInitializeResource(final eue.d userData) {
        Laby.fireEvent(new GameInitializeEvent(GameInitializeEvent.Lifecycle.RESOURCE_INITIALIZATION));
        return userData.d;
    }
    
    @Inject(method = { "resizeDisplay" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;setGuiScale(D)V", shift = At.Shift.AFTER) })
    private void labyMod$updateSizeOfAreas(final CallbackInfo ci) {
        Laby.fireEventNextTick(new WindowResizeEvent());
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;gameDirectory:Ljava/io/File;", shift = At.Shift.BEFORE, ordinal = 0))
    private File labyMod$onPreGameStarted(final eue.a instance) {
        LabyMod.getInstance().onPreGameStarted();
        return instance.a;
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/VirtualScreen;newWindow(Lcom/mojang/blaze3d/platform/DisplayData;Ljava/lang/String;Ljava/lang/String;)Lcom/mojang/blaze3d/platform/Window;"))
    private edh labyMod$adjustWindowSize(final fek instance, ecv displayData, final String $$1, final String $$2) {
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
            displayData = new ecv((int)sizePair.getFirst(), (int)sizePair.getSecond(), displayData.c, displayData.d, displayData.e);
        }
        return instance.a(displayData, $$1, $$2);
    }
    
    @Inject(method = { "<init>" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;resizeDisplay()V", shift = At.Shift.BEFORE) })
    private void labyMod$maximizeDisplay(final eue $$0, final CallbackInfo ci) {
        WindowController.maximize(() -> {
            GLFW.glfwWindowHint(131075, 1);
            GLFW.glfwMaximizeWindow(this.Q.i());
        });
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$firePostGameStartedInitializeEvent(final eue param0, final CallbackInfo ci) {
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
        final eza listener = this.I();
        if (listener != null && listener.a().h()) {
            extraBuilder.append(" - ");
            if (this.aN != null && !this.aN.p()) {
                extraBuilder.append(fpo.a("title.singleplayer", new Object[0]));
            }
            else if (this.ar()) {
                extraBuilder.append(fpo.a("title.multiplayer.realms", new Object[0]));
            }
            else {
                final ezg currentServer = this.P();
                if (this.aN == null && (currentServer == null || !currentServer.d())) {
                    extraBuilder.append(fpo.a("title.multiplayer.other", new Object[0]));
                }
                else {
                    extraBuilder.append(fpo.a("title.multiplayer.lan", new Object[0]));
                }
            }
        }
        return LabyMod.getInstance().createTitle("1.19.3", extraBuilder.toString());
    }
    
    @Redirect(method = { "setScreen" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;removed()V"))
    private void labyMod$cancelRemovedScreen(final epb screen) {
    }
    
    @ModifyVariable(method = { "setScreen" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;", ordinal = 2, shift = At.Shift.BEFORE))
    private epb labyMod$fireScreenOpenEvent(epb newScreen) {
        final epb previousScreen = ejf.N().z;
        try {
            final ScreenInstance instance = Laby.fireEvent(new ScreenDisplayEvent(this.labyMod$createScreenWrapper(previousScreen), this.labyMod$createScreenWrapper(newScreen))).getScreen();
            newScreen = ((instance == null) ? null : ((epb)instance.wrap().getVersionedScreen()));
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        if (previousScreen != null && newScreen != previousScreen) {
            previousScreen.g();
        }
        if (newScreen instanceof final LabyScreenRenderer labyScreenRenderer) {
            final LabyScreen screen = labyScreenRenderer.screen();
            if (screen instanceof final ElementActivity activity) {
                activity.onOpenScreen();
            }
        }
        return newScreen;
    }
    
    private ScreenWrapper labyMod$createScreenWrapper(final epb screen) {
        return (screen == null) ? null : Laby.references().screenWrapperFactory().create(screen);
    }
    
    @Insert(method = { "resizeDisplay()V" }, at = @At("TAIL"))
    private void labyMod$fireScreenResizeEvent(final InsertInfo ci) {
        Laby.fireEvent(new ScreenResizeEvent(this.Q.o(), this.Q.p(), this.Q.k(), this.Q.l()));
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "NEW", target = "net/minecraft/client/gui/screens/LoadingOverlay"))
    private eoo labyMod$createLoadingOverlay(final ejf minecraft, final aka reloadInstance, final Consumer onFinish, final boolean fadeIn) {
        final AppearanceConfig appearance = Laby.labyAPI().config().appearance();
        final FadeOutAnimationType type = appearance.fadeOutAnimation().get();
        return (type == FadeOutAnimationType.FADING && !DynamicBackgroundController.isEnabled()) ? new eoo(minecraft, reloadInstance, onFinish, fadeIn) : new CustomLoadingOverlay(minecraft, reloadInstance, onFinish, fadeIn);
    }
    
    @Insert(method = { "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V" }, at = @At("HEAD"))
    private void labyMod$firePreNetworkDisconnectEvent(final epb screen, final InsertInfo ci) {
        Laby.labyAPI().serverController().disconnect(Phase.PRE);
    }
    
    @Insert(method = { "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V" }, at = @At("TAIL"))
    private void labyMod$firePostNetworkDisconnectEvent(final epb screen, final InsertInfo ci) {
        Laby.labyAPI().serverController().disconnect(Phase.POST);
    }
    
    @Insert(method = { "setLevel(Lnet/minecraft/client/multiplayer/ClientLevel;)V" }, at = @At("HEAD"))
    private void labyMod$firePreWorldLoadEvent(final eyz level, final InsertInfo ci) {
        if (level != null) {
            Laby.fireEvent(new WorldLoadEvent(Phase.PRE));
        }
    }
    
    @Insert(method = { "setLevel(Lnet/minecraft/client/multiplayer/ClientLevel;)V" }, at = @At("TAIL"))
    private void labyMod$firePostWorldLoadEvent(final eyz level, final InsertInfo ci) {
        if (level != null) {
            Laby.fireEvent(new WorldLoadEvent(Phase.POST));
        }
    }
    
    @Insert(method = { "destroy()V" }, at = @At("HEAD"))
    private void labyMod$fireGameShutdownEvent(final InsertInfo ci) {
        Laby.fireEvent(new GameShutdownEvent());
    }
    
    @Redirect(method = { "runTick" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;render(FJZ)V"))
    private void labyMod$fireGameRenderEvent(final fdo gameRenderer, final float partialTicks, final long nanoTime, final boolean running) {
        final eed poseStack = new eed();
        final Stack stack = ((VanillaStackAccessor)poseStack).stack();
        this.labyMod$fireGameRenderEvent(Phase.PRE, stack, partialTicks);
        gameRenderer.a(partialTicks, nanoTime, running);
        this.labyMod$fireGameRenderEvent(Phase.POST, stack, partialTicks);
    }
    
    @Insert(method = { "getFramerateLimit" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireGameFpsLimitEvent(final InsertInfoReturnable<Integer> ci) {
        GameFpsLimitEventCaller.callEvent(ci);
    }
    
    private void labyMod$fireGameRenderEvent(final Phase phase, final Stack stack, final float partialTicks) {
        Laby.fireEvent(new GameRenderEvent(phase, stack, partialTicks));
        Laby.labyAPI().gfxRenderPipeline().gfx().blaze3DBufferSource().endBuffer();
    }
    
    private void labyMod$fireTickEvent(final Phase phase) {
        Laby.fireEvent(new GameTickEvent(phase));
    }
    
    @Overwrite
    public static void c(final n report) {
        final File dir = new File(N().p, "crash-reports");
        final File crashFile = new File(dir, "crash-" + aa.e() + "-client.txt");
        Laby.fireEvent(new GameShutdownEvent(report.b(), crashFile));
        ach.a(report.e());
        if (report.f() != null) {
            ach.a("#@!@# Game crashed! Crash report saved to: #@!@# " + String.valueOf(report.f()));
            System.exit(-1);
        }
        else if (report.a(crashFile)) {
            ach.a("#@!@# Game crashed! Crash report saved to: #@!@# " + crashFile.getAbsolutePath());
            System.exit(-1);
        }
        else {
            ach.a("#@?@# Game crashed! Crash report could not be saved. #@?@#");
            System.exit(-2);
        }
    }
}
