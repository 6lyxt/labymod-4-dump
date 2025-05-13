// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.imgui.control;

import net.labymod.api.Laby;
import java.util.Locale;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.service.Identifiable;

public class ControlEntry implements Identifiable
{
    private final ImGuiBooleanType visible;
    private final String name;
    private final ImGuiWindow window;
    private final String id;
    
    public ControlEntry(final ImGuiWindow window) {
        this.window = window;
        this.visible = window.getVisible();
        this.name = window.getTitle();
        this.id = this.buildId();
    }
    
    public ImGuiBooleanType getVisible() {
        return this.visible;
    }
    
    public String getName() {
        return this.name;
    }
    
    public ImGuiWindow getWindow() {
        return this.window;
    }
    
    @Override
    public String getId() {
        return this.id;
    }
    
    private String buildId() {
        String id = this.name;
        id = id.toLowerCase(Locale.ROOT);
        id = id.replace(' ', '_');
        id = id.replace('-', '_');
        return Laby.labyAPI().getNamespace(this) + ":" + id;
    }
}
