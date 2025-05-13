// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.screen;

import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.core.client.gui.screen.widget.window.debug.util.VersionedWidget;
import java.util.Iterator;
import java.util.ArrayList;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.Style;
import java.net.MalformedURLException;
import net.labymod.api.models.OperatingSystem;
import java.net.URI;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer;
import net.labymod.api.client.gui.screen.theme.Theme;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.v1_8_9.client.component.VersionedClickEvent;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.api.event.client.gui.screen.VersionedScreenInitEvent;
import net.labymod.api.Laby;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import java.util.Map;
import net.labymod.v1_8_9.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.v1_8_9.client.gui.ScrollableGuiScreen;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_8_9.client.gui.GuiScreenAccessor;
import net.labymod.v1_8_9.client.gui.screen.LabyScreenRendererAccessor;

@Mixin({ axu.class })
public abstract class MixinGuiScreen extends avp implements LabyScreenRendererAccessor, GuiScreenAccessor
{
    @Shadow
    protected List<avs> n;
    @Shadow
    protected avn q;
    @Shadow
    protected bjh k;
    private static final char KEY_PRESSED_CHAR = '\u001b';
    @Shadow
    public int l;
    @Shadow
    public int m;
    @Shadow
    protected ave j;
    private ChatMessageSendEvent labyMod$currentMessageSendEvent;
    private boolean labyMod$handleMouseInput;
    
    public MixinGuiScreen() {
        this.labyMod$handleMouseInput = true;
    }
    
    @Shadow
    public abstract void b();
    
    @Shadow
    protected abstract void a(final int p0, final int p1, final int p2);
    
    @Shadow
    protected abstract void b(final int p0, final int p1, final int p2);
    
    @Shadow
    protected abstract void a(final char p0, final int p1);
    
    @Shadow
    protected abstract void a(final int p0, final int p1, final int p2, final long p3);
    
    public boolean wrappedMouseClicked(final MutableMouse mouse, final int mouseButton) {
        this.a(mouse.getX(), mouse.getY(), mouseButton);
        return false;
    }
    
    public boolean wrappedMouseReleased(final MutableMouse mouse, final int mouseButton) {
        this.b(mouse.getX(), mouse.getY(), mouseButton);
        return false;
    }
    
    public boolean wrappedMouseClickMove(final MutableMouse mouse, final int button, final double deltaX, final double deltaY) {
        this.a(mouse.getX(), mouse.getY(), button, ave.J());
        return false;
    }
    
    public boolean wrappedKeyPressed(final Key key, final InputType type) {
        if (type == InputType.ACTION) {
            this.a('\0', key.getId());
        }
        return false;
    }
    
    public boolean wrappedKeyReleased(final Key key, final InputType type) {
        return false;
    }
    
    public boolean wrappedCharTyped(final Key key, final char character) {
        this.a(character, key.getId());
        return false;
    }
    
    public boolean mouseScrolled(final MutableMouse mouse, final double delta) {
        return this instanceof ScrollableGuiScreen && ((ScrollableGuiScreen)this).mouseScrolled(delta);
    }
    
