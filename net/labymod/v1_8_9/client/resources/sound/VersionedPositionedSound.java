// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.resources.sound;

import net.labymod.api.client.resources.ResourceLocation;

public class VersionedPositionedSound extends bpa
{
    public VersionedPositionedSound(final ResourceLocation resourceLocation, final float volume, final float pitch, final bpj.a type, final float x, final float y, final float z) {
        super((jy)resourceLocation.getMinecraftLocation());
        this.b = volume;
        this.c = pitch;
        this.d = x;
        this.e = y;
        this.f = z;
        this.g = false;
        this.h = 0;
        this.i = type;
    }
}
