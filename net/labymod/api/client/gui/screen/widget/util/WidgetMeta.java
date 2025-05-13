// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.util;

import net.labymod.api.Laby;
import net.labymod.api.client.render.RenderPipeline;

public class WidgetMeta
{
    private final AlphaStateModifier alphaStateModifier;
    
    public WidgetMeta() {
        this.alphaStateModifier = new AlphaStateModifier();
    }
    
    public void setAlpha(final float alpha) {
        this.alphaStateModifier.setAlpha(alpha);
    }
    
    public void multiplyAlpha(final float alpha) {
        this.alphaStateModifier.multiplyAlpha(alpha);
    }
    
    public void revertAlphaState() {
        this.alphaStateModifier.revert();
    }
    
    static class AlphaStateModifier
    {
        private static final RenderPipeline RENDER_PIPELINE;
        private float lastState;
        
        public void setAlpha(final float alpha) {
            this.lastState = AlphaStateModifier.RENDER_PIPELINE.getAlpha();
            AlphaStateModifier.RENDER_PIPELINE.setAlpha(alpha);
        }
        
        public void multiplyAlpha(final float alpha) {
            this.lastState = AlphaStateModifier.RENDER_PIPELINE.getAlpha();
            AlphaStateModifier.RENDER_PIPELINE.multiplyAlpha(alpha);
        }
        
        public void revert() {
            AlphaStateModifier.RENDER_PIPELINE.setAlpha(this.lastState);
        }
        
        static {
            RENDER_PIPELINE = Laby.references().renderPipeline();
        }
    }
}
