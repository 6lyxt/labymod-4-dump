// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader;

import java.io.InputStream;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.util.io.IOUtil;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import net.labymod.api.util.function.ThrowableBiFunction;
import net.labymod.api.client.gfx.shader.exception.ShaderException;
import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;

public class Shader
{
    private final ResourceLocation location;
    private final Type type;
    private final ShaderConstants shaderConstants;
    private int shaderId;
    
    public Shader(final ResourceLocation location, final Type type, final ShaderConstants shaderConstants) {
        this.location = location;
        this.type = type;
        this.shaderConstants = shaderConstants;
    }
    
    public ResourceLocation location() {
        return this.location;
    }
    
    public Type type() {
        return this.type;
    }
    
    public ShaderConstants shaderConstants() {
        return this.shaderConstants;
    }
    
    public void attach(final int programId) {
        Laby.gfx().attachShader(programId, this.shaderId);
    }
    
    public void delete() {
        Laby.gfx().deleteShader(this.shaderId);
    }
    
    public void checkAlreadyAdded() {
        if (this.shaderId > 0) {
            throw new ShaderException(String.format(Locale.ROOT, "A shader of type \"%s\" has already been added to the program", this.type));
        }
    }
    
    public void load(final ThrowableBiFunction<String, Shader, String, IOException> processorFunction) {
        final GFXBridge gfx = Laby.gfx();
        this.shaderId = gfx.createShader(this.type);
        try (final InputStream is = this.location.openStream()) {
            String source = IOUtil.toString(is, StandardCharsets.UTF_8);
            source = processorFunction.apply(source, this);
            gfx.shaderSource(this.shaderId, source);
            gfx.compileShader(this.shaderId);
            gfx.onShaderCompileError(this.shaderId, message -> {
                new ShaderException(String.format(Locale.ROOT, "The %s shader could not be compiled. [%s]%n%s", this.type, this, message));
                return;
            });
        }
        catch (final IOException exception) {
            throw new ShaderException("Could not read shader source (" + String.valueOf(this.location), (Throwable)exception);
        }
    }
    
    @Override
    public String toString() {
        return "Shader[" + ((this.location == null) ? "Not set" : this.location.toString());
    }
    
    public enum Type
    {
        VERTEX(35633, "vsh"), 
        FRAGMENT(35632, "fsh"), 
        GEOMETRY(36313, "geo"), 
        TESS_CONTROL(36488, "tessc"), 
        TESS_EVALUATION(36487, "tesse"), 
        COMPUTE(37305, "compute");
        
        private final int id;
        private final String fileExtension;
        
        private Type(final int id, final String fileExtension) {
            this.id = id;
            this.fileExtension = fileExtension;
        }
        
        public int getId() {
            return this.id;
        }
        
        public String getFileExtension() {
            return this.fileExtension;
        }
        
        @Override
        public String toString() {
            return this.getFileExtension();
        }
    }
}
