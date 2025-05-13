// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.model;

import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.HashMap;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.function.Function;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;
import net.labymod.api.client.gfx.pipeline.buffer.BufferState;
import net.labymod.api.client.render.model.ModelPart;
import java.util.Map;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.model.entity.HumanoidModel;

@Mixin({ dum.class })
public class MixinHumanoidModel<T extends aqm> implements HumanoidModel
{
    @Shadow
    public dwn f;
    @Shadow
    public dwn g;
    @Shadow
    public dwn h;
    @Shadow
    public dwn l;
    @Shadow
    public dwn k;
    @Shadow
    public dwn j;
    @Shadow
    public dwn i;
    protected Map<String, ModelPart> labyMod$children;
    protected Map<String, ModelPart> labyMod$parts;
    private BufferState labyMod$bufferState;
    private RenderedBuffer labyMod$renderedBuffer;
    private Metadata labyMod$metadata;
    
    @Override
    public void setDrawCommand(@Nullable final BufferState bufferState) {
        this.labyMod$bufferState = bufferState;
        if (bufferState == null) {
            return;
        }
        this.labyMod$renderedBuffer = bufferState.uploadStaticDraw();
    }
    
    @Nullable
    @Override
    public BufferState getDrawCommand() {
        return this.labyMod$bufferState;
    }
    
    @Nullable
    @Override
    public RenderedBuffer getRenderedBuffer() {
        return this.labyMod$renderedBuffer;
    }
    
    @Override
    public void deleteBuffer() {
        if (this.labyMod$renderedBuffer == null) {
            return;
        }
        this.labyMod$renderedBuffer.dispose();
        this.labyMod$renderedBuffer = null;
    }
    
    @Inject(method = { "<init>(Ljava/util/function/Function;FFII)V" }, at = { @At("RETURN") })
    public void labyMod$addParts(final Function function, final float lvt_2_1_, final float lvt_3_1_, final int lvt_4_1_, final int lvt_5_1_, final CallbackInfo ci) {
        this.labyMod$children = new HashMap<String, ModelPart>();
        this.labyMod$parts = new HashMap<String, ModelPart>();
        this.labyMod$addInternalChild("head", this.getHead());
        this.labyMod$addInternalChild("hat", this.getHat());
        this.labyMod$addInternalChild("body", this.getBody());
        this.labyMod$addInternalChild("left_leg", this.getLeftLeg());
        this.labyMod$addInternalChild("right_leg", this.getRightLeg());
        this.labyMod$addInternalChild("left_arm", this.getLeftArm());
        this.labyMod$addInternalChild("right_arm", this.getRightArm());
    }
    
    protected void labyMod$addInternalChild(final String name, final ModelPart child) {
        this.labyMod$children.put(name, child);
        this.labyMod$parts.put(name, child);
    }
    
    @Override
    public void addPart(final String name, final ModelPart part) {
        throw new UnsupportedOperationException("ADding part not supported");
    }
    
    @Override
    public boolean isInvalidPart(final String name) {
        return false;
    }
    
    @Override
    public ModelPart getPart(final String name) {
        return this.labyMod$parts.get(name);
    }
    
    @Override
    public Map<String, ModelPart> getParts() {
        return this.labyMod$parts;
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
        throw new UnsupportedOperationException("Texture not supported");
    }
    
    @Override
    public void setTextureLocation(final ResourceLocation textureLocation) {
        throw new UnsupportedOperationException("Texture not supported");
    }
    
    @Override
    public Metadata metadata() {
        if (this.labyMod$metadata == null) {
            this.labyMod$metadata = Metadata.create();
        }
        return this.labyMod$metadata;
    }
    
    @Override
    public void addChild(final String name, final ModelPart child) {
        throw new UnsupportedOperationException("Adding child not supported");
    }
    
    @Override
    public ModelPart getChild(final String name) {
        return this.labyMod$children.get(name);
    }
    
    @Override
    public Map<String, ModelPart> getChildren() {
        return this.labyMod$children;
    }
    
    @Override
    public ModelPart getHead() {
        return (ModelPart)this.f;
    }
    
    @Override
    public ModelPart getHat() {
        return (ModelPart)this.g;
    }
    
    @Override
    public ModelPart getBody() {
        return (ModelPart)this.h;
    }
    
    @Override
    public ModelPart getLeftLeg() {
        return (ModelPart)this.l;
    }
    
    @Override
    public ModelPart getRightLeg() {
        return (ModelPart)this.k;
    }
    
    @Override
    public ModelPart getLeftArm() {
        return (ModelPart)this.j;
    }
    
    @Override
    public ModelPart getRightArm() {
        return (ModelPart)this.i;
    }
}
