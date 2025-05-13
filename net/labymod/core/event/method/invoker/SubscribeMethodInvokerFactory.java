// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.method.invoker;

import java.util.Locale;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ClassWriter;
import net.labymod.api.volt.generator.ClassBytecodeComposer;
import org.objectweb.asm.Type;
import org.jetbrains.annotations.Nullable;
import net.labymod.core.event.method.SubscribeMethodInvoker;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.volt.generator.ClassGenerator;

public class SubscribeMethodInvokerFactory extends ClassGenerator
{
    private static final Logging LOGGER;
    private static final String GENERATED_ASM_PACKAGE = "net.labymod.autogen.events.generated";
    private static final String[] GENERATED_SUBSCRIBE_METHOD_INVOKER;
    private final AtomicInteger id;
    private final String sessionId;
    
    public SubscribeMethodInvokerFactory() {
        this(SubscribeMethodInvokerFactory.class.getClassLoader());
    }
    
    public SubscribeMethodInvokerFactory(final ClassLoader parent) {
        super(parent);
        this.id = new AtomicInteger();
        this.sessionId = UUID.randomUUID().toString().replace("-", "");
    }
    
    public SubscribeMethodInvoker create(@NotNull final Method method, @NotNull final Class<?> eventClass) {
        final String className = this.generateInvokerName(method.getDeclaringClass(), method, eventClass);
        Class<?> generatedClass;
        try {
            generatedClass = this.generateClass(className.replace('/', '.'), new SubscribeMethodClassGeneratorContext(className, method, eventClass));
        }
        catch (final Throwable throwable) {
            SubscribeMethodInvokerFactory.LOGGER.error("{} for {}.{} could not be generated.", SubscribeMethodInvoker.class.getName(), method.getDeclaringClass().getName(), method.getName(), throwable);
            return null;
        }
        try {
            return (SubscribeMethodInvoker)generatedClass.getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
        }
        catch (final ReflectiveOperationException exception) {
            SubscribeMethodInvokerFactory.LOGGER.error("Constructor of the generated class \"{}\" could not be called", generatedClass.getName(), exception);
            return null;
        }
    }
    
    @Override
    public byte[] generateClass(@Nullable final Context context) {
        if (context instanceof final SubscribeMethodClassGeneratorContext generatorContext) {
            final Method method = generatorContext.method();
            final Class<?> eventClass = generatorContext.eventClass();
            final Class<?> listener = method.getDeclaringClass();
            final String listenerName = Type.getInternalName((Class)listener);
            final String className = generatorContext.className();
            final ClassBytecodeComposer composer = ClassBytecodeComposer.builder().withName(className.replace('.', '/')).addInterfaces(SubscribeMethodInvokerFactory.GENERATED_SUBSCRIBE_METHOD_INVOKER).build();
            composer.compose(writer -> this.writeClass(writer, listenerName, eventClass, method));
            return composer.getClassData();
        }
        throw new IllegalArgumentException();
    }
    
    private void writeClass(final ClassWriter writer, final String listenerName, final Class<?> eventClass, final Method method) {
        MethodVisitor methodVisitor = writer.visitMethod(1, "<init>", "()V", (String)null, (String[])null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitMethodInsn(183, "java/lang/Object", "<init>", "()V", false);
        methodVisitor.visitInsn(177);
        methodVisitor.visitMaxs(0, 0);
        methodVisitor.visitEnd();
        methodVisitor = writer.visitMethod(1, "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)V", (String)null, (String[])null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitTypeInsn(192, listenerName);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitTypeInsn(192, Type.getInternalName((Class)eventClass));
        methodVisitor.visitMethodInsn(182, listenerName, method.getName(), Type.getMethodDescriptor(method), false);
        methodVisitor.visitInsn(177);
        methodVisitor.visitMaxs(0, 0);
        methodVisitor.visitEnd();
    }
    
    @NotNull
    private String generateInvokerName(@NotNull final Class<?> listener, @NotNull final Method method, @NotNull final Class<?> eventClass) {
        return String.format(Locale.ROOT, "%s.%s.%s-%s-%s-%d", "net.labymod.autogen.events.generated", this.sessionId, listener.getSimpleName(), method.getName(), eventClass.getSimpleName(), this.id.incrementAndGet());
    }
    
    static {
        LOGGER = Logging.create(SubscribeMethodInvokerFactory.class);
        GENERATED_SUBSCRIBE_METHOD_INVOKER = new String[] { Type.getInternalName((Class)SubscribeMethodInvoker.class) };
    }
    
    record SubscribeMethodClassGeneratorContext(@NotNull String className, @NotNull Method method, @NotNull Class<?> eventClass) implements Context {
        @NotNull
        public String className() {
            return this.className;
        }
        
        @NotNull
        public Method method() {
            return this.method;
        }
        
        @NotNull
        public Class<?> eventClass() {
            return this.eventClass;
        }
    }
}
