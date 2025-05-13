// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.vertex.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.gfx.shader.ShaderConstants;
import net.labymod.core.client.render.font.text.msdf.MSDFTextRenderer;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.shader.Shader;
import net.labymod.core.event.client.render.SetupVertexFormatShaderConstantsEvent;

@Deprecated
public class VertexFormatShaderConstantsConfigureListener
{
    @Subscribe
    @Deprecated
    public void onSetupVertexFormatShaderConstants(final SetupVertexFormatShaderConstantsEvent event) {
        if (!event.getName().equals("msdf_font") || event.shaderType() != Shader.Type.FRAGMENT) {
            return;
        }
        final TextRenderer textRenderer = Laby.references().textRenderer("msdf_text_renderer");
        if (textRenderer instanceof MSDFTextRenderer) {
            final ShaderConstants.Builder builder = ShaderConstants.builder();
            ((MSDFTextRenderer)textRenderer).fillShaderConstants(builder);
            event.setShaderConstants(builder.build());
        }
    }
}
