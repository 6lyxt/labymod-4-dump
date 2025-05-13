// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.visibility;

import java.lang.annotation.Annotation;
import net.labymod.api.modloader.ModLoaderRegistry;
import net.labymod.api.addon.AddonService;
import net.labymod.api.modloader.ModLoader;
import net.labymod.api.configuration.loader.annotation.OptiFineRequirement;
import java.util.function.BooleanSupplier;
import net.labymod.api.configuration.settings.creator.MemberInspector;
import net.labymod.api.LabyAPI;
import net.labymod.api.configuration.loader.annotation.ModRequirement;

public class ModRequirementVisibilityEvaluator extends VisibilityEvaluator<ModRequirement>
{
    private final LabyAPI labyAPI;
    
    public ModRequirementVisibilityEvaluator(final LabyAPI labyAPI) {
        super(ModRequirement.class);
        this.labyAPI = labyAPI;
    }
    
    @Override
    public BooleanSupplier canSeeElement(final ModRequirement modRequirement, final MemberInspector element) {
        final boolean requiresInstalled = modRequirement.state() == ModRequirement.RequirementState.INSTALLED;
        final String namespace = modRequirement.namespace();
        if (namespace.equals("optifine") || namespace.equals("optifabric")) {
            throw new IllegalStateException("Use @" + OptiFineRequirement.class.getName() + " instead of @" + ModRequirement.class.getName() + " for " + namespace);
        }
        final ModRequirement.RequirementType type = modRequirement.type();
        BooleanSupplier installedSupplier;
        if (type == ModRequirement.RequirementType.ADDON) {
            final AddonService addonService = this.labyAPI.addonService();
            installedSupplier = (() -> addonService.getAddon(namespace).isPresent());
        }
        else {
            final ModLoaderRegistry modLoaderRegistry = this.labyAPI.modLoaderRegistry();
            installedSupplier = (() -> {
                final ModLoader modLoader = modLoaderRegistry.getById(type.getLoaderId());
                return modLoader != null && modLoader.isModLoaded(namespace);
            });
        }
        return () -> requiresInstalled == installedSupplier.getAsBoolean();
    }
}
