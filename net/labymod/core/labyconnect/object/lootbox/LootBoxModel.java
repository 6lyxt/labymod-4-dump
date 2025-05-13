// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.object.lootbox;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Objects;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import java.util.List;
import net.labymod.api.client.render.model.Model;

public final class LootBoxModel
{
    private final Model model;
    private final List<GeometryEffect> effects;
    private ResourceLocation textureLocation;
    
    public LootBoxModel(final Model model, final List<GeometryEffect> effects) {
        this.model = model;
        this.effects = effects;
    }
    
    public void setTextureLocation(final ResourceLocation textureLocation) {
        this.setTextureLocation(this.model, textureLocation);
    }
    
    private void setTextureLocation(final Model model, final ResourceLocation textureLocation) {
        if (model == null) {
            this.textureLocation = textureLocation;
            return;
        }
        model.setTextureLocation(textureLocation);
        this.textureLocation = textureLocation;
    }
    
    public Model model() {
        return this.model;
    }
    
    public ResourceLocation textureLocation() {
        return this.textureLocation;
    }
    
    public List<GeometryEffect> effects() {
        return this.effects;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        final LootBoxModel that = (LootBoxModel)obj;
        return Objects.equals(this.model, that.model) && Objects.equals(this.textureLocation, that.textureLocation) && Objects.equals(this.effects, that.effects);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.model, this.textureLocation, this.effects);
    }
    
    @Override
    public String toString() {
        return "LootBoxModel[model=" + String.valueOf(this.model) + ", textureLocation=" + String.valueOf(this.textureLocation) + ", effects=" + String.valueOf(this.effects);
    }
    
    public LootBoxModel copy() {
        final LootBoxModel lootBoxModel = new LootBoxModel(this.model.copy(), new ArrayList<GeometryEffect>(this.effects));
        lootBoxModel.setTextureLocation(this.textureLocation);
        return lootBoxModel;
    }
}
