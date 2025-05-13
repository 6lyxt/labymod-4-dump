// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.generated;

import net.labymod.core.client.gui.background.panorama.PanoramaRenderer;
import net.labymod.core.client.font.text.FontSetGlyphProvider;
import net.labymod.core.client.session.UserFactory;
import net.labymod.core.client.multiplayer.ClientNetworkPacketListener;
import net.labymod.core.client.chat.InternalChatModifier;
import net.labymod.core.client.resources.texture.concurrent.RefreshableTextureFactory;
import net.labymod.core.client.util.buffersource.BufferSourceGui;
import net.labymod.api.nbt.NBTFactory;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.StandardBlaze3DRenderTypes;
import net.labymod.api.client.gfx.pipeline.texture.atlas.MinecraftAtlases;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.client.gfx.pipeline.Blaze3DShaderUniformPipeline;
import net.labymod.api.client.gfx.texture.LightmapTexture;
import net.labymod.api.client.gfx.imgui.ImGuiAccessor;
import net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.render.gl.GlStateBridge;
import net.labymod.api.client.render.gl.GlInformation;
import net.labymod.api.client.render.PlayerHeartRenderer;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.render.font.text.StringFormatter;
import net.labymod.api.client.render.matrix.StackProviderFactory;
import net.labymod.api.client.render.vertex.shard.shards.layer.ViewOffsetZLayer;
import net.labymod.api.client.render.vertex.shard.shards.OutputRenderShard;
import net.labymod.api.client.render.RenderConstants;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.crash.GameCrashReport;
import net.labymod.api.client.particle.ParticleController;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.api.client.session.MinecraftAuthenticator;
import net.labymod.api.client.entity.EntityPoseMapper;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.lighting.LightingLayerMapper;
import net.labymod.api.client.world.BossBarRegistry;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.phys.hit.HitResultController;
import net.labymod.api.client.world.block.Blocks;
import net.labymod.api.client.world.block.BlockColorProvider;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.api.client.resources.pack.ResourcePackScanner;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.resources.texture.GameImageTexture;
import net.labymod.api.client.resources.sound.MinecraftSounds;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.client.network.server.ServerAddressResolver;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.client.util.SystemInfo;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.format.numbers.NumberFormatMapper;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.server.IntegratedServer;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.util.FileDialogs;
import net.labymod.api.util.math.GameMathMapper;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import org.jetbrains.annotations.NotNull;
import net.labymod.core.labymodnet.DefaultLabyModNetService;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.account.AccountManagerController;
import net.labymod.core.thirdparty.DefaultSentryService;
import net.labymod.core.thirdparty.discord.DefaultDiscordApp;
import net.labymod.core.thirdparty.DefaultThirdPartyService;
import net.labymod.core.thirdparty.optifine.DefaultOptiFine;
import net.labymod.core.client.gfx.buffer.DefaultVertexArrayObject;
import net.labymod.core.client.gfx.buffer.DefaultBufferObject;
import net.labymod.core.client.gfx.DevelopmentGFXBridge;
import net.labymod.core.client.gfx.pipeline.DefaultGFXRenderPipeline;
import net.labymod.core.client.gfx.pipeline.texture.atlas.DefaultAtlasRegistry;
import net.labymod.core.client.gfx.pipeline.shader.DefaultShaderPipeline;
import net.labymod.core.client.gfx.pipeline.context.DefaultFrameContextRegistry;
import net.labymod.core.client.gfx.pipeline.DefaultRenderEnvironmentContext;
import net.labymod.core.client.gfx.shader.DefaultShaderProgram;
import net.labymod.core.client.gfx.shader.DefaultShaderProgramManager;
import net.labymod.core.client.gfx.vertex.DefaultVertexFormat;
import net.labymod.core.client.gfx.vertex.DefaultVertexFormatRegistry;
import net.labymod.core.client.gfx.imgui.control.DefaultControlEntryRegistry;
import net.labymod.core.client.gui.screen.DefaultScreenService;
import net.labymod.core.client.gui.screen.widget.window.DefaultScreenWindowManagement;
import net.labymod.core.client.gui.screen.widget.DefaultOverlappingTranslator;
import net.labymod.core.client.gui.screen.overlay.DefaultScreenOverlayHandler;
import net.labymod.core.client.gui.screen.theme.DefaultThemeFileFinder;
import net.labymod.core.client.gui.screen.theme.DefaultThemeService;
import net.labymod.core.client.gui.screen.theme.DefaultThemeRendererParser;
import net.labymod.core.client.gui.screen.DefaultScreenCustomFontStack;
import net.labymod.core.client.gui.screen.game.DefaultGameScreenRegistry;
import net.labymod.core.client.gui.screen.key.DefaultHotkeyService;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.ChatOverlay;
import net.labymod.core.client.gui.screen.activity.DefaultIngameActivityOverlay;
import net.labymod.core.client.gui.screen.activity.DefaultOverlayRegistry;
import net.labymod.core.client.gui.embed.DefaultEmbedFactory;
import net.labymod.core.client.gui.DefaultTooltipService;
import net.labymod.core.client.gui.navigation.DefaultNavigationRegistry;
import net.labymod.core.client.gui.hud.DefaultHudWidgetRegistry;
import net.labymod.core.client.gui.hud.DefaultHudWidgetCategoryRegistry;
import net.labymod.core.client.gui.icon.ping.DefaultPingIconRegistry;
import net.labymod.core.client.gui.lss.style.DefaultStyleHelper;
import net.labymod.core.client.gui.lss.style.function.DefaultFunctionRegistry;
import net.labymod.core.client.gui.lss.style.function.parser.DefaultElementParser;
import net.labymod.core.client.gui.lss.style.modifier.processors.DefaultPostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.state.DefaultPseudoClassRegistry;
import net.labymod.core.client.gui.lss.style.modifier.DefaultWidgetModifier;
import net.labymod.core.client.gui.lss.style.modifier.DefaultTypeParser;
import net.labymod.core.client.gui.lss.injector.DefaultLssInjector;
import net.labymod.core.client.gui.lss.DefaultStyleSheetLoader;
import net.labymod.core.client.gui.lss.DefaultLinkMetaLoader;
import net.labymod.core.client.render.font.component.DefaultComponentRenderer;
import net.labymod.core.client.render.font.component.DefaultTextColorStripper;
import net.labymod.core.client.render.font.component.DefaultComponentRendererBuilder;
import net.labymod.core.client.render.font.text.msdf.MSDFGlyphProvider;
import net.labymod.core.client.render.font.text.msdf.MSDFTextRenderer;
import net.labymod.core.client.render.font.text.DefaultTextRendererProvider;
import net.labymod.core.client.render.draw.DefaultBlurRenderer;
import net.labymod.core.client.render.draw.DefaultTriangleRenderer;
import net.labymod.core.client.render.draw.batch.DefaultBatchRectangleRenderer;
import net.labymod.core.client.render.draw.batch.DefaultBatchResourceRenderer;
import net.labymod.core.client.render.draw.builder.DefaultRoundedGeometryBuilder;
import net.labymod.core.client.render.draw.builder.DefaultVanillaWindowBuilder;
import net.labymod.core.client.render.draw.DefaultCircleRenderer;
import net.labymod.core.client.render.draw.hover.DefaultHoverBackgroundEffectRenderer;
import net.labymod.core.client.render.draw.hover.VanillaHoverBackgroundEffect;
import net.labymod.core.client.render.draw.hover.FancyHoverBackgroundEffect;
import net.labymod.core.client.render.draw.DefaultRectangleRenderer;
import net.labymod.core.client.render.draw.DefaultResourceRenderer;
import net.labymod.core.client.render.draw.DefaultPlayerHeadRenderer;
import net.labymod.core.client.render.draw.DefaultHeartRenderer;
import net.labymod.core.client.render.batch.DefaultTriangleRenderContext;
import net.labymod.core.client.render.batch.DefaultRenderContexts;
import net.labymod.core.client.render.batch.DefaultResourceRenderContext;
import net.labymod.core.client.render.batch.DefaultRectangleRenderContext;
import net.labymod.core.client.render.batch.DefaultLineRenderContext;
import net.labymod.core.client.render.shader.DefaultShaderProvider;
import net.labymod.core.client.render.draw.shader.CircleShaderInstance;
import net.labymod.core.client.render.draw.shader.FrameBufferObjectShaderInstance;
import net.labymod.core.client.render.draw.shader.FrameBufferObjectStencilShaderInstance;
import net.labymod.core.client.render.shader.program.RenderPhase3DEnvironmentTextureShaderInstance;
import net.labymod.core.client.render.shader.DefaultMojangShaderRegistry;
import net.labymod.core.client.render.matrix.DefaultEmptyStack;
import net.labymod.core.client.render.vertex.DefaultOldVertexFormatRegistry;
import net.labymod.core.client.render.DefaultHotbarRenderer;
import net.labymod.core.client.render.DefaultExperienceBarRenderer;
import net.labymod.core.client.render.DefaultStatusIconRenderer;
import net.labymod.core.client.render.model.DefaultModelUploader;
import net.labymod.core.client.render.model.DefaultModelRenderer;
import net.labymod.core.client.render.model.DefaultModelService;
import net.labymod.core.client.crash.DefaultCrashReportAppenderIterable;
import net.labymod.core.client.DefaultGameTickProvider;
import net.labymod.core.client.session.DefaultMinecraftServices;
import net.labymod.core.client.entity.player.tag.DefaultTagRegistry;
import net.labymod.core.client.entity.player.badge.DefaultBadgeRegistry;
import net.labymod.core.client.entity.player.interaction.DefaultInteractionMenuRegistry;
import net.labymod.core.client.zoom.DefaultZoomController;
import net.labymod.core.client.world.signobject.DefaultSignObjectRegistry;
import net.labymod.core.client.world.object.DefaultWorldObjectRegistry;
import net.labymod.core.client.world.canvas.DefaultCanvasFactory;
import net.labymod.core.client.world.canvas.DefaultCanvasRegistry;
import net.labymod.core.client.chat.autotext.DefaultAutoTextService;
import net.labymod.core.client.chat.DefaultChatSymbolRegistry;
import net.labymod.core.client.chat.DefaultChatProvider;
import net.labymod.core.client.chat.gui.DefaultChatInputRegistry;
import net.labymod.core.client.chat.filter.DefaultFilterChatService;
import net.labymod.core.client.chat.filter.DefaultDynamicChatFilterService;
import net.labymod.core.client.chat.advanced.DefaultAdvancedChatController;
import net.labymod.core.client.chat.DefaultChatMessage;
import net.labymod.core.client.chat.DefaultChatController;
import net.labymod.core.client.chat.command.DefaultCommandService;
import net.labymod.core.client.resources.AnimatedResourceLocationBuilder;
import net.labymod.core.client.resources.transform.transformer.customhitcolor.DamageOverlayRenderTypeArmorCutoutNoCullJsonResourceTransformer;
import net.labymod.core.client.resources.transform.transformer.customhitcolor.DamageOverlayRenderTypeArmorCutoutNoCullFragmentShaderResourceTransformer;
import net.labymod.core.client.resources.transform.transformer.customhitcolor.DamageOverlayRenderTypeArmorCutoutNoCullVertexShaderResourceTransformer;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;
import net.labymod.core.client.resources.pack.DefaultResourcePackFactory;
import net.labymod.core.client.resources.DefaultResourcesReloadWatcher;
import net.labymod.core.client.resources.texture.concurrent.DefaultAsynchronousTextureUploader;
import net.labymod.core.client.resources.DefaultResources;
import net.labymod.core.client.network.server.global.DefaultPublicServerListService;
import net.labymod.core.client.network.server.DefaultServerPinger;
import net.labymod.core.client.network.server.payload.DefaultPayloadRegistry;
import net.labymod.core.client.network.server.lan.DefaultLanServerDetector;
import net.labymod.core.client.sound.DefaultSoundService;
import net.labymod.core.platform.launcher.DefaultLauncherService;
import net.labymod.core.platform.launcher.DefaultMinecraftLauncherFactory;
import net.labymod.core.main.serverapi.DefaultLabyModProtocolService;
import net.labymod.core.main.serverapi.legacy.LabyModProtocolApi;
import net.labymod.core.addon.DefaultAddonIntegrationService;
import net.labymod.core.labynet.DefaultLabyNetController;
import net.labymod.core.localization.DefaultInternationalization;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.core.main.user.group.DefaultGroupService;
import net.labymod.core.main.user.permission.DefaultPermissionRegistry;
import net.labymod.core.revision.DefaultRevisionRegistry;
import net.labymod.core.configuration.labymod.chat.DefaultGeneralChatGlobalSettingHandler;
import net.labymod.core.configuration.settings.widget.DefaultWidgetRegistry;
import net.labymod.core.configuration.settings.DefaultSwitchableHandlerRegistry;
import net.labymod.core.configuration.converter.DefaultLegacyConfigConverter;
import net.labymod.core.client.mojang.model.DefaultMojangModelService;
import net.labymod.api.reference.DynamicReference;
import net.labymod.core.util.markdown.DefaultMarkdownParser;
import net.labymod.core.util.logging.DefaultLoggingFactory;
import net.labymod.core.util.function.DefaultFunctionMemoizeStorage;
import net.labymod.core.util.jsonfilecache.DefaultJsonFileCacheFactory;
import net.labymod.core.util.io.web.connection.DefaultWebResolver;
import net.labymod.core.mapping.DefaultMixinRemapperInjector;
import net.labymod.core.mapping.FartMappingService;
import net.labymod.core.uri.DefaultAttachmentParser;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import net.labymod.core.labyconnect.session.DefaultTokenStorage;
import net.labymod.core.main.announcement.DefaultAnnouncementService;
import net.labymod.core.main.notification.DefaultNotificationController;
import net.labymod.core.event.DefaultEventBus;
import net.labymod.core.client.gui.screen.tree.DefaultScreenTreeTopRegistry;
import net.labymod.api.reference.SingletonReference;
import net.labymod.core.event.method.DefaultSubscribeMethodResolver;
import net.labymod.core.main.debug.filewatcher.WatchablePathManager;
import net.labymod.core.main.serverapi.protocol.intave.IntaveProtocol;
import net.labymod.core.main.quicklauncher.QuickLauncher;
import net.labymod.core.main.updater.Updater;
import net.labymod.core.main.user.badge.BadgeService;
import net.labymod.core.main.user.subtitle.SubtitleService;
import net.labymod.core.main.user.shop.spray.SprayRenderer;
import net.labymod.core.main.user.shop.spray.SprayService;
import net.labymod.core.main.user.shop.spray.SprayRegistry;
import net.labymod.core.main.user.shop.spray.model.SprayAssetProvider;
import net.labymod.core.main.user.shop.emote.EmoteRenderer;
import net.labymod.core.main.user.shop.emote.EmoteLoader;
import net.labymod.core.main.user.shop.emote.DailyEmoteService;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.core.main.user.shop.bridge.ShopItemLayer;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.core.flint.downloader.FlintDownloader;
import net.labymod.core.flint.FlintController;
import net.labymod.core.thirdparty.discord.natives.DiscordNativeDownloader;
import net.labymod.core.client.gfx.pipeline.renderer.cape.particle.CapeParticleController;
import net.labymod.core.client.gfx.shader.preprocessor.GlslPreprocessor;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.tab.ChatSymbolActivity;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.tab.NameHistoryActivity;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.background.BootLogoController;
import net.labymod.core.client.gui.inventory.InventorySlotRegistry;
import net.labymod.core.client.render.font.text.msdf.MSDFResourceProvider;
import net.labymod.core.client.render.draw.DefaultRoundRectangleRenderer;
import net.labymod.core.client.render.draw.shader.rounded.RoundedGeometryShaderInstanceApplier;
import net.labymod.core.client.render.draw.shader.rounded.RoundedGeometryShaderInstanceHolder;
import net.labymod.core.client.options.OptionsTranslator;
import net.labymod.core.client.screenshot.ScreenshotBrowser;
import net.labymod.core.client.world.rplace.RPlaceRegistry;
import net.labymod.core.client.world.WorldEnterEventHandler;
import net.labymod.core.client.input.MouseBridge;
import net.labymod.core.client.input.KeyboardBridge;
import net.labymod.core.platform.PlatformHandler;
import net.labymod.core.labymodnet.LabyModNetService;
import net.labymod.core.labynet.insight.controller.InsightUploader;
import net.labymod.core.shop.ShopController;
import net.labymod.core.labyconnect.session.ApplyTextureController;
import net.labymod.core.labyconnect.object.marker.MarkerService;
import net.labymod.core.labyconnect.object.lootbox.LootBoxService;
import net.labymod.core.labyconnect.lanworld.SharedLanWorldService;
import net.labymod.core.labyconnect.lanworld.CandidateHarvesterService;
import net.labymod.api.LabyAPI;
import net.labymod.api.account.AccountService;
import net.labymod.api.thirdparty.SentryService;
import net.labymod.api.thirdparty.discord.DiscordApp;
import net.labymod.api.thirdparty.ThirdPartyService;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.client.gfx.buffer.VertexArrayObject;
import net.labymod.api.client.gfx.buffer.BufferObject;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.pipeline.texture.atlas.AtlasRegistry;
import net.labymod.api.client.gfx.pipeline.renderer.shader.ShaderPipeline;
import net.labymod.api.client.gfx.pipeline.context.FrameContextRegistry;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.shader.ShaderProgramManager;
import net.labymod.api.client.gfx.vertex.VertexFormat;
import net.labymod.api.client.gfx.vertex.VertexFormatRegistry;
import net.labymod.api.client.gfx.imgui.control.ControlEntryRegistry;
import net.labymod.api.client.gui.screen.ScreenService;
import net.labymod.api.client.gui.screen.widget.window.ScreenWindowManagement;
import net.labymod.api.client.gui.screen.widget.OverlappingTranslator;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler;
import net.labymod.api.client.gui.screen.theme.ThemeFileFinder;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.client.gui.screen.theme.ThemeRendererParser;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.client.gui.screen.game.GameScreenRegistry;
import net.labymod.api.client.gui.screen.key.HotkeyService;
import net.labymod.api.client.gui.screen.activity.activities.ingame.chat.ChatAccessor;
import net.labymod.api.client.gui.screen.activity.overlay.IngameActivityOverlay;
import net.labymod.api.client.gui.screen.activity.overlay.OverlayRegistry;
import net.labymod.api.client.gui.embed.EmbedFactory;
import net.labymod.api.client.gui.TooltipService;
import net.labymod.api.client.gui.navigation.NavigationRegistry;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategoryRegistry;
import net.labymod.api.client.gui.icon.ping.PingIconRegistry;
import net.labymod.api.client.gui.lss.style.StyleHelper;
import net.labymod.api.client.gui.lss.style.function.FunctionRegistry;
import net.labymod.api.client.gui.lss.style.function.parser.ElementParser;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClassRegistry;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.lss.style.modifier.TypeParser;
import net.labymod.api.client.gui.lss.injector.LssInjector;
import net.labymod.api.client.gui.lss.StyleSheetLoader;
import net.labymod.api.client.gui.lss.meta.LinkMetaLoader;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.TextColorStripper;
import net.labymod.api.client.render.font.ComponentRendererBuilder;
import net.labymod.api.client.render.font.text.glyph.GlyphProvider;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.render.font.text.TextRendererProvider;
import net.labymod.api.client.render.draw.BlurRenderer;
import net.labymod.api.client.render.draw.TriangleRenderer;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.client.render.draw.batch.BatchResourceRenderer;
import net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder;
import net.labymod.api.client.render.draw.builder.VanillaWindowBuilder;
import net.labymod.api.client.render.draw.CircleRenderer;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffectRenderer;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.draw.PlayerHeadRenderer;
import net.labymod.api.client.render.draw.HeartRenderer;
import net.labymod.api.client.render.batch.TriangleRenderContext;
import net.labymod.api.client.render.batch.RenderContexts;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.api.client.render.batch.LineRenderContext;
import net.labymod.api.client.render.shader.ShaderProvider;
import net.labymod.api.client.render.shader.program.ShaderInstance;
import net.labymod.api.client.render.shader.MojangShaderRegistry;
import net.labymod.api.client.render.matrix.EmptyStack;
import net.labymod.api.client.render.vertex.OldVertexFormatRegistry;
import net.labymod.api.client.render.HotbarRenderer;
import net.labymod.api.client.render.ExperienceBarRenderer;
import net.labymod.api.client.render.StatusIconRenderer;
import net.labymod.api.client.render.model.ModelUploader;
import net.labymod.api.client.render.model.ModelRenderer;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.client.crash.CrashReportAppenderIterable;
import net.labymod.api.client.GameTickProvider;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.client.entity.player.badge.BadgeRegistry;
import net.labymod.api.client.entity.player.interaction.InteractionMenuRegistry;
import net.labymod.api.client.zoom.ZoomController;
import net.labymod.api.client.world.signobject.SignObjectRegistry;
import net.labymod.api.client.world.object.WorldObjectRegistry;
import net.labymod.api.client.world.canvas.CanvasFactory;
import net.labymod.api.client.world.canvas.CanvasRegistry;
import net.labymod.api.client.chat.autotext.AutoTextService;
import net.labymod.api.client.chat.ChatSymbolRegistry;
import net.labymod.api.client.chat.ChatProvider;
import net.labymod.api.client.chat.input.ChatInputRegistry;
import net.labymod.api.client.chat.filter.FilterChatService;
import net.labymod.api.client.chat.filter.DynamicChatFilterService;
import net.labymod.api.client.chat.advanced.AdvancedChatController;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.chat.ChatController;
import net.labymod.api.client.chat.command.CommandService;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import net.labymod.api.client.resources.transform.ResourceTransformer;
import net.labymod.api.client.resources.transform.ResourceTransformerRegistry;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.client.resources.ResourcesReloadWatcher;
import net.labymod.api.client.resources.texture.concurrent.AsynchronousTextureUploader;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.network.server.global.PublicServerListService;
import net.labymod.api.client.network.server.ServerPinger;
import net.labymod.api.client.network.server.payload.PayloadRegistry;
import net.labymod.api.client.network.server.lan.LanServerDetector;
import net.labymod.api.client.sound.SoundService;
import net.labymod.api.platform.launcher.LauncherService;
import net.labymod.api.platform.launcher.MinecraftLauncherFactory;
import net.labymod.api.serverapi.LabyModProtocolService;
import net.labymod.api.serverapi.LabyProtocolApi;
import net.labymod.api.addon.integration.AddonIntegrationService;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.localization.Internationalization;
import net.labymod.api.user.GameUserService;
import net.labymod.api.user.group.GroupService;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.api.revision.RevisionRegistry;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatGlobalSettingHandler;
import net.labymod.api.configuration.settings.widget.WidgetRegistry;
import net.labymod.api.configuration.settings.SwitchableHandlerRegistry;
import net.labymod.api.configuration.converter.LegacyConfigConverter;
import net.labymod.api.mojang.model.MojangModelService;
import net.labymod.api.util.markdown.MarkdownParser;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.api.util.JsonFileCache;
import net.labymod.api.util.io.web.request.WebResolver;
import net.labymod.api.mapping.MixinRemapperInjector;
import net.labymod.api.mapping.MappingService;
import net.labymod.api.uri.AttachmentParser;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.api.notification.announcement.AnnouncementService;
import net.labymod.api.notification.NotificationController;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopRegistry;
import net.labymod.api.event.method.SubscribeMethodResolver;
import net.labymod.api.reference.Reference;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.api.reference.ReferenceStorageAccessor;
import net.labymod.api.generated.ReferenceStorage;

