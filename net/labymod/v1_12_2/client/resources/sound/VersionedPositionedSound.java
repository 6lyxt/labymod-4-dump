// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.resources.sound;

import net.labymod.api.client.resources.ResourceLocation;

public class VersionedPositionedSound extends cgj
{
    public VersionedPositionedSound(final ResourceLocation resourceLocation, final float volume, final float pitch, final cgt.a type, final float x, final float y, final float z) {
        super((nf)resourceLocation.getMinecraftLocation(), qg.a);
        this.d = volume;
        this.e = pitch;
        this.f = x;
        this.g = y;
        this.h = z;
        this.i = false;
        this.j = 0;
        this.k = type;
    }
}
