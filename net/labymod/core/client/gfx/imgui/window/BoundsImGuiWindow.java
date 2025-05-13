// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.imgui.window;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gfx.imgui.window.DocumentHandler;
import net.labymod.api.Laby;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;

public class BoundsImGuiWindow extends ImGuiWindow
{
    public BoundsImGuiWindow(@Nullable final ImGuiBooleanType visible) {
        super("Bounds", visible, 0);
    }
    
    @Override
    protected void prepareWindow() {
        super.prepareWindow();
        final ImGuiViewport windowViewport = LabyImGui.getWindowViewport();
        final FloatVector2 windowPosition = windowViewport.position();
        final float x = windowPosition.getX();
        final float y = windowPosition.getY();
        LabyImGui.setNextWindowPosAndSize(x + windowViewport.getWidth() + 205.0f, y, 200.0f, 300.0f);
    }
    
    @Override
    protected void renderContent() {
        final DocumentHandler documentHandler = Laby.references().documentHandler();
        final Widget selectedWidget = documentHandler.getSelectedWidget();
        if (selectedWidget == null) {
            LabyImGui.text("No widget is selected");
            return;
        }
        LabyImGui.renderBounds(selectedWidget.bounds());
    }
}
