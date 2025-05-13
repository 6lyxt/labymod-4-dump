// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api;

import net.labymod.api.client.resources.ThemeResourceLocation;
import net.labymod.api.util.StringUtil;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.texture.ThemeTextureLocation;
import net.labymod.api.client.resources.ResourceLocation;

public class Textures
{
    public static final ResourceLocation EMPTY;
    public static final ResourceLocation SPRAY_LOADING;
    public static final ResourceLocation WHITE;
    public static final ResourceLocation NOTIFICATION;
    public static final ResourceLocation POPUP_BACKGROUND;
    public static final ResourceLocation TRANSPARENT;
    public static final ResourceLocation LABYMOD_LOGO;
    public static final ResourceLocation STAR;
    public static final ResourceLocation DEFAULT_SERVER_ICON;
    public static final ResourceLocation SURVIVAL_INVENTORY_BACKGROUND;
    public static final ResourceLocation CREATIVE_INVENTORY_TAB_INVENTORY;
    public static final ResourceLocation CREATIVE_INVENTORY_TAB_ITEM_SEARCH;
    public static final ResourceLocation CREATIVE_INVENTORY_TAB_ITEMS;
    public static final ResourceLocation CREATIVE_INVENTORY_TABS;
    
    static {
        EMPTY = Constants.Resources.resource("textures/empty.png");
        SPRAY_LOADING = Constants.Resources.resource("textures/spray/spray_loading.png");
        WHITE = ThemeTextureLocation.of("white");
        NOTIFICATION = ThemeTextureLocation.of("activities/notification", 128);
        POPUP_BACKGROUND = ThemeTextureLocation.of("activities/popup_background", 128);
        TRANSPARENT = ThemeTextureLocation.of("transparent");
        LABYMOD_LOGO = ThemeTextureLocation.of("labymod_logo");
        STAR = ThemeTextureLocation.of("misc/star");
        DEFAULT_SERVER_ICON = ThemeTextureLocation.of("widgets/default_server_icon");
        SURVIVAL_INVENTORY_BACKGROUND = ThemeTextureLocation.of("activities/inventory/inventory");
        CREATIVE_INVENTORY_TAB_INVENTORY = ThemeTextureLocation.of("activities/inventory/creative_inventory/tab_inventory");
        CREATIVE_INVENTORY_TAB_ITEM_SEARCH = ThemeTextureLocation.of("activities/inventory/creative_inventory/tab_item_search");
        CREATIVE_INVENTORY_TAB_ITEMS = ThemeTextureLocation.of("activities/inventory/creative_inventory/tab_items");
        CREATIVE_INVENTORY_TABS = ThemeTextureLocation.of("activities/inventory/creative_inventory/tabs");
    }
    
    public static class SpriteMinecraftIcons
    {
        public static final ThemeTextureLocation TEXTURE;
        public static final Icon LANGUAGE;
        public static final Icon ACCESSIBILITY;
        public static final Icon LOCK_CLOSED;
        public static final Icon LOCK_OPEN;
        
        static {
            TEXTURE = ThemeTextureLocation.of("activities/minecraft_icons", 128, 128);
            LANGUAGE = Icon.sprite(SpriteMinecraftIcons.TEXTURE, 0, 0, 16);
            ACCESSIBILITY = Icon.sprite(SpriteMinecraftIcons.TEXTURE, 1, 0, 16);
            LOCK_CLOSED = Icon.sprite(SpriteMinecraftIcons.TEXTURE, 2, 0, 16);
            LOCK_OPEN = Icon.sprite(SpriteMinecraftIcons.TEXTURE, 3, 0, 16);
        }
    }
    
