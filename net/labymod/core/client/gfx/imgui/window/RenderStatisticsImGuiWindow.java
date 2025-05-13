// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.imgui.window;

import java.util.Collection;
import net.labymod.api.debug.DebugFeature;
import net.labymod.api.debug.DebugRegistry;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.Key;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.labymod.api.util.StringUtil;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.api.profiler.SampleLogger;
import java.util.Locale;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.labymod.api.client.gfx.buffer.BufferTarget;
import net.labymod.core.main.profiler.memory.BufferMemory;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.labymod.core.main.profiler.RenderProfiler;
import java.util.Iterator;
import java.util.UUID;
import java.util.Map;
import net.labymod.core.client.gfx.imgui.util.DebugStatistics;
import net.labymod.core.main.profiler.Counters;
import net.labymod.core.main.profiler.CounterType;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.core.main.debug.jvm.Memory;
import net.labymod.core.main.debug.jvm.AllocationRate;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;

public class RenderStatisticsImGuiWindow extends ImGuiWindow
{
    private final AllocationRate allocationRate;
    private final Memory memory;
    private static final String[] UNITS;
    
    public RenderStatisticsImGuiWindow(@Nullable final ImGuiBooleanType visible) {
        super("Game Statistics", visible, 0);
        this.allocationRate = new AllocationRate();
        this.memory = new Memory();
    }
    
    @Override
    protected void renderContent() {
        LabyImGui.keyValuePair("FPS", Laby.labyAPI().minecraft().getFPS());
        Counters.renderCounters(CounterType.NONE);
        this.renderMemory();
        this.renderFrames();
        this.renderCosmetics();
        this.renderInput();
        this.renderDebugFeatures();
        this.renderBuffers();
    }
    
    private void renderBuffers() {
        LabyImGui.separator();
        LabyImGui.text("BufferBuilders:");
        final Map<UUID, Runnable> bufferBuilders = DebugStatistics.getBufferBuilders();
        final int count = bufferBuilders.size();
        int index = 0;
        for (final Map.Entry<UUID, Runnable> entry : bufferBuilders.entrySet()) {
            if (index != count) {
                LabyImGui.separator();
            }
            LabyImGui.text(entry.getKey().toString());
            final Runnable task = entry.getValue();
            task.run();
            ++index;
        }
    }
    
    private void renderMemory() {
        LabyImGui.separator();
        LabyImGui.text("Memory Statistics:");
        LabyImGui.keyValuePair("Memory", this.memory.getMemory());
        LabyImGui.keyValuePair("Allocated", this.memory.getAllocated());
        LabyImGui.keyValuePair("Allocation Rate", this.allocationRate.getAllocationRate());
        LabyImGui.separator();
        LabyImGui.text("GPU Memory Statistics:");
        long vertexBufferSize = 0L;
        long elementBufferSize = 0L;
        for (final Int2ObjectMap.Entry<BufferMemory> entry : RenderProfiler.getBufferMemory().int2ObjectEntrySet()) {
            final BufferMemory bufferMemory = (BufferMemory)entry.getValue();
            if (bufferMemory.getType() == BufferTarget.ARRAY.getId()) {
                vertexBufferSize += bufferMemory.getSize();
            }
            else {
                if (bufferMemory.getType() != BufferTarget.ELEMENT_ARRAY.getId()) {
                    continue;
                }
                elementBufferSize += bufferMemory.getSize();
            }
        }
        LabyImGui.keyValuePair("Vertex Buffer", this.byteToHumanReadable(vertexBufferSize));
        LabyImGui.keyValuePair("Element Buffer", this.byteToHumanReadable(elementBufferSize));
    }
    
    private void renderFrames() {
        LabyImGui.separator();
        LabyImGui.text("Frame Statistics:");
        final SampleLogger logger = Laby.labyAPI().minecraft().frameTimeLogger();
        if (logger == null) {
            LabyImGui.text("FrameTimer is disabled");
        }
        else {
            float average = 0.0f;
            float minValue = Float.MAX_VALUE;
            float maxValue = Float.MIN_VALUE;
            final int startIndex = 0;
            final int maxIndex = logger.size() - startIndex;
            for (int index = 0; index < maxIndex; ++index) {
                final long loggedTime = logger.get(startIndex + index);
                final float time = loggedTime / 1000000.0f;
                minValue = Math.min(minValue, time);
                maxValue = Math.max(maxValue, time);
                average += time;
            }
            final float avgMS = average / maxIndex;
            final String maxFPS = String.format(Locale.ROOT, "%.1f", 1000.0f / minValue);
            final String minFPS = String.format(Locale.ROOT, "%.1f", 1000.0f / maxValue);
            final String avgFPS = String.format(Locale.ROOT, "%.1f", 1000.0f / avgMS);
            final String text = "  Min: " + String.format(Locale.ROOT, "%.1f", minValue) + "ms Avg: " + String.format(Locale.ROOT, "%.1f", avgMS) + "ms Max: " + String.format(Locale.ROOT, "%.1f", maxValue) + "ms\n  Max: " + maxFPS + "fps Avg: " + avgFPS + "fps Min: " + minFPS + "fps";
            LabyImGui.text(text);
        }
    }
    
    private void renderCosmetics() {
        LabyImGui.separator();
        LabyImGui.text("Cosmetic Statistics");
        Counters.renderCounters(CounterType.COSMETICS);
    }
    
    private void renderInput() {
        final Int2ObjectArrayMap<Key> pressedKeys = DefaultKeyMapper.getPressedKeys();
        final Int2ObjectArrayMap<MouseButton> pressedMouseButtons = DefaultKeyMapper.getPressedMouseButtons();
        final boolean hasKeys = !pressedKeys.isEmpty();
        final boolean hasMouseButtons = !pressedMouseButtons.isEmpty();
        if (hasKeys || hasMouseButtons) {
            LabyImGui.separator();
            LabyImGui.text("Input Statistics:");
            if (hasKeys) {
                LabyImGui.keyValuePair("Keys", StringUtil.join((Map<?, ?>)pressedKeys));
            }
            if (hasMouseButtons) {
                LabyImGui.keyValuePair("Mouse Buttons", StringUtil.join((Map<?, ?>)pressedMouseButtons));
            }
        }
    }
    
    private void renderDebugFeatures() {
        final Collection<DebugFeature> debugFeatures = DebugRegistry.getDebugFeatures();
        boolean enabledDebugFeature = false;
        for (final DebugFeature debugFeature : debugFeatures) {
            if (!debugFeature.isEnabled()) {
                continue;
            }
            enabledDebugFeature = true;
            break;
        }
        if (enabledDebugFeature) {
            LabyImGui.separator();
            LabyImGui.text("Debug Features:");
            for (final DebugFeature debugFeature : debugFeatures) {
                if (!debugFeature.isEnabled()) {
                    continue;
                }
                LabyImGui.keyValuePair(debugFeature.getName(), "Enabled");
            }
        }
    }
    
    private String byteToHumanReadable(final long bytes) {
        if (bytes < 1024L) {
            return bytes + " B";
        }
        final int exp = (int)(Math.log((double)bytes) / Math.log(1024.0));
        return String.format(Locale.ROOT, "%.1f %s", bytes / Math.pow(1024.0, exp), RenderStatisticsImGuiWindow.UNITS[exp - 1]);
    }
    
    static {
        UNITS = new String[] { "KB", "MB", "GB" };
    }
}
