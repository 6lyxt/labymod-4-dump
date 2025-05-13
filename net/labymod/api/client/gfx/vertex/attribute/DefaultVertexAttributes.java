// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.vertex.attribute;

import net.labymod.api.client.gfx.opengl.NamedOpenGLVersion;
import java.util.function.Supplier;

public final class DefaultVertexAttributes
{
    public static final VertexAttribute POSITION;
    public static final VertexAttribute UV0;
    public static final VertexAttribute UV1;
    public static final VertexAttribute UV2;
    public static final VertexAttribute COLOR;
    public static final VertexAttribute NORMAL;
    public static final VertexAttribute PADDING1;
    public static final VertexAttribute PADDING2;
    public static final VertexAttribute PADDING3;
    public static final VertexAttribute PADDING4;
    public static final VertexAttribute PADDING;
    
    public static <T extends VertexAttribute> T make(final Supplier<T> factory) {
        return factory.get();
    }
    
    public static <T extends VertexAttribute> T makeGL30(final T trueAttribute, final T falseAttribute) {
        return make(NamedOpenGLVersion.GL30.isSupported(), trueAttribute, falseAttribute);
    }
    
    public static boolean isPadding(final VertexAttribute attribute) {
        return attribute == DefaultVertexAttributes.PADDING1 || attribute == DefaultVertexAttributes.PADDING2 || attribute == DefaultVertexAttributes.PADDING3 || attribute == DefaultVertexAttributes.PADDING4;
    }
    
    private static <T extends VertexAttribute> T make(final boolean condition, final T trueAttribute, final T falseAttribute) {
        return make(() -> condition ? trueAttribute : falseAttribute);
    }
    
    static {
        POSITION = new VertexAttribute3F(false);
        UV0 = new VertexAttribute2F(false);
        UV1 = makeGL30(new VertexAttribute2S(false, false).useIntAttributePointer(), new VertexAttribute4B(true, true));
        UV2 = makeGL30(new VertexAttribute2S(false, false).useIntAttributePointer(), new VertexAttribute4B(true, true));
        COLOR = new VertexAttribute4B(true, true);
        NORMAL = new VertexAttribute3B(false, true);
        PADDING1 = new VertexAttribute1B(true, true);
        PADDING2 = new VertexAttribute2B(true, true);
        PADDING3 = new VertexAttribute3B(true, true);
        PADDING4 = new VertexAttribute4B(true, true);
        PADDING = DefaultVertexAttributes.PADDING1;
    }
}
