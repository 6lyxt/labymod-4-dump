// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.attribute.PropertyAttributePatch;
import java.util.function.Predicate;
import net.labymod.api.util.ThreadSafe;
import java.util.ListIterator;
import net.labymod.api.util.CollectionHelper;
import java.util.Comparator;
import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;
import java.util.Set;
import java.util.HashSet;
import org.jetbrains.annotations.ApiStatus;
import java.util.Collections;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;
import net.labymod.api.client.gui.lss.variable.LssVariable;
import java.util.Map;
import net.labymod.api.client.gui.lss.style.modifier.attribute.StyleInstructions;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import java.util.List;
import net.labymod.api.client.gui.lss.injector.LssInjector;

public abstract class StyledWidget implements Widget
{
    private static final LssInjector LSS_INJECTOR;
    protected final List<StyleSheet> styleSheets;
    protected final List<StyleInstructions> styleInstructions;
    protected final List<StyleInstructions> dynamicStyleInstructions;
    private boolean hasStateAttributes;
    private boolean hasParentStateAttributes;
    protected boolean preventStateUpdate;
    protected Widget actualWidget;
    private final Map<String, LssVariable> variablesForUpdate;
    
    protected StyledWidget() {
        this.styleSheets = new ArrayList<StyleSheet>();
        this.styleInstructions = new ArrayList<StyleInstructions>();
        this.dynamicStyleInstructions = new ArrayList<StyleInstructions>();
        this.hasStateAttributes = false;
        this.hasParentStateAttributes = false;
        this.preventStateUpdate = false;
        this.actualWidget = this;
        this.variablesForUpdate = new HashMap<String, LssVariable>();
    }
    
    @Override
    public void reset() {
        this.styleSheets.clear();
        this.styleInstructions.clear();
        this.hasStateAttributes = false;
    }
    
    @Override
    public void applyStyleSheet(final StyleSheet styleSheet) {
        this.applyStyleSheetInternal(styleSheet);
    }
    
    protected void reloadStyleSheets() {
        this.reloadStyleSheets(true);
    }
    
    protected void reloadStyleSheets(final boolean updateParent) {
        final StyleSheet[] styleSheets = this.styleSheets.toArray(new StyleSheet[0]);
        this.styleSheets.clear();
        for (final Widget child : this.getChildren()) {
            if (child instanceof final StyledWidget styledWidget) {
                styledWidget.reloadStyleSheets(updateParent);
            }
        }
        this.applyStyleSheets(styleSheets, true, updateParent);
    }
    
    @ApiStatus.Internal
    protected void applyStyleSheetInternal(final StyleSheet styleSheet) {
        if (this.styleSheets.contains(styleSheet)) {
            return;
        }
        this.styleSheets.add(styleSheet);
        styleSheet.applyToWidget(this);
        final List<StyleSheet> styleSheetInjectors = StyledWidget.LSS_INJECTOR.getMatchedStyleSheetInjectors(styleSheet);
        this.styleSheets.addAll(styleSheetInjectors);
        for (final StyleSheet injectorStyleSheet : styleSheetInjectors) {
            injectorStyleSheet.applyToWidget(this);
        }
        final List<StyleSheet> widgetInjectors = StyledWidget.LSS_INJECTOR.getMatchedWidgetInjectors(this);
        this.styleSheets.addAll(widgetInjectors);
        for (final StyleSheet injectorStyleSheet2 : widgetInjectors) {
            injectorStyleSheet2.applyToWidget(this);
        }
        Collections.sort(this.styleSheets);
    }
    
    protected void applyStyleSheets(final StyleSheet[] styleSheets) {
        this.applyStyleSheets(styleSheets, false);
    }
    
    protected void applyStyleSheets(final StyleSheet[] styleSheets, final boolean direct) {
        this.applyStyleSheets(styleSheets, direct, true);
    }
    
