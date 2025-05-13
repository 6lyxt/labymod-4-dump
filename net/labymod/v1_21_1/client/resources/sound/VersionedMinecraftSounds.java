// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.resources.sound;

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
        fgo.Q().aj().a((gua)gtv.a((jm)avp.rY, 1.0f));
    }
    
    @Override
    public void playSound(final ResourceLocation location, final float volume, final float pitch) {
        this.playSound(gtv.a(new avo((akr)location.getMinecraftLocation(), 16.0f, false), pitch, volume));
    }
    
    @Override
    public void playSound(final ResourceLocation location, final float volume, final float pitch, final FloatVector3 position) {
        this.playSound(new gtv((akr)location.getMinecraftLocation(), avq.a, volume, pitch, gua.t(), false, 0, gua.a.a, (double)position.getX(), (double)position.getY(), (double)position.getZ(), true));
    }
    
    @Override
    public void playSound(final ResourceLocation location, final float volume, final float pitch, final DoubleVector3 position) {
        this.playSound(new gtv((akr)location.getMinecraftLocation(), avq.a, volume, pitch, gua.t(), false, 0, gua.a.a, position.getX(), position.getY(), position.getZ(), true));
    }
    
    private void playSound(final gtv sound) {
        sound.a(fgo.Q().aj());
        fgo.Q().aj().a((gua)sound);
    }
}
