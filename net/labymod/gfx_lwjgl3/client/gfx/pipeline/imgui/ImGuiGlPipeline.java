// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui;

import imgui.ImDrawData;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.loader.platform.PlatformEnvironment;
import imgui.gl3.ImGuiImplGl3;

public class ImGuiGlPipeline
{
    private final LabyImGuiImplGl2 gl2;
    private final ImGuiImplGl3 gl3;
    
    public ImGuiGlPipeline() {
        this.gl2 = new LabyImGuiImplGl2();
        this.gl3 = new ImGuiImplGl3();
    }
    
    public void init(final String glslVersion) {
        if (PlatformEnvironment.isNoShader() && OperatingSystem.isOSX()) {
            this.gl2.init();
        }
        else {
            this.gl3.init(glslVersion);
        }
    }
    
    public void renderData(final ImDrawData data) {
        if (PlatformEnvironment.isNoShader() && OperatingSystem.isOSX()) {
            this.gl2.renderDrawData(data);
        }
        else {
            this.gl3.renderDrawData(data);
        }
    }
}
