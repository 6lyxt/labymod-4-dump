// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.render.vertex;

import org.lwjgl.opengl.GL11;

public enum VertexFormatElementUsage
{
    POSITION("Position", (count, glType, vertexSize, offset, index, elementPosition) -> {
        enableClientState(32884);
        GL11.glVertexPointer(count, glType, vertexSize, offset);
        return;
    }, (index, elementPosition) -> disableClientState(32884)), 
    NORMAL("Normal", (count, glType, vertexSize, offset, index, elementPosition) -> {
        enableClientState(32885);
        GL11.glNormalPointer(glType, vertexSize, offset);
        return;
    }, (index, elementPosition) -> disableClientState(32885)), 
    COLOR("Color", (count, glType, vertexSize, offset, index, elementPosition) -> {
        enableClientState(32886);
        GL11.glColorPointer(count, glType, vertexSize, offset);
        return;
    }, (index, elementPosition) -> {
        disableClientState(32886);
        bfl.G();
        return;
    }), 
    UV("UV", (count, glType, vertexSize, offset, index, elementPosition) -> {
        bqs.l(bqs.q + index);
        enableClientState(32888);
        GL11.glTexCoordPointer(count, glType, vertexSize, offset);
        bqs.l(bqs.q);
        return;
    }, (index, elementPosition) -> {
        bqs.l(bqs.q + index);
        disableClientState(32888);
        bqs.l(bqs.q);
        return;
    }), 
    PADDING("Padding", (count, glType, vertexSize, offset, index, elementPosition) -> {}, (index, elementPosition) -> {});
    
    private final String name;
    private final ApplyState applyState;
    private final ClearState clearState;
    
    private VertexFormatElementUsage(final String name, final ApplyState applyState, final ClearState clearState) {
        this.name = name;
        this.applyState = applyState;
        this.clearState = clearState;
    }
    
    public void apply(final int count, final int glType, final int vertexSize, final long offset, final int index, final int elementPosition) {
        this.applyState.apply(count, glType, vertexSize, offset, index, elementPosition);
    }
    
    public void clear(final int index, final int elementPosition) {
        this.clearState.clear(index, elementPosition);
    }
    
    private static void enableClientState(final int cap) {
        GL11.glEnableClientState(cap);
    }
    
    private static void disableClientState(final int cap) {
        GL11.glDisableClientState(cap);
    }
    
    @FunctionalInterface
    interface ApplyState
    {
        void apply(final int p0, final int p1, final int p2, final long p3, final int p4, final int p5);
    }
    
    @FunctionalInterface
    interface ClearState
    {
        void clear(final int p0, final int p1);
    }
}
