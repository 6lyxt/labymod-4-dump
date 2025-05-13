// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ExperienceBarRenderer
{
    ExperienceBarRenderer mode(final RenderMode p0);
    
    ExperienceBarRenderer pos(final float p0, final float p1);
    
    ExperienceBarRenderer experienceNeededForNextLevel(final int p0);
    
    ExperienceBarRenderer experienceProgress(final float p0);
    
    ExperienceBarRenderer experienceLevel(final int p0);
    
    void render(final Stack p0);
    
    float getWidth();
    
    float getHeight();
}
