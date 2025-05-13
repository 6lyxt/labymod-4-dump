// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw;

import java.util.UUID;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.client.world.DefaultHeartTracker;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.draw.EntityHeartRenderer;

public class DefaultEntityHeartRenderer extends DefaultHeartRenderer implements EntityHeartRenderer
{
    private LivingEntity livingEntity;
    private int currentHealth;
    
    private DefaultEntityHeartRenderer(final LivingEntity livingEntity) {
        this.currentHealth = this.getHealthAsInt(livingEntity.getHealth());
    }
    
    protected static DefaultEntityHeartRenderer of(final LivingEntity livingEntity) {
        final DefaultEntityHeartRenderer heartRenderer = DefaultHeartTracker.ENTITY_HEART_RENDERER.computeIfAbsent(livingEntity.getUniqueId(), absent -> new DefaultEntityHeartRenderer(livingEntity));
        heartRenderer.livingEntity = livingEntity;
        return heartRenderer;
    }
    
    @Override
    public void renderHealthBar(final Stack stack, final float x, final float y, final int size) {
        if (this.livingEntity != null) {
            int maxHealth = this.getHealthAsInt(this.livingEntity.getMaximalHealth());
            if (maxHealth % 2 == 1) {
                ++maxHealth;
            }
            this.renderHealthBar(stack, x, y, size, this.currentHealth, Math.min(maxHealth, 20), this.getHealthAsInt(this.livingEntity.getAbsorptionHealth()));
        }
        else {
            this.renderHealthBar(stack, x, y, size, this.currentHealth, 20);
        }
    }
    
    @Override
    public void updateHealth(final int health) {
        if (this.currentHealth != health) {
            this.startFlashing((this.currentHealth > health) ? 3 : 2);
            this.currentHealth = health;
        }
    }
    
    @Override
    public void updateHealth(final float health) {
        this.updateHealth(this.getHealthAsInt(health));
    }
    
    @Override
    public void updateHealth(final LivingEntity livingEntity) {
        this.updateHealth(livingEntity.getHealth());
    }
    
    @Override
    public int getWidth(final int size) {
        int maxHealth = 20;
        int absorption = 0;
        if (this.livingEntity != null) {
            maxHealth = Math.min(this.getHealthAsInt(this.livingEntity.getMaximalHealth()), 20);
            if (maxHealth % 2 == 1) {
                ++maxHealth;
            }
            absorption = this.getHealthAsInt(this.livingEntity.getAbsorptionHealth());
            if (absorption % 2 == 1) {
                ++absorption;
            }
        }
        return this.getWidth(maxHealth + absorption, size);
    }
    
    private int getHealthAsInt(final float health) {
        return (int)Math.ceil(health);
    }
}
