// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.function.Predicate;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass;
import java.util.List;
import java.util.ArrayList;
import net.labymod.core.client.gui.lss.style.modifier.state.DefaultPseudoClassRegistry;

public class Branch
{
    private static final DefaultPseudoClassRegistry PSEUDO_CLASS_REGISTRY;
    private final BranchEntry[] entries;
    
    public Branch(final String selector) {
        if (selector.isEmpty()) {
            this.entries = new BranchEntry[0];
            return;
        }
        final List<BranchEntry> entries = new ArrayList<BranchEntry>();
        final String[] array;
        final String[] segments = array = this.parseSelectorSegments(selector).toArray(new String[0]);
        for (final String segment : array) {
            final String[] parts = segment.split(":", 2);
            final String entrySelector = parts[0];
            PseudoClass pseudoClass = null;
            if (parts.length > 1) {
                pseudoClass = Branch.PSEUDO_CLASS_REGISTRY.parse(parts[1]);
            }
            entries.add(new BranchEntry(entrySelector, pseudoClass));
        }
        this.entries = entries.toArray(new BranchEntry[0]);
    }
    
    private List<String> parseSelectorSegments(final String selector) {
        final List<String> out = new ArrayList<String>();
        final StringBuilder builder = new StringBuilder();
        int stateLevel = 0;
        for (final char c : selector.toCharArray()) {
            if (c == '(') {
                ++stateLevel;
            }
            if (c == ')') {
                --stateLevel;
            }
            if (c == '.' && stateLevel == 0 && builder.length() != 0) {
                out.add(builder.toString());
                builder.setLength();
            }
            builder.append(c);
        }
        if (builder.length() != 0) {
            out.add(builder.toString());
        }
        if (stateLevel != 0) {
            throw new IllegalArgumentException("Invalid selector: \"" + selector);
        }
        return out;
    }
    
    public static Branch[] createTree(final String[] selectors) {
        final Branch[] branches = new Branch[selectors.length];
        for (int i = 0; i < selectors.length; ++i) {
            final String selector = selectors[i];
            branches[i] = new Branch(selector);
        }
        return branches;
    }
    
    public BranchEntry[] getEntries() {
        return this.entries;
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (final BranchEntry entry : this.entries) {
            builder.append(entry.getSelector());
            if (entry.getPseudoClass() != null) {
                builder.append(':').append(entry.getPseudoClass().element().toString());
            }
        }
        return builder.toString();
    }
    
    static {
        PSEUDO_CLASS_REGISTRY = (DefaultPseudoClassRegistry)Laby.references().pseudoClassRegistry();
    }
    
    public static class BranchEntry
    {
        private final String selector;
        private final PseudoClass pseudoClass;
        private final Predicate<Widget> predicate;
        
        public BranchEntry(@NotNull final String selector, @Nullable final PseudoClass pseudoClass) {
            this.selector = selector;
            this.pseudoClass = pseudoClass;
            final boolean isId = selector.startsWith(".");
            final boolean isUniqueId = selector.startsWith("#");
            final String id = (isId || isUniqueId) ? selector.substring(1) : selector;
            this.predicate = (widget -> {
                if (id.length() == 0 || this.selector.equals("*")) {
                    return true;
                }
                else if (isUniqueId) {
                    return widget.getUniqueId().equals(id);
                }
                else if (isId) {
                    return widget.hasId(id);
                }
                else {
                    return widget.getTypeName().equals(selector);
                }
            });
        }
        
        @NotNull
        public String getSelector() {
            return this.selector;
        }
        
        @Nullable
        public PseudoClass getPseudoClass() {
            return this.pseudoClass;
        }
        
        public boolean matchesPseudoClass(final Widget widget) {
            return this.pseudoClass == null || this.pseudoClass.matchesState(widget);
        }
        
        public boolean matches(final Widget widget) {
            return this.predicate.test(widget);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final BranchEntry that = (BranchEntry)o;
            return Objects.equals(this.selector, that.selector) && Objects.equals(this.pseudoClass, that.pseudoClass);
        }
        
        @Override
        public int hashCode() {
            int result = this.selector.hashCode();
            result = 31 * result + ((this.pseudoClass != null) ? this.pseudoClass.hashCode() : 0);
            return result;
        }
    }
}
