// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.notification;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.ScreenContext;
import java.util.Iterator;
import net.labymod.api.client.gui.icon.Icon;
import java.util.Objects;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.input.SimpleButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.notification.Notification;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class NotificationWidget extends SimpleWidget
{
    private final Notification notification;
    private int swipeStartPosition;
    private long swipeTimePassed;
    private float swipeVelocity;
    private float swipeDistance;
    
    public NotificationWidget(final Notification notification) {
        this.swipeStartPosition = -1;
        this.swipeTimePassed = 0L;
        this.swipeVelocity = 0.0f;
        this.swipeDistance = 0.0f;
        this.notification = notification;
    }
    
    @Override
    public String getDefaultRendererName() {
        return "Notification";
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final FlexibleContentWidget container = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)container).addId("container");
        final Icon icon = this.notification.icon();
        if (icon != null) {
            final IconWidget iconWidget = new IconWidget(icon);
            iconWidget.addId("icon");
            final Icon secondaryIcon = this.notification.secondaryIcon();
            if (secondaryIcon != null) {
                final IconWidget secondaryIconWidget = new IconWidget(secondaryIcon);
                secondaryIconWidget.addId("secondary-icon");
                ((AbstractWidget<IconWidget>)iconWidget).addChild(secondaryIconWidget);
            }
            container.addContent(iconWidget);
        }
        final FlexibleContentWidget rows = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)rows).addId("rows");
        Component title = this.notification.title();
        if (title != null) {
            if (this.notification.getIndex() > 0) {
                title = title.append(Component.text(" (" + (this.notification.getIndex() + 1), NamedTextColor.YELLOW));
            }
            final ComponentWidget titleWidget = ComponentWidget.component(title);
            titleWidget.addId("title");
            rows.addContent(titleWidget);
        }
        final Component text = this.notification.text();
        if (text != null) {
            final ComponentWidget textWidget = ComponentWidget.component(text);
            textWidget.addId("text");
            rows.addContent(textWidget);
        }
        final Icon thumbnail = this.notification.thumbnail();
        if (thumbnail != null) {
            final IconWidget thumbnailWidget = new IconWidget(thumbnail);
            thumbnailWidget.addId("thumbnail");
            rows.addContent(thumbnailWidget);
        }
        final FlexibleContentWidget buttons = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)buttons).addId("buttons");
        for (final Notification.NotificationButton button : this.notification.buttons()) {
            final SimpleButtonWidget buttonWidget = new SimpleButtonWidget(button.text());
            buttonWidget.addId("button");
            if (button.hoverText() != null) {
                buttonWidget.setHoverComponent(button.hoverText());
            }
            buttonWidget.setPressable(() -> {
                button.action().run();
                this.notification.remove();
                return;
            });
            buttons.addContent(buttonWidget);
        }
        rows.addContent(buttons);
        container.addFlexibleContent(rows);
        final IconWidget closeIcon = new IconWidget(Textures.SpriteCommon.SMALL_X);
        closeIcon.addId("close-button");
        final IconWidget iconWidget2 = closeIcon;
        final Notification notification = this.notification;
        Objects.requireNonNull(notification);
        iconWidget2.setPressable(notification::remove);
        container.addContent(closeIcon);
        ((AbstractWidget<FlexibleContentWidget>)this).addChild(container);
    }
    
    @Override
    public void render(final ScreenContext context) {
        if (this.isHovered()) {
            this.notification.holdProgress();
        }
        else {
            this.notification.resumeProgress();
        }
        double offset;
        if (this.notification.isFadingOut()) {
            this.swipeTimePassed += (long)TimeUtil.convertDeltaToMilliseconds(context.getTickDelta());
            offset = Math.min(this.swipeDistance + this.swipeVelocity * this.swipeTimePassed / 300.0f, this.bounds().getWidth());
        }
        else {
            offset = Math.max(this.swipeDistance, 0.0f);
        }
        final Stack stack = context.stack();
        stack.push();
        stack.translate((float)offset, 0.0f, 0.0f);
        super.render(context);
        stack.pop();
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.notification.isFadingOut()) {
            return false;
        }
        this.swipeStartPosition = mouse.getX();
        this.swipeVelocity = 0.0f;
        super.mouseClicked(mouse, mouseButton);
        return true;
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        if (this.notification.isFadingOut()) {
            return false;
        }
        if (this.isDragging()) {
            this.swipeDistance = (float)(mouse.getX() - this.swipeStartPosition);
            this.swipeVelocity = (float)Math.max(this.swipeVelocity, deltaX);
        }
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.swipeDistance > 0.0f && this.notification.isAlive() && !this.notification.isFadingOut()) {
            this.swipeTimePassed = 0L;
            if (this.swipeDistance > 10.0f) {
                this.notification.remove();
            }
            else {
                this.swipeDistance = 0.0f;
            }
            return true;
        }
        if (this.isHovered() && !this.notification.isFading()) {
            return this.notification.click();
        }
        return super.mouseReleased(mouse, mouseButton);
    }
    
    @Override
    protected boolean canHover() {
        return !this.notification.isFadingOut();
    }
    
    public Notification notification() {
        return this.notification;
    }
}
