// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.metadata;

import net.labymod.core.main.user.shop.item.model.type.TextureType;
import java.util.Locale;
import java.util.Optional;
import java.util.Iterator;
import java.util.function.Consumer;
import net.labymod.core.main.user.shop.item.metadata.type.IntMetadata;
import net.labymod.core.main.user.shop.item.metadata.type.SizeMetadata;
import net.labymod.core.main.user.shop.item.metadata.type.ItemNameTagMetadata;
import net.labymod.core.main.user.shop.item.metadata.type.ColorArrayMetadata;
import net.labymod.core.main.user.shop.item.metadata.type.OffsetVectorMetadata;
import net.labymod.core.main.user.shop.item.metadata.type.TextureDetailsMetadata;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.Objects;
import net.labymod.core.main.user.shop.item.metadata.type.BooleanMetadata;
import java.util.ArrayList;
import net.labymod.core.main.user.shop.item.model.ItemNameTag;
import net.labymod.api.util.Color;
import net.labymod.core.main.user.shop.item.model.TextureDetails;
import net.labymod.core.main.user.shop.item.model.OffsetVector;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.core.main.user.shop.item.geometry.DepthMap;
import java.util.List;
import net.labymod.core.main.user.shop.item.ItemDetails;

public class ItemMetadata implements MetadataWatchable
{
    public static final String SHOULDER_SIDE_KEY = "shoulder_side";
    public static final String SIDE_KEY = "side";
    public static final String TEXTURE_KEY = "texture";
    public static final String TEXTURE_DETAILS_KEYS = "texture_details";
    public static final String MOJANG_UUID_KEY = "mojang_uuid";
    public static final String OFFSET_KEY = "offset";
    public static final String RGB_KEY = "rgb";
    public static final String SIZE_KEY = "size";
    public static final String BRIGHTNESS_KEY = "brightness";
    public static final String LEFT_VISIBLE_KEY = "left_visible";
    public static final String RIGHT_VISIBLE_KEY = "right_visible";
    public static final String CAN_SLEEP_KEY = "can_sleep";
    public static final String CAN_BLINK_KEY = "can_blink";
    public static final String NAME_TAG_KEY = "nametag";
    private final ItemDetails details;
    private final List<AbstractMetadata<?>> dataList;
    private DepthMap depthMap;
    private final MetadataProperty<FloatVector4> size;
    private final MetadataProperty<OffsetVector> offset;
    private final MetadataProperty<Boolean> rightSide;
    private final MetadataProperty<Boolean> leftVisible;
    private final MetadataProperty<Boolean> rightVisible;
    private final MetadataProperty<Integer> brightness;
    private final MetadataProperty<Boolean> canBlink;
    private final MetadataProperty<Boolean> canSleep;
    private final MetadataProperty<TextureDetails> textureDetails;
    private final MetadataProperty<Color[]> colors;
    private final MetadataProperty<ItemNameTag> nameTag;
    private boolean changed;
    
    public ItemMetadata(final ItemDetails details) {
        this.size = new MetadataProperty<FloatVector4>("size", this);
        this.offset = new MetadataProperty<OffsetVector>("offset", this);
        this.rightSide = new MetadataProperty<Boolean>("side", this);
        this.leftVisible = new MetadataProperty<Boolean>("left_visible", this);
        this.rightVisible = new MetadataProperty<Boolean>("right_visible", this);
        this.brightness = new MetadataProperty<Integer>("brightness", this);
        this.canBlink = new MetadataProperty<Boolean>("can_blink", this);
        this.canSleep = new MetadataProperty<Boolean>("can_sleep", this);
        this.textureDetails = new MetadataProperty<TextureDetails>("texture", this);
        this.colors = new MetadataProperty<Color[]>("rgb", this);
        this.nameTag = new MetadataProperty<ItemNameTag>("nametag", this);
        this.details = details;
        this.dataList = new ArrayList<AbstractMetadata<?>>();
        this.registerDefaultData();
    }
    
