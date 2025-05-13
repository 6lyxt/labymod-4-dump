// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.render.vertex.buffer;

import org.lwjgl.opengl.GL20;
import net.labymod.v1_8_9.client.render.vertex.EnumUsageCustom;
import java.util.List;
import java.nio.ByteBuffer;
import net.labymod.core.main.profiler.RenderProfiler;
import org.lwjgl.opengl.GL11;

public class CustomRenderer
{
    public static void draw(final bfd renderer) {
        end(renderer);
        renderer.b();
    }
    
    private static void end(final bfd renderer) {
        final int vertices = renderer.h();
        if (vertices <= 0) {
            return;
        }
        final bmu format = renderer.g();
        final int stride = format.g();
        final ByteBuffer buffer = renderer.f();
        final List<bmv> elements = format.h();
        for (int index = 0; index < elements.size(); ++index) {
            final bmv element = elements.get(index);
            final int offset = format.d(index);
            buffer.position(offset);
            applyVertexAttribute(element, stride, buffer, index);
        }
        GL11.glDrawArrays(renderer.i(), 0, vertices);
        RenderProfiler.increaseRenderCall();
        for (int index = 0; index < elements.size(); ++index) {
            clearVertexAttribute(index, elements.get(index));
        }
    }
    
    private static void applyVertexAttribute(final bmv element, final int stride, final ByteBuffer buffer, final int index) {
        final bmv.b usage = element.b();
        final int glType = element.a().c();
        final int elementIndex = element.d();
        final int elementCount = element.c();
        switch (usage) {
            case a: {
                GL11.glEnableClientState(32884);
                GL11.glVertexPointer(elementCount, glType, stride, buffer);
                break;
            }
            case b: {
                GL11.glEnableClientState(32885);
                GL11.glNormalPointer(glType, stride, buffer);
                break;
            }
            case c: {
                GL11.glEnableClientState(32886);
                GL11.glColorPointer(elementCount, glType, stride, buffer);
                break;
            }
            case d: {
                bqs.l(bqs.q + elementIndex);
                GL11.glEnableClientState(32888);
                GL11.glTexCoordPointer(elementCount, glType, stride, buffer);
                bqs.l(bqs.q);
                break;
            }
            default: {
                if (usage == EnumUsageCustom.GENERIC) {
                    GL20.glEnableVertexAttribArray(index);
                    GL20.glVertexAttribPointer(index, elementCount, glType, false, stride, buffer);
                    break;
                }
                break;
            }
        }
    }
    
    private static void clearVertexAttribute(final int index, final bmv element) {
        final bmv.b usage = element.b();
        final int elementIndex = element.d();
        switch (usage) {
            case a: {
                GL11.glDisableClientState(32884);
                break;
            }
            case b: {
                GL11.glDisableClientState(32885);
                break;
            }
            case c: {
                bfl.G();
                GL11.glDisableClientState(32886);
                break;
            }
            case d: {
                bqs.l(bqs.q + elementIndex);
                GL11.glDisableClientState(32888);
                bqs.l(bqs.q);
                break;
            }
            default: {
                if (usage == EnumUsageCustom.GENERIC) {
                    GL20.glDisableVertexAttribArray(index);
                    break;
                }
                break;
            }
        }
    }
}
