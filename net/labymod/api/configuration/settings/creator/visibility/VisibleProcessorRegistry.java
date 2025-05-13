// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.visibility;

import java.util.Iterator;
import java.util.function.BooleanSupplier;
import net.labymod.api.configuration.settings.creator.MemberInspector;
import java.util.ArrayList;
import net.labymod.api.LabyAPI;
import java.lang.annotation.Annotation;
import java.util.List;

public class VisibleProcessorRegistry
{
    private final List<VisibilityEvaluator<? extends Annotation>> processors;
    private final LabyAPI labyAPI;
    
    public VisibleProcessorRegistry(final LabyAPI labyAPI) {
        this.processors = new ArrayList<VisibilityEvaluator<? extends Annotation>>();
        this.labyAPI = labyAPI;
        this.registerDefaults();
    }
    
    private void registerDefaults() {
        this.registerProcessor(new ModRequirementVisibilityEvaluator(this.labyAPI));
        this.registerProcessor(new OptiFineRequirementVisibilityEvaluator());
        this.registerProcessor(new ModLoaderVisibilityEvaluator(this.labyAPI));
    }
    
    public void registerProcessor(final VisibilityEvaluator<?> processor) {
        this.processors.add((VisibilityEvaluator<? extends Annotation>)processor);
    }
    
    public BooleanSupplier canSeeElement(final MemberInspector element) {
        for (final VisibilityEvaluator processor : this.processors) {
            final Annotation annotation = element.getAnnotation(processor.getAnnotationClass());
            if (!processor.hasAnnotation(annotation)) {
                continue;
            }
            return processor.canSeeElement(annotation, element);
        }
        return null;
    }
}
