// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.pack;

import java.util.Iterator;
import java.io.IOException;
import net.labymod.core.localization.DefaultInternationalization;
import java.util.Collection;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.api.client.resources.pack.ResourcePackDetail;
import net.labymod.api.client.resources.pack.ResourcePack;
import java.util.List;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.resources.pack.ResourcePackRepository;

public abstract class AbstractResourcePackRepository<T> implements ResourcePackRepository
{
    protected static final String SERVER_RESOURCES_PACK_IDENTIFIER = "server";
    protected static final Logging LOGGER;
    private boolean hasServerPackSelected;
    private final List<ResourcePack> registeredResourcePacks;
    protected final List<String> selectedPacks;
    protected final List<ResourcePackDetail> availablePackDetails;
    
    public AbstractResourcePackRepository() {
        this.selectedPacks = new ArrayList<String>();
        this.availablePackDetails = new ArrayList<ResourcePackDetail>();
        this.registeredResourcePacks = new ArrayList<ResourcePack>();
    }
    
    @Override
    public void registerPack(final String name) {
        this.registerPack(Laby.references().resourcePackFactory().create(name));
    }
    
    @Override
    public void registerPack(final String name, final LoadedAddon loadedAddon) {
        this.registerPack(Laby.references().resourcePackFactory().create(name, loadedAddon));
    }
    
    @Override
    public void registerSilentPack(final String name) {
        this.registerSilentPack(Laby.references().resourcePackFactory().create(name));
    }
    
    @Override
    public void registerSilentPack(final String name, final LoadedAddon addon) {
        this.registerSilentPack(Laby.references().resourcePackFactory().create(name, addon));
    }
    
    @Override
    public void registerClientPack(final String name, final String... namespaces) {
        this.registerPack(Laby.references().resourcePackFactory().createClientPack(name, namespaces));
    }
    
    @Override
    public void registerPack(final ResourcePack pack) {
        this.registeredResourcePacks.add(pack);
    }
    
    @Override
    public void registerSilentPack(final ResourcePack pack) {
        this.registerPack(pack);
    }
    
    @Override
    public List<ResourcePack> getRegisteredPacks() {
        return this.registeredResourcePacks;
    }
    
    @Override
    public Collection<String> getSelectedPacks() {
        return this.selectedPacks;
    }
    
    @Override
    public List<ResourcePackDetail> getAvailablePackDetails() {
        return this.availablePackDetails;
    }
    
    @Override
    public boolean hasServerPackSelected() {
        return this.hasServerPackSelected;
    }
    
    public void setHasServerPackSelected(final boolean hasServerPackSelected) {
        this.hasServerPackSelected = hasServerPackSelected;
    }
    
    public final void onReload(final List<T> selected) {
        this.onRebuildSelected(selected);
        this.onReloadPackDetails();
    }
    
    public abstract void onRebuildSelected(final List<T> p0);
    
    protected abstract void onReloadPackDetails();
    
    protected void thrownSilentError(final ResourcePack pack) {
        throw new IllegalStateException("Resource pack could not be loaded silently (" + pack.getName());
    }
    
    protected void loadSilentTranslation(final ResourcePack pack) {
        final DefaultInternationalization localeManager = (DefaultInternationalization)Laby.labyAPI().internationalization();
        for (String clientNamespace : pack.getClientNamespaces()) {
            try {
                localeManager.loadTranslations(clientNamespace);
            }
            catch (final IOException exception) {
                AbstractResourcePackRepository.LOGGER.error("Translations could not be loaded silently (" + exception.getMessage(), new Object[0]);
            }
        }
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
