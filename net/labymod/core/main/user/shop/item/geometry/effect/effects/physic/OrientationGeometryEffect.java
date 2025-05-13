// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.effect.effects.physic;

import net.labymod.api.util.math.Transformation;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.Quaternion;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.Laby;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.main.user.shop.item.AbstractItem;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;

public class OrientationGeometryEffect extends GeometryEffect
{
    private OrientationTarget target;
    
    public OrientationGeometryEffect(final String effectArgument, final ModelPart model) {
        super(effectArgument, model, Type.PHYSIC, 1);
    }
    
    @Override
    protected boolean processParameters() {
        this.target = OrientationTarget.getById(this.getParameter(0));
        return true;
    }
    
    @Override
    public void apply(final AbstractItem item, final Player player, final PlayerModel playerModel, final ItemMetadata metadata, final ItemEffect.EffectData data) {
        final MinecraftCamera camera = Laby.labyAPI().minecraft().getCamera();
        if (camera == null) {
            return;
        }
        final Quaternion cameraRotation = camera.rotation();
        float x = 0.0f;
        float y = 0.0f;
        float z = 0.0f;
        if (this.target == OrientationTarget.CAMERA) {
            x = cameraRotation.getPitch();
            y = cameraRotation.getYaw() - data.getRenderYawOffset();
        }
        if (this.target == OrientationTarget.NORTH) {
            y = -data.getRenderYawOffset();
        }
        for (ModelPart target = this.modelPart.getParent(); target != null; target = target.getParent()) {
            final FloatVector3 rotation = target.getModelPartTransform().getRotation();
            x -= MathHelper.toDegreesFloat(rotation.getX());
            y -= MathHelper.toDegreesFloat(rotation.getY());
            z -= MathHelper.toDegreesFloat(rotation.getZ());
        }
        x += 180.0f;
        final Transformation transformation = this.modelPart.getAnimationTransformation();
        transformation.setRotation(MathHelper.toRadiansFloat(x), MathHelper.toRadiansFloat(y), MathHelper.toRadiansFloat(z));
    }
}
