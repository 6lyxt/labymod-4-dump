// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface Blocks
{
    Block air();
    
    Block getBlock(final ResourceLocation p0);
}
