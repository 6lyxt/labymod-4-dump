// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.addon.integration;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.reflection.Reflection;
import net.labymod.api.addon.LoadedAddon;

public class AddonIntegrationMeta
{
    private final Class<? extends AddonIntegration> integrationClass;
    private final LoadedAddon addon;
    private final String integratedAddonNamespace;
    private final Object[] constructionArguments;
    private AddonIntegration integration;
    private LoadedAddon integratedAddon;
    
    public AddonIntegrationMeta(final Class<? extends AddonIntegration> integrationClass, final LoadedAddon addon, final String integratedAddonNamespace, final Object[] constructionArguments) {
        this.integrationClass = integrationClass;
        this.addon = addon;
        this.integratedAddonNamespace = integratedAddonNamespace;
        this.constructionArguments = constructionArguments;
    }
    
    @NotNull
    public AddonIntegration integration() {
        if (this.integration == null) {
            try {
                this.integration = Reflection.instantiateWithArgs(this.integrationClass, this.constructionArguments);
            }
            catch (final ReflectiveOperationException exception) {
                throw new IllegalStateException("Failed to create addon integration from class " + this.integrationClass.getName(), (Throwable)exception);
            }
        }
        return this.integration;
    }
    
    @NotNull
    public LoadedAddon addon() {
        return this.addon;
    }
    
    @NotNull
    public String getIntegratedAddonNamespace() {
        return this.integratedAddonNamespace;
    }
    
    @Nullable
    public LoadedAddon getIntegratedAddon() {
        return this.integratedAddon;
    }
    
    public void setIntegratedAddon(final LoadedAddon integratedAddon) {
        if (integratedAddon != null && !integratedAddon.info().getNamespace().equals(this.integratedAddonNamespace)) {
            throw new IllegalArgumentException("Invalid addon, expected namespace " + this.integratedAddonNamespace + ", got " + integratedAddon.info().getNamespace());
        }
        this.integratedAddon = integratedAddon;
    }
}
