// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.data;

import net.labymod.core.labymodnet.models.Cosmetic;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.util.GsonUtil;
import java.util.Map;
import net.labymod.core.labymodnet.models.ChangeResponse;
import java.util.function.Consumer;

public class MultipleCosmeticRequestContent extends AbstractRequestContent
{
    private final BulkModel model;
    
    public MultipleCosmeticRequestContent(final CosmeticRequestType type, final Consumer<ChangeResponse> changeResponseConsumer) {
        super(type, changeResponseConsumer);
        this.model = new BulkModel();
    }
    
    public BulkModel getModel() {
        return this.model;
    }
    
    @Override
    public void fill(final Map<String, String> map) {
    }
    
    @Override
    public String toString() {
        return GsonUtil.DEFAULT_GSON.toJson((Object)this.model);
    }
    
    public static class BulkModel
    {
        private final List<BulkEntry> items;
        
        public BulkModel() {
            this(new ArrayList<BulkEntry>());
        }
        
        public BulkModel(final List<BulkEntry> items) {
            this.items = items;
        }
        
        public void add(final BulkEntry entry) {
            final BulkEntry bulkEntry = this.getEntry(entry.getId());
            if (bulkEntry == null) {
                this.items.add(entry);
                return;
            }
            bulkEntry.setEnabled(entry.isEnabled());
        }
        
        @Nullable
        public BulkEntry getEntry(final int id) {
            for (final BulkEntry item : this.items) {
                if (item.getId() == id) {
                    return item;
                }
            }
            return null;
        }
        
        public List<BulkEntry> getItems() {
            return this.items;
        }
        
        public void invalidate() {
            this.items.clear();
        }
        
        public static class BulkEntry
        {
            private final transient Cosmetic cosmetic;
            private final transient Consumer<ChangeResponse> changeResponseConsumer;
            private int id;
            private boolean enabled;
            
            public BulkEntry(final Cosmetic cosmetic, final boolean enabled, final Consumer<ChangeResponse> changeResponseConsumer) {
                this.cosmetic = cosmetic;
                this.id = cosmetic.getId();
                this.enabled = enabled;
                this.changeResponseConsumer = changeResponseConsumer;
            }
            
            public Cosmetic getCosmetic() {
                return this.cosmetic;
            }
            
            public int getId() {
                return this.id;
            }
            
            public void setId(final int id) {
                this.id = id;
            }
            
            public boolean isEnabled() {
                return this.enabled;
            }
            
            public void setEnabled(final boolean enabled) {
                this.enabled = enabled;
            }
            
            public Consumer<ChangeResponse> getChangeResponseConsumer() {
                return this.changeResponseConsumer;
            }
        }
    }
}
