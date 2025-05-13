// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.group.tag;

import net.labymod.api.user.GameUser;
import net.labymod.api.user.group.GroupDisplayType;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.Textures;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.user.group.Group;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.entity.player.tag.tags.IconTag;

public class GroupIconTag extends IconTag
{
    private final Icon wolfIcon;
    private final Icon groupIcon;
    private final LabyAPI labyAPI;
    @Nullable
    private Group group;
    
    public GroupIconTag(final LabyAPI labyAPI) {
        super(9.0f);
        this.labyAPI = labyAPI;
        this.wolfIcon = Textures.SpriteLabyMod.DEFAULT_WOLF_HIGH_RES;
        this.groupIcon = Textures.SpriteLabyMod.WHITE_WOLF_HIGH_RES;
    }
    
    @Override
    public void begin(final Entity entity) {
        this.group = this.getVisibleGroup(entity);
        super.begin(entity);
    }
    
    @Override
    public boolean isVisible() {
        return this.group != null && super.isVisible();
    }
    
    @Override
    public int getColor() {
        final Group group = this.group;
        if (group != null) {
            return group.getColor().getRGB();
        }
        return super.getColor();
    }
    
    @Override
    public Icon getIcon() {
        return (this.group == null) ? this.wolfIcon : (this.group.isDefault() ? this.wolfIcon : this.groupIcon);
    }
    
    private Group getVisibleGroup(final Entity entity) {
        if (!(entity instanceof Player)) {
            return null;
        }
        final Player player = (Player)entity;
        if (player.getUniqueId() == null) {
            return null;
        }
        final GameUser user = player.gameUser();
        if (!user.isUsingLabyMod()) {
            return null;
        }
        final Group visibleGroup = user.visibleGroup();
        if (this.labyAPI.config().ingame().showUserIndicatorBesideName().get()) {
            return visibleGroup;
        }
        return (visibleGroup.getDisplayType() == GroupDisplayType.BESIDE_NAME) ? visibleGroup : null;
    }
}