@AutoService(ReferenceStorageAccessor.class)
public class DefaultReferenceStorage extends ReferenceStorage implements ReferenceStorageAccessor
{
    private final Reference<SubscribeMethodResolver> subscribeMethodResolverReference;
    private final Reference<ScreenTreeTopRegistry> screenTreeTopRegistryReference;
    private final Reference<EventBus> eventBusReference;
    private final Reference<NotificationController> notificationControllerReference;
    private final Reference<AnnouncementService> announcementServiceReference;
    private final Reference<TokenStorage> tokenStorageReference;
    private final Reference<LabyConnect> labyConnectReference;
    private final Reference<AttachmentParser> attachmentParserReference;
    private final Reference<MappingService> mappingServiceReference;
    private final Reference<MixinRemapperInjector> mixinRemapperInjectorReference;
    private final Reference<WebResolver> webResolverReference;
    private final Reference<JsonFileCache.Factory> jsonFileCacheFactoryReference;
    private final Reference<FunctionMemoizeStorage> functionMemoizeStorageReference;
    private final Reference<Logging.Factory> loggingFactoryReference;
    private final Reference<MarkdownParser> markdownParserReference;
    private final Reference<MojangModelService> mojangModelServiceReference;
    private final Reference<LegacyConfigConverter> legacyConfigConverterReference;
    private final Reference<SwitchableHandlerRegistry> switchableHandlerRegistryReference;
    private final Reference<WidgetRegistry> widgetRegistryReference;
    private final Reference<GeneralChatGlobalSettingHandler> generalChatGlobalSettingHandlerReference;
    private final Reference<RevisionRegistry> revisionRegistryReference;
    private final Reference<PermissionRegistry> permissionRegistryReference;
    private final Reference<GroupService> groupServiceReference;
    private final Reference<GameUserService> gameUserServiceReference;
    private final Reference<Internationalization> internationalizationReference;
    private final Reference<LabyNetController> labyNetControllerReference;
    private final Reference<AddonIntegrationService> addonIntegrationServiceReference;
    private final Reference<LabyProtocolApi> labyProtocolApiReference;
    private final Reference<LabyModProtocolService> labyModProtocolServiceReference;
    private final Reference<MinecraftLauncherFactory> minecraftLauncherFactoryReference;
    private final Reference<LauncherService> launcherServiceReference;
    private final Reference<SoundService> soundServiceReference;
    private final Reference<LanServerDetector> lanServerDetectorReference;
    private final Reference<PayloadRegistry> payloadRegistryReference;
    private final Reference<ServerPinger> serverPingerReference;
    private final Reference<PublicServerListService> publicServerListServiceReference;
    private final Reference<Resources> resourcesReference;
    private final Reference<AsynchronousTextureUploader> asynchronousTextureUploaderReference;
    private final Reference<ResourcesReloadWatcher> resourcesReloadWatcherReference;
    private final Reference<ResourcePack.Factory> resourcePackFactoryReference;
    private final Reference<ResourceTransformerRegistry> resourceTransformerRegistryReference;
    private final Reference<ResourceTransformer> resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_vertex_shader;
    private final Reference<ResourceTransformer> resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_fragment_shader;
    private final Reference<ResourceTransformer> resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_json;
    private final Reference<AnimatedResourceLocation.Builder> animatedResourceLocationBuilderReference;
    private final Reference<CommandService> commandServiceReference;
    private final Reference<ChatController> chatControllerReference;
    private final Reference<ChatMessage.Builder> chatMessageBuilderReference;
    private final Reference<AdvancedChatController> advancedChatControllerReference;
    private final Reference<DynamicChatFilterService> dynamicChatFilterServiceReference;
    private final Reference<FilterChatService> filterChatServiceReference;
    private final Reference<ChatInputRegistry> chatInputRegistryReference;
    private final Reference<ChatProvider> chatProviderReference;
    private final Reference<ChatSymbolRegistry> chatSymbolRegistryReference;
    private final Reference<AutoTextService> autoTextServiceReference;
    private final Reference<CanvasRegistry> canvasRegistryReference;
    private final Reference<CanvasFactory> canvasFactoryReference;
    private final Reference<WorldObjectRegistry> worldObjectRegistryReference;
    private final Reference<SignObjectRegistry> signObjectRegistryReference;
    private final Reference<ZoomController> zoomControllerReference;
    private final Reference<InteractionMenuRegistry> interactionMenuRegistryReference;
    private final Reference<BadgeRegistry> badgeRegistryReference;
    private final Reference<TagRegistry> tagRegistryReference;
    private final Reference<MinecraftServices> minecraftServicesReference;
    private final Reference<GameTickProvider> gameTickProviderReference;
    private final Reference<CrashReportAppenderIterable> crashReportAppenderIterableReference;
    private final Reference<ModelService> modelServiceReference;
    private final Reference<ModelRenderer> modelRendererReference;
    private final Reference<ModelUploader> modelUploaderReference;
    private final Reference<StatusIconRenderer> statusIconRendererReference;
    private final Reference<ExperienceBarRenderer> experienceBarRendererReference;
    private final Reference<HotbarRenderer> hotbarRendererReference;
    private final Reference<OldVertexFormatRegistry> oldVertexFormatRegistryReference;
    private final Reference<EmptyStack> emptyStackReference;
    private final Reference<MojangShaderRegistry> mojangShaderRegistryReference;
    private final Reference<ShaderInstance> shaderInstanceReferencerenderphase_3d_environment_texture;
    private final Reference<ShaderInstance> shaderInstanceReferenceframe_buffer_object_stencil_shader_instance;
    private final Reference<ShaderInstance> shaderInstanceReferenceframe_buffer_object_shader_instance;
    private final Reference<ShaderInstance> shaderInstanceReferencecircle_shader_instance;
    private final Reference<ShaderProvider> shaderProviderReference;
    private final Reference<LineRenderContext> lineRenderContextReference;
    private final Reference<RectangleRenderContext> rectangleRenderContextReference;
    private final Reference<ResourceRenderContext> resourceRenderContextReference;
    private final Reference<RenderContexts> renderContextsReference;
    private final Reference<TriangleRenderContext> triangleRenderContextReference;
    private final Reference<HeartRenderer> heartRendererReference;
    private final Reference<PlayerHeadRenderer> playerHeadRendererReference;
    private final Reference<ResourceRenderer> resourceRendererReference;
    private final Reference<RectangleRenderer> rectangleRendererReference;
    private final Reference<HoverBackgroundEffect> hoverBackgroundEffectReferencefancy_hover_effect;
    private final Reference<HoverBackgroundEffect> hoverBackgroundEffectReferencevanilla_hover_effect;
    private final Reference<HoverBackgroundEffectRenderer> hoverBackgroundEffectRendererReference;
    private final Reference<CircleRenderer> circleRendererReference;
    private final Reference<VanillaWindowBuilder> vanillaWindowBuilderReference;
    private final Reference<RoundedGeometryBuilder> roundedGeometryBuilderReference;
    private final Reference<BatchResourceRenderer> batchResourceRendererReference;
    private final Reference<BatchRectangleRenderer> batchRectangleRendererReference;
    private final Reference<TriangleRenderer> triangleRendererReference;
    private final Reference<BlurRenderer> blurRendererReference;
    private final Reference<TextRendererProvider> textRendererProviderReference;
    private final Reference<TextRenderer> textRendererReferencemsdf_text_renderer;
    private final Reference<GlyphProvider> glyphProviderReferencemsdf_glyph_provider;
    private final Reference<ComponentRendererBuilder> componentRendererBuilderReference;
    private final Reference<TextColorStripper> textColorStripperReference;
    private final Reference<ComponentRenderer> componentRendererReference;
    private final Reference<LinkMetaLoader> linkMetaLoaderReference;
    private final Reference<StyleSheetLoader> styleSheetLoaderReference;
    private final Reference<LssInjector> lssInjectorReference;
    private final Reference<TypeParser> typeParserReference;
    private final Reference<WidgetModifier> widgetModifierReference;
    private final Reference<PseudoClassRegistry> pseudoClassRegistryReference;
    private final Reference<PostProcessor> postProcessorReference;
    private final Reference<ElementParser> elementParserReference;
    private final Reference<FunctionRegistry> functionRegistryReference;
    private final Reference<StyleHelper> styleHelperReference;
    private final Reference<PingIconRegistry> pingIconRegistryReference;
    private final Reference<HudWidgetCategoryRegistry> hudWidgetCategoryRegistryReference;
    private final Reference<HudWidgetRegistry> hudWidgetRegistryReference;
    private final Reference<NavigationRegistry> navigationRegistryReference;
    private final Reference<TooltipService> tooltipServiceReference;
    private final Reference<EmbedFactory> embedFactoryReference;
    private final Reference<OverlayRegistry> overlayRegistryReference;
    private final Reference<IngameActivityOverlay> ingameActivityOverlayReference;
    private final Reference<ChatAccessor> chatAccessorReference;
    private final Reference<HotkeyService> hotkeyServiceReference;
    private final Reference<GameScreenRegistry> gameScreenRegistryReference;
    private final Reference<ScreenCustomFontStack> screenCustomFontStackReference;
    private final Reference<ThemeRendererParser> themeRendererParserReference;
    private final Reference<ThemeService> themeServiceReference;
    private final Reference<ThemeFileFinder> themeFileFinderReference;
    private final Reference<ScreenOverlayHandler> screenOverlayHandlerReference;
    private final Reference<OverlappingTranslator> overlappingTranslatorReference;
    private final Reference<ScreenWindowManagement> screenWindowManagementReference;
    private final Reference<ScreenService> screenServiceReference;
    private final Reference<ControlEntryRegistry> controlEntryRegistryReference;
    private final Reference<VertexFormatRegistry> vertexFormatRegistryReference;
    private final Reference<VertexFormat.Builder> vertexFormatBuilderReference;
    private final Reference<ShaderProgramManager> shaderProgramManagerReference;
    private final Reference<ShaderProgram.Factory> shaderProgramFactoryReference;
    private final Reference<RenderEnvironmentContext> renderEnvironmentContextReference;
    private final Reference<FrameContextRegistry> frameContextRegistryReference;
    private final Reference<ShaderPipeline> shaderPipelineReference;
    private final Reference<AtlasRegistry> atlasRegistryReference;
    private final Reference<GFXRenderPipeline> gfxRenderPipelineReference;
    private final Reference<GFXBridge> gfxBridgeReferencegfx_bridge_development;
    private final Reference<BufferObject.Factory> bufferObjectFactoryReference;
    private final Reference<VertexArrayObject.Factory> vertexArrayObjectFactoryReference;
    private final Reference<OptiFine> optiFineReference;
    private final Reference<ThirdPartyService> thirdPartyServiceReference;
    private final Reference<DiscordApp> discordAppReference;
    private final Reference<SentryService> sentryServiceReference;
    private final Reference<AccountService> accountServiceReference;
    private final Reference<LabyAPI> labyAPIReference;
    private final Reference<CandidateHarvesterService> candidateHarvesterServiceReference;
    private final Reference<SharedLanWorldService> sharedLanWorldServiceReference;
    private final Reference<LootBoxService> lootBoxServiceReference;
    private final Reference<MarkerService> markerServiceReference;
    private final Reference<ApplyTextureController> applyTextureControllerReference;
    private final Reference<ShopController> shopControllerReference;
    private final Reference<InsightUploader> insightUploaderReference;
    private final Reference<LabyModNetService> labyModNetServiceReference;
    private final Reference<PlatformHandler> platformHandlerReference;
    private final Reference<KeyboardBridge> keyboardBridgeReference;
    private final Reference<MouseBridge> mouseBridgeReference;
    private final Reference<WorldEnterEventHandler> worldEnterEventHandlerReference;
    private final Reference<RPlaceRegistry> rPlaceRegistryReference;
    private final Reference<ScreenshotBrowser> screenshotBrowserReference;
    private final Reference<OptionsTranslator> optionsTranslatorReference;
    private final Reference<RoundedGeometryShaderInstanceHolder> roundedGeometryShaderInstanceHolderReference;
    private final Reference<RoundedGeometryShaderInstanceApplier> roundedGeometryShaderInstanceApplierReference;
    private final Reference<DefaultRoundRectangleRenderer> defaultRoundRectangleRendererReference;
    private final Reference<MSDFResourceProvider> msdfResourceProviderReference;
    private final Reference<InventorySlotRegistry> inventorySlotRegistryReference;
    private final Reference<BootLogoController> bootLogoControllerReference;
    private final Reference<DynamicBackgroundController> dynamicBackgroundControllerReference;
    private final Reference<NameHistoryActivity> nameHistoryActivityReference;
    private final Reference<ChatSymbolActivity> chatSymbolActivityReference;
    private final Reference<GlslPreprocessor> glslPreprocessorReference;
    private final Reference<CapeParticleController> capeParticleControllerReference;
    private final Reference<DiscordNativeDownloader> discordNativeDownloaderReference;
    private final Reference<FlintController> flintControllerReference;
    private final Reference<FlintDownloader> flintDownloaderReference;
    private final Reference<OldAnimationRegistry> oldAnimationRegistryReference;
    private final Reference<ShopItemLayer> shopItemLayerReference;
    private final Reference<EmoteService> emoteServiceReference;
    private final Reference<DailyEmoteService> dailyEmoteServiceReference;
    private final Reference<EmoteLoader> emoteLoaderReference;
    private final Reference<EmoteRenderer> emoteRendererReference;
    private final Reference<SprayAssetProvider> sprayAssetProviderReference;
    private final Reference<SprayRegistry> sprayRegistryReference;
    private final Reference<SprayService> sprayServiceReference;
    private final Reference<SprayRenderer> sprayRendererReference;
    private final Reference<SubtitleService> subtitleServiceReference;
    private final Reference<BadgeService> badgeServiceReference;
    private final Reference<Updater> updaterReference;
    private final Reference<QuickLauncher> quickLauncherReference;
    private final Reference<IntaveProtocol> intaveProtocolReference;
    private final Reference<WatchablePathManager> watchablePathManagerReference;
    
