// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.renderer;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class ProgressBarWidget extends SimpleWidget
{
    private static final ModifyReason UPDATE_BAR;
    private DivWidget barWidget;
    private ComponentWidget textWidget;
    private float progress;
    
    public ProgressBarWidget() {
        this.progress = 0.0f;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        (this.barWidget = new DivWidget()).addId("bar");
        ((AbstractWidget<DivWidget>)this).addChild(this.barWidget);
        (this.textWidget = ComponentWidget.empty()).addId("text");
        ((AbstractWidget<ComponentWidget>)this).addChild(this.textWidget);
        this.updateBar();
    }
    
    private void updateBar() {
        final Bounds bounds = this.bounds();
        if (this.barWidget != null) {
            this.barWidget.bounds().setBounds(bounds.getLeft(), bounds.getTop(), bounds.getWidth() * this.progress, bounds.getHeight(), ProgressBarWidget.UPDATE_BAR);
        }
        final Component component = Component.text("" + (int)(this.progress * 100.0f));
        final RenderableComponent renderableComponent = RenderableComponent.of(component);
        if (this.textWidget != null) {
            this.textWidget.setComponent(component);
            this.textWidget.bounds().setPosition(bounds.getCenterX() - renderableComponent.getWidth() / 2.0f, bounds.getTop(), ProgressBarWidget.UPDATE_BAR);
        }
    }
    
    public float getProgress() {
        return this.progress;
    }
    
    public void setProgress(final float progress) {
        this.progress = progress;
        this.updateBar();
    }
    
    static {
        UPDATE_BAR = ModifyReason.of("updatebar");
    }
}
