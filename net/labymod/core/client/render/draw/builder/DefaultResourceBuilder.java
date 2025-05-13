// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.builder;

import net.labymod.api.client.render.draw.builder.RectangleBuilder;
import net.labymod.api.util.debug.Preconditions;
import net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.draw.builder.ResourceBuilder;

public class DefaultResourceBuilder<T extends ResourceBuilder<T>> extends DefaultRectangleBuilder<T> implements ResourceBuilder<T>
{
    protected ResourceLocation resourceLocation;
    protected float resolutionWidth;
    protected boolean resolutionWidthChanged;
    protected float resolutionHeight;
    protected boolean resolutionHeightChanged;
    protected float spriteX;
    protected boolean spriteXChanged;
    protected float spriteY;
    protected float spriteWidth;
    protected float spriteHeight;
    protected float offset;
    protected RoundedGeometryBuilder.RoundedData data;
    protected boolean blur;
    
    protected DefaultResourceBuilder() {
        this.resetBuilder();
    }
    
    @Override
    public T texture(final ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
        return (T)this;
    }
    
    @Override
    public T rounded(final float leftTopRadius, final float rightTopRadius, final float leftBottomRadius, final float rightBottomRadius) {
        throw new UnsupportedOperationException("Rounded textures are not implemented yet");
    }
    
    @Override
    public T sprite(final float spriteX, final float spriteY, final float spriteWidth, final float spriteHeight) {
        this.spriteX = spriteX;
        this.spriteXChanged = true;
        this.spriteY = spriteY;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        return (T)this;
    }
    
    @Override
    public T resolution(final float resolutionWidth, final float resolutionHeight) {
        this.resolutionWidth = resolutionWidth;
        if (resolutionWidth > 0.0f) {
            this.resolutionWidthChanged = true;
        }
        this.resolutionHeight = resolutionHeight;
        if (resolutionHeight > 0.0f) {
            this.resolutionHeightChanged = true;
        }
        return (T)this;
    }
    
    @Override
    public T offset(final float offset) {
        this.offset = offset;
        return (T)this;
    }
    
    @Override
    public T roundedData(final RoundedGeometryBuilder.RoundedData data) {
        this.data = data;
        return (T)this;
    }
    
    @Override
    public T blur(final boolean blur) {
        this.blur = blur;
        return (T)this;
    }
    
    @Override
    public void validateBuilder() {
        super.validateBuilder();
        Preconditions.notNull(this.resourceLocation, "Missing resourceLocation (call texture())");
    }
    
    @Override
    public void resetBuilder() {
        super.resetBuilder();
        this.resolutionWidth = 0.0f;
        this.resolutionWidthChanged = false;
        this.resolutionHeight = 0.0f;
        this.resolutionHeightChanged = false;
        this.spriteX = 0.0f;
        this.spriteXChanged = false;
        this.spriteY = 0.0f;
        this.spriteWidth = 0.0f;
        this.spriteHeight = 0.0f;
        this.offset = 0.0f;
        this.data = null;
        this.blur = false;
    }
}
