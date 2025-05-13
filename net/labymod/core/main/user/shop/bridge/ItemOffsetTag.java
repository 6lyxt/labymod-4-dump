// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.bridge;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.core.main.user.shop.item.AbstractItem;
import java.util.Iterator;
import net.labymod.core.main.user.GameUserData;
import net.labymod.core.main.user.shop.item.model.AttachmentPoint;
import net.labymod.core.main.user.GameUserItem;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.api.Laby;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.client.render.RenderConstants;
import net.labymod.api.client.entity.player.tag.tags.NameTag;

public class ItemOffsetTag extends NameTag
{
    private static final float PADDING = 0.2f;
    private final RenderConstants renderConstants;
    private final ConfigProvider<LabyConfig> labyConfigProvider;
    
    public ItemOffsetTag() {
        this.renderConstants = Laby.references().renderConstants();
        this.labyConfigProvider = LabyConfigProvider.INSTANCE;
    }
    
    @Override
    public boolean isVisible() {
        return this.entity instanceof Player && this.labyConfigProvider.get().ingame().cosmetics().renderCosmetics().get();
    }
    
    @Override
    public void render(final Stack stack, final Entity entity) {
    }
    
    @Override
    public float getHeight() {
        final Player player = (Player)this.entity;
        float itemOffset = 0.0f;
        final DefaultGameUser gameUser = (DefaultGameUser)player.gameUser();
        final GameUserData userData = gameUser.getUserData();
        if (userData != null) {
            for (final GameUserItem entry : userData.getItems()) {
                final AbstractItem item = entry.item();
                if (item.itemDetails().getAttachmentPoint() != AttachmentPoint.HEAD) {
                    continue;
                }
                if (!item.canBeRendered()) {
                    continue;
                }
                itemOffset = Math.max(itemOffset, item.getNameTagOffset());
            }
        }
        itemOffset = ((itemOffset == 0.0f) ? itemOffset : (itemOffset + 0.2f));
        return itemOffset / this.renderConstants.nameTagScale() - 1.0f;
    }
    
    @Nullable
    @Override
    protected RenderableComponent getRenderableComponent() {
        return null;
    }
    
    @Override
    public boolean isOnlyVisibleOnMainTag() {
        return true;
    }
}
