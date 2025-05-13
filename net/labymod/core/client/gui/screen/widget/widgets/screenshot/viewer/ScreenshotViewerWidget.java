// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.screenshot.viewer;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Iterator;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.texture.TextureFilter;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.client.screenshot.ScreenshotUtil;
import java.util.List;
import net.labymod.core.client.screenshot.ScreenshotBrowser;
import net.labymod.core.client.screenshot.meta.ScreenshotMeta;
import net.labymod.api.util.bounds.DefaultRectangle;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.util.Color;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.Laby;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.resources.texture.concurrent.RefreshableTexture;
import net.labymod.core.client.screenshot.Screenshot;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.core.labynet.insight.controller.InsightUploader;
import net.labymod.api.notification.NotificationController;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class ScreenshotViewerWidget extends SimpleWidget
{
    private static final long TRANSITION_DURATION = 200L;
    private static final ModifyReason ANIMATE_CANVAS;
    private final ScreenshotViewerHolder holder;
    private final NotificationController notifications;
    private final InsightUploader insightUploader;
    private final ComponentWidget titleWidget;
    private final ComponentWidget metaWidget;
    private ButtonWidget nextButtonWidget;
    private ButtonWidget previousButtonWidget;
    private DivWidget canvasWidget;
    private IconWidget iconWidget;
    private ComponentWidget statusWidget;
    private ButtonWidget cancelButtonWidget;
    private ButtonWidget trashButtonWidget;
    private ButtonWidget openButtonWidget;
    private ButtonWidget copyButtonWidget;
    private ButtonWidget shareButtonWidget;
    private ButtonWidget editButtonWidget;
    private SliderWidget paintThicknessSlider;
    private ColorPickerWidget paintColorPicker;
    private Rectangle transitionSourceRectangle;
    private int transitionTicks;
    private TransitionType transitionType;
    private Screenshot screenshot;
    private IconWidget paintCanvasWidget;
    private RefreshableTexture paintCanvasTexture;
    private boolean open;
    private boolean fileExists;
    private int dragStartX;
    private int dragStartY;
    private int lastMouseX;
    private int lastMouseY;
    private float lastPaintX;
    private float lastPaintY;
    private boolean dragging;
    private boolean editing;
    
    public ScreenshotViewerWidget(final ScreenshotViewerHolder holder) {
        this.transitionType = TransitionType.OPEN;
        this.lastPaintX = -1.0f;
        this.lastPaintY = -1.0f;
        this.holder = holder;
        this.insightUploader = LabyMod.references().insightUploader();
        this.notifications = Laby.references().notificationController();
        (this.titleWidget = ComponentWidget.empty()).addId("title");
        (this.metaWidget = ComponentWidget.empty()).addId("meta");
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        (this.canvasWidget = new DivWidget()).addId("canvas");
        (this.iconWidget = new IconWidget(null)).addId("screenshot");
        ((AbstractWidget<IconWidget>)this.canvasWidget).addChild(this.iconWidget);
        (this.paintCanvasWidget = new IconWidget(null)).addId("paint-canvas");
        ((AbstractWidget<IconWidget>)this.canvasWidget).addChild(this.paintCanvasWidget);
        ((AbstractWidget<DivWidget>)this).addChild(this.canvasWidget);
        (this.statusWidget = ComponentWidget.empty()).addId("loading");
        ((AbstractWidget<ComponentWidget>)this).addChild(this.statusWidget);
        final ButtonWidget closeButtonWidget = ButtonWidget.icon(Textures.SpriteCommon.BACK_BUTTON);
        ((AbstractWidget<Widget>)closeButtonWidget).addId("close");
        closeButtonWidget.setPressable(this::close);
        ((AbstractWidget<ButtonWidget>)this).addChild(closeButtonWidget);
        final HorizontalListWidget menuWidget = new HorizontalListWidget();
        ((AbstractWidget<Widget>)menuWidget).addId("menu");
        (this.cancelButtonWidget = ButtonWidget.i18n("labymod.ui.button.cancel")).setPressable(this::abortEditing);
        this.cancelButtonWidget.setVisible(false);
        menuWidget.addEntry(this.cancelButtonWidget);
        (this.trashButtonWidget = ButtonWidget.component(Component.translatable("labymod.activity.screenshotBrowser.context.delete", new Component[0]), Textures.SpriteCommon.TRASH)).setPressable(() -> PopupWidget.builder().title(Component.translatable("labymod.activity.screenshotBrowser.viewer.delete.warning", new Component[0]).arguments(Component.text(this.screenshot.getFileName()))).confirmCallback(() -> {
            if (this.screenshot.delete()) {
                this.close();
            }
        }).build().displayInOverlay());
        menuWidget.addEntry(this.trashButtonWidget);
        (this.openButtonWidget = ButtonWidget.component(Component.translatable("labymod.activity.screenshotBrowser.context.open", new Component[0]), Textures.SpriteCommon.OPEN_FILE)).setPressable(() -> this.screenshot.openInSystem());
        menuWidget.addEntry(this.openButtonWidget);
        (this.copyButtonWidget = ButtonWidget.component(Component.translatable("labymod.activity.screenshotBrowser.context.copy", new Component[0]), Textures.SpriteCommon.LARGE_COPY)).setPressable(() -> this.copyToClipboard());
        menuWidget.addEntry(this.copyButtonWidget);
        (this.shareButtonWidget = ButtonWidget.component(Component.translatable("labymod.activity.screenshotBrowser.context.share", new Component[0]), Textures.SpriteCommon.SHARE)).setPressable(() -> {
            final ContextMenu contextMenu = this.shareButtonWidget.getContextMenu();
            if (contextMenu != null) {
                contextMenu.open();
            }
            return;
        });
        menuWidget.addEntry(this.shareButtonWidget);
        (this.editButtonWidget = ButtonWidget.component(Component.translatable("labymod.ui.button.edit", new Component[0]), Textures.SpriteCommon.PAINT)).setPressable(() -> this.startEditing());
        menuWidget.addEntry(this.editButtonWidget);
        ((AbstractWidget<Widget>)(this.nextButtonWidget = ButtonWidget.icon(Textures.SpriteCommon.FORWARD_BUTTON))).addId("icon-button", "right");
        this.nextButtonWidget.setPressable(() -> this.displayScreenshotAtOffset(1));
        menuWidget.addEntry(this.nextButtonWidget);
        ((AbstractWidget<Widget>)(this.previousButtonWidget = ButtonWidget.icon(Textures.SpriteCommon.BACK_BUTTON))).addId("icon-button", "right");
        this.previousButtonWidget.setPressable(() -> this.displayScreenshotAtOffset(-1));
        menuWidget.addEntry(this.previousButtonWidget);
        (this.paintColorPicker = ColorPickerWidget.of(Color.RED)).addId("paint-color", "right");
        this.paintColorPicker.setVisible(false);
        menuWidget.addEntry(this.paintColorPicker);
        (this.paintThicknessSlider = new SliderWidget()).range(1.0f, 10.0f);
        this.paintThicknessSlider.addId("paint-thickness", "right");
        this.paintThicknessSlider.setValue(3.0);
        this.paintThicknessSlider.setVisible(false);
        menuWidget.addEntry(this.paintThicknessSlider);
        ((AbstractWidget<HorizontalListWidget>)this).addChild(menuWidget);
        ((AbstractWidget<ComponentWidget>)this).addChild(this.titleWidget);
        ((AbstractWidget<ComponentWidget>)this).addChild(this.metaWidget);
        if (this.screenshot != null && this.open) {
            this.displayScreenshot(this.screenshot);
        }
        this.dragging = false;
        if (this.editing) {
            this.abortEditing();
        }
    }
    
    @Override
    public void render(final ScreenContext context) {
        final MutableMouse mouse = context.mouse();
        this.lastMouseX = mouse.getX();
        this.lastMouseY = mouse.getY();
        final boolean hasTransition = this.transitionSourceRectangle != null;
        final int transitionTicks = (int)(this.transitionTicks + TimeUtil.convertDeltaToMilliseconds(context.getTickDelta()));
        this.transitionTicks = transitionTicks;
        final long transitionTimePassed = transitionTicks;
        if ((this.isOpen() | transitionTimePassed < 200L) && hasTransition) {
            final float progress = this.getTransitionProgressAt(transitionTimePassed);
            final Rectangle rectangle = this.getRectangle(progress);
            this.opacity().set(this.transitionType.hasFading() ? progress : 1.0f);
            super.render(context);
            final Icon icon = (this.screenshot == null) ? null : this.screenshot.getIcon();
            if (icon != null && this.screenshot.isLoaded()) {
                final float padding = this.dragging ? 0.0f : (2.0f * progress);
                final float height = rectangle.getHeight() - padding * 2.0f;
                final float width = height * icon.getAspectRatio();
                this.iconWidget.bounds().setPosition(rectangle.getX() + (rectangle.getWidth() - width) / 2.0f, rectangle.getY() + padding, ScreenshotViewerWidget.ANIMATE_CANVAS);
                this.iconWidget.bounds().setSize(width, height, ScreenshotViewerWidget.ANIMATE_CANVAS);
                this.iconWidget.opacity().set((this.transitionType == TransitionType.SWIPE_LEFT || this.transitionType == TransitionType.SWIPE_RIGHT) ? (Math.abs(progress - 0.5f) * 2.0f) : 1.0f);
                this.statusWidget.setVisible(!this.fileExists);
                this.paintCanvasWidget.bounds().set(this.iconWidget.bounds(), ScreenshotViewerWidget.ANIMATE_CANVAS);
            }
            else {
                this.statusWidget.setVisible(true);
            }
            this.visible().set(true);
        }
        else {
            this.visible().set(false);
        }
        if (this.editing && this.paintCanvasWidget.isHovered()) {
            this.editButtonWidget.icon().get().render(context.stack(), (float)mouse.getX(), (float)(mouse.getY() - 16), 16.0f);
        }
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (this.isOpen()) {
            if (this.editing) {
                if (key == Key.ESCAPE) {
                    this.abortEditing();
                }
                if (key == Key.Y) {
                    final GameImage image = this.paintCanvasTexture.getCurrentImage();
                    if (image != null) {
                        for (int x = 0; x < image.getWidth(); ++x) {
                            for (int y = 0; y < image.getHeight(); ++y) {
                                image.setARGB(x, y, 0);
                            }
                        }
                        this.paintCanvasTexture.queueUpdate(image);
                    }
                }
                return true;
            }
            if (key == Key.ARROW_LEFT) {
                this.displayScreenshotAtOffset(-1);
                return true;
            }
            if (key == Key.ARROW_RIGHT) {
                this.displayScreenshotAtOffset(1);
                return true;
            }
        }
        return super.keyPressed(key, type);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (!this.isOpen()) {
            return false;
        }
        if (this.canvasWidget.isHovered() && mouseButton == MouseButton.LEFT && !this.editing) {
            this.dragStartX = mouse.getX();
            this.dragStartY = mouse.getY();
            this.dragging = true;
            this.startTransition(TransitionType.DRAG);
            return true;
        }
        if (!this.canvasWidget.isHovered() || !this.editing) {
            super.mouseClicked(mouse, mouseButton);
            return true;
        }
        final GameImage image = this.paintCanvasTexture.getCurrentImage();
        if (image == null) {
            return true;
        }
        final Bounds bounds = this.paintCanvasWidget.bounds();
        final float x = (mouse.getX() - bounds.getX()) / bounds.getWidth() * image.getWidth();
        final float y = (mouse.getY() - bounds.getY()) / bounds.getHeight() * image.getHeight();
        this.lastPaintX = x;
        this.lastPaintY = y;
        return true;
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        if (!this.canvasWidget.isHovered() || !this.editing) {
            return super.mouseDragged(mouse, button, deltaX, deltaY);
        }
        final GameImage image = this.paintCanvasTexture.getCurrentImage();
        if (image == null) {
            return true;
        }
        final Bounds bounds = this.paintCanvasWidget.bounds();
        final float x = (mouse.getX() - bounds.getX()) / bounds.getWidth() * image.getWidth();
        final float y = (mouse.getY() - bounds.getY()) / bounds.getHeight() * image.getHeight();
        if (this.lastPaintX != -1.0f && this.lastPaintY != -1.0f) {
            final int thickness = (int)this.paintThicknessSlider.getValue();
            final Color color = this.paintColorPicker.value();
            this.paintLine(image, this.lastPaintX, this.lastPaintY, x, y, thickness, color.get());
            this.paintCanvasTexture.queueUpdate(image);
        }
        this.lastPaintX = x;
        this.lastPaintY = y;
        return true;
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.dragging && !this.editing) {
            this.dragging = false;
            this.startTransition(TransitionType.DRAG);
            final Bounds bounds = this.canvasWidget.bounds();
            final float offsetX = (float)(mouse.getX() - this.dragStartX);
            final float offsetY = (float)(mouse.getY() - this.dragStartY);
            final float heightThreshold = bounds.getHeight() / 4.0f;
            if (Math.abs(offsetY) > heightThreshold) {
                this.close();
                this.startTransition(TransitionType.DRAG_CLOSE);
                return true;
            }
            final float widthThreshold = 20.0f;
            if (offsetX < -widthThreshold) {
                final boolean success = this.displayScreenshotAtOffset(1);
                if (success) {
                    this.startTransition(TransitionType.SWIPE_LEFT);
                }
                return true;
            }
            if (offsetX > widthThreshold) {
                final boolean success = this.displayScreenshotAtOffset(-1);
                if (success) {
                    this.startTransition(TransitionType.SWIPE_RIGHT);
                }
                return true;
            }
        }
        if (this.editing && this.lastPaintX != -1.0f && this.lastPaintY != -1.0f) {
            final GameImage image = this.paintCanvasTexture.getCurrentImage();
            if (image != null) {
                final Bounds bounds2 = this.paintCanvasWidget.bounds();
                final float x = (mouse.getX() - bounds2.getX()) / bounds2.getWidth() * image.getWidth();
                final float y = (mouse.getY() - bounds2.getY()) / bounds2.getHeight() * image.getHeight();
                final int thickness = (int)this.paintThicknessSlider.getValue();
                final Color color = this.paintColorPicker.value();
                this.paintLine(image, this.lastPaintX, this.lastPaintY, x, y, thickness, color.get());
                this.paintCanvasTexture.queueUpdate(image);
            }
            this.lastPaintX = -1.0f;
            this.lastPaintY = -1.0f;
            return true;
        }
        return super.mouseReleased(mouse, mouseButton);
    }
    
    private void paintLine(final GameImage image, final float fromX, final float fromY, final float toX, final float toY, final int thickness, final int argb) {
        final float dx = toX - fromX;
        final float dy = toY - fromY;
        final float distance = (float)Math.sqrt(dx * dx + dy * dy);
        if (distance > 0.0f) {
            final float stepX = dx / distance;
            final float stepY = dy / distance;
            for (int i = 0; i < distance; ++i) {
                final float x = fromX + stepX * i;
                final float y = fromY + stepY * i;
                this.paintCircle(image, x, y, thickness, (argb & 0xFFFFFF) | 0x60000000);
            }
        }
        else {
            this.paintCircle(image, fromX, fromY, thickness, argb);
        }
    }
    
    private void paintCircle(final GameImage image, final float x, final float y, final int radius, final int argb) {
        for (int offsetX = -radius; offsetX < radius; ++offsetX) {
            for (int offsetY = -radius; offsetY < radius; ++offsetY) {
                final float alpha = 1.0f - (float)Math.sqrt(offsetX * offsetX + offsetY * offsetY) / radius;
                if (alpha >= 0.0f) {
                    final int destX = (int)(x + offsetX);
                    final int destY = (int)(y + offsetY);
                    if (destX >= 0 && destX < image.getWidth() && destY >= 0) {
                        if (destY < image.getHeight()) {
                            final int prevColor = image.getARGB(destX, destY);
                            final int newColor = (argb & 0xFFFFFF) | (int)(alpha * (argb >> 24 & 0xFF)) << 24;
                            final int backgroundAlpha = prevColor >> 24 & 0xFF;
                            final int overlayAlpha = newColor >> 24 & 0xFF;
                            final int backgroundRed = prevColor >> 16 & 0xFF;
                            final int overlayRed = newColor >> 16 & 0xFF;
                            final int backgroundGreen = prevColor >> 8 & 0xFF;
                            final int overlayGreen = newColor >> 8 & 0xFF;
                            final int backgroundBlue = prevColor & 0xFF;
                            final int overlayBlue = newColor & 0xFF;
                            final float opacity = overlayAlpha / 255.0f;
                            final int mergedColor = ColorFormat.ARGB32.pack((int)Math.min(overlayRed * opacity + backgroundRed, 255.0f), (int)Math.min(overlayGreen * opacity + backgroundGreen, 255.0f), (int)Math.min(overlayBlue * opacity + backgroundBlue, 255.0f), Math.min(overlayAlpha + backgroundAlpha, 255));
                            image.setARGB(destX, destY, mergedColor);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        if (this.isOpen()) {
            super.mouseScrolled(mouse, scrollDelta);
            return true;
        }
        return false;
    }
    
    private Rectangle getRectangle(final float progress) {
        if (this.transitionType == TransitionType.OPEN || this.transitionType == TransitionType.CLOSE) {
            final Rectangle source = this.transitionSourceRectangle;
            final MutableRectangle destination = this.canvasWidget.bounds().copy();
            return this.hasOpenAndCloseTransition() ? source.lerp(destination, progress) : destination;
        }
        if (this.transitionType == TransitionType.DRAG_CLOSE) {
            final Rectangle source = this.transitionSourceRectangle;
            final MutableRectangle destination = this.canvasWidget.bounds().copy();
            this.applyDragOffset(destination, 1.0f);
            return this.hasOpenAndCloseTransition() ? source.lerp(destination, progress) : destination;
        }
        if (this.transitionType == TransitionType.DRAG) {
            final MutableRectangle destination2 = this.canvasWidget.bounds().copy();
            final MutableRectangle source2 = destination2.copy();
            this.applyDragOffset(source2, 1.0f);
            return this.dragging ? source2 : source2.lerp(destination2, progress);
        }
        if (this.transitionType == TransitionType.SWIPE_LEFT || this.transitionType == TransitionType.SWIPE_RIGHT) {
            final MutableRectangle destination2 = this.canvasWidget.bounds().copy();
            final MutableRectangle source2 = destination2.copy();
            if (progress < 0.5) {
                this.applyDragOffset(source2, 1.0f);
                this.applyDragOffset(destination2, 2.0f);
            }
            else {
                this.applyDragOffset(source2, -2.0f);
            }
            return source2.lerp(destination2, progress);
        }
        return this.canvasWidget.bounds();
    }
    
    private void applyDragOffset(final MutableRectangle rectangle, final float multiplier) {
        final float sourceHeight = this.transitionSourceRectangle.getHeight();
        final float destinationHeight = this.canvasWidget.bounds().getHeight();
        rectangle.shift((this.lastMouseX - this.dragStartX) * multiplier, (this.lastMouseY - this.dragStartY) * multiplier);
        final float distance = Math.abs(this.lastMouseY - this.dragStartY) * multiplier;
        final float minPercentage = sourceHeight / destinationHeight / 2.0f;
        float shrinkPercentage = Math.min(distance / destinationHeight * 2.0f, 1.0f - minPercentage);
        shrinkPercentage = (float)CubicBezier.EASE_IN.curve(shrinkPercentage);
        rectangle.shrink(shrinkPercentage * rectangle.getHeight() / 2.0f);
    }
    
    private float getTransitionProgressAt(final long transitionTimePassed) {
        if (this.transitionSourceRectangle == null) {
            return 1.0f;
        }
        float progress = Math.min(transitionTimePassed / 200.0f, 1.0f);
        if (!this.isOpen()) {
            progress = 1.0f - progress;
        }
        return (float)this.transitionType.easing().curve(progress);
    }
    
    public void displayScreenshot(@NotNull final Screenshot screenshot) {
        final Screenshot previousScreenshot = this.screenshot;
        Rectangle rectangle = this.holder.getTileRectangleOf(screenshot);
        if (rectangle == null) {
            rectangle = new DefaultRectangle();
        }
        this.transitionSourceRectangle = rectangle;
        this.startTransition(TransitionType.OPEN);
        this.screenshot = screenshot;
        this.open = true;
        this.fileExists = screenshot.exists();
        this.titleWidget.setText(screenshot.getFileName());
        this.canvasWidget.setContextMenu(this.screenshot.getContextMenu());
        this.iconWidget.icon().set(screenshot.getIcon());
        this.statusWidget.setComponent(Component.translatable(this.fileExists ? "labymod.misc.loading" : "labymod.activity.screenshotBrowser.viewer.notExisting", new Component[0]));
        this.updateShareContextMenu(screenshot);
        final ScreenshotMeta meta = screenshot.getMeta();
        final String address = meta.get("Server Address");
        this.metaWidget.setComponent((address == null) ? Component.empty() : Component.text(address.replace(":25565", "")));
        this.trashButtonWidget.setEnabled(this.fileExists);
        this.openButtonWidget.setEnabled(this.fileExists);
        this.openButtonWidget.setHoverComponent(this.fileExists ? null : Component.translatable("labymod.activity.screenshotBrowser.viewer.notExisting", new Component[0]));
        this.copyButtonWidget.setEnabled(this.fileExists);
        this.copyButtonWidget.setVisible(true);
        this.copyButtonWidget.setHoverComponent(this.fileExists ? null : Component.translatable("labymod.activity.screenshotBrowser.viewer.notExisting", new Component[0]));
        this.previousButtonWidget.setEnabled(this.getScreenshotAtOffset(-1) != null);
        this.nextButtonWidget.setEnabled(this.getScreenshotAtOffset(1) != null);
        final ScreenshotBrowser browser = this.holder.browser();
        if (this.fileExists) {
            browser.getExecutor().queue(() -> {
                if (this.screenshot.equals(screenshot)) {
                    screenshot.updateQuality(Screenshot.QualityType.RAW);
                    this.iconWidget.icon().set(screenshot.getIcon());
                }
                if (previousScreenshot != null && !screenshot.equals(previousScreenshot) && previousScreenshot.exists()) {
                    previousScreenshot.updateQuality(this.holder.getQuality());
                }
            });
        }
        else {
            browser.removeScreenshot(screenshot);
        }
    }
    
    public boolean displayScreenshotAtOffset(final int offset) {
        final Screenshot screenshotAtOffset = this.getScreenshotAtOffset(offset);
        if (screenshotAtOffset == null) {
            return false;
        }
        this.displayScreenshot(screenshotAtOffset);
        this.startTransition(TransitionType.NONE);
        return true;
    }
    
    public Screenshot getScreenshot() {
        return this.screenshot;
    }
    
    public Screenshot getScreenshotAtOffset(final int offset) {
        final List<Screenshot> screenshots = this.holder.getScreenshots();
        int index = screenshots.indexOf(this.screenshot);
        if (index == -1) {
            return null;
        }
        index += offset;
        return (index < 0 || index >= screenshots.size()) ? null : screenshots.get(index);
    }
    
    public void onSectionChanged() {
        if (this.screenshot != null && !this.screenshot.exists()) {
            this.close();
        }
    }
    
    public void close() {
        if (!this.isOpen()) {
            return;
        }
        this.open = false;
        this.abortEditing();
        this.startTransition(TransitionType.CLOSE);
        this.holder.browser().getExecutor().queue(() -> {
            if (this.screenshot.exists()) {
                this.screenshot.updateQuality(Screenshot.QualityType.LOW);
            }
        });
    }
    
    private void copyToClipboard() {
        if (this.editing) {
            try {
                this.labyAPI.operatingSystemAccessor().copyToClipboard(this.createdPaintedGameImage());
            }
            catch (final Exception e) {
                e.printStackTrace();
            }
        }
        else {
            this.screenshot.copyToClipboard();
        }
    }
    
    private GameImage createdPaintedGameImage() throws Exception {
        final GameImage image = this.screenshot.asGameImage();
        if (image == null) {
            return null;
        }
        GameImage paintCanvas = this.paintCanvasTexture.getCurrentImage();
        if (paintCanvas == null) {
            return null;
        }
        paintCanvas = ScreenshotUtil.resize(paintCanvas, image.getWidth(), image.getHeight());
        final GameImage paintedImage = Laby.references().gameImageProvider().createImage(image.getWidth(), image.getHeight());
        for (int x = 0; x < paintCanvas.getWidth(); ++x) {
            for (int y = 0; y < paintCanvas.getHeight(); ++y) {
                final int background = image.getARGB(x, y);
                final int paintColor = paintCanvas.getARGB(x, y);
                paintedImage.setARGB(x, y, ColorUtil.blendColors(background, paintColor));
            }
        }
        return paintedImage;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    private boolean hasOpenAndCloseTransition() {
        return this.transitionSourceRectangle != null;
    }
    
    private void startTransition(final TransitionType type) {
        this.transitionType = type;
        this.transitionTicks = 0;
    }
    
    private void startEditing() {
        this.editing = true;
        this.cancelButtonWidget.setVisible(true);
        this.trashButtonWidget.setVisible(false);
        this.openButtonWidget.setVisible(false);
        this.shareButtonWidget.setVisible(false);
        this.editButtonWidget.setVisible(false);
        this.previousButtonWidget.setVisible(false);
        this.nextButtonWidget.setVisible(false);
        this.paintCanvasWidget.setVisible(true);
        this.paintThicknessSlider.setVisible(true);
        this.paintColorPicker.setVisible(true);
        try {
            final Icon screenshotIcon = this.screenshot.getIcon();
            final FloatVector2 dimension = ScreenshotUtil.maxSize(new FloatVector2((float)screenshotIcon.getResolutionWidth(), (float)screenshotIcon.getResolutionHeight()), 1920, 1080);
            final GameImage paintGameImage = Laby.references().gameImageProvider().createImage((int)dimension.getX(), (int)dimension.getY());
            (this.paintCanvasTexture = LabyMod.references().asynchronousTextureUploader().newRefreshableTexture(TextureFilter.LINEAR_MIPMAP_LINEAR, TextureFilter.LINEAR)).queueUpdate(paintGameImage);
            final ResourceLocation paintResourceLocation = Laby.labyAPI().renderPipeline().resources().resourceLocationFactory().createLabyMod("paint_overlay");
            this.paintCanvasTexture.bindTo(paintResourceLocation);
            final Icon paintIcon = Icon.texture(paintResourceLocation);
            this.paintCanvasWidget.icon().set(paintIcon);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    private void abortEditing() {
        this.editing = false;
        this.cancelButtonWidget.setVisible(false);
        this.trashButtonWidget.setVisible(true);
        this.openButtonWidget.setVisible(true);
        this.shareButtonWidget.setVisible(true);
        this.editButtonWidget.setVisible(true);
        this.previousButtonWidget.setVisible(true);
        this.nextButtonWidget.setVisible(true);
        this.paintCanvasWidget.setVisible(false);
        this.paintThicknessSlider.setVisible(false);
        this.paintColorPicker.setVisible(false);
        if (this.paintCanvasTexture != null && !this.paintCanvasTexture.wasReleased()) {
            this.paintCanvasTexture.release();
        }
    }
    
    private void updateShareContextMenu(final Screenshot screenshot) {
        final ContextMenu contextMenu = this.shareButtonWidget.createContextMenu();
        final boolean isConnected = this.labyAPI.labyConnect().isAuthenticated();
        final ScreenshotMeta meta = screenshot.getMeta();
        final boolean hasInsight = meta.hasInsight();
        contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable("labymod.activity.screenshotBrowser.context.upload", new Component[0])).icon(Textures.SpriteCommon.UPLOAD).disabled(!hasInsight || !isConnected || !this.fileExists).hoverComponent(this.fileExists ? (hasInsight ? (isConnected ? null : Component.translatable("labymod.activity.screenshotBrowser.viewer.notConnected", new Component[0])) : Component.translatable("labymod.activity.screenshotBrowser.viewer.noInsight", new Component[0])) : Component.translatable("labymod.activity.screenshotBrowser.viewer.notExisting", new Component[0])).clickHandler(entry -> {
            this.shareButtonWidget.setEnabled(false);
            this.screenshot.upload().thenAccept(url -> {
                if (url == null) {
                    return;
                }
                else {
                    OperatingSystem.getPlatform().openUrl(url);
                    Laby.labyAPI().minecraft().chatExecutor().copyToClipboard(url);
                    return;
                }
            }).thenRun(() -> this.shareButtonWidget.setEnabled(true));
            return true;
        }).build());
        final LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        final boolean hasOnlineFriends = session != null && session.getOnlineFriendCount() > 0;
        contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable("labymod.activity.screenshotBrowser.context.send", new Component[0])).icon(Textures.SpriteCommon.SHARE).disabled(!isConnected || !this.fileExists || !hasOnlineFriends).hoverComponent(this.fileExists ? (isConnected ? (hasOnlineFriends ? null : Component.translatable("labymod.activity.screenshotBrowser.viewer.noOnlineFriends", new Component[0])) : Component.translatable("labymod.activity.screenshotBrowser.viewer.notConnected", new Component[0])) : Component.translatable("labymod.activity.screenshotBrowser.viewer.notExisting", new Component[0])).subMenu(() -> {
            final ContextMenu subMenu = new ContextMenu();
            if (session != null) {
                session.getFriends().iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final Friend friend = iterator.next();
                    if (!friend.isOnline()) {
                        continue;
                    }
                    else {
                        subMenu.addEntry(ContextMenuEntry.builder().text(Component.text(friend.getName())).icon(Icon.head(friend.getUniqueId())).clickHandler(entry -> {
                            try {
                                friend.chat().sendImage(screenshot.asGameImage());
                                friend.chat().openChat();
                            }
                            catch (final Exception e) {
                                e.printStackTrace();
                            }
                            return 1 != 0;
                        }).build());
                    }
                }
            }
            return subMenu;
        }).build());
    }
    
    static {
        ANIMATE_CANVAS = ModifyReason.of("animateCanvas");
    }
    
    enum TransitionType
    {
        OPEN(CubicBezier.EASE_IN_OUT, true), 
        CLOSE(CubicBezier.EASE_IN_OUT, true), 
        DRAG_CLOSE(CubicBezier.EASE_IN_OUT, true), 
        DRAG(CubicBezier.EASE_IN_OUT, false), 
        SWIPE_RIGHT(CubicBezier.EASE_OUT, false), 
        SWIPE_LEFT(CubicBezier.EASE_OUT, false), 
        NONE(CubicBezier.LINEAR, false);
        
        private final CubicBezier easing;
        private final boolean fading;
        
        private TransitionType(final CubicBezier easing, final boolean fading) {
            this.easing = easing;
            this.fading = fading;
        }
        
        public CubicBezier easing() {
            return this.easing;
        }
        
        public boolean hasFading() {
            return this.fading;
        }
    }
    
    public interface ScreenshotViewerHolder
    {
        ScreenshotBrowser browser();
        
        Rectangle getTileRectangleOf(final Screenshot p0);
        
        List<Screenshot> getScreenshots();
        
        Screenshot.QualityType getQuality();
    }
}
