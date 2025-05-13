// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.filter;

import net.labymod.api.client.resources.ResourceLocation;

public class FilterSound
{
    private boolean shouldPlaySound;
    private ResourceLocation soundLocation;
    private float volume;
    private float pitch;
    
    public FilterSound(final boolean shouldPlaySound, final ResourceLocation soundLocation) {
        this(shouldPlaySound, soundLocation, 1.0f, 1.0f);
    }
    
    public FilterSound(final boolean shouldPlaySound, final ResourceLocation soundLocation, final float volume, final float pitch) {
        this.shouldPlaySound = shouldPlaySound;
        this.soundLocation = soundLocation;
        this.volume = volume;
        this.pitch = pitch;
    }
    
    public boolean shouldPlaySound() {
        return this.shouldPlaySound;
    }
    
    public void setShouldPlaySound(final boolean shouldPlaySound) {
        this.shouldPlaySound = shouldPlaySound;
    }
    
    public ResourceLocation getSoundLocation() {
        return this.soundLocation;
    }
    
    public void setSoundLocation(final ResourceLocation soundLocation) {
        this.soundLocation = soundLocation;
    }
    
    public float getVolume() {
        return this.volume;
    }
    
    public void setVolume(final float volume) {
        this.volume = volume;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
}
