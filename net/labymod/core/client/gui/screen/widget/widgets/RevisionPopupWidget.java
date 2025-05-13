// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.main.LabyMod;
import java.util.List;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
@Link("activity/revision-popup.lss")
public class RevisionPopupWidget extends SimpleWidget
{
    private final Icon banner;
    private WidgetReference overlayReference;
    private FlexibleContentWidget containerWidget;
    
    public RevisionPopupWidget(final Icon banner) {
        this.banner = banner;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        ((AbstractWidget<Widget>)(this.containerWidget = new FlexibleContentWidget())).addId("container");
        final IconWidget banner = new IconWidget(this.banner);
        banner.addId("banner");
        this.containerWidget.addContent(banner);
        final HorizontalListWidget footer = new HorizontalListWidget();
        ((AbstractWidget<Widget>)footer).addId("footer");
        final ButtonWidget button = ButtonWidget.component(Component.translatable("labymod.ui.button.close", new Component[0]));
        ((AbstractWidget<Widget>)button).addId("ok");
        button.setPressable(this::close);
        footer.addEntry(button);
        this.containerWidget.addContent(footer);
        ((AbstractWidget<FlexibleContentWidget>)this).addChild(this.containerWidget);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (!this.containerWidget.bounds().isInRectangle(mouse)) {
            this.close();
        }
        return super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (key == Key.ENTER || key == Key.ESCAPE) {
            this.close();
            return true;
        }
        return super.keyPressed(key, type);
    }
    
    public void displayInOverlay() {
        this.overlayReference = this.displayInOverlay(new ArrayList<StyleSheet>(), this);
        if (this.overlayReference == null) {
            throw new IllegalStateException("Widget overlay not initialized yet");
        }
        this.overlayReference.clickRemoveStrategy(WidgetReference.ClickRemoveStrategy.OUTSIDE);
        this.overlayReference.keyPressRemoveStrategy(WidgetReference.KeyPressRemoveStrategy.ESCAPE);
    }
    
    public void close() {
        if (this.overlayReference != null && this.overlayReference.isAlive()) {
            this.overlayReference.remove();
        }
    }
    
    @Override
    public void dispose() {
        super.dispose();
        this.close();
        LabyMod.getInstance().getLabyConfig().save();
    }
}
