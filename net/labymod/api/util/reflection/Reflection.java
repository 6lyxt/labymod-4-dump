// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.reflection;

import java.util.Objects;
import java.util.HashMap;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Arrays;
import net.labymod.api.loader.platform.PlatformEnvironment;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.util.Collection;
import net.labymod.api.util.MethodOrder;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.function.Consumer;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.util.Locale;
import java.lang.invoke.MethodHandles;
import net.labymod.api.util.reflection.exception.ReflectionException;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public final class Reflection
{
    private static final Map<Class<?>, List<Class<?>>> CLASS_TREES;
    private static final Map<FieldKey, Field> FIELDS;
    
    private Reflection() {
    }
    
    public static Class<?> getNoneAnonymousClass(Class<?> cls) {
        while (cls.isAnonymousClass()) {
            cls = cls.getSuperclass();
        }
        return cls;
    }
    
    @NotNull
    public static List<Class<?>> getClassTree(final Class<?> clazz) {
        List<Class<?>> classes = null;
        try {
            classes = Reflection.CLASS_TREES.get(clazz);
            if (classes != null) {
                return classes;
            }
            classes = getClassTree0(clazz);
            Reflection.CLASS_TREES.put(clazz, classes);
            return classes;
        }
        catch (final Exception exception) {
            System.err.println("Failed to cache class tree for " + clazz.getName());
            exception.printStackTrace(System.err);
            return (classes == null) ? getClassTree0(clazz) : classes;
        }
    }
    
    private static List<Class<?>> getClassTree0(Class<?> clazz) {
        final List<Class<?>> classes = new ArrayList<Class<?>>();
        classes.add(clazz);
        while (clazz != Object.class) {
            clazz = clazz.getSuperclass();
            classes.add(clazz);
        }
        return classes;
    }
    
    @Nullable
    public static Field getField(final Class<?> clazz, final String name) {
        final List<Class<?>> classTree = getClassTree(clazz);
        int cacheCount = 0;
        for (final Class<?> treeClass : classTree) {
            final FieldKey fieldKey = new FieldKey(treeClass, name);
            if (Reflection.FIELDS.containsKey(fieldKey)) {
                ++cacheCount;
                final Field field = Reflection.FIELDS.get(fieldKey);
                if (field != null) {
                    return field;
                }
                continue;
            }
        }
        if (cacheCount == classTree.size()) {
            return null;
        }
        for (final Class<?> treeClass : classTree) {
            try {
                final Field field2 = treeClass.getDeclaredField(name);
                field2.setAccessible(true);
                Reflection.FIELDS.put(new FieldKey(treeClass, name), field2);
                return field2;
            }
            catch (final Exception ex) {
                continue;
            }
            break;
        }
        for (final Class<?> treeClass : classTree) {
            Reflection.FIELDS.put(new FieldKey(treeClass, name), null);
        }
        return null;
    }
    
    public static <T> T getFieldValue(final Object instance, final String fieldName) {
        final Field field = getField(instance.getClass(), fieldName);
        if (field == null) {
            return null;
        }
        return getFieldValue(instance, field);
    }
    
    public static <T> T getFieldValue(final Object instance, final Field field) {
        try {
            return (T)getUnreflectFieldValue(instance, field);
        }
        catch (final ReflectionException exception) {
            field.setAccessible(true);
            try {
                return (T)field.get(instance);
            }
            catch (final Exception e) {
                return null;
            }
        }
    }
    
    public static <T> T getUnreflectFieldValue(final Object instance, final Field field) throws ReflectionException {
        try {
            final MethodHandle methodHandle = MethodHandles.publicLookup().unreflectGetter(field);
            return (T)methodHandle.invokeWithArguments(instance);
        }
        catch (final Throwable throwable) {
            throw new ReflectionException(String.format(Locale.ROOT, "Could not get field value %s.%s", instance.getClass().getName(), field.getName()), throwable);
        }
    }
    
    public static <T extends Annotation> T getAnnotation(final Class<?> clazz, final Class<T> annotationClass) {
        final List<Class<?>> classTree = getClassTree(clazz);
        for (final Class<?> cls : classTree) {
            if (cls.isAnnotationPresent(annotationClass)) {
                return cls.getAnnotation(annotationClass);
            }
        }
        return null;
    }
    
    public static void getFields(final Class<?> cls, final boolean reversed, final Consumer<Field> fieldConsumer) {
        if (fieldConsumer == null) {
            return;
        }
        final List<Class<?>> classTree = getClassTree(cls);
        if (reversed) {
            for (int index = classTree.size(); index > 0; --index) {
                final Class<?> clazz = classTree.get(index - 1);
                for (final Field declaredField : clazz.getDeclaredFields()) {
                    fieldConsumer.accept(declaredField);
                }
            }
        }
        else {
            for (final Class<?> clazz : classTree) {
                for (final Field declaredField : clazz.getDeclaredFields()) {
                    fieldConsumer.accept(declaredField);
                }
            }
        }
    }
    
    public static void getMethods(final Class<?> cls, final boolean reversed, final Consumer<Method> methodConsumer) {
        final List<Class<?>> classTree = getClassTree(cls);
        if (methodConsumer == null) {
            return;
        }
        if (reversed) {
            for (int index = classTree.size(); index > 0; --index) {
                final Class<?> clazz = classTree.get(index - 1);
                if (clazz != Object.class) {
                    for (final Method declaredMethod : clazz.getDeclaredMethods()) {
                        methodConsumer.accept(declaredMethod);
                    }
                }
            }
        }
        else {
            for (final Class<?> clazz : classTree) {
                if (clazz == Object.class) {
                    continue;
                }
                for (final Method declaredMethod : clazz.getDeclaredMethods()) {
                    methodConsumer.accept(declaredMethod);
                }
            }
        }
    }
    
    public static <T extends Member & AnnotatedElement> void getMembers(final Class<?> cls, final boolean reversed, final Consumer<T> methodConsumer) {
        final List<Class<?>> classTree = getClassTree(cls);
        if (methodConsumer == null) {
            return;
        }
        if (reversed) {
            for (int index = classTree.size(); index > 0; --index) {
                final Class<?> clazz = classTree.get(index - 1);
                getMembers(clazz, methodConsumer);
            }
        }
        else {
            for (final Class<?> clazz : classTree) {
                getMembers(clazz, methodConsumer);
            }
        }
    }
    
    private static <T extends Member & AnnotatedElement> void getMembers(final Class<?> clazz, final Consumer<T> memberConsumer) {
        if (clazz == Object.class) {
            return;
        }
        final Collection<Method> orderedMethods = new ArrayList<Method>();
        for (final Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(MethodOrder.class)) {
                orderedMethods.add(method);
            }
        }
        for (final Field field : clazz.getDeclaredFields()) {
            sort(orderedMethods, field, memberConsumer);
        }
    }
    
    private static <T extends Member & AnnotatedElement> void sort(final Collection<Method> orderedMethods, final Member member, final Consumer<T> memberConsumer) {
        for (final Method orderedMethod : orderedMethods) {
            if (orderedMethod != member && orderedMethod.getAnnotation(MethodOrder.class).before().equals(member.getName())) {
                sort(orderedMethods, orderedMethod, (Consumer<Member>)memberConsumer);
            }
        }
        memberConsumer.accept((T)member);
        for (final Method method : orderedMethods) {
            if (method != member && method.getAnnotation(MethodOrder.class).after().equals(member.getName())) {
                sort(orderedMethods, method, (Consumer<Member>)memberConsumer);
            }
        }
    }
    
    public static Constructor<?> searchEmptyConstructor(final Class<?> cls) {
        Constructor<?> emptyConstructor = null;
        for (final Constructor<?> declaredConstructor : cls.getDeclaredConstructors()) {
            if (declaredConstructor.getParameterCount() == 0) {
                emptyConstructor = declaredConstructor;
                break;
            }
        }
        return emptyConstructor;
    }
    
    public static MethodHandle findVirtual(final Class<?> reference, final String name, final MethodType methodType) throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.publicLookup().findVirtual(reference, name, methodType);
    }
    
    public static MethodHandle findStatic(final Class<?> reference, final String name, final MethodType methodType) throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.publicLookup().findStatic(reference, name, methodType);
    }
    
    public static <T> T invokeGetterField(final Object instance, final Field field) {
        try {
            return (T)invokeUnreflectGetterField(instance, field);
        }
        catch (final ReflectionException exception) {
            field.setAccessible(true);
            try {
                return (T)field.get(instance);
            }
            catch (final Exception e) {
                return null;
            }
        }
    }
    
    public static void invokeSetterField(final Object instance, final Field field, final Object value) {
        try {
            invokeUnreflectSetterField(instance, field, value);
        }
        catch (final ReflectionException exception) {
            field.setAccessible(true);
            try {
                field.set(instance, value);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void invokeUnreflectSetterField(final Object instance, final Field field, final Object value) throws ReflectionException {
        try {
            final MethodHandle methodHandle = MethodHandles.publicLookup().unreflectSetter(field);
            methodHandle.invokeWithArguments(instance, value);
        }
        catch (final Throwable throwable) {
            throw new ReflectionException(String.format(Locale.ROOT, "Could not invoke setter field %s.%s (Value: %s)", instance.getClass().getName(), field.getName(), value), throwable);
        }
    }
    
    public static <T> T invokeUnreflectGetterField(final Object instance, final Field field) throws ReflectionException {
        try {
            final MethodHandle methodHandle = MethodHandles.publicLookup().unreflectGetter(field);
            return (T)methodHandle.invokeWithArguments(instance);
        }
        catch (final Throwable throwable) {
            throw new ReflectionException(String.format(Locale.ROOT, "Could not invoke getter field %s.%s", instance.getClass().getName(), field.getName()), throwable);
        }
    }
    
    @Deprecated(forRemoval = true, since = "4.2.32")
    @Nullable
    public static Class<?> findCallerClass() {
        return findCallerClass(2);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.32")
    @Nullable
    public static Class<?> findCallerClass(final int id) {
        try {
            return PlatformEnvironment.getPlatformClassloader().findClass(Thread.currentThread().getStackTrace()[id].getClassName());
        }
        catch (final ClassNotFoundException exception) {
            return null;
        }
    }
    
    public static <T> T instantiateWithArgs(final Class<?> owner, final Object... constructionArguments) throws ReflectiveOperationException {
        final Class<?>[] parameterTypes = buildParameterTypes(constructionArguments);
        Constructor<?> targetConstructor = null;
        for (final Constructor<?> declaredConstructor : owner.getDeclaredConstructors()) {
            final int parameterCount = declaredConstructor.getParameterCount();
            if (parameterCount == constructionArguments.length && Arrays.equals(declaredConstructor.getParameterTypes(), parameterTypes)) {
                targetConstructor = declaredConstructor;
                break;
            }
        }
        if (targetConstructor == null) {
            throw new IllegalStateException("No constructor found in " + owner.getName() + " with " + (String)Arrays.stream(parameterTypes).map((Function<? super Class<?>, ?>)Class::getName).collect((Collector<? super Object, ?, String>)Collectors.joining(", ")) + " parameters");
        }
        targetConstructor.setAccessible(true);
        return (T)targetConstructor.newInstance(constructionArguments);
    }
    
    public static Class<?>[] buildParameterTypes(final Object[] arguments) {
        final Class<?>[] parameterTypes = new Class[arguments.length];
        for (int index = 0; index < parameterTypes.length; ++index) {
            parameterTypes[index] = arguments[index].getClass();
        }
        return parameterTypes;
    }
    
    public static boolean isType(final Type type, final Class<?>... classes) {
        for (final Class<?> cls : classes) {
            if (type == cls) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isMethodOverridden(final Class<?> superClass, final Class<?> subClass, final String name, final Class<?>... parameters) {
        try {
            final Method superClassMethod = superClass.getDeclaredMethod(name, parameters);
            final Method subClassMethod = subClass.getDeclaredMethod(name, parameters);
            return !Modifier.isAbstract(superClassMethod.getModifiers()) && !subClassMethod.equals(superClassMethod);
        }
        catch (final NoSuchMethodException exception) {
            return false;
        }
    }
    
    static {
        CLASS_TREES = new HashMap<Class<?>, List<Class<?>>>();
        FIELDS = new HashMap<FieldKey, Field>();
    }
    
    record FieldKey(Class<?> clazz, String field) {
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final FieldKey fieldKey = (FieldKey)o;
            return Objects.equals(this.clazz, fieldKey.clazz) && Objects.equals(this.field, fieldKey.field);
        }
        
        @Override
        public int hashCode() {
            int result = (this.clazz != null) ? this.clazz.hashCode() : 0;
            result = 31 * result + ((this.field != null) ? this.field.hashCode() : 0);
            return result;
        }
    }
}
