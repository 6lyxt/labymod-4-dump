// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.modifier.attribute;

import java.util.Objects;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.style.Selector;
import java.util.Comparator;

public class StyleInstructions
{
    public static final Comparator<StyleInstructions> COMPARATOR;
    private final Selector selector;
    private final AttributePatch patch;
    private final int skipDepth;
    
    public StyleInstructions(final Selector selector, final AttributePatch patch, final int skipDepth) {
        this.selector = selector;
        this.patch = patch;
        this.skipDepth = skipDepth;
    }
    
    public static int compare(final StyleInstructions a, final StyleInstructions b) {
        if (a.equals(b)) {
            return 0;
        }
        final StyleSheet styleA = a.patch().instruction().styleSheet();
        final StyleSheet styleB = b.patch().instruction().styleSheet();
        if (!styleA.equals(styleB)) {
            if (styleB.getImports().contains(styleA)) {
                return -1;
            }
            if (styleA.getImports().contains(styleB)) {
                return 1;
            }
            final int c4 = Integer.compare(styleA.getPriority(), styleB.getPriority());
            if (c4 != 0) {
                return c4;
            }
        }
        final int c5 = Boolean.compare(a.patch().instruction().isImportant(), b.patch().instruction().isImportant());
        if (c5 != 0) {
            return c5;
        }
        final int c6 = Boolean.compare(a.selector().hasStateAttributes(), b.selector().hasStateAttributes());
        if (c6 != 0) {
            return c6;
        }
        if (a.patch() instanceof PropertyAttributePatch && b.patch() instanceof PropertyAttributePatch) {
            final int c7 = Integer.compare(((PropertyAttributePatch)b.patch()).forwarder().getPriority(), ((PropertyAttributePatch)a.patch()).forwarder().getPriority());
            if (c7 != 0) {
                return c7;
            }
        }
        try {
            final PseudoClass pca = a.selector().lastPseudoClass();
            final PseudoClass pcb = b.selector().lastPseudoClass();
            final int c8 = Integer.compare((pca != null) ? pca.getPriority() : Integer.MIN_VALUE, (pcb != null) ? pcb.getPriority() : Integer.MIN_VALUE);
            if (c8 != 0) {
                return c8;
            }
        }
        catch (final ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        if (styleA.equals(styleB)) {
            return Integer.compare(a.getLineNumber(), b.getLineNumber());
        }
        return Integer.compare(styleA.getLoadIndex(), styleB.getLoadIndex());
    }
    
    public Selector selector() {
        return this.selector;
    }
    
    public int getSkipDepth() {
        return this.skipDepth;
    }
    
    public AttributePatch patch() {
        return this.patch;
    }
    
    public int getLineNumber() {
        return this.patch.getMeta().getBlock().getLineOf(this.patch.getKey());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final StyleInstructions that = (StyleInstructions)o;
        return Objects.equals(this.selector, that.selector) && Objects.equals(this.patch, that.patch);
    }
    
    @Override
    public int hashCode() {
        int result = (this.selector != null) ? this.selector.hashCode() : 0;
        result = 31 * result + ((this.patch != null) ? this.patch.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "StyleInstructions{" + this.patch.getKey() + ": " + this.patch.rawValue();
    }
    
    static {
        COMPARATOR = StyleInstructions::compare;
    }
}
