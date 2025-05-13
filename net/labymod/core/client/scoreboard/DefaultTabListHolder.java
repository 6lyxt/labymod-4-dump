// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.scoreboard;

import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;
import java.util.function.BooleanSupplier;
import net.labymod.api.event.client.gui.screen.playerlist.PlayerListUpdateEvent;
import net.labymod.api.client.scoreboard.TabListHolder;

public final class DefaultTabListHolder implements TabListHolder
{
    private static final PlayerListUpdateEvent UPDATE_EVENT;
    private final BooleanSupplier visibleSupplier;
    private final ComponentMapper mapper;
    @Nullable
    private Component header;
    @Nullable
    private Component footer;
    
    public DefaultTabListHolder(final BooleanSupplier visibleSupplier) {
        this.visibleSupplier = visibleSupplier;
        this.mapper = Laby.references().componentMapper();
    }
    
    @Nullable
    @Override
    public Component getHeader() {
        return this.header;
    }
    
    public void setHeader(final Object component) {
        this.setHeader((component == null) ? null : this.mapper.fromMinecraftComponent(component));
    }
    
    public void setHeader(final Component header) {
        this.header = header;
        Laby.fireEvent(DefaultTabListHolder.UPDATE_EVENT);
    }
    
    @Nullable
    @Override
    public Component getFooter() {
        return this.footer;
    }
    
    public void setFooter(final Object component) {
        this.setFooter((component == null) ? null : this.mapper.fromMinecraftComponent(component));
    }
    
    public void setFooter(final Component footer) {
        this.footer = footer;
        Laby.fireEvent(DefaultTabListHolder.UPDATE_EVENT);
    }
    
    public void reset() {
        this.header = null;
        this.footer = null;
        Laby.fireEvent(DefaultTabListHolder.UPDATE_EVENT);
    }
    
    @Override
    public boolean isVisible() {
        return this.visibleSupplier.getAsBoolean();
    }
    
    static {
        UPDATE_EVENT = new PlayerListUpdateEvent();
    }
}
