// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.availability;

import net.labymod.api.configuration.settings.creator.MemberInspector;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.configuration.loader.annotation.OSCompatibility;

public class OperatingSystemCompatibleMemberAvailability implements MemberAvailability
{
    @Override
    public boolean isAvailable(final MemberAvailabilityContext context) {
        final MemberInspector inspector = context.inspector();
        final OSCompatibility annotation = inspector.getAnnotation(OSCompatibility.class);
        if (annotation == null) {
            return true;
        }
        final OperatingSystem operatingSystem = OperatingSystem.getPlatform();
        final OperatingSystem[] value;
        final OperatingSystem[] compatible = value = annotation.value();
        for (final OperatingSystem os : value) {
            if (os == operatingSystem) {
                return true;
            }
        }
        return false;
    }
}
