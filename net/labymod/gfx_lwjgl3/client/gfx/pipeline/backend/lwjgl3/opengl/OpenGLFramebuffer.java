// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.target.RenderTargetMode;
import net.labymod.api.util.function.IntIntFunction;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.gfx.target.FramebufferTarget;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.GL30;
import org.jetbrains.annotations.Nullable;

public final class OpenGLFramebuffer
{
    public static int generate(@Nullable final OpenGLFramebufferGenerate otherMethod) {
        if (otherMethod != null) {
            return otherMethod.generate();
        }
        return switch (getRenderTargetMode()) {
            default -> throw new MatchException(null, null);
            case BASE -> GL30.glGenFramebuffers();
            case ARB -> ARBFramebufferObject.glGenFramebuffers();
            case EXT -> EXTFramebufferObject.glGenFramebuffersEXT();
        };
    }
    
    public static void bind(final FramebufferTarget target, final int id) {
        bind(target, id, null);
    }
    
    public static void bind(final FramebufferTarget target, final int id, @Nullable final OpenGLFramebufferBindMethod otherMethod) {
        if (otherMethod != null) {
            otherMethod.bind(target.getId(), id);
            return;
        }
        switch (getRenderTargetMode()) {
            case BASE: {
                GL30.glBindFramebuffer(target.getId(), id);
                break;
            }
            case ARB: {
                ARBFramebufferObject.glBindFramebuffer(target.getId(), id);
                break;
            }
            case EXT: {
                EXTFramebufferObject.glBindFramebufferEXT(target.getId(), id);
                break;
            }
        }
    }
    
    public static void delete(final int id, @Nullable final OpenGLFramebufferDeleteMethod otherMethod) {
        if (otherMethod != null) {
            otherMethod.delete(id);
            return;
        }
        switch (getRenderTargetMode()) {
            case BASE: {
                GL30.glDeleteFramebuffers(id);
                break;
            }
            case ARB: {
                ARBFramebufferObject.glDeleteFramebuffers(id);
                break;
            }
            case EXT: {
                EXTFramebufferObject.glDeleteFramebuffersEXT(id);
                break;
            }
        }
    }
    
    public static void texture2D(final FramebufferTarget target, final int attachment, final TextureTarget textureTarget, final int texture, final int level, @Nullable final OpenGLFramebufferTexture2DMethod otherMethod) {
        if (otherMethod != null) {
            otherMethod.texture2D(target.getId(), attachment, textureTarget.getId(), texture, level);
            return;
        }
        switch (getRenderTargetMode()) {
            case BASE: {
                GL30.glFramebufferTexture2D(target.getId(), attachment, textureTarget.getId(), texture, level);
                break;
            }
            case ARB: {
                ARBFramebufferObject.glFramebufferTexture2D(target.getId(), attachment, textureTarget.getId(), texture, level);
                break;
            }
            case EXT: {
                EXTFramebufferObject.glFramebufferTexture2DEXT(target.getId(), attachment, textureTarget.getId(), texture, level);
                break;
            }
        }
    }
    
    public static void unbind(final FramebufferTarget target) {
        unbind(target, null);
    }
    
    public static void unbind(final FramebufferTarget target, @Nullable final OpenGLFramebufferBindMethod otherMethod) {
        bind(target, 0, otherMethod);
    }
    
    @Nullable
    public static String checkFramebufferComplete(final FramebufferTarget target, @Nullable final IntIntFunction otherMethod) {
        final int status = glCheckFramebufferStatus(target, otherMethod);
        if (status == 36053) {
            return null;
        }
        return switch (status) {
            case 36054 -> "A framebuffer attachment is incomplete";
            case 36055 -> "Framebuffer has no attachments";
            case 36059 -> "Draw framebuffer is incomplete";
            case 36060 -> "Read framebuffer is incomplete";
            case 36061 -> "Framebuffer unsupported";
            default -> "Unknown status: 0x" + Integer.toHexString(status);
        };
    }
    
    public static int glCheckFramebufferStatus(final FramebufferTarget target, @Nullable final IntIntFunction otherMethod) {
        if (otherMethod != null) {
            return otherMethod.apply(target.getId());
        }
        return switch (getRenderTargetMode()) {
            default -> throw new MatchException(null, null);
            case BASE -> GL30.glCheckFramebufferStatus(target.getId());
            case ARB -> ARBFramebufferObject.glCheckFramebufferStatus(target.getId());
            case EXT -> EXTFramebufferObject.glCheckFramebufferStatusEXT(target.getId());
        };
    }
    
    private static RenderTargetMode getRenderTargetMode() {
        return Laby.gfx().capabilities().getRenderTargetMode();
    }
    
    @FunctionalInterface
    public interface OpenGLFramebufferGenerate
    {
        int generate();
    }
    
    @FunctionalInterface
    public interface OpenGLFramebufferBindMethod
    {
        void bind(final int p0, final int p1);
    }
    
    @FunctionalInterface
    public interface OpenGLFramebufferDeleteMethod
    {
        void delete(final int p0);
    }
    
    @FunctionalInterface
    public interface OpenGLFramebufferTexture2DMethod
    {
        void texture2D(final int p0, final int p1, final int p2, final int p3, final int p4);
    }
}
