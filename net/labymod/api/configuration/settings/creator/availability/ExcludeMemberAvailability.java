// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.availability;

import net.labymod.api.configuration.settings.creator.MemberInspector;
import net.labymod.api.configuration.loader.annotation.Exclude;

public class ExcludeMemberAvailability implements MemberAvailability
{
    @Override
    public boolean isAvailable(final MemberAvailabilityContext context) {
        final MemberInspector inspector = context.inspector();
        return !inspector.isAnnotationPresent(Exclude.class);
    }
}
