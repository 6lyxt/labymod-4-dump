// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.popup;

import net.labymod.api.client.gui.lss.style.StyleSheet;
import java.util.Iterator;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
@Link(value = "activity/popup.lss", priority = -127)
public class AdvancedPopupWidget extends AbstractWidget<Widget>
{
    private final AdvancedPopup popup;
    
    protected AdvancedPopupWidget(@NotNull final AdvancedPopup popup) {
        Objects.requireNonNull(popup, "popup");
        (this.popup = popup).setup(this);
    }
    
    @NotNull
    public static AdvancedPopupWidget of(@NotNull final AdvancedPopup popup) {
        return new AdvancedPopupWidget(popup);
    }
    
    @Override
    public void initialize(final Parent parent) {
        this.addId("popup-wrapper");
        super.initialize(parent);
        final Widget containerWidget = this.popup.initialize();
        if (containerWidget == null) {
            return;
        }
        containerWidget.addId("popup-container");
        if (this.children.contains(containerWidget)) {
            return;
        }
        final DivWidget containerWrapper = new DivWidget();
        containerWrapper.addId("popup-container-wrapper");
        containerWrapper.addChild(containerWidget);
        this.children.add((T)containerWrapper);
    }
    
    @Override
    public void tick() {
        super.tick();
        this.popup.tick();
    }
    
    @Override
    public void postInitialize() {
        super.postInitialize();
        Laby.references().linkMetaLoader().loadMeta(this.popup.getClass(), styleSheet -> {
            if (!this.styleSheets.contains(styleSheet)) {
                this.applyStyleSheet(styleSheet);
            }
        });
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (super.mouseClicked(mouse, mouseButton)) {
            return true;
        }
        for (final Widget child : this.children) {
            if (child.bounds().isInRectangle(BoundsType.OUTER, mouse)) {
                return false;
            }
        }
        this.popup.close();
        return true;
    }
    
    @NotNull
    public final AdvancedPopup popup() {
        return this.popup;
    }
}
