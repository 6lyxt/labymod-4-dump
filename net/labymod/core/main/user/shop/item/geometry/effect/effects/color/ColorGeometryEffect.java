// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.effect.effects.color;

import net.labymod.api.client.gfx.pipeline.material.Material;
import net.labymod.api.client.gfx.pipeline.material.MaterialColor;
import net.labymod.api.client.gfx.pipeline.material.MutableMaterial;
import net.labymod.api.util.Color;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.main.user.shop.item.AbstractItem;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;

public class ColorGeometryEffect extends GeometryEffect
{
    private int index;
    private boolean rainbow;
    private long cycleDuration;
    
    public ColorGeometryEffect(final String effectArgument, final ModelPart model) {
        super(effectArgument, model, Type.BUFFER_CREATION, 1);
        this.index = 0;
    }
    
    @Override
    protected boolean processParameters() {
        try {
            final String parameter = this.getParameter(0);
            if (parameter.equalsIgnoreCase("rainbow")) {
                this.rainbow = true;
                this.cycleDuration = Long.parseLong(this.getParameter(1));
            }
            else {
                this.index = Integer.parseInt(parameter);
            }
        }
        catch (final NumberFormatException ignored) {
            return false;
        }
        return true;
    }
    
    @Override
    public void apply(final AbstractItem item, final Player player, final PlayerModel playerModel, final ItemMetadata itemMetadata, final ItemEffect.EffectData effectData) {
        final int colorIndex = this.index;
        final Color[] itemColors = itemMetadata.getColors();
        if (this.rainbow) {
            this.setColor(Color.WHITE);
        }
        else if (itemColors.length > colorIndex) {
            this.setColor(itemColors[colorIndex]);
        }
    }
    
    private void setColor(final Color color) {
        final Material material = this.modelPart.getMaterial();
        if (material instanceof final MutableMaterial mutableMaterial) {
            final MaterialColor materialColor = new MaterialColor();
            materialColor.setColor(color);
            materialColor.setRainbow(this.rainbow);
            materialColor.setCycle(this.cycleDuration);
            mutableMaterial.setColor(materialColor);
        }
    }
    
    public int getIndex() {
        return this.index;
    }
}
