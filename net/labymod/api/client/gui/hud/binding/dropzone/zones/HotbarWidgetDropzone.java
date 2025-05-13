// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.options.AttackIndicatorPosition;
import net.labymod.api.event.client.render.overlay.HudWidgetDropzoneElementShiftEvent;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;

public abstract class HotbarWidgetDropzone extends HudWidgetDropzone
{
    private static final int HOTBAR_WIDTH = 150;
    private static final int OFFHAND_OFFSET = 30;
    private static final int INDICATOR_OFFSET = 25;
    private final boolean isLeft;
    
    public HotbarWidgetDropzone(final String id, final boolean isLeft) {
        super(id);
        this.isLeft = isLeft;
    }
    
    public float getOffset(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        float offset = 94.0f;
        if (!renderer.isEditor()) {
            final Minecraft minecraft = Laby.labyAPI().minecraft();
            final ClientPlayer clientPlayer = minecraft.getClientPlayer();
            final ItemStack mainHandItemStack = (clientPlayer == null) ? null : clientPlayer.getMainHandItemStack();
            final ItemStack offHandItemStack = (clientPlayer == null) ? null : clientPlayer.getOffHandItemStack();
            final MainHand mainHand = minecraft.options().mainHand();
            final boolean isOffhandSide = this.isLeft == (mainHand != MainHand.LEFT);
            final ItemStack itemStack = isOffhandSide ? offHandItemStack : mainHandItemStack;
            if (isOffhandSide && itemStack != null && !itemStack.isAir()) {
                final HudWidgetDropzoneElementShiftEvent event = Laby.fireEvent(new HudWidgetDropzoneElementShiftEvent(true, itemStack));
                if (!event.isCancelled()) {
                    offset += 30.0f;
                }
            }
            final AttackIndicatorPosition indicatorPosition = minecraft.options().attackIndicatorPosition();
            if (indicatorPosition == AttackIndicatorPosition.HOTBAR && !isOffhandSide) {
                final HudWidgetDropzoneElementShiftEvent event2 = Laby.fireEvent(new HudWidgetDropzoneElementShiftEvent(false, itemStack));
                if (!event2.isCancelled()) {
                    offset += 25.0f;
                }
            }
        }
        return this.isLeft ? (-offset - hudWidgetSize.getScaledWidth()) : offset;
    }
    
    @Override
    public HudWidgetAnchor getAnchor() {
        return this.isLeft ? HudWidgetAnchor.RIGHT_BOTTOM : HudWidgetAnchor.LEFT_BOTTOM;
    }
}
