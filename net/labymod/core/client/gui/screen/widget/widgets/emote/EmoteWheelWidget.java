// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.emote;

import net.labymod.core.main.user.shop.emote.model.DailyEmoteFlat;
import net.labymod.core.main.user.GameUserData;
import java.util.Collection;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.DefaultGameUserService;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.UUID;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.function.Function;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.ints.IntList;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.util.CharSequences;
import net.labymod.api.client.gui.screen.Parent;
import java.util.ArrayList;
import java.util.function.Supplier;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import java.util.List;
import java.util.function.IntSupplier;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;

@AutoWidget
@Link("widget/emote-wheel.lss")
public class EmoteWheelWidget extends WheelWidget
{
    @Deprecated(forRemoval = true, since = "4.1.18")
    public static final int SEGMENTS = 6;
    public static final int DAILY_EMOTES_PAGE = -1;
    private static final EmoteService EMOTE_SERVICE;
    private final IntSupplier pageSupplier;
    private final IntSupplier segmentCountSupplier;
    private final List<EmoteItem> emotes;
    private Supplier<CharSequence> querySupplier;
    private SegmentSupplier segmentSupplier;
    
    public EmoteWheelWidget(final IntSupplier pageSupplier, final IntSupplier segmentCountSupplier) {
        this.pageSupplier = pageSupplier;
        this.segmentCountSupplier = segmentCountSupplier;
        this.emotes = new ArrayList<EmoteItem>();
    }
    
    @Override
    public void initialize(final Parent parent) {
        this.refresh();
        super.initialize(parent);
    }
    
    public void refresh() {
        this.removeChildIf(widget -> widget instanceof Segment);
        if (this.segmentSupplier == null) {
            return;
        }
        final CharSequence searchQuery = (this.querySupplier == null) ? null : this.querySupplier.get();
        final int page = this.pageSupplier.getAsInt();
        final Storage storage = Storage.INSTANCE;
        final boolean dailyEmotesPage = page == -1;
        final IntList source = dailyEmotesPage ? storage.getDailyEmotes() : storage.getUserEmotes();
        this.emotes.clear();
        for (final int id : source) {
            final EmoteItem emote = EmoteWheelWidget.EMOTE_SERVICE.getEmote(id);
            if (emote == null) {
                continue;
            }
            if (searchQuery != null && !CharSequences.contains(emote.getLowercaseName(), searchQuery)) {
                continue;
            }
            this.emotes.add(emote);
        }
        final int size = this.emotes.size();
        final boolean clockwise = LabyConfigProvider.INSTANCE.get().ingame().emotes().orderEmotesClockwise().get();
        for (int segmentCount = this.segmentCountSupplier.getAsInt(), i = 0; i < segmentCount; ++i) {
            int index;
            if (dailyEmotesPage) {
                index = (segmentCount - i) % segmentCount;
            }
            else if (clockwise) {
                index = segmentCount - i + page * segmentCount - 1;
            }
            else {
                index = (i + 1) % segmentCount + page * segmentCount;
            }
            final EmoteItem item = (index >= 0 && index < size) ? this.emotes.get(index) : null;
            final Segment segment = this.segmentSupplier.get(index, i, item);
            if (this.initialized) {
                this.addSegmentInitialized(segment);
            }
            else {
                this.addSegment(segment);
            }
        }
    }
    
    public EmoteWheelWidget querySupplier(final Supplier<CharSequence> querySupplier) {
        this.querySupplier = querySupplier;
        return this;
    }
    
    public EmoteWheelWidget segmentSupplier(final Function<EmoteItem, Segment> segmentSupplier) {
        return this.segmentSupplier((index, wheelIndex, emote) -> segmentSupplier.apply(emote));
    }
    
    public EmoteWheelWidget segmentSupplier(final SegmentSupplier segmentSupplier) {
        this.segmentSupplier = segmentSupplier;
        return this;
    }
    
    static {
        EMOTE_SERVICE = LabyMod.references().emoteService();
    }
    
    public static class Storage
    {
        public static final Storage INSTANCE;
        private final IntList userEmotes;
        private final IntList dailyEmotes;
        private UUID uniqueId;
        private boolean hasDailyEmotes;
        
        private Storage() {
            this.userEmotes = (IntList)new IntArrayList();
            this.dailyEmotes = (IntList)new IntArrayList();
            this.refreshDailyEmotes();
        }
        
        public void refreshUserData() {
            this.userEmotes.clear();
            final DefaultGameUserService gameUserService = (DefaultGameUserService)LabyMod.references().gameUserService();
            final DefaultGameUser gameUser = (DefaultGameUser)gameUserService.clientGameUser();
            final GameUserData userData = gameUser.getUserData();
            this.uniqueId = gameUser.getUniqueId();
            final boolean emoteDebug = LabyConfigProvider.INSTANCE.get().ingame().emotes().emoteDebug().get();
            this.userEmotes.addAll((Collection)(emoteDebug ? LabyMod.references().emoteService().getEmotes().keySet() : userData.getEmotes()));
            final DailyEmoteFlat dailyEmoteFlat = userData.getDailyEmoteFlat();
            this.hasDailyEmotes = (dailyEmoteFlat != null && dailyEmoteFlat.hasFlat());
        }
        
        public void refreshDailyEmotes() {
            this.dailyEmotes.clear();
            final DefaultGameUserService gameUserService = (DefaultGameUserService)LabyMod.references().gameUserService();
            this.dailyEmotes.addAll(gameUserService.dailyEmoteService().getDailyEmotes());
        }
        
        public IntList getUserEmotes() {
            return this.userEmotes;
        }
        
        public IntList getDailyEmotes() {
            return this.dailyEmotes;
        }
        
        public UUID getUniqueId() {
            return this.uniqueId;
        }
        
        public boolean hasDailyEmotes() {
            return this.hasDailyEmotes;
        }
        
        static {
            INSTANCE = new Storage();
        }
    }
    
    public interface SegmentSupplier
    {
        Segment get(final int p0, final int p1, final EmoteItem p2);
    }
}
