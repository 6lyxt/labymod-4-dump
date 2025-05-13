// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.screen;

import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.core.client.accessor.gui.CommandSuggestionsAccessor;
import net.labymod.core.client.accessor.gui.AbstractWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.accessor.chat.ChatScreenAccessor;

@Mixin({ fym.class })
public abstract class MixinChatScreen extends fzq implements ChatScreenAccessor
{
    @Shadow
    protected fuh b;
    @Shadow
    fub w;
    
    @Shadow
    @Nullable
    protected abstract yd b(final double p0, final double p1);
    
    @Shadow
    public abstract String a(final String p0);
    
    public MixinChatScreen(final xg component) {
        super(component);
    }
    
    public void insertChatText(final String text, final boolean override, final boolean skipInput) {
        this.a_(text, override);
    }
    
    @Inject(method = { "init" }, at = { @At("TAIL") })
    private void labyMod$modifyInputBounds(final CallbackInfo ci) {
        this.labyMod$updateInputBounds();
    }
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    private void labyMod$modifyInputBounds(final ftk param0, final int param1, final int param2, final float param3, final CallbackInfo ci) {
        this.labyMod$updateInputBounds();
    }
    
    @Redirect(method = { "render", "mouseClicked" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/ChatScreen;getComponentStyleAt(DD)Lnet/minecraft/network/chat/Style;"))
    public yd cancelHoveredComponent(final fym chatScreen, final double x, final double y) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get()) {
            return null;
        }
        return this.b(x, y);
    }
    
    @Inject(method = { "mouseScrolled" }, at = { @At(value = "RETURN", ordinal = 1) }, cancellable = true)
    public void cancelMouseScrolled(final double $$0, final double $$1, final double $$2, final double $$3, final CallbackInfoReturnable<Boolean> cir) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get()) {
            cir.setReturnValue((Object)false);
        }
    }
    
    @Insert(method = { "render" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;II)V", shift = At.Shift.BEFORE), cancellable = true)
    public void cancelRenderTooltip(final ftk $$0, final int $$1, final int $$2, final float $$3, final InsertInfo ci) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get()) {
            ci.cancel();
        }
    }
    
    @Redirect(method = { "render" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"))
    private void labyMod$preventBackgroundRender(final ftk graphics, final int left, final int top, final int right, final int bottom, final int color) {
    }
    
    @Overwrite
    public void b(String message, final boolean showInHistory) {
        message = this.a(message);
        final ChatMessageSendEvent event = Laby.fireEvent(new ChatMessageSendEvent(message, showInHistory));
        if (event.getHistoryText() != null && !event.getHistoryText().trim().isEmpty()) {
            this.m.m.d().a(event.getHistoryText());
        }
        if (event.isCancelled() || event.getMessage() == null || event.getMessage().trim().isEmpty()) {
            return;
        }
        message = event.getMessage();
        if (message.startsWith("/")) {
            this.m.t.j.c(message.substring(1));
        }
        else {
            this.m.t.j.b(message);
        }
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
        widget.backgroundColor().set(this.m.n.a(Integer.MIN_VALUE));
        final Rectangle rectangle = widget.bounds().rectangle(BoundsType.INNER);
        this.b.j((int)rectangle.getX() + 2);
        this.b.k((int)rectangle.getY() + 2);
        this.b.h((int)rectangle.getWidth());
        ((AbstractWidgetAccessor)this.b).setHeight((int)rectangle.getHeight());
        ((CommandSuggestionsAccessor)this.w).setBottomY((int)(rectangle.getY() - 2.0f));
    }
    
    public String getChatText() {
        return this.b.a();
    }
}
