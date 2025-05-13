// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.imgui.window;

import java.text.SimpleDateFormat;
import net.labymod.api.client.component.format.NamedTextColor;
import java.util.Iterator;
import java.util.Map;
import net.labymod.api.client.gui.lss.variable.LssVariable;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gfx.imgui.window.DocumentHandler;
import java.util.Set;
import net.labymod.api.client.gui.lss.variable.LssVariableHolder;
import java.util.HashSet;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import java.text.DateFormat;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;

public class VariablesImGuiWindow extends ImGuiWindow
{
    private static final DateFormat FORMAT;
    
    public VariablesImGuiWindow(@Nullable final ImGuiBooleanType open) {
        super("Variables", open, 0);
    }
    
    @Override
    protected void renderContent() {
        final DocumentHandler documentHandler = Laby.references().documentHandler();
        final Widget selectedWidget = documentHandler.getSelectedWidget();
        if (selectedWidget == null) {
            LabyImGui.text("No widget is selected");
            return;
        }
        this.renderVariables(Laby.labyAPI().minecraft().minecraftWindow(), "", new HashSet<String>());
    }
    
    private void renderVariables(final LssVariableHolder holder, final String indentation, final Set<String> usedKeys) {
        final Map<String, LssVariable> variables = holder.getLssVariables();
        for (final LssVariable variable : variables.values()) {
            this.renderVariable(variable, indentation, usedKeys);
        }
    }
    
    private void renderVariable(final LssVariable variable, final String indentation, final Set<String> usedKeys) {
        LabyImGui.beginGroup();
        LabyImGui.text(indentation);
        LabyImGui.sameLine(0.0f, 0.0f);
        final String key = variable.key();
        if (usedKeys.contains(key)) {
            LabyImGui.text(key, NamedTextColor.RED);
        }
        else {
            LabyImGui.text(key, NamedTextColor.GRAY);
        }
        LabyImGui.sameLine(0.0f, 0.0f);
        LabyImGui.text(": ", NamedTextColor.DARK_GRAY);
        LabyImGui.sameLine(0.0f, 0.0f);
        final String value = variable.value();
        LabyImGui.text(value, NamedTextColor.WHITE);
        LabyImGui.endGroup();
        if (LabyImGui.isItemHovered()) {
            LabyImGui.beginTooltip();
            this.keyValueText("Key", key);
            this.keyValueText("Value", value);
            this.keyValueText("Last update", VariablesImGuiWindow.FORMAT.format(variable.timestamp()));
            this.keyValueText("Holder", variable.holder().getClass().getSimpleName());
            LabyImGui.endTooltip();
        }
        usedKeys.add(key);
    }
    
    private void keyValueText(final String key, final String value) {
        LabyImGui.text(key, NamedTextColor.GRAY);
        LabyImGui.sameLine(0.0f, 0.0f);
        LabyImGui.text(": ", NamedTextColor.DARK_GRAY);
        LabyImGui.sameLine(0.0f, 0.0f);
        LabyImGui.text(value, NamedTextColor.WHITE);
    }
    
    static {
        FORMAT = new SimpleDateFormat("HH:mm:ss");
    }
}
