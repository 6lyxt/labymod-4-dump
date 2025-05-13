// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.thirdparty.discord;

import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.event.Event;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.time.Instant;

public final class DiscordActivity
{
    private final String namespace;
    private final Asset largeAsset;
    private final Asset smallAsset;
    private final String details;
    private final String state;
    private final Instant startTime;
    private final Instant endTime;
    
    private DiscordActivity(final Builder builder) {
        this.namespace = builder.namespace;
        this.largeAsset = builder.largeAsset;
        this.smallAsset = builder.smallAsset;
        this.details = builder.details;
        this.state = builder.state;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
    }
    
    public static Builder builder(@NotNull final Object holder) {
        return new Builder(holder);
    }
    
    public static Builder builder(@NotNull final Object holder, @NotNull final DiscordActivity activity) {
        return new Builder(holder, activity);
    }
    
    @NotNull
    public String getNamespace() {
        return this.namespace;
    }
    
    @Nullable
    public Asset getLargeAsset() {
        return this.largeAsset;
    }
    
    @Nullable
    public Asset getSmallAsset() {
        return this.smallAsset;
    }
    
    @Nullable
    public String getDetails() {
        return this.details;
    }
    
    @Nullable
    public String getState() {
        return this.state;
    }
    
    @Nullable
    public Instant getStartTime() {
        return this.startTime;
    }
    
    @Nullable
    public Instant getEndTime() {
        return this.endTime;
    }
    
