// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import java.util.UUID;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot(x = 3, y = 1)
public class ComboHudWidget extends TextHudWidget<TextHudWidgetConfig>
{
    private float lastHealth;
    private int comboCount;
    private long lastHit;
    private UUID lastEntityId;
    private TextLine textLine;
    
    public ComboHudWidget() {
        super("combo");
        this.lastEntityId = UUID.randomUUID();
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Override
    public void load(final TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Combo", 0);
    }
    
    @Subscribe
    public void attackEntity(final ClientPlayerInteractEvent event) {
        if (!this.isEnabled() || !event.type().equals(ClientPlayerInteractEvent.InteractionType.ATTACK)) {
            return;
        }
        final Entity entity = this.labyAPI.minecraft().getTargetEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        final Player player = (Player)entity;
        if (player.getHurtTime() > 1) {
            return;
        }
        if (entity.getUniqueId().equals(this.lastEntityId)) {
            this.setComboCount(this.comboCount + 1);
        }
        else {
            this.lastEntityId = entity.getUniqueId();
            this.setComboCount(1);
        }
        this.lastHit = TimeUtil.getMillis();
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        final ClientPlayer player = this.labyAPI.minecraft().getClientPlayer();
        if (player == null) {
            return;
        }
        if (player.getHealth() < this.lastHealth) {
            this.setComboCount(0);
        }
        this.lastHealth = player.getHealth();
        if (this.lastHit + 2000L < TimeUtil.getMillis()) {
            this.setComboCount(0);
        }
    }
    
    @Override
    public boolean isVisibleInGame() {
        return this.labyAPI.minecraft().getClientPlayer() != null && this.comboCount > 0;
    }
    
    private void setComboCount(final int comboCount) {
        this.comboCount = comboCount;
        this.textLine.updateAndFlush(comboCount);
    }
}
