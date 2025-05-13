// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface TextColorStripper
{
    String stripColorCodes(final String p0);
    
    String stripColorCodes(final String p0, final char p1);
}
