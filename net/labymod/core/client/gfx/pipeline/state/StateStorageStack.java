// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.state;

import net.labymod.core.util.GrowableArrayIndex;
import net.labymod.core.util.ArrayIndex;

public final class StateStorageStack
{
    private final ArrayIndex<Entry> entries;
    private int currentIndex;
    
    public StateStorageStack(final StateStorageApplier<?>[] appliers) {
        (this.entries = new GrowableArrayIndex<Entry>(5, Entry[]::new)).fill(() -> new Entry(this.copyAppliers(appliers)));
    }
    
    public void push() {
        ++this.currentIndex;
        Entry currentEntry = this.getEntry();
        int index = this.currentIndex;
        while (currentEntry == null) {
            currentEntry = this.entries.get(index--);
            this.set(currentEntry);
        }
        currentEntry.store();
    }
    
    public void pop() {
        final Entry entry = this.getEntry();
        entry.restore();
        --this.currentIndex;
    }
    
    private Entry getEntry() {
        return this.entries.get(this.currentIndex);
    }
    
    private void set(final Entry entry) {
        if (entry == null) {
            return;
        }
        this.entries.set(this.currentIndex, entry.copy());
    }
    
    private StateStorageApplier<?>[] copyAppliers(final StateStorageApplier<?>[] appliers) {
        final StateStorageApplier<?>[] newArray = new StateStorageApplier[appliers.length];
        for (int i = 0; i < newArray.length; ++i) {
            newArray[i] = appliers[i].copy();
        }
        return newArray;
    }
    
    public static final class Entry
    {
        private final StateStorageApplier<?>[] appliers;
        
        public Entry(final StateStorageApplier<?>[] appliers) {
            this.appliers = appliers;
        }
        
        public void store() {
            for (final StateStorageApplier<?> applier : this.appliers) {
                applier.store();
            }
        }
        
        public void restore() {
            for (final StateStorageApplier<?> applier : this.appliers) {
                applier.restore();
            }
        }
        
        public Entry copy() {
            final StateStorageApplier<?>[] appliers = new StateStorageApplier[this.appliers.length];
            for (int i = 0; i < appliers.length; ++i) {
                appliers[i] = this.appliers[i].copy();
            }
            return new Entry(appliers);
        }
    }
}