    public static class SpriteCommon
    {
        public static final ThemeTextureLocation TEXTURE;
        public static final Icon WHITE_X;
        public static final Icon GREEN_CHECKED;
        public static final Icon ARROW_RIGHT;
        public static final Icon ARROW_LEFT;
        public static final Icon MOUSE_RIGHT;
        public static final Icon MOUSE_LEFT;
        public static final Icon GREATER_THAN;
        public static final Icon LESS_THAN;
        public static final Icon COPY;
        public static final Icon SETTINGS;
        public static final Icon TRASH;
        public static final Icon ADD;
        public static final Icon X;
        public static final Icon QUESTION_MARK;
        public static final Icon LARGE_BURGER_DOTS;
        public static final Icon LARGE_DOTS;
        public static final Icon EDIT;
        public static final Icon REFRESH;
        public static final Icon PIN;
        public static final Icon DARK_ADD;
        public static final Icon SMALL_BURGER_DOTS;
        public static final Icon SMALL_BURGER_WITH_SHADOW;
        public static final Icon SMALL_BURGER;
        public static final Icon EXPORT;
        public static final Icon SMALL_UP_SHADOW;
        public static final Icon SMALL_DOWN_SHADOW;
        public static final Icon SMALL_UP;
        public static final Icon SMALL_DOWN;
        public static final Icon DELIMITER;
        public static final Icon SMALL_X;
        public static final Icon SMALL_CHECKED;
        public static final Icon SMALL_ADD;
        public static final Icon SMALL_ADD_WITH_SHADOW;
        public static final Icon BACK_BUTTON;
        public static final Icon FORWARD_BUTTON;
        public static final Icon SMALL_X_WITH_SHADOW;
        public static final Icon MEDIUM_X_WITH_SHADOW;
        public static final Icon CHAT_BUBBLE;
        public static final Icon MULTIPLAYER;
        public static final Icon SINGLEPLAYER;
        public static final Icon WORKBENCH;
        public static final Icon LABYMOD;
        public static final Icon WHITE_GREATER_THAN;
        public static final Icon WHITE_LESS_THAN;
        public static final Icon SUBMIT;
        public static final Icon SHIELD;
        public static final Icon ROBOT;
        public static final Icon HOPPER;
        public static final Icon SYMBOLS;
        public static final Icon BOOK;
        public static final Icon CIRCLE_WARNING;
        public static final Icon BULLET_POINT;
        public static final Icon CROWN;
        public static final Icon PICTURE;
        public static final Icon CART;
        public static final Icon DISCONNECT;
        public static final Icon FOLDER;
        public static final Icon LEAVE_FOLDER;
        public static final Icon LOOTBOX;
        public static final Icon OPEN_FILE;
        public static final Icon LARGE_COPY;
        public static final Icon UPLOAD;
        public static final Icon PAINT;
        public static final Icon SHARE;
        public static final Icon STATUS_INDICATOR;
        public static final Icon BUG;
        public static final Icon NEW;
        public static final Icon EXCLAMATION_MARK_DARK;
        public static final Icon EXCLAMATION_MARK_LIGHT;
        public static final Icon PEPE_SAD;
        
