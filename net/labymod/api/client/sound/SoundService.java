// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.sound;

import org.jetbrains.annotations.Nullable;
import java.util.function.BooleanSupplier;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface SoundService
{
    void bind(@NotNull final SoundType p0, @NotNull final String p1, @NotNull final ResourceLocation p2);
    
    void bindConditionally(@NotNull final SoundType p0, @NotNull final String p1, @NotNull final ResourceLocation p2, @NotNull final BooleanSupplier p3);
    
    void play(@NotNull final SoundType p0, final float p1, final float p2);
    
    SoundType createSoundType(@NotNull final String p0, @Nullable final ResourceLocation p1, @Nullable final SoundType p2);
    
    default void play(@NotNull final SoundType soundType) {
        this.play(soundType, 0.25f, 1.0f);
    }
    
    default void play(@NotNull final SoundType soundType, final float volume) {
        this.play(soundType, volume, 1.0f);
    }
}
