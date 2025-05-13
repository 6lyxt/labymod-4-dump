// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.tag.renderer;

import java.util.Iterator;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.util.KeyValue;
import java.util.List;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.matrix.Stack;

public abstract class AbstractPositionRenderer implements PositionRenderer
{
    protected float scale;
    protected float height;
    protected float width;
    
    protected AbstractPositionRenderer() {
    }
    
    @Override
    public void render(final Stack stack, final Entity entity, final List<KeyValue<TagRenderer>> tags, final float usernameWidth, final TagType tagType) {
        boolean rendered = false;
        final boolean keepUsernamePosition = this.keepUsernamePosition();
        for (final KeyValue<TagRenderer> entry : tags) {
            final TagRenderer tag = entry.getValue();
            tag.begin(entity);
            if (tagType == TagType.MAIN_TAG || tag.isOnlyVisibleOnMainTag()) {
                if (!tag.isVisible()) {
                    continue;
                }
                if (keepUsernamePosition && !rendered) {
                    rendered = true;
                    stack.push();
                }
                this.scale = tag.getScale();
                this.width = tag.getWidth();
                this.height = tag.getHeight();
                stack.push();
                this.setupPosition(stack);
                tag.render(stack, entity);
                stack.pop();
                this.shiftPosition(stack);
            }
        }
        if (rendered) {
            stack.pop();
        }
    }
    
    protected boolean keepUsernamePosition() {
        return true;
    }
    
    protected abstract void setupPosition(final Stack p0);
    
    protected abstract void shiftPosition(final Stack p0);
}
