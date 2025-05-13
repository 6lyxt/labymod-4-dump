// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.model;

import java.util.Iterator;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.HashMap;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.client.render.model.ModelPart;
import java.util.Map;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.model.entity.HumanoidModel;

@Mixin({ bpx.class })
public class MixinModelBiped implements HumanoidModel
{
    @Shadow
    public brs e;
    @Shadow
    public brs f;
    @Shadow
    public brs g;
    @Shadow
    public brs k;
    @Shadow
    public brs j;
    @Shadow
    public brs i;
    @Shadow
    public brs h;
    protected Map<String, ModelPart> labyMod$children;
    protected Map<String, ModelPart> labyMod$parts;
    private Metadata labyMod$metadata;
    
    @Inject(method = { "<init>(FFII)V" }, at = { @At("RETURN") })
    public void labyMod$addParts(final float p_i354_1_, final float p_i354_2_, final int textureWidth, final int textureHeight, final CallbackInfo callbackInfo) {
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
        this.labyMod$parts.put(name, part);
        part.setDebugName(name);
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
        return (ModelPart)this.e;
    }
    
    @Override
    public ModelPart getHat() {
        return (ModelPart)this.f;
    }
    
    @Override
    public ModelPart getBody() {
        return (ModelPart)this.g;
    }
    
    @Override
    public ModelPart getLeftLeg() {
        return (ModelPart)this.k;
    }
    
    @Override
    public ModelPart getRightLeg() {
        return (ModelPart)this.j;
    }
    
    @Override
    public ModelPart getLeftArm() {
        return (ModelPart)this.i;
    }
    
    @Override
    public ModelPart getRightArm() {
        return (ModelPart)this.h;
    }
    
    @Override
    public void reset() {
        for (final ModelPart modelPart : this.labyMod$parts.values()) {
            modelPart.reset();
        }
    }
}
