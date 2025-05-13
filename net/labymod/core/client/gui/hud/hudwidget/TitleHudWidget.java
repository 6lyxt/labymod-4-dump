// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.component.format.NamedTextColor;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.util.VanillaParityUtil;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import java.util.Objects;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.chat.Title;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;

@IntroducedIn("4.1.0")
@SpriteSlot(x = 2, y = 5)
public class TitleHudWidget extends SimpleHudWidget<TitleHudWidgetConfig>
{
    private static final Title DUMMY_TITLE;
    private static final TitleHandler DUMMY_TITLE_HANDLER;
    private TitleHandler titleHandler;
    
    public TitleHudWidget() {
        super("title", TitleHudWidgetConfig.class);
        this.bindCategory(HudWidgetCategory.INGAME);
        this.bindDropzones(NamedHudWidgetDropzones.TITLE);
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        super.onTick(isEditorContext);
        if (this.titleHandler != null) {
            this.titleHandler.onTick();
        }
        final Title title = this.getTitle();
        if (title == null) {
            this.titleHandler = null;
            return;
        }
        if (this.titleHandler != null && Objects.equals(this.titleHandler.title(), title)) {
            return;
        }
        this.titleHandler = new TitleHandler(title, false);
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
        final TitleHandler handler = isEditorContext ? TitleHudWidget.DUMMY_TITLE_HANDLER : this.titleHandler;
        if (handler == null || !handler.hasTitle()) {
            return;
        }
        final int opacity = handler.calculateOpacity(partialTicks);
        if (opacity <= 8) {
            return;
        }
        final ComponentRenderer componentRenderer = Laby.references().componentRenderer();
        final Title title = handler.title();
        final Component titleComponent = title.getTitle();
        final Component subTitleComponent = title.getSubTitle();
        final float titleScale = ((TitleHudWidgetConfig)this.config).titleScale().get();
        final float subTitleScale = ((TitleHudWidgetConfig)this.config).subTitleScale().get();
        final float padding = 2.0f;
        final float lineSpacing = ((TitleHudWidgetConfig)this.config).lineSpacing().get();
        final float titleWidth = this.getComponentWidth(componentRenderer, titleComponent) * titleScale;
        final float subTitleWidth = this.getComponentWidth(componentRenderer, subTitleComponent) * subTitleScale;
        final float maxWidth = Math.max(titleWidth, subTitleWidth) + padding * 2.0f;
        final float titleHeight = (componentRenderer.height() + padding * 2.0f) * titleScale;
        final float subTitleHeight = (componentRenderer.height() + padding * 2.0f) * subTitleScale;
        final float titlePadding = padding * titleScale;
        final float subTitlePadding = padding * subTitleScale;
        if (stack != null) {
            stack.push();
            stack.translate(0.0f, 0.0f, VanillaParityUtil.getTitlesOverlayZLevel());
            final float titleOffsetX = this.anchor.getGapX(maxWidth, titleWidth + titlePadding * 2.0f);
            final int finalColor = 0xFFFFFF | (opacity << 24 & 0xFF000000);
            this.drawBackground(stack, titleOffsetX, 0.0f, titleWidth + titlePadding * 2.0f, titleHeight);
            componentRenderer.builder().text(titleComponent).pos(titleOffsetX + titlePadding, titlePadding).scale(titleScale).useFloatingPointPosition(true).color(finalColor).render(stack);
            if (subTitleComponent != null) {
                final float subTitleOffsetX = this.anchor.getGapX(maxWidth, subTitleWidth + subTitlePadding * 2.0f);
                this.drawBackground(stack, subTitleOffsetX, titleHeight - titlePadding + lineSpacing - subTitlePadding, subTitleWidth + subTitlePadding * 2.0f, subTitleHeight);
                componentRenderer.builder().text(subTitleComponent).pos(subTitleOffsetX + subTitlePadding, titleHeight - titlePadding + lineSpacing).scale(subTitleScale).useFloatingPointPosition(true).color(finalColor).render(stack);
            }
            stack.pop();
        }
        size.set(maxWidth, titleHeight - titlePadding + lineSpacing + subTitleHeight - subTitlePadding);
    }
    
    @Subscribe
    public void onIngameOverlayElementRender(final IngameOverlayElementRenderEvent event) {
        if (!this.isEnabled()) {
            return;
        }
        if (event.phase() != Phase.PRE || event.elementType() != IngameOverlayElementRenderEvent.OverlayElementType.TITLE) {
            return;
        }
        event.setCancelled(this.isVisibleInGame());
    }
    