        static {
            TEXTURE = ThemeTextureLocation.of("activities/common", 128, 128);
            WHITE_X = Icon.sprite(SpriteCommon.TEXTURE, 0, 0, 16);
            GREEN_CHECKED = Icon.sprite(SpriteCommon.TEXTURE, 1, 0, 16);
            ARROW_RIGHT = Icon.sprite(SpriteCommon.TEXTURE, 3, 0, 16);
            ARROW_LEFT = Icon.sprite(SpriteCommon.TEXTURE, 3, 1, 16);
            MOUSE_RIGHT = Icon.sprite(SpriteCommon.TEXTURE, 4, 0, 16);
            MOUSE_LEFT = Icon.sprite(SpriteCommon.TEXTURE, 4, 1, 16);
            GREATER_THAN = Icon.sprite(SpriteCommon.TEXTURE, 5, 0, 16);
            LESS_THAN = Icon.sprite(SpriteCommon.TEXTURE, 5, 1, 16);
            COPY = Icon.sprite(SpriteCommon.TEXTURE, 12, 0, 8);
            SETTINGS = Icon.sprite(SpriteCommon.TEXTURE, 2, 0, 16);
            TRASH = Icon.sprite(SpriteCommon.TEXTURE, 2, 1, 16);
            ADD = Icon.sprite(SpriteCommon.TEXTURE, 6, 1, 16);
            X = Icon.sprite(SpriteCommon.TEXTURE, 6, 2, 16);
            QUESTION_MARK = Icon.sprite(SpriteCommon.TEXTURE, 7, 1, 16);
            LARGE_BURGER_DOTS = Icon.sprite(SpriteCommon.TEXTURE, 0, 1, 16);
            LARGE_DOTS = Icon.sprite(SpriteCommon.TEXTURE, 4, 2, 16);
            EDIT = Icon.sprite(SpriteCommon.TEXTURE, 14, 4, 8);
            REFRESH = Icon.sprite(SpriteCommon.TEXTURE, 14, 5, 8);
            PIN = Icon.sprite(SpriteCommon.TEXTURE, 2, 2, 8);
            DARK_ADD = Icon.sprite(SpriteCommon.TEXTURE, 3, 2, 8);
            SMALL_BURGER_DOTS = Icon.sprite(SpriteCommon.TEXTURE, 2, 3, 8);
            SMALL_BURGER_WITH_SHADOW = Icon.sprite(SpriteCommon.TEXTURE, 3, 3, 8);
            SMALL_BURGER = Icon.sprite(SpriteCommon.TEXTURE, 6, 4, 8);
            EXPORT = Icon.sprite(SpriteCommon.TEXTURE, 6, 5, 8);
            SMALL_UP_SHADOW = Icon.sprite(SpriteCommon.TEXTURE, 7, 4, 8);
            SMALL_DOWN_SHADOW = Icon.sprite(SpriteCommon.TEXTURE, 7, 5, 8);
            SMALL_UP = Icon.sprite(SpriteCommon.TEXTURE, 0, 4, 8);
            SMALL_DOWN = Icon.sprite(SpriteCommon.TEXTURE, 0, 5, 8);
            DELIMITER = Icon.sprite(SpriteCommon.TEXTURE, 15, 2, 8, 16);
            SMALL_X = Icon.sprite(SpriteCommon.TEXTURE, 2, 4, 8);
            SMALL_CHECKED = Icon.sprite(SpriteCommon.TEXTURE, 2, 5, 8);
            SMALL_ADD = Icon.sprite(SpriteCommon.TEXTURE, 3, 4, 8);
            SMALL_ADD_WITH_SHADOW = Icon.sprite(SpriteCommon.TEXTURE, 3, 5, 8);
            BACK_BUTTON = Icon.sprite(SpriteCommon.TEXTURE, 4, 4, 8);
            FORWARD_BUTTON = Icon.sprite(SpriteCommon.TEXTURE, 5, 4, 8);
            SMALL_X_WITH_SHADOW = Icon.sprite(SpriteCommon.TEXTURE, 4, 5, 8);
            MEDIUM_X_WITH_SHADOW = Icon.sprite(SpriteCommon.TEXTURE, 5, 5, 8);
            CHAT_BUBBLE = Icon.sprite(SpriteCommon.TEXTURE, 0, 3, 16);
            MULTIPLAYER = Icon.sprite(SpriteCommon.TEXTURE, 1, 3, 16);
            SINGLEPLAYER = Icon.sprite(SpriteCommon.TEXTURE, 2, 3, 16);
            WORKBENCH = Icon.sprite(SpriteCommon.TEXTURE, 3, 3, 16);
            LABYMOD = Icon.sprite(SpriteCommon.TEXTURE, 4, 3, 16);
            WHITE_GREATER_THAN = Icon.sprite(SpriteCommon.TEXTURE, 5, 2, 16);
            WHITE_LESS_THAN = Icon.sprite(SpriteCommon.TEXTURE, 5, 3, 16);
            SUBMIT = Icon.sprite(SpriteCommon.TEXTURE, 6, 3, 16);
            SHIELD = Icon.sprite(SpriteCommon.TEXTURE, 7, 3, 16);
            ROBOT = Icon.sprite(SpriteCommon.TEXTURE, 0, 6, 16);
            HOPPER = Icon.sprite(SpriteCommon.TEXTURE, 1, 6, 16);
            SYMBOLS = Icon.sprite(SpriteCommon.TEXTURE, 2, 6, 16);
            BOOK = Icon.sprite(SpriteCommon.TEXTURE, 3, 6, 16);
            CIRCLE_WARNING = Icon.sprite(SpriteCommon.TEXTURE, 4, 6, 16);
            BULLET_POINT = Icon.sprite(SpriteCommon.TEXTURE, 1, 5, 16);
            CROWN = Icon.sprite(SpriteCommon.TEXTURE, 0, 4, 16);
            PICTURE = Icon.sprite(SpriteCommon.TEXTURE, 1, 4, 16);
            CART = Icon.sprite(SpriteCommon.TEXTURE, 2, 4, 16);
            DISCONNECT = Icon.sprite(SpriteCommon.TEXTURE, 3, 4, 16);
            FOLDER = Icon.sprite(SpriteCommon.TEXTURE, 4, 4, 16);
            LEAVE_FOLDER = Icon.sprite(SpriteCommon.TEXTURE, 5, 4, 16);
            LOOTBOX = Icon.sprite(SpriteCommon.TEXTURE, 6, 4, 16);
            OPEN_FILE = Icon.sprite(SpriteCommon.TEXTURE, 3, 5, 16);
            LARGE_COPY = Icon.sprite(SpriteCommon.TEXTURE, 4, 5, 16);
            UPLOAD = Icon.sprite(SpriteCommon.TEXTURE, 5, 5, 16);
            PAINT = Icon.sprite(SpriteCommon.TEXTURE, 6, 5, 16);
            SHARE = Icon.sprite(SpriteCommon.TEXTURE, 7, 5, 16);
            STATUS_INDICATOR = Icon.sprite(SpriteCommon.TEXTURE, 0, 10, 8);
            BUG = Icon.sprite(SpriteCommon.TEXTURE, 1, 7, 16);
            NEW = Icon.sprite(SpriteCommon.TEXTURE, 2, 7, 16);
            EXCLAMATION_MARK_DARK = Icon.sprite(SpriteCommon.TEXTURE, 0, 7, 8, 16);
            EXCLAMATION_MARK_LIGHT = Icon.sprite(SpriteCommon.TEXTURE, 1, 7, 8, 16);
            PEPE_SAD = Icon.sprite(SpriteCommon.TEXTURE, 3, 3, 32);
        }
    }
    