    protected void applyStyleSheets(final StyleSheet[] styleSheets, final boolean direct, final boolean updateParent) {
        for (final StyleSheet styleSheet : styleSheets) {
            if (direct) {
                this.applyStyleSheetInternal(styleSheet);
            }
            else {
                this.applyStyleSheet(styleSheet);
            }
        }
        this.postStyleSheetLoad();
        this.updateState(true);
        this.updateBounds();
        this.handleAttributes();
        if (updateParent && this.getParent() instanceof Widget) {
            ((Widget)this.getParent()).updateBounds();
            ((Widget)this.getParent()).handleAttributes();
        }
    }
    
    @Override
    public void updateState(final boolean force) {
        if (this.preventStateUpdate) {
            return;
        }
        if (force) {
            this.patchAttributes();
        }
        if (this.hasStateAttributes || force) {
            this.patchAttributes();
            for (final Widget child : this.getChildren()) {
                if (child.hasParentStateAttributes()) {
                    child.updateState(force);
                }
            }
            this.updateBounds();
            this.handleAttributes();
        }
    }
    
    @Override
    public void resetState() {
        for (final StyleInstructions styleInstruction : this.styleInstructions) {
            styleInstruction.patch().unpatch(this);
        }
    }
    
    @Override
    public void patchAttributes() {
        final List<StyleInstructions> instructions = this.getSortedStyleInstructions();
        final Set<String> patchedKeys = new HashSet<String>();
        for (final StyleInstructions instruction : instructions) {
            this.patchAttribute(instruction, patchedKeys);
        }
        for (final StyleInstructions instruction : this.getDynamicSortedStyleInstructions()) {
            this.patchAttribute(instruction, patchedKeys);
        }
    }
    
    private void patchAttribute(final StyleInstructions instruction, final Set<String> patchedKeys) {
        if (instruction.selector().match(instruction.getSkipDepth(), this, true)) {
            this.patch(instruction.patch());
            patchedKeys.add(instruction.patch().getKey());
        }
        else if (!patchedKeys.contains(instruction.patch().getKey()) && instruction.selector().match(instruction.getSkipDepth(), this, false)) {
            instruction.patch().unpatch(this);
        }
    }
    
    @Override
    public void patch(final AttributePatch patch) {
        patch.patch(this);
    }
    
    @Override
    public void addAttributePatch(final Selector selector, final AttributePatch patch, final int skipDepth) {
        final StyleInstructions styleInstruction = new StyleInstructions(selector, patch, skipDepth);
        if (this.styleInstructions.contains(styleInstruction)) {
            return;
        }
        this.styleInstructions.add(styleInstruction);
        try {
            this.styleInstructions.sort(StyleInstructions.COMPARATOR);
        }
        catch (final IllegalArgumentException e) {
            e.printStackTrace();
        }
        if (!selector.hasStateAttributes() && CollectionHelper.noneMatch(this.styleInstructions, styleInstructions -> styleInstructions.patch().equalsKey(patch) && styleInstructions.selector().hasStateAttributes())) {
            final ListIterator<StyleInstructions> iterator = this.styleInstructions.listIterator(this.styleInstructions.size());
            boolean found = false;
            while (iterator.hasPrevious()) {
                final StyleInstructions previous = iterator.previous();
                if (previous.patch().equals(patch) || (previous.patch().equalsKey(patch) && !previous.selector().hasStateAttributes() && previous.patch().instruction().styleSheet().getPriority() != patch.instruction().styleSheet().getPriority())) {
                    if (found) {
                        iterator.remove();
                    }
                    else {
                        found = true;
                    }
                }
            }
        }
        if (selector.hasStateAttributes()) {
            this.hasStateAttributes = true;
            if (selector.hasParentStateAttributes()) {
                this.hasParentStateAttributes = true;
            }
        }
    }
    
    @Override
    public List<StyleInstructions> getStyleInstructions() {
        return this.styleInstructions;
    }
    
