// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.sound;

import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface MinecraftSounds
{
    void playButtonPress();
    
    void playChatFilterSound();
    
    void playSound(final ResourceLocation p0, final float p1, final float p2);
    
    void playSound(final ResourceLocation p0, final float p1, final float p2, final FloatVector3 p3);
    
    void playSound(final ResourceLocation p0, final float p1, final float p2, final DoubleVector3 p3);
}
