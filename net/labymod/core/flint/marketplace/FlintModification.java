// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.flint.marketplace;

import net.labymod.api.util.version.SemanticVersion;
import java.text.ParseException;
import java.util.Locale;
import net.labymod.core.main.LabyMod;
import net.labymod.api.util.io.web.result.Result;
import java.util.Comparator;
import java.util.ArrayList;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.util.collection.Lists;
import java.util.Objects;
import java.util.HashSet;
import java.util.List;
import net.labymod.api.util.io.web.result.ResultCallback;
import java.util.Optional;
import java.util.function.Consumer;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.version.VersionMultiRange;
import net.labymod.api.util.version.VersionRange;
import net.labymod.api.models.version.VersionCompatibility;
import net.labymod.api.util.I18n;
import net.labymod.api.BuildData;
import net.labymod.api.Laby;
import net.labymod.api.util.markdown.MarkdownDocument;
import com.google.gson.annotations.SerializedName;
import net.labymod.api.models.addon.info.dependency.AddonDependency;
import net.labymod.api.models.addon.info.AddonMeta;
import java.util.Set;
import java.text.SimpleDateFormat;
import net.labymod.core.flint.downloader.FlintDownloader;
import net.labymod.core.flint.FlintController;

public class FlintModification
{
    private static final FlintController CONTROLLER;
    private static final FlintDownloader DOWNLOADER;
    private static final SimpleDateFormat DATE_FORMAT;
    protected Set<FlintTag> flintTags;
    protected Image icon;
    protected Image thumbnail;
    protected Image[] sliderImages;
    protected int id;
    protected String namespace;
    protected String name;
    protected String changelog;
    protected String licence;
    protected String author;
    protected boolean featured;
    protected boolean verified;
    protected int organization;
    protected int version;
    protected int downloads;
    protected int ranking;
    protected int releases;
    protected int[] tags;
    protected Rating rating;
    protected AddonMeta[] meta;
    protected String[] permissions;
    protected AddonDependency[] dependencies;
    @SerializedName("short_description")
    protected String shortDescription;
    @SerializedName("flint_url")
    protected String flintUrl;
    @SerializedName("version_string")
    protected String versionString;
    @SerializedName("download_string")
    protected String downloadsString;
    @SerializedName("brand_images")
    protected Image[] brandImages;
    @SerializedName("last_update")
    protected long lastUpdate;
    @SerializedName("required_labymod_build")
    protected int requiredLabyModBuild;
    @SerializedName("file_hash")
    protected String fileHash;
    private transient String niceVersionString;
    private transient FlintPermission[] flintPermissions;
    private transient MarkdownDocument description;
    private transient boolean compatible;
    
    public Rating getRating() {
        return this.rating;
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getShortDescription() {
        return this.shortDescription;
    }
    
    public String getFlintUrl() {
        return this.flintUrl;
    }
    
    public String getFileHash() {
        return this.fileHash;
    }
    
    public boolean isBuildCompatible() {
        return Laby.labyAPI().labyModLoader().isLabyModDevelopmentEnvironment() || this.requiredLabyModBuild <= BuildData.getBuildNumber();
    }
    
    public boolean isCompatible() {
        return this.compatible;
    }
    
    public void setCompatible(final boolean compatible) {
        this.compatible = compatible;
    }
    
    public String getVersionString() {
        if (this.niceVersionString != null || this.versionString == null) {
            return (this.niceVersionString != null) ? this.niceVersionString : this.versionString;
        }
        if (this.versionString.equals("*")) {
            final String translation = I18n.getTranslation("labymod.addons.allVersions", new Object[0]);
            return this.niceVersionString = ((translation == null) ? this.versionString : translation);
        }
        final String[] ranges = this.versionString.split(",");
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ranges.length; ++i) {
            if (i != 0) {
                stringBuilder.append(", ");
            }
            final String range = ranges[i];
            final String[] versions = range.split("<");
            if (versions.length == 1) {
                stringBuilder.append(versions[0]);
            }
            else {
                if (versions[0].equals("*")) {
                    stringBuilder.append("1.8.9");
                }
                else {
                    stringBuilder.append(versions[0]);
                }
                if (versions[1].equals("*")) {
                    stringBuilder.append('+');
                }
                else {
                    stringBuilder.append(" - ").append(versions[1]);
                }
            }
        }
        return this.niceVersionString = stringBuilder.toString();
    }
    
    public VersionCompatibility getVersionCompatibility() {
        return (this.versionString == null) ? new VersionRange("*") : new VersionMultiRange(this.versionString);
    }
    
    public String getChangelog() {
        return this.changelog;
    }
    
    public String getLicence() {
        if (this.licence == null) {
            return null;
        }
        return this.licence.replace("-", " ");
    }
    
