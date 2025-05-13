// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.chat.ActionBarReceiveEvent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.client.util.VanillaParityUtil;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;

@IntroducedIn("4.1.0")
@SpriteSlot(x = 3, y = 5)
public class ActionBarHudWidget extends SimpleHudWidget<HudWidgetConfig>
{
    private static final ActionBarHolder COOKIES_ARE_DELICIOUS;
    private ActionBarHolder actionBarHolder;
    
    public ActionBarHudWidget() {
        super("action_bar", HudWidgetConfig.class);
        this.bindCategory(HudWidgetCategory.INGAME);
        this.bindDropzones(NamedHudWidgetDropzones.ACTION_BAR);
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        super.onTick(isEditorContext);
        if (this.actionBarHolder != null) {
            this.actionBarHolder.onTick();
        }
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
        final ActionBarHolder holder = isEditorContext ? ActionBarHudWidget.COOKIES_ARE_DELICIOUS : this.actionBarHolder;
        if (holder == null || !holder.hasMessage()) {
            return;
        }
        final ComponentRenderer componentRenderer = this.labyAPI.renderPipeline().componentRenderer();
        final Component message = holder.getMessage();
        final float width = componentRenderer.width(message);
        final float height = componentRenderer.height();
        final float padding = 2.0f;
        if (stack != null) {
            stack.push();
            stack.translate(0.0f, 0.0f, VanillaParityUtil.getActionBarZLevel());
            final float timePassed = holder.getOverlayMessageTime() - partialTicks;
            int opacity = (int)(timePassed * 255.0f / 20.0f);
            if (opacity > 255) {
                opacity = 255;
            }
            if (opacity > 8) {
                int color = -1;
                if (holder.isAnimatedMessage()) {
                    color = ColorUtil.hsvToRgb(timePassed / 50.0f, 0.7f, 0.6f);
                }
                this.drawBackground(stack, 0.0f, 0.0f, width + padding * 2.0f, height + padding * 2.0f);
                opacity = (opacity << 24 & 0xFF000000);
                componentRenderer.builder().text(message).pos(padding, padding).color(color | opacity).allowColors(true).render(stack);
            }
            stack.pop();
        }
        size.set(width + padding * 2.0f, height + padding * 2.0f);
    }
    
    @Override
    public boolean isVisibleInGame() {
        return this.actionBarHolder != null && this.actionBarHolder.hasMessage();
    }
    
    @Subscribe
    public void onActionBarReceive(final ActionBarReceiveEvent event) {
        if (!this.isEnabled()) {
            return;
        }
        if (event.phase() != Phase.PRE) {
            return;
        }
        this.actionBarHolder = new ActionBarHolder(event.getMessage(), event.isAnimatedMessage());
        event.setCancelled(true);
    }
    
    @Override
    public boolean renderInDebug() {
        return true;
    }
    
    private void drawBackground(final Stack stack, final float x, final float y, final float width, final float height) {
        final int backgroundColor = this.labyAPI.minecraft().options().getBackgroundColorWithOpacity(0);
        if (backgroundColor == 0) {
            return;
        }
        final RectangleRenderer rectangleRenderer = this.labyAPI.renderPipeline().rectangleRenderer();
        rectangleRenderer.pos(x, y).size(width, height).color(backgroundColor).render(stack);
    }
    
    static {
        COOKIES_ARE_DELICIOUS = new ActionBarHolder(Component.translatable("labymod.hudWidget.action_bar.dummy", new Component[0]), false);
    }
    
    private static class ActionBarHolder
    {
        private final Component message;
        private final boolean animatedMessage;
        private int overlayMessageTime;
        
        public ActionBarHolder(final Component message, final boolean animatedMessage) {
            this.message = message;
            this.animatedMessage = animatedMessage;
            this.overlayMessageTime = 60;
        }
        
        public void onTick() {
            if (this.overlayMessageTime > 0) {
                --this.overlayMessageTime;
            }
        }
        
        public boolean hasMessage() {
            return this.getMessage() != null && this.overlayMessageTime > 0;
        }
        
        @Nullable
        public Component getMessage() {
            return this.message;
        }
        
        public boolean isAnimatedMessage() {
            return this.animatedMessage;
        }
        
        public int getOverlayMessageTime() {
            return this.overlayMessageTime;
        }
    }
}
