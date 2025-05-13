// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.accesswidener;

import java.io.InputStream;
import net.labymod.api.loader.platform.PlatformClassTransformer;
import net.labymod.api.loader.platform.PlatformClassloader;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.accesswidener.AccessWidenerReader;
import net.labymod.core.loader.ReflectLabyModLoader;
import net.labymod.accesswidener.AccessWidener;
import net.labymod.accesswidener.bytecode.BytecodeProvider;
import net.labymod.accesswidener.AccessWidenerBridge;
import java.util.Locale;

public final class AccessWidenerUtil
{
    public static void findAndCreateAccessWidener(final ClassLoader classLoader, final String addonId, final String runningVersion) {
        final InputStream accessWidenerStream = classLoader.getResourceAsStream(String.format(Locale.ROOT, "%s-%s.accesswidener", addonId, runningVersion));
        final AccessWidenerBridge bridge = AccessWidenerBridge.getInstance();
        if (bridge.getBytecodeProvider() == null) {
            bridge.setBytecodeProvider((BytecodeProvider)new ASMBytecodeProvider());
        }
        if (accessWidenerStream != null) {
            final AccessWidener accessWidener = new AccessWidener();
            final String namespace = ReflectLabyModLoader.invokeIsLabyModDevEnvironment() ? "named" : "vanilla";
            accessWidener.setNamespace(namespace);
            final AccessWidenerReader accessWidenerReader = new AccessWidenerReader(accessWidener);
            accessWidenerReader.read(accessWidenerStream, namespace);
            PlatformEnvironment.getPlatformClassloader().registerTransformer(PlatformClassloader.TransformerPhase.NORMAL, new AccessWidenerTransformer(accessWidener));
        }
    }
}
