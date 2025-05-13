// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.converter.models;

import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.chat.filter.ChatFilter;

public class LegacyChatFilter
{
    private String filterName;
    private String[] wordsContains;
    private String[] wordsContainsNot;
    private boolean playSound;
    private boolean highlightMessage;
    private int highlightColorR;
    private int highlightColorG;
    private int highlightColorB;
    private boolean hideMessage;
    private boolean displayInSecondChat;
    private boolean filterTooltips;
    
    public ChatFilter convert() {
        final ChatFilter chatFilter = new ChatFilter();
        chatFilter.name().set(this.filterName);
        for (final String word : this.wordsContains) {
            chatFilter.getIncludedTags().add(word);
        }
        for (final String word : this.wordsContainsNot) {
            chatFilter.getExcludedTags().add(word);
        }
        chatFilter.shouldPlaySound().set(this.playSound);
        chatFilter.shouldChangeBackground().set(this.highlightMessage);
        chatFilter.backgroundColor().set(ColorFormat.ARGB32.pack(this.highlightColorR, this.highlightColorG, this.highlightColorB));
        chatFilter.shouldHideMessage().set(this.hideMessage);
        chatFilter.shouldFilterTooltip().set(this.filterTooltips);
        return chatFilter;
    }
    
    public boolean shouldDisplayInSecondChat() {
        return this.displayInSecondChat;
    }
}
