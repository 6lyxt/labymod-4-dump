// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper;

import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.Constants;
import net.labymod.api.loader.platform.PlatformClassloader;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class LaunchWrapperTransformerRegistry
{
    private static final String TRANSFORMER_PACKAGE = "net.labymod.core.loader.vanilla.launchwrapper.transformer.";
    private static final String PATCH_TRANSFORMER_PACKAGE = "net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.";
    private final LaunchClassLoader classLoader;
    private PlatformClassloader platformClassloader;
    private String runningVersion;
    private boolean labyModDevEnvironment;
    
    public LaunchWrapperTransformerRegistry(final LaunchClassLoader classLoader) {
        this.classLoader = classLoader;
    }
    
    public void registerEarlyTransformers() {
        this.classLoader.registerTransformer(this.patchClass("gson.TypeTokenTransformer"));
        this.classLoader.registerTransformer(this.patchClass("gson.ReflectiveTypeAdapterFactoryTransformer"));
    }
    
    public void registerPreTransformers() {
        if (this.labyModDevEnvironment) {
            this.classLoader.registerTransformer(this.transformerClass("GuiScreenV18Transformer"));
        }
        final boolean legacyGuava = this.platformClassloader.searchOnClasspath("guava-17.0");
        if (this.labyModDevEnvironment && !legacyGuava && this.runningVersion.equals("1.8.9")) {
            this.classLoader.registerTransformer(this.patchClass("guava.GuavaJreTransformer"));
        }
        this.classLoader.registerTransformer(this.transformerClass("GameScreenTransformer"));
        this.classLoader.registerTransformer(this.patchClass("RenamedFromServiceTransformer"));
        this.classLoader.registerTransformer(this.patchClass("compatibility.WorldObjectTransformer"));
        this.classLoader.registerTransformer(this.patchClass("optifine.PlayerConfigurationsTransformer"));
        this.classLoader.registerTransformer(this.patchClass("java.StringFormatTransformer"));
        this.classLoader.registerTransformer(this.patchClass("guava.ObjectsTransformer"));
        this.classLoader.registerTransformer(this.patchClass("guava.IteratorsTransformer"));
        this.classLoader.registerTransformer(this.patchClass("guava.PreconditionsTransformer"));
        this.classLoader.registerTransformer(this.patchClass("ice4j.SocketTransformer"));
        this.classLoader.registerTransformer(this.patchClass("lwjgl.GLFWTransformer"));
    }
    
    public void registerPostTransformers() {
        final boolean debugOpenGLCalls = Constants.SystemProperties.getBoolean(Constants.SystemProperties.OPENGL_CALL);
        if (debugOpenGLCalls) {
            this.classLoader.registerTransformer(this.patchClass("lwjgl.VertexArrayObjectTrackerTransformer"));
        }
        if (debugOpenGLCalls) {
            this.classLoader.registerTransformer(this.patchClass("lwjgl.TextureDebuggerTransformer"));
        }
        if (this.runningVersion.equals("1.8.9") || this.runningVersion.equals("1.12.2")) {
            this.classLoader.registerTransformer(this.patchClass("lwjgl.LWJGLContextTransformer"));
            this.classLoader.registerTransformer(this.patchClass("lwjgl.GLCapabilitiesExtensionsTransformer"));
        }
        this.platformClassloader.getAccessWidener().findAndCreateAccessWidener((ClassLoader)this.classLoader, "labymod", this.runningVersion);
    }
    
    public void setRunningVersion(final String runningVersion) {
        PlatformEnvironment.setRunningVersion(this.runningVersion = runningVersion);
    }
    
    public void setLabyModDevEnvironment(final boolean labyModDevEnvironment) {
        this.labyModDevEnvironment = labyModDevEnvironment;
    }
    
    public void setPlatformClassloader(final PlatformClassloader platformClassloader) {
        PlatformEnvironment.setPlatformClassloader(this.platformClassloader = platformClassloader);
    }
    
    private String transformerClass(final String path) {
        return "net.labymod.core.loader.vanilla.launchwrapper.transformer." + path;
    }
    
    private String patchClass(final String path) {
        return "net.labymod.core.loader.vanilla.launchwrapper.transformer.patch." + path;
    }
}
