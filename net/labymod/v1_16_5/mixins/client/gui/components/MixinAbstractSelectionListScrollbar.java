// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.gui.components;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollbarWidget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.Minecraft;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.core.client.gui.screen.widget.widgets.dummy.DummyScrollbarWidget;
import net.labymod.core.client.gui.screen.widget.widgets.dummy.DummyScrollWidget;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.PositionedBounds;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dlf.class })
public abstract class MixinAbstractSelectionListScrollbar
{
    @Shadow
    protected int j;
    @Shadow
    protected int i;
    @Shadow
    protected int e;
    @Shadow
    protected int d;
    @Shadow
    protected int n;
    
    @Shadow
    protected abstract int c();
    
    @Shadow
    public abstract double m();
    
    @Shadow
    protected abstract int e();
    
    @Redirect(method = { "render" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;getMaxScroll()I"))
    private int labyMod$render(final dlf instance) {
        final int maxScroll = instance.n();
        if (maxScroll <= 0) {
            return 0;
        }
        final LabyAPI labyAPI = Laby.labyAPI();
        final Minecraft minecraft = labyAPI.minecraft();
        final Theme theme = labyAPI.themeService().currentTheme();
        final ThemeRenderer<ScrollbarWidget> scrollbarWidgetThemeRenderer = theme.getWidgetRendererByName("Scrollbar");
        final int x = this.e();
        final Bounds scrollBar = new PositionedBounds((float)x, (float)this.i, (float)(x + 6), (float)this.j);
        final float scrollBarHeight = scrollBar.getHeight();
        final int contentHeight = this.c() - this.n;
        final int scrollButtonHeight = MathHelper.ceilOrFloor(scrollBarHeight * scrollBarHeight / contentHeight);
        int scrolled = (int)this.m() * ((int)scrollBarHeight - scrollButtonHeight) / maxScroll;
        if (scrolled < 0) {
            scrolled = 0;
        }
        final Stack stack = labyAPI.renderPipeline().renderContexts().currentStack();
        final ScrollbarWidget scrollbarWidget = new DummyScrollbarWidget(scrollBar, (float)contentHeight, (float)scrolled, new DummyScrollWidget());
        final ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        screenContext.runInContext(stack, minecraft.mouse(), minecraft.getTickDelta(), context -> scrollbarWidgetThemeRenderer.render(scrollbarWidget, context));
        return 0;
    }
}
