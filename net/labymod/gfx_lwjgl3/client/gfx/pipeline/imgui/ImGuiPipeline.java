// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui;

import imgui.ImFontAtlas;
import net.labymod.api.loader.platform.PlatformEnvironment;
import java.io.InputStream;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.OpenOption;
import net.labymod.api.util.io.IOUtil;
import net.labymod.core.util.classpath.ClasspathUtil;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import net.labymod.api.Constants;
import imgui.ImGuiStyle;
import imgui.ImGuiIO;
import imgui.assertion.ImAssertCallback;
import net.labymod.api.models.OperatingSystem;
import org.lwjgl.glfw.GLFW;
import net.labymod.api.client.gfx.GFXCapabilities;
import imgui.ImGui;
import org.lwjgl.opengl.GL11;
import net.labymod.core.client.gfx.imgui.window.ControlImGuiWindow;
import net.labymod.api.event.labymod.debug.ImGuiInitializeEvent;
import net.labymod.api.Laby;
import net.labymod.api.debug.DebugRegistry;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;
import imgui.glfw.ImGuiImplGlfw;
import net.labymod.api.util.logging.Logging;

public class ImGuiPipeline
{
    private static final String DEFAULT_IMGUI_INI = "assets/labymod/data/default_imgui.ini";
    private static final Logging LOGGER;
    private static ImGuiPipeline instance;
    private final ImGuiImplGlfw glfw;
    private final ImGuiGlPipeline glPipeline;
    private long windowPointer;
    private boolean initialized;
    private ImGuiWindow controlWindow;
    
    private ImGuiPipeline() {
        this.glfw = new ImGuiImplGlfw();
        this.glPipeline = new ImGuiGlPipeline();
        this.initialized = false;
        DebugRegistry.DEBUG_WINDOWS.addListener(value -> {
            if (!value) {
                this.render();
                this.renderEmptyFrame();
            }
        });
    }
    
    public static ImGuiPipeline getInstance() {
        if (ImGuiPipeline.instance == null) {
            ImGuiPipeline.instance = new ImGuiPipeline();
        }
        return ImGuiPipeline.instance;
    }
    
    public void initialize(final long windowPointer) {
        this.initializeImGui(windowPointer);
        this.windowPointer = windowPointer;
        if (!this.initialized) {
            return;
        }
        Laby.fireEvent(new ImGuiInitializeEvent(Laby.references().controlEntryRegistry()));
        this.controlWindow = new ControlImGuiWindow();
    }
    
    public void renderFrame() {
        if (!this.initialized) {
            return;
        }
        if (!this.isDebugWindowsEnabled()) {
            return;
        }
        this.render();
    }
    
    private void render() {
        if (!this.initialized) {
            return;
        }
        final GFXCapabilities capabilities = Laby.gfx().capabilities();
        if (capabilities.isGlPushMatrixAvailable()) {
            GL11.glPushMatrix();
        }
        this.glfw.newFrame();
        ImGui.newFrame();
        this.process();
        ImGui.render();
        this.endFrame();
        if (capabilities.isGlPopMatrixAvailable()) {
            GL11.glPopMatrix();
        }
    }
    
    private void process() {
        if (this.isDebugWindowsEnabled()) {
            this.controlWindow.render();
        }
    }
    
    @Deprecated(forRemoval = true, since = "4.2.34")
    private void renderEmptyFrame() {
        this.glfw.newFrame();
        ImGui.newFrame();
        ImGui.render();
        this.endFrame();
    }
    
    private boolean isDebugWindowsEnabled() {
        return DebugRegistry.DEBUG_WINDOWS.isEnabled();
    }
    
    private void endFrame() {
        this.glPipeline.renderData(ImGui.getDrawData());
        if (ImGui.getIO().hasConfigFlags(1024)) {
            final long currentContext = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(currentContext);
        }
    }
    
    private void initializeImGui(final long windowPointer) {
        try {
            ImGui.createContext();
            this.initialized = true;
        }
        catch (final UnsatisfiedLinkError error) {
            ImGuiPipeline.LOGGER.warn("Failed to initialize ImGui", error);
            return;
        }
        final ImGuiIO io = ImGui.getIO();
        io.addConfigFlags(17472);
        io.setConfigViewportsNoTaskBarIcon(true);
        io.setConfigViewportsNoAutoMerge(true);
        io.setConfigMacOSXBehaviors(OperatingSystem.isOSX());
        this.initializeIniFile(io);
        this.prepareFontAtlas(io);
        if (io.hasConfigFlags(1024)) {
            final ImGuiStyle style = ImGui.getStyle();
            style.setWindowRounding(0.0f);
            style.setColor(2, ImGui.getColorU32(2, 1.0f));
        }
        ImGui.setAssertCallback((ImAssertCallback)new LabyImAssertCallback());
        this.glfw.init(windowPointer, true);
        this.initGl();
    }
    
    private void initializeIniFile(final ImGuiIO io) {
        final Path userImGuiPath = Constants.Files.LABYMOD_DIRECTORY.resolve("imgui.ini");
        if (!Files.exists(userImGuiPath, new LinkOption[0])) {
            try (final InputStream stream = ClasspathUtil.getResourceAsInputStream("labymod", "assets/labymod/data/default_imgui.ini")) {
                final byte[] data = IOUtil.readBytes(stream);
                Files.write(userImGuiPath, data, new OpenOption[0]);
            }
            catch (final IOException exception) {
                ImGuiPipeline.LOGGER.warn("Could not load the default imgui config", exception);
            }
        }
        io.setIniFilename("labymod-neo/imgui.ini");
    }
    
    private void initGl() {
        String glslVersion = null;
        if (OperatingSystem.isOSX()) {
            if (PlatformEnvironment.isNoShader()) {
                glslVersion = "#version 110";
            }
            else {
                glslVersion = "#version 150";
            }
        }
        this.glPipeline.init(glslVersion);
    }
    
    private void prepareFontAtlas(final ImGuiIO io) {
        final ImFontAtlas fonts = io.getFonts();
        fonts.addFontDefault();
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
