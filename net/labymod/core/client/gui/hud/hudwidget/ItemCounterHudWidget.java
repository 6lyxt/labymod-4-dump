// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget;

import net.labymod.api.property.Property;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.VanillaItem;
import net.labymod.api.client.entity.player.ClientPlayer;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.itemstack.ItemStackPickerWidget;
import net.labymod.api.client.world.item.ItemData;
import net.labymod.api.configuration.settings.type.list.ListSettingConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.ItemStackRenderer;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import java.util.Iterator;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundHudWidget;

@IntroducedIn("4.1.0")
@SpriteSlot(x = 1, y = 5)
public class ItemCounterHudWidget extends BackgroundHudWidget<ItemCounterHudWidgetConfig>
{
    private final List<CountingItem> dummyItems;
    
    public ItemCounterHudWidget() {
        super("item_counter", ItemCounterHudWidgetConfig.class);
        this.dummyItems = new ArrayList<CountingItem>();
        this.bindCategory(HudWidgetCategory.INGAME);
        this.bindDropzones(NamedHudWidgetDropzones.ITEM_COUNTER_LEFT, NamedHudWidgetDropzones.ITEM_COUNTER_RIGHT);
    }
    
    @Override
    public void load(final ItemCounterHudWidgetConfig config) {
        super.load(config);
        this.dummyItems.clear();
        this.dummyItems.add(CountingItem.createDummy(VanillaItems.GOLDEN_APPLE, 4));
        this.dummyItems.add(CountingItem.createDummy(VanillaItems.MUSHROOM_STEM, 12));
        this.dummyItems.add(CountingItem.createDummy(VanillaItems.COOKED_BEEF, 5));
        this.dummyItems.add(CountingItem.createDummy(VanillaItems.ARROW, 128));
        this.dummyItems.add(CountingItem.createDummy(VanillaItems.SPLASH_POTION, 0));
        final List<CountingItem> items = config.items.get();
        if (items.isEmpty()) {
            for (final CountingItem dummyItem : this.dummyItems) {
                final CountingItem item = dummyItem.copyWithCount(0);
                item.updateItemCount();
                items.add(item);
            }
        }
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
        final List<CountingItem> countingItems = ((ItemCounterHudWidgetConfig)this.config).items.get();
        if (countingItems.isEmpty() && !isEditorContext) {
            return;
        }
        final ItemStackRenderer itemStackRenderer = this.labyAPI.minecraft().itemStackRenderer();
        final ComponentRenderer componentRenderer = this.labyAPI.renderPipeline().componentRenderer();
        final float itemSize = 16.0f;
        final float margin = ((ItemCounterHudWidgetConfig)this.config).background().getMargin();
        final float padding = ((ItemCounterHudWidgetConfig)this.config).background().getPadding();
        final float spacingHeight = ((ItemCounterHudWidgetConfig)this.config).spacingHeight().get();
        final float segmentSpacing = ((ItemCounterHudWidgetConfig)this.config).segmentSpacing().get();
        int visibleItems = 0;
        for (final CountingItem countingItem : countingItems) {
            if (countingItem.hideWhenNoItems().get() && countingItem.getItemCount() == 0) {
                continue;
            }
            ++visibleItems;
        }
        final boolean floatingPointPosition = Laby.references().themeService().currentTheme().metadata().get("hud_widget_floating_point_position", false);
        float x = 0.0f;
        final List<CountingItem> list = (countingItems.isEmpty() || (visibleItems == 0 && isEditorContext)) ? this.dummyItems : countingItems;
        for (final CountingItem countingItem2 : list) {
            float segmentWidth = Math.max(itemSize, countingItem2.renderableCount().getWidth()) + padding * 2.0f;
            if (segmentWidth % 2.0f != 0.0f) {
                ++segmentWidth;
            }
            if (countingItem2.hideWhenNoItems().get() && countingItem2.getItemCount() == 0) {
                continue;
            }
            if (stack != null) {
                super.renderBackgroundSegment(stack, x + margin, margin, segmentWidth, itemSize + spacingHeight + componentRenderer.height() + padding * 2.0f);
                final ItemStack itemStack = countingItem2.itemStack();
                itemStackRenderer.renderItemStack(stack, itemStack, (int)(x + segmentWidth / 2.0f - 8.0f + margin), (int)(padding + margin));
                componentRenderer.builder().pos(x + segmentWidth / 2.0f + margin + (floatingPointPosition ? 0.5f : 0.0f), itemSize + spacingHeight + padding + margin).useFloatingPointPosition(floatingPointPosition).centered(true).text(countingItem2.renderableCount()).render(stack);
            }
            x += segmentWidth + segmentSpacing;
        }
        size.set(Math.max(x - segmentSpacing, segmentSpacing) + margin * 2.0f, itemSize + spacingHeight + componentRenderer.height() + padding * 2.0f + margin * 2.0f);
    }
    
    @Subscribe
    public void onInventorySetSlot(final InventorySetSlotEvent event) {
        for (final CountingItem countingItem : ((ItemCounterHudWidgetConfig)this.config).items.get()) {
            countingItem.updateItemCount();
        }
    }
    
