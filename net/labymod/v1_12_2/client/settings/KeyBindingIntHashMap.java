// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.settings;

import net.labymod.v1_12_2.client.LabyModKeyMapping;

public class KeyBindingIntHashMap extends rg<bhy>
{
    public void addKey(final int lvt_1_1_, final bhy lvt_2_1_) {
        if (lvt_2_1_ instanceof LabyModKeyMapping) {
            return;
        }
        super.a(lvt_1_1_, (Object)lvt_2_1_);
    }
}
