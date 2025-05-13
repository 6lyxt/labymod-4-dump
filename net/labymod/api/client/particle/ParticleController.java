// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.particle;

import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ParticleController
{
    void crackBlock(final FloatVector3 p0, final Direction p1);
}