    public static class SpriteWidgetEditor
    {
        public static final ThemeTextureLocation TEXTURE;
        public static final Icon TRASH_FRAME_0;
        public static final Icon TRASH_FRAME_1;
        public static final Icon TRASH_FRAME_2;
        public static final Icon MAXIMIZE;
        public static final Icon MINIMIZE;
        public static final Icon GLOBE;
        
        static {
            TEXTURE = ThemeTextureLocation.of("activities/hud/widget_editor", 256, 256);
            TRASH_FRAME_0 = Icon.sprite16(SpriteWidgetEditor.TEXTURE, 0, 0);
            TRASH_FRAME_1 = Icon.sprite16(SpriteWidgetEditor.TEXTURE, 0, 1);
            TRASH_FRAME_2 = Icon.sprite16(SpriteWidgetEditor.TEXTURE, 0, 2);
            MAXIMIZE = Icon.sprite(SpriteWidgetEditor.TEXTURE, 4, 0, 16);
            MINIMIZE = Icon.sprite(SpriteWidgetEditor.TEXTURE, 4, 1, 16);
            GLOBE = Icon.sprite(SpriteWidgetEditor.TEXTURE, 5, 0, 16);
        }
    }
    
    public static class SpriteAccountManager
    {
        public static final ThemeTextureLocation TEXTURE;
        public static Icon MOJANG_LOGO;
        public static Icon MICROSOFT_LOGO;
        public static Icon LABYMOD_LOGO;
        public static Icon JAVA_LOGO;
        public static Icon MICROSOFT_STORE_LOGO;
        
