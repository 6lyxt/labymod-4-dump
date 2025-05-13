// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.gui.screens.controls;

import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.Laby;
import net.labymod.api.volt.annotation.Insert;
import com.google.common.collect.ImmutableList;
import java.util.List;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.options.MinecraftInputMapping;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.v1_20_1.client.gui.screen.widget.KeyEntryCustomWidgetListener;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.util.bounds.ModifyReason;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.options.MinecraftKeyEntry;

@Mixin({ evj.c.class })
public class MixinKeyEntry implements MinecraftKeyEntry
{
    private static final ModifyReason LABYMOD$MINECRAFT_BOUNDS;
    private DivWidget labyMod$customWidget;
    private boolean labyMod$customInitialized;
    private KeyEntryCustomWidgetListener labyMod$customWidgetListener;
    
    @Override
    public Widget getCustomWidget() {
        return this.labyMod$customWidget;
    }
    
    @Override
    public void setCustomWidget(final Widget widget) {
        ThreadSafe.ensureRenderThread();
        (this.labyMod$customWidget = new DivWidget()).addId("controls-custom-wrapper");
        this.labyMod$customWidget.addChild(widget);
        this.labyMod$customInitialized = false;
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$injectCustomWidget(final evj list, final enl keyMapping, final sw component, final CallbackInfo ci) {
        final Widget replacement = ((MinecraftInputMapping)keyMapping).getReplacement();
        if (replacement != null) {
            this.setCustomWidget(replacement);
        }
    }
    
    @Insert(method = { "children" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$injectCustomWidgetListener(final InsertInfoReturnable<List<? extends eqt>> cir) {
        if (this.labyMod$customWidget == null) {
            return;
        }
        if (this.labyMod$customWidgetListener == null) {
            this.labyMod$customWidgetListener = new KeyEntryCustomWidgetListener(this);
        }
        cir.setReturnValue((List<? extends eqt>)ImmutableList.of((Object)this.labyMod$customWidgetListener));
    }
    
    @Insert(method = { "narratables" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$injectCustomWidgetNarratable(final InsertInfoReturnable<List<? extends eqt>> cir) {
        if (this.labyMod$customWidget == null) {
            return;
        }
        cir.setReturnValue((List<? extends eqt>)ImmutableList.of());
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)I", shift = At.Shift.AFTER) })
    private void labyMod$renderCustomWidget(final eox graphics, final int index, final int top, final int left, final int width, final int height, final int mouseX, final int mouseY, final boolean isMouserOver, final float tickDelta, final CallbackInfo ci) {
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
            this.labyMod$customWidget.bounds().setOuterPosition((float)(left + 105), (float)top, MixinKeyEntry.LABYMOD$MINECRAFT_BOUNDS);
            this.labyMod$customWidget.bounds().setOuterSize(135.0f, 20.0f, MixinKeyEntry.LABYMOD$MINECRAFT_BOUNDS);
            final ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
            screenContext.runInContext(((VanillaStackAccessor)graphics.c()).stack(), mouseX, mouseY, tickDelta, context -> this.labyMod$customWidget.render(context));
        }
    }
    
    @Redirect(method = { "render" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", ordinal = 0))
    private void labyMod$preventResetButtonRender(final epi instance, final eox graphics, final int mouseX, final int mouseY, final float partialTicks) {
        if (this.labyMod$customWidget == null) {
            instance.a(graphics, mouseX, mouseY, partialTicks);
        }
    }
    
    @Redirect(method = { "render" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", ordinal = 1))
    private void labyMod$preventChangeButtonRender(final epi instance, final eox graphics, final int mouseX, final int mouseY, final float partialTicks) {
        if (this.labyMod$customWidget == null) {
            instance.a(graphics, mouseX, mouseY, partialTicks);
        }
    }
    
    static {
        LABYMOD$MINECRAFT_BOUNDS = ModifyReason.of("minecraftBounds");
    }
}
