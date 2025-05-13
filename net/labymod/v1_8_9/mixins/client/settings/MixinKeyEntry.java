// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.settings;

import net.labymod.api.client.gui.mouse.MutableMouse;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.Minecraft;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.options.MinecraftInputMapping;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.util.bounds.ModifyReason;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.options.MinecraftKeyEntry;

@Mixin({ ayi.b.class })
public class MixinKeyEntry implements MinecraftKeyEntry
{
    private static final ModifyReason LABYMOD$MINECRAFT_BOUNDS;
    private DivWidget labyMod$customWidget;
    private boolean labyMod$customInitialized;
    
    @Override
    public Widget getCustomWidget() {
        return this.labyMod$customWidget;
    }
    
    @Override
    public void setCustomWidget(final Widget widget) {
        ThreadSafe.ensureRenderThread();
        if (widget != null) {
            (this.labyMod$customWidget = new DivWidget()).addId("controls-custom-wrapper");
            this.labyMod$customWidget.addChild(widget);
        }
        else {
            this.labyMod$customWidget = null;
        }
        this.labyMod$customInitialized = false;
    }
    
    @Inject(method = { "<init>(Lnet/minecraft/client/gui/GuiKeyBindingList;Lnet/minecraft/client/settings/KeyBinding;)V" }, at = { @At("TAIL") })
    private void labyMod$injectCustomWidget(final ayi list, final avb keyBinding, final CallbackInfo ci) {
        final Widget replacement = ((MinecraftInputMapping)keyBinding).getReplacement();
        if (replacement != null) {
            this.setCustomWidget(replacement);
        }
    }
    
    @Inject(method = { "mousePressed" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$forwardMouseClicked(final int mouseX, final int mouseY, final int i1, final int mouseButton, final int i2, final int i3, final CallbackInfoReturnable<Boolean> cir) {
        if (this.labyMod$customWidget == null) {
            return;
        }
        cir.setReturnValue((Object)false);
        if (!this.labyMod$customWidget.isHovered()) {
            return;
        }
        final MouseButton button = KeyMapper.getMouseButton(mouseButton);
        if (button != null) {
            Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> this.labyMod$customWidget.mouseClicked(mouse, button));
            cir.setReturnValue((Object)true);
        }
    }
    
    @Inject(method = { "mouseReleased" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$forwardMouseReleased(final int mouseX, final int mouseY, final int i1, final int mouseButton, final int i2, final int i3, final CallbackInfo ci) {
        if (this.labyMod$customWidget == null) {
            return;
        }
        ci.cancel();
        if (!this.labyMod$customWidget.isHovered()) {
            return;
        }
        final MouseButton button = KeyMapper.getMouseButton(mouseButton);
        if (button != null) {
            Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> this.labyMod$customWidget.mouseReleased(mouse, button));
        }
    }
    
    @Inject(method = { "drawEntry" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I", shift = At.Shift.AFTER) })
    private void labyMod$renderCustomWidget(final int i1, final int x, final int y, final int i4, final int i5, final int mouseX, final int mouseY, final boolean b1, final CallbackInfo ci) {
        if (this.labyMod$customWidget != null) {
            final Minecraft minecraft = Laby.labyAPI().minecraft();
            if (!this.labyMod$customInitialized) {
                this.labyMod$customInitialized = true;
                final Window window = minecraft.minecraftWindow();
                this.labyMod$customWidget.initialize(window);
                this.labyMod$customWidget.postInitialize();
                final ThemeFile file = ThemeFile.create(Laby.labyAPI().themeService().currentTheme(), "labymod", "lss/minecraft-widget.lss");
                final StyleSheet styleSheet = Laby.references().styleSheetLoader().load(file);
                if (styleSheet != null) {
                    this.labyMod$customWidget.applyStyleSheet(styleSheet);
                }
                this.labyMod$customWidget.postStyleSheetLoad();
            }
            this.labyMod$customWidget.bounds().setOuterPosition((float)(x + 105), (float)y, MixinKeyEntry.LABYMOD$MINECRAFT_BOUNDS);
            this.labyMod$customWidget.bounds().setOuterSize(135.0f, 20.0f, MixinKeyEntry.LABYMOD$MINECRAFT_BOUNDS);
            final ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
            screenContext.runInContext(VersionedStackProvider.DEFAULT_STACK, mouseX, mouseY, minecraft.getTickDelta(), context -> this.labyMod$customWidget.render(context));
        }
    }
    
    @Redirect(method = { "drawEntry" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiButton;drawButton(Lnet/minecraft/client/Minecraft;II)V", ordinal = 0))
    private void labyMod$preventResetButtonRender(final avs instance, final ave mc, final int mouseX, final int mouseY) {
        if (this.labyMod$customWidget == null) {
            instance.a(mc, mouseX, mouseY);
        }
    }
    
    @Redirect(method = { "drawEntry" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiButton;drawButton(Lnet/minecraft/client/Minecraft;II)V", ordinal = 1))
    private void labyMod$preventChangeButtonRender(final avs instance, final ave mc, final int mouseX, final int mouseY) {
        if (this.labyMod$customWidget == null) {
            instance.a(mc, mouseX, mouseY);
        }
    }
    
    static {
        LABYMOD$MINECRAFT_BOUNDS = ModifyReason.of("minecraftBounds");
    }
}
