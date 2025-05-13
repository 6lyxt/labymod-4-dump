// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl.debug;

import net.labymod.api.util.function.Functional;
import java.util.stream.Stream;
import java.util.Locale;
import org.lwjgl.opengl.ARBMultitexture;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.DoubleBuffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import net.labymod.api.util.logging.Logging;

public final class TextureDebugger
{
    private static final Logging LOGGER;
    private static int activeTexture;
    private static final TextureState[] TEXTURE_STATES;
    
    private static void setActiveTexture(final int texture) {
        TextureDebugger.activeTexture = texture - 33984;
    }
    
    public static void glActiveTexture(final int texture) {
        GL13.glActiveTexture(texture);
        setActiveTexture(texture);
        onError("texture", toHexString(texture));
    }
    
    public static void glEnable(final int target) {
        GL11.glEnable(target);
        if (target == 3553) {
            TextureDebugger.TEXTURE_STATES[TextureDebugger.activeTexture].setEnabled(true);
        }
        onError("target", target);
    }
    
    public static void glDisable(final int target) {
        GL11.glDisable(target);
        if (target == 3553) {
            TextureDebugger.TEXTURE_STATES[TextureDebugger.activeTexture].setEnabled(false);
        }
        onError("target", target);
    }
    
    public static int glGenTextures() {
        final int id = GL11.glGenTextures();
        onError(new Object[0]);
        return id;
    }
    
    public static void glBindTexture(final int target, final int texture) {
        GL11.glBindTexture(target, texture);
        TextureDebugger.TEXTURE_STATES[TextureDebugger.activeTexture].setTextureId(texture);
        onError("target", toHexString(target), "texture", texture);
    }
    
    public static void glGenTextures(final int[] textures) {
        GL11.glGenTextures(textures);
        onError("textures", Arrays.toString(textures));
    }
    
    public static void glGenTextures(final IntBuffer textures) {
        GL11.glGenTextures(textures);
        onError("textures", textures);
    }
    
    public static void glDeleteTextures(final int texture) {
        GL11.glDeleteTextures(texture);
        onError("texture", texture);
    }
    
    public static void glDeleteTextures(final int[] textures) {
        GL11.glDeleteTextures(textures);
        onError("textures", Arrays.toString(textures));
    }
    
    public static void glDeleteTextures(final IntBuffer textures) {
        GL11.glDeleteTextures(textures);
        onError("textures", textures);
    }
    
    public static void glTexImage2D(final int target, final int level, final int internalformat, final int width, final int height, final int border, final int format, final int type, final ByteBuffer pixels) {
        GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        onError("target", toHexString(target), "level", level, "internalformat", toHexString(internalformat), "width", width, "height", height, "border", border, "format", toHexString(format), "type", toHexString(target), "pixels", pixels);
    }
    
    public static void glTexImage2D(final int target, final int level, final int internalformat, final int width, final int height, final int border, final int format, final int type, final DoubleBuffer pixels) {
        GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        onError("target", toHexString(target), "level", level, "internalformat", toHexString(internalformat), "width", width, "height", height, "border", border, "format", toHexString(format), "type", toHexString(type), "pixels", pixels);
    }
    
    public static void glTexImage2D(final int target, final int level, final int internalformat, final int width, final int height, final int border, final int format, final int type, final ShortBuffer pixels) {
        GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        onError("target", toHexString(target), "level", level, "internalformat", toHexString(internalformat), "width", width, "height", height, "border", border, "format", toHexString(format), "type", toHexString(type), "pixels", pixels);
    }
    
    public static void glTexImage2D(final int target, final int level, final int internalformat, final int width, final int height, final int border, final int format, final int type, final IntBuffer pixels) {
        GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        onError("target", toHexString(target), "level", level, "internalformat", toHexString(internalformat), "width", width, "height", height, "border", border, "format", toHexString(format), "type", toHexString(type), "pixels", pixels);
    }
    
    public static void glTexImage2D(final int target, final int level, final int internalformat, final int width, final int height, final int border, final int format, final int type, final FloatBuffer pixels) {
        GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        onError("target", toHexString(target), "level", level, "internalformat", toHexString(internalformat), "width", width, "height", height, "border", border, "format", toHexString(format), "type", toHexString(type), "pixels", pixels);
    }
    
