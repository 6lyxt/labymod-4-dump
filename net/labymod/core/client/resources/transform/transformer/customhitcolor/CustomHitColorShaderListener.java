// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.transform.transformer.customhitcolor;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.resources.transform.RegisterResourceTransformerEvent;
import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocationFactory;
import javax.inject.Singleton;

@Singleton
public final class CustomHitColorShaderListener
{
    private final ResourceLocationFactory locationFactory;
    
    @Inject
    public CustomHitColorShaderListener() {
        this.locationFactory = Laby.references().resourceLocationFactory();
    }
    
    @Subscribe
    public void registerResourceTransformer(final RegisterResourceTransformerEvent event) {
        event.register(this.locationFactory.createMinecraft("shaders/core/rendertype_armor_cutout_no_cull.vsh"), Laby.references().resourceTransformer("damage_overlay_rendertype_armor_cutout_no_cull_vertex_shader"));
        event.register(this.locationFactory.createMinecraft("shaders/core/rendertype_armor_cutout_no_cull.fsh"), Laby.references().resourceTransformer("damage_overlay_rendertype_armor_cutout_no_cull_fragment_shader"));
        event.register(this.locationFactory.createMinecraft("shaders/core/rendertype_armor_cutout_no_cull.json"), Laby.references().resourceTransformer("damage_overlay_rendertype_armor_cutout_no_cull_json"));
    }
}
