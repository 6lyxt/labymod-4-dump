// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui;

import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewport;
import imgui.ImFontAtlas;
import imgui.type.ImInt;
import imgui.ImVec2;
import org.lwjgl.system.MemoryUtil;
import imgui.ImVec4;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11;
import imgui.ImDrawData;
import imgui.ImGuiIO;
import imgui.ImGui;

public class LabyImGuiImplGl2
{
    private static final int POSITION_OFFSET = 0;
    private static final int TEXTURE_OFFSET = 8;
    private static final int COLOR_OFFSET = 16;
    private final int[] lastTexture;
    private final int[] lastPolygonMode;
    private final int[] lastViewport;
    private final int[] lastScissorBox;
    private final int[] lastShadeModel;
    private final int[] lastTexEnvMode;
    private int gFontTexture;
    
    public LabyImGuiImplGl2() {
        this.lastTexture = new int[1];
        this.lastPolygonMode = new int[2];
        this.lastViewport = new int[4];
        this.lastScissorBox = new int[4];
        this.lastShadeModel = new int[1];
        this.lastTexEnvMode = new int[1];
    }
    
    public void init() {
        final ImGuiIO io = ImGui.getIO();
        io.setBackendRendererName("imgui_java_impl_opengl2");
        io.addBackendFlags(4096);
        this.updateFontsTexture();
        if (io.hasConfigFlags(1024)) {
            this.initPlatformInterface();
        }
    }
    
    public void renderDrawData(final ImDrawData drawData) {
        final int commandListsCount = drawData.getCmdListsCount();
        if (commandListsCount <= 0) {
            return;
        }
        final int framebufferWidth = (int)(drawData.getDisplaySize().x * drawData.getFramebufferScale().x);
        final int framebufferHeight = (int)(drawData.getDisplaySize().y * drawData.getFramebufferScale().y);
        if (framebufferWidth == 0 || framebufferHeight == 0) {
            return;
        }
        GL11.glGetIntegerv(32873, this.lastTexture);
        GL11.glGetIntegerv(2880, this.lastPolygonMode);
        GL11.glGetIntegerv(2978, this.lastViewport);
        GL11.glGetIntegerv(3088, this.lastScissorBox);
        GL11.glGetIntegerv(2900, this.lastShadeModel);
        GL11.glGetTexEnviv(8960, 8704, this.lastTexEnvMode);
        GL11.glPushAttrib(28672);
        this.setupRenderState(drawData, framebufferWidth, framebufferHeight);
        final ImVec2 clipOff = drawData.getDisplayPos();
        final ImVec2 clipScale = drawData.getFramebufferScale();
        final float clipOffX = clipOff.x;
        final float clipOffY = clipOff.y;
        final float clipScaleX = clipScale.x;
        final float clipScaleY = clipScale.y;
        for (int commandListIndex = 0; commandListIndex < commandListsCount; ++commandListIndex) {
            ByteBuffer indexBuffer = drawData.getCmdListIdxBufferData(commandListIndex);
            indexBuffer = ByteBuffer.allocateDirect(indexBuffer.remaining()).put(indexBuffer);
            final ByteBuffer vertexBuffer = drawData.getCmdListVtxBufferData(commandListIndex);
            final int indexSize = ImDrawData.sizeOfImDrawIdx();
            final int stride = ImDrawData.sizeOfImDrawVert();
            vertexBuffer.position(0);
            GL11.glVertexPointer(2, 5126, stride, vertexBuffer);
            vertexBuffer.position(8);
            GL11.glTexCoordPointer(2, 5126, stride, vertexBuffer);
            vertexBuffer.position(16);
            GL11.glColorPointer(4, 5121, stride, vertexBuffer);
            for (int commandBufferSize = drawData.getCmdListCmdBufferSize(commandListIndex), commandBufferIndex = 0; commandBufferIndex < commandBufferSize; ++commandBufferIndex) {
                final ImVec4 clipRect = new ImVec4();
                drawData.getCmdListCmdBufferClipRect(commandListIndex, commandBufferIndex, clipRect);
                final float clipMinX = (clipRect.x - clipOffX) * clipScaleX;
                final float clipMinY = (clipRect.y - clipOffY) * clipScaleY;
                final float clipMaxX = (clipRect.z - clipOffX) * clipScaleX;
                final float clipMaxY = (clipRect.w - clipOffY) * clipScaleY;
                if (clipMaxX > clipMinX) {
                    if (clipMaxY > clipMinY) {
                        GL11.glScissor((int)clipMinX, (int)(framebufferHeight - clipMaxY), (int)(clipMaxX - clipMinX), (int)(clipMaxY - clipMinY));
                        final int textureId = drawData.getCmdListCmdBufferTextureId(commandListIndex, commandBufferIndex);
                        final int elemCount = drawData.getCmdListCmdBufferElemCount(commandListIndex, commandBufferIndex);
                        final int indexOffset = drawData.getCmdListCmdBufferIdxOffset(commandListIndex, commandBufferIndex);
                        final int type = (indexSize == 2) ? 5123 : 5125;
                        GL11.glBindTexture(3553, textureId);
                        indexBuffer.position(indexOffset * indexSize);
                        GL11.glDrawElements(4, elemCount, type, MemoryUtil.memAddress(indexBuffer));
                    }
                }
            }
        }
        GL11.glDisableClientState(32886);
        GL11.glDisableClientState(32888);
        GL11.glDisableClientState(32884);
        GL11.glBindTexture(3553, this.lastTexture[0]);
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
        GL11.glPolygonMode(1028, this.lastPolygonMode[0]);
        GL11.glPolygonMode(1029, this.lastPolygonMode[1]);
        GL11.glViewport(this.lastViewport[0], this.lastViewport[1], this.lastViewport[2], this.lastViewport[3]);
        GL11.glScissor(this.lastScissorBox[0], this.lastScissorBox[1], this.lastScissorBox[2], this.lastScissorBox[3]);
        GL11.glShadeModel(this.lastShadeModel[0]);
        GL11.glTexEnvi(8960, 8704, this.lastTexEnvMode[0]);
    }
    