    private void registerDefaultData() {
        final Function<String[], BooleanMetadata> metadataFactory = BooleanMetadata::new;
        final Supplier<Boolean> defaultValue = () -> false;
        final MetadataProperty<Boolean> rightSide = this.rightSide;
        Objects.requireNonNull(rightSide);
        this.register((Function<String[], AbstractMetadata<Object>>)metadataFactory, (Supplier<Object>)defaultValue, rightSide::reset, "shoulder_side", "side");
        final Function<String[], TextureDetailsMetadata> metadataFactory2 = TextureDetailsMetadata::new;
        final Supplier<T> defaultValue2 = null;
        final MetadataProperty<TextureDetails> textureDetails = this.textureDetails;
        Objects.requireNonNull(textureDetails);
        this.register((Function<String[], AbstractMetadata<Object>>)metadataFactory2, (Supplier<Object>)defaultValue2, textureDetails::reset, "texture", "mojang_uuid", "texture_details");
        final Function<String[], OffsetVectorMetadata> metadataFactory3 = OffsetVectorMetadata::new;
        final Supplier<OffsetVector> defaultValue3 = OffsetVector::new;
        final MetadataProperty<OffsetVector> offset = this.offset;
        Objects.requireNonNull(offset);
        this.register((Function<String[], AbstractMetadata<Object>>)metadataFactory3, (Supplier<Object>)defaultValue3, offset::reset, "offset");
        final Function<String[], ColorArrayMetadata> metadataFactory4 = ColorArrayMetadata::new;
        final Supplier<Color[]> defaultValue4 = () -> new Color[0];
        final MetadataProperty<Color[]> colors = this.colors;
        Objects.requireNonNull(colors);
        this.register((Function<String[], AbstractMetadata<Object>>)metadataFactory4, (Supplier<Object>)defaultValue4, colors::reset, "rgb");
        final Function<String[], ItemNameTagMetadata> metadataFactory5 = ItemNameTagMetadata::new;
        final Supplier<ItemNameTag> defaultValue5 = () -> ItemNameTag.DEFAULT;
        final MetadataProperty<ItemNameTag> nameTag = this.nameTag;
        Objects.requireNonNull(nameTag);
        this.register((Function<String[], AbstractMetadata<Object>>)metadataFactory5, (Supplier<Object>)defaultValue5, nameTag::reset, "nametag");
        final Function<String[], SizeMetadata> metadataFactory6 = SizeMetadata::new;
        final Supplier<FloatVector4> defaultValue6 = FloatVector4::new;
        final MetadataProperty<FloatVector4> size = this.size;
        Objects.requireNonNull(size);
        this.register((Function<String[], AbstractMetadata<Object>>)metadataFactory6, (Supplier<Object>)defaultValue6, size::reset, "size");
        final Function<String[], BooleanMetadata> metadataFactory7 = BooleanMetadata::new;
        final Supplier<Boolean> defaultValue7 = () -> true;
        final MetadataProperty<Boolean> leftVisible = this.leftVisible;
        Objects.requireNonNull(leftVisible);
        this.register((Function<String[], AbstractMetadata<Object>>)metadataFactory7, (Supplier<Object>)defaultValue7, leftVisible::reset, "left_visible");
        final Function<String[], BooleanMetadata> metadataFactory8 = BooleanMetadata::new;
        final Supplier<Boolean> defaultValue8 = () -> true;
        final MetadataProperty<Boolean> rightVisible = this.rightVisible;
        Objects.requireNonNull(rightVisible);
        this.register((Function<String[], AbstractMetadata<Object>>)metadataFactory8, (Supplier<Object>)defaultValue8, rightVisible::reset, "right_visible");
        final Function<String[], BooleanMetadata> metadataFactory9 = BooleanMetadata::new;
        final Supplier<Boolean> defaultValue9 = () -> true;
        final MetadataProperty<Boolean> canSleep = this.canSleep;
        Objects.requireNonNull(canSleep);
        this.register((Function<String[], AbstractMetadata<Object>>)metadataFactory9, (Supplier<Object>)defaultValue9, canSleep::reset, "can_sleep");
        final Function<String[], BooleanMetadata> metadataFactory10 = BooleanMetadata::new;
        final Supplier<Boolean> defaultValue10 = () -> true;
        final MetadataProperty<Boolean> canBlink = this.canBlink;
        Objects.requireNonNull(canBlink);
        this.register((Function<String[], AbstractMetadata<Object>>)metadataFactory10, (Supplier<Object>)defaultValue10, canBlink::reset, "can_blink");
        final Function<String[], IntMetadata> metadataFactory11 = IntMetadata::new;
        final Supplier<Integer> defaultValue11 = () -> 0;
        final MetadataProperty<Integer> brightness = this.brightness;
        Objects.requireNonNull(brightness);
        this.register((Function<String[], AbstractMetadata<Object>>)metadataFactory11, (Supplier<Object>)defaultValue11, brightness::reset, "brightness");
    }
    
