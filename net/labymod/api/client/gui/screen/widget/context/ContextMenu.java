// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.context;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.context.ContextMenuListWidget;
import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import java.util.List;
import net.labymod.api.util.bounds.ModifyReason;

public class ContextMenu
{
    private static final float PADDING = 1.0f;
    private static final ModifyReason DROPDOWN_POSITION;
    private final List<ContextMenuEntry> entries;
    private WidgetReference reference;
    private Object source;
    private ContextMenu subMenu;
    
    public ContextMenu() {
        this.entries = new ArrayList<ContextMenuEntry>();
    }
    
    public void open(final Object source) {
        this.source = source;
        this.open();
    }
    
    public void open() {
        if (!this.hasEntries()) {
            return;
        }
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        final Window window = minecraft.minecraftWindow();
        final Mouse mouse = minecraft.absoluteMouse().copy();
        (this.reference = Laby.labyAPI().screenOverlayHandler().displayInOverlay(new ContextMenuListWidget(this))).boundsUpdater((ref, bounds) -> {
            ContextMenuListWidget subMenuList = null;
            float x;
            float y;
            if (this.isSubMenu()) {
                final AppliedContextMenuEntry parent = this.getParent();
                subMenuList = (ContextMenuListWidget)parent.contextMenu().reference.widget();
                Widget widget = subMenuList.getEntryWidget(parent.entry());
                if (widget == null) {
                    widget = subMenuList;
                }
                x = subMenuList.bounds().getRight(BoundsType.OUTER);
                y = widget.bounds().getY(BoundsType.OUTER);
            }
            else {
                x = mouse.getX() + 1.0f;
                y = (float)mouse.getY();
            }
            final boolean dropUp = y + bounds.getHeight() > window.getScaledHeight();
            final boolean dropLeft = x + bounds.getWidth() > window.getScaledWidth();
            if (this.isSubMenu()) {
                if (dropLeft) {
                    x = subMenuList.bounds().getLeft(BoundsType.OUTER) - bounds.getWidth();
                }
            }
            else {
                x -= (dropLeft ? bounds.getWidth() : 0.0f);
            }
            final float y2 = y - (dropUp ? bounds.getHeight() : 0.0f);
            bounds.setPosition(x, y2, ContextMenu.DROPDOWN_POSITION);
            return;
        });
        this.reference.clickRemoveStrategy(WidgetReference.ClickRemoveStrategy.OUTSIDE);
        this.reference.destroyHandlers().add(this::close);
    }
    
    public void openSubMenu(final ContextMenu subMenu, final ContextMenuEntry source) {
        if (this.reference == null) {
            return;
        }
        (this.subMenu = subMenu).open(new AppliedContextMenuEntry(this, source));
        this.reference.clickRemoveStrategy(WidgetReference.ClickRemoveStrategy.NEVER);
    }
    
    public void close() {
        if (this.reference != null && this.reference.isAlive()) {
            this.reference.remove();
        }
        if (this.isSubMenu()) {
            final ContextMenu parent = this.getParent().contextMenu();
            parent.close();
            if (parent.reference != null) {
                parent.reference.clickRemoveStrategy(WidgetReference.ClickRemoveStrategy.OUTSIDE);
            }
        }
        if (this.subMenu != null) {
            final ContextMenu subMenu = this.subMenu;
            this.subMenu = null;
            subMenu.close();
            if (this.reference != null) {
                this.reference.clickRemoveStrategy(WidgetReference.ClickRemoveStrategy.OUTSIDE);
            }
        }
    }
    
    public void addEntry(final ContextMenuEntry entry) {
        this.entries.add(entry);
    }
    
    public ContextMenu with(final ContextMenuEntry entry) {
        this.entries.add(entry);
        return this;
    }
    
    public List<ContextMenuEntry> entries() {
        return this.entries;
    }
    
    public boolean isOpen() {
        return this.reference != null && this.reference.isAlive();
    }
    
    public boolean isSubMenu() {
        return this.source instanceof AppliedContextMenuEntry;
    }
    
    public AppliedContextMenuEntry getParent() {
        return this.isSubMenu() ? ((AppliedContextMenuEntry)this.source) : null;
    }
    
    public boolean hasEntries() {
        return !this.entries.isEmpty();
    }
    
    public Object getSource() {
        return this.source;
    }
    
    static {
        DROPDOWN_POSITION = ModifyReason.of("dropdownPosition");
    }
}
