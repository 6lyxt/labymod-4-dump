// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.texture;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface BufferedImageProcessor
{
    int getPixelColor(final BufferedImage p0, final int p1, final int p2);
}
