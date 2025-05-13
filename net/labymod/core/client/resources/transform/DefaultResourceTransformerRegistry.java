// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.transform;

import java.nio.file.Path;
import java.util.Iterator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.Locale;
import net.labymod.api.Constants;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.Laby;
import java.io.InputStream;
import java.util.Comparator;
import java.util.ArrayList;
import javax.inject.Inject;
import java.util.concurrent.ConcurrentHashMap;
import net.labymod.core.client.resources.transform.transformer.customhitcolor.CustomHitColorShaderListener;
import net.labymod.api.event.EventBus;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.transform.ResourceTransformer;
import java.util.List;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.resources.transform.ResourceTransformerRegistry;

@Singleton
@Implements(ResourceTransformerRegistry.class)
public class DefaultResourceTransformerRegistry implements ResourceTransformerRegistry
{
    private final Logging logging;
    private final Map<ResourceLocation, List<ResourceTransformer>> transformers;
    
    @Inject
    public DefaultResourceTransformerRegistry(@NotNull final Logging.Factory factory, @NotNull final EventBus eventBus) {
        this.logging = factory.create("Resource Transformer");
        eventBus.registerListener(new CustomHitColorShaderListener());
        this.transformers = new ConcurrentHashMap<ResourceLocation, List<ResourceTransformer>>();
    }
    
    @Override
    public void register(final ResourceLocation location, final ResourceTransformer transformer) {
        List<ResourceTransformer> transformers = this.transformers.get(location);
        if (transformers == null) {
            transformers = new ArrayList<ResourceTransformer>();
        }
        transformers.add(transformer);
        transformers.sort(Comparator.comparingInt(ResourceTransformer::priority));
        this.transformers.put(location, transformers);
    }
    
    public InputStream applyTransformers(final ResourceLocation location, InputStream inputStream) {
        if (Laby.references().resourcePackRepository().hasServerPackSelected()) {
            return inputStream;
        }
        ResourceTransformer currentTransformer = null;
        for (final Map.Entry<ResourceLocation, List<ResourceTransformer>> entry : this.transformers.entrySet()) {
            if (!entry.getKey().equals(location)) {
                continue;
            }
            this.logging.info("Transforming {}", location);
            try {
                byte[] resourceData = IOUtil.readBytes(inputStream);
                final Iterator iterator2 = entry.getValue().iterator();
                while (iterator2.hasNext()) {
                    final ResourceTransformer resourceTransformer = currentTransformer = (ResourceTransformer)iterator2.next();
                    resourceData = resourceTransformer.transform(resourceData);
                }
                if (Constants.SystemProperties.getDebugging(Constants.SystemProperties.RESOURCE_TRANSFORM)) {
                    try {
                        final String path = "labymod-neo/debug/resources/%s/%s";
                        final Path modifiedClassDataPath = Paths.get(String.format(Locale.ROOT, path, location.getNamespace(), location.getPath()), new String[0]);
                        IOUtil.createDirectories(modifiedClassDataPath.getParent());
                        Files.write(modifiedClassDataPath, resourceData, new OpenOption[0]);
                    }
                    catch (final IOException ex) {}
                }
                inputStream = IOUtil.writeBytes(resourceData);
            }
            catch (final IOException exception) {
                this.logging.error("The transformer \"{}\" has caused an error while transforming the resource \"{}\".", (currentTransformer == null) ? "unknown" : currentTransformer.getClass().getName(), location);
            }
        }
        return inputStream;
    }
    
    public void clear() {
        this.transformers.clear();
    }
}
