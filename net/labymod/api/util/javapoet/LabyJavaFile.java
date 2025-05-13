// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.javapoet;

import com.squareup.javapoet.ClassName;
import javax.tools.JavaFileObject;
import javax.annotation.processing.Filer;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.io.IOException;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.JavaFile;

public class LabyJavaFile
{
    private final JavaFile delegate;
    
    private LabyJavaFile(final JavaFile delegate) {
        this.delegate = delegate;
    }
    
    public static Builder builder(final TypeSpec spec, final String packageName, final Object... args) {
        return new Builder(spec, packageName, args);
    }
    
    public static LabyJavaFile of(final TypeSpec spec, final String packageName, final Object... args) {
        return builder(spec, packageName, args).build();
    }
    
    public String getClassName() {
        return this.delegate.packageName + "." + this.delegate.typeSpec.name;
    }
    
    public void writeTo(final Appendable out) throws IOException {
        this.delegate.writeTo(out);
    }
    
    public void writeTo(final Path directory) throws IOException {
        this.delegate.writeTo(directory);
    }
    
    public void writeTo(final Path directory, final Charset charset) throws IOException {
        this.delegate.writeTo(directory, charset);
    }
    
    public Path writeToPath(final Path directory) throws IOException {
        return this.delegate.writeToPath(directory);
    }
    
    public Path writeToPath(final Path directory, final Charset charset) throws IOException {
        return this.delegate.writeToPath(directory, charset);
    }
    
    public void writeTo(final File directory) throws IOException {
        this.delegate.writeTo(directory);
    }
    
    public File writeToFile(final File directory) throws IOException {
        return this.delegate.writeToFile(directory);
    }
    
    public void writeTo(final Filer filer) throws IOException {
        this.delegate.writeTo(filer);
    }
    
    public JavaFileObject toJavaFileObject() {
        return this.delegate.toJavaFileObject();
    }
    
    public JavaFile.Builder toBuilder() {
        return this.delegate.toBuilder();
    }
    
    public static class Builder
    {
        private final JavaFile.Builder builder;
        
        public Builder(final TypeSpec spec, final String packageName, final Object... args) {
            this.builder = JavaFile.builder(String.format(packageName, args), spec);
        }
        
        public Builder addFileComment(final String format, final Object... args) {
            this.builder.addFileComment(format, args);
            return this;
        }
        
        public Builder addStaticImport(final Enum<?> constant) {
            this.builder.addStaticImport((Enum)constant);
            return this;
        }
        
        public Builder addStaticImport(final Class<?> clazz, final String... names) {
            this.builder.addStaticImport((Class)clazz, names);
            return this;
        }
        
        public Builder addStaticImport(final ClassName className, final String... names) {
            this.builder.addStaticImport(className, names);
            return this;
        }
        
        public Builder skipJavaLangImports(final boolean skipJavaLangImports) {
            this.builder.skipJavaLangImports(skipJavaLangImports);
            return this;
        }
        
        public Builder indent(final String indent) {
            this.builder.indent(indent);
            return this;
        }
        
        public LabyJavaFile build() {
            return new LabyJavaFile(this.builder.build());
        }
    }
}