    private void drawBackground(final Stack stack, final float x, final float y, final float width, final float height) {
        final int backgroundColor = this.labyAPI.minecraft().options().getBackgroundColorWithOpacity(0);
        if (backgroundColor == 0) {
            return;
        }
        this.labyAPI.renderPipeline().rectangleRenderer().pos(x, y).size(width, height).color(backgroundColor).render(stack);
    }
    
    private float getComponentWidth(final ComponentRenderer componentRenderer, final Component component) {
        if (component == null) {
            return 0.0f;
        }
        return componentRenderer.width(component);
    }
    
    @Override
    public boolean isVisibleInGame() {
        return this.getTitle() != null;
    }
    
    @Nullable
    private Title getTitle() {
        return this.labyAPI.minecraft().getTitle();
    }
    
    @Override
    public boolean renderInDebug() {
        return true;
    }
    
    static {
        DUMMY_TITLE = Title.builder().title(Component.text("LabyMod", NamedTextColor.BLUE)).subTitle(Component.translatable("labymod.hudWidget.title.dummy.subTitle", new Component[0])).fadeIn(50).stay(100).fadeOut(50).build();
        DUMMY_TITLE_HANDLER = new TitleHandler(TitleHudWidget.DUMMY_TITLE, true);
    }
    
    public static class TitleHudWidgetConfig extends HudWidgetConfig
    {
        @SliderWidget.SliderSetting(min = 1.0f, max = 8.0f, steps = 0.5f)
        private final ConfigProperty<Float> titleScale;
        @SliderWidget.SliderSetting(min = 1.0f, max = 8.0f, steps = 0.5f)
        private final ConfigProperty<Float> subTitleScale;
        @SliderWidget.SliderSetting(min = 0.0f, max = 40.0f)
        private final ConfigProperty<Float> lineSpacing;
        
        public TitleHudWidgetConfig() {
            this.titleScale = new ConfigProperty<Float>(4.0f);
            this.subTitleScale = new ConfigProperty<Float>(2.0f);
            this.lineSpacing = new ConfigProperty<Float>(14.0f);
        }
        
        public ConfigProperty<Float> titleScale() {
            return this.titleScale;
        }
        
        public ConfigProperty<Float> subTitleScale() {
            return this.subTitleScale;
        }
        
        public ConfigProperty<Float> lineSpacing() {
            return this.lineSpacing;
        }
    }
    
    private static class TitleHandler
    {
        private final boolean dummy;
        private Title title;
        private int titleTime;
        private int titleFadeInTime;
        private int titleStayTime;
        private int titleFadeOutTime;
        
        public TitleHandler(final Title title, final boolean dummy) {
            this.title = title;
            this.dummy = dummy;
            this.setTimes(title);
        }
        
        protected void onTick() {
            this.decrementTitleTime();
        }
        
        protected boolean hasTitle() {
            return this.dummy || (this.title != null && this.titleTime > 0);
        }
        
        protected int calculateOpacity(final float partialTicks) {
            if (this.dummy) {
                return 255;
            }
            final float timePassed = this.titleTime - partialTicks;
            int opacity = 255;
            if (this.titleTime > this.titleFadeOutTime + this.titleStayTime) {
                final float timeTotal = (float)(this.titleFadeInTime + this.titleStayTime + this.titleFadeOutTime);
                final float temp = timeTotal - timePassed;
                opacity = (int)(temp * 255.0f / this.titleFadeOutTime);
            }
            if (this.titleTime <= this.titleFadeOutTime) {
                opacity = (int)(timePassed * 255.0f / this.titleFadeOutTime);
            }
            return MathHelper.clamp(opacity, 0, 255);
        }
        
        private void decrementTitleTime() {
            if (this.titleTime <= 0) {
                return;
            }
            --this.titleTime;
            if (this.titleTime == 0) {
                this.title = null;
            }
        }
        
        private void setTimes(final Title title) {
            this.setTimes(title.getFadeInTicks(), title.getStayTicks(), title.getFadeOutTicks());
        }
        
        private void setTimes(final int fadeInTime, final int stayTime, final int fadeOutTime) {
            if (fadeInTime >= 0) {
                this.titleFadeInTime = fadeInTime;
            }
            if (stayTime >= 0) {
                this.titleStayTime = stayTime;
            }
            if (fadeOutTime >= 0) {
                this.titleFadeOutTime = fadeOutTime;
            }
            this.titleTime = this.titleFadeInTime + this.titleStayTime + this.titleFadeOutTime;
        }
        
        public Title title() {
            return this.title;
        }
    }
}