    public DefaultReferenceStorage() {
        this.subscribeMethodResolverReference = new SingletonReference<SubscribeMethodResolver>(SubscribeMethodResolver.class, () -> new DefaultSubscribeMethodResolver(this.labyAPI()));
        this.screenTreeTopRegistryReference = new SingletonReference<ScreenTreeTopRegistry>(ScreenTreeTopRegistry.class, () -> new DefaultScreenTreeTopRegistry());
        this.eventBusReference = new SingletonReference<EventBus>(EventBus.class, () -> new DefaultEventBus(this.subscribeMethodResolver()));
        this.notificationControllerReference = new SingletonReference<NotificationController>(NotificationController.class, () -> new DefaultNotificationController(this.eventBus()));
        this.announcementServiceReference = new SingletonReference<AnnouncementService>(AnnouncementService.class, () -> new DefaultAnnouncementService(this.labyAPI()));
        this.tokenStorageReference = new SingletonReference<TokenStorage>(TokenStorage.class, () -> new DefaultTokenStorage());
        this.labyConnectReference = new SingletonReference<LabyConnect>(LabyConnect.class, () -> new DefaultLabyConnect(this.sessionAccessor(), this.eventBus(), this.notificationController(), this.commandService()));
        this.attachmentParserReference = new SingletonReference<AttachmentParser>(AttachmentParser.class, () -> new DefaultAttachmentParser());
        this.mappingServiceReference = new SingletonReference<MappingService>(MappingService.class, () -> new FartMappingService());
        this.mixinRemapperInjectorReference = new SingletonReference<MixinRemapperInjector>(MixinRemapperInjector.class, () -> new DefaultMixinRemapperInjector());
        this.webResolverReference = new SingletonReference<WebResolver>(WebResolver.class, () -> new DefaultWebResolver());
        this.jsonFileCacheFactoryReference = new SingletonReference<JsonFileCache.Factory>(JsonFileCache.Factory.class, () -> new DefaultJsonFileCacheFactory());
        this.functionMemoizeStorageReference = new SingletonReference<FunctionMemoizeStorage>(FunctionMemoizeStorage.class, () -> new DefaultFunctionMemoizeStorage());
        this.loggingFactoryReference = new SingletonReference<Logging.Factory>(Logging.Factory.class, () -> new DefaultLoggingFactory());
        this.markdownParserReference = new DynamicReference<MarkdownParser>(MarkdownParser.class, () -> new DefaultMarkdownParser());
        this.mojangModelServiceReference = new SingletonReference<MojangModelService>(MojangModelService.class, () -> new DefaultMojangModelService());
        this.legacyConfigConverterReference = new SingletonReference<LegacyConfigConverter>(LegacyConfigConverter.class, () -> new DefaultLegacyConfigConverter());
        this.switchableHandlerRegistryReference = new SingletonReference<SwitchableHandlerRegistry>(SwitchableHandlerRegistry.class, () -> new DefaultSwitchableHandlerRegistry());
        this.widgetRegistryReference = new SingletonReference<WidgetRegistry>(WidgetRegistry.class, () -> new DefaultWidgetRegistry(this.eventBus()));
        this.generalChatGlobalSettingHandlerReference = new SingletonReference<GeneralChatGlobalSettingHandler>(GeneralChatGlobalSettingHandler.class, () -> new DefaultGeneralChatGlobalSettingHandler(this.labyAPI()));
        this.revisionRegistryReference = new SingletonReference<RevisionRegistry>(RevisionRegistry.class, () -> new DefaultRevisionRegistry());
        this.permissionRegistryReference = new SingletonReference<PermissionRegistry>(PermissionRegistry.class, () -> new DefaultPermissionRegistry());
        this.groupServiceReference = new SingletonReference<GroupService>(GroupService.class, () -> new DefaultGroupService());
        this.gameUserServiceReference = new SingletonReference<GameUserService>(GameUserService.class, () -> new DefaultGameUserService(this.labyAPI()));
        this.internationalizationReference = new SingletonReference<Internationalization>(Internationalization.class, () -> new DefaultInternationalization());
        this.labyNetControllerReference = new SingletonReference<LabyNetController>(LabyNetController.class, () -> new DefaultLabyNetController(this.eventBus()));
        this.addonIntegrationServiceReference = new SingletonReference<AddonIntegrationService>(AddonIntegrationService.class, () -> new DefaultAddonIntegrationService());
        this.labyProtocolApiReference = new SingletonReference<LabyProtocolApi>(LabyProtocolApi.class, () -> new LabyModProtocolApi());
        this.labyModProtocolServiceReference = new SingletonReference<LabyModProtocolService>(LabyModProtocolService.class, () -> new DefaultLabyModProtocolService());
        this.minecraftLauncherFactoryReference = new SingletonReference<MinecraftLauncherFactory>(MinecraftLauncherFactory.class, () -> new DefaultMinecraftLauncherFactory());
        this.launcherServiceReference = new SingletonReference<LauncherService>(LauncherService.class, () -> new DefaultLauncherService());
        this.soundServiceReference = new SingletonReference<SoundService>(SoundService.class, () -> new DefaultSoundService());
        this.lanServerDetectorReference = new DynamicReference<LanServerDetector>(LanServerDetector.class, () -> new DefaultLanServerDetector());
        this.payloadRegistryReference = new SingletonReference<PayloadRegistry>(PayloadRegistry.class, () -> new DefaultPayloadRegistry());
        this.serverPingerReference = new SingletonReference<ServerPinger>(ServerPinger.class, () -> new DefaultServerPinger());
        this.publicServerListServiceReference = new SingletonReference<PublicServerListService>(PublicServerListService.class, () -> new DefaultPublicServerListService());
        this.resourcesReference = new SingletonReference<Resources>(Resources.class, () -> new DefaultResources(this.resourceLocationFactory(), this.textureRepository(), this.gameImageTextureFactory()));
        this.asynchronousTextureUploaderReference = new SingletonReference<AsynchronousTextureUploader>(AsynchronousTextureUploader.class, () -> new DefaultAsynchronousTextureUploader(this.refreshableTextureFactory()));
        this.resourcesReloadWatcherReference = new SingletonReference<ResourcesReloadWatcher>(ResourcesReloadWatcher.class, () -> new DefaultResourcesReloadWatcher(this.resourceTransformerRegistry()));
        this.resourcePackFactoryReference = new SingletonReference<ResourcePack.Factory>(ResourcePack.Factory.class, () -> new DefaultResourcePackFactory());
        this.resourceTransformerRegistryReference = new SingletonReference<ResourceTransformerRegistry>(ResourceTransformerRegistry.class, () -> new DefaultResourceTransformerRegistry(this.loggingFactory(), this.eventBus()));
        this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_vertex_shader = new SingletonReference<ResourceTransformer>(ResourceTransformer.class, () -> new DamageOverlayRenderTypeArmorCutoutNoCullVertexShaderResourceTransformer());
        this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_fragment_shader = new SingletonReference<ResourceTransformer>(ResourceTransformer.class, () -> new DamageOverlayRenderTypeArmorCutoutNoCullFragmentShaderResourceTransformer());
        this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_json = new SingletonReference<ResourceTransformer>(ResourceTransformer.class, () -> new DamageOverlayRenderTypeArmorCutoutNoCullJsonResourceTransformer());
        this.animatedResourceLocationBuilderReference = new DynamicReference<AnimatedResourceLocation.Builder>(AnimatedResourceLocation.Builder.class, () -> new AnimatedResourceLocationBuilder());
        this.commandServiceReference = new SingletonReference<CommandService>(CommandService.class, () -> new DefaultCommandService(this.labyAPI(), this.eventBus()));
        this.chatControllerReference = new SingletonReference<ChatController>(ChatController.class, () -> new DefaultChatController(this.internalChatModifier(), this.eventBus()));
        this.chatMessageBuilderReference = new DynamicReference<ChatMessage.Builder>(ChatMessage.Builder.class, () -> new DefaultChatMessage.Builder());
        this.advancedChatControllerReference = new SingletonReference<AdvancedChatController>(AdvancedChatController.class, () -> new DefaultAdvancedChatController(this.eventBus()));
        this.dynamicChatFilterServiceReference = new SingletonReference<DynamicChatFilterService>(DynamicChatFilterService.class, () -> new DefaultDynamicChatFilterService(this.labyAPI(), this.eventBus()));
        this.filterChatServiceReference = new SingletonReference<FilterChatService>(FilterChatService.class, () -> new DefaultFilterChatService(this.eventBus(), this.minecraftSounds()));
        this.chatInputRegistryReference = new SingletonReference<ChatInputRegistry>(ChatInputRegistry.class, () -> new DefaultChatInputRegistry(this.eventBus(), this.chatSymbolActivity(), this.nameHistoryActivity()));
        this.chatProviderReference = new SingletonReference<ChatProvider>(ChatProvider.class, () -> new DefaultChatProvider(this.chatController(), this.chatInputRegistry(), this.filterChatService(), this.eventBus()));
        this.chatSymbolRegistryReference = new SingletonReference<ChatSymbolRegistry>(ChatSymbolRegistry.class, () -> new DefaultChatSymbolRegistry());
        this.autoTextServiceReference = new SingletonReference<AutoTextService>(AutoTextService.class, () -> new DefaultAutoTextService(this.chatProvider()));
        this.canvasRegistryReference = new SingletonReference<CanvasRegistry>(CanvasRegistry.class, () -> new DefaultCanvasRegistry(this.signObjectRegistry()));
        this.canvasFactoryReference = new SingletonReference<CanvasFactory>(CanvasFactory.class, () -> new DefaultCanvasFactory());
        this.worldObjectRegistryReference = new SingletonReference<WorldObjectRegistry>(WorldObjectRegistry.class, () -> new DefaultWorldObjectRegistry(this.eventBus(), this.ingameActivityOverlay(), this.renderEnvironmentContext()));
        this.signObjectRegistryReference = new SingletonReference<SignObjectRegistry>(SignObjectRegistry.class, () -> new DefaultSignObjectRegistry(this.resourceLocationFactory()));
        this.zoomControllerReference = new SingletonReference<ZoomController>(ZoomController.class, () -> new DefaultZoomController(this.labyAPI()));
        this.interactionMenuRegistryReference = new SingletonReference<InteractionMenuRegistry>(InteractionMenuRegistry.class, () -> new DefaultInteractionMenuRegistry());
        this.badgeRegistryReference = new SingletonReference<BadgeRegistry>(BadgeRegistry.class, () -> new DefaultBadgeRegistry());
        this.tagRegistryReference = new SingletonReference<TagRegistry>(TagRegistry.class, () -> new DefaultTagRegistry());
        this.minecraftServicesReference = new SingletonReference<MinecraftServices>(MinecraftServices.class, () -> new DefaultMinecraftServices(this.sessionAccessor()));
        this.gameTickProviderReference = new SingletonReference<GameTickProvider>(GameTickProvider.class, () -> new DefaultGameTickProvider(this.labyAPI()));
        this.crashReportAppenderIterableReference = new SingletonReference<CrashReportAppenderIterable>(CrashReportAppenderIterable.class, () -> new DefaultCrashReportAppenderIterable());
        this.modelServiceReference = new SingletonReference<ModelService>(ModelService.class, () -> new DefaultModelService());
        this.modelRendererReference = new DynamicReference<ModelRenderer>(ModelRenderer.class, () -> new DefaultModelRenderer());
        this.modelUploaderReference = new SingletonReference<ModelUploader>(ModelUploader.class, () -> new DefaultModelUploader());
        this.statusIconRendererReference = new SingletonReference<StatusIconRenderer>(StatusIconRenderer.class, () -> new DefaultStatusIconRenderer(this.labyAPI()));
        this.experienceBarRendererReference = new SingletonReference<ExperienceBarRenderer>(ExperienceBarRenderer.class, () -> new DefaultExperienceBarRenderer(this.labyAPI()));
        this.hotbarRendererReference = new SingletonReference<HotbarRenderer>(HotbarRenderer.class, () -> new DefaultHotbarRenderer(this.labyAPI(), this.itemStackFactory()));
        this.oldVertexFormatRegistryReference = new SingletonReference<OldVertexFormatRegistry>(OldVertexFormatRegistry.class, () -> new DefaultOldVertexFormatRegistry());
        this.emptyStackReference = new SingletonReference<EmptyStack>(EmptyStack.class, () -> new DefaultEmptyStack());
        this.mojangShaderRegistryReference = new SingletonReference<MojangShaderRegistry>(MojangShaderRegistry.class, () -> new DefaultMojangShaderRegistry());
        this.shaderInstanceReferencerenderphase_3d_environment_texture = new SingletonReference<ShaderInstance>(ShaderInstance.class, () -> new RenderPhase3DEnvironmentTextureShaderInstance(this.shaderProvider()));
        this.shaderInstanceReferenceframe_buffer_object_stencil_shader_instance = new SingletonReference<ShaderInstance>(ShaderInstance.class, () -> new FrameBufferObjectStencilShaderInstance(this.shaderProvider()));
        this.shaderInstanceReferenceframe_buffer_object_shader_instance = new SingletonReference<ShaderInstance>(ShaderInstance.class, () -> new FrameBufferObjectShaderInstance(this.shaderProvider()));
        this.shaderInstanceReferencecircle_shader_instance = new SingletonReference<ShaderInstance>(ShaderInstance.class, () -> new CircleShaderInstance(this.shaderProvider()));
        this.shaderProviderReference = new SingletonReference<ShaderProvider>(ShaderProvider.class, () -> new DefaultShaderProvider());
        this.lineRenderContextReference = new SingletonReference<LineRenderContext>(LineRenderContext.class, () -> new DefaultLineRenderContext());
        this.rectangleRenderContextReference = new SingletonReference<RectangleRenderContext>(RectangleRenderContext.class, () -> new DefaultRectangleRenderContext());
        this.resourceRenderContextReference = new SingletonReference<ResourceRenderContext>(ResourceRenderContext.class, () -> new DefaultResourceRenderContext());
        this.renderContextsReference = new SingletonReference<RenderContexts>(RenderContexts.class, () -> new DefaultRenderContexts(this.resourceRenderContext(), this.lineRenderContext(), this.rectangleRenderContext(), this.triangleRenderContext()));
        this.triangleRenderContextReference = new SingletonReference<TriangleRenderContext>(TriangleRenderContext.class, () -> new DefaultTriangleRenderContext());
        this.heartRendererReference = new DynamicReference<HeartRenderer>(HeartRenderer.class, () -> new DefaultHeartRenderer());
        this.playerHeadRendererReference = new SingletonReference<PlayerHeadRenderer>(PlayerHeadRenderer.class, () -> new DefaultPlayerHeadRenderer());
        this.resourceRendererReference = new SingletonReference<ResourceRenderer>(ResourceRenderer.class, () -> new DefaultResourceRenderer(this.roundedGeometryShaderInstanceHolder(), this.roundedGeometryShaderInstanceApplier(), this.resourceRenderContext(), this.playerHeadRenderer(), this.batchResourceRenderer()));
        this.rectangleRendererReference = new SingletonReference<RectangleRenderer>(RectangleRenderer.class, () -> new DefaultRectangleRenderer(this.labyAPI(), this.defaultRoundRectangleRenderer(), this.roundedGeometryBuilder(), this.rectangleRenderContext(), this.batchRectangleRenderer()));
        this.hoverBackgroundEffectReferencefancy_hover_effect = new SingletonReference<HoverBackgroundEffect>(HoverBackgroundEffect.class, () -> new FancyHoverBackgroundEffect(this.labyAPI()));
        this.hoverBackgroundEffectReferencevanilla_hover_effect = new SingletonReference<HoverBackgroundEffect>(HoverBackgroundEffect.class, () -> new VanillaHoverBackgroundEffect(this.labyAPI()));
        this.hoverBackgroundEffectRendererReference = new SingletonReference<HoverBackgroundEffectRenderer>(HoverBackgroundEffectRenderer.class, () -> new DefaultHoverBackgroundEffectRenderer());
        this.circleRendererReference = new SingletonReference<CircleRenderer>(CircleRenderer.class, () -> new DefaultCircleRenderer(this.shaderInstance("circle_shader_instance"), this.rectangleRenderContext()));
        this.vanillaWindowBuilderReference = new SingletonReference<VanillaWindowBuilder>(VanillaWindowBuilder.class, () -> new DefaultVanillaWindowBuilder(this.resourceRenderer()));
        this.roundedGeometryBuilderReference = new SingletonReference<RoundedGeometryBuilder>(RoundedGeometryBuilder.class, () -> new DefaultRoundedGeometryBuilder());
        this.batchResourceRendererReference = new SingletonReference<BatchResourceRenderer>(BatchResourceRenderer.class, () -> new DefaultBatchResourceRenderer(this.resourceRenderContext()));
        this.batchRectangleRendererReference = new SingletonReference<BatchRectangleRenderer>(BatchRectangleRenderer.class, () -> new DefaultBatchRectangleRenderer(this.rectangleRenderContext()));
        this.triangleRendererReference = new SingletonReference<TriangleRenderer>(TriangleRenderer.class, () -> new DefaultTriangleRenderer(this.triangleRenderContext()));
        this.blurRendererReference = new SingletonReference<BlurRenderer>(BlurRenderer.class, () -> new DefaultBlurRenderer(this.eventBus(), this.gfxRenderPipeline()));
        this.textRendererProviderReference = new SingletonReference<TextRendererProvider>(TextRendererProvider.class, () -> new DefaultTextRendererProvider(this.eventBus(), this.themeService(), this.renderEnvironmentContext(), this.textRenderer("vanilla_text_renderer")));
        this.textRendererReferencemsdf_text_renderer = new SingletonReference<TextRenderer>(TextRenderer.class, () -> new MSDFTextRenderer(this.glyphProvider("msdf_glyph_provider"), this.msdfResourceProvider(), this.stringFormatter()));
        this.glyphProviderReferencemsdf_glyph_provider = new SingletonReference<GlyphProvider>(GlyphProvider.class, () -> new MSDFGlyphProvider(this.msdfResourceProvider()));
        this.componentRendererBuilderReference = new SingletonReference<ComponentRendererBuilder>(ComponentRendererBuilder.class, () -> new DefaultComponentRendererBuilder(this.labyAPI()));
        this.textColorStripperReference = new SingletonReference<TextColorStripper>(TextColorStripper.class, () -> new DefaultTextColorStripper(this.chatSymbolRegistry()));
        this.componentRendererReference = new SingletonReference<ComponentRenderer>(ComponentRenderer.class, () -> new DefaultComponentRenderer(this.componentRendererBuilder(), this.hoverBackgroundEffectRenderer(), this.eventBus()));
        this.linkMetaLoaderReference = new SingletonReference<LinkMetaLoader>(LinkMetaLoader.class, () -> new DefaultLinkMetaLoader(this.labyAPI(), this.eventBus(), this.resourcesReloadWatcher()));
        this.styleSheetLoaderReference = new SingletonReference<StyleSheetLoader>(StyleSheetLoader.class, () -> new DefaultStyleSheetLoader());
        this.lssInjectorReference = new SingletonReference<LssInjector>(LssInjector.class, () -> new DefaultLssInjector(this.labyAPI()));
        this.typeParserReference = new SingletonReference<TypeParser>(TypeParser.class, () -> new DefaultTypeParser(this.themeRendererParser()));
        this.widgetModifierReference = new SingletonReference<WidgetModifier>(WidgetModifier.class, () -> new DefaultWidgetModifier(this.functionRegistry(), this.typeParser(), this.elementParser(), this.postProcessor()));
        this.pseudoClassRegistryReference = new SingletonReference<PseudoClassRegistry>(PseudoClassRegistry.class, () -> new DefaultPseudoClassRegistry(this.elementParser()));
        this.postProcessorReference = new SingletonReference<PostProcessor>(PostProcessor.class, () -> new DefaultPostProcessor(this.typeParser()));
        this.elementParserReference = new SingletonReference<ElementParser>(ElementParser.class, () -> new DefaultElementParser());
        this.functionRegistryReference = new SingletonReference<FunctionRegistry>(FunctionRegistry.class, () -> new DefaultFunctionRegistry());
        this.styleHelperReference = new SingletonReference<StyleHelper>(StyleHelper.class, () -> new DefaultStyleHelper());
        this.pingIconRegistryReference = new SingletonReference<PingIconRegistry>(PingIconRegistry.class, () -> new DefaultPingIconRegistry(this.labyAPI(), this.resourceRenderContext()));
        this.hudWidgetCategoryRegistryReference = new SingletonReference<HudWidgetCategoryRegistry>(HudWidgetCategoryRegistry.class, () -> new DefaultHudWidgetCategoryRegistry());
        this.hudWidgetRegistryReference = new SingletonReference<HudWidgetRegistry>(HudWidgetRegistry.class, () -> new DefaultHudWidgetRegistry(this.labyAPI(), this.hudWidgetCategoryRegistry(), this.revisionRegistry()));
        this.navigationRegistryReference = new SingletonReference<NavigationRegistry>(NavigationRegistry.class, () -> new DefaultNavigationRegistry(this.eventBus()));
        this.tooltipServiceReference = new SingletonReference<TooltipService>(TooltipService.class, () -> new DefaultTooltipService(this.hoverBackgroundEffectRenderer()));
        this.embedFactoryReference = new SingletonReference<EmbedFactory>(EmbedFactory.class, () -> new DefaultEmbedFactory());
        this.overlayRegistryReference = new SingletonReference<OverlayRegistry>(OverlayRegistry.class, () -> new DefaultOverlayRegistry(this.labyAPI()));
        this.ingameActivityOverlayReference = new SingletonReference<IngameActivityOverlay>(IngameActivityOverlay.class, () -> new DefaultIngameActivityOverlay(this.labyAPI(), this.eventBus(), this.screenCustomFontStack()));
        this.chatAccessorReference = new SingletonReference<ChatAccessor>(ChatAccessor.class, () -> new ChatOverlay());
        this.hotkeyServiceReference = new SingletonReference<HotkeyService>(HotkeyService.class, () -> new DefaultHotkeyService());
        this.gameScreenRegistryReference = new SingletonReference<GameScreenRegistry>(GameScreenRegistry.class, () -> new DefaultGameScreenRegistry());
        this.screenCustomFontStackReference = new SingletonReference<ScreenCustomFontStack>(ScreenCustomFontStack.class, () -> new DefaultScreenCustomFontStack(this.frameContextRegistry(), this.renderEnvironmentContext()));
        this.themeRendererParserReference = new SingletonReference<ThemeRendererParser>(ThemeRendererParser.class, () -> new DefaultThemeRendererParser(this.themeService()));
        this.themeServiceReference = new SingletonReference<ThemeService>(ThemeService.class, () -> new DefaultThemeService(this.labyAPI(), this.resourcesReloadWatcher(), this.linkMetaLoader(), this.styleSheetLoader()));
        this.themeFileFinderReference = new SingletonReference<ThemeFileFinder>(ThemeFileFinder.class, () -> new DefaultThemeFileFinder());
        this.screenOverlayHandlerReference = new SingletonReference<ScreenOverlayHandler>(ScreenOverlayHandler.class, () -> new DefaultScreenOverlayHandler(this.labyAPI(), this.screenTreeTopRegistry(), this.screenCustomFontStack()));
        this.overlappingTranslatorReference = new SingletonReference<OverlappingTranslator>(OverlappingTranslator.class, () -> new DefaultOverlappingTranslator());
        this.screenWindowManagementReference = new SingletonReference<ScreenWindowManagement>(ScreenWindowManagement.class, () -> new DefaultScreenWindowManagement(this.labyAPI(), this.screenTreeTopRegistry()));
        this.screenServiceReference = new SingletonReference<ScreenService>(ScreenService.class, () -> new DefaultScreenService());
        this.controlEntryRegistryReference = new SingletonReference<ControlEntryRegistry>(ControlEntryRegistry.class, () -> new DefaultControlEntryRegistry());
        this.vertexFormatRegistryReference = new SingletonReference<VertexFormatRegistry>(VertexFormatRegistry.class, () -> new DefaultVertexFormatRegistry(this.eventBus(), this.resourceLocationFactory()));
        this.vertexFormatBuilderReference = new DynamicReference<VertexFormat.Builder>(VertexFormat.Builder.class, () -> new DefaultVertexFormat.DefaultVertexFormatBuilder());
        this.shaderProgramManagerReference = new SingletonReference<ShaderProgramManager>(ShaderProgramManager.class, () -> new DefaultShaderProgramManager());
        this.shaderProgramFactoryReference = new SingletonReference<ShaderProgram.Factory>(ShaderProgram.Factory.class, () -> new DefaultShaderProgram.DefaultShaderProgramFactory(this.shaderProgramManager()));
        this.renderEnvironmentContextReference = new SingletonReference<RenderEnvironmentContext>(RenderEnvironmentContext.class, () -> new DefaultRenderEnvironmentContext());
        this.frameContextRegistryReference = new SingletonReference<FrameContextRegistry>(FrameContextRegistry.class, () -> new DefaultFrameContextRegistry());
        this.shaderPipelineReference = new SingletonReference<ShaderPipeline>(ShaderPipeline.class, () -> new DefaultShaderPipeline());
        this.atlasRegistryReference = new SingletonReference<AtlasRegistry>(AtlasRegistry.class, () -> new DefaultAtlasRegistry());
        this.gfxRenderPipelineReference = new SingletonReference<GFXRenderPipeline>(GFXRenderPipeline.class, () -> new DefaultGFXRenderPipeline());
        this.gfxBridgeReferencegfx_bridge_development = new SingletonReference<GFXBridge>(GFXBridge.class, () -> new DevelopmentGFXBridge(this.blaze3DGlStatePipeline()));
        this.bufferObjectFactoryReference = new SingletonReference<BufferObject.Factory>(BufferObject.Factory.class, () -> new DefaultBufferObject.DefaultBufferObjectFactory());
        this.vertexArrayObjectFactoryReference = new SingletonReference<VertexArrayObject.Factory>(VertexArrayObject.Factory.class, () -> new DefaultVertexArrayObject.DefaultVertexArrayObjectFactory());
        this.optiFineReference = new SingletonReference<OptiFine>(OptiFine.class, () -> new DefaultOptiFine());
        this.thirdPartyServiceReference = new SingletonReference<ThirdPartyService>(ThirdPartyService.class, () -> new DefaultThirdPartyService(this.labyAPI(), this.eventBus(), this.discordApp()));
        this.discordAppReference = new SingletonReference<DiscordApp>(DiscordApp.class, () -> new DefaultDiscordApp(this.discordNativeDownloader()));
        this.sentryServiceReference = new SingletonReference<SentryService>(SentryService.class, () -> new DefaultSentryService());
        this.accountServiceReference = new SingletonReference<AccountService>(AccountService.class, () -> new AccountManagerController());
        this.labyAPIReference = new SingletonReference<LabyAPI>(LabyAPI.class, () -> new LabyMod());
        this.candidateHarvesterServiceReference = new SingletonReference<CandidateHarvesterService>(CandidateHarvesterService.class, () -> new CandidateHarvesterService());
        this.sharedLanWorldServiceReference = new SingletonReference<SharedLanWorldService>(SharedLanWorldService.class, () -> new SharedLanWorldService(this.integratedServer()));
        this.lootBoxServiceReference = new SingletonReference<LootBoxService>(LootBoxService.class, () -> new LootBoxService());
        this.markerServiceReference = new SingletonReference<MarkerService>(MarkerService.class, () -> new MarkerService(this.worldObjectRegistry()));
        this.applyTextureControllerReference = new SingletonReference<ApplyTextureController>(ApplyTextureController.class, () -> new ApplyTextureController(this.notificationController(), this.labyConnect(), this.labyAPI(), this.resourceLocationFactory(), this.textureRepository(), this.minecraftServices(), this.gameImageProvider(), this.fileDialogs()));
        this.shopControllerReference = new SingletonReference<ShopController>(ShopController.class, () -> new ShopController());
        this.insightUploaderReference = new SingletonReference<InsightUploader>(InsightUploader.class, () -> new InsightUploader());
        this.labyModNetServiceReference = new SingletonReference<LabyModNetService>(LabyModNetService.class, () -> new DefaultLabyModNetService(this.labyConnect(), this.sessionAccessor()));
        this.platformHandlerReference = new SingletonReference<PlatformHandler>(PlatformHandler.class, () -> new PlatformHandler());
        this.keyboardBridgeReference = new SingletonReference<KeyboardBridge>(KeyboardBridge.class, () -> new KeyboardBridge(this.labyAPI(), this.screenTreeTopRegistry()));
        this.mouseBridgeReference = new SingletonReference<MouseBridge>(MouseBridge.class, () -> new MouseBridge(this.labyAPI(), this.screenTreeTopRegistry()));
        this.worldEnterEventHandlerReference = new SingletonReference<WorldEnterEventHandler>(WorldEnterEventHandler.class, () -> new WorldEnterEventHandler(this.labyAPI()));
        this.rPlaceRegistryReference = new SingletonReference<RPlaceRegistry>(RPlaceRegistry.class, () -> new RPlaceRegistry());
        this.screenshotBrowserReference = new SingletonReference<ScreenshotBrowser>(ScreenshotBrowser.class, () -> new ScreenshotBrowser(this.eventBus()));
        this.optionsTranslatorReference = new SingletonReference<OptionsTranslator>(OptionsTranslator.class, () -> new OptionsTranslator());
        this.roundedGeometryShaderInstanceHolderReference = new SingletonReference<RoundedGeometryShaderInstanceHolder>(RoundedGeometryShaderInstanceHolder.class, () -> new RoundedGeometryShaderInstanceHolder(this.shaderProvider()));
        this.roundedGeometryShaderInstanceApplierReference = new SingletonReference<RoundedGeometryShaderInstanceApplier>(RoundedGeometryShaderInstanceApplier.class, () -> new RoundedGeometryShaderInstanceApplier());
        this.defaultRoundRectangleRendererReference = new SingletonReference<DefaultRoundRectangleRenderer>(DefaultRoundRectangleRenderer.class, () -> new DefaultRoundRectangleRenderer(this.renderContexts(), this.roundedGeometryShaderInstanceHolder(), this.roundedGeometryShaderInstanceApplier()));
        this.msdfResourceProviderReference = new SingletonReference<MSDFResourceProvider>(MSDFResourceProvider.class, () -> new MSDFResourceProvider(this.gameImageTextureFactory(), this.themeService(), this.textureRepository()));
        this.inventorySlotRegistryReference = new SingletonReference<InventorySlotRegistry>(InventorySlotRegistry.class, () -> new InventorySlotRegistry());
        this.bootLogoControllerReference = new SingletonReference<BootLogoController>(BootLogoController.class, () -> new BootLogoController());
        this.dynamicBackgroundControllerReference = new SingletonReference<DynamicBackgroundController>(DynamicBackgroundController.class, () -> new DynamicBackgroundController());
        this.nameHistoryActivityReference = new SingletonReference<NameHistoryActivity>(NameHistoryActivity.class, () -> new NameHistoryActivity(this.labyNetController()));
        this.chatSymbolActivityReference = new SingletonReference<ChatSymbolActivity>(ChatSymbolActivity.class, () -> new ChatSymbolActivity());
        this.glslPreprocessorReference = new SingletonReference<GlslPreprocessor>(GlslPreprocessor.class, () -> new GlslPreprocessor());
        this.capeParticleControllerReference = new SingletonReference<CapeParticleController>(CapeParticleController.class, () -> new CapeParticleController());
        this.discordNativeDownloaderReference = new SingletonReference<DiscordNativeDownloader>(DiscordNativeDownloader.class, () -> new DiscordNativeDownloader());
        this.flintControllerReference = new SingletonReference<FlintController>(FlintController.class, () -> new FlintController(this.loggingFactory(), this.labyAPI()));
        this.flintDownloaderReference = new SingletonReference<FlintDownloader>(FlintDownloader.class, () -> new FlintDownloader());
        this.oldAnimationRegistryReference = new SingletonReference<OldAnimationRegistry>(OldAnimationRegistry.class, () -> new OldAnimationRegistry(this.eventBus()));
        this.shopItemLayerReference = new SingletonReference<ShopItemLayer>(ShopItemLayer.class, () -> new ShopItemLayer(this.tagRegistry(), this.labyAPI(), this.eventBus(), this.shaderPipeline()));
        this.emoteServiceReference = new SingletonReference<EmoteService>(EmoteService.class, () -> new EmoteService(this.emoteLoader(), this.emoteRenderer(), this.labyAPI(), this.labyConnect(), this.eventBus()));
        this.dailyEmoteServiceReference = new SingletonReference<DailyEmoteService>(DailyEmoteService.class, () -> new DailyEmoteService());
        this.emoteLoaderReference = new SingletonReference<EmoteLoader>(EmoteLoader.class, () -> new EmoteLoader(this.modelService(), this.resources()));
        this.emoteRendererReference = new SingletonReference<EmoteRenderer>(EmoteRenderer.class, () -> new EmoteRenderer(this.labyAPI(), this.renderConstants()));
        this.sprayAssetProviderReference = new SingletonReference<SprayAssetProvider>(SprayAssetProvider.class, () -> new SprayAssetProvider(this.eventBus(), this.textureRepository(), this.gameImageProvider()));
        this.sprayRegistryReference = new SingletonReference<SprayRegistry>(SprayRegistry.class, () -> new SprayRegistry(this.emoteService(), this.clientWorld()));
        this.sprayServiceReference = new SingletonReference<SprayService>(SprayService.class, () -> new SprayService(this.eventBus(), this.sprayRegistry()));
        this.sprayRendererReference = new SingletonReference<SprayRenderer>(SprayRenderer.class, () -> new SprayRenderer(this.eventBus(), this.surfaceRenderer(), this.gfxRenderPipeline(), this.sprayAssetProvider(), this.sprayService(), this.sprayRegistry()));
        this.subtitleServiceReference = new SingletonReference<SubtitleService>(SubtitleService.class, () -> new SubtitleService(this.labyAPI()));
        this.badgeServiceReference = new SingletonReference<BadgeService>(BadgeService.class, () -> new BadgeService(this.badgeRegistry()));
        this.updaterReference = new SingletonReference<Updater>(Updater.class, () -> new Updater(this.labyAPI(), this.loggingFactory()));
        this.quickLauncherReference = new SingletonReference<QuickLauncher>(QuickLauncher.class, () -> new QuickLauncher(this.labyAPI(), this.loggingFactory()));
        this.intaveProtocolReference = new SingletonReference<IntaveProtocol>(IntaveProtocol.class, () -> new IntaveProtocol(this.labyModProtocolService()));
        this.watchablePathManagerReference = new SingletonReference<WatchablePathManager>(WatchablePathManager.class, () -> new WatchablePathManager());
    }
    
