// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.effect;

import org.jetbrains.annotations.Nullable;
import java.util.function.Predicate;
import net.labymod.core.main.debug.ErrorWrapper;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.util.collection.map.HashMultimap;
import net.labymod.api.util.collection.map.Multimap;
import net.labymod.core.main.user.shop.item.AbstractItem;

public class ItemEffect
{
    private final AbstractItem item;
    private final Multimap<GeometryEffect.Type, GeometryEffect> effects;
    private final EffectData effectData;
    
    public ItemEffect(final AbstractItem item) {
        this.item = item;
        this.effects = new HashMultimap<GeometryEffect.Type, GeometryEffect>();
        this.effectData = new EffectData();
    }
    
    public void loadEffects(final List<GeometryEffect> effects) {
        for (final GeometryEffect effect : effects) {
            final Collection<GeometryEffect> geometryEffects = this.effects.get(effect.getType());
            geometryEffects.add(effect);
        }
    }
    
    public void apply(final Player player, final PlayerModel playerModel, final PhysicData physicData, final ItemMetadata itemMetadata, final boolean rightSide, final GeometryEffect.Type type) {
        if (this.item.getModel() == null) {
            return;
        }
        if (physicData != null) {
            this.effectData.setPhysic(physicData);
        }
        final boolean slim = playerModel.isSlim();
        this.effectData.setSlim(slim).setRightSide(rightSide);
        final Collection<GeometryEffect> geometryEffects = this.effects.get(type);
        this.applyEffects(geometryEffects, player, playerModel, itemMetadata);
    }
    
    private void applyEffects(final Collection<GeometryEffect> effects, final Player player, final PlayerModel playerModel, final ItemMetadata itemMetadata) {
        for (final GeometryEffect effect : effects) {
            ErrorWrapper.wrap(() -> effect.apply(this.item, player, playerModel, itemMetadata, this.effectData), () -> "Geometry effect ([" + this.item.getName() + "]" + effect.getName());
        }
    }
    
    public Collection<GeometryEffect> getEffects(final GeometryEffect.Type type) {
        return this.effects.get(type);
    }
    
    @Nullable
    public GeometryEffect findEffect(final GeometryEffect.Type type, final Predicate<GeometryEffect> effectFilter) {
        final Collection<GeometryEffect> effects = this.getEffects(type);
        for (final GeometryEffect effect : effects) {
            if (effectFilter.test(effect)) {
                return effect;
            }
        }
        return null;
    }
    
    public static class EffectData
    {
        private float forward;
        private float strafe;
        private float gravity;
        private float renderYawOffset;
        private float pitch;
        private boolean slim;
        private boolean rightSide;
        
        public EffectData setPhysic(final PhysicData data) {
            this.forward = data.getForward();
            this.strafe = data.getStrafe();
            this.pitch = data.getPitch();
            this.gravity = data.getGravity();
            this.renderYawOffset = data.getRenderYawOffset();
            return this;
        }
        
        public float getForward() {
            return this.forward;
        }
        
        public EffectData setForward(final float forward) {
            this.forward = forward;
            return this;
        }
        
        public float getStrafe() {
            return this.strafe;
        }
        
        public EffectData setStrafe(final float strafe) {
            this.strafe = strafe;
            return this;
        }
        
        public float getGravity() {
            return this.gravity;
        }
        
        public EffectData setGravity(final float gravity) {
            this.gravity = gravity;
            return this;
        }
        
        public float getRenderYawOffset() {
            return this.renderYawOffset;
        }
        
        public EffectData setRenderYawOffset(final float renderYawOffset) {
            this.renderYawOffset = renderYawOffset;
            return this;
        }
        
        public float getPitch() {
            return this.pitch;
        }
        
        public EffectData setPitch(final float pitch) {
            this.pitch = pitch;
            return this;
        }
        
        public boolean isSlim() {
            return this.slim;
        }
        
        public EffectData setSlim(final boolean slim) {
            this.slim = slim;
            return this;
        }
        
        public boolean isRightSide() {
            return this.rightSide;
        }
        
        public EffectData setRightSide(final boolean rightSide) {
            this.rightSide = rightSide;
            return this;
        }
    }
}
