// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx;

import net.labymod.api.util.version.SemanticVersion;
import java.util.function.Supplier;
import java.lang.reflect.Field;
import net.labymod.api.util.ThreadSafe;
import java.util.Iterator;
import net.labymod.api.util.reflection.Reflection;
import java.util.function.Consumer;
import net.labymod.api.client.crash.CrashReportAppender;
import java.util.ArrayList;
import net.labymod.api.client.gfx.GFXCapabilityEntry;
import net.labymod.api.client.gfx.GFXVersion;
import java.util.List;
import net.labymod.api.models.version.Version;
import net.labymod.api.client.gfx.GFXCapabilities;

public abstract class AbstractGFXCapabilities implements GFXCapabilities
{
    private static final boolean EMULATE_OPENGL_VERSION = false;
    private static final Version EMULATED_VERSION;
    protected boolean initialized;
    protected List<GFXVersion> versions;
    protected GFXCapabilityEntry<Integer> maximumColorAttachments;
    protected GFXCapabilityEntry<Integer> maximumRenderbufferSize;
    protected GFXCapabilityEntry<Integer> maximumSamples;
    protected GFXCapabilityEntry<Integer> maximumVertexUniformComponents;
    protected GFXCapabilityEntry<Integer> maximumFragmentUniformComponents;
    protected GFXCapabilityEntry<Integer> maximumVaryingFloats;
    protected GFXCapabilityEntry<Integer> maximumVertexAttributes;
    protected GFXCapabilityEntry<Integer> maximumTextureImageUnits;
    protected GFXCapabilityEntry<Integer> maximumVertexTextureImageUnits;
    protected GFXCapabilityEntry<Integer> maximumCombinedTextureImageUnits;
    protected GFXCapabilityEntry<Integer> maximumTextureCoords;
    protected GFXCapabilityEntry<Integer> maximumUniformBufferBindings;
    protected GFXCapabilityEntry<Integer> maximumUniformBlockSize;
    protected GFXCapabilityEntry<Integer> maximumVertexOutputComponents;
    
    public AbstractGFXCapabilities() {
        this.versions = new ArrayList<GFXVersion>();
    }
    
    @Override
    public void appendDetails(final CrashReportAppender appender) {
        appender.setSubtitle("Framebuffer");
        this.setDetail(appender, this.maximumColorAttachments);
        this.setDetail(appender, this.maximumRenderbufferSize);
        this.setDetail(appender, this.maximumSamples);
        appender.setSubtitle("Shader");
        this.setDetail(appender, this.maximumVertexUniformComponents);
        this.setDetail(appender, this.maximumFragmentUniformComponents);
        this.setDetail(appender, this.maximumVaryingFloats);
        this.setDetail(appender, this.maximumVertexAttributes);
        this.setDetail(appender, this.maximumTextureImageUnits);
        this.setDetail(appender, this.maximumVertexTextureImageUnits);
        this.setDetail(appender, this.maximumCombinedTextureImageUnits);
        this.setDetail(appender, this.maximumTextureCoords);
        this.setDetail(appender, this.maximumUniformBufferBindings);
        this.setDetail(appender, this.maximumUniformBlockSize);
        this.setDetail(appender, this.maximumVertexOutputComponents);
    }
    
    @Override
    public void forEach(final Consumer<GFXCapabilityEntry<?>> consumer) {
        Reflection.getFields(this.getClass(), false, field -> {
            final Class<?> type = field.getType();
            if (type == GFXCapabilityEntry.class) {
                final Object value = Reflection.invokeGetterField(this, field);
                if (value instanceof GFXCapabilityEntry) {
                    final GFXCapabilityEntry<?> entry = (GFXCapabilityEntry<?>)value;
                    if (!entry.hidden()) {
                        consumer.accept(entry);
                    }
                }
            }
        });
    }
    
    @Override
    public boolean isSupported(final Version source) {
        for (final GFXVersion version : this.versions) {
            if (version.isSupported() && version.isCompatible(source)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public GFXCapabilityEntry<Integer> getMaximumColorAttachmentCount() {
        return this.maximumColorAttachments;
    }
    
    @Override
    public GFXCapabilityEntry<Integer> getMaximumVertexUniformComponents() {
        return this.maximumVertexUniformComponents;
    }
    
    @Override
    public GFXCapabilityEntry<Integer> getMaximumFragmentUniformComponents() {
        return this.maximumFragmentUniformComponents;
    }
    
    @Override
    public GFXCapabilityEntry<Integer> getMaximumRenderbufferSize() {
        return this.maximumRenderbufferSize;
    }
    
    @Override
    public GFXCapabilityEntry<Integer> getMaximumSamples() {
        return this.maximumSamples;
    }
    
    @Override
    public GFXCapabilityEntry<Integer> getMaximumVaryingFloats() {
        return this.maximumVaryingFloats;
    }
    
    @Override
    public GFXCapabilityEntry<Integer> getMaximumVertexAttributes() {
        return this.maximumVertexAttributes;
    }
    
    @Override
    public GFXCapabilityEntry<Integer> getMaximumTextureImageUnits() {
        return this.maximumTextureImageUnits;
    }
    
    @Override
    public GFXCapabilityEntry<Integer> getMaximumVertexTextureImageUnits() {
        return this.maximumVertexTextureImageUnits;
    }
    
    @Override
    public GFXCapabilityEntry<Integer> getMaximumCombinedTextureImageUnits() {
        return this.maximumCombinedTextureImageUnits;
    }
    
    @Override
    public GFXCapabilityEntry<Integer> getMaximumTextureCoords() {
        return this.maximumTextureCoords;
    }
    
    @Override
    public GFXCapabilityEntry<Integer> getMaximumUniformBufferBindings() {
        return this.maximumUniformBufferBindings;
    }
    
    @Override
    public GFXCapabilityEntry<Integer> getMaximumUniformBlockSize() {
        return this.maximumUniformBlockSize;
    }
    
    @Override
    public GFXCapabilityEntry<Integer> getMaximumVertexOutputComponents() {
        return this.maximumVertexOutputComponents;
    }
    
    @Override
    public List<GFXVersion> getVersions() {
        return this.versions;
    }
    
    @Override
    public String dumpOpenGlStates() {
        if (!ThreadSafe.isRenderThread()) {
            return "Could not dump OpenGL states";
        }
        return this.onDumpOpenGlStates();
    }
    
    protected abstract String onDumpOpenGlStates();
    
    protected void addVersion(final GFXVersion version) {
        this.versions.add(version);
    }
    
    private void _initialize() {
        if (this.initialized) {
            return;
        }
        this.initialize();
    }
    
    protected int getInteger(final char c) {
        return Integer.parseInt(String.valueOf(c));
    }
    
    @Deprecated
    protected boolean readField(final Field field, final Supplier<Object> capabilitiesSupplier) {
        field.setAccessible(true);
        try {
            return field.getBoolean(capabilitiesSupplier.get());
        }
        catch (final Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
    
    protected abstract GFXCapabilityEntry<Integer> getInt(final String p0, final int p1);
    
    private void setDetail(final CrashReportAppender appender, final GFXCapabilityEntry<?> entry) {
        if (entry == null) {
            return;
        }
        appender.setDetail(entry.key(), entry.value());
    }
    
    static {
        EMULATED_VERSION = new SemanticVersion(2, 1);
    }
}
