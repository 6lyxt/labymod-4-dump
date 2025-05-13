// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.group.tag;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.user.group.GroupDisplayType;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.entity.Entity;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.user.group.Group;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.tag.tags.NameTag;

public class GroupTextTag extends NameTag
{
    private static final Component PREFIX;
    @Nullable
    private Group group;
    
    @Override
    public void begin(final Entity entity) {
        this.group = this.visibleGroup(entity);
        super.begin(entity);
    }
    
    @Override
    public boolean isVisible() {
        return this.group != null && super.isVisible();
    }
    
    @Override
    protected RenderableComponent getRenderableComponent() {
        final Group group = this.group;
        if (group == null) {
            return null;
        }
        return RenderableComponent.of(((Component.Builder<T, TextComponent.Builder>)((Component.Builder<T, TextComponent.Builder>)((Component.Builder<T, TextComponent.Builder>)Component.text()).append(GroupTextTag.PREFIX)).append(Component.text(" "))).append(((BaseComponent<Component>)Component.text(group.getTagName())).color(group.getTextColor())).build());
    }
    
    @Override
    public float getScale() {
        return 0.5f;
    }
    
    private Group visibleGroup(final Entity entity) {
        if (!(entity instanceof Player)) {
            return null;
        }
        final Player player = (Player)entity;
        if (player.getUniqueId() == null) {
            return null;
        }
        final Group visibleGroup = player.gameUser().visibleGroup();
        if (visibleGroup.getDisplayType() != GroupDisplayType.ABOVE_HEAD) {
            return null;
        }
        return visibleGroup;
    }
    
    static {
        PREFIX = Component.text("LABYMOD", Style.style().color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, true).build());
    }
}
