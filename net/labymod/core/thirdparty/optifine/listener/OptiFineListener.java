// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.thirdparty.optifine.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.event.client.render.shader.ShaderPipelineContextEvent;

public class OptiFineListener
{
    @Subscribe
    public void onShaderPipelineContext(final ShaderPipelineContextEvent event) {
        event.setActiveShaderPackSupplier(() -> OptiFine.config().hasShaders());
    }
}
