// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator;

import java.lang.reflect.AnnotatedElement;
import java.lang.annotation.Annotation;

public class AnnotationHolder
{
    private static final AnnotationHolder EMPTY;
    private final Annotation annotation;
    
    private AnnotationHolder(final Annotation annotation) {
        this.annotation = annotation;
    }
    
    public Annotation getAnnotation() {
        return this.annotation;
    }
    
    public boolean isEmpty() {
        return this == AnnotationHolder.EMPTY;
    }
    
    public static AnnotationHolder of(final AnnotatedElement element, final Class<? extends Annotation> annotationClass) {
        final Annotation annotation = element.getAnnotation(annotationClass);
        return (annotation == null) ? AnnotationHolder.EMPTY : new AnnotationHolder(annotation);
    }
    
    static {
        EMPTY = new AnnotationHolder(null);
    }
}
