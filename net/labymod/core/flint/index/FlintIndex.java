// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.flint.index;

import java.util.function.Function;
import java.util.Comparator;
import java.util.Collection;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.labymod.core.flint.marketplace.FlintTag;
import java.util.Locale;
import net.labymod.core.flint.FlintSortBy;
import net.labymod.api.models.addon.info.dependency.AddonDependency;
import net.labymod.api.models.addon.info.AddonMeta;
import net.labymod.core.flint.marketplace.FlintOrganization;
import net.labymod.api.util.JsonFileCache;
import java.util.Optional;
import net.labymod.api.models.version.VersionCompatibility;
import com.google.gson.JsonObject;
import net.labymod.api.util.version.VersionMultiRange;
import com.google.gson.JsonElement;
import net.labymod.api.util.io.web.result.Result;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.util.io.web.result.ResultCallback;
import java.util.Objects;
import com.google.gson.JsonArray;
import net.labymod.api.util.collection.Lists;
import net.labymod.api.Laby;
import net.labymod.core.flint.marketplace.FlintModification;
import java.util.List;
import net.labymod.api.models.version.Version;
import net.labymod.core.flint.FlintController;

public class FlintIndex
{
    private final FlintIndexLoader indexLoader;
    private final FlintController flintController;
    private final IndexFilter indexFilter;
    private final Version version;
    private List<FlintModification> indexModifications;
    
    public FlintIndex(final FlintController flintController) {
        this.flintController = flintController;
        this.version = Laby.labyAPI().labyModLoader().version();
        this.indexLoader = FlintIndexLoader.getInstance();
        this.indexFilter = new IndexFilter(this);
        this.indexModifications = (List<FlintModification>)Lists.newArrayList();
    }
    
    public void setupIndex() {
        this.indexLoader.addCallback(result -> {
            if (result.isPresent()) {
                this.indexModifications = this.convertToFlintModifications((JsonArray)result.get());
            }
            return;
        });
        Task.builder(() -> this.indexLoader.getIndexFileCache().readLastModifiedDateFromHeader("Last-Modified", FlintIndexLoader.DATE_FORMAT, success -> {
            this.indexLoader.getIndexFileCache();
            final FlintIndexLoader indexLoader = this.indexLoader;
            Objects.requireNonNull(indexLoader);
            final Object o;
            final boolean b;
            ((JsonFileCache<JsonArray>)o).update(b, (ResultCallback<JsonArray>)indexLoader::handleResult);
        })).delay(1L, TimeUnit.HOURS).repeat(2L, TimeUnit.HOURS).build().execute();
    }
    
    public IndexFilter filter() {
        return this.indexFilter;
    }
    
    public List<FlintModification> getIndexModifications() {
        return this.indexModifications;
    }
    
    public FlintModification getModification(final String namespace) {
        for (final FlintModification indexModification : this.indexModifications) {
            if (indexModification.getNamespace().equals(namespace)) {
                return indexModification;
            }
        }
        return null;
    }
    
    public Result<JsonArray> getIndex() {
        return this.indexLoader.getLatestIndex();
    }
    
    public List<FlintModification> convertToFlintModifications(final JsonArray jsonArray) {
        final List<FlintModification> modifications = (List<FlintModification>)Lists.newArrayList();
        for (final JsonElement jsonElement : jsonArray) {
            final JsonObject jsonObject = jsonElement.getAsJsonObject();
            final String type = jsonObject.has("type") ? jsonObject.get("type").getAsString() : "addon";
            if (!type.equals("addon")) {
                continue;
            }
            final String versionString = jsonObject.has("version_string") ? jsonObject.get("version_string").getAsString() : "*";
            final VersionCompatibility compatibility = new VersionMultiRange(versionString);
            final boolean compatible = compatibility.isCompatible(this.version);
            final String namespace = jsonObject.get("namespace").getAsString();
            final Optional<FlintModification> cachedModification = this.flintController.getCachedModification(namespace);
            final int ranking = jsonObject.get("ranking").getAsInt();
            if (cachedModification.isPresent()) {
                final FlintModification modification = cachedModification.get();
                modification.setCompatible(compatible);
                modification.setRanking(ranking);
                modifications.add(modification);
            }
            else {
                final FlintIndexModification modification2 = new FlintIndexModification(jsonObject);
                modification2.setCompatible(compatible);
                modifications.add(modification2);
            }
        }
        return modifications;
    }
    
