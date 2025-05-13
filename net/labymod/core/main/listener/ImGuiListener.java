// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gfx.imgui.control.ControlEntryRegistry;
import net.labymod.core.client.gfx.imgui.window.BlurRendererImGuiWindow;
import net.labymod.core.client.gfx.imgui.window.OtherImGuiWindow;
import net.labymod.core.client.gfx.imgui.window.RenderStatisticsImGuiWindow;
import net.labymod.core.client.gfx.imgui.window.RenderPauseImGuiWindow;
import net.labymod.core.client.gfx.imgui.window.InstructionImGuiWindow;
import net.labymod.core.client.gfx.imgui.window.VariablesImGuiWindow;
import net.labymod.core.client.gfx.imgui.window.DocumentImGuiWindow;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import java.util.function.Function;
import net.labymod.core.client.gfx.imgui.window.BoundsImGuiWindow;
import net.labymod.api.event.labymod.debug.ImGuiInitializeEvent;

public class ImGuiListener
{
    @Subscribe
    public void onImGuiInitialize(final ImGuiInitializeEvent event) {
        final ControlEntryRegistry registry = event.registry();
        registry.registerEntry(true, (Function<ImGuiBooleanType, ImGuiWindow>)BoundsImGuiWindow::new);
        registry.registerEntry(true, (Function<ImGuiBooleanType, ImGuiWindow>)DocumentImGuiWindow::new);
        registry.registerEntry(true, (Function<ImGuiBooleanType, ImGuiWindow>)VariablesImGuiWindow::new);
        registry.registerEntry(true, (Function<ImGuiBooleanType, ImGuiWindow>)InstructionImGuiWindow::new);
        registry.registerEntry(false, (Function<ImGuiBooleanType, ImGuiWindow>)RenderPauseImGuiWindow::new);
        registry.registerEntry(false, (Function<ImGuiBooleanType, ImGuiWindow>)RenderStatisticsImGuiWindow::new);
        registry.registerEntry(false, (Function<ImGuiBooleanType, ImGuiWindow>)OtherImGuiWindow::new);
        registry.registerEntry(false, (Function<ImGuiBooleanType, ImGuiWindow>)BlurRendererImGuiWindow::new);
    }
}
