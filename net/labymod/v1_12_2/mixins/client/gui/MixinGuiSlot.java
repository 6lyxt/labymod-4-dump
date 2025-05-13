// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui;

import java.util.Collections;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollbarWidget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.Minecraft;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.core.client.gui.screen.widget.widgets.dummy.DummyScrollbarWidget;
import net.labymod.core.client.gui.screen.widget.widgets.dummy.DummyScrollWidget;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.PositionedBounds;
import net.labymod.api.client.gfx.pipeline.util.Scissor;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.Laby;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Shadow;
import java.util.Collection;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bjr.class })
public abstract class MixinGuiSlot
{
    private static final Collection<Class<?>> BACKGROUND_IGNORED_SLOTS;
    @Shadow
    protected int b;
    @Shadow
    protected int c;
    @Shadow
    protected int d;
    @Shadow
    protected int e;
    @Shadow
    protected int g;
    @Shadow
    protected int i;
    @Shadow
    protected int j;
    @Shadow
    protected float n;
    @Shadow
    protected boolean s;
    @Shadow
    protected boolean q;
    
    @Shadow
    protected abstract void a();
    
    @Insert(method = { "drawScreen" }, at = @At("HEAD"), cancellable = true)
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        final Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (!theme.metadata().getBoolean("hide_default_background")) {
            return;
        }
        if (this.q) {
            this.i = mouseX;
            this.j = mouseY;
            if (MixinGuiSlot.BACKGROUND_IGNORED_SLOTS.contains(this.getClass())) {
                this.a();
            }
            this.l();
            bus.g();
            bus.p();
            final bve var6 = bve.a();
            final int x = this.g + this.b / 2 - this.c() / 2 + 2;
            final int y = this.d + 4 - (int)this.n;
            if (this.s) {
                this.a(x, y, var6);
            }
            this.labyMod$drawSelectionBox(x, y, mouseX, mouseY, partialTicks);
            this.labyMod$renderScrollbar((bjr)this);
            this.b(mouseX, mouseY);
            bus.y();
            bus.j(7424);
            bus.e();
            bus.l();
        }
        ci.cancel();
    }
    
    @Redirect(method = { "drawScreen" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiSlot;drawSelectionBox(IIIIF)V"))
    public void labyMod$drawSelectionBox(final bjr instance, final int x, final int y, final int mouseX, final int mouseY, final float partialTicks) {
        this.labyMod$drawSelectionBox(x, y, mouseX, mouseY, partialTicks);
    }
    
    @Shadow
    protected void a(final int x, final int y, final bve tessellator) {
    }
    
    private void labyMod$drawSelectionBox(final int x, final int y, final int mouseX, final int mouseY, final float partialTicks) {
        final Scissor scissor = Laby.labyAPI().gfxRenderPipeline().scissor();
        try {
            scissor.push(VersionedStackProvider.DEFAULT_STACK, (float)this.d, (float)(this.e - this.d));
            bus.k();
            this.a(x, y, mouseX, mouseY, partialTicks);
        }
        finally {
            scissor.pop();
        }
    }
    
    @Shadow
    public int c() {
        return 0;
    }
    
    @Shadow
    protected void l() {
    }
    
    @Shadow
    protected abstract int d();
    
    @Shadow
    protected abstract int k();
    
    @Shadow
    protected abstract void a(final int p0, final int p1, final int p2, final int p3, final float p4);
    
    @Shadow
    protected abstract void b(final int p0, final int p1);
    
    @Redirect(method = { "drawScreen" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiSlot;getMaxScroll()I"))
    private int laybMod$render(final bjr instance) {
        return this.labyMod$renderScrollbar(instance);
    }
    
    private int labyMod$renderScrollbar(final bjr slot) {
        final int maxScroll = slot.m();
        if (maxScroll <= 0) {
            return 0;
        }
        final LabyAPI labyAPI = Laby.labyAPI();
        final Minecraft minecraft = labyAPI.minecraft();
        final Theme theme = labyAPI.themeService().currentTheme();
        final ThemeRenderer<ScrollbarWidget> scrollbarWidgetThemeRenderer = theme.getWidgetRendererByName("Scrollbar");
        final float x = (float)this.d();
        final float contentHeight = (float)this.k();
        final Bounds scrollBar = new PositionedBounds(x, (float)(this.d + 2), x + 6.0f, (float)(this.e - 2));
        final float scrollBarHeight = scrollBar.getHeight();
        final int scrollButtonHeight = MathHelper.ceilOrFloor(scrollBarHeight * scrollBarHeight / contentHeight);
        int scrolled = (int)this.n * ((int)scrollBarHeight - scrollButtonHeight) / maxScroll;
        if (scrolled < 0) {
            scrolled = 0;
        }
        final Stack stack = labyAPI.renderPipeline().renderContexts().currentStack();
        final ScrollbarWidget scrollbarWidget = new DummyScrollbarWidget(scrollBar, contentHeight, (float)scrolled, new DummyScrollWidget());
        final ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        screenContext.runInContext(stack, minecraft.mouse(), minecraft.getTickDelta(), context -> scrollbarWidgetThemeRenderer.render(scrollbarWidget, context));
        return 0;
    }
    
    static {
        BACKGROUND_IGNORED_SLOTS = Collections.singletonList(blc.class.getDeclaredClasses()[0]);
    }
}
