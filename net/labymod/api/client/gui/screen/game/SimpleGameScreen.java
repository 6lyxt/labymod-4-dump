// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.game;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.tag.Tag;
import net.labymod.api.tag.TaggedObject;

public class SimpleGameScreen implements GameScreen
{
    private final String id;
    private final boolean allowCustomFont;
    private final TaggedObject taggedObject;
    
    public SimpleGameScreen(final String id, final boolean allowCustomFont, final ScreenTag screenTag) {
        this.taggedObject = new TaggedObject();
        this.id = id;
        this.allowCustomFont = allowCustomFont;
        if (screenTag == null) {
            return;
        }
        this.taggedObject.setTag(screenTag.getTag());
    }
    
    public SimpleGameScreen(final String id, final boolean allowCustomFont, final Tag... tags) {
        this.taggedObject = new TaggedObject();
        this.id = id;
        this.allowCustomFont = allowCustomFont;
        for (final Tag tag : tags) {
            this.taggedObject.setTag(tag);
        }
    }
    
    @Override
    public String getId() {
        return this.id;
    }
    
    @Override
    public boolean allowCustomFont() {
        return this.allowCustomFont;
    }
    
    @Nullable
    @Override
    public ScreenTag getTag() {
        return null;
    }
    
    @Override
    public TaggedObject taggedObject() {
        return this.taggedObject;
    }
}
