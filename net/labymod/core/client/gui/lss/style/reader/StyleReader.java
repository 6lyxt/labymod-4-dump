// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.reader;

import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import net.labymod.api.util.TextFormat;
import net.labymod.api.client.gui.screen.theme.Theme;
import java.util.Iterator;
import net.labymod.api.client.gui.lss.style.reader.StyleRule;
import java.util.Objects;
import net.labymod.api.client.gui.screen.theme.ExtendingTheme;
import net.labymod.core.client.gui.lss.style.DefaultStyleSheet;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import java.io.IOException;
import java.util.ArrayList;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import java.util.List;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import net.labymod.api.client.gui.lss.StyleSheetLoader;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.util.logging.Logging;
import java.io.DataInputStream;

public class StyleReader extends DataInputStream
{
    private static final Logging LOGGER;
    private static final String IMPORTANT_SUFFIX = " !important";
    private static final WidgetModifier WIDGET_MODIFIER;
    private static final StyleSheetLoader STYLE_SHEET_LOADER;
    private final ThemeFile file;
    private final List<StyleBlock> blocks;
    private final List<DefaultStyleRule> rules;
    private boolean skip;
    private boolean inlineSkip;
    private boolean prepareSkip;
    private boolean foundQuotationMark;
    private boolean searchNextQuotationMark;
    private int lineIndex;
    
    public StyleReader(final ThemeFile file) throws IOException {
        super(file.openInputStream());
        this.blocks = new ArrayList<StyleBlock>();
        this.rules = new ArrayList<DefaultStyleRule>();
        this.skip = false;
        this.inlineSkip = false;
        this.prepareSkip = false;
        this.foundQuotationMark = false;
        this.searchNextQuotationMark = false;
        this.lineIndex = 1;
        this.file = file;
    }
    
    public StyleSheet readStyleSheet() throws IOException {
        final DefaultStyleSheet styleSheet = new DefaultStyleSheet(this.file);
        try {
            while (this.nextNestedStyleBlocks(styleSheet)) {}
        }
        catch (final StyleParseException e) {
            throw e;
        }
        catch (final Exception e2) {
            throw new StyleParseException(this.lineIndex, e2.getMessage());
        }
        for (final StyleBlock block : this.blocks) {
            for (final String subSelector : block.getRawSelector().split(",")) {
                block.add(subSelector, block);
            }
            styleSheet.addBlock(block);
        }
        for (DefaultStyleRule rule : this.rules) {
            if (rule.getKey().equals("import")) {
                try {
                    final String value = rule.getValue();
                    ThemeFile file;
                    if (value.equals("super()")) {
                        final Theme theme = this.file.theme();
                        if (!(theme instanceof ExtendingTheme)) {
                            throw new StyleParseException(this.lineIndex, "Can't import parent style sheet of " + this.file.theme().getId() + " because it has no parent theme");
                        }
                        file = this.file.forTheme(((ExtendingTheme)theme).parentTheme()).normalize();
                    }
                    else {
                        file = ThemeFile.create(this.file.theme(), this.file.getNamespace(), this.file.parent().getPath() + "/" + this.cleanUpBounds(rule.getValue()));
                    }
                    if (Objects.equals(file, this.file)) {
                        throw new StyleParseException(this.lineIndex, "Can't import style sheet into itself (" + this.file.getFullRawPath());
                    }
                    styleSheet.addImport(StyleReader.STYLE_SHEET_LOADER.load(file));
                }
                catch (final Throwable throwable) {
                    StyleReader.LOGGER.error("Could not import style sheet", throwable);
                }
            }
            for (final StyleBlock block2 : rule.getBlocks()) {
                ((DefaultStyleBlock)block2).setStyleSheet(styleSheet);
                for (final String subSelector2 : block2.getRawSelector().split(",")) {
                    block2.add(subSelector2, block2);
                }
            }
            styleSheet.addRule(rule);
        }
        styleSheet.setLoaded();
        return styleSheet;
    }
    
