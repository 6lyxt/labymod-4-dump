// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.model;

import java.util.function.Consumer;
import java.util.Iterator;
import java.util.Objects;
import net.labymod.api.Textures;
import java.util.ArrayList;
import java.util.HashMap;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.pipeline.buffer.BufferState;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.List;
import net.labymod.api.client.render.model.ModelPart;
import java.util.Map;
import net.labymod.api.client.render.model.Model;

public class DefaultModel implements Model
{
    private final Map<String, ModelPart> children;
    private final Map<String, ModelPart> parts;
    private final List<String> invalidParts;
    private ModelPart[] bakedChildren;
    private String[] bakedInvalidParts;
    private ResourceLocation textureLocation;
    private Metadata metadata;
    @Nullable
    private BufferState bufferState;
    @Nullable
    private RenderedBuffer renderedBuffer;
    
    public DefaultModel() {
        this(new HashMap<String, ModelPart>());
    }
    
    public DefaultModel(final Map<String, ModelPart> children) {
        this.parts = new HashMap<String, ModelPart>();
        this.invalidParts = new ArrayList<String>();
        this.bakedInvalidParts = new String[0];
        this.textureLocation = Textures.EMPTY;
        this.children = children;
        this.bakedChildren = children.values().toArray(new ModelPart[0]);
    }
    
    @Override
    public void addPart(final String name, final ModelPart part) {
        this.parts.put(name, part);
        part.setDebugName(name);
    }
    
    @Override
    public boolean isInvalidPart(final String name) {
        for (final String invalidPart : this.bakedInvalidParts) {
            if (Objects.equals(name, invalidPart)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public ModelPart getPart(final String name) {
        final ModelPart part = this.parts.get(name);
        if (part == null) {
            this.invalidParts.add(name);
            this.bakedInvalidParts = this.invalidParts.toArray(new String[0]);
        }
        return part;
    }
    
    @Override
    public Map<String, ModelPart> getParts() {
        return this.parts;
    }
    
    @Override
    public void addChild(final String name, final ModelPart child) {
        this.addPart(name, child);
        this.children.put(name, child);
        this.bakedChildren = this.children.values().toArray(new ModelPart[0]);
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
    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }
    
    @Override
    public void setTextureLocation(final ResourceLocation textureLocation) {
        if (Objects.equals(textureLocation, this.textureLocation)) {
            return;
        }
        this.textureLocation = textureLocation;
    }
    
    @Override
    public Metadata metadata() {
        return (this.metadata == null) ? (this.metadata = Metadata.create()) : this.metadata;
    }
    
    @Override
    public void setDrawCommand(@Nullable final BufferState bufferState) {
        this.bufferState = bufferState;
        if (bufferState == null) {
            return;
        }
        this.renderedBuffer = bufferState.uploadStaticDraw();
    }
    
    @Nullable
    @Override
    public BufferState getDrawCommand() {
        return this.bufferState;
    }
    
    @Nullable
    @Override
    public RenderedBuffer getRenderedBuffer() {
        return this.renderedBuffer;
    }
    
    @Override
    public void deleteBuffer() {
        if (this.renderedBuffer == null) {
            return;
        }
        this.renderedBuffer.dispose();
        this.renderedBuffer = null;
    }
    
    @Override
    public void printModelTree() {
        for (final Map.Entry<String, ModelPart> entry : this.children.entrySet()) {
            final String name = entry.getKey();
            final ModelPart modelPart = entry.getValue();
            this.printModelPart(name, modelPart, 0);
            System.out.println();
        }
    }
    
    @Override
    public Model copy() {
        final Model newModel = new DefaultModel();
        this.forChildren(child -> {
            final ModelPart copiedChild = child.copy();
            this.addChild(newModel, copiedChild);
            newModel.addChild(child.getDebugName(), copiedChild);
            return;
        });
        newModel.setTextureLocation(this.getTextureLocation());
        return newModel;
    }
    
    @Override
    public void forChildren(final Consumer<ModelPart> consumer) {
        Objects.requireNonNull(consumer);
        for (final ModelPart bakedChild : this.bakedChildren) {
            consumer.accept(bakedChild);
        }
    }
    
    private void addChild(final Model model, final ModelPart child) {
        model.addPart(child.getDebugName(), child);
        for (final ModelPart innerChild : child.getChildren().values()) {
            this.addChild(model, innerChild);
        }
    }
    
    private void printModelPart(final String name, final ModelPart part, int depth) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < depth; ++i) {
            builder.append(" ");
        }
        System.out.println(String.valueOf(builder) + "-" + name + "(" + part.getId());
        ++depth;
        for (final Map.Entry<String, ModelPart> entry : part.getChildren().entrySet()) {
            this.printModelPart(entry.getKey(), entry.getValue(), depth);
        }
    }
}