    private <T> void register(final Function<String[], AbstractMetadata<T>> metadataFactory, final Supplier<T> defaultValue, final Consumer<String> invalidator, final String... keys) {
        final AbstractMetadata<T> metadata = metadataFactory.apply(keys);
        if (defaultValue != null) {
            metadata.defaultValue(defaultValue);
        }
        this.register(metadata.withMetadataInvalidator(invalidator));
    }
    
    private void register(final AbstractMetadata<?> metadata) {
        this.dataList.add(metadata.withWatchable(this));
    }
    
    public void invalidate() {
        for (final AbstractMetadata<?> metadata : this.dataList) {
            metadata.invalidate();
        }
    }
    
    public ItemDetails getDetails() {
        return this.details;
    }
    
    public OffsetVector getOffset() {
        return this.offset.get();
    }
    
    public boolean isRightSide() {
        return this.rightSide.get();
    }
    
    public TextureDetails getTextureDetails() {
        return this.textureDetails.get();
    }
    
    public Optional<TextureDetails> texture() {
        return Optional.ofNullable(this.textureDetails.get());
    }
    
    public Color[] getColors() {
        return this.colors.get();
    }
    
    public FloatVector4 getSize() {
        return this.size.get();
    }
    
    public int getBrightness() {
        return this.brightness.get();
    }
    
    public boolean isLeftVisible() {
        return this.leftVisible.get();
    }
    
    public boolean isRightVisible() {
        return this.rightVisible.get();
    }
    
    public boolean canBlink() {
        return this.canBlink.get();
    }
    
    public boolean canSleep() {
        return this.canSleep.get();
    }
    
    public DepthMap getDepthMap() {
        return this.depthMap;
    }
    
    public ItemNameTag getNameTag() {
        return this.nameTag.get();
    }
    
    public void setDepthMap(final DepthMap depthMap) {
        this.depthMap = depthMap;
        this.onUpdate();
    }
    
    public <T> T readValue(final String key) {
        for (final AbstractMetadata abstractMetadata : this.dataList) {
            if (!abstractMetadata.hasKey(key)) {
                continue;
            }
            return abstractMetadata.getValue();
        }
        throw new IllegalStateException(String.format(Locale.ROOT, "No key \"%s\" was found (%s/%s)", key, this.details.getIdentifier(), this.details.getName()));
    }
    
    public List<AbstractMetadata<?>> dataList() {
        return this.dataList;
    }
    
    public boolean isChanged() {
        final boolean changed = this.changed;
        this.changed = false;
        return changed;
    }
    
    public boolean isMojangBound() {
        return this.details.getTextureType() == TextureType.MOJANG_BOUND;
    }
    
    public boolean isUserBound() {
        return this.details.getTextureType() == TextureType.USER_BOUND;
    }
    
    @Override
    public void onUpdate() {
        this.changed = true;
    }
}
