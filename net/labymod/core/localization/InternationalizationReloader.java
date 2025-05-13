// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.localization;

import java.util.Iterator;
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.api.generated.ReferenceStorage;
import java.io.IOException;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.Laby;
import net.labymod.api.util.logging.Logging;

public final class InternationalizationReloader
{
    private static final Logging LOGGER;
    
    public static void reload() {
        final ReferenceStorage references = Laby.references();
        final DefaultInternationalization internationalization = (DefaultInternationalization)references.internationalization();
        internationalization.onResourceReload();
        final ResourcePackRepository repository = references.resourcePackRepository();
        for (final ResourcePack registeredPack : repository.getRegisteredPacks()) {
            for (final String clientNamespace : registeredPack.getClientNamespaces()) {
                try {
                    internationalization.loadTranslations(clientNamespace);
                }
                catch (final IOException exception) {
                    InternationalizationReloader.LOGGER.error("Failed to load translations (Namespace: {})", clientNamespace, exception);
                }
            }
        }
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
