// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style;

import net.labymod.api.client.gui.lss.style.modifier.attribute.StyleInstructions;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.reader.StyleRule;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.lss.meta.LinkMetaList;
import net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaRule;
import java.util.List;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import net.labymod.api.client.resources.ThemeResourceLocation;

public interface StyleSheet extends ThemeResourceLocation, Comparable<StyleSheet>
{
    ThemeFile file();
    
    void addBlock(final StyleBlock p0);
    
    List<StyleBlock> getBlocks();
    
    void addImport(final StyleSheet p0);
    
    List<StyleSheet> getImports();
    
    List<MediaRule> getMediaRules();
    
    @Nullable
    LinkMetaList getLinkMetaList();
    
    void addRule(final StyleRule p0);
    
    List<StyleRule> getRules();
    
    StyleRule getRule(final String p0);
    
    StyleRule getRule(final String p0, final String p1);
    
    void applyToWidget(final Widget p0);
    
    List<StyleInstructions> getMatchingMediaRules(final Widget p0);
    
    int getPriority();
    
    int getLoadIndex();
    
    StyleSheet withPriority(final int p0);
}
