// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.resources.sound;

import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.sound.SoundType;
import net.labymod.core.client.sound.DefaultSoundService;
import net.labymod.api.Laby;
import net.labymod.api.client.sound.SoundService;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.resources.sound.MinecraftSounds;

@Singleton
@Implements(MinecraftSounds.class)
public class VersionedMinecraftSounds implements MinecraftSounds
{
    private final SoundService soundService;
    
    public VersionedMinecraftSounds() {
        this.soundService = Laby.references().soundService();
        ((DefaultSoundService)this.soundService).bindDefault(SoundType.BUTTON_CLICK, ResourceLocation.create("minecraft", "ui.button.click"));
    }
    
    @Override
    public void playButtonPress() {
        this.soundService.play(SoundType.BUTTON_CLICK);
    }
    
    @Override
    public void playChatFilterSound() {
        evi.O().ai().a((gia)ghv.a((ih)ars.rd, 1.0f));
    }
    
    @Override
    public void playSound(final ResourceLocation location, final float volume, final float pitch) {
        this.playSound(ghv.a(new arr((ahg)location.getMinecraftLocation(), 16.0f, false), pitch, volume));
    }
    
    @Override
    public void playSound(final ResourceLocation location, final float volume, final float pitch, final FloatVector3 position) {
        this.playSound(new ghv((ahg)location.getMinecraftLocation(), art.a, volume, pitch, gia.t(), false, 0, gia.a.a, (double)position.getX(), (double)position.getY(), (double)position.getZ(), true));
    }
    
    @Override
    public void playSound(final ResourceLocation location, final float volume, final float pitch, final DoubleVector3 position) {
        this.playSound(new ghv((ahg)location.getMinecraftLocation(), art.a, volume, pitch, gia.t(), false, 0, gia.a.a, position.getX(), position.getY(), position.getZ(), true));
    }
    
    private void playSound(final ghv sound) {
        sound.a(evi.O().ai());
        evi.O().ai().a((gia)sound);
    }
}