    public static class FlintIndexModification extends FlintModification
    {
        private static final Rating INDEX_RATING;
        private final FlintOrganization indexOrganization;
        
        protected FlintIndexModification(final JsonObject jsonObject) {
            this.name = jsonObject.get("name").getAsString();
            this.namespace = jsonObject.get("namespace").getAsString();
            this.author = (jsonObject.has("author") ? jsonObject.get("author").getAsString() : "Unknown");
            this.shortDescription = jsonObject.get("short_description").getAsString();
            this.ranking = jsonObject.get("ranking").getAsInt();
            if (jsonObject.has("file_hash") && !jsonObject.get("file_hash").isJsonNull()) {
                this.fileHash = jsonObject.get("file_hash").getAsString();
            }
            this.indexOrganization = new FlintOrganization(jsonObject.get("organization_name").getAsString());
            this.requiredLabyModBuild = (jsonObject.has("required_labymod_build") ? jsonObject.get("required_labymod_build").getAsInt() : 0);
            this.tags = (int[])FlintController.GSON.fromJson((JsonElement)jsonObject.get("tags").getAsJsonArray(), (Class)int[].class);
            this.meta = (AddonMeta[])FlintController.GSON.fromJson((JsonElement)jsonObject.get("meta").getAsJsonArray(), (Class)AddonMeta[].class);
            if (jsonObject.has("dependencies")) {
                this.dependencies = (AddonDependency[])FlintController.GSON.fromJson((JsonElement)jsonObject.get("dependencies").getAsJsonArray(), (Class)AddonDependency[].class);
            }
            if (jsonObject.has("rating")) {
                this.rating = (Rating)FlintController.GSON.fromJson(jsonObject.get("rating"), (Class)Rating.class);
            }
            if (jsonObject.has("icon_hash")) {
                this.icon = new Image(ImageType.ICON.name(), jsonObject.get("icon_hash").getAsString());
            }
            if (jsonObject.has("thumbnail_hash")) {
                this.icon = new Image(ImageType.THUMBNAIL.name(), jsonObject.get("thumbnail_hash").getAsString());
            }
        }
        
        @Override
        public FlintOrganization getOrganization(final ResultCallback<FlintOrganization> result) {
            return this.indexOrganization;
        }
        
        @Override
        public Rating getRating() {
            return (this.rating == null) ? FlintIndexModification.INDEX_RATING : this.rating;
        }
        
        static {
            INDEX_RATING = new Rating(0, 0.0);
        }
    }
    
    public static class IndexFilter
    {
        private final FlintIndex index;
        private final List<String> hiddenAddons;
        
        private IndexFilter(final FlintIndex index) {
            this.index = index;
            final String hiddenAddons = System.getProperty("net.labymod.hide-addons");
            if (hiddenAddons != null) {
                this.hiddenAddons = Lists.newArrayList(hiddenAddons.split(","));
            }
            else {
                this.hiddenAddons = (List<String>)Lists.newArrayList();
            }
        }
        
        public Optional<FlintModification> namespace(final String namespace) {
            for (final FlintModification modification : this.getModifications()) {
                if (modification.getNamespace().equals(namespace)) {
                    return Optional.of(modification);
                }
            }
            return Optional.empty();
        }
        
        public List<FlintModification> search(final FlintSortBy sortBy, final String search) {
            final String query = (search == null) ? null : search.toLowerCase(Locale.ROOT).replace(" ", "");
            if (query == null || query.isEmpty()) {
                return (List<FlintModification>)Lists.newArrayList();
            }
            final String[] queries = search.toLowerCase(Locale.ROOT).split(" ");
            final List<FlintModification> searchedList = (List<FlintModification>)Lists.newArrayList();
            for (final FlintModification modification : this.getModifications()) {
                if (this.queryMatches(searchedList, modification, query)) {
                    searchedList.add(modification);
                }
                else {
                    if (queries.length == 1) {
                        continue;
                    }
                    boolean matches = true;
                    for (final String advancedQuery : queries) {
                        if (!this.queryMatches(searchedList, modification, advancedQuery)) {
                            matches = false;
                            break;
                        }
                    }
                    if (!matches) {
                        continue;
                    }
                    searchedList.add(modification);
                }
            }
            return this.sortBy(sortBy, searchedList);
        }
        
