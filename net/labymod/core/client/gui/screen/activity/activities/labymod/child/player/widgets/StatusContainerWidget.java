// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets;

import net.labymod.api.client.gui.screen.Parent;
import java.util.function.Supplier;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

@AutoWidget
public class StatusContainerWidget<T extends Widget, S extends Widget> extends AbstractWidget<Widget>
{
    protected static final String STATUS_WIDGET_ID = "status-content";
    protected Supplier<S> statusWidgetSupplier;
    protected boolean status;
    protected S statusWidget;
    protected T contentWidget;
    
    public StatusContainerWidget(final Supplier<S> statusWidgetSupplier) {
        this.statusWidgetSupplier = statusWidgetSupplier;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.children.clear();
        if (this.status) {
            this.statusWidget = this.statusWidgetSupplier.get();
            if (this.statusWidget == null) {
                return;
            }
            this.statusWidget.addId("status-content");
            this.addChild(this.statusWidget);
        }
        else if (this.contentWidget != null) {
            final Widget firstChildIf = this.findFirstChildIf(widget -> widget == this.contentWidget);
            if (firstChildIf != null) {
                firstChildIf.setVisible(true);
            }
            else {
                this.addChild(this.contentWidget);
            }
        }
    }
    
    public StatusContainerWidget<T, S> displayStatus() {
        this.status = true;
        if (!this.initialized) {
            return this;
        }
        final Widget statusWidget = this.findFirstChildIf(widget -> widget == this.statusWidget);
        if (statusWidget == null) {
            (this.statusWidget = this.statusWidgetSupplier.get()).addId("status-content");
            this.statusWidget.setVisible(true);
            this.addChildInitialized(this.statusWidget);
        }
        else {
            statusWidget.setVisible(true);
        }
        if (this.contentWidget != null) {
            this.contentWidget.setVisible(false);
        }
        return this;
    }
    
    public StatusContainerWidget<T, S> displayContent() {
        this.status = false;
        if (!this.initialized) {
            return this;
        }
        if (this.statusWidget != null) {
            this.statusWidget.setVisible(false);
        }
        final Widget contentWidget = this.findFirstChildIf(widget -> widget == this.contentWidget);
        if (contentWidget == null) {
            this.contentWidget.setVisible(true);
            this.addChildInitialized(this.contentWidget);
        }
        else {
            contentWidget.setVisible(true);
        }
        return this;
    }
    
    public void updateContent(final T content) {
        this.contentWidget = content;
    }
    
    public S getStatusWidget() {
        return this.statusWidget;
    }
    
    public T getContentWidget() {
        return this.contentWidget;
    }
}
