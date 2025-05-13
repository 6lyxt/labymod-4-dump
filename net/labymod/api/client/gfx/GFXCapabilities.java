// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx;

import net.labymod.api.client.gfx.target.RenderTargetMode;
import java.util.List;
import java.util.function.Consumer;
import net.labymod.api.models.version.Version;
import net.labymod.api.client.crash.CrashReportDetails;

public interface GFXCapabilities extends CrashReportDetails
{
    void initialize();
    
    boolean isSupported(final Version p0);
    
    void forEach(final Consumer<GFXCapabilityEntry<?>> p0);
    
    GFXCapabilityEntry<Integer> getMaximumColorAttachmentCount();
    
    GFXCapabilityEntry<Integer> getMaximumVertexUniformComponents();
    
    GFXCapabilityEntry<Integer> getMaximumFragmentUniformComponents();
    
    GFXCapabilityEntry<Integer> getMaximumRenderbufferSize();
    
    GFXCapabilityEntry<Integer> getMaximumSamples();
    
    GFXCapabilityEntry<Integer> getMaximumVaryingFloats();
    
    GFXCapabilityEntry<Integer> getMaximumVertexAttributes();
    
    GFXCapabilityEntry<Integer> getMaximumTextureImageUnits();
    
    GFXCapabilityEntry<Integer> getMaximumVertexTextureImageUnits();
    
    GFXCapabilityEntry<Integer> getMaximumCombinedTextureImageUnits();
    
    GFXCapabilityEntry<Integer> getMaximumTextureCoords();
    
    GFXCapabilityEntry<Integer> getMaximumUniformBufferBindings();
    
    GFXCapabilityEntry<Integer> getMaximumUniformBlockSize();
    
    GFXCapabilityEntry<Integer> getMaximumVertexOutputComponents();
    
    List<GFXVersion> getVersions();
    
    String dumpOpenGlStates();
    
    boolean isArbVertexArrayObjectSupported();
    
    RenderTargetMode getRenderTargetMode();
    
    boolean isGlPushMatrixAvailable();
    
    boolean isGlPopMatrixAvailable();
}
