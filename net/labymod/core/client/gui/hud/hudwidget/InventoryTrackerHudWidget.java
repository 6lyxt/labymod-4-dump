// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import java.util.stream.Stream;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.world.item.VanillaItem;
import net.labymod.api.client.world.item.Item;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.RenderTypeAttachmentEvent;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import java.util.Arrays;
import net.labymod.api.event.client.world.WorldLeaveEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.event.client.world.WorldLoadEvent;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.font.RenderableComponent;
import java.util.Iterator;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundConfig;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundHudWidget;

@IntroducedIn("4.1.0")
@SpriteSlot(x = 7, y = 4)
public class InventoryTrackerHudWidget extends BackgroundHudWidget<InventoryTrackerHudWidgetConfig>
{
    private static final long FADE_DURATION = 200L;
    private static final int ICON_SIZE = 15;
    private static final float ICON_SCALE = 0.65f;
    private final ItemStack[] slots;
    private final List<InventoryChangeRecord> records;
    private final List<InventoryChangeRecord> dummyRecords;
    private boolean dirty;
    private boolean renderItem;
    private long timeLastWorldLoad;
    
    public InventoryTrackerHudWidget() {
        super("inventory_tracker", InventoryTrackerHudWidgetConfig.class);
        this.slots = new ItemStack[41];
        this.records = new ArrayList<InventoryChangeRecord>();
        this.dummyRecords = new ArrayList<InventoryChangeRecord>();
        this.dirty = false;
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Override
    public void load(final InventoryTrackerHudWidgetConfig config) {
        super.load(config);
        this.dummyRecords.clear();
        this.dummyRecords.add(InventoryChangeRecord.createDummy(VanillaItems.STONE, 46, null, config));
        this.dummyRecords.add(InventoryChangeRecord.createDummy(VanillaItems.DIAMOND_SWORD, 1, null, config));
        this.dummyRecords.add(InventoryChangeRecord.createDummy(VanillaItems.COOKIE, 1, ((BaseComponent<Component>)Component.text("Cookie").color(NamedTextColor.GOLD)).append(((BaseComponent<Component>)Component.text(" \u2764").color(NamedTextColor.RED)).decorate(TextDecoration.BOLD)), config));
        this.dummyRecords.add(InventoryChangeRecord.createDummy(VanillaItems.STICK, -1, null, config));
        final TextComponent displayName = Component.text("My Diamond").color(NamedTextColor.AQUA);
        this.dummyRecords.add(InventoryChangeRecord.createDummy(VanillaItems.DIAMOND, -2, displayName, config));
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
        final BackgroundConfig background = ((InventoryTrackerHudWidgetConfig)this.config).background();
        final float margin = background.getMargin();
        final float padding = background.getPadding();
        final float spacing = ((InventoryTrackerHudWidgetConfig)this.config).lineSpacing().get();
        final float x = margin + padding;
        float y = margin + padding;
        float width = 0.0f;
        boolean firstLayer = true;
        final boolean rightAligned = this.anchor().isRight();
        for (final RecordRenderLayer layer : RecordRenderLayer.getLayers(rightAligned)) {
            float maxLayerWidth = 0.0f;
            y = margin + padding;
            if (!((InventoryTrackerHudWidgetConfig)this.config).showIcon().get() && layer == RecordRenderLayer.ICON) {
                width += 2.0f;
            }
            else {
                final List<InventoryChangeRecord> records = isEditorContext ? this.dummyRecords : this.records;
                for (final InventoryChangeRecord record : records) {
                    if (record.isExpired()) {
                        continue;
                    }
                    final RenderableComponent nameComponent = record.displayNameComponent();
                    final RenderableComponent countComponent = record.countComponent();
                    final float height = (nameComponent.getHeight() + padding * 2.0f) * record.getOpacity();
                    if (stack != null) {
                        if (firstLayer) {
                            this.renderBackgroundSegment(stack, x - padding, y - padding, size.getActualWidth() - margin * 2.0f, height, record.getOpacity());
                        }
                        this.renderRecord(stack, record, x + width, y, layer, isEditorContext);
                    }
                    float recordLayerWidth = padding * 2.0f;
                    switch (layer.ordinal()) {
                        case 0: {
                            recordLayerWidth += countComponent.getWidth();
                            break;
                        }
                        case 1: {
                            recordLayerWidth += 15.0f;
                            break;
                        }
                        case 2: {
                            recordLayerWidth += nameComponent.getWidth();
                            break;
                        }
                    }
                    y += height + spacing;
                    maxLayerWidth = Math.max(maxLayerWidth, recordLayerWidth);
                }
                width += maxLayerWidth;
                firstLayer = false;
            }
        }
        size.set(width + 2.0f + margin * 2.0f, Math.max(y + margin - padding - spacing, spacing));
    }
    
    private void renderRecord(final Stack stack, final InventoryChangeRecord record, final float x, final float y, final RecordRenderLayer layer, final boolean isEditorContext) {
        final RenderPipeline renderPipeline = this.labyAPI.renderPipeline();
        final float currentAlpha = renderPipeline.getAlpha();
        final float opacity = currentAlpha * record.getOpacity();
        if (opacity == 0.0f) {
            return;
        }
        renderPipeline.setAlpha(opacity, () -> {
            switch (layer.ordinal()) {
                case 0: {
                    final RenderableComponent countComponent = record.countComponent();
                    renderPipeline.componentRenderer().builder().text(countComponent).pos(x, y + 1.0f).render(stack);
                    break;
                }
                case 1: {
                    stack.push();
                    stack.scale(0.65f, 0.65f, 1.0f);
                    this.renderItem = true;
                    this.labyAPI.minecraft().itemStackRenderer().renderItemStack(stack, record.icon(), (int)((x + 7.0f) / 0.65f) - 8, (int)((y + 5.0f) / 0.65f) - 8, false, opacity);
                    this.renderItem = false;
                    stack.pop();
                    break;
                }
                case 2: {
                    final RenderableComponent nameComponent = record.displayNameComponent();
                    final float offset = this.anchor().isRight() ? (this.getMaxNameWidth(isEditorContext) - nameComponent.getWidth()) : 0.0f;
                    renderPipeline.componentRenderer().builder().text(nameComponent).pos(x + offset, y + 1.0f).render(stack);
                    break;
                }
            }
        });
    }
    
    @Override
    public boolean isVisibleInGame() {
        for (final InventoryChangeRecord record : this.records) {
            if (!record.isExpired()) {
                return true;
            }
        }
        return false;
    }
    
    @Subscribe
    public void onWorldLoad(final WorldLoadEvent event) {
        this.timeLastWorldLoad = TimeUtil.getMillis();
    }
    
    @Subscribe
    public void onGameTick(final GameTickEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        if (!this.labyAPI.minecraft().isIngame()) {
            this.timeLastWorldLoad = TimeUtil.getMillis();
        }
    }
    
    @Subscribe
    public void onWorldLeave(final WorldLeaveEvent event) {
        this.records.clear();
        Arrays.fill(this.slots, null);
    }
    
    @Subscribe
    public void onInventorySlotChanged(final InventorySetSlotEvent event) {
        this.dirty = true;
    }
    
    @Subscribe
    public void onRenderTypeAttachment(final RenderTypeAttachmentEvent event) {
        if (event.phase() != Phase.POST || !this.renderItem) {
            return;
        }
        final String name = event.name();
        if ("entity_cutout".equals(name) || "entity_translucent_cull".equals(name)) {
            if (event.state() == RenderTypeAttachmentEvent.State.APPLY) {
                Laby.gfx().enableBlend();
            }
            else {
                Laby.gfx().disableBlend();
            }
        }
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        super.onTick(isEditorContext);
        final ClientPlayer player = this.labyAPI.minecraft().getClientPlayer();
        if (player != null && this.dirty) {
            this.dirty = false;
            this.purgeInvalidRecords();
            this.analyzeDifference(player);
        }
    }
    
    private void analyzeDifference(final ClientPlayer player) {
        for (int i = 0; i < this.slots.length; ++i) {
            final ItemStack newStack = this.getItemAtIndex(player, i).copy();
            final ItemStack oldStack = this.slots[i];
            if (oldStack == null || oldStack.isAir()) {
                if (!newStack.isAir()) {
                    this.itemStackChanged(newStack, newStack.getSize());
                }
            }
            else if (newStack.getAsItem().equals(oldStack.getAsItem())) {
                final int difference = newStack.getSize() - oldStack.getSize();
                if (difference != 0) {
                    this.itemStackChanged(newStack, difference);
                }
            }
            else {
                this.itemStackChanged(oldStack, -oldStack.getSize());
                if (!newStack.isAir()) {
                    this.itemStackChanged(newStack, newStack.getSize());
                }
            }
            this.slots[i] = newStack;
        }
    }
    
    private ItemStack getItemAtIndex(final ClientPlayer player, final int index) {
        if (index < 36) {
            return player.inventory().itemStackAt(index);
        }
        return switch (index) {
            case 36 -> player.getEquipmentItemStack(LivingEntity.EquipmentSpot.HEAD);
            case 37 -> player.getEquipmentItemStack(LivingEntity.EquipmentSpot.CHEST);
            case 38 -> player.getEquipmentItemStack(LivingEntity.EquipmentSpot.LEGS);
            case 39 -> player.getEquipmentItemStack(LivingEntity.EquipmentSpot.FEET);
            default -> player.getOffHandItemStack();
        };
    }
    
    private void itemStackChanged(final ItemStack itemStack, final int difference) {
        final long timePassedSinceLastWorldLoad = TimeUtil.getMillis() - this.timeLastWorldLoad;
        final boolean showInitialItems = ((InventoryTrackerHudWidgetConfig)this.config).showInitialItems().get() || timePassedSinceLastWorldLoad > 1000L;
        if (showInitialItems) {
            this.computeIfAbsent(itemStack).add(difference);
            this.records.sort((a, b) -> Long.compare(b.getStartTimestamp(), a.getStartTimestamp()));
        }
        else {
            this.records.clear();
        }
    }
    
    private InventoryChangeRecord computeIfAbsent(final ItemStack itemStack) {
        for (final InventoryChangeRecord record : this.records) {
            if (record.isItem(itemStack.getAsItem()) && record.isMutable()) {
                return record;
            }
        }
        final InventoryTrackerHudWidgetConfig config = (InventoryTrackerHudWidgetConfig)this.config;
        InventoryChangeRecord record = InventoryChangeRecord.fromItemStack(itemStack, config);
        this.records.add(record);
        return record;
    }
    
    private void purgeInvalidRecords() {
        this.records.removeIf(InventoryChangeRecord::isExpired);
    }
    
    private float getMaxNameWidth(final boolean isEditorContext) {
        float maxWidth = 0.0f;
        for (final InventoryChangeRecord record : isEditorContext ? this.dummyRecords : this.records) {
            if (!record.isExpired()) {
                maxWidth = Math.max(maxWidth, record.displayNameComponent().getWidth());
            }
        }
        return maxWidth;
    }
    
    private static class InventoryChangeRecord
    {
        private final ItemStack icon;
        private final RenderableComponent displayNameComponent;
        private final boolean colored;
        private final Boolean showItemDrops;
        private long timestamp;
        private long duration;
        private int count;
        private RenderableComponent countComponent;
        
        private InventoryChangeRecord(final ItemStack icon, final InventoryTrackerHudWidgetConfig config) {
            this.icon = icon;
            this.duration = config.displayTime().get() * 1000;
            this.colored = config.coloredAmount().get();
            this.showItemDrops = config.showItemDrops().get();
            if (config.customItemName().get()) {
                this.displayNameComponent = RenderableComponent.of(icon.getDisplayName());
            }
            else {
                final ResourceLocation identifier = this.icon.getAsItem().getIdentifier();
                final ItemStack itemStack = Laby.references().itemStackFactory().create(identifier);
                this.displayNameComponent = RenderableComponent.of(itemStack.getDisplayName());
            }
        }
        
        public void add(final int amount) {
            this.count += amount;
            final boolean positive = this.count >= 0;
            final boolean isInitial = this.timestamp <= 1L;
            final boolean isZero = this.count == 0;
            if (isInitial) {
                this.timestamp = ((this.showItemDrops || positive) ? TimeUtil.getMillis() : -200L);
            }
            else {
                final boolean isFadingIn = this.getFadeInProgress() < 1.0f;
                if (!isFadingIn) {
                    this.timestamp = TimeUtil.getMillis() - 200L;
                }
            }
            final String sign = positive ? "+" : "";
            final TextColor color = (isZero || !this.colored) ? NamedTextColor.WHITE : (positive ? NamedTextColor.GREEN : NamedTextColor.RED);
            final TextComponent amountComponent = Component.text(sign + this.count).color(color);
            this.countComponent = RenderableComponent.of(amountComponent);
            if (isZero || (!this.showItemDrops && !positive)) {
                this.finish();
            }
        }
        
        private void finish() {
            this.duration = 0L;
            if (this.getTimeAlive() <= 20L) {
                this.timestamp = 0L;
            }
        }
        
        private void showForever() {
            final boolean positive = this.count >= 0;
            if (positive || this.showItemDrops) {
                this.duration = Long.MAX_VALUE;
                this.timestamp -= 200L;
            }
        }
        
        public float getFadeInProgress() {
            final long timePassed = this.getTimeAlive();
            return (timePassed > 200L) ? 1.0f : (timePassed / 200.0f);
        }
        
        public float getFadeOutProgress() {
            final long timeRemaining = this.getTimeRemaining();
            return (timeRemaining > 200L) ? 0.0f : (1.0f - timeRemaining / 200.0f);
        }
        
        public long getStartTimestamp() {
            return this.timestamp;
        }
        
        public long getEndTimestamp() {
            if (this.duration == Long.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
            return this.timestamp + this.duration + 400L;
        }
        
        public float getOpacity() {
            final float opacity = this.getFadeInProgress() * (1.0f - this.getFadeOutProgress());
            return (float)CubicBezier.EASE_IN_OUT.curve(opacity);
        }
        
        public long getTimeAlive() {
            return TimeUtil.getMillis() - this.timestamp;
        }
        
        public long getTimeRemaining() {
            return this.getEndTimestamp() - TimeUtil.getMillis();
        }
        
        public boolean isMutable() {
            return !this.isExpired() && this.getTimeRemaining() > 200L;
        }
        
        public boolean isExpired() {
            return this.getEndTimestamp() < TimeUtil.getMillis();
        }
        
        public ItemStack icon() {
            return this.icon;
        }
        
        public RenderableComponent displayNameComponent() {
            return this.displayNameComponent;
        }
        
        public RenderableComponent countComponent() {
            return this.countComponent;
        }
        
        public int getCount() {
            return this.count;
        }
        
        public boolean isItem(final Item item) {
            return this.icon.getAsItem().equals(item);
        }
        
        public static InventoryChangeRecord fromItemStack(final ItemStack itemStack, final InventoryTrackerHudWidgetConfig config) {
            return new InventoryChangeRecord(itemStack, config);
        }
        
        public static InventoryChangeRecord createDummy(final VanillaItem item, final int amount, final Component displayName, final InventoryTrackerHudWidgetConfig config) {
            return createDummy(item.identifier(), amount, displayName, config);
        }
        
        public static InventoryChangeRecord createDummy(final ResourceLocation identifier, final int amount, final Component displayName, final InventoryTrackerHudWidgetConfig config) {
            final ItemStackFactory factory = Laby.references().itemStackFactory();
            final ItemStack itemStack = factory.create(identifier);
            if (displayName != null) {
                final NBTTagCompound nbtTag = itemStack.getOrCreateNBTTag();
                final NBTTagCompound display = Laby.references().nbtFactory().compound();
                if (MinecraftVersions.V1_12_2.orOlder()) {
                    final String name = LegacyComponentSerializer.legacySection().serialize(displayName);
                    display.setString("Name", name);
                }
                else {
                    final String json = Laby.references().gsonComponentSerializer().serialize(displayName);
                    display.setString("Name", json);
                }
                nbtTag.set("display", display);
            }
            final InventoryChangeRecord record = fromItemStack(itemStack, config);
            record.add(amount);
            record.showForever();
            return record;
        }
    }
    
    private enum RecordRenderLayer
    {
        COUNT, 
        ICON, 
        NAME;
        
        public static final RecordRenderLayer[] VALUES;
        public static final RecordRenderLayer[] VALUES_REVERSE;
        
        public static RecordRenderLayer[] getLayers(final boolean reverse) {
            return reverse ? RecordRenderLayer.VALUES_REVERSE : RecordRenderLayer.VALUES;
        }
        
        static {
            VALUES = values();
            VALUES_REVERSE = Stream.of(RecordRenderLayer.VALUES).sorted((a, b) -> Integer.compare(b.ordinal(), a.ordinal())).toArray(RecordRenderLayer[]::new);
        }
    }
    
    public static class InventoryTrackerHudWidgetConfig extends BackgroundHudWidgetConfig
    {
        @SliderWidget.SliderSetting(min = 1.0f, max = 10.0f, steps = 1.0f)
        private final ConfigProperty<Integer> displayTime;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> coloredAmount;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> customItemName;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showIcon;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showInitialItems;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showItemDrops;
        @SliderWidget.SliderSetting(min = 0.0f, max = 10.0f, steps = 1.0f)
        private final ConfigProperty<Integer> lineSpacing;
        
        public InventoryTrackerHudWidgetConfig() {
            this.displayTime = new ConfigProperty<Integer>(5);
            this.coloredAmount = new ConfigProperty<Boolean>(true);
            this.customItemName = new ConfigProperty<Boolean>(true);
            this.showIcon = new ConfigProperty<Boolean>(true);
            this.showInitialItems = new ConfigProperty<Boolean>(false);
            this.showItemDrops = new ConfigProperty<Boolean>(true);
            this.lineSpacing = new ConfigProperty<Integer>(1);
        }
        
        public ConfigProperty<Integer> displayTime() {
            return this.displayTime;
        }
        
        public ConfigProperty<Boolean> coloredAmount() {
            return this.coloredAmount;
        }
        
        public ConfigProperty<Boolean> customItemName() {
            return this.customItemName;
        }
        
        public ConfigProperty<Boolean> showIcon() {
            return this.showIcon;
        }
        
        public ConfigProperty<Boolean> showInitialItems() {
            return this.showInitialItems;
        }
        
        public ConfigProperty<Boolean> showItemDrops() {
            return this.showItemDrops;
        }
        
        public ConfigProperty<Integer> lineSpacing() {
            return this.lineSpacing;
        }
    }
}
