// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.resources.sound;

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
        bib.z().U().a((cgt)cgp.a(this.create("block.note.harp"), 1.0f));
    }
    
    @Override
    public void playSound(final ResourceLocation location, final float volume, final float pitch) {
        bib.z().U().a((cgt)new VersionedPositionedSound(location, volume, pitch, cgt.a.a, 0.0f, 0.0f, 0.0f));
    }
    
    @Override
    public void playSound(final ResourceLocation location, final float volume, final float pitch, final FloatVector3 position) {
        bib.z().U().a((cgt)new VersionedPositionedSound(location, volume, pitch, cgt.a.b, position.getX(), position.getY(), position.getZ()));
    }
    
    @Override
    public void playSound(final ResourceLocation location, final float volume, final float pitch, final DoubleVector3 position) {
        bib.z().U().a((cgt)new VersionedPositionedSound(location, volume, pitch, cgt.a.b, (float)position.getX(), (float)position.getY(), (float)position.getZ()));
    }
    
    private qe create(final String path) {
        return new qe(new nf(path));
    }
}