        static {
            TEXTURE = ThemeTextureLocation.of("activities/account_manager");
            SpriteAccountManager.MOJANG_LOGO = Icon.sprite(SpriteAccountManager.TEXTURE, 0, 0, 32);
            SpriteAccountManager.MICROSOFT_LOGO = Icon.sprite(SpriteAccountManager.TEXTURE, 1, 0, 32);
            SpriteAccountManager.LABYMOD_LOGO = Icon.sprite(SpriteAccountManager.TEXTURE, 1, 1, 32);
            SpriteAccountManager.JAVA_LOGO = Icon.sprite(SpriteAccountManager.TEXTURE, 1, 1, 32);
            SpriteAccountManager.MICROSOFT_STORE_LOGO = Icon.sprite(SpriteAccountManager.TEXTURE, 2, 1, 32);
        }
    }
    
    public static class SpriteLabyMod
    {
        public static final ThemeTextureLocation TEXTURE;
        public static final Icon DEFAULT_WOLF_BLURRY;
        public static final Icon WHITE_WOLF_BLURRY;
        public static final Icon DEFAULT_WOLF_SHARP;
        public static final Icon WHITE_WOLF_HIGH_RES;
        public static final Icon DEFAULT_WOLF_HIGH_RES;
        
        static {
            TEXTURE = ThemeTextureLocation.of("labymod", 256, 128);
            DEFAULT_WOLF_BLURRY = Icon.sprite(SpriteLabyMod.TEXTURE, 0, 0, 32);
            WHITE_WOLF_BLURRY = Icon.sprite(SpriteLabyMod.TEXTURE, 1, 0, 32);
            DEFAULT_WOLF_SHARP = Icon.sprite(SpriteLabyMod.TEXTURE, 0, 1, 32);
            WHITE_WOLF_HIGH_RES = Icon.spriteCoordinates(SpriteLabyMod.TEXTURE, 32, 32, 96, 96);
            DEFAULT_WOLF_HIGH_RES = Icon.spriteCoordinates(SpriteLabyMod.TEXTURE, 128, 32, 96, 96);
        }
    }
    
    public static class SpriteFlint
    {
        public static final ThemeTextureLocation TEXTURE;
        public static final Icon SMALL_STAR;
        public static final Icon SMALL_STAR_LEFT;
        public static final Icon SMALL_STAR_RIGHT;
        public static final Icon WARNING;
        public static final Icon DOWNLOADS;
        public static final Icon REFRESH;
        public static final Icon PACKAGES;
        public static final Icon DOCUMENT;
        
        static {
            TEXTURE = ThemeTextureLocation.of("activities/flint/flint");
            SMALL_STAR = Icon.sprite16(SpriteFlint.TEXTURE, 0, 0);
            SMALL_STAR_LEFT = Icon.sprite16(SpriteFlint.TEXTURE, 1, 0);
            SMALL_STAR_RIGHT = Icon.sprite16(SpriteFlint.TEXTURE, 2, 0);
            WARNING = Icon.sprite(SpriteFlint.TEXTURE, 0, 2, 32);
            DOWNLOADS = Icon.sprite(SpriteFlint.TEXTURE, 0, 1, 32);
            REFRESH = Icon.sprite(SpriteFlint.TEXTURE, 1, 1, 32);
            PACKAGES = Icon.sprite(SpriteFlint.TEXTURE, 2, 1, 32);
            DOCUMENT = Icon.sprite(SpriteFlint.TEXTURE, 3, 1, 32);
        }
    }
    