    @Override
    public boolean isVisibleInGame() {
        return !((ItemCounterHudWidgetConfig)this.config).items.get().isEmpty();
    }
    
    public static class ItemCounterHudWidgetConfig extends BackgroundHudWidgetConfig
    {
        private final ConfigProperty<List<CountingItem>> items;
        @SliderWidget.SliderSetting(min = 0.0f, max = 20.0f, steps = 1.0f)
        private final ConfigProperty<Float> segmentSpacing;
        @SliderWidget.SliderSetting(min = 0.0f, max = 20.0f, steps = 1.0f)
        private final ConfigProperty<Float> spacingHeight;
        
        public ItemCounterHudWidgetConfig() {
            this.items = new ConfigProperty<List<CountingItem>>(new ArrayList<CountingItem>());
            this.segmentSpacing = new ConfigProperty<Float>(2.0f);
            this.spacingHeight = new ConfigProperty<Float>(2.0f);
        }
        
        public ConfigProperty<Float> segmentSpacing() {
            return this.segmentSpacing;
        }
        
        public ConfigProperty<Float> spacingHeight() {
            return this.spacingHeight;
        }
    }
    
    public static class CountingItem extends Config implements ListSettingConfig
    {
        private static final ItemData DEFAULT_ITEM;
        @ItemStackPickerWidget.ItemStackSetting
        private final ConfigProperty<ItemData> item;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> hideWhenNoItems;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> ignoreNBT;
        private transient ItemStack itemStack;
        private transient int itemCount;
        private transient RenderableComponent renderableCount;
        
        public CountingItem() {
            this.item = ConfigProperty.create(CountingItem.DEFAULT_ITEM, this::updateItemStack);
            this.hideWhenNoItems = new ConfigProperty<Boolean>(false);
            this.ignoreNBT = new ConfigProperty<Boolean>(false);
            this.item.addChangeListener((type, oldValue, newValue) -> {
                this.updateItemStack(newValue);
                this.updateItemCount();
                return;
            });
            this.ignoreNBT.addChangeListener((type, oldValue, newValue) -> this.updateItemCount());
            this.updateItemCount();
        }
        
        private CountingItem(final ItemData itemStack, final int itemCount) {
            this.item = ConfigProperty.create(CountingItem.DEFAULT_ITEM, this::updateItemStack);
            this.hideWhenNoItems = new ConfigProperty<Boolean>(false);
            this.ignoreNBT = new ConfigProperty<Boolean>(false);
            this.item.set(itemStack);
            this.onItemCountChange(itemCount);
        }
        
        @NotNull
        @Override
        public Component newEntryTitle() {
            return this.itemStack.getDisplayName();
        }
        
        @NotNull
        @Override
        public Component entryDisplayName() {
            return this.itemStack.getDisplayName();
        }
        
        public int getItemCount() {
            return this.itemCount;
        }
        
        public RenderableComponent renderableCount() {
            return this.renderableCount;
        }
        
        public ConfigProperty<ItemData> item() {
            return this.item;
        }
        
        public ConfigProperty<Boolean> hideWhenNoItems() {
            return this.hideWhenNoItems;
        }
        
        public ConfigProperty<Boolean> ignoreNBT() {
            return this.ignoreNBT;
        }
        
        private void updateItemCount() {
            final ClientPlayer player = Laby.labyAPI().minecraft().getClientPlayer();
            if (player == null) {
                this.onItemCountChange(0);
                return;
            }
            int count = 0;
            for (int slot = 0; slot < 36; ++slot) {
                final ItemStack stack = player.inventory().itemStackAt(slot);
                if (!stack.isAir() && this.compare(stack, this.itemStack)) {
                    count += stack.getSize();
                }
            }
            this.onItemCountChange(count);
        }
        
        private boolean compare(final ItemStack first, final ItemStack second) {
            return this.ignoreNBT().get() ? ItemStack.isSameItem(first, second) : ItemStack.isSameItemSameTags(first, second);
        }
        
        private void onItemCountChange(final int count) {
            this.itemCount = count;
            this.renderableCount = RenderableComponent.of(Component.text(count));
        }
        
        public CountingItem copyWithCount(final int itemCount) {
            return new CountingItem(this.item.get(), itemCount);
        }
        
        public ItemStack itemStack() {
            return this.itemStack;
        }
        
        private void updateItemStack(final ItemData data) {
            this.itemStack = data.toItemStack();
        }
        
        public static CountingItem createDummy(final VanillaItem item, final int itemCount) {
            return createDummy(item.identifier(), itemCount);
        }
        
        public static CountingItem createDummy(final ResourceLocation identifier, final int itemCount) {
            final ItemStack itemStack = Laby.references().itemStackFactory().create(identifier);
            return new CountingItem(itemStack.toItemData(), itemCount);
        }
        
        static {
            DEFAULT_ITEM = Laby.references().itemStackFactory().create("minecraft", "apple").toItemData();
        }
    }
}