    public String getDownloadsString() {
        return (this.downloadsString == null) ? "?" : this.downloadsString;
    }
    
    public int getReleases() {
        return this.releases;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getVersion() {
        return this.version;
    }
    
    public int getDownloads() {
        return this.downloads;
    }
    
    public boolean isVerified() {
        return this.verified;
    }
    
    public boolean isFeatured() {
        return this.featured;
    }
    
    public long getLastUpdate() {
        return this.lastUpdate * 1000L;
    }
    
    public void forEachAddonDependency(final Version version, final Consumer<AddonDependency> consumer) {
        if (this.dependencies == null) {
            return;
        }
        for (final AddonDependency dependency : this.dependencies) {
            final Optional<VersionCompatibility> compatability = dependency.getCompatability();
            if (!compatability.isPresent() || compatability.get().isCompatible(version)) {
                consumer.accept(dependency);
            }
        }
    }
    
    @Deprecated
    public AddonDependency[] getDependencies() {
        if (this.dependencies == null) {
            this.dependencies = new AddonDependency[0];
        }
        return this.dependencies;
    }
    
    public Optional<List<Review>> getOrLoadReviews(final ResultCallback<List<Review>> consumer) {
        return FlintModification.CONTROLLER.getOrLoadReviews(this.namespace, consumer);
    }
    
    public Optional<List<Changelog>> getOrLoadChangelog(final ResultCallback<List<Changelog>> consumer) {
        return FlintModification.CONTROLLER.getOrLoadChangelog(this.namespace, consumer);
    }
    
    public MarkdownDocument getOrLoadDescription(final ResultCallback<MarkdownDocument> consumer) {
        if (this.description == null) {
            FlintModification.CONTROLLER.loadDescription(this.namespace, result -> {
                if (result.hasException()) {
                    consumer.acceptException(result.exception());
                }
                else if (result.isEmpty()) {
                    consumer.acceptRaw(null);
                }
                else {
                    consumer.acceptRaw(this.description = Laby.references().markdownParser().parse((String)result.get()));
                }
                return;
            });
        }
        return this.description;
    }
    
    public Set<FlintTag> getTags() {
        if (this.flintTags == null) {
            final Set<FlintTag> flintTags = new HashSet<FlintTag>();
            if (this.tags != null) {
                for (final int tag : this.tags) {
                    final Optional<FlintTag> tag2;
                    final Optional<FlintTag> optionalFlintTag = tag2 = FlintModification.CONTROLLER.getTag(tag);
                    final Set<FlintTag> obj = flintTags;
                    Objects.requireNonNull((HashSet)obj);
                    tag2.ifPresent(obj::add);
                }
            }
            this.flintTags = flintTags;
        }
        return this.flintTags;
    }
    
    public Image getIcon() {
        if (this.icon == null && this.brandImages != null) {
            for (final Image brandImage : this.brandImages) {
                if (brandImage.getType() == ImageType.ICON) {
                    this.icon = brandImage;
                    break;
                }
            }
        }
        return this.icon;
    }
    
    public Image getThumbnail() {
        if (this.thumbnail == null && this.brandImages != null) {
            for (final Image brandImage : this.brandImages) {
                if (brandImage.getType() == ImageType.THUMBNAIL) {
                    this.thumbnail = brandImage;
                    break;
                }
            }
        }
        return this.thumbnail;
    }
    
    public Image[] getSliderImages() {
        if (this.sliderImages == null && this.brandImages != null) {
            final List<Image> sliderImages = (List<Image>)Lists.newArrayList();
            for (final Image brandImage : this.brandImages) {
                if (brandImage.getType() == ImageType.IMAGE) {
                    sliderImages.add(brandImage);
                }
            }
            this.sliderImages = sliderImages.toArray(new Image[0]);
        }
        return this.sliderImages;
    }
    
    public Image[] getBrandImages() {
        return this.brandImages;
    }
    
    public String getAuthor() {
        if (this.author == null) {
            final FlintOrganization organization = this.getOrganization(null);
            if (organization != null) {
                this.author = organization.getDisplayName();
            }
            return this.author;
        }
        return this.author;
    }
    
    public FlintOrganization getOrganization(final ResultCallback<FlintOrganization> result) {
        return FlintModification.CONTROLLER.getOrganization(this.organization, result);
    }
    
    public boolean isOrganizationLabyMod() {
        return this.organization == 1;
    }
    
    public FlintModification loadModification(final ResultCallback<FlintModification> result) {
        return FlintModification.CONTROLLER.loadModification(this.namespace, result);
    }
    
    public Optional<LoadedAddon> getAsLoadedAddon() {
        return DefaultAddonService.getInstance().getAddon(this.namespace);
    }
    
    public boolean isInstalled() {
        return this.getAsLoadedAddon().isPresent();
    }
    
    public boolean isDeleted() {
        return FlintModification.DOWNLOADER.isScheduledForRemoval(this.namespace);
    }
    
    public String[] getRawPermissions() {
        return this.permissions;
    }
    
    public FlintPermission[] getPermissions() {
        if (this.flintPermissions == null) {
            final ArrayList<FlintPermission> permissions = new ArrayList<FlintPermission>(this.permissions.length);
            for (final String key : this.permissions) {
                permissions.add(FlintModification.CONTROLLER.getPermission(key));
            }
            permissions.sort(Comparator.comparing(permission -> !permission.isCritical()));
            this.flintPermissions = permissions.toArray(new FlintPermission[0]);
        }
        return this.flintPermissions;
    }
    
    public int getRanking() {
        return this.ranking;
    }
    
    public void setRanking(final int ranking) {
        this.ranking = ranking;
    }
    
    public boolean hasMeta(final AddonMeta addonMeta) {
        if (this.meta == null) {
            return false;
        }
        for (final AddonMeta meta : this.meta) {
            if (meta == addonMeta) {
                return true;
            }
        }
        return false;
    }
    
    static {
        CONTROLLER = LabyMod.references().flintController();
        DOWNLOADER = LabyMod.references().flintDownloader();
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    }
    
    public enum ImageType
    {
        ICON(256), 
        THUMBNAIL(1280), 
        IMAGE(1280, 1920);
        
        private static final ImageType[] VALUES;
        private final int smallSize;
        private final int fullSize;
        
        private ImageType(final int smallSize, final int fullSize) {
            this.smallSize = smallSize;
            this.fullSize = fullSize;
        }
        
        private ImageType(final int size) {
            this(size, size);
        }
        
        public int getSmallSize() {
            return this.smallSize;
        }
        
        public int getFullSize() {
            return this.fullSize;
        }
        
        public static ImageType of(final String name) {
            for (final ImageType imageType : ImageType.VALUES) {
                if (imageType.name().equalsIgnoreCase(name)) {
                    return imageType;
                }
            }
            return null;
        }
        
        static {
            VALUES = values();
        }
    }
    
    public static class Rating
    {
        private final int count;
        private final double rating;
        
        public Rating(final int count, final double rating) {
            this.count = count;
            this.rating = rating;
        }
        
        public int getCount() {
            return this.count;
        }
        
        public double getRating() {
            return this.rating;
        }
    }
    
    public static class Image
    {
        private String type;
        private String hash;
        
        public Image() {
        }
        
        public Image(final String type, final String hash) {
            this.type = type;
            this.hash = hash;
        }
        
        public ImageType getType() {
            return ImageType.of(this.type);
        }
        
        public String getIconUrl() {
            return String.format(Locale.ROOT, "https://flintmc.net/brand/modification/%s.png", this.hash);
        }
    }
    
    public static class Review
    {
        private int rating;
        private String comment;
        private FlintUser user;
        @SerializedName("added_at")
        private String addedAt;
        private long addedAtLong;
        
        public Review() {
            this.addedAtLong = -1L;
        }
        
        public int getRating() {
            return this.rating;
        }
        
        public String getComment() {
            return this.comment;
        }
        
        public FlintUser user() {
            return this.user;
        }
        
        public String getAddedAtString() {
            return this.addedAt;
        }
        
        public long getAddedAt() {
            if (this.addedAtLong == -1L) {
                try {
                    this.addedAtLong = FlintModification.DATE_FORMAT.parse(this.addedAt).getTime();
                }
                catch (final ParseException e) {
                    e.printStackTrace();
                }
            }
            return this.addedAtLong;
        }
    }
    
    public static class Changelog
    {
        private String changelog;
        private String release;
        @SerializedName("added_at")
        private String addedAt;
        private long addedAtLong;
        private VersionCompatibility releaseVersion;
        
        public Changelog() {
            this.addedAtLong = -1L;
        }
        
        public String getChanges() {
            return this.changelog;
        }
        
        public String getRelease() {
            return this.release;
        }
        
        public VersionCompatibility releaseVersion() {
            if (this.releaseVersion == null) {
                this.releaseVersion = new SemanticVersion(this.release);
            }
            return this.releaseVersion;
        }
        
        public String getAddedAtString() {
            return this.addedAt;
        }
        
        public long getAddedAt() {
            if (this.addedAtLong == -1L) {
                try {
                    this.addedAtLong = FlintModification.DATE_FORMAT.parse(this.addedAt).getTime();
                }
                catch (final ParseException e) {
                    e.printStackTrace();
                }
            }
            return this.addedAtLong;
        }
    }
}
