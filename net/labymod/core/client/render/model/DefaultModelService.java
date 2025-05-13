// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.model;

import net.labymod.core.main.user.shop.item.geometry.BlockBenchLoader;
import java.util.Iterator;
import net.labymod.core.client.render.model.animation.DefaultAnimationController;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.core.main.user.shop.item.geometry.animation.AnimationLoader;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;
import java.util.Collection;
import net.labymod.api.client.resources.ResourceLocation;
import java.io.InputStream;
import net.labymod.core.main.user.shop.item.geometry.GeometryLoader;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.model.ModelService;

@Singleton
@Implements(ModelService.class)
public class DefaultModelService implements ModelService
{
    private static final Logging LOGGER;
    private int boneId;
    
    @Override
    public Model createEmptyModel() {
        return new DefaultModel();
    }
    
    @Override
    public ModelPart createEmptyPart() {
        return new DefaultModelPart();
    }
    
    @Override
    public Model loadBlockBenchModel(final String json) {
        try {
            return new GeometryLoader(json).toModel(blockBenchMeta -> blockBenchMeta.shouldGenerateBoneIds(true));
        }
        catch (final Exception exception) {
            DefaultModelService.LOGGER.error("Block bench model could not be loaded.", exception);
            return null;
        }
    }
    
    @Override
    public Model loadBlockBenchModel(final InputStream inputStream) {
        try {
            return new GeometryLoader(inputStream).toModel(blockBenchMeta -> blockBenchMeta.shouldGenerateBoneIds(true));
        }
        catch (final Exception exception) {
            DefaultModelService.LOGGER.error("Block bench model could not be loaded.", exception);
            return null;
        }
    }
    
    @Override
    public Model loadBlockBenchModel(final ResourceLocation location) {
        try {
            return this.loadBlockBenchModel(location.openStream());
        }
        catch (final Exception exception) {
            DefaultModelService.LOGGER.error("Block bench model could not be loaded.", exception);
            return null;
        }
    }
    
    @Override
    public Collection<ModelAnimation> loadBlockBenchAnimations(final String json, final Collection<AnimationMeta<?>> supportedMeta) {
        return new AnimationLoader(json).load(supportedMeta).getAnimations();
    }
    
    @Override
    public AnimationController createAnimationController() {
        return new DefaultAnimationController();
    }
    
    @Override
    public void generateBoneIds(final Model model, final int modelPartSize) {
        if (modelPartSize > 240) {
            for (final ModelPart modelPart : model.getChildren().values()) {
                modelPart.setId(this.boneId++);
                this.generateBoneId(modelPart.getChildren().values());
                this.boneId = 0;
            }
        }
        else {
            this._generateBoneIds(model);
        }
        this.boneId = 0;
    }
    
    private void _generateBoneIds(final Model model) {
        this.boneId = 0;
        this.generateBoneId(model.getChildren().values());
        if (this.boneId > 240) {
            throw new IllegalStateException("Model has " + this.boneId + " bones, this exceeds the maximum limit of 240 bones.");
        }
    }
    
    private void generateBoneId(final Collection<? extends ModelPart> children) {
        for (final ModelPart child : children) {
            child.setId(this.boneId++);
            this.generateBoneId(child.getChildren().values());
        }
    }
    
    static {
        LOGGER = Logging.create(ModelService.class);
    }
}
