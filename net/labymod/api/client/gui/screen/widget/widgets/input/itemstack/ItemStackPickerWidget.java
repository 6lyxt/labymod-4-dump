// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.itemstack;

import net.labymod.api.client.component.BaseComponent;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import java.lang.annotation.Annotation;
import java.util.Objects;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.world.item.ItemData;
import net.labymod.api.client.gui.screen.widget.action.Selectable;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
@SettingWidget
public class ItemStackPickerWidget extends SimpleWidget
{
    private final Selectable<ItemData> selectable;
    private ItemData itemData;
    
    public ItemStackPickerWidget(final ItemData itemData, final Selectable<ItemData> selectable) {
        this.itemData = itemData;
        this.selectable = selectable;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.setPressable(() -> {
            final ClientPlayer player2 = this.labyAPI.minecraft().getClientPlayer();
            if (player2 == null) {
                return;
            }
            else {
                final ItemStack itemStackInHand2 = player2.getMainHandItemStack();
                if (itemStackInHand2 == null) {
                    return;
                }
                else {
                    final ItemStack copyItemStack = itemStackInHand2.copy();
                    copyItemStack.setSize(1);
                    this.itemData = copyItemStack.toItemData();
                    this.selectable.select(this.itemData);
                    return;
                }
            }
        });
        boolean enabled = false;
        final ClientPlayer player = this.labyAPI.minecraft().getClientPlayer();
        Component itemName;
        if (player == null) {
            itemName = ((BaseComponent<Component>)Component.translatable("labymod.ui.itemStackPicker.notInGame", new Component[0])).color(NamedTextColor.RED);
        }
        else {
            final ItemStack itemStackInHand = player.getMainHandItemStack();
            if (itemStackInHand == null || itemStackInHand.isAir()) {
                itemName = ((BaseComponent<Component>)Component.translatable("labymod.ui.itemStackPicker.noItemInHand", new Component[0])).color(NamedTextColor.RED);
            }
            else {
                itemName = itemStackInHand.getDisplayName().color(NamedTextColor.YELLOW);
                enabled = true;
            }
        }
        this.setHoverComponent(Component.translatable("labymod.ui.itemStackPicker.setItemInHand", itemName));
        this.setAttributeState(AttributeState.ENABLED, enabled);
        this.interactable().set(enabled);
    }
    
    public ItemStack itemStack() {
        return this.itemData.toItemStack();
    }
    
    @Override
    public String getDefaultRendererName() {
        return "ItemStackPicker";
    }
    
    @SettingFactory
    public static class Factory implements WidgetFactory<ItemStackSetting, Widget>
    {
        @Override
        public Widget[] create(final Setting setting, final ItemStackSetting annotation, final SettingAccessor accessor) {
            final Object object = accessor.get();
            ItemData itemData;
            if (object instanceof final ItemStack itemStack) {
                itemData = itemStack.toItemData();
            }
            else {
                itemData = (ItemData)object;
            }
            final ItemData itemData2 = itemData;
            Objects.requireNonNull(accessor);
            final ItemStackPickerWidget widget = new ItemStackPickerWidget(itemData2, accessor::set);
            return new ItemStackPickerWidget[] { widget };
        }
        
        @Override
        public Class<?>[] types() {
            return new Class[] { ItemStack.class, ItemData.class };
        }
    }
    
    @SettingElement
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ItemStackSetting {
    }
}
