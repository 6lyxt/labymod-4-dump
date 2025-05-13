// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client.model.geom;

import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import net.labymod.core.client.accessor.model.ModelPartCubeAccessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.ArrayList;
import net.labymod.v1_21_4.client.render.model.ModelPartAnimationTransformation;
import net.labymod.api.client.gfx.pipeline.material.Material;
import org.spongepowered.asm.mixin.Final;
import java.util.Map;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.client.render.model.box.ModelBox;
import java.util.List;
import net.labymod.api.util.math.Transformation;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.model.ModelPart;

@Mixin({ gfe.class })
public abstract class MixinModelPart implements ModelPart
{
    private final Transformation labyMod$transformation;
    private final Transformation labyMod$animationTransformation;
    private final List<ModelBox> labyMod$boxes;
    @Shadow
    public boolean k;
    @Shadow
    @Final
    private Map<String, gfe> n;
    @Shadow
    @Final
    private List<gfe.a> m;
    private Material labyMod$material;
    
    public MixinModelPart() {
        this.labyMod$transformation = new Transformation();
        this.labyMod$animationTransformation = new ModelPartAnimationTransformation((gfe)this, this.labyMod$transformation);
        this.labyMod$boxes = new ArrayList<ModelBox>();
    }
    
    @Override
    public int getId() {
        return 0;
    }
    
    @Override
    public void setId(final int id) {
    }
    
    @Inject(method = { "loadPose" }, at = { @At("TAIL") })
    public void labyMod$setDefaultValues(final gfg partPose, final CallbackInfo callbackInfo) {
        this.labyMod$transformation.setTranslation(partPose.a(), partPose.b(), partPose.c());
        this.labyMod$transformation.setRotation(partPose.d(), partPose.e(), partPose.f());
        this.labyMod$transformation.setScale(partPose.g(), partPose.h(), partPose.i());
    }
    
    @Override
    public Transformation getModelPartTransform() {
        return this.labyMod$transformation;
    }
    
    @Override
    public Transformation getAnimationTransformation() {
        return this.labyMod$animationTransformation;
    }
    
    @Override
    public void addBox(final float x, final float y, final float z, final float width, final float height, final float depth, final float delta, final boolean mirror) {
        throw new UnsupportedOperationException("Boxes not supported");
    }
    
    @Override
    public void addBox(final ModelBox box) {
        throw new UnsupportedOperationException("Boxes not supported");
    }
    
    @Override
    public List<ModelBox> getBoxes() {
        if (this.labyMod$boxes.isEmpty()) {
            for (final gfe.a cube : this.m) {
                this.labyMod$boxes.add(((ModelPartCubeAccessor)cube).getModelBox());
            }
        }
        return this.labyMod$boxes;
    }
    
    @Override
    public boolean isVisible() {
        return this.k;
    }
    
    @Override
    public void setVisible(final boolean visible) {
        this.k = visible;
    }
    
    @Nullable
    @Override
    public Material getMaterial() {
        return this.labyMod$material;
    }
    
    @Override
    public void setMaterial(@Nullable final Material material) {
        this.labyMod$material = material;
    }
    
    @Override
    public void copyFrom(final ModelPart modelPart) {
        this.labyMod$transformation.set(modelPart.getModelPartTransform());
        this.labyMod$animationTransformation.set(modelPart.getAnimationTransformation());
    }
    
    @Override
    public int getTextureWidth() {
        throw new UnsupportedOperationException("Texture size not supported");
    }
    
    @Override
    public int getTextureHeight() {
        throw new UnsupportedOperationException("Texture size not supported");
    }
    
    @Override
    public void setTextureSize(final int textureWidth, final int textureHeight) {
        throw new UnsupportedOperationException("Texture size not supported");
    }
    
    @Override
    public int getTextureOffsetX() {
        throw new UnsupportedOperationException("Texture offset not supported");
    }
    
    @Override
    public int getTextureOffsetY() {
        throw new UnsupportedOperationException("Texture offset not supported");
    }
    
    @Override
    public void setTextureOffset(final int textureOffsetX, final int textureOffsetY) {
        throw new UnsupportedOperationException("Texture offset not supported");
    }
    
    @Override
    public void addChild(final String name, final ModelPart child) {
        throw new UnsupportedOperationException("Adding children not supported");
    }
    
    @Override
    public ModelPart getChild(final String name) {
        return (ModelPart)this.n.get(name);
    }
    
    @Override
    public Map<String, ModelPart> getChildren() {
        return (Map)this.n;
    }
    
    @Override
    public void reset() {
        this.labyMod$animationTransformation.reset();
    }
}
