// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.tag.tags;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.player.tag.renderer.AbstractTagRenderer;

public class IconTag extends AbstractTagRenderer
{
    protected final LabyAPI labyAPI;
    private final Icon icon;
    private final float width;
    private final float height;
    private Icon renderableIcon;
    private int color;
    
    protected IconTag(final float size) {
        this(size, size);
    }
    
    protected IconTag(final float width, final float height) {
        this(null, width, height);
    }
    
    public IconTag(final Icon icon, final float size) {
        this(icon, size, size);
    }
    
    public IconTag(final Icon icon, final float width, final float height) {
        this.labyAPI = Laby.labyAPI();
        this.icon = icon;
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void begin(final Entity entity) {
        super.begin(entity);
        this.color = this.getColor();
        this.renderableIcon = this.getIcon();
    }
    
    @Override
    public void render(final Stack stack, final Entity entity) {
        this.labyAPI.renderPipeline().renderSeeThrough(entity, () -> this.renderableIcon.render(stack, 0.0f, 0.0f, this.width, this.height, false, this.color));
    }
    
    @Override
    public boolean isVisible() {
        return this.renderableIcon != null && !this.entity.isCrouchingPose();
    }
    
    @Override
    public float getWidth() {
        return this.width;
    }
    
    @Override
    public float getHeight() {
        return this.height;
    }
    
    @Override
    public float getScale() {
        return 1.0f;
    }
    
    public Icon getIcon() {
        return this.icon;
    }
    
    public int getColor() {
        return -1;
    }
}