        private boolean queryMatches(final List<FlintModification> list, final FlintModification modification, final String query) {
            if (this.matchQuery(modification.getName(), query) || this.matchQuery(modification.getShortDescription(), query)) {
                return true;
            }
            final String authorName = modification.getAuthor();
            return authorName != null && this.matchQuery(authorName, query);
        }
        
        public List<FlintModification> tag(final FlintSortBy sortBy, final FlintTag flintTag) {
            return this.tag(sortBy, IntSet.of(flintTag.getId()), true);
        }
        
        public List<FlintModification> tag(final FlintSortBy sortBy, final IntSet flintTags) {
            return this.tag(sortBy, flintTags, false);
        }
        
        private List<FlintModification> tag(final FlintSortBy sortBy, final IntSet flintTags, final boolean checkForParent) {
            final List<FlintModification> filteredList = (List<FlintModification>)Lists.newArrayList();
            if (flintTags.isEmpty()) {
                return filteredList;
            }
            for (final FlintModification modification : this.getModifications()) {
                for (final FlintTag tag : modification.getTags()) {
                    if (((checkForParent && flintTags.contains(tag.getParentCategory())) || flintTags.contains(tag.getId())) && !filteredList.contains(modification)) {
                        filteredList.add(modification);
                    }
                }
            }
            return this.sortBy(sortBy, filteredList);
        }
        
        public List<FlintModification> getModifications() {
            for (int i = 0; i < this.index.getIndexModifications().size(); ++i) {
                final FlintModification modification = this.index.getIndexModifications().get(i);
                if (modification instanceof FlintIndexModification) {
                    final Optional<FlintModification> cachedModification = this.index.flintController.getCachedModification(modification.getNamespace());
                    if (cachedModification.isPresent()) {
                        final FlintModification cachedModificationValue = cachedModification.get();
                        cachedModificationValue.setRanking(modification.getRanking());
                        this.index.getIndexModifications().remove(modification);
                        this.index.getIndexModifications().add(i, cachedModificationValue);
                    }
                }
            }
            return this.index.getIndexModifications();
        }
        
        public boolean isHidden(final String namespace) {
            return this.hiddenAddons.contains(namespace);
        }
        
        public boolean isHidden(final FlintModification modification) {
            return this.isHidden(modification.getNamespace());
        }
        
        public List<FlintModification> sortBy(final FlintSortBy sortBy) {
            return this.sortBy(sortBy, null);
        }
        
        private List<FlintModification> sortBy(final FlintSortBy sortBy, final List<FlintModification> modifications) {
            final List<FlintModification> sortedList = (modifications == null) ? Lists.newArrayList(this.getModifications()) : modifications;
            switch (sortBy) {
                case NAME_AZ: {
                    sortedList.sort(Comparator.comparing((Function<? super FlintModification, ? extends Comparable>)FlintModification::getName));
                    break;
                }
                case NAME_ZA: {
                    sortedList.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
                    break;
                }
                case TRENDING: {
                    sortedList.sort(Comparator.comparingInt(FlintModification::getRanking));
                    sortedList.sort(Comparator.comparing(flintModification -> !flintModification.isCompatible() || !flintModification.isBuildCompatible()));
                    break;
                }
                case DOWNLOADS: {
                    sortedList.sort((o1, o2) -> Integer.compare(o2.getDownloads(), o1.getDownloads()));
                    break;
                }
                case RATING: {
                    sortedList.sort((o1, o2) -> {
                        final FlintModification.Rating first = o1.getRating();
                        final FlintModification.Rating second = o2.getRating();
                        return Double.compare(second.getRating() * second.getCount(), first.getRating() * first.getCount());
                    });
                    break;
                }
                case LATEST: {
                    sortedList.sort((o1, o2) -> Long.compare(o2.getLastUpdate(), o1.getLastUpdate()));
                    break;
                }
                case OLDEST: {
                    sortedList.sort(Comparator.comparingLong(FlintModification::getLastUpdate));
                    break;
                }
            }
            return sortedList;
        }
        
        boolean matchQuery(final String s1, final String s2) {
            return s1.toLowerCase(Locale.ROOT).replace(" ", "").contains(s2);
        }
    }
}