    public static void glTexImage2D(final int target, final int level, final int internalformat, final int width, final int height, final int border, final int format, final int type, final long pixels) {
        GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        onError("target", toHexString(target), "level", level, "internalformat", toHexString(internalformat), "width", width, "height", height, "border", border, "format", toHexString(format), "type", toHexString(type), "pixels", pixels);
    }
    
    public static void glTexImage2D(final int target, final int level, final int internalformat, final int width, final int height, final int border, final int format, final int type, final double[] pixels) {
        GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        onError("target", toHexString(target), "level", level, "internalformat", toHexString(internalformat), "width", width, "height", height, "border", border, "format", toHexString(format), "type", toHexString(type), "pixels", Arrays.toString(pixels));
    }
    
    public static void glTexImage2D(final int target, final int level, final int internalformat, final int width, final int height, final int border, final int format, final int type, final float[] pixels) {
        GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        onError("target", toHexString(target), "level", level, "internalformat", toHexString(internalformat), "width", width, "height", height, "border", border, "format", toHexString(format), "type", toHexString(type), "pixels", Arrays.toString(pixels));
    }
    
    public static void glTexImage2D(final int target, final int level, final int internalformat, final int width, final int height, final int border, final int format, final int type, final short[] pixels) {
        GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        onError("target", toHexString(target), "level", level, "internalformat", toHexString(internalformat), "width", width, "height", height, "border", border, "format", toHexString(format), "type", toHexString(type), "pixels", Arrays.toString(pixels));
    }
    
    public static void glTexImage2D(final int target, final int level, final int internalformat, final int width, final int height, final int border, final int format, final int type, final int[] pixels) {
        GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        onError("target", toHexString(target), "level", level, "internalformat", toHexString(internalformat), "width", width, "height", height, "border", border, "format", toHexString(format), "type", toHexString(type), "pixels", Arrays.toString(pixels));
    }
    
