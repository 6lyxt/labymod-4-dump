// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client;

import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.render.GameRenderEvent;
import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.core.event.client.lifecycle.GameFpsLimitEventCaller;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.event.client.lifecycle.GameShutdownEvent;
import net.labymod.api.event.client.world.WorldLoadEvent;
import net.labymod.api.configuration.labymod.main.laby.AppearanceConfig;
import java.util.Optional;
import net.labymod.v1_20_1.client.overlay.CustomLoadingOverlay;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.api.configuration.labymod.model.FadeOutAnimationType;
import java.util.function.Consumer;
import net.labymod.api.event.client.gui.screen.ScreenResizeEvent;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.ElementActivity;
import net.labymod.v1_20_1.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.event.Phase;
import net.labymod.api.volt.callback.InsertInfo;
import org.lwjgl.glfw.GLFWVidMode;
import net.labymod.core.client.WindowController;
import net.labymod.api.util.Pair;
import org.lwjgl.glfw.GLFW;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.event.client.gui.window.WindowResizeEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.Laby;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import java.net.Proxy;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.io.File;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ enn.class })
public abstract class MixinMinecraft
{
    @Shadow
    @Final
    private ehn Q;
    @Shadow
    @Nullable
    private fyp aN;
    @Shadow
    @Nullable
    public fiy t;
    @Shadow
    @Final
    public enr m;
    @Shadow
    private int aQ;
    @Shadow
    @Final
    public File p;
    
    @Shadow
    @Nullable
    public abstract fex I();
    
    @Shadow
    public abstract boolean ar();
    
    @Shadow
    @Nullable
    public abstract ffd P();
    
