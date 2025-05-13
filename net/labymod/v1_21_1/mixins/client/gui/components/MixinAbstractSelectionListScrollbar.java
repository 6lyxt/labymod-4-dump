// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.client.gui.components;

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

@Mixin({ fih.class })
public abstract class MixinAbstractSelectionListScrollbar extends fie
{
    @Shadow
    protected int f;
    
    public MixinAbstractSelectionListScrollbar(final int $$0, final int $$1, final int $$2, final int $$3, final wz $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }
    
    @Shadow
    protected abstract int a();
    
    @Shadow
    public abstract double n();
    
    @Shadow
    protected abstract int q();
    
    @Redirect(method = { "renderWidget" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;scrollbarVisible()Z"))
    private boolean labyMod$render(final fih instance) {
        final int maxScroll = instance.p();
        if (maxScroll <= 0) {
            return false;
        }
        final LabyAPI labyAPI = Laby.labyAPI();
        final Minecraft minecraft = labyAPI.minecraft();
        final Theme theme = labyAPI.themeService().currentTheme();
        final ThemeRenderer<ScrollbarWidget> scrollbarWidgetThemeRenderer = theme.getWidgetRendererByName("Scrollbar");
        final int x = this.q();
        final Bounds scrollBar = new PositionedBounds((float)x, (float)this.E(), (float)(x + 6), (float)this.G());
        final float scrollBarHeight = scrollBar.getHeight();
        final int contentHeight = this.a() - this.f;
        final int scrollButtonHeight = MathHelper.ceilOrFloor(scrollBarHeight * scrollBarHeight / contentHeight);
        int scrolled = (int)this.n() * ((int)scrollBarHeight - scrollButtonHeight) / maxScroll;
        if (scrolled < 0) {
            scrolled = 0;
        }
        final Stack stack = labyAPI.renderPipeline().renderContexts().currentStack();
        final ScrollbarWidget scrollbarWidget = new DummyScrollbarWidget(scrollBar, (float)contentHeight, (float)scrolled, new DummyScrollWidget());
        final ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        screenContext.runInContext(stack, minecraft.mouse(), minecraft.getTickDelta(), context -> scrollbarWidgetThemeRenderer.render(scrollbarWidget, context));
        return false;
    }
}
