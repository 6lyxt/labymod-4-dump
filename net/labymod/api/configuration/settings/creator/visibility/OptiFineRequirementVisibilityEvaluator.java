// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.visibility;

import java.lang.annotation.Annotation;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.configuration.loader.annotation.ModRequirement;
import java.util.function.BooleanSupplier;
import net.labymod.api.configuration.settings.creator.MemberInspector;
import net.labymod.api.configuration.loader.annotation.OptiFineRequirement;

public class OptiFineRequirementVisibilityEvaluator extends VisibilityEvaluator<OptiFineRequirement>
{
    public OptiFineRequirementVisibilityEvaluator() {
        super(OptiFineRequirement.class);
    }
    
    @Override
    public BooleanSupplier canSeeElement(final OptiFineRequirement optiFineRequirement, final MemberInspector element) {
        final boolean requiresInstalled = optiFineRequirement.value() == ModRequirement.RequirementState.INSTALLED;
        return () -> OptiFine.isPresent() == requiresInstalled;
    }
}