    private boolean nextNestedStyleBlocks(final StyleSheet styleSheet) throws IOException {
        boolean found = false;
        List<StyleBlock> blocks;
        final List<StyleBlock> outerBlocks = blocks = this.blocks;
        int depth = 0;
        StringBuilder buffer = new StringBuilder();
        String secondBuffer = "";
        ReadState state = ReadState.BLANK;
        ReadState prevRuleState = null;
        boolean isString = false;
        int ruleDepth = -1;
        while (this.available() > 0) {
            final char character = (char)this.readByte();
            if (character == '\n') {
                ++this.lineIndex;
            }
            if (character == '\r') {
                continue;
            }
            final boolean isQuotationMark = character == '\"';
            this.handleQuotationMarkSearch(isQuotationMark);
            if (!this.foundQuotationMark) {
                if (this.skip) {
                    if (this.prepareSkip && character == '/') {
                        this.skip = false;
                    }
                    if (this.inlineSkip && character == '\n') {
                        this.prepareSkip = false;
                        this.skip = false;
                        this.inlineSkip = false;
                        continue;
                    }
                    this.prepareSkip = (character == '*');
                    continue;
                }
                else if (this.prepareSkip) {
                    if (character == '*') {
                        this.skip = true;
                        continue;
                    }
                    if (character == '/') {
                        this.skip = true;
                        this.inlineSkip = true;
                        continue;
                    }
                    this.prepareSkip = false;
                    buffer.append('/');
                }
                else {
                    if (character == '/') {
                        this.prepareSkip = true;
                        continue;
                    }
                    this.prepareSkip = false;
                }
            }
            if (isQuotationMark && this.searchNextQuotationMark) {
                this.foundQuotationMark = false;
                this.searchNextQuotationMark = false;
            }
            if (character == '@' && state != ReadState.INSTRUCTION_VALUE) {
                if (depth != 0) {
                    throw new StyleParseException(this.lineIndex, "Cannot handle @-rules inside other blocks");
                }
                prevRuleState = state;
                state = ReadState.RULE;
                buffer = new StringBuilder();
            }
            else {
                if (state == ReadState.RULE) {
                    if (character == '{') {
                        final DefaultStyleRule rule = new DefaultStyleRule(styleSheet, buffer.toString().trim());
                        ruleDepth = depth;
                        this.rules.add(rule);
                        found = true;
                        blocks = rule.getBlocks();
                        buffer = new StringBuilder();
                        state = ReadState.INSTRUCTION_KEY;
                    }
                    else {
                        if (character == '\n' || character == ';') {
                            this.rules.add(new DefaultStyleRule(styleSheet, buffer.toString().trim()));
                            found = true;
                            buffer = new StringBuilder();
                            state = prevRuleState;
                            continue;
                        }
                        buffer.append(character);
                        continue;
                    }
                }
                if (character == '}' && (state == ReadState.BLANK || state == ReadState.INSTRUCTION_KEY)) {
                    if (--depth >= 0 && depth == ruleDepth) {
                        blocks = outerBlocks;
                        ruleDepth = -1;
                    }
                    if (depth == 0) {
                        return found;
                    }
                    if (depth < 0) {
                        throw StyleParseException.unexpectedCharacter(this.lineIndex, character);
                    }
                    buffer = new StringBuilder();
                }
                else {
                    if (state == ReadState.BLANK) {
                        if (character == '{') {
                            throw StyleParseException.unexpectedCharacter(this.lineIndex, character);
                        }
                        if (character == ' ' || character == '\t') {
                            continue;
                        }
                        if (character == '\n') {
                            continue;
                        }
                        state = ReadState.INSTRUCTION_KEY;
                        buffer = new StringBuilder();
                    }
                    if ((state == ReadState.INSTRUCTION_KEY || state == ReadState.INSTRUCTION_VALUE) && character == '{') {
                        StyleBlock parentBlock = null;
                        for (final StyleBlock block : blocks) {
                            if (block.getDepth() < depth) {
                                parentBlock = block;
                            }
                        }
                        final StringBuilder parentSelector = new StringBuilder();
                        if (parentBlock != null) {
                            parentSelector.append(parentBlock.getRawSelector()).append(" ");
                        }
                        final String rawSelector = (state == ReadState.INSTRUCTION_VALUE) ? (secondBuffer + ":" + String.valueOf(buffer)) : buffer.toString();
                        String selector = this.cleanUp(rawSelector).replace(", ", ",");
                        if (selector.startsWith("&")) {
                            selector = selector.substring(1);
                            parentSelector.deleteCharAt(parentSelector.length() - 1);
                        }
                        final StyleBlock styleBlock = new DefaultStyleBlock();
                        styleBlock.setRawSelector(depth, String.valueOf(parentSelector) + selector);
                        ++depth;
                        blocks.add(styleBlock);
                        found = true;
                        state = ReadState.INSTRUCTION_KEY;
                        buffer = new StringBuilder();
                    }
                    else {
                        if (state == ReadState.INSTRUCTION_KEY) {
                            if (character == ';') {
                                throw StyleParseException.unexpectedCharacter(this.lineIndex, character);
                            }
                            if (character == ':') {
                                secondBuffer = buffer.toString();
                                state = ReadState.INSTRUCTION_VALUE;
                                buffer = new StringBuilder();
                                continue;
                            }
                            buffer.append(character);
                        }
                        if (state != ReadState.INSTRUCTION_VALUE) {
                            continue;
                        }
                        if (isString) {
                            if (character == '\n') {
                                throw StyleParseException.unexpectedCharacter(this.lineIndex, character);
                            }
                        }
                        else {
                            if (character == ':') {
                                buffer.insert(0, secondBuffer);
                                secondBuffer = "";
                                state = ReadState.INSTRUCTION_KEY;
                            }
                            if (character == ';') {
                                StyleBlock styleBlock2 = null;
                                for (int i = blocks.size() - 1; i >= 0; --i) {
                                    final StyleBlock block = blocks.get(i);
                                    if (block.getDepth() == depth - 1) {
                                        styleBlock2 = block;
                                        break;
                                    }
                                }
                                if (styleBlock2 == null) {
                                    throw new StyleParseException(this.lineIndex, "Current depth " + depth + " has no block");
                                }
                                String value = this.cleanUpBounds(buffer.toString());
                                if (value.startsWith("@")) {
                                    for (final DefaultStyleRule rule2 : this.rules) {
                                        if (rule2.getKey().equals(value.substring(1))) {
                                            value = this.cleanUpBounds(rule2.getValue());
                                        }
                                    }
                                }
                                final String rawKey = this.cleanUp(secondBuffer);
                                final String key = StyleReader.WIDGET_MODIFIER.isVariableKey(rawKey) ? rawKey : TextFormat.DASH_CASE.toCamelCase(rawKey, true);
                                String resultValue = value;
                                boolean important = false;
                                if (resultValue.endsWith(" !important")) {
                                    important = true;
                                    resultValue = resultValue.substring(0, resultValue.length() - " !important".length());
                                }
                                styleBlock2.put(new SingleInstruction(key, resultValue, this.lineIndex, important, styleSheet));
                                state = ReadState.INSTRUCTION_KEY;
                                buffer = new StringBuilder();
                                continue;
                            }
                        }
                        if (character == '\"') {
                            isString = !isString;
                        }
                        buffer.append(character);
                    }
                }
            }
        }
        return found;
    }
    