    @NotNull
    @Override
    public SubscribeMethodResolver subscribeMethodResolver() {
        return (SubscribeMethodResolver)this.subscribeMethodResolverReference.get();
    }
    
    @NotNull
    @Override
    public RenderFirstPersonItemInHandEvent.AnimationTypeMapper renderFirstPersonItemInHandEventAnimationTypeMapper() {
        return null;
    }
    
    @NotNull
    @Override
    public ScreenTreeTopRegistry screenTreeTopRegistry() {
        return (ScreenTreeTopRegistry)this.screenTreeTopRegistryReference.get();
    }
    
    @NotNull
    @Override
    public EventBus eventBus() {
        return (EventBus)this.eventBusReference.get();
    }
    
    @NotNull
    @Override
    public NotificationController notificationController() {
        return (NotificationController)this.notificationControllerReference.get();
    }
    
    @NotNull
    @Override
    public AnnouncementService announcementService() {
        return (AnnouncementService)this.announcementServiceReference.get();
    }
    
    @NotNull
    @Override
    public TokenStorage tokenStorage() {
        return (TokenStorage)this.tokenStorageReference.get();
    }
    
    @NotNull
    @Override
    public LabyConnect labyConnect() {
        return (LabyConnect)this.labyConnectReference.get();
    }
    
