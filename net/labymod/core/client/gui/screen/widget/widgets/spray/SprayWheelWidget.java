// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.spray;

import net.labymod.core.main.user.shop.spray.model.SprayPack;
import net.labymod.core.main.user.shop.spray.SprayService;
import net.labymod.core.main.user.GameUserData;
import net.labymod.core.generated.DefaultReferenceStorage;
import java.util.Collection;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.core.main.LabyMod;
import java.util.UUID;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.function.Function;
import net.labymod.api.util.CharSequences;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.Parent;
import java.util.ArrayList;
import java.util.function.Supplier;
import net.labymod.core.main.user.shop.spray.model.Spray;
import java.util.List;
import java.util.function.IntSupplier;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;

@AutoWidget
@Link("widget/spray-wheel.lss")
public class SprayWheelWidget extends WheelWidget
{
    private final IntSupplier pageSupplier;
    private final IntSupplier segmentCountSupplier;
    private final List<Spray> sprays;
    private Supplier<CharSequence> querySupplier;
    private SegmentSupplier segmentSupplier;
    
    public SprayWheelWidget(final IntSupplier pageSupplier, final IntSupplier segmentCountSupplier) {
        this.pageSupplier = pageSupplier;
        this.segmentCountSupplier = segmentCountSupplier;
        this.sprays = new ArrayList<Spray>();
    }
    
    @Override
    public void initialize(final Parent parent) {
        Storage.INSTANCE.refreshUserData();
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
        final List<Spray> source = storage.getSprays();
        this.sprays.clear();
        this.filterSpraysBySearchQuery(searchQuery, source);
        final int size = this.sprays.size();
        final boolean clockwise = this.labyAPI.config().ingame().spray().orderSprayClockwise().get();
        for (int segmentCount = this.segmentCountSupplier.getAsInt(), i = 0; i < segmentCount; ++i) {
            int index;
            if (clockwise) {
                index = segmentCount - i + page * segmentCount - 1;
            }
            else {
                index = (i + 1) % segmentCount + page * segmentCount;
            }
            final Spray spray = (index >= 0 && index < size) ? this.sprays.get(index) : null;
            final Segment segment = this.segmentSupplier.get(index, i, spray);
            if (this.initialized) {
                this.addSegmentInitialized(segment);
            }
            else {
                this.addSegment(segment);
            }
        }
    }
    
    private void filterSpraysBySearchQuery(final CharSequence searchQuery, final List<Spray> source) {
        for (final Spray spray : source) {
            if (searchQuery == null) {
                this.sprays.add(spray);
            }
            else if (this.containsIgnoreCase(searchQuery, spray.getName())) {
                this.sprays.add(spray);
            }
            else {
                for (final String tag : spray.getTags()) {
                    if (this.containsIgnoreCase(searchQuery, tag)) {
                        this.sprays.add(spray);
                        break;
                    }
                }
            }
        }
    }
    
    private boolean containsIgnoreCase(final CharSequence searchQuery, CharSequence value) {
        value = CharSequences.toLowerCase(value);
        return CharSequences.contains(value, searchQuery);
    }
    
    public SprayWheelWidget querySupplier(final Supplier<CharSequence> querySupplier) {
        this.querySupplier = querySupplier;
        return this;
    }
    
    public SprayWheelWidget segmentSupplier(final Function<Spray, Segment> segmentSupplier) {
        return this.segmentSupplier((index, wheelIndex, sprayPack) -> segmentSupplier.apply(sprayPack));
    }
    
    public SprayWheelWidget segmentSupplier(final SegmentSupplier segmentSupplier) {
        this.segmentSupplier = segmentSupplier;
        return this;
    }
    
    public static class Storage
    {
        public static final Storage INSTANCE;
        private final List<Spray> sprays;
        private UUID uniqueId;
        
        private Storage() {
            this.sprays = new ArrayList<Spray>();
        }
        
        public void refreshUserData() {
            this.sprays.clear();
            final DefaultReferenceStorage references = LabyMod.references();
            final DefaultGameUserService gameUserService = (DefaultGameUserService)references.gameUserService();
            final DefaultGameUser gameUser = (DefaultGameUser)gameUserService.clientGameUser();
            final GameUserData userData = gameUser.getUserData();
            this.uniqueId = gameUser.getUniqueId();
            final SprayService sprayService = references.sprayService();
            for (final Short packId : userData.getSprayPacks().getShorts()) {
                final SprayPack pack = sprayService.findPack(packId);
                if (pack == null) {
                    continue;
                }
                this.sprays.addAll(pack.getSprays());
            }
        }
        
        public List<Spray> getSprays() {
            return this.sprays;
        }
        
        public UUID getUniqueId() {
            return this.uniqueId;
        }
        
        static {
            INSTANCE = new Storage();
        }
    }
    
    public interface SegmentSupplier
    {
        Segment get(final int p0, final int p1, final Spray p2);
    }
}
