// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server.storage;

import net.labymod.api.util.Color;

public class ServerFolder
{
    public static final Color DEFAULT_COLOR;
    private int index;
    private int length;
    private String name;
    private Color color;
    
    public ServerFolder(final String name, final int index, final int length, final Color color) {
        this.name = name;
        this.index = index;
        this.length = length;
        this.color = color;
    }
    
    public int getStartIndex() {
        return this.index;
    }
    
    public int getEndIndex() {
        return this.index + this.length - 1;
    }
    
    public int getLength() {
        return this.length;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Color getColor() {
        return (this.color == null) ? ServerFolder.DEFAULT_COLOR : this.color;
    }
    
    public void setColor(final Color color) {
        this.color = color;
    }
    
    public void shift(final int offset) {
        if (this.index + offset < 0) {
            throw new IllegalArgumentException("Cannot shift folder below 0");
        }
        this.index += offset;
    }
    
    public void expand() {
        ++this.length;
    }
    
    public void shrink() {
        --this.length;
    }
    
    public boolean isEmpty() {
        return this.length == 0;
    }
    
    @Override
    public String toString() {
        return "Folder[" + this.getStartIndex() + "-" + this.getEndIndex();
    }
    
    static {
        DEFAULT_COLOR = Color.ofRGB(236, 190, 64, 255);
    }
}
