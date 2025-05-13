// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style;

import java.util.Arrays;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.Selector;

public class DefaultSelector implements Selector
{
    private final Branch[] tree;
    private boolean hasStateAttributes;
    private boolean hasParentStateAttributes;
    
    public DefaultSelector(final String selector) {
        this(selector.split(" "));
    }
    
    public DefaultSelector(final String[] selectors) {
        this(Branch.createTree(selectors));
    }
    
    public DefaultSelector(final Branch[] branches) {
        this.tree = branches;
        for (int i = 0; i < this.tree.length; ++i) {
            for (final Branch.BranchEntry entry : this.tree[i].getEntries()) {
                if (entry.getPseudoClass() != null) {
                    this.hasStateAttributes = true;
                    if (i != this.tree.length - 1) {
                        this.hasParentStateAttributes = true;
                    }
                }
            }
        }
    }
    
    public Branch[] getTree() {
        return this.tree;
    }
    
    @Override
    public boolean hasStateAttributes() {
        return this.hasStateAttributes;
    }
    
    @Override
    public boolean hasParentStateAttributes() {
        return this.hasParentStateAttributes;
    }
    
    @Override
    public boolean hasEnabledStateAttributes(final Widget widget) {
        if (!this.hasStateAttributes) {
            return false;
        }
        for (final Branch branch : this.tree) {
            for (final Branch.BranchEntry entry : branch.getEntries()) {
                if (entry.getPseudoClass() != null && entry.matchesPseudoClass(widget)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public PseudoClass lastPseudoClass() {
        if (this.tree.length == 0) {
            return null;
        }
        final Branch.BranchEntry[] entries = this.tree[this.tree.length - 1].getEntries();
        return entries[entries.length - 1].getPseudoClass();
    }
    
    @Override
    public String buildSelector() {
        final StringBuilder selector = new StringBuilder();
        for (final Branch subSelector : this.tree) {
            if (selector.length() > 0) {
                selector.append(" ");
            }
            selector.append(subSelector);
        }
        return selector.toString();
    }
    
    @Override
    public boolean match(final Widget widget, final boolean matchStates) {
        return this.match(0, widget, matchStates);
    }
    
    @Override
    public boolean match(final int depth, final Widget widget, final boolean matchStates) {
        if (this.tree.length == 1) {
            return this.compareSelector(widget, this.tree[0], matchStates);
        }
        final Branch branch = this.tree[this.tree.length - depth - 1];
        if (!this.compareSelector(widget, branch, matchStates)) {
            final Parent parent = widget.getParent();
            return depth != 0 && parent instanceof Widget && this.match(depth, (Widget)parent, matchStates);
        }
        if (depth >= this.tree.length - 1) {
            return true;
        }
        final Parent parent = widget.getParent();
        return parent instanceof Widget && this.match(depth + 1, (Widget)parent, matchStates);
    }
    
    private boolean compareSelector(final Widget widget, final Branch branch, final boolean matchStates) {
        final Branch.BranchEntry[] entries2;
        final Branch.BranchEntry[] entries = entries2 = branch.getEntries();
        for (final Branch.BranchEntry entry : entries2) {
            if (matchStates && !entry.matchesPseudoClass(widget)) {
                return false;
            }
            if (!entry.matches(widget)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DefaultSelector that = (DefaultSelector)o;
        return Arrays.equals(this.tree, that.tree);
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.tree);
    }
    
    @Override
    public String toString() {
        return this.buildSelector();
    }
}
