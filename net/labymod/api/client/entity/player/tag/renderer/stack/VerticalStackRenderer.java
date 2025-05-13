// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.tag.renderer.stack;

import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.api.util.KeyValue;
import java.util.List;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.entity.player.tag.renderer.AbstractPositionRenderer;

public abstract class VerticalStackRenderer extends AbstractPositionRenderer
{
    private float offset;
    
    protected VerticalStackRenderer() {
    }
    
    @Override
    public void render(final Stack stack, final Entity entity, final List<KeyValue<TagRenderer>> tags, final float usernameWidth, final TagType tagType) {
        this.offset = this.getEntryYOffset();
        super.render(stack, entity, tags, usernameWidth, tagType);
    }
    
    @Override
    protected void setupPosition(final Stack stack) {
        stack.translate(0.0f, this.offset, 0.0f);
        stack.scale(this.scale, this.scale, 1.0f);
        stack.translate(-this.width / 2.0f, -this.height, 0.0f);
    }
    
    @Override
    protected void shiftPosition(final Stack stack) {
        final float shift = this.height * this.scale + 1.0f;
        stack.translate(0.0f, -shift, 0.0f);
    }
    
    protected abstract float getEntryYOffset();
}
