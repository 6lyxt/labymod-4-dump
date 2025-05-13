// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface BlurRenderer
{
    void renderRectangle(@NotNull final Stack p0, @NotNull final Rectangle p1, final int p2);
    
    void setEdgeRadius(final BorderRadius p0);
    
    public enum BlurAlgorithm
    {
        KAWASE_UP(ResourceLocation.create("labymod", "shaders/blur/kawase.vsh"), ResourceLocation.create("labymod", "shaders/blur/kawase_up.fsh")), 
        KAWASE_DOWN(ResourceLocation.create("labymod", "shaders/blur/kawase.vsh"), ResourceLocation.create("labymod", "shaders/blur/kawase_down.fsh"));
        
        private final ResourceLocation vertexShaderLocation;
        private final ResourceLocation fragmentShaderLocation;
        
        private BlurAlgorithm(final ResourceLocation vertexShaderLocation, final ResourceLocation fragmentShaderLocation) {
            this.vertexShaderLocation = vertexShaderLocation;
            this.fragmentShaderLocation = fragmentShaderLocation;
        }
        
        public ResourceLocation vertexShaderLocation() {
            return this.vertexShaderLocation;
        }
        
        public ResourceLocation fragmentShaderLocation() {
            return this.fragmentShaderLocation;
        }
    }
}
