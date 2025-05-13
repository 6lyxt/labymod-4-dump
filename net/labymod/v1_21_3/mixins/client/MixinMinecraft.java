// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client;

import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import org.joml.Matrix4fStack;
import net.labymod.api.event.client.render.GameRenderEvent;
import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.event.client.lifecycle.GameShutdownEvent;
import net.labymod.api.event.client.world.WorldLoadEvent;
import net.labymod.api.configuration.labymod.main.laby.AppearanceConfig;
import java.util.Optional;
import net.labymod.v1_21_3.client.overlay.CustomLoadingOverlay;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.api.configuration.labymod.model.FadeOutAnimationType;
import java.util.function.Consumer;
import net.labymod.api.event.client.gui.screen.ScreenResizeEvent;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.ElementActivity;
import net.labymod.v1_21_3.client.gui.screen.LabyScreenRenderer;
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

@Mixin({ fmg.class })
public abstract class MixinMinecraft
{
    @Shadow
    @Final
    private ffv S;
    @Shadow
    @Nullable
    private hfw aU;
    @Shadow
    @Nullable
    public gkh t;
    @Shadow
    @Final
    public fmk n;
    @Shadow
    private int aX;
    @Shadow
    @Final
    public File q;
    
    @Shadow
    @Nullable
    public abstract gfl L();
    
    @Shadow
    @javax.annotation.Nullable
    public abstract gfz S();
    