    @Override
    public List<StyleInstructions> getSortedStyleInstructions() {
        return this.styleInstructions;
    }
    
    @Override
    public List<StyleInstructions> getDynamicStyleInstructions() {
        return this.dynamicStyleInstructions;
    }
    
    @Override
    public List<StyleInstructions> getDynamicSortedStyleInstructions() {
        return this.dynamicStyleInstructions;
    }
    
    @Override
    public boolean hasParentStateAttributes() {
        return this.hasParentStateAttributes;
    }
    
    @Override
    public Widget actualWidget() {
        return this.actualWidget;
    }
    
    @Override
    public void applyMediaRules(final boolean updateState) {
        this.dynamicStyleInstructions.clear();
        for (final StyleSheet styleSheet : this.styleSheets) {
            final List<StyleInstructions> matchingMediaRules = styleSheet.getMatchingMediaRules(this);
            if (matchingMediaRules == null) {
                continue;
            }
            this.dynamicStyleInstructions.addAll(matchingMediaRules);
        }
        if (this.dynamicStyleInstructions.isEmpty()) {
            return;
        }
        if (updateState) {
            try {
                this.updateState(true);
            }
            catch (final StackOverflowError e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void updateLssVariable(final LssVariable variable) {
        ThreadSafe.ensureRenderThread();
        this.variablesForUpdate.put(variable.key(), variable);
    }
    
    @Override
    public void forceUpdateLssVariable(final LssVariable variable) {
        this.refreshProperties(variable);
        this.variablesForUpdate.remove(variable.key());
    }
    
    private void refreshProperties(final LssVariable variable) {
        this.refreshProperties(patch -> this.requiresUpdate(patch.element(), variable));
    }
    
    private void refreshProperties(final Map<String, LssVariable> variables) {
        if (variables.size() == 1) {
            this.refreshProperties(variables.values().iterator().next());
            return;
        }
        this.refreshProperties(patch -> {
            final Map<String, String> patchVariables = patch.collectVariables(this);
            if (patchVariables.isEmpty()) {
                return false;
            }
            else {
                variables.keySet().iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final String variable = iterator.next();
                    if (patchVariables.containsKey(variable)) {
                        return true;
                    }
                }
                return false;
            }
        });
    }
    
    private void refreshProperties(final Predicate<PropertyAttributePatch> predicate) {
        boolean updated = false;
        for (final StyleInstructions styleInstruction : this.styleInstructions) {
            final AttributePatch patch = styleInstruction.patch();
            if (patch instanceof PropertyAttributePatch && predicate.test((PropertyAttributePatch)patch)) {
                updated = true;
            }
        }
        if (updated) {
            this.patchAttributes();
            this.updateState(false);
            this.handleAttributes();
        }
    }
    
    private boolean requiresUpdate(final net.labymod.api.client.gui.lss.style.function.Element element, final LssVariable variable) {
        if (!(element instanceof Function)) {
            return false;
        }
        final Function function = (Function)element;
        if (function.getName().equals("var") && function.parameters().length == 1) {
            return function.parameters()[0].getRawValue().equals(variable.key());
        }
        for (final net.labymod.api.client.gui.lss.style.function.Element parameter : function.parameters()) {
            if (this.requiresUpdate(parameter, variable)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float tickDelta) {
        if (!this.variablesForUpdate.isEmpty()) {
            this.refreshProperties(this.variablesForUpdate);
            this.variablesForUpdate.clear();
        }
    }
    
    @Override
    public void render(final ScreenContext context) {
        if (!this.variablesForUpdate.isEmpty()) {
            this.refreshProperties(this.variablesForUpdate);
            this.variablesForUpdate.clear();
        }
    }
    
    public List<StyleSheet> getStyleSheets() {
        return this.styleSheets;
    }
    
    static {
        LSS_INJECTOR = Laby.references().lssInjector();
    }
}
