// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.visibility;

import java.util.function.BooleanSupplier;
import net.labymod.api.configuration.settings.creator.MemberInspector;
import java.lang.annotation.Annotation;

public abstract class VisibilityEvaluator<A extends Annotation>
{
    private final Class<A> annotationClass;
    
    public VisibilityEvaluator(final Class<A> annotationClass) {
        this.annotationClass = annotationClass;
    }
    
    public boolean hasAnnotation(final A a) {
        return a != null;
    }
    
    public abstract BooleanSupplier canSeeElement(final A p0, final MemberInspector p1);
    
    public Class<A> getAnnotationClass() {
        return this.annotationClass;
    }
}
