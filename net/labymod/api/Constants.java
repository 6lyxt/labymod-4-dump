// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api;

import net.labymod.api.util.io.IOUtil;
import java.util.ArrayList;
import java.io.File;
import java.util.Collections;
import java.util.List;
import net.labymod.api.client.resources.ThemeResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ThemeResourceRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Constants
{
    private Constants() {
    }
    
    public static class Files
    {
        public static final Path LABYMOD_DIRECTORY;
        public static final Path ADDONS;
        public static final Path LIBRARIES;
        public static final Path NATIVES;
        public static final Path CONFIGS;
        public static final Path MAPPINGS;
        public static final Path FILE_CACHE;
        public static final Path LABY_NET_SERVER_GROUPS;
        public static final Path ACCOUNTS;
        public static final Path SERVER_FOLDERS;
        public static final Path TOKENS;
        public static final Path ADDONS_SCHEDULE_FOR_REMOVAL;
        public static final Path RESTART;
        public static final Path ADDONS_INDEX;
        public static final Path LABY_NET_INDEX;
        public static final String LABYMOD_DIRECTORY_PATH;
        public static final String UPDATER_PATH;
        public static final String QUICK_LAUNCHER_DIRECTORY;
        public static final String QUICK_LAUNCHER_JAR_PATH;
        public static final String MOJANG_MAPPINGS_PATH;
        public static final String MCP_MAPPINGS_PATH;
        public static final String SEARGE_MAPPINGS_PATH;
        public static final String REMAP_JAR_PATH;
        public static final Path LABYMOD_ASSETS;
        public static final Path CACHE;
        public static final Path SCREENSHOT_META_CACHE;
        public static final Path SCREENSHOT_DIRECTORY;
        
        private Files() {
        }
        
        public static Path fromArguments(final String argument, final Path fallback) {
            final String path = System.getProperty(argument);
            if (path == null) {
                return fallback;
            }
            return Paths.get(path, new String[0]);
        }
        
        static {
            LABYMOD_DIRECTORY = Paths.get("labymod-neo", new String[0]);
            ADDONS = fromArguments("net.labymod.addons-dir", Files.LABYMOD_DIRECTORY.resolve("addons"));
            LIBRARIES = Files.LABYMOD_DIRECTORY.resolve("libraries");
            NATIVES = Files.LABYMOD_DIRECTORY.resolve("natives");
            CONFIGS = Files.LABYMOD_DIRECTORY.resolve("configs");
            MAPPINGS = Files.LABYMOD_DIRECTORY.resolve("mappings");
            FILE_CACHE = Files.LABYMOD_DIRECTORY.resolve("cache");
            LABY_NET_SERVER_GROUPS = Files.FILE_CACHE.resolve("server-groups");
            ACCOUNTS = Files.LABYMOD_DIRECTORY.resolve("accounts.json");
            SERVER_FOLDERS = Files.LABYMOD_DIRECTORY.resolve("server-folders.json");
            TOKENS = Files.LABYMOD_DIRECTORY.resolve("tokens.json");
            ADDONS_SCHEDULE_FOR_REMOVAL = Files.LABYMOD_DIRECTORY.resolve(".asfr");
            RESTART = Files.LABYMOD_DIRECTORY.resolve(".restart");
            ADDONS_INDEX = Files.FILE_CACHE.resolve("index.json");
            LABY_NET_INDEX = Files.FILE_CACHE.resolve("server-groups.json");
            LABYMOD_DIRECTORY_PATH = Files.LABYMOD_DIRECTORY.toString();
            UPDATER_PATH = Files.LABYMOD_DIRECTORY_PATH + "/updater-%s.jar";
            QUICK_LAUNCHER_DIRECTORY = Files.LABYMOD_DIRECTORY_PATH + "/quick-launcher/";
            QUICK_LAUNCHER_JAR_PATH = Files.QUICK_LAUNCHER_DIRECTORY + "quick-launcher.jar";
            MOJANG_MAPPINGS_PATH = String.valueOf(Files.MAPPINGS) + "/%s-mojang.proguard";
            MCP_MAPPINGS_PATH = String.valueOf(Files.MAPPINGS) + "/%s-mcp.tsrg2";
            SEARGE_MAPPINGS_PATH = String.valueOf(Files.MAPPINGS) + "/%s-searge.tsrg2";
            REMAP_JAR_PATH = String.valueOf(Files.MAPPINGS) + "/%s-%s-remapped.jar";
            LABYMOD_ASSETS = Paths.get("assets", new String[0]).resolve("labymod");
            CACHE = Files.LABYMOD_ASSETS.resolve("textures");
            SCREENSHOT_META_CACHE = Files.LABYMOD_ASSETS.resolve("screenshot_meta.bin");
            SCREENSHOT_DIRECTORY = Paths.get("screenshots", new String[0]);
        }
    }
    
    @Deprecated
    public static class LegacyUrls
    {
        public static final String BASE = "https://dl.labymod.net/";
        public static final String WHITELIST;
        public static final String USER_DATA;
        public static final String EMOTE_DATA;
        public static final String COSMETICS_DATA;
        public static final String GROUPS;
        public static final String STICKERS;
        public static final String CUSTOM_TEXTURES;
        public static final String REMOTE_COSMETICS_TEXTURE;
        public static final String DEFAULT_REMOTE_COSMETICS_TEXTURE;
        public static final String REMOTE_COSMETICS_GEOMETRY;
        public static final String REMOTE_COSMETICS_ANIMATION;
        public static final String SERVER_GROUPS;
        public static final String PUBLIC_SERVERS;
        public static final String DAILY_EMOTES;
        public static final String SPLASH_DATES;
        public static final String QUICK_LAUNCHER;
        
        private LegacyUrls() {
        }
        
        private static String protocol(final String path) {
            return "https://dl.labymod.net/" + path;
        }
        
        static {
            WHITELIST = protocol("whitelist.bin");
            USER_DATA = protocol("userdata/%s.json");
            EMOTE_DATA = protocol("emotes/emotedata");
            COSMETICS_DATA = protocol("cosmetics/index.json");
            GROUPS = protocol("groups.json");
            STICKERS = protocol("stickers.json");
            CUSTOM_TEXTURES = protocol("textures/%s/%s");
            REMOTE_COSMETICS_TEXTURE = protocol("cosmetics/%s/textures/%s.png");
            DEFAULT_REMOTE_COSMETICS_TEXTURE = protocol("cosmetics/%s/texture.png");
            REMOTE_COSMETICS_GEOMETRY = protocol("cosmetics/%s/geo.json");
            REMOTE_COSMETICS_ANIMATION = protocol("cosmetics/%s/animation.json");
            SERVER_GROUPS = protocol("server_groups.json");
            PUBLIC_SERVERS = protocol("public_servers.json");
            DAILY_EMOTES = protocol("advertisement/entries.json");
            SPLASH_DATES = protocol("advertisement/entries.json");
            QUICK_LAUNCHER = protocol("latest/install/quicklauncher/quicklauncher.jar");
        }
    }
    
    public static class Urls
    {
        public static final String WEB_BASE = "https://www.labymod.net/";
        public static final String WEB_SHOP = "https://www.labymod.net/shop";
        public static final String WEB_USER_ITEMS = "https://www.labymod.net/api/user-items";
        public static final String WEB_USER_ITEM_CHANGE = "https://www.labymod.net/api/change";
        public static final String WEB_USER_ITEM_CHANGE_BULK = "https://www.labymod.net/api/change/cosmetics";
        public static final String WEB_USER_COSMETIC_OPTIONS = "https://www.labymod.net/api/user-cosmetic-options";
        public static final String WEB_SHOP_PRODUCTS_IMAGE = "https://www.labymod.net/page/tpl/assets/images/shop/products/%s_0.png";
        public static final String NEO_BASE = "https://neo.labymod.net/";
        public static final String RELEASE_BASE = "https://releases.labymod.net/api/v1/";
        public static final String API_BASE = "https://next.api.labymod.net/client/";
        public static final String LABYNET_BASE = "https://laby.net/";
        public static final String SKIN_LABYNET_BASE = "https://skin.laby.net/";
        public static final String TEXTURE_LABYNET_BASE = "https://texture.laby.net/";
        public static final String ISSUE_CERTIFICATE = "https://issue.labymod.net/certificate.php";
        public static final String LABYNET_PROFILE_NAME = "https://laby.net/@%s";
        public static final String LABYNET_SCREENSHOT_UPLOAD = "https://laby.net/api/v3/post";
        public static final String LABYNET_SKIN_PAGE = "https://laby.net/skin/%s";
        public static final String LABYNET_TEXTURE_SEARCH = "https://laby.net/api/texture/search?type=%s&order=%s";
        public static final String LABYNET_SURVEY = "https://laby.net/api/v2/survey/%s";
        public static final String LABYNET_SURVEY_SOURCE_CLIENT = "https://laby.net/api/v2/survey?source=CLIENT";
        public static final String LABYNET_USER_GET_TEXTURES = "https://laby.net/api/user/%s/get-textures";
        public static final String LABYNET_USER_SOCIALS = "https://laby.net/api/v2/user/%s/socials";
        public static final String LABYNET_SERVICE_WIDGET = "https://laby.net/api/v2/service/%s/widget";
        public static final String LABYNET_USER_PROFILE = "https://laby.net/api/v3/user/%s/profile";
        public static final String LABYNET_USER_GET_NAMES = "https://laby.net/api/v3/user/%s/names";
        public static final String LABYNET_HEAD = "https://laby.net/texture/profile/head/%s.png?size=32";
        public static final String LABYNET_SKIN = "https://laby.net/api/v3/user/%s/skin.png?strategy=skip";
        public static final String LABYNET_TEXTURE_DOWNLOAD = "https://texture.laby.net/%s.png";
        public static final String LABYNET_SKIN_PREVIEW = "https://skin.laby.net/api/render/texture/%s.png?shadow=true&width=400&height=1024";
        public static final String LABYMOD_MANIFEST;
        public static final String LIBRARY_MANIFEST;
        public static final String UPDATER_MANIFEST;
        public static final String UPDATER_DOWNLOAD;
        public static final String ASSET_DOWNLOAD;
        public static final String ANNOUNCEMENTS;
        public static final String ANNOUNCEMENTS_ICONS;
        public static final String INCENTIVES_DATA;
        public static final String REMOTE_INCENTIVES_GEOMETRY;
        public static final String REMOTE_INCENTIVES_ANIMATION;
        public static final String REMOTE_INCENTIVES_TEXTURE;
        public static final String EMOTES_DIRECTORY;
        public static final String EMOTE_DATA;
        public static final String EMOTE_ANIMATION;
        public static final String EMOTE_GEOMETRY;
        public static final String EMOTE_TEXTURE;
        public static final String EMOTE_PLAYER_MODEL_STEVE;
        public static final String EMOTE_PLAYER_MODEL_ALEX;
        public static final String DISCORD_NATIVES = "https://dl-game-sdk.discordapp.net/3.2.1/discord_game_sdk.zip";
        
        private Urls() {
        }
        
        @Contract(pure = true)
        @NotNull
        private static String neoProtocol(final String path) {
            return "https://neo.labymod.net/" + path;
        }
        
        @Contract(pure = true)
        @NotNull
        private static String releasesProtocol(final String path) {
            return "https://releases.labymod.net/api/v1/" + path;
        }
        
        @Contract(pure = true)
        @NotNull
        private static String apiProtocol(final String path) {
            return "https://next.api.labymod.net/client/" + path;
        }
        
        @Contract(pure = true)
        @NotNull
        private static String protocol(final String parent, final String path) {
            return parent + "/" + path;
        }
        
        static {
            LABYMOD_MANIFEST = releasesProtocol("manifest/%s/latest");
            LIBRARY_MANIFEST = releasesProtocol("libraries/%s");
            UPDATER_MANIFEST = releasesProtocol("updater/%s");
            UPDATER_DOWNLOAD = releasesProtocol("download/updater/%s/%s");
            ASSET_DOWNLOAD = releasesProtocol("download/assets/labymod4/%s/%s/%s/%s");
            ANNOUNCEMENTS = apiProtocol("announcements");
            ANNOUNCEMENTS_ICONS = protocol(neoProtocol("announcements"), "icons");
            INCENTIVES_DATA = neoProtocol("incentives/index.json");
            REMOTE_INCENTIVES_GEOMETRY = neoProtocol("incentives/%s/geo.json");
            REMOTE_INCENTIVES_ANIMATION = neoProtocol("incentives/%s/animation.json");
            REMOTE_INCENTIVES_TEXTURE = neoProtocol("incentives/%s/texture.png");
            EMOTES_DIRECTORY = neoProtocol("emotes");
            EMOTE_DATA = protocol(Urls.EMOTES_DIRECTORY, "index.json");
            EMOTE_ANIMATION = protocol(Urls.EMOTES_DIRECTORY, "%s/animation.json");
            EMOTE_GEOMETRY = protocol(Urls.EMOTES_DIRECTORY, "%s/geo.json");
            EMOTE_TEXTURE = protocol(Urls.EMOTES_DIRECTORY, "%s/texture.png");
            EMOTE_PLAYER_MODEL_STEVE = protocol(Urls.EMOTES_DIRECTORY, "%s/model_steve.json");
            EMOTE_PLAYER_MODEL_ALEX = protocol(Urls.EMOTES_DIRECTORY, "%s/model_alex.json");
        }
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static class NamedThemeResource extends ThemeResourceRegistry
    {
        public static final ResourceLocation ACCOUNT_MANAGER;
        public static final ResourceLocation COMMON;
        public static final ResourceLocation BRANDS;
        public static final ResourceLocation LABYMOD;
        public static final ResourceLocation CUSTOMIZATION;
        public static final ResourceLocation FLINT;
        public static final ResourceLocation WHITE;
        public static final ResourceLocation NOTIFICATION;
        public static final ResourceLocation DEFAULT_SERVER_ICON;
        public static final ResourceLocation FLINT_DEFAULT_ICON;
        public static final ResourceLocation SURVIVAL_INVENTORY_BACKGROUND;
        public static final ResourceLocation SPRITE_VANILLA_WINDOW;
        public static final ResourceLocation CREATIVE_INVENTORY_TAB_INVENTORY;
        public static final ResourceLocation CREATIVE_INVENTORY_TAB_ITEM_SEARCH;
        public static final ResourceLocation CREATIVE_INVENTORY_TAB_ITEMS;
        public static final ResourceLocation CREATIVE_INVENTORY_TABS;
        public static final ResourceLocation WIDGET_EDITOR;
        public static final ResourceLocation HUD_PLACEHOLDER;
        public static final ResourceLocation LABYMOD_LOGO;
        public static final ResourceLocation TRANSPARENT;
        public static final ResourceLocation STAR;
        
        private static ResourceLocation texture(final String path) {
            return ThemeResourceLocation.FACTORY.createThemeTexture(path, 128, 128);
        }
        
        static {
            ACCOUNT_MANAGER = Textures.SpriteAccountManager.TEXTURE;
            COMMON = Textures.SpriteCommon.TEXTURE;
            BRANDS = texture("textures/activities/brands.png");
            LABYMOD = Textures.SpriteLabyMod.TEXTURE;
            CUSTOMIZATION = Textures.SpriteCustomization.TEXTURE;
            FLINT = Textures.SpriteFlint.TEXTURE;
            WHITE = Textures.WHITE;
            NOTIFICATION = Textures.NOTIFICATION;
            DEFAULT_SERVER_ICON = Textures.DEFAULT_SERVER_ICON;
            FLINT_DEFAULT_ICON = texture("textures/activities/flint/default_icon.png");
            SURVIVAL_INVENTORY_BACKGROUND = Textures.SURVIVAL_INVENTORY_BACKGROUND;
            SPRITE_VANILLA_WINDOW = Textures.POPUP_BACKGROUND;
            CREATIVE_INVENTORY_TAB_INVENTORY = Textures.CREATIVE_INVENTORY_TAB_INVENTORY;
            CREATIVE_INVENTORY_TAB_ITEM_SEARCH = Textures.CREATIVE_INVENTORY_TAB_ITEM_SEARCH;
            CREATIVE_INVENTORY_TAB_ITEMS = Textures.CREATIVE_INVENTORY_TAB_ITEMS;
            CREATIVE_INVENTORY_TABS = Textures.CREATIVE_INVENTORY_TABS;
            WIDGET_EDITOR = Textures.SpriteWidgetEditor.TEXTURE;
            HUD_PLACEHOLDER = Textures.SpriteHudPlaceholder.TEXTURE;
            LABYMOD_LOGO = Textures.LABYMOD_LOGO;
            TRANSPARENT = Textures.TRANSPARENT;
            STAR = Textures.STAR;
        }
    }
    
    public static class Identifiers
    {
        public static final ResourceLocation LABYMOD_NEO_IDENTIFIER;
        @Deprecated
        public static final ResourceLocation LABYMOD3_MAIN_IDENTIFIER;
        @Deprecated
        public static final ResourceLocation LABYMOD3_CCP_IDENTIFIER;
        @Deprecated
        public static final ResourceLocation LABYMOD3_SHADOW_IDENTIFIER;
        
        private Identifiers() {
        }
        
        static {
            LABYMOD_NEO_IDENTIFIER = ResourceLocation.create("labymod", "neo");
            LABYMOD3_MAIN_IDENTIFIER = ResourceLocation.create("labymod3", "main");
            LABYMOD3_CCP_IDENTIFIER = ResourceLocation.create("labymod3", "ccp");
            LABYMOD3_SHADOW_IDENTIFIER = ResourceLocation.create("labymod3", "shadow");
        }
    }
    
    public static class Resources
    {
        public static final ResourceLocation SOUND_CHAT_MESSAGE;
        public static final ResourceLocation SOUND_PLACE_MARKER;
        public static final ResourceLocation SOUND_MARKER_NOTIFY;
        public static final ResourceLocation SOUND_HUDWIDGET_ALIGN;
        public static final ResourceLocation SOUND_HUDWIDGET_ATTACH;
        public static final ResourceLocation SOUND_HUDWIDGET_TRASH;
        public static final ResourceLocation SOUND_UI_SWITCH_ON;
        public static final ResourceLocation SOUND_UI_SWITCH_OFF;
        public static final ResourceLocation SOUND_UI_BUTTON_CLICK;
        public static final ResourceLocation SOUND_UI_SERVER_MOVE;
        public static final ResourceLocation SOUND_SPRAY_CAN_SHAKE;
        public static final ResourceLocation SOUND_SPRAY_CAN_SPRAY;
        public static final ResourceLocation SOUND_SCREENSHOT_CAPTURE;
        public static final ResourceLocation SOUND_LOOTBOX_OPEN;
        public static final ResourceLocation SOUND_LOOTBOX_COMMON;
        public static final ResourceLocation SOUND_LOOTBOX_SPECIAL;
        public static final ResourceLocation SOUND_LOOTBOX_LEGENDARY;
        public static final ResourceLocation MODEL_STEVE;
        public static final ResourceLocation MODEL_ALEX;
        public static final ResourceLocation MODEL_HEAD;
        public static final ResourceLocation CLOAK_AND_ELYTRA;
        public static final ResourceLocation CAVE_ENTITIES;
        public static final ResourceLocation MINECRAFT_LOGO;
        public static final ResourceLocation LOOTBOX;
        public static final ResourceLocation SYMBOLS;
        public static final ResourceLocation CAVE;
        public static final ResourceLocation CAVE_ENTITIES_ANIMATION;
        public static final ResourceLocation MINECRAFT_LOGO_ANIMATION;
        public static final ResourceLocation LOOTBOX_ANIMATION;
        
        private Resources() {
        }
        
        private static ResourceLocation labyMod3Identifier(final String path) {
            return resource("labymod3", path);
        }
        
        static ResourceLocation resource(final String path) {
            return resource("labymod", path);
        }
        
        private static ResourceLocation minecraftTexture(final String path) {
            return resource("minecraft", path);
        }
        
        private static ResourceLocation resource(final String namespace, final String path) {
            return ResourceLocation.create(namespace, path);
        }
        
        static {
            SOUND_CHAT_MESSAGE = resource("misc.pop");
            SOUND_PLACE_MARKER = resource("marker.place_marker");
            SOUND_MARKER_NOTIFY = resource("marker.marker_notify");
            SOUND_HUDWIDGET_ALIGN = resource("hudwidget.align");
            SOUND_HUDWIDGET_ATTACH = resource("hudwidget.attach");
            SOUND_HUDWIDGET_TRASH = resource("hudwidget.trash");
            SOUND_UI_SWITCH_ON = resource("ui.switch.on");
            SOUND_UI_SWITCH_OFF = resource("ui.switch.off");
            SOUND_UI_BUTTON_CLICK = resource("ui.button.click");
            SOUND_UI_SERVER_MOVE = resource("ui.server.move");
            SOUND_SPRAY_CAN_SHAKE = resource("spray_can.shake");
            SOUND_SPRAY_CAN_SPRAY = resource("spray_can.spray");
            SOUND_SCREENSHOT_CAPTURE = resource("screenshot.capture");
            SOUND_LOOTBOX_OPEN = resource("lootbox.open");
            SOUND_LOOTBOX_COMMON = resource("lootbox.common");
            SOUND_LOOTBOX_SPECIAL = resource("lootbox.special");
            SOUND_LOOTBOX_LEGENDARY = resource("lootbox.legendary");
            MODEL_STEVE = resource("models/steve.geo.json");
            MODEL_ALEX = resource("models/alex.geo.json");
            MODEL_HEAD = resource("models/head.geo.json");
            CLOAK_AND_ELYTRA = resource("models/cloak_and_elytra.geo.json");
            CAVE_ENTITIES = resource("models/cave_entities.geo.json");
            MINECRAFT_LOGO = resource("models/minecraft_logo.geo.json");
            LOOTBOX = resource("models/lootbox.geo.json");
            SYMBOLS = resource("data/symbols.txt");
            CAVE = resource("data/cave.schem");
            CAVE_ENTITIES_ANIMATION = resource("animations/cave_entities.animation.json");
            MINECRAFT_LOGO_ANIMATION = resource("animations/minecraft_logo.animation.json");
            LOOTBOX_ANIMATION = resource("animations/lootbox.animation.json");
        }
    }
    
    public static class SystemProperties
    {
        private static final String DEBUGGING = "net.labymod.debugging.";
        public static final String ALL;
        public static final String RENDERDOC;
        public static final String ASM;
        public static final String DISABLE_WIDGET_MODIFIER_CACHE;
        public static final String UI;
        public static final String OPENGL;
        public static final String OPENGL_CALL;
        public static final String KEYMAPPING;
        public static final String RESOURCE_TRANSFORM;
        public static final String BUNDLED_OPTIFINE;
        public static final String HOT_FILE_RELOADING_DIRECTORIES;
        public static final String DUMP_SPRITES;
        public static final String MIXIN_AUDIT;
        public static final String DEBUG_LOGGER = "net.labymod.debug-logger";
        
        private SystemProperties() {
        }
        
        private static String debugProperty(final String path) {
            return "net.labymod.debugging." + path;
        }
        
        public static List<Path> getFiles(final String key) {
            final String property = System.getProperty(key);
            if (property == null || property.isEmpty()) {
                return Collections.emptyList();
            }
            final String[] entries = property.split(File.pathSeparator);
            final List<Path> files = new ArrayList<Path>(entries.length);
            for (final String entry : entries) {
                final Path file = Path.of(entry, new String[0]);
                if (IOUtil.exists(file)) {
                    files.add(file);
                }
            }
            return files;
        }
        
        public static boolean getDebugging(final String name) {
            return getBoolean(SystemProperties.ALL) || getBoolean(name);
        }
        
        public static boolean getBoolean(final String name) {
            return Boolean.getBoolean(name);
        }
        
        public static boolean getKeymapping() {
            return getBoolean(SystemProperties.KEYMAPPING);
        }
        
        public static boolean isMixinAudit() {
            return getBoolean(SystemProperties.MIXIN_AUDIT);
        }
        
        static {
            ALL = debugProperty("all");
            RENDERDOC = debugProperty("renderdoc");
            ASM = debugProperty("asm");
            DISABLE_WIDGET_MODIFIER_CACHE = debugProperty("disable-widget-modifier-cache");
            UI = debugProperty("ui");
            OPENGL = debugProperty("opengl");
            OPENGL_CALL = debugProperty("opengl-call");
            KEYMAPPING = debugProperty("keymapping");
            RESOURCE_TRANSFORM = debugProperty("resource-transform");
            BUNDLED_OPTIFINE = debugProperty("bundled-optifine");
            HOT_FILE_RELOADING_DIRECTORIES = debugProperty("hot-file-reloading-directories");
            DUMP_SPRITES = debugProperty("dump-sprites");
            MIXIN_AUDIT = debugProperty("mixin-audit");
        }
    }
}