    public static class SpriteShop
    {
        public static final ThemeTextureLocation TEXTURE;
        public static final Icon FEATURED;
        public static final Icon PARTNER;
        public static final Icon AURAS;
        public static final Icon BODY;
        public static final Icon HEAD;
        public static final Icon PETS;
        public static final Icon SHOES;
        public static final Icon UNDERGLOWS;
        public static final Icon WINGS;
        public static final Icon DAILY;
        public static final Icon EPIC;
        public static final Icon LEGENDARY;
        
        static {
            TEXTURE = ThemeTextureLocation.of("activities/shop/shop", 64);
            FEATURED = Icon.sprite(SpriteShop.TEXTURE, 0, 0, 8);
            PARTNER = Icon.sprite(SpriteShop.TEXTURE, 1, 0, 8);
            AURAS = Icon.sprite(SpriteShop.TEXTURE, 2, 0, 8);
            BODY = Icon.sprite(SpriteShop.TEXTURE, 3, 0, 8);
            HEAD = Icon.sprite(SpriteShop.TEXTURE, 4, 0, 8);
            PETS = Icon.sprite(SpriteShop.TEXTURE, 5, 0, 8);
            SHOES = Icon.sprite(SpriteShop.TEXTURE, 6, 0, 8);
            UNDERGLOWS = Icon.sprite(SpriteShop.TEXTURE, 7, 0, 8);
            WINGS = Icon.sprite(SpriteShop.TEXTURE, 0, 1, 8);
            DAILY = Icon.sprite(SpriteShop.TEXTURE, 1, 1, 8);
            EPIC = Icon.sprite(SpriteShop.TEXTURE, 2, 1, 8);
            LEGENDARY = Icon.sprite(SpriteShop.TEXTURE, 3, 1, 8);
        }
    }
    
    public static class SpriteServerSelection
    {
        public static final ThemeTextureLocation TEXTURE;
        
        static {
            TEXTURE = ThemeTextureLocation.of("activities/multiplayer/selection");
        }
    }
    
    public static class SpriteBrands
    {
        public static final ThemeTextureLocation TEXTURE;
        public static final Icon TWITCH;
        public static final Icon TWITTER;
        public static final Icon YOUTUBE;
        public static final Icon REDDIT;
        public static final Icon DISCORD;
        public static final Icon TIKTOK;
        public static final Icon INSTAGRAM;
        public static final Icon GITHUB;
        public static final Icon SPOTIFY;
        public static final Icon XBOX;
        
        public static Icon byName(final String service) {
            final String lowercase = StringUtil.toLowercase(service);
            switch (lowercase) {
                case "twitch": {
                    return SpriteBrands.TWITCH;
                }
                case "twitter": {
                    return SpriteBrands.TWITTER;
                }
                case "youtube": {
                    return SpriteBrands.YOUTUBE;
                }
                case "reddit": {
                    return SpriteBrands.REDDIT;
                }
                case "discord": {
                    return SpriteBrands.DISCORD;
                }
                case "tiktok": {
                    return SpriteBrands.TIKTOK;
                }
                case "instagram": {
                    return SpriteBrands.INSTAGRAM;
                }
                case "github": {
                    return SpriteBrands.GITHUB;
                }
                case "spotify": {
                    return SpriteBrands.SPOTIFY;
                }
                case "xbox": {
                    return SpriteBrands.XBOX;
                }
                default: {
                    return null;
                }
            }
        }
        
        static {
            TEXTURE = ThemeTextureLocation.of("activities/brands");
            TWITCH = Icon.sprite(SpriteBrands.TEXTURE, 0, 0, 32);
            TWITTER = Icon.sprite(SpriteBrands.TEXTURE, 1, 0, 32);
            YOUTUBE = Icon.sprite(SpriteBrands.TEXTURE, 2, 0, 32);
            REDDIT = Icon.sprite(SpriteBrands.TEXTURE, 3, 0, 32);
            DISCORD = Icon.sprite(SpriteBrands.TEXTURE, 0, 1, 32);
            TIKTOK = Icon.sprite(SpriteBrands.TEXTURE, 1, 1, 32);
            INSTAGRAM = Icon.sprite(SpriteBrands.TEXTURE, 2, 1, 32);
            GITHUB = Icon.sprite(SpriteBrands.TEXTURE, 3, 1, 32);
            SPOTIFY = Icon.sprite(SpriteBrands.TEXTURE, 0, 2, 32);
            XBOX = Icon.sprite(SpriteBrands.TEXTURE, 1, 2, 32);
        }
    }
    
