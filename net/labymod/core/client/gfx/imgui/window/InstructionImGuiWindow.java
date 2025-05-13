// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.imgui.window;

import java.util.Collection;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import java.util.Map;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import java.util.Arrays;
import net.labymod.api.util.color.format.ColorFormat;
import java.util.Locale;
import net.labymod.api.util.color.format.PackedColor;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass;
import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;
import java.util.Iterator;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.core.client.gui.lss.style.modifier.state.SimplePseudoClass;
import net.labymod.api.client.gui.lss.style.modifier.attribute.PropertyAttributePatch;
import net.labymod.api.client.gui.lss.style.modifier.attribute.StyleInstructions;
import java.util.List;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gfx.imgui.window.DocumentHandler;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;

public class InstructionImGuiWindow extends ImGuiWindow
{
    private static final Logging LOGGER;
    
    public InstructionImGuiWindow(@Nullable final ImGuiBooleanType visible) {
        super("LSS", visible, 0);
    }
    
    @Override
    protected void renderContent() {
        final DocumentHandler documentHandler = Laby.references().documentHandler();
        final Widget selectedWidget = documentHandler.getSelectedWidget();
        if (selectedWidget == null) {
            LabyImGui.text("No widget is selected");
            return;
        }
        this.buildStates(selectedWidget);
        this.buildInstructions(selectedWidget, selectedWidget.getSortedStyleInstructions());
    }
    
    private void buildInstructions(final Widget widget, final List<StyleInstructions> instructions) {
        for (final StyleInstructions instruction : instructions) {
            final AttributePatch patch2 = instruction.patch();
            if (patch2 instanceof final PropertyAttributePatch patch) {
                final net.labymod.api.client.gui.lss.style.StyleInstructions meta = patch.getMeta();
                final StyleBlock block = meta.getBlock();
                final Selector selector = meta.getSelector();
                final PseudoClass lastPseudoClass = selector.lastPseudoClass();
                if (lastPseudoClass instanceof final SimplePseudoClass simplePseudoClass) {
                    if (simplePseudoClass.state().isStaticState()) {
                        continue;
                    }
                }
                final boolean patched = instruction.selector().match(widget, true);
                final String key = patch.getKey();
                final Element element = patch.element();
                final String value = element.getRawValue();
                LabyImGui.beginGroup();
                LabyImGui.text("- ");
                LabyImGui.sameLine(0.0f, 0.0f);
                final TextColor color = patched ? (patch.instruction().isImportant() ? NamedTextColor.GOLD : NamedTextColor.GRAY) : NamedTextColor.RED;
                LabyImGui.text(key, color);
                LabyImGui.sameLine(0.0f, 0.0f);
                LabyImGui.text(": ", NamedTextColor.DARK_GRAY);
                LabyImGui.sameLine(0.0f, 0.0f);
                LabyImGui.text((value == null) ? "null" : value.replace("%", "%%"), -1);
                LabyImGui.sameLine(0.0f, 5.0f);
                final PackedColor packedColor = this.createColor(block, patch);
                if (packedColor != null) {
                    LabyImGui.addRectangle(0.0f, 0.0f, 13.0f, 13.0f, packedColor.value(), 0.0f);
                }
                LabyImGui.endGroup();
                if (!LabyImGui.isItemHovered()) {
                    continue;
                }
                LabyImGui.beginTooltip();
                this.buildInstructionTooltip(widget, patch, selector, block, patched, key);
                LabyImGui.endTooltip();
            }
        }
    }
    
    private PackedColor createColor(final StyleBlock block, final PropertyAttributePatch patch) {
        PackedColor packedColor = null;
        final String lowercaseKey = patch.getKey().toLowerCase(Locale.ROOT);
        if (lowercaseKey.contains("color") && patch.getType() == Integer.class) {
            try {
                final ColorFormat format = ColorFormat.ABGR32;
                final ProcessedObject object = patch.objects()[0];
                packedColor = format.createPackedColor(ColorFormat.ARGB32.packTo(format, (int)object.value()));
            }
            catch (final Exception exception) {
                final String errorMessage = "Color could not be obtained because an error occurred!" + System.lineSeparator() + "Stylesheet: " + String.valueOf(patch.instruction().styleSheet().file()) + System.lineSeparator() + "Line: " + block.getLineOf(patch.getKey()) + System.lineSeparator() + "Computed value: " + Arrays.toString(patch.objects());
                InstructionImGuiWindow.LOGGER.error(errorMessage, exception);
            }
        }
        return packedColor;
    }
    
    private void buildInstructionTooltip(final Widget selectedWidget, final PropertyAttributePatch patch, final Selector selector, final StyleBlock block, final boolean patched, final String key) {
        try {
            final ThemeFile file = patch.instruction().styleSheet().file();
            this.keyValueText("Key", key);
            this.keyValueText("Computed value", Arrays.toString(patch.objects()));
            this.keyValueText("Stylesheet", file.toString());
            this.keyValueText("Full selector", selector.buildSelector());
            this.keyValueText("Line", String.valueOf(block.getLineOf(key)));
            final Map<String, String> variables = patch.collectVariables(selectedWidget);
            if (!variables.isEmpty()) {
                LabyImGui.text("Used Variables:", NamedTextColor.GRAY);
                for (final Map.Entry<String, String> entry : variables.entrySet()) {
                    LabyImGui.text("- ");
                    LabyImGui.sameLine(0.0f, 0.0f);
                    this.keyValueText(entry.getKey(), entry.getValue());
                }
            }
            if (!patched) {
                LabyImGui.text("Currently not applied", NamedTextColor.RED);
            }
            if (patch.instruction().isImportant()) {
                LabyImGui.text("Important!", NamedTextColor.GOLD);
            }
        }
        catch (final Exception exception) {
            InstructionImGuiWindow.LOGGER.error("Failed to build instruction tooltip", exception);
        }
    }
    
    private void keyValueText(final String key, final String value) {
        LabyImGui.text(key, NamedTextColor.GRAY);
        LabyImGui.sameLine(0.0f, 0.0f);
        LabyImGui.text(": ", NamedTextColor.DARK_GRAY);
        LabyImGui.sameLine(0.0f, 0.0f);
        LabyImGui.text(value);
    }
    
    private void buildStates(final Widget widget) {
        LabyImGui.text("States: ", NamedTextColor.GRAY);
        LabyImGui.sameLine(0.0f, 0.0f);
        LabyImGui.text("[", NamedTextColor.GRAY);
        LabyImGui.sameLine(0.0f, 0.0f);
        final Collection<AttributeState> attributeStates = widget.getAttributeStates();
        final int size = attributeStates.size();
        int index = 0;
        for (final AttributeState attributeState : attributeStates) {
            final boolean lastEntry = index == size - 1;
            final TextColor color = attributeState.isEnabled(widget) ? NamedTextColor.GREEN : NamedTextColor.GRAY;
            LabyImGui.text(attributeState.toString(), color);
            LabyImGui.sameLine(0.0f, 0.0f);
            if (!lastEntry) {
                LabyImGui.text(", ", NamedTextColor.GRAY);
                LabyImGui.sameLine(0.0f, 0.0f);
            }
            ++index;
        }
        LabyImGui.text("]", NamedTextColor.GRAY);
        LabyImGui.separator();
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
