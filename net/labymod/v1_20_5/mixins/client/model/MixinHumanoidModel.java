// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.model;

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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.model.entity.HumanoidModel;

@Mixin({ fuo.class })
public class MixinHumanoidModel<T extends btq> implements HumanoidModel
{
    @Shadow
    @Final
    public fxb k;
    @Shadow
    @Final
    public fxb l;
    @Shadow
    @Final
    public fxb m;
    @Shadow
    @Final
    public fxb q;
    @Shadow
    @Final
    public fxb p;
    @Shadow
    @Final
    public fxb o;
    @Shadow
    @Final
    public fxb n;
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
    
    @Inject(method = { "<init>(Lnet/minecraft/client/model/geom/ModelPart;Ljava/util/function/Function;)V" }, at = { @At("RETURN") })
    public void labyMod$addParts(final fxb modelPart, final Function<alf, gdx> function, final CallbackInfo callbackInfo) {
        final Map<String, ModelPart> children = ((ModelPart)modelPart).getChildren();
        this.labyMod$children = new HashMap<String, ModelPart>(children);
        this.labyMod$parts = new HashMap<String, ModelPart>(children);
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
        return (ModelPart)this.k;
    }
    
    @Override
    public ModelPart getHat() {
        return (ModelPart)this.l;
    }
    
    @Override
    public ModelPart getBody() {
        return (ModelPart)this.m;
    }
    
    @Override
    public ModelPart getLeftLeg() {
        return (ModelPart)this.q;
    }
    
    @Override
    public ModelPart getRightLeg() {
        return (ModelPart)this.p;
    }
    
    @Override
    public ModelPart getLeftArm() {
        return (ModelPart)this.o;
    }
    
    @Override
    public ModelPart getRightArm() {
        return (ModelPart)this.n;
    }
}
