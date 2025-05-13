// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.mojang.model;

import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.Laby;
import net.labymod.api.Constants;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.mojang.model.MojangModelService;

@Singleton
@Implements(MojangModelService.class)
public class DefaultMojangModelService implements MojangModelService
{
    @Override
    public Model getPlayerModel(final MinecraftServices.SkinVariant variant, final ResourceLocation textureResource) {
        final ResourceLocation modelResource = (variant == MinecraftServices.SkinVariant.SLIM) ? Constants.Resources.MODEL_ALEX : Constants.Resources.MODEL_STEVE;
        return this.createModel(textureResource, modelResource);
    }
    
    @Override
    public Model createModel(final ResourceLocation textureResource, final ResourceLocation modelResource) {
        final ModelService modelService = Laby.labyAPI().renderPipeline().modelService();
        final Model model = modelService.loadBlockBenchModel(modelResource);
        if (model != null) {
            model.setTextureLocation(textureResource);
            model.metadata().set("variant", (modelResource == Constants.Resources.MODEL_ALEX) ? MinecraftServices.SkinVariant.SLIM : MinecraftServices.SkinVariant.CLASSIC);
        }
        return model;
    }
}
