// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.item;

import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Laby;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.item.ItemHudWidget;

@SpriteSlot(x = 4, y = 1)
public class ArrowHudWidget extends ItemHudWidget<ArrowHudWidgetConfig>
{
    private ItemStack arrowItemStack;
    
    public ArrowHudWidget() {
        super("arrow", ArrowHudWidgetConfig.class);
    }
    
    @Override
    public void load(final ArrowHudWidgetConfig config) {
        super.load(config);
        this.updateItemStack(this.arrowItemStack = Laby.references().itemStackFactory().create(ResourceLocation.create("minecraft", "arrow")), false);
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        ItemStack stack = this.arrowItemStack;
        int amount = isEditorContext ? 64 : 0;
        final ClientPlayer player = this.labyAPI.minecraft().getClientPlayer();
        if (player != null) {
            final ArrowHudWidgetConfig.DisplayMode displayMode = ((ArrowHudWidgetConfig)this.config).displayMode().get();
            switch (displayMode.ordinal()) {
                case 0: {
                    final Inventory inventory = player.inventory();
                    amount = inventory.countAllArrows();
                    break;
                }
                case 1: {
                    stack = player.inventory().getNextArrows();
                    amount = ((stack == null) ? 0 : stack.getSize());
                    break;
                }
            }
        }
        this.updateItemStack(stack, isEditorContext);
        this.updateItemName(Component.text("" + amount), isEditorContext);
    }
    
    @Override
    public boolean isVisibleInGame() {
        final Minecraft minecraft = this.labyAPI.minecraft();
        if (minecraft == null) {
            return false;
        }
        final ClientPlayer player = minecraft.getClientPlayer();
        if (player == null || player.gameMode() == GameMode.SPECTATOR) {
            return false;
        }
        final ArrowHudWidgetConfig.DisplayMode displayMode = ((ArrowHudWidgetConfig)this.config).displayMode().get();
        switch (displayMode.ordinal()) {
            case 0: {
                final Inventory inventory = player.inventory();
                return inventory.countAllArrows() > 0;
            }
            case 1: {
                final ItemStack stack = player.inventory().getNextArrows();
                return stack != null && stack.getSize() > 0;
            }
            default: {
                return false;
            }
        }
    }
    
    @Override
    public Icon createPlaceholderIcon() {
        return Textures.SpriteHudPlaceholder.ARROW;
    }
    
    public static class ArrowHudWidgetConfig extends HudWidgetConfig
    {
        @DropdownWidget.DropdownSetting
        private final ConfigProperty<DisplayMode> displayMode;
        
        public ArrowHudWidgetConfig() {
            this.displayMode = ConfigProperty.createEnum(DisplayMode.ALL);
        }
        
        public ConfigProperty<DisplayMode> displayMode() {
            return this.displayMode;
        }
        
        public enum DisplayMode
        {
            ALL, 
            NEXT;
        }
    }
}
