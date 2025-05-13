// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.effect.effects.layer;

import net.labymod.core.main.user.shop.item.model.TextureDetails;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.main.user.shop.item.AbstractItem;
import net.labymod.api.util.gson.UUIDTypeAdapter;
import net.labymod.api.client.render.model.ModelPart;
import java.util.UUID;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;

public class LayerGeometryEffect extends GeometryEffect
{
    private UUID uniqueId;
    private boolean negate;
    private boolean slim;
    private boolean rightSide;
    
    public LayerGeometryEffect(final String effectArgument, final ModelPart model) {
        super(effectArgument, model, Type.BUFFER_CREATION, 1);
        this.uniqueId = null;
    }
    
    @Override
    protected boolean processParameters() {
        final String id = this.getParameter(0);
        if (id.equals("slim")) {
            this.slim = true;
        }
        else if (id.equals("right")) {
            this.rightSide = true;
        }
        else {
            try {
                this.uniqueId = UUIDTypeAdapter.fromString(id);
            }
            catch (final Exception ignored) {
                return false;
            }
        }
        this.negate = (this.hasParameter(1) && this.getParameter(1).equals("negate"));
        return true;
    }
    
    @Override
    public void apply(final AbstractItem item, final Player player, final PlayerModel playerModel, final ItemMetadata itemMetadata, final ItemEffect.EffectData effectData) {
        if (this.uniqueId != null) {
            final TextureDetails textureDetails = itemMetadata.getTextureDetails();
            this.setModelPartVisible(textureDetails != null && textureDetails.getUuid() != null && this.negate != textureDetails.getUuid().equals(this.uniqueId));
        }
        if (this.slim) {
            this.setModelPartVisible(this.negate != effectData.isSlim());
        }
        if (this.rightSide) {
            this.setModelPartVisible(this.negate != effectData.isRightSide());
        }
    }
    
    public UUID uniqueId() {
        return this.uniqueId;
    }
    
    public void setModelPartVisible(final boolean condition) {
        this.modelPart.setVisible(condition);
    }
}
