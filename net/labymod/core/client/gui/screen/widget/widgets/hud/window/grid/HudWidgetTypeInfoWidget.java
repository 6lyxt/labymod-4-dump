// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.hud.window.grid;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.revision.Revision;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget;

@AutoWidget
public class HudWidgetTypeInfoWidget extends InterpolateWidget
{
    private final HudWidget<?> hudWidget;
    private final HudWidgetTilesGridWidget gridWidget;
    
    public HudWidgetTypeInfoWidget(final HudWidget<?> hudWidget, final HudWidgetTilesGridWidget gridWidget) {
        super(50.0f);
        this.hudWidget = hudWidget;
        this.gridWidget = gridWidget;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.setAttributeState(AttributeState.ENABLED, !this.hudWidget.isEnabled());
        if (this.hudWidget.isEnabled() && this.gridWidget.draggingWidget() != this) {
            this.removeId("available");
        }
        else {
            this.addId("available");
        }
        final Icon icon = this.hudWidget.getIcon();
        final IconWidget iconWidget = new IconWidget((icon == null) ? Textures.SpriteCommon.QUESTION_MARK : icon);
        iconWidget.addId("icon");
        ((AbstractWidget<IconWidget>)this).addChild(iconWidget);
        final ComponentWidget componentWidget = ComponentWidget.component(this.hudWidget.displayName());
        componentWidget.addId("name");
        ((AbstractWidget<ComponentWidget>)this).addChild(componentWidget);
        final Revision revision = this.hudWidget.getRevision();
        if (revision != null && revision.isRelevant()) {
            final IconWidget newBadge = new IconWidget(Textures.SpriteCommon.NEW);
            newBadge.addId("new-badge");
            newBadge.setHoverComponent(Component.translatable("labymod.misc.introduced", new Component[0]).color(NamedTextColor.BLUE).argument(((BaseComponent<Component>)Component.text(revision.getDisplayName()).color(NamedTextColor.WHITE)).decorate(TextDecoration.BOLD)));
            componentWidget.addId("new-name");
            ((AbstractWidget<IconWidget>)this).addChild(newBadge);
        }
        final ContextMenu contextMenu = this.createContextMenu();
        if (this.hudWidget.isEnabled()) {
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.settings", new Component[0])).clickHandler((entry, source) -> this.gridWidget.editor().window().displaySettings(this.hudWidget)).build());
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.remove", new Component[0])).clickHandler((entry, source) -> this.gridWidget.editor().renderer().destroyHudWidget(this.hudWidget, false)).build());
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.reset", new Component[0])).clickHandler((entry, source) -> this.gridWidget.editor().renderer().resetHudWidget(this.hudWidget)).build());
        }
        this.skipInterpolation();
    }
    
    public boolean canHover() {
        return super.canHover();
    }
    
    public HudWidget<?> hudWidget() {
        return this.hudWidget;
    }
}
