// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.annotation.processing;

import java.io.IOException;
import net.labymod.api.annotation.processing.exception.ProcessingException;
import javax.tools.JavaFileManager;
import javax.lang.model.element.Element;
import javax.tools.StandardLocation;
import javax.tools.FileObject;
import java.util.Locale;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Types;
import javax.lang.model.util.Elements;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import java.util.Map;
import javax.annotation.processing.ProcessingEnvironment;

public class LabyProcessingEnvironment implements ProcessingEnvironment
{
    private final ProcessingEnvironment delegate;
    
    public LabyProcessingEnvironment(final ProcessingEnvironment delegate) {
        this.delegate = delegate;
    }
    
    @Override
    public Map<String, String> getOptions() {
        return this.delegate.getOptions();
    }
    
    @Override
    public Messager getMessager() {
        return this.delegate.getMessager();
    }
    
    @Override
    public Filer getFiler() {
        return this.delegate.getFiler();
    }
    
    @Override
    public Elements getElementUtils() {
        return this.delegate.getElementUtils();
    }
    
    @Override
    public Types getTypeUtils() {
        return this.delegate.getTypeUtils();
    }
    
    @Override
    public SourceVersion getSourceVersion() {
        return this.delegate.getSourceVersion();
    }
    
    @Override
    public Locale getLocale() {
        return this.delegate.getLocale();
    }
    
    @Override
    public boolean isPreviewEnabled() {
        return this.delegate.isPreviewEnabled();
    }
    
    public String getOption(final String key) {
        return this.getOption(key, null);
    }
    
    public String getOption(final String key, final String def) {
        return this.getOptions().getOrDefault(key, def);
    }
    
    public FileObject createServiceResource(final String name) {
        return this.createResource((JavaFileManager.Location)StandardLocation.CLASS_OUTPUT, "META-INF/services/" + name, new Element[0]);
    }
    
    public FileObject createCustomServiceResource(final String name) {
        return this.createResource((JavaFileManager.Location)StandardLocation.CLASS_OUTPUT, "META-INF/custom-services/" + name + ".json", new Element[0]);
    }
    
    public FileObject createResource(final JavaFileManager.Location location, final String relativeName, final Element... elements) {
        final Filer filer = this.getFiler();
        try {
            return filer.createResource(location, "", relativeName, elements);
        }
        catch (final IOException exception) {
            throw new ProcessingException("Could not create resource", exception);
        }
    }
}