    public static class SpriteMarker
    {
        public static final ThemeTextureLocation TEXTURE;
        public static final Icon CIRCLE;
        public static final Icon LINE;
        
        static {
            TEXTURE = ThemeTextureLocation.of("misc/marker", 128, 64);
            CIRCLE = Icon.sprite(SpriteMarker.TEXTURE, 0, 0, 64);
            LINE = Icon.sprite(SpriteMarker.TEXTURE, 1, 0, 64);
        }
    }
    
    public static class SpriteCustomization
    {
        public static final ThemeTextureLocation TEXTURE;
        public static final Icon CLOAK;
        public static final Icon JACKET;
        public static final Icon RIGHT_ARM;
        public static final Icon LEFT_ARM;
        public static final Icon RIGHT_LEG;
        public static final Icon LEFT_LEG;
        public static final Icon HEAD;
        public static final Icon BODY;
        public static final Icon LEGS;
        public static final Icon HAND;
        
        static {
            TEXTURE = ThemeTextureLocation.of("activities/customization", 128);
            CLOAK = Icon.sprite(SpriteCustomization.TEXTURE, 0, 0, 32);
            JACKET = Icon.sprite(SpriteCustomization.TEXTURE, 1, 0, 32);
            RIGHT_ARM = Icon.sprite(SpriteCustomization.TEXTURE, 2, 0, 32);
            LEFT_ARM = Icon.sprite(SpriteCustomization.TEXTURE, 3, 0, 32);
            RIGHT_LEG = Icon.sprite(SpriteCustomization.TEXTURE, 0, 1, 32);
            LEFT_LEG = Icon.sprite(SpriteCustomization.TEXTURE, 1, 1, 32);
            HEAD = Icon.sprite(SpriteCustomization.TEXTURE, 2, 1, 32);
            BODY = Icon.sprite(SpriteCustomization.TEXTURE, 3, 1, 32);
            LEGS = Icon.sprite(SpriteCustomization.TEXTURE, 0, 2, 32);
            HAND = Icon.sprite(SpriteCustomization.TEXTURE, 1, 2, 32);
        }
    }
    
    public static class SpriteHudPlaceholder
    {
        public static final ThemeTextureLocation TEXTURE;
        public static final Icon HELMET;
        public static final Icon CHESTPLATE;
        public static final Icon LEGGINGS;
        public static final Icon BOOTS;
        public static final Icon MAIN_AHND;
        public static final Icon OFF_HAND;
        public static final Icon ARROW;
        
        static {
            TEXTURE = ThemeTextureLocation.of("activities/hud/hud_placeholder", 128);
            HELMET = Icon.sprite(SpriteHudPlaceholder.TEXTURE, 0, 0, 16);
            CHESTPLATE = Icon.sprite(SpriteHudPlaceholder.TEXTURE, 1, 0, 16);
            LEGGINGS = Icon.sprite(SpriteHudPlaceholder.TEXTURE, 2, 0, 16);
            BOOTS = Icon.sprite(SpriteHudPlaceholder.TEXTURE, 3, 0, 16);
            MAIN_AHND = Icon.sprite(SpriteHudPlaceholder.TEXTURE, 4, 0, 16);
            OFF_HAND = Icon.sprite(SpriteHudPlaceholder.TEXTURE, 5, 0, 16);
            ARROW = Icon.sprite(SpriteHudPlaceholder.TEXTURE, 6, 0, 16);
        }
    }
    
