// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.thirdparty.optifine;

import org.objectweb.asm.MethodVisitor;
import net.labymod.api.loader.platform.PlatformEnvironment;
import org.objectweb.asm.ClassWriter;
import net.labymod.api.volt.generator.ClassBytecodeComposer;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.thirdparty.optifine.OptiFineConfig;
import java.util.function.Consumer;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.volt.generator.ClassGenerator;

public final class OptiFineConfigClassGenerator extends ClassGenerator
{
    private static final Logging LOGGER;
    private final Consumer<OptiFineConfig> setterConsumer;
    
    OptiFineConfigClassGenerator(final Consumer<OptiFineConfig> setterConsumer) {
        super(OptiFine.class);
        this.setterConsumer = setterConsumer;
    }
    
    @Override
    public Class<?> generateClass(@NotNull final String name, @Nullable final Context context) {
        final Class<?> generatedClass = super.generateClass(name, context);
        try {
            this.setterConsumer.accept((OptiFineConfig)generatedClass.getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]));
        }
        catch (final ReflectiveOperationException exception) {
            OptiFineConfigClassGenerator.LOGGER.warn("OptiFineConfig could not be set, this could cause problems.", exception);
        }
        return generatedClass;
    }
    
    @Override
    public byte[] generateClass(@Nullable final Context context) {
        final ClassBytecodeComposer composer = ClassBytecodeComposer.builder().withName("net/labymod/core/generated/thirdparty/optifine/DefaultOptiFineConfig").withVersion(52).withAccess(17).addInterface("net/labymod/api/thirdparty/optifine/OptiFineConfig").build();
        composer.compose(this::writeClass);
        return composer.getClassData();
    }
    
    private void writeClass(final ClassWriter writer) {
        this.buildConstructor(writer);
        this.buildMethod(writer, "hasShaders", "()Z", methodVisitor -> {
            methodVisitor.visitMethodInsn(184, PlatformEnvironment.isAncientOpenGL() ? "Config" : "net/optifine/Config", "isShaders", "()Z", false);
            methodVisitor.visitInsn(172);
            methodVisitor.visitMaxs(0, 0);
            return;
        });
        this.buildMethod(writer, "getActiveProgramId", "()I", methodVisitor -> {
            methodVisitor.visitFieldInsn(178, "net/optifine/shaders/Shaders", "activeProgramID", "I");
            methodVisitor.visitInsn(172);
            methodVisitor.visitMaxs(0, 0);
        });
    }
    
    private void buildMethod(final ClassWriter writer, final String methodName, final String descriptor, final Consumer<MethodVisitor> visitorConsumer) {
        final MethodVisitor method = writer.visitMethod(1, methodName, descriptor, (String)null, (String[])null);
        method.visitCode();
        visitorConsumer.accept(method);
        method.visitEnd();
    }
    
    private void buildConstructor(final ClassWriter writer) {
        final MethodVisitor methodVisitor = writer.visitMethod(1, "<init>", "()V", (String)null, (String[])null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitMethodInsn(183, "java/lang/Object", "<init>", "()V", false);
        methodVisitor.visitInsn(177);
        methodVisitor.visitMaxs(0, 0);
        methodVisitor.visitEnd();
    }
    
    static {
        LOGGER = Logging.create(OptiFineConfigClassGenerator.class);
    }
}
