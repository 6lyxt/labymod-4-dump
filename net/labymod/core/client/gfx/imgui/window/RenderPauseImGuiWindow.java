// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.imgui.window;

import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.client.render.statistics.FrameTimer;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;

public class RenderPauseImGuiWindow extends ImGuiWindow
{
    private final FrameTimer frameTimer;
    
    public RenderPauseImGuiWindow(@Nullable final ImGuiBooleanType visible) {
        super("Render Pause", visible, 0);
        this.frameTimer = Laby.references().frameTimer();
    }
    
    @Override
    protected void renderContent() {
        final boolean paused = this.frameTimer.isPaused();
        LabyImGui.text("Current State: ");
        LabyImGui.sameLine(0.0f, 0.0f);
        LabyImGui.text(paused ? "Paused" : "Running", paused ? -65536 : -16711936);
        if (paused) {
            this.keyValue("\tPaused Frame", String.valueOf(this.frameTimer.getPausedFrame()));
        }
        this.keyValue("\tCurrent Frame", String.valueOf(this.frameTimer.getFrame()));
        LabyImGui.separator();
        if (paused) {
            LabyImGui.text("Next Frame:");
            LabyImGui.text("\tarrow up");
            LabyImGui.text("\t+ shift, next 10 Frames");
            LabyImGui.text("Resume:");
            LabyImGui.text("\tarrow down");
            if (LabyImGui.button("Resume")) {
                this.frameTimer.resume();
            }
            LabyImGui.sameLine(0.0f, 5.0f);
            if (LabyImGui.button("Next Frame")) {
                this.frameTimer.nextFrame();
            }
            LabyImGui.sameLine(0.0f, 5.0f);
            if (LabyImGui.button("Next 10 Frames")) {
                this.frameTimer.nextFrame(10);
            }
        }
        else {
            LabyImGui.text("Pause: ");
            LabyImGui.sameLine(0.0f, 0.0f);
            LabyImGui.text("\tarrow down");
            if (LabyImGui.button("Pause")) {
                this.frameTimer.pause();
            }
        }
    }
    
    private void keyValue(final String key, final String value) {
        LabyImGui.text(key);
        LabyImGui.sameLine(0.0f, 0.0f);
        LabyImGui.text(": ");
        LabyImGui.sameLine(0.0f, 0.0f);
        LabyImGui.text(value);
    }
}
