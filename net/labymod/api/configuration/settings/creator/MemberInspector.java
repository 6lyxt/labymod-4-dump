// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator;

import java.util.function.Function;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.lang.reflect.AnnotatedElement;

public class MemberInspector
{
    private final AnnotatedElement member;
    private final String name;
    private final Map<Class<? extends Annotation>, AnnotationHolder> annotationCache;
    
    public MemberInspector(final AnnotatedElement member) {
        this.annotationCache = new HashMap<Class<? extends Annotation>, AnnotationHolder>();
        if (member instanceof final Member reflectiveMember) {
            this.member = member;
            this.name = reflectiveMember.getName();
            return;
        }
        throw new IllegalArgumentException(member.getClass().getName() + " is not an instance of " + Member.class.getName());
    }
    
    public String getName() {
        return this.name;
    }
    
    public <A extends Annotation> A getAnnotation(final Class<A> annotationClass) {
        final AnnotationHolder annotation = this.annotationCache.computeIfAbsent(annotationClass, cls -> AnnotationHolder.of(this.member, cls));
        return (A)(annotation.isEmpty() ? null : annotation.getAnnotation());
    }
    
    public <A extends Annotation, T> T get(final Class<A> annotatoinClass, final Function<A, T> mappingFunction) {
        return this.getOrElse(annotatoinClass, mappingFunction, (T)null);
    }
    
    public <A extends Annotation, T> T getOrElse(final Class<A> annotationClass, final Function<A, T> mappingFunction, final T defaultValue) {
        final A annotation = this.getAnnotation(annotationClass);
        if (annotation == null) {
            return defaultValue;
        }
        return mappingFunction.apply(annotation);
    }
    
    public <A extends Annotation> boolean isAnnotationPresent(final Class<A> annotationClass) {
        final A annotation = this.getAnnotation(annotationClass);
        return annotation != null;
    }
    
    public <A extends Annotation> A orElse(final Class<A> annotationClass, final A defaultValue) {
        final A annotation = this.getAnnotation(annotationClass);
        return (annotation == null) ? defaultValue : annotation;
    }
    
    public AnnotatedElement member() {
        return this.member;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
