// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.availability;

import net.labymod.api.configuration.settings.creator.MemberInspector;
import net.labymod.api.configuration.settings.annotation.SettingDevelopment;
import net.labymod.api.LabyAPI;

public class DevelopmentMemberAvailability implements MemberAvailability
{
    private final LabyAPI labyAPI;
    
    public DevelopmentMemberAvailability(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    @Override
    public boolean isAvailable(final MemberAvailabilityContext context) {
        final MemberInspector inspector = context.inspector();
        final boolean annotationPresent = inspector.isAnnotationPresent(SettingDevelopment.class);
        final String namespace = context.namespace();
        final boolean devEnvironment = this.labyAPI.labyModLoader().isDevelopmentEnvironment(namespace);
        return !annotationPresent || devEnvironment;
    }
}
