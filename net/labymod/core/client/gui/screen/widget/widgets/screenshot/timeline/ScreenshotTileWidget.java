// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline;

import net.labymod.core.client.screenshot.meta.ScreenshotMeta;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.Textures;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import java.io.IOException;
import java.util.Objects;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.component.Component;
import java.util.Date;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.core.client.screenshot.ScreenshotBrowser;
import net.labymod.core.client.screenshot.Screenshot;
import java.text.SimpleDateFormat;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class ScreenshotTileWidget extends SimpleWidget
{
    private static final SimpleDateFormat FORMAT;
    private final ScreenshotSectionWidget sectionWidget;
    private final Screenshot screenshot;
    private final ScreenshotTimelineWidget.ScreenshotTimelineHolder holder;
    private final ScreenshotBrowser.QueuedExecutor executor;
    private final RenderableComponent nameComponent;
    private ScreenshotBrowser.QueuedExecutor.Task task;
    private boolean isInFrame;
    
    public ScreenshotTileWidget(final ScreenshotSectionWidget sectionWidget, final Screenshot screenshot) {
        this.isInFrame = false;
        this.sectionWidget = sectionWidget;
        this.screenshot = screenshot;
        this.holder = this.sectionWidget.containerWidget().timelineWidget().holder();
        this.executor = this.holder.browser().getExecutor();
        final long time = screenshot.getMeta().getTimestamp();
        final String displayTime = ScreenshotTileWidget.FORMAT.format(new Date(time));
        this.nameComponent = RenderableComponent.of(Component.text(displayTime));
        this.setContextMenu(screenshot.getContextMenu());
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.visible().set(this.isInFrame);
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        this.updateVisibleState();
    }
    
    public void updateVisibleState() {
        final Bounds window = this.parent.getParent().bounds();
        final Bounds bounds = this.bounds();
        final boolean visible = window.isOverlapping(bounds);
        if (this.visible().get() != visible) {
            final LssProperty<Boolean> visible2 = this.visible();
            final boolean b = visible;
            this.isInFrame = b;
            visible2.set(b);
            if (visible) {
                if (!this.screenshot.isLoaded()) {
                    this.task = this.executor.queue(() -> {
                        try {
                            Screenshot.QualityType quality = this.sectionWidget.containerWidget().timelineWidget().getQuality();
                            final Screenshot openScreenshot = this.holder.getOpenScreenshot();
                            if (Objects.equals(openScreenshot, this.screenshot)) {
                                quality = Screenshot.QualityType.RAW;
                            }
                            this.screenshot.load(quality);
                        }
                        catch (final IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
            else {
                this.unload();
            }
        }
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        super.renderWidget(context);
        final int tilesPerRow = this.sectionWidget.containerWidget().timelineWidget().getTilesPerRow();
        final Rectangle rectangle = this.bounds().copy(BoundsType.INNER).shrink(0.5f);
        final Rectangle outline = this.bounds();
        final boolean selected = this.isHovered() || (this.getContextMenu() != null && this.getContextMenu().isOpen());
        this.labyAPI.renderPipeline().rectangleRenderer().renderRectangle(context.stack(), outline, ColorFormat.ARGB32.pack(0, 0, 0, 100));
        final Icon icon = this.screenshot.getIcon();
        if (this.screenshot.isLoaded() && icon != null) {
            icon.render(context.stack(), rectangle);
            if (selected) {
                this.labyAPI.renderPipeline().rectangleRenderer().renderRectangle(context.stack(), rectangle, ColorFormat.ARGB32.pack(255, 255, 255, 30));
            }
        }
        else {
            final float animationOffset = this.bounds().getX() + this.bounds().getY();
            final int brightness = (int)(Math.sin(TimeUtil.getMillis() % 100000000L / 500.0 - animationOffset / 200.0) * 20.0 + 60.0);
            this.labyAPI.renderPipeline().rectangleRenderer().renderRectangle(context.stack(), rectangle, ColorFormat.ARGB32.pack(brightness, brightness, brightness));
        }
        if (tilesPerRow <= 10 && selected) {
            this.labyAPI.renderPipeline().rectangleRenderer().renderRectangle(context.stack(), rectangle.getLeft(), rectangle.getBottom() - 8.0f, rectangle.getRight(), rectangle.getBottom(), ColorFormat.ARGB32.pack(0, 0, 0, 100));
            this.labyAPI.renderPipeline().componentRenderer().builder().pos(rectangle.getLeft() + 2.0f, rectangle.getBottom() - 6.0f).color(ColorFormat.ARGB32.pack(255, 255, 255)).useFloatingPointPosition(true).shadow(false).scale(0.67f).text(this.nameComponent).render(context.stack());
        }
        if (tilesPerRow <= 20 && !this.screenshot.getMeta().getAttributes().isEmpty()) {
            Textures.SpriteCommon.LABYMOD.render(context.stack(), (float)((int)rectangle.getRight() - 8), (float)((int)rectangle.getBottom() - 8), 8.0f, 8.0f);
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (mouseButton == MouseButton.LEFT) {
            this.sectionWidget.containerWidget().timelineWidget().holder().open(this.screenshot);
        }
        return super.mouseClicked(mouse, mouseButton);
    }
    
    public void unload() {
        if (this.task != null) {
            this.task.cancel();
        }
        if (this.screenshot.isLoaded()) {
            this.screenshot.unload();
        }
    }
    
    @Override
    public int getSortingValue() {
        final ScreenshotMeta meta = this.screenshot.getMeta();
        return (int)(-meta.getTimestamp());
    }
    
    public Screenshot getScreenshot() {
        return this.screenshot;
    }
    
    static {
        FORMAT = new SimpleDateFormat("MM-dd HH:mm");
    }
}
