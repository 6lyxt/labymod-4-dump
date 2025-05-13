// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.vertex;

import net.labymod.core.util.camera.CinematicCamera;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.shader.UniformContext;
import net.labymod.core.client.render.schematic.lightning.LightSourceRegistry;
import net.labymod.core.client.render.schematic.ShaderCamera;
import net.labymod.api.client.gfx.shader.uniform.UniformSampler;
import net.labymod.api.client.gfx.shader.uniform.Uniform1I;
import net.labymod.api.client.gfx.shader.uniform.Uniform1FArray;
import net.labymod.api.client.gfx.shader.uniform.UniformFloatVector3Array;
import net.labymod.api.client.gfx.shader.uniform.UniformMatrix4;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Locale;
import net.labymod.api.client.gfx.shader.Shader;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.shader.ShaderConstants;
import java.util.function.Function;
import net.labymod.api.util.function.FunctionMemoizeStorage;

public final class SchematicShaderPrograms
{
    private static final String SHADER_PATH = "shaders/vertex/schematic/%s.%s";
    private static final FunctionMemoizeStorage MEMOIZE_STORAGE;
    public static boolean LIGHTNING;
    private static final Function<ShaderConstants.Builder, ShaderProgram> DEFAULT_SCHEMATIC_PROGRAM;
    
    public static ShaderProgram applyDefaultSchematicProgram(final ShaderConstants.Builder builder) {
        return SchematicShaderPrograms.DEFAULT_SCHEMATIC_PROGRAM.apply(builder);
    }
    
    private static void addShader(final ShaderProgram shaderProgram, final String name, final Shader.Type type, final ShaderConstants constants) {
        shaderProgram.addShader(ResourceLocation.create("labymod", String.format(Locale.ROOT, "shaders/vertex/schematic/%s.%s", name, type.toString())), type, constants);
    }
    
    static {
        MEMOIZE_STORAGE = Laby.references().functionMemoizeStorage();
        SchematicShaderPrograms.LIGHTNING = true;
        DEFAULT_SCHEMATIC_PROGRAM = SchematicShaderPrograms.MEMOIZE_STORAGE.memoize(builder -> {
            final ShaderProgram.Factory programFactory = Laby.references().shaderProgramFactory();
            final ShaderProgram schematicShaderProgram = programFactory.create(context -> {
                final UniformMatrix4 projectionMatrix = context.addUniform(new UniformMatrix4("ProjectionMatrix"));
                final UniformMatrix4 previousProjectionMatrix = context.addUniform(new UniformMatrix4("PreviousProjectionMatrix"));
                final UniformMatrix4 viewMatrix = context.addUniform(new UniformMatrix4("ViewMatrix"));
                final UniformMatrix4 previousViewMatrix = context.addUniform(new UniformMatrix4("PreviousViewMatrix"));
                final UniformMatrix4 modelMatrix = context.addUniform(new UniformMatrix4("ModelMatrix"));
                final UniformFloatVector3Array lightSourcePosition = context.addUniform(new UniformFloatVector3Array("LightSourcePosition", 48));
                final UniformFloatVector3Array lightSourceColor = context.addUniform(new UniformFloatVector3Array("LightSourceColor", 48));
                final Uniform1FArray lightSourceConstant = context.addUniform(new Uniform1FArray("LightSourceConstant", 48));
                final Uniform1FArray lightSourceLinear = context.addUniform(new Uniform1FArray("LightSourceLinear", 48));
                final Uniform1FArray lightSourceQuadratic = context.addUniform(new Uniform1FArray("LightSourceQuadratic", 48));
                final Uniform1I lightningEnabledConstant = context.addUniform(new Uniform1I("LightningEnabled"));
                context.addUniform(new UniformSampler("DiffuseSampler", 0));
                ShaderCamera.setShaderHook(camera -> {
                    projectionMatrix.set(camera.projectionMatrix());
                    previousProjectionMatrix.set(camera.previousProjectionMatrix());
                    viewMatrix.set(camera.viewMatrix());
                    previousViewMatrix.set(camera.previousViewMatrix());
                    return;
                });
                context.setUniformApplier(pipeline -> {
                    LightSourceRegistry.getInstance().uploadPointLights(lightSourcePosition, lightSourceColor, lightSourceConstant, lightSourceLinear, lightSourceQuadratic);
                    modelMatrix.set(pipeline.getModelViewMatrix());
                    lightningEnabledConstant.set(Laby.labyAPI().config().appearance().dynamicBackground().shader().get() && SchematicShaderPrograms.LIGHTNING);
                });
                return;
            });
            final ShaderConstants constants = builder.addConstant((CharSequence)"MAX_LIGHTS", () -> "48").build();
            addShader(schematicShaderProgram, "default", Shader.Type.VERTEX, constants);
            addShader(schematicShaderProgram, "default", Shader.Type.FRAGMENT, constants);
            schematicShaderProgram.setDebugName("Schematic Default");
            return schematicShaderProgram;
        });
    }
}
