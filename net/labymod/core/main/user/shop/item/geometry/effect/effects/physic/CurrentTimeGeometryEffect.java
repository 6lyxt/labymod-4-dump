// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.effect.effects.physic;

import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.MathHelper;
import java.time.temporal.Temporal;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.main.user.shop.item.AbstractItem;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;

public class CurrentTimeGeometryEffect extends GeometryEffect
{
    private PhysicMapping mapping;
    private boolean mirror;
    private int cycle;
    private int offset;
    private int interval;
    
    public CurrentTimeGeometryEffect(final String effectArgument, final ModelPart modelPart) {
        super(effectArgument, modelPart, Type.PHYSIC, 5);
        this.mapping = PhysicMapping.X;
        this.cycle = 1;
        this.interval = 1;
    }
    
    @Override
    protected boolean processParameters() {
        final String mappingString = this.getParameter(0, 1);
        final String mirrorString = this.getParameter(1, 1);
        this.mapping = PhysicMapping.get(mappingString.charAt(0));
        this.mirror = (mirrorString.charAt(0) == 'n');
        this.cycle = Math.max(1, Integer.parseInt(this.getParameter(2)));
        this.offset = Integer.parseInt(this.getParameter(3));
        this.interval = Math.max(1, Integer.parseInt(this.getParameter(4)));
        return true;
    }
    
    @Override
    public void apply(final AbstractItem item, final Player player, final PlayerModel playerModel, final ItemMetadata itemMetadata, final ItemEffect.EffectData effectData) {
        final ZonedDateTime nowZoned = ZonedDateTime.now();
        final Instant midnight = nowZoned.toLocalDate().atStartOfDay(nowZoned.getZone()).toInstant();
        final Duration duration = Duration.between(midnight, Instant.now());
        final long millisToday = duration.toMillis();
        final long time = millisToday % (1000L * this.cycle);
        final float seconds = (int)(time / this.interval) * this.interval / 1000.0f;
        final float progress = seconds % this.cycle + this.offset;
        final float rotation = MathHelper.toRadiansFloat(360.0f / this.cycle * progress * (this.mirror ? -1 : 1));
        final FloatVector3 rotationVector = this.modelPart.getAnimationTransformation().getRotation();
        switch (this.mapping) {
            case X: {
                rotationVector.setX(rotation);
                break;
            }
            case Y: {
                rotationVector.setY(rotation);
                break;
            }
            case Z: {
                rotationVector.setZ(rotation);
                break;
            }
        }
    }
}
