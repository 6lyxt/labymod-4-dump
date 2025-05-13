// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style;

import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import net.labymod.api.client.gui.lss.LssStyleException;
import net.labymod.api.client.gui.lss.style.modifier.attribute.VariableAttributePatch;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;
import net.labymod.api.client.gui.lss.style.Selector;
import java.util.Iterator;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import java.util.ArrayList;
import java.util.HashMap;
import net.labymod.api.Laby;
import java.util.List;
import net.labymod.api.client.gui.lss.style.modifier.attribute.StyleInstructions;
import java.util.Map;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.lss.style.StyleSelectorList;

public abstract class DefaultStyleSelectorList implements StyleSelectorList
{
    private final WidgetModifier widgetModifier;
    protected final Map<String, StyleInstructions> patches;
    protected final List<DefaultStyleInstruction> selectors;
    
    public DefaultStyleSelectorList() {
        this.widgetModifier = Laby.references().widgetModifier();
        this.patches = new HashMap<String, StyleInstructions>();
        this.selectors = new ArrayList<DefaultStyleInstruction>();
    }
    
    protected abstract StyleSheet styleSheet();
    
    @Override
    public void add(final String rawSubSelector, final StyleBlock instruction) {
        this.selectors.add(new DefaultStyleInstruction(rawSubSelector, instruction));
    }
    
    @Override
    public void applyToWidget(final Widget widget, final int skipDepth) {
        boolean modified = false;
        for (final DefaultStyleInstruction block : this.selectors) {
            final Selector selector = block.getSelector();
            if (selector.match(skipDepth, widget, false)) {
                for (final Map.Entry<String, SingleInstruction> entry : block.getInstructions().entrySet()) {
                    final String key = entry.getKey();
                    final SingleInstruction instruction = entry.getValue();
                    final String value = instruction.getValue();
                    final AttributePatch patch = this.makePatch(widget, instruction, key, value, block);
                    if (patch != null) {
                        modified = true;
                        widget.addAttributePatch(selector, patch, skipDepth);
                    }
                }
            }
        }
        if (modified) {
            widget.updateState(true);
        }
    }
    
    @Override
    public Map<String, StyleInstructions> generateAttributePatches(final Widget widget, final int skipDepth) {
        this.patches.clear();
        for (final DefaultStyleInstruction block : this.selectors) {
            final Selector selector = block.getSelector();
            if (selector.match(skipDepth, widget, false)) {
                for (final Map.Entry<String, SingleInstruction> entry : block.getInstructions().entrySet()) {
                    final String key = entry.getKey();
                    if (this.patches.containsKey(key)) {
                        continue;
                    }
                    final SingleInstruction instruction = entry.getValue();
                    final String value = instruction.getValue();
                    final AttributePatch patch = this.makePatch(widget, instruction, key, value, block);
                    if (patch == null) {
                        continue;
                    }
                    this.patches.put(key, new StyleInstructions(selector, patch, skipDepth));
                }
            }
        }
        return this.patches;
    }
    
    private AttributePatch makePatch(final Widget widget, final SingleInstruction instruction, final String key, final String value, final DefaultStyleInstruction block) {
        AttributePatch patch;
        if (this.widgetModifier.isVariableKey(key)) {
            patch = new VariableAttributePatch(instruction, value);
        }
        else {
            final Forwarder forwarder = this.widgetModifier.findForwarder(widget, key);
            patch = this.widgetModifier.makeAttributePatch(widget, forwarder, instruction, value);
        }
        if (patch != null) {
            patch.setMeta(block);
        }
        if (patch != null) {
            try {
                patch.init();
            }
            catch (final LssStyleException exception) {
                exception.printStackTrace();
                return null;
            }
        }
        return patch;
    }
}
