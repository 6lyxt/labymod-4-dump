// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.asm.tree;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;

public class VoltInsnList
{
    private final InsnList list;
    private final boolean isStatic;
    private int stackSize;
    
    public VoltInsnList(final boolean isStatic) {
        this.isStatic = isStatic;
        this.list = new InsnList();
    }
    
    public void add(final InsnList list) {
        this.list.add(list);
        ++this.stackSize;
    }
    
    public void addIfNotStatic(final InsnList list) {
        if (this.isStatic) {
            return;
        }
        this.add(list);
    }
    
    public void add(final AbstractInsnNode node) {
        this.list.add(node);
        ++this.stackSize;
    }
    
    public void addIfNotStatic(final AbstractInsnNode node) {
        if (this.isStatic) {
            return;
        }
        this.add(node);
    }
    
    public InsnList getList() {
        return this.list;
    }
    
    public int stackSize() {
        return this.stackSize;
    }
}