    public static void glTexSubImage2D(final int target, final int level, final int xOffset, final int yOffset, final int width, final int height, final int format, final int type, final ByteBuffer pixels) {
        GL11.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, pixels);
        onError("target", toHexString(target), "level", level, "xOffset", xOffset, "yOffset", yOffset, "width", width, "height", height, "format", toHexString(format), "type", toHexString(type), "pixels", pixels);
    }
    
    public static void glTexSubImage2D(final int target, final int level, final int xOffset, final int yOffset, final int width, final int height, final int format, final int type, final DoubleBuffer pixels) {
        GL11.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, pixels);
        onError("target", toHexString(target), "level", level, "xOffset", xOffset, "yOffset", yOffset, "width", width, "height", height, "format", toHexString(format), "type", toHexString(type), "pixels", pixels);
    }
    
    public static void glTexSubImage2D(final int target, final int level, final int xOffset, final int yOffset, final int width, final int height, final int format, final int type, final FloatBuffer pixels) {
        GL11.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, pixels);
        onError("target", toHexString(target), "level", level, "xOffset", xOffset, "yOffset", yOffset, "width", width, "height", height, "format", toHexString(format), "type", toHexString(type), "pixels", pixels);
    }
    
    public static void glTexSubImage2D(final int target, final int level, final int xOffset, final int yOffset, final int width, final int height, final int format, final int type, final ShortBuffer pixels) {
        GL11.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, pixels);
        onError("target", toHexString(target), "level", level, "xOffset", xOffset, "yOffset", yOffset, "width", width, "height", height, "format", toHexString(format), "type", toHexString(type), "pixels", pixels);
    }
    
    public static void glTexSubImage2D(final int target, final int level, final int xOffset, final int yOffset, final int width, final int height, final int format, final int type, final IntBuffer pixels) {
        GL11.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, pixels);
        onError("target", toHexString(target), "level", level, "xOffset", xOffset, "yOffset", yOffset, "width", width, "height", height, "format", toHexString(format), "type", toHexString(type), "pixels", pixels);
    }
    
    public static void glTexSubImage2D(final int target, final int level, final int xOffset, final int yOffset, final int width, final int height, final int format, final int type, final long pixels) {
        GL11.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, pixels);
        onError("target", toHexString(target), "level", level, "xOffset", xOffset, "yOffset", yOffset, "width", width, "height", height, "format", toHexString(format), "type", toHexString(type), "pixels", pixels);
    }
    
    public static void glTexSubImage2D(final int target, final int level, final int xOffset, final int yOffset, final int width, final int height, final int format, final int type, final short[] pixels) {
        GL11.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, pixels);
        onError("target", toHexString(target), "level", level, "xOffset", xOffset, "yOffset", yOffset, "width", width, "height", height, "format", toHexString(format), "type", toHexString(type), "pixels", Arrays.toString(pixels));
    }
    
    public static void glTexSubImage2D(final int target, final int level, final int xOffset, final int yOffset, final int width, final int height, final int format, final int type, final double[] pixels) {
        GL11.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, pixels);
        onError("target", toHexString(target), "level", level, "xOffset", xOffset, "yOffset", yOffset, "width", width, "height", height, "format", toHexString(format), "type", toHexString(type), "pixels", Arrays.toString(pixels));
    }
    
    public static void glTexSubImage2D(final int target, final int level, final int xOffset, final int yOffset, final int width, final int height, final int format, final int type, final float[] pixels) {
        GL11.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, pixels);
        onError("target", toHexString(target), "level", level, "xOffset", xOffset, "yOffset", yOffset, "width", width, "height", height, "format", toHexString(format), "type", toHexString(type), "pixels", Arrays.toString(pixels));
    }
    
    public static void glTexSubImage2D(final int target, final int level, final int xOffset, final int yOffset, final int width, final int height, final int format, final int type, final int[] pixels) {
        GL11.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, pixels);
        onError("target", toHexString(target), "level", level, "xOffset", xOffset, "yOffset", yOffset, "width", width, "height", height, "format", toHexString(format), "type", toHexString(type), "pixels", Arrays.toString(pixels));
    }
    
    public static void glTexParameteri(final int target, final int pname, final int param) {
        GL11.glTexParameteri(target, pname, param);
        onError("target", toHexString(target), "pname", toHexString(pname), "param", param);
    }
    
    public static void glTexParameteriv(final int target, final int pname, final int[] param) {
        GL11.glTexParameteriv(target, pname, param);
        onError("target", toHexString(target), "pname", toHexString(pname), "param", Arrays.toString(param));
    }
    
    public static void glTexParameteriv(final int target, final int pname, final IntBuffer param) {
        GL11.glTexParameteriv(target, pname, param);
        onError("target", toHexString(target), "pname", toHexString(pname), "param", param);
    }
    
    public static void glTexParameterf(final int target, final int pname, final float param) {
        GL11.glTexParameterf(target, pname, param);
        onError("target", toHexString(target), "pname", toHexString(pname), "param", param);
    }
    
    public static void glTexParameterfv(final int target, final int pname, final float[] param) {
        GL11.glTexParameterfv(target, pname, param);
        onError("target", toHexString(target), "pname", toHexString(pname), "param", Arrays.toString(param));
    }
    
    public static void glTexParameterfv(final int target, final int pname, final FloatBuffer param) {
        GL11.glTexParameterfv(target, pname, param);
        onError("target", toHexString(target), "pname", toHexString(pname), "param", param);
    }
    
    public static void glCopyTexSubImage2D(final int target, final int level, final int xOffset, final int yOffset, final int x, final int y, final int width, final int height) {
        GL11.glCopyTexSubImage2D(target, level, xOffset, yOffset, x, y, width, height);
        onError("target", toHexString(target), "level", level, "xOffset", xOffset, "yOffset", yOffset, "x", x, "y", y, "width", width, "height", height);
    }
    
    public static void glMultiTexCoord2f(final int texture, final float s, final float t) {
        GL13.glMultiTexCoord2f(texture, s, t);
        onError("texture", toHexString(texture), "s", s, "t", t);
    }
    
    public static void glClientActiveTexture(final int texture) {
        GL13.glClientActiveTexture(texture);
        onError("texture", toHexString(texture));
    }
    
    public static void glTexGeni(final int coord, final int pname, final int param) {
        GL11.glTexGeni(coord, pname, param);
        onError("coord", toHexString(coord), "pname", toHexString(pname), "param", param);
    }
    
    public static void glTexGeniv(final int coord, final int pname, final int[] param) {
        GL11.glTexGeniv(coord, pname, param);
        onError("coord", toHexString(coord), "pname", toHexString(pname), "param", Arrays.toString(param));
    }
    
    public static void glTexGeniv(final int coord, final int pname, final IntBuffer param) {
        GL11.glTexGeniv(coord, pname, param);
        onError("coord", toHexString(coord), "pname", toHexString(pname), "param", param);
    }
    
    public static void glTexGenf(final int coord, final int pname, final float param) {
        GL11.glTexGenf(coord, pname, param);
        onError("coord", toHexString(coord), "pname", toHexString(pname), "param", param);
    }
    
    public static void glTexGenfv(final int coord, final int pname, final float[] param) {
        GL11.glTexGenfv(coord, pname, param);
        onError("coord", toHexString(coord), "pname", toHexString(pname), "param", Arrays.toString(param));
    }
    
    public static void glTexGenfv(final int coord, final int pname, final FloatBuffer param) {
        GL11.glTexGenfv(coord, pname, param);
        onError("coord", toHexString(coord), "pname", toHexString(pname), "param", param);
    }
    
    public static void glActiveTextureARB(final int texture) {
        ARBMultitexture.glActiveTextureARB(texture);
        setActiveTexture(texture);
        onError("texture", toHexString(texture));
    }
    
    public static void glClientActiveTextureARB(final int texture) {
        ARBMultitexture.glClientActiveTextureARB(texture);
        onError("texture", toHexString(texture));
    }
    
    public static void glMultiTexCoord2fARB(final int texture, final float s, final float t) {
        ARBMultitexture.glMultiTexCoord2fARB(texture, s, t);
        onError("texture", toHexString(texture), "s", s, "t", t);
    }
    
    private static String toHexString(final int value) {
        return "0x" + Integer.toHexString(value).toUpperCase(Locale.ENGLISH);
    }
    
    private static void onError(final Object... args) {
        final int errorCode = GL11.glGetError();
        if (errorCode == 0) {
            printTextureInfo(false);
            return;
        }
        printError(errorCode, args);
    }
    
    private static void printError(final int errorCode, final Object... args) {
        final StringBuilder builder = new StringBuilder();
        for (int length = args.length, index = 0; index < length; index += 2) {
            builder.append("{}={}");
            if (index + 1 != length - 1) {
                builder.append(", ");
            }
        }
        final StackWalker.StackFrame frame = StackWalker.getInstance(StackWalker.Option.SHOW_REFLECT_FRAMES).walk(stream -> stream.limit(3L).toList().get(2));
        final String errorMessage = switch (errorCode) {
            case 1280 -> "Invalid Enum";
            case 1281 -> "Invalid Value";
            case 1282 -> "Invalid Operation";
            case 1283 -> "Out of Memory";
            default -> String.valueOf(errorCode);
        };
        TextureDebugger.LOGGER.error("!!!DEBUGGER [Error Code: %s] %s(%s)".formatted(errorMessage, frame.getMethodName(), builder), args);
        printTextureInfo(true);
    }
    
    private static void printTextureInfo(final boolean error) {
        log(error, "Current ActiveTexture: {}", TextureDebugger.activeTexture);
        log(error, "Textures:", new Object[0]);
        for (int index = 0; index < TextureDebugger.TEXTURE_STATES.length; ++index) {
            final TextureState state = TextureDebugger.TEXTURE_STATES[index];
            if (state.getTextureId() != 0 || state.isEnabled()) {
                log(error, "\t [{}] TextureID: {} | Enabled: {}", index, state.getTextureId(), state.isEnabled());
            }
        }
    }
    
    private static void log(final boolean error, final String message, final Object... args) {
        if (error) {
            TextureDebugger.LOGGER.error(message, args);
        }
        else {
            TextureDebugger.LOGGER.info(message, args);
        }
    }
    
    static {
        LOGGER = Logging.create(TextureDebugger.class);
        TextureDebugger.activeTexture = 0;
        TEXTURE_STATES = Functional.of(new TextureState[32], textureStates -> {
            for (int i = 0; i < textureStates.length; ++i) {
                textureStates[i] = new TextureState();
            }
        });
    }
    
    private static class TextureState
    {
        private boolean enabled;
        private int textureId;
        
        public boolean isEnabled() {
            return this.enabled;
        }
        
        public void setEnabled(final boolean enabled) {
            this.enabled = enabled;
        }
        
        public int getTextureId() {
            return this.textureId;
        }
        
        public void setTextureId(final int textureId) {
            this.textureId = textureId;
        }
    }
}
