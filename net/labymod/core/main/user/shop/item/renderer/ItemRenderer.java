// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.renderer;

import net.labymod.api.user.GameUser;
import net.labymod.core.main.user.shop.item.ItemRendererContext;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.main.user.shop.item.AbstractItem;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.user.shop.RenderMode;
import net.labymod.api.LabyAPI;

public abstract class ItemRenderer
{
    protected final LabyAPI labyAPI;
    private final RenderMode renderMode;
    
    public ItemRenderer(final LabyAPI labyAPI, final RenderMode renderMode) {
        this.labyAPI = labyAPI;
        this.renderMode = renderMode;
    }
    
    public abstract void apply(final Player p0, final PlayerModel p1, final ItemMetadata p2, final float p3, final AbstractItem p4, final ResourceLocation p5);
    
    public abstract void render(final AbstractItem p0, final ItemRendererContext p1);
    
    public abstract void finish();
    
    public RenderMode renderMode() {
        return this.renderMode;
    }
    
    public void begin(final GameUser user) {
    }
    
    public void end(final GameUser user) {
    }
}
