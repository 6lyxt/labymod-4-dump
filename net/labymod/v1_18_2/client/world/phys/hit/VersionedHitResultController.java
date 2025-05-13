// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.world.phys.hit;

import net.labymod.api.client.world.phys.hit.HitResult;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.world.phys.hit.HitResultController;

@Singleton
@Implements(HitResultController.class)
public class VersionedHitResultController implements HitResultController
{
    @Inject
    public VersionedHitResultController() {
    }
    
    @Override
    public HitResult getResult() {
        return (HitResult)dyr.D().v;
    }
}
