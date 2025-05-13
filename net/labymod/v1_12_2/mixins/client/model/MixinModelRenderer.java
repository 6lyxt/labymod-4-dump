// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.model.box.ModelBox;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.util.math.vector.FloatVector3;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.v1_12_2.client.render.model.ModelRendererAnimationTransformation;
import net.labymod.api.client.gfx.pipeline.material.Material;
import java.util.List;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.util.math.Transformation;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.model.ModelPart;

@Mixin({ brs.class })
public abstract class MixinModelRenderer implements ModelPart
{
    private final Transformation labyMod$transformation;
    private final Transformation labyMod$animationTransformation;
    @Shadow
    public boolean j;
    @Shadow
    public float a;
    @Shadow
    public float b;
    @Shadow
    public List<brq> l;
    @Shadow
    public List<brs> m;
    @Shadow
    private int r;
    @Shadow
    private int s;
    private boolean labyMod$defaultsSet;
    private Material labyMod$material;
    
    public MixinModelRenderer() {
        this.labyMod$transformation = new Transformation();
        this.labyMod$animationTransformation = new ModelRendererAnimationTransformation((brs)this, this.labyMod$transformation);
    }
    
    @Shadow
    public abstract void a(final float p0, final float p1, final float p2, final int p3, final int p4, final int p5, final float p6);
    
    @Insert(method = { "setRotationPoint(FFF)V" }, at = @At("HEAD"))
    public void labyMod$setDefaultValues(final float x, final float y, final float z, final InsertInfo callbackInfo) {
        if (!this.labyMod$defaultsSet) {
            this.labyMod$transformation.setTranslation(x, y, z);
            this.labyMod$transformation.setRotation(0.0f, 0.0f, 0.0f);
            this.labyMod$transformation.setScale(1.0f);
            this.labyMod$defaultsSet = true;
        }
    }
    
    @Inject(method = { "render(F)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;callList(I)V", shift = At.Shift.BEFORE) })
    public void labyMod$applyScale(final float modelScale, final CallbackInfo callbackInfo) {
        final FloatVector3 currentScale = this.labyMod$animationTransformation.getScale();
        if (currentScale.getX() != 1.0f || currentScale.getY() != 1.0f || currentScale.getZ() != 1.0f) {
            bus.b(currentScale.getX(), currentScale.getY(), currentScale.getZ());
        }
    }
    
    @Override
    public void addBox(final float x, final float y, final float z, final float width, final float height, final float depth, final float delta, final boolean mirror) {
        this.l.add(new brq((brs)this, this.r, this.s, x, y, z, (int)width, (int)height, (int)delta, delta, mirror));
    }
    
    @Override
    public void addBox(final ModelBox box) {
        throw new UnsupportedOperationException("Boxes not supported");
    }
    
    @Override
    public List<ModelBox> getBoxes() {
        throw new UnsupportedOperationException("Boxes not supported");
    }
    
    @Override
    public boolean isVisible() {
        return this.j;
    }
    
    @Override
    public void setVisible(final boolean visible) {
        this.j = visible;
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
    public Transformation getModelPartTransform() {
        return this.labyMod$transformation;
    }
    
    @Override
    public Transformation getAnimationTransformation() {
        return this.labyMod$animationTransformation;
    }
    
    @Override
    public void copyFrom(final ModelPart modelPart) {
        this.labyMod$transformation.set(modelPart.getModelPartTransform());
        this.labyMod$animationTransformation.set(modelPart.getAnimationTransformation());
    }
    
    @Override
    public int getTextureWidth() {
        return (int)this.a;
    }
    
    @Override
    public int getTextureHeight() {
        return (int)this.b;
    }
    
    @Override
    public void setTextureSize(final int textureWidth, final int textureHeight) {
        this.a = (float)textureWidth;
        this.b = (float)textureHeight;
    }
    
    @Override
    public int getTextureOffsetX() {
        return this.r;
    }
    
    @Override
    public int getTextureOffsetY() {
        return this.s;
    }
    
    @Override
    public void setTextureOffset(final int textureOffsetX, final int textureOffsetY) {
        this.r = textureOffsetX;
        this.s = textureOffsetY;
    }
    
    @Override
    public void addChild(final String name, final ModelPart child) {
        throw new UnsupportedOperationException("Adding children not supported");
    }
    
    @Override
    public ModelPart getChild(final String name) {
        for (final brs child : this.m) {
            if (child.n.equals(name)) {
                return (ModelPart)child;
            }
        }
        return null;
    }
    
    @Override
    public Map<String, ModelPart> getChildren() {
        final Map<String, ModelPart> children = new HashMap<String, ModelPart>();
        for (final brs child : this.m) {
            children.put(child.n, (ModelPart)child);
        }
        return children;
    }
    
    @Override
    public void reset() {
        this.labyMod$animationTransformation.reset();
    }
}
