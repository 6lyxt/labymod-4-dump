// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.sound;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.Laby;
import java.util.function.BooleanSupplier;
import java.util.Objects;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.client.resources.sound.MinecraftSounds;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.sound.SoundService;

@Singleton
@Implements(SoundService.class)
public class DefaultSoundService implements SoundService
{
    private MinecraftSounds sounds;
    
    @Override
    public void bind(@NotNull final SoundType soundType, @NotNull final String identifier, @NotNull final ResourceLocation resourceLocation) {
        Objects.requireNonNull(identifier, "identifier");
        Objects.requireNonNull(resourceLocation, "resourceLocation");
        this.get(soundType).bind(identifier, () -> resourceLocation);
    }
    
    @Override
    public void bindConditionally(@NotNull final SoundType soundType, @NotNull final String identifier, @NotNull final ResourceLocation resourceLocation, @NotNull final BooleanSupplier booleanSupplier) {
        Objects.requireNonNull(identifier, "identifier");
        Objects.requireNonNull(resourceLocation, "resourceLocation");
        Objects.requireNonNull(booleanSupplier, "booleanSupplier");
        this.get(soundType).bind(identifier, () -> booleanSupplier.getAsBoolean() ? resourceLocation : null);
    }
    
    @Override
    public void play(@NotNull final SoundType soundType, final float volume, final float pitch) {
        if (this.sounds == null) {
            this.sounds = Laby.labyAPI().minecraft().sounds();
        }
        final ResourceLocation resourceLocation = soundType.getLocation();
        if (resourceLocation == null) {
            return;
        }
        this.sounds.playSound(resourceLocation, volume, pitch);
    }
    
    @Override
    public SoundType createSoundType(@NotNull final String identifier, @Nullable final ResourceLocation location, @Nullable final SoundType parent) {
        Objects.requireNonNull(identifier, "identifier");
        return new DefaultSoundType(identifier, location, parent);
    }
    
    public void bindDefault(final SoundType soundType, final ResourceLocation resourceLocation) {
        this.get(soundType).setDefault(resourceLocation);
    }
    
    private DefaultSoundType get(final SoundType soundType) {
        if (soundType instanceof final DefaultSoundType defaultSoundType) {
            return defaultSoundType;
        }
        if (soundType == null) {
            throw new NullPointerException("SoundType is null");
        }
        throw new IllegalArgumentException(soundType.getIdentifier() + " was not created via SoundService#createSoundType");
    }
}