    @Shadow
    public static fmg Q() {
        return null;
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/User;getSessionId()Ljava/lang/String;"))
    private String labyMod$censorSessionId(final fms instance) {
        return "********************************";
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/main/GameConfig$UserData;proxy:Ljava/net/Proxy;"))
    private Proxy labyMod$callGameInitializeResource(final fzy.d userData) {
        Laby.fireEvent(new GameInitializeEvent(GameInitializeEvent.Lifecycle.RESOURCE_INITIALIZATION));
        return userData.d;
    }
    
    @Inject(method = { "resizeDisplay" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;setGuiScale(D)V", shift = At.Shift.AFTER) })
    private void labyMod$updateSizeOfAreas(final CallbackInfo ci) {
        Laby.fireEventNextTick(new WindowResizeEvent());
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;gameDirectory:Ljava/io/File;", shift = At.Shift.BEFORE, ordinal = 0))
    private File labyMod$onPreGameStarted(final fzy.a instance) {
        LabyMod.getInstance().onPreGameStarted();
        return instance.a;
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/VirtualScreen;newWindow(Lcom/mojang/blaze3d/platform/DisplayData;Ljava/lang/String;Ljava/lang/String;)Lcom/mojang/blaze3d/platform/Window;"))
    private ffv labyMod$adjustWindowSize(final gmk instance, ffi displayData, final String $$1, final String $$2) {
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
            displayData = new ffi((int)sizePair.getFirst(), (int)sizePair.getSecond(), displayData.c, displayData.d, displayData.e);
        }
        return instance.a(displayData, $$1, $$2);
    }
    
    @Inject(method = { "<init>" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;resizeDisplay()V", shift = At.Shift.BEFORE) })
    private void labyMod$maximizeDisplay(final fzy $$0, final CallbackInfo ci) {
        WindowController.maximize(() -> {
            GLFW.glfwWindowHint(131075, 1);
            GLFW.glfwMaximizeWindow(this.S.h());
        });
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$firePostGameStartedInitializeEvent(final fzy param0, final CallbackInfo ci) {
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
        final gfl listener = this.L();
        if (listener != null && listener.k().i()) {
            extraBuilder.append(" - ");
            final gfz currentServer = this.S();
            if (this.aU != null && !this.aU.r()) {
                extraBuilder.append(hcs.a("title.singleplayer", new Object[0]));
            }
            else if (currentServer != null && currentServer.e()) {
                extraBuilder.append(hcs.a("title.multiplayer.realms", new Object[0]));
            }
            else if (this.aU == null && (currentServer == null || !currentServer.d())) {
                extraBuilder.append(hcs.a("title.multiplayer.other", new Object[0]));
            }
            else {
                extraBuilder.append(hcs.a("title.multiplayer.lan", new Object[0]));
            }
        }
        return LabyMod.getInstance().createTitle("1.21.3", extraBuilder.toString());
    }
    
    @Redirect(method = { "setScreen" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;removed()V"))
    private void labyMod$cancelRemovedScreen(final fty screen) {
    }
    
    @ModifyVariable(method = { "setScreen" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;", ordinal = 2, shift = At.Shift.BEFORE))
    private fty labyMod$fireScreenOpenEvent(fty newScreen) {
        final fty previousScreen = fmg.Q().z;
        try {
            final ScreenInstance instance = Laby.fireEvent(new ScreenDisplayEvent(this.labyMod$createScreenWrapper(previousScreen), this.labyMod$createScreenWrapper(newScreen))).getScreen();
            newScreen = ((instance == null) ? null : ((fty)instance.wrap().getVersionedScreen()));
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        if (previousScreen != null && newScreen != previousScreen) {
            previousScreen.aJ_();
        }
        if (newScreen instanceof final LabyScreenRenderer labyScreenRenderer) {
            final LabyScreen screen = labyScreenRenderer.screen();
            if (screen instanceof final ElementActivity activity) {
                activity.onOpenScreen();
            }
        }
        return newScreen;
    }
    
    private ScreenWrapper labyMod$createScreenWrapper(final fty screen) {
        return (screen == null) ? null : Laby.references().screenWrapperFactory().create(screen);
    }
    
    @Insert(method = { "resizeDisplay()V" }, at = @At("TAIL"))
    private void labyMod$fireScreenResizeEvent(final InsertInfo ci) {
        Laby.fireEvent(new ScreenResizeEvent(this.S.o(), this.S.p(), this.S.k(), this.S.l()));
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "NEW", target = "net/minecraft/client/gui/screens/LoadingOverlay"))
    private fto labyMod$createLoadingOverlay(final fmg minecraft, final avr reloadInstance, final Consumer onFinish, final boolean fadeIn) {
        final AppearanceConfig appearance = Laby.labyAPI().config().appearance();
        final FadeOutAnimationType type = appearance.fadeOutAnimation().get();
        return (type == FadeOutAnimationType.FADING && !DynamicBackgroundController.isEnabled()) ? new fto(minecraft, reloadInstance, onFinish, fadeIn) : new CustomLoadingOverlay(minecraft, reloadInstance, onFinish, fadeIn);
    }
    
    @Insert(method = { "disconnect(Lnet/minecraft/client/gui/screens/Screen;Z)V" }, at = @At("HEAD"))
    private void labyMod$firePreNetworkDisconnectEvent(final fty screen, final boolean transferring, final InsertInfo ci) {
        Laby.labyAPI().serverController().disconnect(Phase.PRE);
    }
    
    @Insert(method = { "disconnect(Lnet/minecraft/client/gui/screens/Screen;Z)V" }, at = @At("TAIL"))
    private void labyMod$firePostNetworkDisconnectEvent(final fty screen, final boolean transferring, final InsertInfo ci) {
        Laby.labyAPI().serverController().disconnect(Phase.POST);
    }
    
    @Insert(method = { "setLevel" }, at = @At("HEAD"))
    private void labyMod$firePreWorldLoadEvent(final gfk level, final ftw.a reason, final InsertInfo ci) {
        if (level != null) {
            Laby.fireEvent(new WorldLoadEvent(Phase.PRE));
        }
    }
    
    @Insert(method = { "setLevel" }, at = @At("TAIL"))
    private void labyMod$firePostWorldLoadEvent(final gfk level, final ftw.a reason, final InsertInfo ci) {
        if (level != null) {
            Laby.fireEvent(new WorldLoadEvent(Phase.POST));
        }
    }
    
    @Insert(method = { "destroy()V" }, at = @At("HEAD"))
    private void labyMod$fireGameShutdownEvent(final InsertInfo ci) {
        Laby.fireEvent(new GameShutdownEvent());
    }
    
    @Redirect(method = { "runTick" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;render(Lnet/minecraft/client/DeltaTracker;Z)V"))
    private void labyMod$fireGameRenderEvent(final glb gameRenderer, final flw deltaTracker, final boolean running) {
        final fgs poseStack = new fgs();
        final Stack stack = ((VanillaStackAccessor)poseStack).stack();
        final float realtimeDeltaTicks = deltaTracker.b();
        this.labyMod$fireGameRenderEvent(Phase.PRE, stack, realtimeDeltaTicks);
        gameRenderer.a(deltaTracker, running);
        this.labyMod$fireGameRenderEvent(Phase.POST, stack, realtimeDeltaTicks);
    }
    
    private void labyMod$fireGameRenderEvent(final Phase phase, final Stack stack, final float partialTicks) {
        final Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushMatrix();
        modelViewStack.translate(0.0f, 0.0f, -2000.0f);
        Laby.fireEvent(new GameRenderEvent(phase, stack, partialTicks));
        Laby.labyAPI().gfxRenderPipeline().gfx().blaze3DBufferSource().endBuffer();
        modelViewStack.popMatrix();
    }
    
    private void labyMod$fireTickEvent(final Phase phase) {
        Laby.fireEvent(new GameTickEvent(phase));
    }
    
    @Overwrite
    public static void a(final fmg mc, final File file, final o report) {
        final File dir = new File(file, "crash-reports");
        final File crashFile = new File(dir, "crash-" + ae.f() + "-client.txt");
        Laby.fireEvent(new GameShutdownEvent(report.b(), crashFile));
        amb.a(report.a(y.a));
        if (mc != null) {
            mc.ak().h();
        }
        if (report.e() != null) {
            amb.a("#@!@# Game crashed! Crash report saved to: #@!@# " + String.valueOf(report.e()));
            System.exit(-1);
        }
        else if (report.a(crashFile.toPath(), y.a)) {
            amb.a("#@!@# Game crashed! Crash report saved to: #@!@# " + crashFile.getAbsolutePath());
            System.exit(-1);
        }
        else {
            amb.a("#@?@# Game crashed! Crash report could not be saved. #@?@#");
            System.exit(-2);
        }
    }
}
