// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.resources.sound;

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
        ((DefaultSoundService)this.soundService).bindDefault(SoundType.BUTTON_CLICK, ResourceLocation.create("minecraft", "gui.button.press"));
    }
    
    @Override
    public void playButtonPress() {
        this.soundService.play(SoundType.BUTTON_CLICK);
    }
    
    @Override
    public void playChatFilterSound() {
        ave.A().W().a((bpj)bpf.a(new jy("note.harp"), 1.0f));
    }
    
    @Override
    public void playSound(final ResourceLocation location, final float volume, final float pitch) {
        ave.A().W().a((bpj)new VersionedPositionedSound(location, volume, pitch, bpj.a.a, 0.0f, 0.0f, 0.0f));
    }
    
    @Override
    public void playSound(final ResourceLocation location, final float volume, final float pitch, final FloatVector3 position) {
        ave.A().W().a((bpj)new VersionedPositionedSound(location, volume, pitch, bpj.a.b, position.getX(), position.getY(), position.getZ()));
    }
    
    @Override
    public void playSound(final ResourceLocation location, final float volume, final float pitch, final DoubleVector3 position) {
        ave.A().W().a((bpj)new VersionedPositionedSound(location, volume, pitch, bpj.a.b, (float)position.getX(), (float)position.getY(), (float)position.getZ()));
    }
}
