// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.annotation.processing;

import net.labymod.api.annotation.processing.exception.ProcessingException;
import java.util.Iterator;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.tools.JavaFileManager;
import javax.tools.FileObject;
import net.labymod.api.annotation.processing.util.ProcessorUtil;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Set;
import javax.tools.Diagnostic;
import javax.annotation.processing.Messager;
import net.labymod.api.annotation.processing.util.StringUtil;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.AbstractProcessor;

public abstract class LabyAnnotationProcessor extends AbstractProcessor
{
    private static final String PROJECT_ID_KEY = "projectId";
    private static final String PROJECT_NAME_KEY = "projectName";
    private static final String DEFAULT_PACKAGE_NAME_KEY = "defaultPackageName";
    private static final String VERSIONED_MODULE_KEY = "versionedModule";
    private static final String SOURCE_SET_NAME_KEY = "sourceSetName";
    private static final String RUNNING_VERSION_KEY = "runningVersion";
    private static final String JAVA_VERSION_KEY = "javaVersion";
    private LabyProcessingEnvironment processingEnvironment;
    private String projectId;
    private String projectName;
    private boolean versionedModule;
    private String defaultPackageName;
    private String sourceSetName;
    private String runningVersion;
    private int classVersion;
    
    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.processingEnvironment = new LabyProcessingEnvironment(processingEnv);
        this.projectId = this.getOption("projectId");
        this.projectName = this.getOption("projectName");
        this.classVersion = this.readClassVersion();
        this.defaultPackageName = this.getOption("defaultPackageName");
        this.versionedModule = Boolean.parseBoolean(this.getOption("versionedModule", "false"));
        this.sourceSetName = StringUtil.validateName(this.getOption("sourceSetName"));
        this.runningVersion = this.getOption("runningVersion");
        final Messager messager = processingEnv.getMessager();
        this.validateProject(messager, "projectId", this.projectId);
        this.validateProject(messager, "projectName", this.projectName);
    }
    
    private void validateProject(final Messager messager, final String name, final String value) {
        if (value == null) {
            messager.printMessage(Diagnostic.Kind.WARNING, "Project was not set up correctly, " + name + " is null");
        }
    }
    
    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        final boolean shouldProcess = ProcessorUtil.shouldProcess(this, this.processingEnvironment.getMessager(), () -> this.projectId == null);
        if (!shouldProcess) {
            return false;
        }
        if (roundEnv.processingOver()) {
            this.onProcessingOver();
            return true;
        }
        this.onProcess(annotations, roundEnv);
        return true;
    }
    
    protected abstract void onProcess(final Set<? extends TypeElement> p0, final RoundEnvironment p1);
    
    protected abstract void onProcessingOver();
    
    public FileObject createServiceResource(final String name) {
        return this.processingEnvironment.createServiceResource(name);
    }
    
    public FileObject createCustomServiceResource(final String name) {
        return this.processingEnvironment.createCustomServiceResource(name);
    }
    
    public FileObject createResource(final JavaFileManager.Location location, final String relativeName, final Element... elements) {
        return this.processingEnvironment.createResource(location, relativeName, elements);
    }
    
    public Filer getFiler() {
        return this.processingEnvironment.getFiler();
    }
    
    public void printMessage(final Diagnostic.Kind kind, final CharSequence message) {
        final Messager messager = this.processingEnvironment.getMessager();
        messager.printMessage(kind, message);
    }
    
    public String getOption(final String key) {
        return this.processingEnvironment.getOption(key);
    }
    
    public String getOption(final String key, final String def) {
        return this.processingEnvironment.getOption(key, def);
    }
    
    public String getPackageName() {
        final String name = this._getSourceSetName();
        return this.defaultPackageName + "." + name + ".generated";
    }
    
    public String getProjectId() {
        return this.projectId;
    }
    
    public String getProjectName() {
        return this.projectName;
    }
    
    public boolean isVersionedModule() {
        return this.versionedModule;
    }
    
    public String getDefaultPackageName() {
        return this.defaultPackageName;
    }
    
    public String getSourceSetName() {
        return this.sourceSetName;
    }
    
    public String getRunningVersion() {
        return this.runningVersion;
    }
    
    public int getClassVersion() {
        return this.classVersion;
    }
    
    protected boolean isNotSupportedAnnotationType(final TypeElement element) {
        return !this.isSupportedAnnotationType(element);
    }
    
    protected boolean isSupportedAnnotationType(final TypeElement element) {
        final String name = element.getQualifiedName().toString();
        boolean supported = false;
        final Set<String> supportedAnnotationTypes = this.getSupportedAnnotationTypes();
        for (String s : supportedAnnotationTypes) {
            final boolean wildcard = s.endsWith("*");
            if (wildcard) {
                s = s.substring(0, s.length() - 1);
                if (name.startsWith(s)) {
                    supported = true;
                    break;
                }
            }
            if (name.equals(s)) {
                supported = true;
                break;
            }
        }
        return supported;
    }
    
    private String _getSourceSetName() {
        if (this.versionedModule) {
            return this.sourceSetName;
        }
        return StringUtil.validateName(this.projectName);
    }
    
    private int readClassVersion() {
        final String value = this.getOption("javaVersion", "52.0");
        try {
            return (int)Double.parseDouble(value);
        }
        catch (final NumberFormatException exception) {
            throw new ProcessingException("Invalid class version (" + value, (Throwable)exception);
        }
    }
}
