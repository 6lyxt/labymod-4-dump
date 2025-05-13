// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.model;

import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.client.gfx.pipeline.material.MutableMaterial;
import java.util.Iterator;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gfx.shader.uniform.UniformBone;
import net.labymod.core.client.render.model.box.DefaultModelBox;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.function.Function;
import net.labymod.api.client.gfx.pipeline.material.SimpleMaterial;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.pipeline.material.Material;
import net.labymod.api.util.math.Transformation;
import java.util.Map;
import net.labymod.api.client.render.model.box.ModelBox;
import java.util.List;
import net.labymod.api.client.render.model.ModelPart;

public class DefaultModelPart implements ModelPart
{
    private static final String GLOW_PREFIX = "glow_";
    private final List<ModelBox> boxes;
    private final Map<String, ModelPart> children;
    private final List<ModelPart> modelParts;
    private final Transformation transformation;
    private final Transformation animationTransformation;
    private ModelPart[] bakedChildren;
    private String debugName;
    public boolean visible;
    @Nullable
    private Material material;
    private int textureOffsetX;
    private int textureOffsetY;
    private int textureWidth;
    private int textureHeight;
    private int id;
    @Nullable
    private ModelPart parent;
    
    public DefaultModelPart() {
        this.bakedChildren = new ModelPart[0];
        this.material = Material.builder().build((Function<Material.Builder, Material>)SimpleMaterial::new);
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.id = -1;
        this.boxes = new ArrayList<ModelBox>();
        this.children = new HashMap<String, ModelPart>();
        this.modelParts = new ArrayList<ModelPart>();
        this.visible = true;
        (this.transformation = new Transformation()).setScale(1.0f);
        (this.animationTransformation = new Transformation()).set(this.transformation);
    }
    
    @Override
    public void addChild(final String name, final ModelPart child) {
        child.setParent(this);
        this.children.put(name, child);
        this.bakedChildren = this.children.values().toArray(new ModelPart[0]);
        this.modelParts.add(child);
    }
    
    @Override
    public ModelPart getChild(final String name) {
        return this.children.get(name);
    }
    
    @Override
    public Map<String, ModelPart> getChildren() {
        return this.children;
    }
    
    @Override
    public void setId(final int id) {
        this.id = id;
    }
    
    @Override
    public int getId() {
        return (this.id == -1) ? ((this.parent == null) ? 0 : this.parent.getId()) : this.id;
    }
    
    @Override
    public void addBox(final float x, final float y, final float z, final float width, final float height, final float depth, final float delta, final boolean mirror) {
        this.addBox(new DefaultModelBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, delta, delta, delta, mirror, (float)this.textureWidth, (float)this.textureHeight));
    }
    
    @Override
    public ModelBox createAndAddBox(final float x, final float y, final float z, final float width, final float height, final float depth, final float delta, final boolean mirror) {
        final ModelBox newBox = new DefaultModelBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, delta, delta, delta, mirror, (float)this.textureWidth, (float)this.textureHeight);
        this.addBox(newBox);
        return newBox;
    }
    
    @Override
    public void addBox(final ModelBox box) {
        this.boxes.add(box);
    }
    
    @Override
    public List<ModelBox> getBoxes() {
        return this.boxes;
    }
    
    @Override
    public boolean isVisible() {
        return this.visible;
    }
    
    @Override
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    @Nullable
    @Override
    public Material getMaterial() {
        return (this.material == null) ? ((this.parent == null) ? null : this.parent.getMaterial()) : this.material;
    }
    
    @Override
    public void setMaterial(@Nullable final Material material) {
        this.material = material;
    }
    
    @Override
    public Transformation getModelPartTransform() {
        return this.transformation;
    }
    
    @Override
    public Transformation getAnimationTransformation() {
        return this.animationTransformation;
    }
    
    @Override
    public void copyFrom(final ModelPart modelPart) {
        this.transformation.set(modelPart.getModelPartTransform());
        this.animationTransformation.set(modelPart.getAnimationTransformation());
    }
    
    @Override
    public int getTextureWidth() {
        return this.textureWidth;
    }
    
    @Override
    public int getTextureHeight() {
        return this.textureHeight;
    }
    
    @Override
    public void setTextureSize(final int textureWidth, final int textureHeight) {
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }
    
    @Override
    public int getTextureOffsetX() {
        return this.textureOffsetX;
    }
    
    @Override
    public int getTextureOffsetY() {
        return this.textureOffsetY;
    }
    
    @Override
    public void setTextureOffset(final int textureOffsetX, final int textureOffsetY) {
        this.textureOffsetX = textureOffsetX;
        this.textureOffsetY = textureOffsetY;
    }
    
    @Nullable
    @Override
    public ModelPart getParent() {
        return this.parent;
    }
    
    @Override
    public void setParent(@Nullable final ModelPart parent) {
        this.parent = parent;
    }
    
    @Override
    public void transformMatrix(final UniformBone uniform, final Stack transformMatrix) {
        transformMatrix.push();
        this.transformation.transform(transformMatrix, this.animationTransformation);
        final FloatMatrix4 pivotPointMatrix = transformMatrix.getProvider().getPosition();
        if (this.id >= 0) {
            uniform.setBoneMatrix(this.id, pivotPointMatrix);
        }
        for (final ModelPart child : this.bakedChildren) {
            child.transformMatrix(uniform, transformMatrix);
        }
        transformMatrix.pop();
    }
    
    @Override
    public ModelPart copy() {
        final DefaultModelPart newModelPart = new DefaultModelPart();
        newModelPart.setDebugName(this.getDebugName());
        newModelPart.setId(this.getId());
        newModelPart.setParent(this.getParent());
        newModelPart.getModelPartTransform().set(this.getModelPartTransform());
        newModelPart.getAnimationTransformation().set(this.getAnimationTransformation());
        newModelPart.setVisible(this.isVisible());
        newModelPart.setMaterial(this.getMaterial());
        newModelPart.setTextureSize(this.getTextureWidth(), this.getTextureHeight());
        newModelPart.setTextureOffset(this.getTextureOffsetX(), this.getTextureOffsetY());
        for (final ModelPart bakedChild : this.bakedChildren) {
            newModelPart.addChild(bakedChild.getDebugName(), bakedChild.copy());
        }
        for (final ModelBox box : this.boxes) {
            newModelPart.addBox(box);
        }
        return newModelPart;
    }
    
    @Override
    public void setDebugName(final String name) {
        this.debugName = name;
        if (this.material instanceof final MutableMaterial mutableMaterial) {
            mutableMaterial.setGlowing(this.debugName.startsWith("glow_"));
        }
        if (this.animationTransformation != null) {
            this.animationTransformation.setDebugName(name);
        }
        if (this.transformation != null) {
            this.transformation.setDebugName(name);
        }
    }
    
    @Override
    public String getDebugName() {
        return this.debugName;
    }
    
    @Override
    public void forChildren(final Consumer<ModelPart> consumer) {
        Objects.requireNonNull(consumer);
        for (final ModelPart bakedChild : this.bakedChildren) {
            consumer.accept(bakedChild);
        }
    }
}