    public static class Splash extends GlobalResourceDirectory
    {
        public static final String PATH_SPLASH = "textures/splash/";
        public static final String PATH_CAVE = "textures/splash/cave/";
        public static final ResourceLocation[] MOJANG_STUDIOS_FRAMES;
        public static final ResourceLocation[] MINECRAFT_FRAMES;
        public static final ResourceLocation LABYMOD;
        public static final ResourceLocation CAVE_ENTITIES;
        public static final ResourceLocation MINECRAFT_SPRITE;
        public static final ResourceLocation LAVA_FLOW;
        public static final ResourceLocation BLOCKS;
        public static final ResourceLocation PARTICLES;
        public static final ResourceLocation SNOW;
        
        static {
            MOJANG_STUDIOS_FRAMES = new ResourceLocation[12];
            MINECRAFT_FRAMES = new ResourceLocation[9];
            LABYMOD = GlobalResourceDirectory.globalResource("textures/splash/labymod.png");
            CAVE_ENTITIES = GlobalResourceDirectory.globalResource("textures/splash/cave/entities.png");
            MINECRAFT_SPRITE = GlobalResourceDirectory.globalResource("textures/splash/minecraft_sprite.png");
            LAVA_FLOW = GlobalResourceDirectory.globalResource("textures/splash/cave/lava_flow.png");
            BLOCKS = GlobalResourceDirectory.globalResource("textures/splash/cave/blocks.png");
            PARTICLES = GlobalResourceDirectory.globalResource("textures/splash/cave/particles.png");
            SNOW = GlobalResourceDirectory.globalResource("textures/splash/cave/snow.png");
            for (int i = 0; i < Splash.MOJANG_STUDIOS_FRAMES.length; ++i) {
                Splash.MOJANG_STUDIOS_FRAMES[i] = GlobalResourceDirectory.globalResource("textures/splash/mojangstudios/mojangstudios_" + i + ".png");
            }
            for (int i = 0; i < Splash.MINECRAFT_FRAMES.length; ++i) {
                Splash.MINECRAFT_FRAMES[i] = GlobalResourceDirectory.globalResource("textures/splash/minecraft/minecraft_" + i + ".png");
            }
        }
    }
    
    public static class Title extends ThemedResourceDirectory
    {
        private static final String GUI = "textures/gui/";
        public static final ResourceLocation MINECRAFT_LOGO;
        public static final ResourceLocation LABYMOD_EDITION;
        
        static {
            MINECRAFT_LOGO = ThemedResourceDirectory.resource("textures/gui/", "minecraft_logo.png", 1024, 1024);
            LABYMOD_EDITION = ThemedResourceDirectory.resource("textures/gui/", "labymod_edition.png", 384, 48);
        }
    }
    
    public static class Hud extends ThemedResourceDirectory
    {
        private static final String HUD = "textures/activities/hud/";
        public static final ResourceLocation BACKGROUND;
        public static final ResourceLocation FRAME;
        public static final ResourceLocation STRING;
        
        static {
            BACKGROUND = ThemedResourceDirectory.resource("textures/activities/hud/", "background.png", 1920, 1080);
            FRAME = ThemedResourceDirectory.resource("textures/activities/hud/", "frame.png", 762, 762);
            STRING = ThemedResourceDirectory.resource("textures/activities/hud/", "string.png", 200, 200);
        }
    }
    
    private static class ThemedResourceDirectory
    {
        protected static ResourceLocation resource(final String parent, final String path, final int width, final int height) {
            return resource(parent + path, width, height);
        }
        
        protected static ResourceLocation resource(final String path, final int width, final int height) {
            return ThemeResourceLocation.FACTORY.createThemeTexture(path, width, height);
        }
    }
    
    private static class GlobalResourceDirectory
    {
        protected static ResourceLocation globalResource(final String path) {
            return Laby.labyAPI().renderPipeline().resources().resourceLocationFactory().create("labymod", path);
        }
    }
}
