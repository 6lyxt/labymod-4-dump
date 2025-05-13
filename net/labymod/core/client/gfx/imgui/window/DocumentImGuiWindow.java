// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.imgui.window;

import net.labymod.api.client.gui.screen.activity.ElementActivity;
import java.util.Collection;
import net.labymod.api.util.StringUtil;
import net.labymod.api.util.color.format.ColorFormat;
import java.util.List;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Iterator;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.client.gfx.imgui.window.DocumentHandler;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;

public class DocumentImGuiWindow extends ImGuiWindow
{
    private static final int VISIBLE_HOVERED_ARGB = -1;
    private static final int VISIBLE_INVALID_ARGB = -52429;
    private static final int VISIBLE_VALID_DEPTH_0_ARGB = -14518273;
    private static final int VISIBLE_VALID_DEPTH_1_ARGB = -10061825;
    private static final int NOT_VISIBLE_ARGB = -12303292;
    private final DocumentHandler documentHandler;
    
    public DocumentImGuiWindow(@Nullable final ImGuiBooleanType open) {
        super("Document", open, 0);
        this.documentHandler = Laby.references().documentHandler();
    }
    
    @Override
    protected void renderContent() {
        Object selectedActivity = this.documentHandler.getSelectedActivity();
        if (selectedActivity == null) {
            this.documentHandler.resetSelectedActivity();
        }
        else {
            String name = "";
            if (selectedActivity instanceof final Activity activity) {
                name = activity.getName();
            }
            if (LabyImGui.beginCombo("##", name)) {
                for (final Activity activity2 : this.documentHandler.getActivities()) {
                    final boolean isSelected = selectedActivity == activity2;
                    if (LabyImGui.selectable(activity2.getName(), isSelected)) {
                        selectedActivity = activity2;
                        this.documentHandler.setSelectedActivity(activity2);
                    }
                    if (isSelected) {
                        LabyImGui.setItemDefaultFocus();
                    }
                }
                LabyImGui.endCombo();
            }
            LabyImGui.separator();
            if (selectedActivity instanceof final Activity activity) {
                this.renderElement(((ElementActivity<Object>)activity).document());
            }
        }
        if (LabyImGui.isWindowFocused() && LabyImGui.isKeyPressed(Key.TAB)) {
            this.documentHandler.onKey(new KeyEvent(KeyEvent.State.PRESS, Key.TAB));
        }
        this.documentHandler.clear();
    }
    
    private void renderElement(final Object widget) {
        Widget acutalWidget = null;
        if (widget instanceof final Widget widget2) {
            acutalWidget = widget2;
        }
        if (acutalWidget == null) {
            return;
        }
        final List<? extends Widget> children = acutalWidget.getChildren();
        LabyImGui.pushId(acutalWidget.getUniqueId());
        final boolean hovered = acutalWidget.isHovered();
        if (hovered) {
            this.documentHandler.setTargetWidget(acutalWidget);
        }
        if (this.documentHandler.isSelectedWidget(acutalWidget)) {
            this.documentHandler.setTargetWidget(acutalWidget);
        }
        final boolean visible = acutalWidget.isVisible();
        final boolean invalid = visible && (acutalWidget.bounds().getWidth() <= 0.0f || acutalWidget.bounds().getHeight() <= 0.0f);
        final int color = this.evaluateElementARGB(visible, hovered, invalid);
        final boolean open = this.buildWidgetItem(acutalWidget, color);
        if (open) {
            for (final Widget child : children) {
                this.renderElement(child);
            }
            LabyImGui.popTree();
        }
        LabyImGui.popId();
    }
    
    private boolean buildWidgetItem(final Widget widget, final int color) {
        final List<CharSequence> ids = widget.getIds();
        final boolean noIds = ids.isEmpty();
        int treeNodeFlags = 4228;
        if (widget.getChildren().isEmpty()) {
            treeNodeFlags |= 0x100;
        }
        else {
            treeNodeFlags |= 0x20;
        }
        if (this.documentHandler.isSelectedWidget(widget)) {
            treeNodeFlags |= 0x1;
        }
        LabyImGui.pushStyleColor(0, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, color));
        LabyImGui.pushStyleColor(24, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, 553647872));
        LabyImGui.pushStyleColor(26, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, 1342242815));
        LabyImGui.pushStyleColor(25, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, 536936447));
        LabyImGui.pushId(widget.getUniqueId() + "_treeNode");
        final boolean open = LabyImGui.treeNodeEx(widget.toString(), treeNodeFlags);
        if (LabyImGui.isItemHovered()) {
            this.documentHandler.setTargetWidget(widget);
        }
        if (LabyImGui.isItemClicked()) {
            this.documentHandler.setSelectedWidget(widget);
        }
        LabyImGui.popId();
        LabyImGui.popStyleColor();
        LabyImGui.popStyleColor();
        LabyImGui.popStyleColor();
        LabyImGui.popStyleColor();
        widget.renderExtraDebugInformation();
        if (!noIds) {
            LabyImGui.sameLine(0.0f, 0.0f);
            final String widgetIds = " (" + StringUtil.join(ids, ", ");
            LabyImGui.text(widgetIds, 180, 180, 180, 255);
        }
        return open;
    }
    
    private int evaluateElementARGB(final boolean visible, final boolean hovered, final boolean invalid) {
        if (!visible) {
            return -12303292;
        }
        if (hovered) {
            return -1;
        }
        if (invalid) {
            return -52429;
        }
        return -14518273;
    }
}
