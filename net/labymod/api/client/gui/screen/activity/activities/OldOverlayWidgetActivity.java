// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.activities;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.Parent;
import java.util.Iterator;
import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import java.util.function.Consumer;
import java.util.Collection;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlay;

@Deprecated
@AutoActivity
public class OldOverlayWidgetActivity extends ScreenOverlay
{
    private final Activity origin;
    private final Widget widget;
    private final Collection<Runnable> closeCallbacks;
    private final int openFrame;
    private boolean closeAlways;
    private boolean allowAnimations;
    private Consumer<Bounds> positionUpdater;
    private AbstractWidget<?> attachedWidget;
    
    @Deprecated
    public OldOverlayWidgetActivity(final int zLayer, final Activity origin) {
        this(zLayer, origin, null);
    }
    
    @Deprecated
    public OldOverlayWidgetActivity(final int zLayer, final Activity origin, final Widget widget) {
        this(zLayer, origin, widget, null);
    }
    
    @Deprecated
    public OldOverlayWidgetActivity(final int zLayer, final Activity origin, final Widget widget, final Runnable closeCallback) {
        super(zLayer);
        this.closeCallbacks = new ArrayList<Runnable>();
        this.closeAlways = false;
        this.allowAnimations = true;
        this.origin = origin;
        (this.widget = widget).addId("overlay-widget");
        if (closeCallback != null) {
            this.addCloseCallback(closeCallback);
        }
        this.openFrame = Laby.references().frameTimer().getFrame();
        this.setActive(true);
        this.closeCallbacks.add(() -> {
            this.labyAPI.screenOverlayHandler().unregisterOverlay(this);
            this.origin.overlayClosed(this);
        });
    }
    
    public void closeOverlayActivity() {
        if (this.openFrame == Laby.references().frameTimer().getFrame()) {
            return;
        }
        this.setClosing(true);
        this.widget.dispose();
        this.widget.destroy();
        if (this.allowAnimations) {
            this.widget.playAnimation("fade-out-overlay-widget", () -> {
                this.closeCallbacks.iterator();
                final Iterator iterator2;
                while (iterator2.hasNext()) {
                    final Runnable callback2 = iterator2.next();
                    callback2.run();
                }
            });
        }
        else {
            for (final Runnable callback : this.closeCallbacks) {
                callback.run();
            }
        }
    }
    
    public OldOverlayWidgetActivity setCloseAlways() {
        this.closeAlways = true;
        return this;
    }
    
    public OldOverlayWidgetActivity attachBounds(final Consumer<Bounds> positionUpdater) {
        (this.positionUpdater = positionUpdater).accept(this.widget.bounds());
        return this;
    }
    
    public AbstractWidget<?> getAttachedWidget() {
        return this.attachedWidget;
    }
    
    public OldOverlayWidgetActivity attachedWidget(final AbstractWidget<?> attachedWidget) {
        this.attachedWidget = attachedWidget;
        return this;
    }
    
    public OldOverlayWidgetActivity disableAnimations() {
        this.allowAnimations = false;
        return this;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        ((Document)this.document).addChild(this.widget);
    }
    
    @Override
    protected void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        this.widget.playAnimation("fade-in-overlay-widget", null);
    }
    
    @Override
    public void reloadStyles() {
        super.reloadStyles();
        for (final StyleSheet stylesheet : this.origin.getStylesheets()) {
            this.addStyle(stylesheet);
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        final boolean res = super.mouseClicked(mouse, mouseButton);
        if (this.closeAlways || !this.widget.isHovered()) {
            this.closeOverlayActivity();
        }
        return res || this.widget.isHovered();
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        return super.mouseReleased(mouse, mouseButton) || this.widget.isHovered();
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        return super.mouseDragged(mouse, button, deltaX, deltaY) || this.widget.isHovered();
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        return super.mouseScrolled(mouse, scrollDelta) || this.widget.isHovered();
    }
    
    @Override
    public void render(final ScreenContext context) {
        Label_0044: {
            if (this.attachedWidget != null) {
                Parent parent = this.attachedWidget.getParent();
                while (!(parent instanceof AbstractWidget) || !((AbstractWidget)parent).isOutOfBounds()) {
                    if ((parent = parent.getParent()) == null) {
                        break Label_0044;
                    }
                }
                return;
            }
        }
        if (this.positionUpdater != null) {
            this.positionUpdater.accept(this.widget.bounds());
        }
        super.render(context);
    }
    
    @Override
    public boolean displayPreviousScreen() {
        this.closeOverlayActivity();
        return true;
    }
    
    public OldOverlayWidgetActivity addCloseCallback(final Runnable closeCallback) {
        this.closeCallbacks.add(closeCallback);
        return this;
    }
    
    public Activity getOrigin() {
        return this.origin;
    }
    
    public boolean isHovered() {
        return this.widget.isHovered();
    }
    
    public Widget widget() {
        return this.widget;
    }
    
    @Override
    public void onOpenScreen() {
        this.origin.overlayUpdated(this);
        super.onOpenScreen();
    }
}
