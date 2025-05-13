// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old.animations;

import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.render.overlay.HudWidgetDropzoneElementShiftEvent;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.Laby;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.Minecraft;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

public class EquipOldAnimation extends AbstractOldAnimation
{
    public static final String NAME = "equip";
    public static final boolean LEGACY_PVP;
    private final Minecraft minecraft;
    private ItemStack mainHandItem;
    private ItemStack offHandItem;
    private float mainHandHeight;
    private float oMainHandHeight;
    private float offHandHeight;
    private float oOffHandHeight;
    
    public EquipOldAnimation() {
        super("equip");
        this.minecraft = Laby.labyAPI().minecraft();
    }
    
    @Subscribe
    public void modifyEquipAnimation(final RenderFirstPersonItemInHandEvent event) {
        if (!this.isEnabled() || event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.HEAD) {
            return;
        }
        final boolean isMain = event.hand() == LivingEntity.Hand.MAIN_HAND;
        final float equipProgress = 1.0f - MathHelper.lerp(isMain ? this.mainHandHeight : this.offHandHeight, isMain ? this.oMainHandHeight : this.oOffHandHeight, event.getPartialTicks());
        event.setEquipProgress(equipProgress);
    }
    
    @Subscribe
    public void onIngameOverlayElementRender(final IngameOverlayElementRenderEvent event) {
        if (!this.isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        if (!this.classicPvPConfig.oldEquip().hideIndicator().get()) {
            return;
        }
        final IngameOverlayElementRenderEvent.OverlayElementType type = event.elementType();
        final ClientPlayer player = this.minecraft.getClientPlayer();
        if (player == null) {
            return;
        }
        if (type == IngameOverlayElementRenderEvent.OverlayElementType.ATTACK_INDICATOR) {
            event.setCancelled(true);
        }
    }
    
    @Subscribe
    public void onHudWidgetDropzoneElementShift(final HudWidgetDropzoneElementShiftEvent event) {
        if (!this.isEnabled() || event.isOffHandSide()) {
            return;
        }
        if (!this.classicPvPConfig.oldEquip().hideIndicator().get()) {
            return;
        }
        event.setCancelled(true);
    }
    
    @Subscribe
    public void onTick(final GameTickEvent event) {
        if (!this.isEnabled() || event.phase() != Phase.POST) {
            return;
        }
        this.oMainHandHeight = this.mainHandHeight;
        this.oOffHandHeight = this.offHandHeight;
        final ClientPlayer player = this.minecraft.getClientPlayer();
        if (player == null) {
            return;
        }
        final ItemStack mainHandItemStack = player.getMainHandItemStack();
        if (ItemStack.matches(this.mainHandItem, mainHandItemStack)) {
            this.mainHandItem = mainHandItemStack;
        }
        final ItemStack offHandItemStack = player.getOffHandItemStack();
        if (ItemStack.matches(this.offHandItem, offHandItemStack)) {
            this.offHandItem = offHandItemStack;
        }
        final float mainValue = (this.mainHandItem == mainHandItemStack) ? 1.0f : 0.0f;
        final float offValue = (this.offHandItem == offHandItemStack) ? 1.0f : 0.0f;
        this.mainHandHeight += MathHelper.clamp(mainValue - this.mainHandHeight, -0.4f, 0.4f);
        this.offHandHeight += MathHelper.clamp(offValue - this.offHandHeight, -0.4f, 0.4f);
        if (this.mainHandHeight < 0.1f) {
            this.mainHandItem = mainHandItemStack;
        }
        if (this.offHandHeight < 0.1f) {
            this.offHandItem = offHandItemStack;
        }
    }
    
    @Override
    public boolean isEnabled() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldEquip().enabled()) && !EquipOldAnimation.LEGACY_PVP;
    }
    
    static {
        LEGACY_PVP = MinecraftVersions.V1_8_9.orOlder();
    }
}
