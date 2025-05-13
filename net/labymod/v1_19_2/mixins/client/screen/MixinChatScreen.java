// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.screen;

import org.spongepowered.asm.mixin.Overwrite;
import java.util.function.Function;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.core.client.accessor.gui.CommandSuggestionsAccessor;
import net.labymod.core.client.accessor.gui.AbstractWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.accessor.chat.ChatScreenAccessor;

@Mixin({ ekg.class })
public abstract class MixinChatScreen extends elm implements ChatScreenAccessor
{
    @Shadow
    private ehg B;
    @Shadow
    protected ehx b;
    @Shadow
    ehs A;
    @Shadow
    private boolean D;
    @Shadow
    private evg C;
    
    @Shadow
    public abstract String a(final String p0);
    
    @Shadow
    protected abstract sj a(final double p0, final double p1);
    
    @Shadow
    protected abstract void c(final String p0);
    
    public MixinChatScreen(final rq component) {
        super(component);
    }
    
    @Redirect(method = { "render", "mouseClicked" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/ChatScreen;getComponentStyleAt(DD)Lnet/minecraft/network/chat/Style;"))
    public sj cancelHoveredComponent(final ekg chatScreen, final double x, final double y) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get()) {
            return null;
        }
        return this.a(x, y);
    }
    
    @Inject(method = { "mouseScrolled" }, at = { @At(value = "RETURN", ordinal = 1) }, cancellable = true)
    public void cancelMouseScrolled(final double $$0, final double $$1, final double $$2, final CallbackInfoReturnable<Boolean> cir) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get()) {
            cir.setReturnValue((Object)false);
        }
    }
    
    @Insert(method = { "render" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/ChatScreen;renderTooltip(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/List;II)V", shift = At.Shift.BEFORE), cancellable = true)
    public void cancelRenderTooltip(final eaq $$0, final int $$1, final int $$2, final float $$3, final InsertInfo ci) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get()) {
            ci.cancel();
        }
    }
    
    @Redirect(method = { "render" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/ChatScreen;fill(Lcom/mojang/blaze3d/vertex/PoseStack;IIIII)V"))
    private void labyMod$preventBackgroundRender(final eaq poseStack, final int left, final int top, final int right, final int bottom, final int color) {
    }
    
    @Inject(method = { "init" }, at = { @At("TAIL") })
    private void labyMod$modifyInputBounds(final CallbackInfo ci) {
        this.labyMod$updateInputBounds();
    }
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    private void labyMod$modifyInputBounds(final eaq param0, final int param1, final int param2, final float param3, final CallbackInfo ci) {
        this.labyMod$updateInputBounds();
    }
    
    private void labyMod$updateInputBounds() {
        final LabyScreen screen = Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen();
        if (!(screen instanceof ChatInputOverlay)) {
            return;
        }
        final ChatInputOverlay overlay = (ChatInputOverlay)screen;
        final DivWidget widget = overlay.getInputUnderlay();
        if (widget == null) {
            return;
        }
        widget.backgroundColor().set(this.e.m.a(Integer.MIN_VALUE));
        final Rectangle rectangle = widget.bounds().rectangle(BoundsType.INNER);
        this.b.m = (int)rectangle.getX() + 2;
        this.b.n = (int)rectangle.getY() + 2;
        this.b.b((int)rectangle.getWidth());
        ((AbstractWidgetAccessor)this.b).setHeight((int)rectangle.getHeight());
        ((CommandSuggestionsAccessor)this.A).setBottomY((int)(rectangle.getY() - 2.0f));
    }
    
    public void insertChatText(final String text, final boolean override, final boolean skipInput) {
        this.a(text, override);
    }
    
    @Overwrite
    public boolean b(String message, final boolean showInHistory) {
        message = this.a(message);
        final ChatMessageSendEvent event = Laby.fireEvent(new ChatMessageSendEvent(message, showInHistory));
        if (this.C == evg.c && !this.D) {
            this.A.a();
            if (!this.B.b(message)) {
                this.c(message);
                return false;
            }
        }
        if (event.getHistoryText() != null && !event.getHistoryText().trim().isEmpty()) {
            this.e.l.d().a(event.getHistoryText());
        }
        if (event.isCancelled() || event.getMessage() == null || event.getMessage().trim().isEmpty()) {
            return true;
        }
        message = event.getMessage();
        final rq pullComponent = (rq)ad.a((Object)this.B.c(message), (Function)ehg.a::c);
        if (message.startsWith("/")) {
            this.e.t.b(message.substring(1), pullComponent);
        }
        else {
            this.e.t.a(message, pullComponent);
        }
        return true;
    }
    
    public String getChatText() {
        return this.b.b();
    }
}
