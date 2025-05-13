// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style;

import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import java.util.Map;

public interface StyleInstructions
{
    Selector getSelector();
    
    Map<String, SingleInstruction> getInstructions();
    
    StyleBlock getBlock();
}
