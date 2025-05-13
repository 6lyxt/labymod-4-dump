// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.visibility;

import java.lang.annotation.Annotation;
import net.labymod.api.modloader.ModLoaderRegistry;
import net.labymod.api.modloader.ModLoader;
import net.labymod.api.configuration.loader.annotation.ModRequirement;
import java.util.function.BooleanSupplier;
import net.labymod.api.configuration.settings.creator.MemberInspector;
import net.labymod.api.LabyAPI;
import net.labymod.api.configuration.loader.annotation.ModLoaderRequirement;

public class ModLoaderVisibilityEvaluator extends VisibilityEvaluator<ModLoaderRequirement>
{
    private final LabyAPI labyAPI;
    
    public ModLoaderVisibilityEvaluator(final LabyAPI labyAPI) {
        super(ModLoaderRequirement.class);
        this.labyAPI = labyAPI;
    }
    
    @Override
    public BooleanSupplier canSeeElement(final ModLoaderRequirement modLoaderRequirement, final MemberInspector element) {
        final boolean requiresInstalled = modLoaderRequirement.state() == ModRequirement.RequirementState.INSTALLED;
        final ModLoaderRegistry modLoaderRegistry = this.labyAPI.modLoaderRegistry();
        return () -> {
            final ModLoader loader = modLoaderRegistry.getById(modLoaderRequirement.loaderId());
            return requiresInstalled == (loader != null);
        };
    }
}