    @Insert(method = { "handleMouseInput" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$redirectMouseInput(final InsertInfo ci) {
        if (!this.labyMod$handleMouseInput) {
            ci.cancel();
            return;
        }
        if (this instanceof final LabyScreenRenderer labyScreenRenderer) {
            labyScreenRenderer.screen().doScreenAction("handleMouseInput", null);
        }
    }
    
    @Insert(method = { "handleMouseInput()V" }, at = @At("TAIL"))
    private void labyMod$injectMouseScrolled(final InsertInfo ci) {
        final double scrollDelta = Mouse.getDWheel() * 1.0;
        if (scrollDelta != 0.0) {
            final float scrollDeltaDirection = (scrollDelta > 0.0) ? 1.0f : -1.0f;
            final int mouseX = Mouse.getEventX() * this.l / this.j.d;
            final int mouseY = this.m - Mouse.getEventY() * this.m / this.j.e - 1;
            Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> this.mouseScrolled(mouse, scrollDeltaDirection));
        }
    }
    
    @Insert(method = { "sendChatMessage(Ljava/lang/String;Z)V" }, at = @At("HEAD"), cancellable = true)
    public void labyMod$fireChatMessageSendEvent(final String message, final boolean addToHistory, final InsertInfo callbackInfo) {
        this.labyMod$currentMessageSendEvent = Laby.fireEvent(new ChatMessageSendEvent(message, addToHistory));
        if (this.labyMod$currentMessageSendEvent.isCancelled()) {
            callbackInfo.cancel();
            if (!addToHistory) {
                return;
            }
            final String history = this.labyMod$currentMessageSendEvent.getHistoryText();
            if (history != null && !history.trim().isEmpty()) {
                this.j.q.d().a(history);
            }
        }
    }
    
    @Insert(method = { "setWorldAndResolution" }, at = @At("HEAD"))
    public void labyMod$fireScreenInitEvent(final ave mc, final int width, final int height, final InsertInfo ci) {
        Laby.fireEvent(new VersionedScreenInitEvent(this));
    }
    
    @Insert(method = { "handleComponentClick" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$handleCustomClickEvent(final eu component, final InsertInfoReturnable<Boolean> ci) {
        if (component == null) {
            return;
        }
        final et clickEvent = component.b().h();
        if (!(clickEvent instanceof VersionedClickEvent) || r()) {
            return;
        }
        final ClickEvent.Action action = ((ClickEvent)clickEvent).action();
        if (action == ClickEvent.Action.COPY_TO_CLIPBOARD) {
            Laby.references().chatExecutor().copyToClipboard(clickEvent.b());
            ci.setReturnValue(true);
        }
    }
    
    @ModifyVariable(method = { "sendChatMessage(Ljava/lang/String;Z)V" }, at = @At("HEAD"))
    public boolean labyMod$modifyAddToHistory(final boolean addToHistory) {
        return this.labyMod$currentMessageSendEvent.getHistoryText() != null;
    }
    
    @ModifyVariable(method = { "sendChatMessage(Ljava/lang/String;Z)V" }, at = @At("HEAD"))
    public String labyMod$modifyHistoryText(final String message) {
        return this.labyMod$currentMessageSendEvent.getHistoryText();
    }
    
    @ModifyVariable(method = { "sendChatMessage(Ljava/lang/String;Z)V" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;", shift = At.Shift.BEFORE, ordinal = 0))
    public String labyMod$modifyMessage(final String message) {
        return this.labyMod$currentMessageSendEvent.getMessage();
    }
    
    @Insert(method = { "drawWorldBackground(I)V" }, at = @At("HEAD"), cancellable = true)
    public void labyMod$renderWorldBackground(final int p1, final InsertInfo ci) {
        this.labyMod$renderCustomBackground(ci);
    }
    
    @Insert(method = { "drawBackground(I)V" }, at = @At("HEAD"), cancellable = true)
    public void labyMod$renderBackground(final int p1, final InsertInfo ci) {
        this.labyMod$renderCustomBackground(ci);
    }
    
    private void labyMod$renderCustomBackground(final InsertInfo ci) {
        final Theme theme = Laby.labyAPI().themeService().currentTheme();
        final BackgroundRenderer backgroundRenderer = theme.getBackgroundRenderer();
        if (backgroundRenderer == null) {
            return;
        }
        final Stack stack = Laby.labyAPI().renderPipeline().renderContexts().currentStack();
        if (backgroundRenderer.renderBackground(stack, this)) {
            ci.cancel();
        }
    }
    
    @Redirect(method = { "drawWorldBackground" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;drawGradientRect(IIIIII)V"))
    private void labyMod$disableBackground(final axu instance, final int x, final int y, final int width, final int height, final int color0, final int color1) {
        if (Laby.labyAPI().config().appearance().hideMenuBackground().get()) {
            return;
        }
        this.a(x, y, width, height, color0, color1);
    }
    
    public void resize(final int width, final int height) {
        this.a(ave.A(), width, height);
    }
    
    public void setMinecraft(final ave minecraft) {
        this.j = minecraft;
    }
    
    public boolean isHandleMouseInput() {
        return this.labyMod$handleMouseInput;
    }
    
    public void setHandleMouseInput(final boolean handleMouseInput) {
        this.labyMod$handleMouseInput = handleMouseInput;
    }
    
    @Inject(method = { "drawHoveringText" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;disableDepth()V", shift = At.Shift.AFTER) })
    private void labyMod$enableDepthTest(final List<String> lines, final int x, final int y, final CallbackInfo ci) {
        bfl.j();
    }
    
    @Inject(method = { "drawHoveringText" }, at = { @At(value = "INVOKE", target = "Ljava/util/List;size()I", shift = At.Shift.BEFORE, ordinal = 2) })
    private void labyMod$disableDepthTest(final List<String> lines, final int x, final int y, final CallbackInfo ci) {
        bfl.i();
    }
    
    @Inject(method = { "openWebLink" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$openWebLink(final URI url, final CallbackInfo ci) {
        try {
            OperatingSystem.getPlatform().openUrl(url.toURL());
            ci.cancel();
        }
        catch (final MalformedURLException e) {
            e.printStackTrace();
        }
    }
    
    public void renderComponentHoverEffect(final Stack stack, final Style style, final int x, final int y) {
        final TextComponent component = Component.empty().style(style);
        this.a((eu)component, x, y);
    }
    
    @Shadow
    protected abstract void a(final eu p0, final int p1, final int p2);
    
    @Shadow
    public static boolean r() {
        return false;
    }
    
    @Shadow
    public abstract void a(final ave p0, final int p1, final int p2);
    
    public List<Object> getWrappedWidgets() {
        final List<Object> wrappedWidgets = new ArrayList<Object>();
        for (final avs child : this.n) {
            wrappedWidgets.add(this.asVersionedWidget(child));
        }
        return wrappedWidgets;
    }
    
    private VersionedWidget asVersionedWidget(final avs child) {
        VersionedWidget versionedWidget = null;
        if (child instanceof ConvertableMinecraftWidget) {
            final ConvertableMinecraftWidget<?> convertable = (ConvertableMinecraftWidget<?>)child;
            final WidgetWatcher<?> watcher = convertable.getWatcher();
            if (watcher != null && watcher.getWidget() != null) {
                versionedWidget = new VersionedWidget(child.getClass(), (Widget)watcher.getWidget());
            }
        }
        if (versionedWidget != null) {
            return versionedWidget;
        }
        return new VersionedWidget(child.getClass());
    }
}
