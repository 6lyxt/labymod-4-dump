// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.imgui.window;

import java.util.Iterator;
import net.labymod.api.client.gfx.imgui.control.ControlEntryRegistry;
import net.labymod.core.client.gfx.imgui.control.DefaultControlEntryRegistry;
import net.labymod.api.client.gfx.imgui.control.ControlEntry;
import net.labymod.api.util.KeyValue;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;

public class ControlImGuiWindow extends ImGuiWindow
{
    private final LabyConfig labyConfig;
    
    public ControlImGuiWindow() {
        super("Control", null, 2097152);
        this.labyConfig = Laby.labyAPI().config();
    }
    
    @Override
    protected void prepareWindow() {
        super.prepareWindow();
        LabyImGui.setNextWindowPosAndSize(150.0f, 10.0f, 300.0f, 400.0f);
    }
    
    @Override
    protected void renderContent() {
        LabyImGui.text("LabyMod");
        final ControlEntryRegistry controlEntryRegistry = Laby.references().controlEntryRegistry();
        for (KeyValue<ControlEntry> element : controlEntryRegistry.getElements()) {
            final ControlEntry value = element.getValue();
            final ImGuiBooleanType shown = value.getVisible();
            LabyImGui.checkbox("Show " + value.getName(), shown);
            ((DefaultControlEntryRegistry)controlEntryRegistry).setState(value.getWindow());
            if (shown.get()) {
                value.getWindow().render();
            }
        }
        LabyImGui.separator();
    }
    
    @Override
    protected boolean showContentIfCollapsed() {
        return true;
    }
}
