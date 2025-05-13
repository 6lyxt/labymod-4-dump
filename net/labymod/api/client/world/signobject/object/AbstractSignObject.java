// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.signobject.object;

import net.labymod.api.client.world.signobject.template.SignObjectTemplate;
import java.util.Iterator;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.Laby;
import net.labymod.api.util.time.TimeUtil;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.client.world.signobject.template.SignObjectMeta;

public abstract class AbstractSignObject<M extends SignObjectMeta<?>> implements SignObject<M>
{
    private final M meta;
    private final SignObjectPosition position;
    private final long creationTimestamp;
    private final List<SignObjectListener<M>> listeners;
    
    protected AbstractSignObject(final M meta, final SignObjectPosition position) {
        this.listeners = new ArrayList<SignObjectListener<M>>();
        this.meta = meta;
        this.position = position;
        this.creationTimestamp = TimeUtil.getCurrentTimeMillis();
    }
    
    @Override
    public M meta() {
        return this.meta;
    }
    
    @Override
    public SignObjectPosition position() {
        return this.position;
    }
    
    @Override
    public long creationTimestamp() {
        return this.creationTimestamp;
    }
    
    @Override
    public boolean isEnabled() {
        final InstalledAddonInfo addon = ((SignObjectMeta<SignObjectTemplate>)this.meta).template().getAddon();
        return addon == null || Laby.labyAPI().addonService().isEnabled(addon, false);
    }
    
    @Override
    public void addListener(final SignObjectListener<M> listener) {
        this.listeners.add(listener);
    }
    
    @Override
    public void dispose() {
        for (final SignObjectListener<M> listener : this.listeners) {
            listener.onDispose(this);
        }
    }
}
