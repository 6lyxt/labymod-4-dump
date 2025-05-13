// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.effect.effects.physic;

import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.main.user.shop.item.AbstractItem;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;

public class HeadPhysicGeometryEffect extends GeometryEffect
{
    private PhysicMapping physicMapping;
    private float strength;
    private boolean mirror;
    
    public HeadPhysicGeometryEffect(final String effectArgument, final ModelPart model) {
        super(effectArgument, model, Type.PHYSIC, 5);
        this.physicMapping = PhysicMapping.X;
        this.strength = 1.0f;
        this.mirror = false;
    }
    
    @Override
    protected boolean processParameters() {
        try {
            this.strength = Integer.parseInt(this.getParameter(0)) / 100.0f;
        }
        catch (final NumberFormatException ignored) {
            return false;
        }
        final String mappingParameter = this.getParameter(1, 1);
        if (mappingParameter == null || mappingParameter.isEmpty()) {
            return false;
        }
        final String mirrorParameter = this.getParameter(2, 1);
        if (mirrorParameter == null || mirrorParameter.isEmpty()) {
            return false;
        }
        this.physicMapping = PhysicMapping.get(mappingParameter.charAt(0));
        this.mirror = (mirrorParameter.charAt(0) == 'n');
        return true;
    }
    
    @Override
    public void apply(final AbstractItem item, final Player player, final PlayerModel playerModel, final ItemMetadata itemMetadata, final ItemEffect.EffectData effectData) {
        final float pitch = effectData.getPitch() - MathHelper.toDegreesFloat(playerModel.getBody().getAnimationTransformation().getRotation().getX());
        final float rotation = -MathHelper.toRadiansFloat(pitch * (this.mirror ? -1 : 1) * this.strength);
        final FloatVector3 modelPartRotation = this.modelPart.getAnimationTransformation().getRotation();
        switch (this.physicMapping) {
            case X: {
                modelPartRotation.setX(rotation);
                break;
            }
            case Y: {
                modelPartRotation.setY(rotation);
                break;
            }
            case Z: {
                modelPartRotation.setZ(rotation);
                break;
            }
        }
    }
    
    public PhysicMapping physicMapping() {
        return this.physicMapping;
    }
    
    public double strength() {
        return this.strength;
    }
    
    public boolean mirror() {
        return this.mirror;
    }
}
