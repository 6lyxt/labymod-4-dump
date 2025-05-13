// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.listener;

import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.render.overlay.HudWidgetDropzoneElementShiftEvent;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.HumanoidModelPoseAnimationEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.LabyAPI;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.resources.ResourceLocation;

public class TotemListener
{
    private static final ResourceLocation TOTEM_IDENTIFIER;
    private final Minecraft minecraft;
    private final ConfigProperty<Boolean> configProperty;
    
    public TotemListener(final LabyAPI labyAPI) {
        this.minecraft = labyAPI.minecraft();
        this.configProperty = labyAPI.config().ingame().hideTotemInOffHand();
    }
    
    @Subscribe
    public void cancelShieldInOffHandFirstPerson(final RenderFirstPersonItemInHandEvent event) {
        if (!this.isEnabled() || event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.HEAD) {
            return;
        }
        if (event.hand() == LivingEntity.Hand.OFF_HAND && this.isTotem(event.itemStack())) {
            event.setRenderItem(false);
        }
    }
    
    @Subscribe
    public void cancelVanillaArmPoseThirdPerson(final HumanoidModelPoseAnimationEvent event) {
        if (!this.isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        final LivingEntity entity = event.livingEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        final Player player = (Player)entity;
        if (this.isMainHand(player, event.handSide())) {
            return;
        }
        if (this.isTotem(player.getOffHandItemStack())) {
            event.setCancelled(true);
        }
    }
    
    @Subscribe
    public void modifyItemsInHandInThirdPerson(final ItemInHandLayerRenderEvent event) {
        if (!this.isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        final LivingEntity entity = event.livingEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        final Player player = (Player)entity;
        if (this.isMainHand(player, event.handSide())) {
            return;
        }
        if (this.isTotem(event.itemStack())) {
            event.setCancelled(true);
        }
    }
    
    @Subscribe
    public void onIngameOverlayElementRender(final IngameOverlayElementRenderEvent event) {
        if (!this.isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        final IngameOverlayElementRenderEvent.OverlayElementType type = event.elementType();
        final ClientPlayer player = this.minecraft.getClientPlayer();
        if (player == null) {
            return;
        }
        if ((type == IngameOverlayElementRenderEvent.OverlayElementType.OFFHAND_ITEM || type == IngameOverlayElementRenderEvent.OverlayElementType.OFFHAND_TEXTURE) && this.isTotem(player.getOffHandItemStack())) {
            event.setCancelled(true);
        }
    }
    
    @Subscribe
    public void onHudWidgetDropzoneElementShift(final HudWidgetDropzoneElementShiftEvent event) {
        if (!this.isEnabled() || !event.isOffHandSide()) {
            return;
        }
        if (this.isTotem(event.itemStack())) {
            event.setCancelled(true);
        }
    }
    
    public boolean isMainHand(final LivingEntity entity, final LivingEntity.HandSide side) {
        return entity.isMainHandRight() == (side == LivingEntity.HandSide.RIGHT);
    }
    
    public boolean isTotem(final ItemStack itemStack) {
        return itemStack.getIdentifier().equals(TotemListener.TOTEM_IDENTIFIER);
    }
    
    public boolean isEnabled() {
        return this.configProperty.get();
    }
    
    static {
        TOTEM_IDENTIFIER = ResourceLocation.create("minecraft", "totem_of_undying");
    }
}
