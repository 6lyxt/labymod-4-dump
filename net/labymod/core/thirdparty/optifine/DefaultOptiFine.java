// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.thirdparty.optifine;

import net.labymod.core.thirdparty.optifine.listener.OptiFineListener;
import net.labymod.api.Laby;
import net.labymod.api.volt.generator.ClassGenerator;
import net.labymod.api.modloader.ModLoaderRegistry;
import net.labymod.api.modloader.ModLoader;
import java.net.URL;
import net.labymod.api.loader.platform.PlatformClassloader;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.thirdparty.optifine.OptiFineConfig;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.thirdparty.optifine.OptiFine;

@Singleton
@Implements(OptiFine.class)
public class DefaultOptiFine implements OptiFine
{
    static final String INTERFACE = "net/labymod/api/thirdparty/optifine/OptiFineConfig";
    private static final String PACKAGE = "net/labymod/core/generated/thirdparty/optifine/";
    static final String IMPLEMENTATION_CLASS_NAME = "net/labymod/core/generated/thirdparty/optifine/DefaultOptiFineConfig";
    private final OptiFineConfigClassGenerator configClassGenerator;
    private OptiFineConfig optiFineConfig;
    private boolean foundOptiFine;
    private boolean onlyOnce;
    
    public DefaultOptiFine() {
        this.setOptiFineConfig(new NOPOptiFineConfig());
        this.configClassGenerator = new OptiFineConfigClassGenerator(this::setOptiFineConfig);
    }
    
    public void setOptiFineConfig(final OptiFineConfig config) {
        this.optiFineConfig = config;
    }
    
    @Override
    public boolean isOptiFinePresent() {
        if (this.onlyOnce) {
            return this.foundOptiFine;
        }
        this.onlyOnce = true;
        if (DefaultOptiFine.BUNDLED_OPTIFINE) {
            final PlatformClassloader platformClassloader = PlatformEnvironment.getPlatformClassloader();
            final ClassLoader classloader = platformClassloader.getPlatformClassloader();
            final URL resource = classloader.getResource("net.optifine.BetterGrass".replace(".", "/").concat(".class"));
            final boolean exists = resource != null;
            this.setFoundOptiFine(exists);
            return exists;
        }
        this.setFoundOptiFine(DefaultAddonService.getInstance().getAddon("optifine").isPresent() || this.isOptiFabricPresent());
        return this.foundOptiFine;
    }
    
    @Override
    public boolean isOptiFabricPresent() {
        final ModLoader fabricLoader = ModLoaderRegistry.instance().getById("fabricloader");
        return fabricLoader != null && fabricLoader.isModLoaded("optifabric");
    }
    
    @Override
    public OptiFineConfig optiFineConfig() {
        return this.optiFineConfig;
    }
    
    private void setFoundOptiFine(final boolean foundOptiFine) {
        this.foundOptiFine = foundOptiFine;
        if (foundOptiFine) {
            this.configClassGenerator.generateClass("net/labymod/core/generated/thirdparty/optifine/DefaultOptiFineConfig".replace('/', '.'), null);
            Laby.references().eventBus().registerListener(new OptiFineListener());
        }
    }
}