    @Shadow
    public static enn N() {
        return null;
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/User;getSessionId()Ljava/lang/String;"))
    private String labyMod$censorSessionId(final eoc instance) {
        return "********************************";
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/main/GameConfig$UserData;proxy:Ljava/net/Proxy;"))
    private Proxy labyMod$callGameInitializeResource(final ezy.d userData) {
        Laby.fireEvent(new GameInitializeEvent(GameInitializeEvent.Lifecycle.RESOURCE_INITIALIZATION));
        return userData.d;
    }
    
    @Inject(method = { "resizeDisplay" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;setGuiScale(D)V", shift = At.Shift.AFTER) })
    private void labyMod$updateSizeOfAreas(final CallbackInfo ci) {
        Laby.fireEventNextTick(new WindowResizeEvent());
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;gameDirectory:Ljava/io/File;", shift = At.Shift.BEFORE, ordinal = 0))
    private File labyMod$onPreGameStarted(final ezy.a instance) {
        LabyMod.getInstance().onPreGameStarted();
        return instance.a;
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/VirtualScreen;newWindow(Lcom/mojang/blaze3d/platform/DisplayData;Ljava/lang/String;Ljava/lang/String;)Lcom/mojang/blaze3d/platform/Window;"))
    private ehn labyMod$adjustWindowSize(final fkm instance, eha displayData, final String $$1, final String $$2) {
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
            displayData = new eha((int)sizePair.getFirst(), (int)sizePair.getSecond(), displayData.c, displayData.d, displayData.e);
        }
        return instance.a(displayData, $$1, $$2);
    }
    
    @Inject(method = { "<init>" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;resizeDisplay()V", shift = At.Shift.BEFORE) })
    private void labyMod$maximizeDisplay(final ezy $$0, final CallbackInfo ci) {
        WindowController.maximize(() -> {
            GLFW.glfwWindowHint(131075, 1);
            GLFW.glfwMaximizeWindow(this.Q.i());
        });
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$firePostGameStartedInitializeEvent(final ezy param0, final CallbackInfo ci) {
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
        final fex listener = this.I();
        if (listener != null && listener.g().h()) {
            extraBuilder.append(" - ");
            if (this.aN != null && !this.aN.p()) {
                extraBuilder.append(fvz.a("title.singleplayer", new Object[0]));
            }
            else if (this.ar()) {
                extraBuilder.append(fvz.a("title.multiplayer.realms", new Object[0]));
            }
            else {
                final ffd currentServer = this.P();
                if (this.aN == null && (currentServer == null || !currentServer.d())) {
                    extraBuilder.append(fvz.a("title.multiplayer.other", new Object[0]));
                }
                else {
                    extraBuilder.append(fvz.a("title.multiplayer.lan", new Object[0]));
                }
            }
        }
        return LabyMod.getInstance().createTitle("1.20.1", extraBuilder.toString());
    }
    
    @Redirect(method = { "setScreen" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;removed()V"))
    private void labyMod$cancelRemovedScreen(final euq screen) {
    }
    
    @ModifyVariable(method = { "setScreen" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;", ordinal = 2, shift = At.Shift.BEFORE))
    private euq labyMod$fireScreenOpenEvent(euq newScreen) {
        final euq previousScreen = enn.N().z;
        try {
            final ScreenInstance instance = Laby.fireEvent(new ScreenDisplayEvent(this.labyMod$createScreenWrapper(previousScreen), this.labyMod$createScreenWrapper(newScreen))).getScreen();
            newScreen = ((instance == null) ? null : ((euq)instance.wrap().getVersionedScreen()));
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        if (previousScreen != null && newScreen != previousScreen) {
            previousScreen.ax_();
        }
        if (newScreen instanceof final LabyScreenRenderer labyScreenRenderer) {
            final LabyScreen screen = labyScreenRenderer.screen();
            if (screen instanceof final ElementActivity activity) {
                activity.onOpenScreen();
            }
        }
        return newScreen;
    }
    
    private ScreenWrapper labyMod$createScreenWrapper(final euq screen) {
        return (screen == null) ? null : Laby.references().screenWrapperFactory().create(screen);
    }
    
    @Insert(method = { "resizeDisplay()V" }, at = @At("TAIL"))
    private void labyMod$fireScreenResizeEvent(final InsertInfo ci) {
        Laby.fireEvent(new ScreenResizeEvent(this.Q.o(), this.Q.p(), this.Q.k(), this.Q.l()));
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "NEW", target = "net/minecraft/client/gui/screens/LoadingOverlay"))
    private eud labyMod$createLoadingOverlay(final enn minecraft, final akt reloadInstance, final Consumer onFinish, final boolean fadeIn) {
        final AppearanceConfig appearance = Laby.labyAPI().config().appearance();
        final FadeOutAnimationType type = appearance.fadeOutAnimation().get();
        return (type == FadeOutAnimationType.FADING && !DynamicBackgroundController.isEnabled()) ? new eud(minecraft, reloadInstance, onFinish, fadeIn) : new CustomLoadingOverlay(minecraft, reloadInstance, onFinish, fadeIn);
    }
    
    @Insert(method = { "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V" }, at = @At("HEAD"))
    private void labyMod$firePreNetworkDisconnectEvent(final euq screen, final InsertInfo ci) {
        Laby.labyAPI().serverController().disconnect(Phase.PRE);
    }
    
    @Insert(method = { "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V" }, at = @At("TAIL"))
    private void labyMod$firePostNetworkDisconnectEvent(final euq screen, final InsertInfo ci) {
        Laby.labyAPI().serverController().disconnect(Phase.POST);
    }
    
    @Insert(method = { "setLevel(Lnet/minecraft/client/multiplayer/ClientLevel;)V" }, at = @At("HEAD"))
    private void labyMod$firePreWorldLoadEvent(final few level, final InsertInfo ci) {
        if (level != null) {
            Laby.fireEvent(new WorldLoadEvent(Phase.PRE));
        }
    }
    
    @Insert(method = { "setLevel(Lnet/minecraft/client/multiplayer/ClientLevel;)V" }, at = @At("TAIL"))
    private void labyMod$firePostWorldLoadEvent(final few level, final InsertInfo ci) {
        if (level != null) {
            Laby.fireEvent(new WorldLoadEvent(Phase.POST));
        }
    }
    
    @Insert(method = { "destroy()V" }, at = @At("HEAD"))
    private void labyMod$fireGameShutdownEvent(final InsertInfo ci) {
        Laby.fireEvent(new GameShutdownEvent());
    }
    
    @Redirect(method = { "runTick" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;render(FJZ)V"))
    private void labyMod$fireGameRenderEvent(final fjq gameRenderer, final float partialTicks, final long nanoTime, final boolean running) {
        final eij poseStack = new eij();
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
        final eij modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.a();
        modelViewStack.a(0.0f, 0.0f, -2000.0f);
        RenderSystem.applyModelViewMatrix();
        Laby.fireEvent(new GameRenderEvent(phase, stack, partialTicks));
        Laby.labyAPI().gfxRenderPipeline().gfx().blaze3DBufferSource().endBuffer();
        modelViewStack.b();
        RenderSystem.applyModelViewMatrix();
    }
    
    private void labyMod$fireTickEvent(final Phase phase) {
        Laby.fireEvent(new GameTickEvent(phase));
    }
    
    @Overwrite
    public static void c(final o report) {
        final File dir = new File(N().p, "crash-reports");
        final File crashFile = new File(dir, "crash-" + ac.e() + "-client.txt");
        Laby.fireEvent(new GameShutdownEvent(report.b(), crashFile));
        acs.a(report.e());
        if (report.f() != null) {
            acs.a("#@!@# Game crashed! Crash report saved to: #@!@# " + String.valueOf(report.f()));
            System.exit(-1);
        }
        else if (report.a(crashFile)) {
            acs.a("#@!@# Game crashed! Crash report saved to: #@!@# " + crashFile.getAbsolutePath());
            System.exit(-1);
        }
        else {
            acs.a("#@?@# Game crashed! Crash report could not be saved. #@?@#");
            System.exit(-2);
        }
    }
}
