// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.canvas.template;

import net.labymod.api.client.world.signobject.template.SignObjectMeta;
import net.labymod.api.client.world.signobject.object.SignCanvasSize;
import java.util.Arrays;
import net.labymod.api.client.world.canvas.CanvasMeta;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.world.signobject.template.SignObjectTemplate;

public class CanvasTemplate extends SignObjectTemplate
{
    private static final float DEFAULT_RATIO = 1.0f;
    private static final FloatVector3 DEFAULT_OFFSET;
    private final float widthToHeightRatio;
    private final FloatVector3 renderOffset;
    private final MinMax widthBlocks;
    private final MinMax heightBlocks;
    
    protected CanvasTemplate(final ResourceLocation location, final float widthToHeightRatio, final FloatVector3 renderOffset, final MinMax widthBlocks, final MinMax heightBlocks, final InstalledAddonInfo addon) {
        super(location, addon);
        this.widthToHeightRatio = widthToHeightRatio;
        this.renderOffset = renderOffset;
        this.widthBlocks = widthBlocks;
        this.heightBlocks = heightBlocks;
    }
    
    public static Builder builder(final ResourceLocation location) {
        return builder(location, location.getNamespace());
    }
    
    public static Builder builder(final ResourceLocation location, final String addonNamespace) {
        return new Builder(location, SignObjectTemplate.getAddon(addonNamespace));
    }
    
    public static CanvasTemplate simple(final ResourceLocation location) {
        return simple(location, location.getNamespace());
    }
    
    public static CanvasTemplate simple(final ResourceLocation location, final String addonNamespace) {
        return new CanvasTemplate(location, 1.0f, CanvasTemplate.DEFAULT_OFFSET, MinMax.positive(), MinMax.positive(), SignObjectTemplate.getAddon(addonNamespace));
    }
    
    public float widthToHeightRatio() {
        return this.widthToHeightRatio;
    }
    
    public FloatVector3 renderOffset() {
        return this.renderOffset;
    }
    
    public MinMax widthBlocks() {
        return this.widthBlocks;
    }
    
    public MinMax heightBlocks() {
        return this.heightBlocks;
    }
    
    public float calculateWidth(final float height) {
        return height * this.widthToHeightRatio;
    }
    
    public float calculateHeight(final float width) {
        return width / this.widthToHeightRatio;
    }
    
    @Override
    public CanvasMeta parseMeta(final String[] meta) {
        if (meta.length == 0) {
            return null;
        }
        final String[] size = meta[0].split("x");
        if (size.length != 2) {
            return null;
        }
        float widthBlocks;
        float heightBlocks;
        try {
            widthBlocks = Float.parseFloat(size[0]);
            heightBlocks = Float.parseFloat(size[1]);
        }
        catch (final NumberFormatException ignored) {
            return null;
        }
        if (widthBlocks == 0.0f && heightBlocks == 0.0f) {
            return null;
        }
        if (!this.widthBlocks.isValid(widthBlocks) || !this.heightBlocks.isValid(heightBlocks)) {
            return null;
        }
        return new CanvasMeta(this, Arrays.copyOfRange(meta, 1, meta.length), new SignCanvasSize(widthBlocks, heightBlocks));
    }
    
    static {
        DEFAULT_OFFSET = new FloatVector3(0.0f, 0.0f, 1.0f);
    }
    
    public static class Builder
    {
        private final ResourceLocation location;
        private final InstalledAddonInfo addon;
        private FloatVector3 renderOffset;
        private float widthToHeightRatio;
        private MinMax widthBlocks;
        private MinMax heightBlocks;
        
        private Builder(final ResourceLocation location, final InstalledAddonInfo addon) {
            this.widthToHeightRatio = 1.0f;
            this.location = location;
            this.addon = addon;
        }
        
        public Builder ratio(final float widthToHeightRatio) {
            this.widthToHeightRatio = widthToHeightRatio;
            return this;
        }
        
        public Builder ratio(final float sampleWidth, final float sampleHeight) {
            this.widthToHeightRatio = sampleWidth / sampleHeight;
            return this;
        }
        
        public Builder ratio1x1() {
            return this.ratio(1.0f);
        }
        
        public Builder ratio16x9() {
            return this.ratio(16.0f, 9.0f);
        }
        
        public Builder restrictWidth(final MinMax widthBlocks) {
            this.widthBlocks = widthBlocks;
            return this;
        }
        
        public Builder restrictHeight(final MinMax heightBlocks) {
            this.heightBlocks = heightBlocks;
            return this;
        }
        
        public Builder renderOffset(final FloatVector3 renderOffset) {
            this.renderOffset = renderOffset;
            return this;
        }
        
        public CanvasTemplate build() {
            return new CanvasTemplate(this.location, this.widthToHeightRatio, (this.renderOffset != null) ? this.renderOffset : CanvasTemplate.DEFAULT_OFFSET, (this.widthBlocks != null) ? this.widthBlocks : MinMax.positive(), (this.heightBlocks != null) ? this.heightBlocks : MinMax.positive(), this.addon);
        }
    }
}