    @NotNull
    @Override
    public AttachmentParser attachmentParser() {
        return (AttachmentParser)this.attachmentParserReference.get();
    }
    
    @NotNull
    @Override
    public MappingService mappingService() {
        return (MappingService)this.mappingServiceReference.get();
    }
    
    @NotNull
    @Override
    public MixinRemapperInjector mixinRemapperInjector() {
        return (MixinRemapperInjector)this.mixinRemapperInjectorReference.get();
    }
    
    @NotNull
    @Override
    public WebResolver webResolver() {
        return (WebResolver)this.webResolverReference.get();
    }
    
    @NotNull
    @Override
    public GameMathMapper gameMathMapper() {
        return null;
    }
    
    @NotNull
    @Override
    public JsonFileCache.Factory jsonFileCacheFactory() {
        return (JsonFileCache.Factory)this.jsonFileCacheFactoryReference.get();
    }
    
    @NotNull
    @Override
    public FileDialogs fileDialogs() {
        return null;
    }
    
    @NotNull
    @Override
    public FunctionMemoizeStorage functionMemoizeStorage() {
        return (FunctionMemoizeStorage)this.functionMemoizeStorageReference.get();
    }
    
    @NotNull
    @Override
    public Logging.Factory loggingFactory() {
        return (Logging.Factory)this.loggingFactoryReference.get();
    }
    
