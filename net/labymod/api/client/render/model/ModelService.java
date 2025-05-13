// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model;

import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;
import java.util.Collection;
import net.labymod.api.client.resources.ResourceLocation;
import java.io.InputStream;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ModelService
{
    Model createEmptyModel();
    
    ModelPart createEmptyPart();
    
    @Nullable
    Model loadBlockBenchModel(final String p0);
    
    @Nullable
    Model loadBlockBenchModel(final InputStream p0);
    
    @Nullable
    Model loadBlockBenchModel(final ResourceLocation p0);
    
    Collection<ModelAnimation> loadBlockBenchAnimations(final String p0, final Collection<AnimationMeta<?>> p1);
    
    AnimationController createAnimationController();
    
    default void generateBoneIds(final Model model) {
        this.generateBoneIds(model, -1);
    }
    
    void generateBoneIds(final Model p0, final int p1);
}