    private void handleQuotationMarkSearch(final boolean isQuotationMark) {
        boolean isFirstQuotationMark = false;
        if (isQuotationMark && !this.foundQuotationMark) {
            this.foundQuotationMark = true;
            isFirstQuotationMark = true;
        }
        if (isQuotationMark && !isFirstQuotationMark) {
            this.searchNextQuotationMark = true;
        }
    }
    
    @NotNull
    private String cleanUp(@NotNull String line) {
        while (line.contains("\t")) {
            line = line.replace("\t", " ");
        }
        while (line.contains("\n")) {
            line = line.replace("\n", "");
        }
        while (line.contains("  ")) {
            line = line.replace("  ", " ");
        }
        line = this.cleanUpBounds(line);
        return line;
    }
    
    @NotNull
    private String cleanUpBounds(@NotNull String line) {
        while ((line.startsWith(" ") || line.startsWith("\"")) && line.length() > 1) {
            line = line.substring(1);
        }
        if ((line.endsWith(" ") || line.endsWith("\"")) && line.length() > 1) {
            line = line.substring(0, line.length() - 1);
        }
        return line;
    }
    
    static {
        LOGGER = Logging.getLogger();
        WIDGET_MODIFIER = Laby.references().widgetModifier();
        STYLE_SHEET_LOADER = Laby.references().styleSheetLoader();
    }
    
    private enum ReadState
    {
        BLANK, 
        INSTRUCTION_KEY, 
        INSTRUCTION_VALUE, 
        RULE;
    }
}