    @NotNull
    @Override
    public MarkdownParser markdownParser() {
        return (MarkdownParser)this.markdownParserReference.get();
    }
    
    @NotNull
    @Override
    public MojangModelService mojangModelService() {
        return (MojangModelService)this.mojangModelServiceReference.get();
    }
    
    @NotNull
    @Override
    public MojangTextureService mojangTextureService() {
        return null;
    }
    
    @NotNull
    @Override
    public LegacyConfigConverter legacyConfigConverter() {
        return (LegacyConfigConverter)this.legacyConfigConverterReference.get();
    }
    
    @NotNull
    @Override
    public SwitchableHandlerRegistry switchableHandlerRegistry() {
        return (SwitchableHandlerRegistry)this.switchableHandlerRegistryReference.get();
    }
    
    @NotNull
    @Override
    public WidgetRegistry widgetRegistry() {
        return (WidgetRegistry)this.widgetRegistryReference.get();
    }
    
    @NotNull
    @Override
    public GeneralChatGlobalSettingHandler generalChatGlobalSettingHandler() {
        return (GeneralChatGlobalSettingHandler)this.generalChatGlobalSettingHandlerReference.get();
    }
    
    @NotNull
    @Override
    public RevisionRegistry revisionRegistry() {
        return (RevisionRegistry)this.revisionRegistryReference.get();
    }
    
    @NotNull
    @Override
    public PermissionRegistry permissionRegistry() {
        return (PermissionRegistry)this.permissionRegistryReference.get();
    }
    
    @NotNull
    @Override
    public GroupService groupService() {
        return (GroupService)this.groupServiceReference.get();
    }
    
    @NotNull
    @Override
    public GameUserService gameUserService() {
        return (GameUserService)this.gameUserServiceReference.get();
    }
    
    @NotNull
    @Override
    public Internationalization internationalization() {
        return (Internationalization)this.internationalizationReference.get();
    }
    
    @NotNull
    @Override
    public IntegratedServer integratedServer() {
        return null;
    }
    
    @NotNull
    @Override
    public LabyNetController labyNetController() {
        return (LabyNetController)this.labyNetControllerReference.get();
    }
    
    @NotNull
    @Override
    public AddonIntegrationService addonIntegrationService() {
        return (AddonIntegrationService)this.addonIntegrationServiceReference.get();
    }
    
    @NotNull
    @Override
    public LabyProtocolApi labyProtocolApi() {
        return (LabyProtocolApi)this.labyProtocolApiReference.get();
    }
    
    @NotNull
    @Override
    public LabyModProtocolService labyModProtocolService() {
        return (LabyModProtocolService)this.labyModProtocolServiceReference.get();
    }
    
    @NotNull
    @Override
    public MinecraftLauncherFactory minecraftLauncherFactory() {
        return (MinecraftLauncherFactory)this.minecraftLauncherFactoryReference.get();
    }
    
    @NotNull
    @Override
    public LauncherService launcherService() {
        return (LauncherService)this.launcherServiceReference.get();
    }
    
    @NotNull
    @Override
    public ComponentService componentService() {
        return null;
    }
    
    @Nullable
    @Override
    public NumberFormatMapper getNumberFormatMapper() {
        return null;
    }
    
    @NotNull
    @Override
    public SystemInfo systemInfo() {
        return null;
    }
    
    @NotNull
    @Override
    public SoundService soundService() {
        return (SoundService)this.soundServiceReference.get();
    }
    
    @NotNull
    @Override
    public ServerController serverController() {
        return null;
    }
    
    @NotNull
    @Override
    public LanServerDetector lanServerDetector() {
        return (LanServerDetector)this.lanServerDetectorReference.get();
    }
    
    @NotNull
    @Override
    public PayloadRegistry payloadRegistry() {
        return (PayloadRegistry)this.payloadRegistryReference.get();
    }
    
    @NotNull
    @Override
    public ServerPinger serverPinger() {
        return (ServerPinger)this.serverPingerReference.get();
    }
    
    @NotNull
    @Override
    public PublicServerListService publicServerListService() {
        return (PublicServerListService)this.publicServerListServiceReference.get();
    }
    
    @NotNull
    @Override
    public ServerAddressResolver serverAddressResolver() {
        return null;
    }
    
    @NotNull
    @Override
    public ResourceLocationFactory resourceLocationFactory() {
        return null;
    }
    
    @NotNull
    @Override
    public MinecraftSounds minecraftSounds() {
        return null;
    }
    
    @NotNull
    @Override
    public Resources resources() {
        return (Resources)this.resourcesReference.get();
    }
    
    @NotNull
    @Override
    public GameImageTexture.Factory gameImageTextureFactory() {
        return null;
    }
    
    @NotNull
    @Override
    public TextureRepository textureRepository() {
        return null;
    }
    
    @NotNull
    @Override
    public GameImageProvider gameImageProvider() {
        return null;
    }
    
    @NotNull
    @Override
    public AsynchronousTextureUploader asynchronousTextureUploader() {
        return (AsynchronousTextureUploader)this.asynchronousTextureUploaderReference.get();
    }
    
    @NotNull
    @Override
    public ResourcesReloadWatcher resourcesReloadWatcher() {
        return (ResourcesReloadWatcher)this.resourcesReloadWatcherReference.get();
    }
    
    @NotNull
    @Override
    public ResourcePack.Factory resourcePackFactory() {
        return (ResourcePack.Factory)this.resourcePackFactoryReference.get();
    }
    
    @NotNull
    @Override
    public ResourcePackScanner resourcePackScanner() {
        return null;
    }
    
    @NotNull
    @Override
    public ResourcePackRepository resourcePackRepository() {
        return null;
    }
    
    @NotNull
    @Override
    public ResourceTransformerRegistry resourceTransformerRegistry() {
        return (ResourceTransformerRegistry)this.resourceTransformerRegistryReference.get();
    }
    
    @NotNull
    @Override
    public ResourceTransformer resourceTransformer(final String key) {
        if (key.equals("damage_overlay_rendertype_armor_cutout_no_cull_vertex_shader")) {
            return (ResourceTransformer)this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_vertex_shader.get();
        }
        if (key.equals("damage_overlay_rendertype_armor_cutout_no_cull_fragment_shader")) {
            return (ResourceTransformer)this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_fragment_shader.get();
        }
        if (key.equals("damage_overlay_rendertype_armor_cutout_no_cull_json")) {
            return (ResourceTransformer)this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_json.get();
        }
        return null;
    }
    
    @NotNull
    @Override
    public AnimatedResourceLocation.Builder animatedResourceLocationBuilder() {
        return (AnimatedResourceLocation.Builder)this.animatedResourceLocationBuilderReference.get();
    }
    
    @NotNull
    @Override
    public CommandService commandService() {
        return (CommandService)this.commandServiceReference.get();
    }
    
    @NotNull
    @Override
    public ChatController chatController() {
        return (ChatController)this.chatControllerReference.get();
    }
    
    @NotNull
    @Override
    public ChatMessage.Builder chatMessageBuilder() {
        return (ChatMessage.Builder)this.chatMessageBuilderReference.get();
    }
    
    @NotNull
    @Override
    public AdvancedChatController advancedChatController() {
        return (AdvancedChatController)this.advancedChatControllerReference.get();
    }
    
    @NotNull
    @Override
    public DynamicChatFilterService dynamicChatFilterService() {
        return (DynamicChatFilterService)this.dynamicChatFilterServiceReference.get();
    }
    
    @NotNull
    @Override
    public FilterChatService filterChatService() {
        return (FilterChatService)this.filterChatServiceReference.get();
    }
    
    @NotNull
    @Override
    public ChatInputRegistry chatInputRegistry() {
        return (ChatInputRegistry)this.chatInputRegistryReference.get();
    }
    
