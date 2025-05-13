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

public abstract class HorizontalStackRenderer extends AbstractPositionRenderer
{
    private final HorizontalPosition horizontalPosition;
    private float mirror;
    private float offsetX;
    private float offsetY;
    
    protected HorizontalStackRenderer(final HorizontalPosition horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
    }
    
    @Override
    public void render(final Stack stack, final Entity entity, final List<KeyValue<TagRenderer>> tags, final float usernameWidth, final TagType tagType) {
        this.mirror = ((this.horizontalPosition == HorizontalPosition.RIGHT) ? 1.0f : -1.0f);
        this.offsetX = usernameWidth / 2.0f * this.mirror;
        this.offsetY = 4.0f;
        super.render(stack, entity, tags, usernameWidth, tagType);
    }
    
    @Override
    protected void setupPosition(final Stack stack) {
        stack.translate(this.offsetX + 0.5f + 1.0f * this.mirror, this.offsetY, 0.0f);
        stack.scale(this.scale, this.scale, 1.0f);
        stack.translate((this.horizontalPosition == HorizontalPosition.RIGHT) ? 0.0f : (-this.width), -this.height / 2.0f, 0.0f);
    }
    
    @Override
    protected void shiftPosition(final Stack stack) {
        final float shift = this.width * this.scale + 1.0f;
        stack.translate(shift * this.mirror, 0.0f, 0.0f);
    }
    
    public enum HorizontalPosition
    {
        LEFT, 
        RIGHT;
    }
}
