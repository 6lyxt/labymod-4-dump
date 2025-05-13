// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.gfx.pipeline;

import net.labymod.api.Laby;
import net.labymod.api.client.render.shader.ShaderProgram;
import java.util.function.Function;

public class LegacyGlProgram extends fjh
{
    private static final Function<ShaderProgram, fjh> PROGRAMS;
    private final ShaderProgram program;
    
    public LegacyGlProgram(final ShaderProgram program) {
        super(program.getProgramID(), program.toString());
        this.program = program;
    }
    
    public static fjh fromShaderProgram(final ShaderProgram program) {
        return LegacyGlProgram.PROGRAMS.apply(program);
    }
    
    public int b() {
        this.program.bind();
        return super.b();
    }
    
    public void a() {
        super.a();
        this.program.unbind();
    }
    
    static {
        PROGRAMS = Laby.references().functionMemoizeStorage().memoize(LegacyGlProgram::new);
    }
}
