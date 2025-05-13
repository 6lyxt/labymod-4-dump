// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.imgui.window;

import net.labymod.api.client.gfx.target.RenderTarget;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.Laby;
import net.labymod.core.client.render.draw.DefaultBlurRenderer;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;

public class BlurRendererImGuiWindow extends ImGuiWindow
{
    public BlurRendererImGuiWindow(@Nullable final ImGuiBooleanType visible) {
        super("Blur Renderer", visible, 0);
    }
    
    @Override
    protected void renderContent() {
        final DefaultBlurRenderer blurRenderer = (DefaultBlurRenderer)Laby.references().blurRenderer();
        final RenderTarget[] renderTargets = blurRenderer.getRenderTargets();
        for (int length = renderTargets.length, i = 0; i < length; ++i) {
            final RenderTarget renderTarget = renderTargets[i];
            final int width = renderTarget.getWidth();
            final int height = renderTarget.getHeight();
            LabyImGui.beginGroup();
            LabyImGui.image(renderTarget.findColorAttachment().getId(), (float)width, (float)height, 0.0f, 1.0f, 1.0f, 0.0f);
            LabyImGui.sameLine(2.0f);
            LabyImGui.text(width + "x" + height);
            LabyImGui.endGroup();
        }
    }
}
