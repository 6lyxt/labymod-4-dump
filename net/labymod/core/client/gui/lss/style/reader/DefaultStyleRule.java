// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.reader;

import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import net.labymod.api.client.gui.lss.style.modifier.Query;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import java.util.List;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.lss.style.reader.StyleRule;

public class DefaultStyleRule implements StyleRule
{
    private static final WidgetModifier WIDGET_MODIFIER;
    private final StyleSheet sourceStyleSheet;
    private final List<StyleBlock> blocks;
    private final String key;
    private final String value;
    private final Query query;
    
    public DefaultStyleRule(final StyleSheet sourceStyleSheet, final String key, final String value) {
        this(sourceStyleSheet, key + " " + value);
    }
    
    public DefaultStyleRule(final StyleSheet sourceStyleSheet, final String content) {
        this.sourceStyleSheet = sourceStyleSheet;
        this.blocks = new ArrayList<StyleBlock>();
        final String[] parts = content.split(" ", 2);
        this.key = parts[0];
        this.value = parts[1];
        this.query = DefaultStyleRule.WIDGET_MODIFIER.findQuery(this.key);
    }
    
    public DefaultStyleRule(final StyleSheet sourceStyleSheet, final List<StyleBlock> blocks, final DefaultStyleRule rule) {
        this.sourceStyleSheet = sourceStyleSheet;
        this.blocks = blocks;
        this.key = rule.key;
        this.value = rule.value;
        this.query = rule.query;
    }
    
    @NotNull
    @Override
    public StyleSheet sourceStyleSheet() {
        return this.sourceStyleSheet;
    }
    
    @Override
    public String getKey() {
        return this.key;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    @Override
    public List<StyleBlock> getBlocks() {
        return this.blocks;
    }
    
    @Override
    public Object process() {
        if (this.query == null) {
            return null;
        }
        return this.query.process(this);
    }
    
    static {
        WIDGET_MODIFIER = Laby.references().widgetModifier();
    }
}
