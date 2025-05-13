// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity.player;

import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.Laby;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.entity.player.PlayerModel;

public class DummyPlayerModel implements PlayerModel
{
    private final Model model;
    
    public DummyPlayerModel(final Model model) {
        this.model = model;
    }
    
    @Override
    public void addPart(final String name, final ModelPart part) {
    }
    
    @Override
    public boolean isInvalidPart(final String name) {
        return this.model.isInvalidPart(name);
    }
    
    @Override
    public ModelPart getPart(final String name) {
        return this.model.getPart(name);
    }
    
    @Override
    public Map<String, ModelPart> getParts() {
        return this.model.getParts();
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
        return this.model.getTextureLocation();
    }
    
    @Override
    public void setTextureLocation(final ResourceLocation textureLocation) {
        this.model.setTextureLocation(textureLocation);
    }
    
    @Override
    public Metadata metadata() {
        return this.model.metadata();
    }
    
    @Override
    public void addChild(final String name, final ModelPart child) {
        this.model.addChild(name, child);
    }
    
    @Override
    public ModelPart getChild(final String name) {
        return this.model.getChild(name);
    }
    
    @Override
    public Map<String, ModelPart> getChildren() {
        return this.model.getChildren();
    }
    
    @Override
    public ModelPart getHead() {
        return this.model.getPart("head");
    }
    
    @Override
    public ModelPart getHat() {
        return this.model.getPart("hat");
    }
    
    @Override
    public ModelPart getBody() {
        return this.model.getPart("chest");
    }
    
    @Override
    public ModelPart getLeftLeg() {
        return this.model.getPart("left_leg");
    }
    
    @Override
    public ModelPart getRightLeg() {
        return this.model.getPart("right_leg");
    }
    
    @Override
    public ModelPart getLeftArm() {
        return this.model.getPart("left_arm");
    }
    
    @Override
    public ModelPart getRightArm() {
        return this.model.getPart("right_arm");
    }
    
    @Override
    public void copyToSecondLayer() {
    }
    
    @Override
    public ModelPart getJacket() {
        return this.model.getPart("jacket");
    }
    
    @Override
    public ModelPart getCloak() {
        return this.model.getPart("cloak");
    }
    
    @Override
    public ModelPart getLeftPants() {
        return this.model.getPart("left_pants");
    }
    
    @Override
    public ModelPart getRightPants() {
        return this.model.getPart("right_pants");
    }
    
    @Override
    public ModelPart getLeftSleeve() {
        return this.model.getPart("left_sleeve");
    }
    
    @Override
    public ModelPart getRightSleeve() {
        return this.model.getPart("right_sleeve");
    }
    
    @Override
    public boolean isSlim() {
        final ResourceLocation location = this.model.getTextureLocation();
        return Laby.references().mojangTextureService().getVariant(location) == MinecraftServices.SkinVariant.SLIM;
    }
}
