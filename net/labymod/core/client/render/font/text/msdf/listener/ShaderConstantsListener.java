// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text.msdf.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gfx.shader.ShaderConstants;
import net.labymod.api.client.gfx.shader.Shader;
import net.labymod.core.event.client.render.SetupVertexFormatShaderConstantsEvent;
import net.labymod.core.client.render.font.text.msdf.MSDFTextRenderer;

public final class ShaderConstantsListener
{
    private final MSDFTextRenderer textRenderer;
    
    public ShaderConstantsListener(final MSDFTextRenderer textRenderer) {
        this.textRenderer = textRenderer;
    }
    
    @Subscribe
    public void onSetupVertexFormatShaderConstants(final SetupVertexFormatShaderConstantsEvent event) {
        if (!event.getName().equals("msdf_font") || event.shaderType() != Shader.Type.FRAGMENT) {
            return;
        }
        final ShaderConstants.Builder builder = ShaderConstants.builder();
        this.textRenderer.fillShaderConstants(builder);
        event.setShaderConstants(builder.build());
    }
}
