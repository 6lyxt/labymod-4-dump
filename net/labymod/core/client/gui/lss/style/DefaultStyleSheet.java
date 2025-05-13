// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style;

import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import java.util.Collection;
import net.labymod.api.client.gui.lss.style.modifier.attribute.StyleInstructions;
import net.labymod.api.client.gui.screen.widget.Widget;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.gui.lss.style.reader.DefaultStyleRule;
import java.util.Iterator;
import net.labymod.core.client.gui.lss.style.reader.DefaultStyleBlock;
import java.util.ArrayList;
import net.labymod.api.client.gui.lss.meta.LinkMetaList;
import net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaRule;
import net.labymod.api.client.gui.lss.style.reader.StyleRule;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import java.util.List;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import net.labymod.api.client.gui.lss.style.StyleSheet;

public class DefaultStyleSheet implements StyleSheet
{
    private static int LOAD_INDEX_NEXT;
    private final ThemeFile file;
    private final List<StyleBlock> blocks;
    private final List<StyleSheet> imports;
    private final List<StyleRule> rules;
    private final List<MediaRule> mediaQueries;
    private LinkMetaList linkMetaList;
    private final int priority;
    private final int loadIndex;
    private boolean loaded;
    
    public DefaultStyleSheet(final ThemeFile file) {
        this.blocks = new ArrayList<StyleBlock>();
        this.imports = new ArrayList<StyleSheet>();
        this.rules = new ArrayList<StyleRule>();
        this.mediaQueries = new ArrayList<MediaRule>();
        this.file = file;
        this.priority = 0;
        this.loadIndex = DefaultStyleSheet.LOAD_INDEX_NEXT++;
    }
    
    private DefaultStyleSheet(final DefaultStyleSheet styleSheet, final int priority) {
        this.blocks = new ArrayList<StyleBlock>();
        this.imports = new ArrayList<StyleSheet>();
        this.rules = new ArrayList<StyleRule>();
        this.mediaQueries = new ArrayList<MediaRule>();
        this.file = styleSheet.file;
        this.priority = priority;
        this.loadIndex = DefaultStyleSheet.LOAD_INDEX_NEXT++;
        for (final StyleBlock block : styleSheet.blocks) {
            this.blocks.add(new DefaultStyleBlock(this, (DefaultStyleBlock)block));
        }
        for (final StyleRule rule : styleSheet.rules) {
            this.rules.add(this.mapRule(rule));
        }
        for (final MediaRule mediaQuery : styleSheet.mediaQueries) {
            this.mediaQueries.add(new MediaRule(this.mapRule(mediaQuery.getStyleRule()), mediaQuery.getRequirements(), mediaQuery.getIdentifier()));
        }
    }
    
    private StyleRule mapRule(final StyleRule rule) {
        final List<StyleBlock> blocks = new ArrayList<StyleBlock>(rule.getBlocks().size());
        for (final StyleBlock block : rule.getBlocks()) {
            blocks.add(new DefaultStyleBlock(this, (DefaultStyleBlock)block));
        }
        return new DefaultStyleRule(rule.sourceStyleSheet(), blocks, (DefaultStyleRule)rule);
    }
    
    @Override
    public ResourceLocation resource() {
        return this.file.toResourceLocation();
    }
    
    @Override
    public ThemeFile file() {
        return this.file;
    }
    
    @Override
    public void addBlock(final StyleBlock block) {
        if (!this.blocks.contains(block)) {
            this.blocks.add(block);
            ((DefaultStyleBlock)block).setStyleSheet(this);
        }
    }
    
    @Override
    public List<StyleBlock> getBlocks() {
        return this.blocks;
    }
    
    @Override
    public void addImport(final StyleSheet styleSheet) {
        if (!this.imports.contains(styleSheet)) {
            this.imports.add(styleSheet);
            this.mergeImport(styleSheet);
        }
    }
    
    @Override
    public List<StyleSheet> getImports() {
        return this.imports;
    }
    
    @Override
    public List<MediaRule> getMediaRules() {
        return this.mediaQueries;
    }
    
    @Nullable
    @Override
    public LinkMetaList getLinkMetaList() {
        return this.linkMetaList;
    }
    
