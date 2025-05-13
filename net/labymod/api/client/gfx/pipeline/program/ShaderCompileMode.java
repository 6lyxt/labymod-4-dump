// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program;

public enum ShaderCompileMode
{
    DEFAULT, 
    ROUND_RECTANGLE, 
    ROUND_RECTANGLE_POST_PROCESSING(true);
    
    private static final ShaderCompileMode[] VALUES;
    private final boolean postProcessing;
    
    private ShaderCompileMode() {
        this(false);
    }
    
    private ShaderCompileMode(final boolean postProcessing) {
        this.postProcessing = postProcessing;
    }
    
    public boolean isPostProcessing() {
        return this.postProcessing;
    }
    
    public static ShaderCompileMode[] getValues() {
        return ShaderCompileMode.VALUES;
    }
    
    public static ShaderCompileMode get(final String name) {
        for (final ShaderCompileMode value : ShaderCompileMode.VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No enum constant " + ShaderCompileMode.class.getCanonicalName() + "." + name);
    }
    
    public static ShaderCompileMode getOrDefault(final String name, final ShaderCompileMode defaultValue) {
        for (final ShaderCompileMode value : ShaderCompileMode.VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return defaultValue;
    }
    
    static {
        VALUES = values();
    }
}
