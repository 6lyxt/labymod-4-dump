// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.context;

import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuHandler;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@AutoWidget
@Link("activity/overlay/context/context.lss")
public class ContextMenuListWidget extends VerticalListWidget<ContextMenuEntryWidget>
{
    private final ContextMenu contextMenu;
    
    public ContextMenuListWidget(final ContextMenu contextMenu) {
        this.contextMenu = contextMenu;
        this.setSelectCallback(widget -> {
            if (!widget.isDisabledOnInitialize()) {
                final ContextMenuEntry entry = widget.entry();
                final ContextMenuHandler handler = entry.clickHandler();
                if (handler != null && handler.handle(entry)) {
                    Laby.references().soundService().play(SoundType.BUTTON_CLICK);
                    contextMenu.close();
                }
                else {
                    final ContextMenu subMenu = entry.getSubMenu();
                    if (subMenu != null) {
                        Laby.references().soundService().play(SoundType.BUTTON_CLICK);
                        contextMenu.openSubMenu(subMenu, entry);
                    }
                }
            }
        });
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        for (final ContextMenuEntry entry : this.contextMenu.entries()) {
            this.addChild(new ContextMenuEntryWidget(entry));
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return mouseButton != MouseButton.LEFT || super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        this.playAnimation("fade-in");
    }
    
    public ContextMenuEntryWidget getEntryWidget(final ContextMenuEntry entry) {
        for (final ContextMenuEntryWidget child : this.getChildren()) {
            if (child.entry() == entry) {
                return child;
            }
        }
        return null;
    }
}