    @NotNull
    @Override
    public ChatProvider chatProvider() {
        return (ChatProvider)this.chatProviderReference.get();
    }
    
    @NotNull
    @Override
    public ChatSymbolRegistry chatSymbolRegistry() {
        return (ChatSymbolRegistry)this.chatSymbolRegistryReference.get();
    }
    
    @NotNull
    @Override
    public AutoTextService autoTextService() {
        return (AutoTextService)this.autoTextServiceReference.get();
    }
    
    @NotNull
    @Override
    public ChatExecutor chatExecutor() {
        return null;
    }
    
    @NotNull
    @Override
    public BlockColorProvider blockColorProvider() {
        return null;
    }
    
    @NotNull
    @Override
    public Blocks blocks() {
        return null;
    }
    
    @NotNull
    @Override
    public CanvasRegistry canvasRegistry() {
        return (CanvasRegistry)this.canvasRegistryReference.get();
    }
    
    @NotNull
    @Override
    public CanvasFactory canvasFactory() {
        return (CanvasFactory)this.canvasFactoryReference.get();
    }
    
    @NotNull
    @Override
    public WorldObjectRegistry worldObjectRegistry() {
        return (WorldObjectRegistry)this.worldObjectRegistryReference.get();
    }
    
    @NotNull
    @Override
    public HitResultController hitResultController() {
        return null;
    }
    
    @NotNull
    @Override
    public ClientWorld clientWorld() {
        return null;
    }
    
    @NotNull
    @Override
    public BossBarRegistry bossBarRegistry() {
        return null;
    }
    
    @NotNull
    @Override
    public LightingLayerMapper lightingLayerMapper() {
        return null;
    }
    
    @NotNull
    @Override
    public ItemStackFactory itemStackFactory() {
        return null;
    }
    
    @NotNull
    @Override
    public SignObjectRegistry signObjectRegistry() {
        return (SignObjectRegistry)this.signObjectRegistryReference.get();
    }
    
    @NotNull
    @Override
    public ZoomController zoomController() {
        return (ZoomController)this.zoomControllerReference.get();
    }
    
    @NotNull
    @Override
    public InteractionMenuRegistry interactionMenuRegistry() {
        return (InteractionMenuRegistry)this.interactionMenuRegistryReference.get();
    }
    
    @NotNull
    @Override
    public BadgeRegistry badgeRegistry() {
        return (BadgeRegistry)this.badgeRegistryReference.get();
    }
    
    @NotNull
    @Override
    public TagRegistry tagRegistry() {
        return (TagRegistry)this.tagRegistryReference.get();
    }
    
    @NotNull
    @Override
    public EntityPoseMapper entityPoseMapper() {
        return null;
    }
    
    @NotNull
    @Override
    public MinecraftAuthenticator minecraftAuthenticator() {
        return null;
    }
    
    @NotNull
    @Override
    public SessionAccessor sessionAccessor() {
        return null;
    }
    
    @NotNull
    @Override
    public MinecraftServices minecraftServices() {
        return (MinecraftServices)this.minecraftServicesReference.get();
    }
    
    @NotNull
    @Override
    public ParticleController particleController() {
        return null;
    }
    
    @NotNull
    @Override
    public GameTickProvider gameTickProvider() {
        return (GameTickProvider)this.gameTickProviderReference.get();
    }
    
    @NotNull
    @Override
    public CrashReportAppenderIterable crashReportAppenderIterable() {
        return (CrashReportAppenderIterable)this.crashReportAppenderIterableReference.get();
    }
    
    @NotNull
    @Override
    public GameCrashReport.Factory gameCrashReportFactory() {
        return null;
    }
    
    @NotNull
    @Override
    public RenderPipeline renderPipeline() {
        return null;
    }
    
    @NotNull
    @Override
    public ModelService modelService() {
        return (ModelService)this.modelServiceReference.get();
    }
    
    @NotNull
    @Override
    public ModelRenderer modelRenderer() {
        return (ModelRenderer)this.modelRendererReference.get();
    }
    
    @NotNull
    @Override
    public ModelUploader modelUploader() {
        return (ModelUploader)this.modelUploaderReference.get();
    }
    
    @NotNull
    @Override
    public StatusIconRenderer statusIconRenderer() {
        return (StatusIconRenderer)this.statusIconRendererReference.get();
    }
    
    @NotNull
    @Override
    public RenderConstants renderConstants() {
        return null;
    }
    
    @NotNull
    @Override
    public ExperienceBarRenderer experienceBarRenderer() {
        return (ExperienceBarRenderer)this.experienceBarRendererReference.get();
    }
    
    @NotNull
    @Override
    public HotbarRenderer hotbarRenderer() {
        return (HotbarRenderer)this.hotbarRendererReference.get();
    }
    
    @NotNull
    @Override
    public OutputRenderShard.OutputRender outputRenderShardOutputRender(final String key) {
        return null;
    }
    
    @NotNull
    @Override
    public ViewOffsetZLayer viewOffsetZLayer() {
        return null;
    }
    
    @NotNull
    @Override
    public OldVertexFormatRegistry oldVertexFormatRegistry() {
        return (OldVertexFormatRegistry)this.oldVertexFormatRegistryReference.get();
    }
    
    @NotNull
    @Override
    public EmptyStack emptyStack() {
        return (EmptyStack)this.emptyStackReference.get();
    }
    
    @NotNull
    @Override
    public StackProviderFactory stackProviderFactory() {
        return null;
    }
    
    @NotNull
    @Override
    public MojangShaderRegistry mojangShaderRegistry() {
        return (MojangShaderRegistry)this.mojangShaderRegistryReference.get();
    }
    
    @NotNull
    @Override
    public ShaderInstance shaderInstance(final String key) {
        if (key.equals("renderphase_3d_environment_texture")) {
            return (ShaderInstance)this.shaderInstanceReferencerenderphase_3d_environment_texture.get();
        }
        if (key.equals("frame_buffer_object_stencil_shader_instance")) {
            return (ShaderInstance)this.shaderInstanceReferenceframe_buffer_object_stencil_shader_instance.get();
        }
        if (key.equals("frame_buffer_object_shader_instance")) {
            return (ShaderInstance)this.shaderInstanceReferenceframe_buffer_object_shader_instance.get();
        }
        if (key.equals("circle_shader_instance")) {
            return (ShaderInstance)this.shaderInstanceReferencecircle_shader_instance.get();
        }
        return null;
    }
    
    @NotNull
    @Override
    public ShaderProvider shaderProvider() {
        return (ShaderProvider)this.shaderProviderReference.get();
    }
    
    @NotNull
    @Override
    public LineRenderContext lineRenderContext() {
        return (LineRenderContext)this.lineRenderContextReference.get();
    }
    
    @NotNull
    @Override
    public RectangleRenderContext rectangleRenderContext() {
        return (RectangleRenderContext)this.rectangleRenderContextReference.get();
    }
    
    @NotNull
    @Override
    public ResourceRenderContext resourceRenderContext() {
        return (ResourceRenderContext)this.resourceRenderContextReference.get();
    }
    
    @NotNull
    @Override
    public RenderContexts renderContexts() {
        return (RenderContexts)this.renderContextsReference.get();
    }
    
    @NotNull
    @Override
    public TriangleRenderContext triangleRenderContext() {
        return (TriangleRenderContext)this.triangleRenderContextReference.get();
    }
    
    @NotNull
    @Override
    public HeartRenderer heartRenderer() {
        return (HeartRenderer)this.heartRendererReference.get();
    }
    
    @NotNull
    @Override
    public PlayerHeadRenderer playerHeadRenderer() {
        return (PlayerHeadRenderer)this.playerHeadRendererReference.get();
    }
    
    @NotNull
    @Override
    public ResourceRenderer resourceRenderer() {
        return (ResourceRenderer)this.resourceRendererReference.get();
    }
    
    @NotNull
    @Override
    public RectangleRenderer rectangleRenderer() {
        return (RectangleRenderer)this.rectangleRendererReference.get();
    }
    
    @NotNull
    @Override
    public HoverBackgroundEffect hoverBackgroundEffect(final String key) {
        if (key.equals("fancy_hover_effect")) {
            return (HoverBackgroundEffect)this.hoverBackgroundEffectReferencefancy_hover_effect.get();
        }
        if (key.equals("vanilla_hover_effect")) {
            return (HoverBackgroundEffect)this.hoverBackgroundEffectReferencevanilla_hover_effect.get();
        }
        return null;
    }
    
    @NotNull
    @Override
    public HoverBackgroundEffectRenderer hoverBackgroundEffectRenderer() {
        return (HoverBackgroundEffectRenderer)this.hoverBackgroundEffectRendererReference.get();
    }
    
    @NotNull
    @Override
    public CircleRenderer circleRenderer() {
        return (CircleRenderer)this.circleRendererReference.get();
    }
    
    @NotNull
    @Override
    public VanillaWindowBuilder vanillaWindowBuilder() {
        return (VanillaWindowBuilder)this.vanillaWindowBuilderReference.get();
    }
    
    @NotNull
    @Override
    public RoundedGeometryBuilder roundedGeometryBuilder() {
        return (RoundedGeometryBuilder)this.roundedGeometryBuilderReference.get();
    }
    
    @NotNull
    @Override
    public BatchResourceRenderer batchResourceRenderer() {
        return (BatchResourceRenderer)this.batchResourceRendererReference.get();
    }
    
    @NotNull
    @Override
    public BatchRectangleRenderer batchRectangleRenderer() {
        return (BatchRectangleRenderer)this.batchRectangleRendererReference.get();
    }
    
    @NotNull
    @Override
    public TriangleRenderer triangleRenderer() {
        return (TriangleRenderer)this.triangleRendererReference.get();
    }
    
    @NotNull
    @Override
    public BlurRenderer blurRenderer() {
        return (BlurRenderer)this.blurRendererReference.get();
    }
    
    @NotNull
    @Override
    public TextRendererProvider textRendererProvider() {
        return (TextRendererProvider)this.textRendererProviderReference.get();
    }
    
    @NotNull
    @Override
    public TextRenderer textRenderer(final String key) {
        if (key.equals("msdf_text_renderer")) {
            return (TextRenderer)this.textRendererReferencemsdf_text_renderer.get();
        }
        return null;
    }
    
    @NotNull
    @Override
    public StringFormatter stringFormatter() {
        return null;
    }
    
    @NotNull
    @Override
    public GlyphProvider glyphProvider(final String key) {
        if (key.equals("msdf_glyph_provider")) {
            return (GlyphProvider)this.glyphProviderReferencemsdf_glyph_provider.get();
        }
        return null;
    }
    
    @NotNull
    @Override
    public ComponentMapper componentMapper() {
        return null;
    }
    
    @NotNull
    @Override
    public ComponentRendererBuilder componentRendererBuilder() {
        return (ComponentRendererBuilder)this.componentRendererBuilderReference.get();
    }
    
    @NotNull
    @Override
    public TextColorStripper textColorStripper() {
        return (TextColorStripper)this.textColorStripperReference.get();
    }
    
    @NotNull
    @Override
    public ComponentRenderer componentRenderer() {
        return (ComponentRenderer)this.componentRendererReference.get();
    }
    
    @NotNull
    @Override
    public PlayerHeartRenderer playerHeartRenderer() {
        return null;
    }
    
    @NotNull
    @Override
    public GlInformation glInformation() {
        return null;
    }
    
    @NotNull
    @Override
    public GlStateBridge glStateBridge() {
        return null;
    }
    
    @NotNull
    @Override
    public LinkMetaLoader linkMetaLoader() {
        return (LinkMetaLoader)this.linkMetaLoaderReference.get();
    }
    
    @NotNull
    @Override
    public StyleSheetLoader styleSheetLoader() {
        return (StyleSheetLoader)this.styleSheetLoaderReference.get();
    }
    
    @NotNull
    @Override
    public LssInjector lssInjector() {
        return (LssInjector)this.lssInjectorReference.get();
    }
    
    @NotNull
    @Override
    public TypeParser typeParser() {
        return (TypeParser)this.typeParserReference.get();
    }
    
    @NotNull
    @Override
    public WidgetModifier widgetModifier() {
        return (WidgetModifier)this.widgetModifierReference.get();
    }
    
    @NotNull
    @Override
    public PseudoClassRegistry pseudoClassRegistry() {
        return (PseudoClassRegistry)this.pseudoClassRegistryReference.get();
    }
    
    @NotNull
    @Override
    public PostProcessor postProcessor() {
        return (PostProcessor)this.postProcessorReference.get();
    }
    
    @NotNull
    @Override
    public ElementParser elementParser() {
        return (ElementParser)this.elementParserReference.get();
    }
    
    @NotNull
    @Override
    public FunctionRegistry functionRegistry() {
        return (FunctionRegistry)this.functionRegistryReference.get();
    }
    
    @NotNull
    @Override
    public StyleHelper styleHelper() {
        return (StyleHelper)this.styleHelperReference.get();
    }
    
    @NotNull
    @Override
    public PingIconRegistry pingIconRegistry() {
        return (PingIconRegistry)this.pingIconRegistryReference.get();
    }
    
    @NotNull
    @Override
    public HudWidgetCategoryRegistry hudWidgetCategoryRegistry() {
        return (HudWidgetCategoryRegistry)this.hudWidgetCategoryRegistryReference.get();
    }
    
    @NotNull
    @Override
    public HudWidgetRegistry hudWidgetRegistry() {
        return (HudWidgetRegistry)this.hudWidgetRegistryReference.get();
    }
    
    @NotNull
    @Override
    public NavigationRegistry navigationRegistry() {
        return (NavigationRegistry)this.navigationRegistryReference.get();
    }
    
    @NotNull
    @Override
    public TooltipService tooltipService() {
        return (TooltipService)this.tooltipServiceReference.get();
    }
    
    @NotNull
    @Override
    public EmbedFactory embedFactory() {
        return (EmbedFactory)this.embedFactoryReference.get();
    }
    