    private void setupRenderState(final ImDrawData drawData, final int framebufferWidth, final int framebufferHeight) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2884);
        GL11.glDisable(2929);
        GL11.glDisable(2960);
        GL11.glDisable(2896);
        GL11.glDisable(2903);
        GL11.glEnable(3089);
        GL11.glEnableClientState(32884);
        GL11.glEnableClientState(32888);
        GL11.glEnableClientState(32886);
        GL11.glDisableClientState(32885);
        GL11.glEnable(3553);
        GL11.glPolygonMode(1032, 6914);
        GL11.glShadeModel(7425);
        GL11.glTexEnvi(8960, 8704, 8448);
        GL11.glViewport(0, 0, framebufferWidth, framebufferHeight);
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        final float left = drawData.getDisplayPosX();
        final float right = left + drawData.getDisplaySizeX();
        final float top = drawData.getDisplayPosY();
        final float bottom = top + drawData.getDisplaySizeY();
        GL11.glOrtho((double)left, (double)right, (double)bottom, (double)top, -1.0, 1.0);
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
    }
    
    private void updateFontsTexture() {
        GL11.glDeleteTextures(this.gFontTexture);
        final ImFontAtlas fontAtlas = ImGui.getIO().getFonts();
        final ImInt width = new ImInt();
        final ImInt height = new ImInt();
        final ByteBuffer buffer = fontAtlas.getTexDataAsRGBA32(width, height);
        GL11.glBindTexture(3553, this.gFontTexture = GL11.glGenTextures());
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glPixelStorei(3314, 0);
        GL11.glTexImage2D(3553, 0, 6408, width.get(), height.get(), 0, 6408, 5121, buffer);
        fontAtlas.setTexID(this.gFontTexture);
    }
    
    private void initPlatformInterface() {
        ImGui.getPlatformIO().setRendererRenderWindow((ImPlatformFuncViewport)new ImPlatformFuncViewport() {
            public void accept(final ImGuiViewport vp) {
                if (!vp.hasFlags(256)) {
                    GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                    GL11.glClear(16384);
                }
                LabyImGuiImplGl2.this.renderDrawData(vp.getDrawData());
            }
        });
    }
}
