// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server.storage;

import net.labymod.api.client.network.server.ServerAddress;

public interface ServerList
{
    void load();
    
    void save();
    
    void saveAsync();
    
    StorageServerData get(final int p0);
    
    void remove(final StorageServerData p0);
    
    void add(final StorageServerData p0);
    
    int size();
    
    void swap(final int p0, final int p1, final ServerFolder p2);
    
    boolean move(final int p0, final int p1, final MoveActionType p2, final ServerFolder p3);
    
    void replace(final int p0, final StorageServerData p1);
    
    void update(final StorageServerData p0);
    
    boolean has(final ServerAddress p0);
    
    int index(final StorageServerData p0);
    
    default void swap(final int index, final int index2) {
        this.swap(index, index2, null);
    }
}
