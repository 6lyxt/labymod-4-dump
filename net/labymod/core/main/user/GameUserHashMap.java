// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user;

import java.util.Iterator;
import net.labymod.api.user.GameUser;
import java.util.UUID;
import java.util.HashMap;

public class GameUserHashMap extends HashMap<UUID, GameUser>
{
    @Override
    public GameUser remove(final Object key) {
        final GameUser user = super.remove(key);
        if (user != null) {
            user.dispose();
        }
        return user;
    }
    
    @Override
    public boolean remove(final Object key, final Object value) {
        final boolean removed = super.remove(key, value);
        if (removed && value instanceof GameUser) {
            final GameUser gameUser = (GameUser)value;
            gameUser.dispose();
        }
        return removed;
    }
    
    @Override
    public void clear() {
        for (final GameUser user : ((HashMap<K, GameUser>)this).values()) {
            if (user == null) {
                continue;
            }
            user.dispose();
        }
        super.clear();
    }
}