    @NotNull
    @Override
    public OverlayRegistry overlayRegistry() {
        return (OverlayRegistry)this.overlayRegistryReference.get();
    }
    
    @NotNull
    @Override
    public IngameActivityOverlay ingameActivityOverlay() {
        return (IngameActivityOverlay)this.ingameActivityOverlayReference.get();
    }
    
    @NotNull
    @Override
    public ChatAccessor chatAccessor() {
        return (ChatAccessor)this.chatAccessorReference.get();
    }
    
    @NotNull
    @Override
    public ScreenWrapper.Factory screenWrapperFactory() {
        return null;
    }
    
    @NotNull
    @Override
    public HotkeyService hotkeyService() {
        return (HotkeyService)this.hotkeyServiceReference.get();
    }
    
    @NotNull
    @Override
    public KeyMapper keyMapper() {
        return null;
    }
    
    @NotNull
    @Override
    public GameScreenRegistry gameScreenRegistry() {
        return (GameScreenRegistry)this.gameScreenRegistryReference.get();
    }
    
    @NotNull
    @Override
    public ScreenCustomFontStack screenCustomFontStack() {
        return (ScreenCustomFontStack)this.screenCustomFontStackReference.get();
    }
    
    @NotNull
    @Override
    public ThemeRendererParser themeRendererParser() {
        return (ThemeRendererParser)this.themeRendererParserReference.get();
    }
    
    @NotNull
    @Override
    public ThemeService themeService() {
        return (ThemeService)this.themeServiceReference.get();
    }
    
    @NotNull
    @Override
    public ThemeFileFinder themeFileFinder() {
        return (ThemeFileFinder)this.themeFileFinderReference.get();
    }
    
    @NotNull
    @Override
    public ScreenOverlayHandler screenOverlayHandler() {
        return (ScreenOverlayHandler)this.screenOverlayHandlerReference.get();
    }
    
    @NotNull
    @Override
    public OverlappingTranslator overlappingTranslator() {
        return (OverlappingTranslator)this.overlappingTranslatorReference.get();
    }
    
    @NotNull
    @Override
    public ScreenWindowManagement screenWindowManagement() {
        return (ScreenWindowManagement)this.screenWindowManagementReference.get();
    }
    
    @NotNull
    @Override
    public ScreenService screenService() {
        return (ScreenService)this.screenServiceReference.get();
    }
    
    @NotNull
    @Override
    public ImGuiTypeProvider imGuiTypeProvider() {
        return null;
    }
    
    @NotNull
    @Override
    public ImGuiAccessor imGuiAccessor() {
        return null;
    }
    
    @NotNull
    @Override
    public ControlEntryRegistry controlEntryRegistry() {
        return (ControlEntryRegistry)this.controlEntryRegistryReference.get();
    }
    
    @NotNull
    @Override
    public VertexFormatRegistry vertexFormatRegistry() {
        return (VertexFormatRegistry)this.vertexFormatRegistryReference.get();
    }
    
    @NotNull
    @Override
    public VertexFormat.Builder vertexFormatBuilder() {
        return (VertexFormat.Builder)this.vertexFormatBuilderReference.get();
    }
    
    @NotNull
    @Override
    public ShaderProgramManager shaderProgramManager() {
        return (ShaderProgramManager)this.shaderProgramManagerReference.get();
    }
    
    @NotNull
    @Override
    public ShaderProgram.Factory shaderProgramFactory() {
        return (ShaderProgram.Factory)this.shaderProgramFactoryReference.get();
    }
    
    @NotNull
    @Override
    public LightmapTexture lightmapTexture() {
        return null;
    }
    
    @NotNull
    @Override
    public RenderEnvironmentContext renderEnvironmentContext() {
        return (RenderEnvironmentContext)this.renderEnvironmentContextReference.get();
    }
    
    @NotNull
    @Override
    public Blaze3DShaderUniformPipeline blaze3DShaderUniformPipeline() {
        return null;
    }
    
    @NotNull
    @Override
    public FrameContextRegistry frameContextRegistry() {
        return (FrameContextRegistry)this.frameContextRegistryReference.get();
    }
    
    @NotNull
    @Override
    public ShaderPipeline shaderPipeline() {
        return (ShaderPipeline)this.shaderPipelineReference.get();
    }
    
    @NotNull
    @Override
    public Blaze3DGlStatePipeline blaze3DGlStatePipeline() {
        return null;
    }
    
    @NotNull
    @Override
    public MinecraftAtlases minecraftAtlases() {
        return null;
    }
    
    @NotNull
    @Override
    public AtlasRegistry atlasRegistry() {
        return (AtlasRegistry)this.atlasRegistryReference.get();
    }
    
    @NotNull
    @Override
    public GFXRenderPipeline gfxRenderPipeline() {
        return (GFXRenderPipeline)this.gfxRenderPipelineReference.get();
    }
    
    @NotNull
    @Override
    public StandardBlaze3DRenderTypes standardBlaze3DRenderTypes() {
        return null;
    }
    
    @NotNull
    @Override
    public GFXBridge gfxBridge(final String key) {
        if (key.equals("gfx_bridge_development")) {
            return (GFXBridge)this.gfxBridgeReferencegfx_bridge_development.get();
        }
        return null;
    }
    
    @NotNull
    @Override
    public BufferObject.Factory bufferObjectFactory() {
        return (BufferObject.Factory)this.bufferObjectFactoryReference.get();
    }
    
    @NotNull
    @Override
    public VertexArrayObject.Factory vertexArrayObjectFactory() {
        return (VertexArrayObject.Factory)this.vertexArrayObjectFactoryReference.get();
    }
    
    @NotNull
    @Override
    public OptiFine optiFine() {
        return (OptiFine)this.optiFineReference.get();
    }
    
    @NotNull
    @Override
    public ThirdPartyService thirdPartyService() {
        return (ThirdPartyService)this.thirdPartyServiceReference.get();
    }
    
    @NotNull
    @Override
    public DiscordApp discordApp() {
        return (DiscordApp)this.discordAppReference.get();
    }
    
    @NotNull
    @Override
    public SentryService sentryService() {
        return (SentryService)this.sentryServiceReference.get();
    }
    
    @NotNull
    @Override
    public NBTFactory nbtFactory() {
        return null;
    }
    
    @NotNull
    @Override
    public AccountService accountService() {
        return (AccountService)this.accountServiceReference.get();
    }
    
    @NotNull
    @Override
    public LabyAPI labyAPI() {
        return (LabyAPI)this.labyAPIReference.get();
    }
    
    @NotNull
    public CandidateHarvesterService candidateHarvesterService() {
        return (CandidateHarvesterService)this.candidateHarvesterServiceReference.get();
    }
    
    @NotNull
    public SharedLanWorldService sharedLanWorldService() {
        return (SharedLanWorldService)this.sharedLanWorldServiceReference.get();
    }
    
    @NotNull
    public LootBoxService lootBoxService() {
        return (LootBoxService)this.lootBoxServiceReference.get();
    }
    
    @NotNull
    public MarkerService markerService() {
        return (MarkerService)this.markerServiceReference.get();
    }
    
    @NotNull
    public ApplyTextureController applyTextureController() {
        return (ApplyTextureController)this.applyTextureControllerReference.get();
    }
    
    @NotNull
    public ShopController shopController() {
        return (ShopController)this.shopControllerReference.get();
    }
    
    @NotNull
    public InsightUploader insightUploader() {
        return (InsightUploader)this.insightUploaderReference.get();
    }
    
    @NotNull
    public LabyModNetService labyModNetService() {
        return (LabyModNetService)this.labyModNetServiceReference.get();
    }
    
    @NotNull
    public PlatformHandler platformHandler() {
        return (PlatformHandler)this.platformHandlerReference.get();
    }
    
    @NotNull
    public BufferSourceGui bufferSourceGui() {
        return null;
    }
    
    @NotNull
    public RefreshableTextureFactory refreshableTextureFactory() {
        return null;
    }
    
    @NotNull
    public InternalChatModifier internalChatModifier() {
        return null;
    }
    
    @NotNull
    public KeyboardBridge keyboardBridge() {
        return (KeyboardBridge)this.keyboardBridgeReference.get();
    }
    
    @NotNull
    public MouseBridge mouseBridge() {
        return (MouseBridge)this.mouseBridgeReference.get();
    }
    
    @NotNull
    public WorldEnterEventHandler worldEnterEventHandler() {
        return (WorldEnterEventHandler)this.worldEnterEventHandlerReference.get();
    }
    
    @NotNull
    public RPlaceRegistry rPlaceRegistry() {
        return (RPlaceRegistry)this.rPlaceRegistryReference.get();
    }
    
    @NotNull
    public ClientNetworkPacketListener clientNetworkPacketListener() {
        return null;
    }
    
    @NotNull
    public ScreenshotBrowser screenshotBrowser() {
        return (ScreenshotBrowser)this.screenshotBrowserReference.get();
    }
    
    @NotNull
    public UserFactory userFactory() {
        return null;
    }
    
    @NotNull
    public FontSetGlyphProvider fontSetGlyphProvider() {
        return null;
    }
    
    @NotNull
    public OptionsTranslator optionsTranslator() {
        return (OptionsTranslator)this.optionsTranslatorReference.get();
    }
    
    @NotNull
    public RoundedGeometryShaderInstanceHolder roundedGeometryShaderInstanceHolder() {
        return (RoundedGeometryShaderInstanceHolder)this.roundedGeometryShaderInstanceHolderReference.get();
    }
    
    @NotNull
    public RoundedGeometryShaderInstanceApplier roundedGeometryShaderInstanceApplier() {
        return (RoundedGeometryShaderInstanceApplier)this.roundedGeometryShaderInstanceApplierReference.get();
    }
    
    @NotNull
    public DefaultRoundRectangleRenderer defaultRoundRectangleRenderer() {
        return (DefaultRoundRectangleRenderer)this.defaultRoundRectangleRendererReference.get();
    }
    
    @NotNull
    public MSDFResourceProvider msdfResourceProvider() {
        return (MSDFResourceProvider)this.msdfResourceProviderReference.get();
    }
    
    @NotNull
    public InventorySlotRegistry inventorySlotRegistry() {
        return (InventorySlotRegistry)this.inventorySlotRegistryReference.get();
    }
    
    @NotNull
    public BootLogoController bootLogoController() {
        return (BootLogoController)this.bootLogoControllerReference.get();
    }
    
    @NotNull
    public PanoramaRenderer panoramaRenderer() {
        return null;
    }
    
    @NotNull
    public DynamicBackgroundController dynamicBackgroundController() {
        return (DynamicBackgroundController)this.dynamicBackgroundControllerReference.get();
    }
    
    @NotNull
    public NameHistoryActivity nameHistoryActivity() {
        return (NameHistoryActivity)this.nameHistoryActivityReference.get();
    }
    
    @NotNull
    public ChatSymbolActivity chatSymbolActivity() {
        return (ChatSymbolActivity)this.chatSymbolActivityReference.get();
    }
    
    @NotNull
    public GlslPreprocessor glslPreprocessor() {
        return (GlslPreprocessor)this.glslPreprocessorReference.get();
    }
    
    @NotNull
    public CapeParticleController capeParticleController() {
        return (CapeParticleController)this.capeParticleControllerReference.get();
    }
    
    @NotNull
    public DiscordNativeDownloader discordNativeDownloader() {
        return (DiscordNativeDownloader)this.discordNativeDownloaderReference.get();
    }
    
    @NotNull
    public FlintController flintController() {
        return (FlintController)this.flintControllerReference.get();
    }
    
    @NotNull
    public FlintDownloader flintDownloader() {
        return (FlintDownloader)this.flintDownloaderReference.get();
    }
    
    @NotNull
    public OldAnimationRegistry oldAnimationRegistry() {
        return (OldAnimationRegistry)this.oldAnimationRegistryReference.get();
    }
    
    @NotNull
    public ShopItemLayer shopItemLayer() {
        return (ShopItemLayer)this.shopItemLayerReference.get();
    }
    
    @NotNull
    public EmoteService emoteService() {
        return (EmoteService)this.emoteServiceReference.get();
    }
    
    @NotNull
    public DailyEmoteService dailyEmoteService() {
        return (DailyEmoteService)this.dailyEmoteServiceReference.get();
    }
    
    @NotNull
    public EmoteLoader emoteLoader() {
        return (EmoteLoader)this.emoteLoaderReference.get();
    }
    
    @NotNull
    public EmoteRenderer emoteRenderer() {
        return (EmoteRenderer)this.emoteRendererReference.get();
    }
    
    @NotNull
    public SprayAssetProvider sprayAssetProvider() {
        return (SprayAssetProvider)this.sprayAssetProviderReference.get();
    }
    
    @NotNull
    public SprayRegistry sprayRegistry() {
        return (SprayRegistry)this.sprayRegistryReference.get();
    }
    
    @NotNull
    public SprayService sprayService() {
        return (SprayService)this.sprayServiceReference.get();
    }
    
    @NotNull
    public SprayRenderer sprayRenderer() {
        return (SprayRenderer)this.sprayRendererReference.get();
    }
    
    @NotNull
    public SubtitleService subtitleService() {
        return (SubtitleService)this.subtitleServiceReference.get();
    }
    
    @NotNull
    public BadgeService badgeService() {
        return (BadgeService)this.badgeServiceReference.get();
    }
    
    @NotNull
    public Updater updater() {
        return (Updater)this.updaterReference.get();
    }
    
    @NotNull
    public QuickLauncher quickLauncher() {
        return (QuickLauncher)this.quickLauncherReference.get();
    }
    
    @NotNull
    public IntaveProtocol intaveProtocol() {
        return (IntaveProtocol)this.intaveProtocolReference.get();
    }
    
    @NotNull
    public WatchablePathManager watchablePathManager() {
        return (WatchablePathManager)this.watchablePathManagerReference.get();
    }
}