    @Override
    public void addRule(final StyleRule rule) {
        final Object processed = rule.process();
        if (processed != null && processed instanceof MediaRule) {
            this.mediaQueries.add((MediaRule)processed);
        }
        else if (!this.rules.contains(rule)) {
            this.rules.add(rule);
        }
    }
    
    @Override
    public List<StyleRule> getRules() {
        return this.rules;
    }
    
    @Override
    public StyleRule getRule(final String key) {
        for (final StyleRule rule : this.rules) {
            if (rule.getKey().equals(key)) {
                return rule;
            }
        }
        return null;
    }
    
    @Override
    public StyleRule getRule(final String key, final String value) {
        for (final StyleRule rule : this.rules) {
            if (rule.getKey().equals(key) && rule.getValue().equals(value)) {
                return rule;
            }
        }
        return null;
    }
    
    @Override
    public void applyToWidget(final Widget widget) {
        for (final StyleBlock block : this.blocks) {
            block.applyToWidget(widget, 0);
        }
    }
    
    @Override
    public List<StyleInstructions> getMatchingMediaRules(final Widget widget) {
        List<StyleInstructions> instructions = null;
        for (final MediaRule mediaQuery : this.mediaQueries) {
            if (mediaQuery.matches()) {
                for (final StyleBlock block : mediaQuery.getBlocks()) {
                    if (instructions == null) {
                        instructions = new ArrayList<StyleInstructions>();
                    }
                    instructions.addAll(block.generateAttributePatches(widget, 0).values());
                }
            }
        }
        return instructions;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DefaultStyleSheet that = (DefaultStyleSheet)o;
        return Objects.equals(this.file, that.file);
    }
    
    @Override
    public int hashCode() {
        return this.file.hashCode();
    }
    
    public void setLinkMetaList(final LinkMetaList linkMetaList) {
        this.linkMetaList = linkMetaList;
    }
    
    @Override
    public int getPriority() {
        return this.priority;
    }
    
    @Override
    public int getLoadIndex() {
        return this.loadIndex;
    }
    
    @Override
    public StyleSheet withPriority(final int priority) {
        return new DefaultStyleSheet(this, priority);
    }
    
    @Override
    public int compareTo(@NotNull final StyleSheet o) {
        return Integer.compare(this.getPriority(), o.getPriority());
    }
    
    private void mergeImport(final StyleSheet styleSheet) {
        if (this.loaded) {
            throw new IllegalStateException("This StyleSheet doesn't accept any more imports because it has already been loaded");
        }
        final List<StyleBlock> styleSheetBlocks = styleSheet.getBlocks();
        for (int i = styleSheetBlocks.size() - 1; i >= 0; --i) {
            final StyleBlock block = styleSheetBlocks.get(i);
            boolean found = false;
            for (final StyleBlock styleBlock : this.blocks) {
                if (!styleBlock.getRawSelector().equals(block.getRawSelector())) {
                    continue;
                }
                for (final Map.Entry<String, SingleInstruction> entry : block.getInstructions().entrySet()) {
                    final Map<String, SingleInstruction> instructions = styleBlock.getInstructions();
                    if (!instructions.containsKey(entry.getKey())) {
                        instructions.put(entry.getKey(), entry.getValue());
                    }
                }
                found = true;
                break;
            }
            if (!found) {
                this.blocks.add(0, block);
            }
        }
        final List<StyleRule> styleSheetRules = styleSheet.getRules();
        for (int j = styleSheetRules.size() - 1; j >= 0; --j) {
            final StyleRule rule = styleSheetRules.get(j);
            this.rules.add(0, rule);
        }
        final List<MediaRule> styleSheetMediaQueries = styleSheet.getMediaRules();
        for (int k = styleSheetMediaQueries.size() - 1; k >= 0; --k) {
            final MediaRule mediaQuery = styleSheetMediaQueries.get(k);
            this.mediaQueries.add(0, mediaQuery);
        }
    }
    
    public void setLoaded() {
        this.loaded = true;
    }
    
    static {
        DefaultStyleSheet.LOAD_INDEX_NEXT = 0;
    }
}