    public boolean isCustom() {
        return !this.namespace.equals("labymod");
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || (this.getClass() != o.getClass() && o.getClass() != Builder.class)) {
            return false;
        }
        if (o.getClass() == Builder.class) {
            final Builder that = (Builder)o;
            return Objects.equals(this.largeAsset, that.largeAsset) && Objects.equals(this.smallAsset, that.smallAsset) && Objects.equals(this.details, that.details) && Objects.equals(this.state, that.state) && Objects.equals(this.startTime, that.startTime) && Objects.equals(this.endTime, that.endTime);
        }
        final DiscordActivity that2 = (DiscordActivity)o;
        return Objects.equals(this.largeAsset, that2.largeAsset) && Objects.equals(this.smallAsset, that2.smallAsset) && Objects.equals(this.details, that2.details) && Objects.equals(this.state, that2.state) && Objects.equals(this.startTime, that2.startTime) && Objects.equals(this.endTime, that2.endTime);
    }
    
    @Override
    public int hashCode() {
        int result = (this.largeAsset != null) ? this.largeAsset.hashCode() : 0;
        result = 31 * result + ((this.smallAsset != null) ? this.smallAsset.hashCode() : 0);
        result = 31 * result + ((this.details != null) ? this.details.hashCode() : 0);
        result = 31 * result + ((this.state != null) ? this.state.hashCode() : 0);
        result = 31 * result + ((this.startTime != null) ? this.startTime.hashCode() : 0);
        result = 31 * result + ((this.endTime != null) ? this.endTime.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "DiscordActivity{namespace='" + this.namespace + "', largeAsset=" + String.valueOf(this.largeAsset) + ", smallAsset=" + String.valueOf(this.smallAsset) + ", details='" + this.details + "', state='" + this.state + "', startTimestamp=" + String.valueOf(this.startTime) + ", endTimestamp=" + String.valueOf(this.endTime);
    }
    
    public static final class Asset
    {
        private boolean weeklyCached;
        private String key;
        private String text;
        
        private Asset() {
        }
        
        private Asset(@NotNull final String key, @Nullable final String text, final boolean weeklyCached) {
            Objects.requireNonNull(key, "Asset key cannot be null!");
            this.key = key;
            this.text = text;
            this.weeklyCached = weeklyCached;
        }
        
        public static Asset of(@NotNull final String key, @Nullable final String text) {
            return new Asset(key, text, false);
        }
        
        public static Asset ofWeeklyCached(@NotNull final String key, @Nullable final String text) {
            return new Asset(key, text, true);
        }
        
        @NotNull
        public String getKey() {
            return (this.key == null) ? "" : this.key;
        }
        
        @NotNull
        public String getText() {
            return (this.text == null) ? "" : this.text;
        }
        
        public boolean isWeeklyCached() {
            return this.weeklyCached;
        }
        
        public boolean isValid() {
            return this.key != null && !this.key.isEmpty();
        }
        
        Asset copy() {
            final Asset asset = new Asset();
            asset.key = this.key;
            asset.text = this.text;
            asset.weeklyCached = this.weeklyCached;
            return asset;
        }
        
        @Override
        public boolean equals(final Object object) {
            if (this == object) {
                return true;
            }
            if (object instanceof final Asset asset) {
                return this.weeklyCached == asset.weeklyCached && Objects.equals(this.key, asset.key) && Objects.equals(this.text, asset.text);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.weeklyCached, this.key, this.text);
        }
        
        @Override
        public String toString() {
            return "Asset{weeklyCached=" + this.weeklyCached + ", key='" + this.key + "', text='" + this.text + "'}";
        }
    }
    
    public static final class Builder
    {
        private final String namespace;
        private Asset largeAsset;
        private Asset smallAsset;
        private String details;
        private String state;
        private Instant startTime;
        private Instant endTime;
        
        private Builder() {
            this.namespace = "labymod";
        }
        
        private Builder(@NotNull final Object holder) {
            Objects.requireNonNull(holder, "Holder cannot be null!");
            if (holder instanceof Event) {
                throw new UnsupportedOperationException("Cannot create a activity builder for an event!");
            }
            this.namespace = Laby.labyAPI().getNamespace(holder);
        }
        
        private Builder(@NotNull final Object holder, @NotNull final DiscordActivity activity) {
            this(holder);
            Objects.requireNonNull(activity, "Activity cannot be null!");
            this.largeAsset = activity.largeAsset;
            this.smallAsset = activity.smallAsset;
            this.details = activity.details;
            this.state = activity.state;
            this.startTime = activity.startTime;
            this.endTime = activity.endTime;
        }
        
        public Builder start(@Nullable final Instant startTime) {
            this.startTime = startTime;
            this.endTime = null;
            return this;
        }
        
        public Builder start() {
            return this.start(Instant.now());
        }
        
        public Builder end(@Nullable final Instant endTime) {
            this.endTime = endTime;
            this.startTime = null;
            return this;
        }
        
        public Builder end() {
            return this.end(Instant.now());
        }
        
        public Builder details(@Nullable final String details) {
            this.details = details;
            return this;
        }
        
        public Builder state(@Nullable final String state) {
            this.state = state;
            return this;
        }
        
        public Builder largeAsset(@Nullable final Asset largeAsset) {
            if (largeAsset != null && this.smallAsset != null) {
                throw new IllegalStateException("Cannot set large asset while small asset is set! (You can only set one asset)");
            }
            this.largeAsset = largeAsset;
            return this;
        }
        
        public Builder largeAssetKey(@NotNull final String largeAssetKey) {
            Objects.requireNonNull(largeAssetKey, "Large asset key cannot be null!");
            return this.largeAsset(asset -> asset.key = largeAssetKey);
        }
        
        public Builder largeAssetText(@Nullable final String largeAssetText) {
            return this.largeAsset(asset -> asset.text = largeAssetText);
        }
        
        public Builder largeAssetWeeklyCached(final boolean weeklyCached) {
            return this.largeAsset(asset -> asset.weeklyCached = weeklyCached);
        }
        
        public Builder largeAssetWeeklyCached() {
            return this.largeAssetWeeklyCached(true);
        }
        
        public Builder smallAsset(final Asset smallAsset) {
            if (smallAsset != null && this.largeAsset != null) {
                throw new IllegalStateException("Cannot set small asset while large asset is set! (You can only set one asset)");
            }
            this.smallAsset = smallAsset;
            return this;
        }
        
        public Builder smallAssetKey(@NotNull final String smallAssetKey) {
            Objects.requireNonNull(smallAssetKey, "Small asset key cannot be null!");
            return this.smallAsset(asset -> asset.key = smallAssetKey);
        }
        
        public Builder smallAssetText(@Nullable final String smallAssetText) {
            return this.smallAsset(asset -> asset.text = smallAssetText);
        }
        
        public Builder smallAssetWeeklyCached(final boolean weeklyCached) {
            return this.smallAsset(asset -> asset.weeklyCached = weeklyCached);
        }
        
        public Builder smallAssetWeeklyCached() {
            return this.smallAssetWeeklyCached(true);
        }
        
        @NotNull
        public DiscordActivity build() {
            return new DiscordActivity(this);
        }
        
        private Asset assetOrNew(@Nullable final Asset asset) {
            return (asset != null) ? asset : new Asset();
        }
        
        private Builder smallAsset(final Consumer<Asset> consumer) {
            final Asset asset = this.assetOrNew(this.smallAsset);
            consumer.accept(asset);
            return this.smallAsset(asset);
        }
        
        private Builder largeAsset(final Consumer<Asset> consumer) {
            final Asset asset = this.assetOrNew(this.largeAsset);
            consumer.accept(asset);
            return this.largeAsset(asset);
        }
        
        @NotNull
        public Builder copy() {
            final Builder builder = new Builder(this.namespace);
            builder.details = this.details;
            builder.state = this.state;
            builder.startTime = this.startTime;
            builder.endTime = this.endTime;
            if (this.largeAsset != null) {
                builder.largeAsset = this.largeAsset.copy();
            }
            if (this.smallAsset != null) {
                builder.smallAsset = this.smallAsset.copy();
            }
            return builder;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || (this.getClass() != o.getClass() && o.getClass() != DiscordActivity.class)) {
                return false;
            }
            if (o.getClass() == DiscordActivity.class) {
                final DiscordActivity that = (DiscordActivity)o;
                return Objects.equals(this.largeAsset, that.largeAsset) && Objects.equals(this.smallAsset, that.smallAsset) && Objects.equals(this.details, that.details) && Objects.equals(this.state, that.state) && Objects.equals(this.startTime, that.startTime) && Objects.equals(this.endTime, that.endTime);
            }
            final Builder that2 = (Builder)o;
            return Objects.equals(this.largeAsset, that2.largeAsset) && Objects.equals(this.smallAsset, that2.smallAsset) && Objects.equals(this.details, that2.details) && Objects.equals(this.state, that2.state) && Objects.equals(this.startTime, that2.startTime) && Objects.equals(this.endTime, that2.endTime);
        }
        
        @Override
        public int hashCode() {
            int result = (this.largeAsset != null) ? this.largeAsset.hashCode() : 0;
            result = 31 * result + ((this.smallAsset != null) ? this.smallAsset.hashCode() : 0);
            result = 31 * result + ((this.details != null) ? this.details.hashCode() : 0);
            result = 31 * result + ((this.state != null) ? this.state.hashCode() : 0);
            result = 31 * result + ((this.startTime != null) ? this.startTime.hashCode() : 0);
            result = 31 * result + ((this.endTime != null) ? this.endTime.hashCode() : 0);
            return result;
        }
        
        @Override
        public String toString() {
            return "Builder{namespace='" + this.namespace + "', largeAsset=" + String.valueOf(this.largeAsset) + ", smallAsset=" + String.valueOf(this.smallAsset) + ", details='" + this.details + "', state='" + this.state + "', startTime=" + String.valueOf(this.startTime) + ", endTime=" + String.valueOf(this.endTime);
        }
    }
}
