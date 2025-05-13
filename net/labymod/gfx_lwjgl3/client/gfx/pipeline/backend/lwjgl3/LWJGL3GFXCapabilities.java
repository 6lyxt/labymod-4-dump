// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3;

import org.lwjgl.opengl.GL11;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.util.PrettyStringBuilder;
import net.labymod.api.client.gfx.GFXCapabilityEntry;
import java.lang.reflect.Field;
import net.labymod.api.client.gfx.GFXVersion;
import net.labymod.api.util.version.SemanticVersion;
import net.labymod.api.models.version.Version;
import net.labymod.api.client.gfx.opengl.NamedOpenGLVersion;
import org.lwjgl.opengl.GL;
import net.labymod.api.client.gfx.target.RenderTargetMode;
import org.lwjgl.opengl.GLCapabilities;
import net.labymod.core.client.gfx.AbstractGFXCapabilities;

class LWJGL3GFXCapabilities extends AbstractGFXCapabilities
{
    private static final boolean AVOID_WEIRD_DRIVER_BUG = true;
    private GLCapabilities capabilities;
    private RenderTargetMode renderTargetMode;
    private long glPushMatrix;
    private long glPopMatrix;
    
    LWJGL3GFXCapabilities() {
        this.renderTargetMode = RenderTargetMode.BASE;
    }
    
    @Override
    public void initialize() {
        if (this.initialized) {
            return;
        }
        this.initialized = true;
        this.collectSupportedOpenGLVersions(this.capabilities = GL.getCapabilities());
        this.maximumVertexUniformComponents = this.getInt("Max. Vertex Uniform Components", 35658);
        this.maximumVaryingFloats = this.getInt("Max. Varying Floats", 35659);
        this.maximumVertexAttributes = this.getInt("Max. Vertex Attributes", 34921);
        this.maximumTextureImageUnits = this.getInt("Max. Texture Image Units", 34930);
        this.maximumVertexTextureImageUnits = this.getInt("Max. Vertex Texture Image Units", 35660);
        this.maximumCombinedTextureImageUnits = this.getInt("Max. Combined Texture Image Units", 35661);
        this.maximumTextureCoords = this.getInt("Max. Texture Coords", 34929);
        this.maximumFragmentUniformComponents = this.getInt("Max. Fragment Uniform Components", 35657);
        if (this.isSupported(NamedOpenGLVersion.GL30)) {
            this.maximumColorAttachments = this.getInt("Max. Color Attachments", 36063);
            this.maximumRenderbufferSize = this.getInt("Max. Renderbuffer Size", 34024);
            this.maximumSamples = this.getInt("Max. Samples", 36183);
        }
        if (this.isSupported(NamedOpenGLVersion.GL31)) {
            this.maximumUniformBufferBindings = this.getInt("Max. Uniform Buffer Bindings", 35375);
            this.maximumUniformBlockSize = this.getInt("Max. Uniform Block Size", 35376);
        }
        if (this.isSupported(NamedOpenGLVersion.GL32)) {
            this.maximumVertexOutputComponents = this.getInt("Max. Vertex Output Components", 37154);
        }
        this.glPushMatrix = this.capabilities.glPushMatrix;
        this.glPopMatrix = this.capabilities.glPopMatrix;
        this.findRenderTargetMode();
    }
    
    private void findRenderTargetMode() {
        if (NamedOpenGLVersion.GL30.isSupported()) {
            this.renderTargetMode = RenderTargetMode.BASE;
        }
        else if (this.capabilities.GL_ARB_framebuffer_object) {
            this.renderTargetMode = RenderTargetMode.ARB;
        }
        else {
            if (!this.capabilities.GL_EXT_framebuffer_object) {
                throw new IllegalStateException("Could not initialize framebuffer support.");
            }
            this.renderTargetMode = RenderTargetMode.EXT;
        }
    }
    
    @Override
    public boolean isGlPushMatrixAvailable() {
        return this.glPushMatrix != 0L;
    }
    
    @Override
    public boolean isGlPopMatrixAvailable() {
        return this.glPopMatrix != 0L;
    }
    
    @Override
    public boolean isArbVertexArrayObjectSupported() {
        return this.capabilities.GL_ARB_vertex_array_object && !NamedOpenGLVersion.GL30.isSupported();
    }
    
    @Override
    public RenderTargetMode getRenderTargetMode() {
        return this.renderTargetMode;
    }
    
    private void collectSupportedOpenGLVersions(final GLCapabilities capabilities) {
        final Class<? extends GLCapabilities> cls = capabilities.getClass();
        for (final Field declaredField : cls.getDeclaredFields()) {
            String name = declaredField.getName();
            if (name.startsWith("OpenGL")) {
                name = name.substring("OpenGL".length());
                final char[] chars = name.toCharArray();
                final int length = chars.length;
                final int major = (length >= 1) ? this.getInteger(chars[0]) : 0;
                final int minor = (length >= 2) ? this.getInteger(chars[1]) : 0;
                final int patch = (length >= 3) ? this.getInteger(chars[2]) : 0;
                this.addVersion(new GFXVersion(new SemanticVersion(major, minor, patch), this.readField(declaredField, () -> capabilities)));
            }
        }
    }
    
    @Override
    protected GFXCapabilityEntry<Integer> getInt(final String key, final int pname) {
        return new GFXCapabilityEntry<Integer>(key, 0, true);
    }
    
    @Override
    protected String onDumpOpenGlStates() {
        final PrettyStringBuilder builder = new PrettyStringBuilder();
        if (PlatformEnvironment.isNoShader()) {
            builder.append("Matrices:").newLine();
            this.appendKeyIntValue(builder, "ModelViewStack-Depth", 2979);
            this.appendKeyIntValue(builder, "ProjectionStack-Depth", 2980);
            this.appendKeyIntValue(builder, "TextureStack-Depth", 2981);
        }
        return builder.toString();
    }
    
    protected void appendKeyIntValue(final PrettyStringBuilder builder, final String key, final int pname) {
        builder.appendKeyValue(key, GL11.glGetInteger(pname), 1);
    }
}
