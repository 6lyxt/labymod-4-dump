// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.Minecraft;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import java.text.DecimalFormat;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot(x = 3, y = 3)
public class RangeHudWidget extends TextHudWidget<TextHudWidgetConfig>
{
    private final DecimalFormat df;
    private TextLine textLine;
    private long lastAttack;
    private double lastDistance;
    
    public RangeHudWidget() {
        super("range");
        this.df = new DecimalFormat("#.##");
    }
    
    @Override
    public void load(final TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Range", "0 blocks");
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Subscribe
    public void attackEntity(final ClientPlayerInteractEvent event) {
        if (!this.isEnabled() || !event.type().equals(ClientPlayerInteractEvent.InteractionType.ATTACK)) {
            return;
        }
        final Minecraft minecraft = this.labyAPI.minecraft();
        final Entity entity = minecraft.getTargetEntity();
        final HitResult hitResult = minecraft.getHitResult();
        if (hitResult == null || !(entity instanceof LivingEntity)) {
            return;
        }
        final LivingEntity livingEntity = (LivingEntity)entity;
        if (livingEntity.getHealth() == 0.0f) {
            return;
        }
        final double distanceSquared = hitResult.location().distance(minecraft.getCameraEntity().eyePosition());
        this.textLine.updateAndFlush(this.df.format(distanceSquared) + " blocks");
        this.lastAttack = TimeUtil.getMillis();
    }
    
    @Override
    public boolean isVisibleInGame() {
        return this.lastAttack + 3000L > TimeUtil.getMillis();
    }
}
