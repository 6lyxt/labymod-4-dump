// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.game.ScreenTag;
import net.labymod.api.client.gui.screen.game.ScreenTags;
import net.labymod.api.tag.Tag;
import net.labymod.api.tag.TaggedObject;
import net.labymod.api.client.gui.screen.game.GameScreen;

public enum NamedScreen implements GameScreen
{
    MAIN_MENU("main_menu"), 
    SINGLEPLAYER("singleplayer"), 
    MULTIPLAYER("multiplayer"), 
    EDIT_SERVER("edit_server"), 
    CHAT_INPUT("chat_input", false), 
    CHAT_INPUT_IN_BED("chat_input_in_bed", false), 
    INGAME_MENU("ingame_menu"), 
    CREATE_WORLD("create_world"), 
    DIRECT_CONNECT("direct_connect"), 
    INVENTORY("inventory", false), 
    CREATIVE_INVENTORY("creative_inventory", false), 
    CONNECTING("connecting"), 
    DISCONNECTED("disconnected"), 
    CREDITS("credits"), 
    REALMS("realms"), 
    LEVEL_LOADING("level_loading"), 
    RECEIVING_LEVEL("receiving_level"), 
    PACK_CONFIRM("pack_confirm"), 
    PROGRESS("progress"), 
    GENERIC_MESSAGE("generic_message"), 
    OPEN_LAN_WORLD("open_lan_world"), 
    STATISTICS("statistics"), 
    ADVANCEMENTS("advancements"), 
    CONFIRM("confirm"), 
    SOCIAL_INTERACTIONS("social_interactions"), 
    EDIT_BOOK("book", false), 
    OPTIONS("options", new Tag[] { ScreenTags.OPTIONS }), 
    SKIN_CUSTOMIZATION("skin_customization", new Tag[] { ScreenTags.OPTIONS }), 
    VIDEO_SETTINGS("video_settings", new Tag[] { ScreenTags.OPTIONS }), 
    LANGUAGE_SELECTION("language_selection", new Tag[] { ScreenTags.OPTIONS }), 
    RESOURCE_PACK_SETTINGS("resource_pack_settings", new Tag[] { ScreenTags.OPTIONS }), 
    AUDIO_SETTINGS("audio_settings", new Tag[] { ScreenTags.OPTIONS }), 
    CONTROL_SETTINGS("control_settings", new Tag[] { ScreenTags.OPTIONS }), 
    KEYBIND_SETTINGS("keybind_settings", new Tag[] { ScreenTags.OPTIONS }), 
    MOUSE_SETTINGS("mouse_settings", new Tag[] { ScreenTags.OPTIONS }), 
    CHAT_SETTINGS("chat_settings", new Tag[] { ScreenTags.OPTIONS }), 
    ACCESSIBILITY_SETTINGS("accessibility_settings", new Tag[] { ScreenTags.OPTIONS }), 
    SNOOPER_SETTINGS("snooper_settings", new Tag[] { ScreenTags.OPTIONS });
    
    private final String id;
    private final TaggedObject taggedObject;
    
    private NamedScreen(final String id) {
        this(id, true);
    }
    
    private NamedScreen(final String id, final boolean allowCustomFont) {
        this(id, allowCustomFont, new Tag[0]);
    }
    
    private NamedScreen(final String id, final Tag[] tags) {
        this(id, true, tags);
    }
    
    private NamedScreen(final String id, final boolean allowCustomFont, final Tag[] tags) {
        this.taggedObject = new TaggedObject();
        this.id = id;
        if (allowCustomFont) {
            this.taggedObject.setTag(ScreenTags.ALLOW_CUSTOM_FONT);
        }
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
        return this.taggedObject.hasTag(ScreenTags.ALLOW_CUSTOM_FONT);
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
