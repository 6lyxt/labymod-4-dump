// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.generated;

import net.labymod.api.LabyAPI;
import net.labymod.api.account.AccountService;
import net.labymod.api.nbt.NBTFactory;
import net.labymod.api.thirdparty.SentryService;
import net.labymod.api.thirdparty.discord.DiscordApp;
import net.labymod.api.thirdparty.ThirdPartyService;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.client.gfx.buffer.VertexArrayObject;
import net.labymod.api.client.gfx.buffer.BufferObject;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.StandardBlaze3DRenderTypes;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.pipeline.texture.atlas.AtlasRegistry;
import net.labymod.api.client.gfx.pipeline.texture.atlas.MinecraftAtlases;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.client.gfx.pipeline.renderer.shader.ShaderPipeline;
import net.labymod.api.client.gfx.pipeline.context.FrameContextRegistry;
import net.labymod.api.client.gfx.pipeline.Blaze3DShaderUniformPipeline;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.texture.LightmapTexture;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.shader.ShaderProgramManager;
import net.labymod.api.client.gfx.vertex.VertexFormat;
import net.labymod.api.client.gfx.vertex.VertexFormatRegistry;
import net.labymod.api.client.gfx.imgui.control.ControlEntryRegistry;
import net.labymod.api.client.gfx.imgui.ImGuiAccessor;
import net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider;
import net.labymod.api.client.gui.screen.ScreenService;
import net.labymod.api.client.gui.screen.widget.window.ScreenWindowManagement;
import net.labymod.api.client.gui.screen.widget.OverlappingTranslator;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler;
import net.labymod.api.client.gui.screen.theme.ThemeFileFinder;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.client.gui.screen.theme.ThemeRendererParser;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.client.gui.screen.game.GameScreenRegistry;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.key.HotkeyService;
import net.labymod.api.client.gui.screen.ScreenWrapper;
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
import net.labymod.api.client.render.gl.GlStateBridge;
import net.labymod.api.client.render.gl.GlInformation;
import net.labymod.api.client.render.PlayerHeartRenderer;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.TextColorStripper;
import net.labymod.api.client.render.font.ComponentRendererBuilder;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.render.font.text.glyph.GlyphProvider;
import net.labymod.api.client.render.font.text.StringFormatter;
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
import net.labymod.api.client.render.matrix.StackProviderFactory;
import net.labymod.api.client.render.matrix.EmptyStack;
import net.labymod.api.client.render.vertex.OldVertexFormatRegistry;
import net.labymod.api.client.render.vertex.shard.shards.layer.ViewOffsetZLayer;
import net.labymod.api.client.render.HotbarRenderer;
import net.labymod.api.client.render.ExperienceBarRenderer;
import net.labymod.api.client.render.RenderConstants;
import net.labymod.api.client.render.StatusIconRenderer;
import net.labymod.api.client.render.model.ModelUploader;
import net.labymod.api.client.render.model.ModelRenderer;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.crash.GameCrashReport;
import net.labymod.api.client.crash.CrashReportAppenderIterable;
import net.labymod.api.client.GameTickProvider;
import net.labymod.api.client.particle.ParticleController;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.api.client.session.MinecraftAuthenticator;
import net.labymod.api.client.entity.EntityPoseMapper;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.client.entity.player.badge.BadgeRegistry;
import net.labymod.api.client.entity.player.interaction.InteractionMenuRegistry;
import net.labymod.api.client.zoom.ZoomController;
import net.labymod.api.client.world.signobject.SignObjectRegistry;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.lighting.LightingLayerMapper;
import net.labymod.api.client.world.BossBarRegistry;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.phys.hit.HitResultController;
import net.labymod.api.client.world.object.WorldObjectRegistry;
import net.labymod.api.client.world.canvas.CanvasFactory;
import net.labymod.api.client.world.canvas.CanvasRegistry;
import net.labymod.api.client.world.block.Blocks;
import net.labymod.api.client.world.block.BlockColorProvider;
import net.labymod.api.client.chat.ChatExecutor;
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
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.api.client.resources.pack.ResourcePackScanner;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.client.resources.ResourcesReloadWatcher;
import net.labymod.api.client.resources.texture.concurrent.AsynchronousTextureUploader;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.resources.texture.GameImageTexture;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.sound.MinecraftSounds;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.client.network.server.ServerAddressResolver;
import net.labymod.api.client.network.server.global.PublicServerListService;
import net.labymod.api.client.network.server.ServerPinger;
import net.labymod.api.client.network.server.payload.PayloadRegistry;
import net.labymod.api.client.network.server.lan.LanServerDetector;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.client.sound.SoundService;
import net.labymod.api.client.util.SystemInfo;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.format.numbers.NumberFormatMapper;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.platform.launcher.LauncherService;
import net.labymod.api.platform.launcher.MinecraftLauncherFactory;
import net.labymod.api.serverapi.LabyModProtocolService;
import net.labymod.api.serverapi.LabyProtocolApi;
import net.labymod.api.addon.integration.AddonIntegrationService;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.server.IntegratedServer;
import net.labymod.api.localization.Internationalization;
import net.labymod.api.user.GameUserService;
import net.labymod.api.user.group.GroupService;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.api.revision.RevisionRegistry;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatGlobalSettingHandler;
import net.labymod.api.configuration.settings.widget.WidgetRegistry;
import net.labymod.api.configuration.settings.SwitchableHandlerRegistry;
import net.labymod.api.configuration.converter.LegacyConfigConverter;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.model.MojangModelService;
import net.labymod.api.util.markdown.MarkdownParser;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.api.util.FileDialogs;
import net.labymod.api.util.JsonFileCache;
import net.labymod.api.util.math.GameMathMapper;
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
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.method.SubscribeMethodResolver;
import net.labymod.api.client.render.vertex.shard.shards.output.ItemEntityOutputRenderShard;
import net.labymod.api.reference.SingletonReference;
import net.labymod.api.client.gfx.pipeline.renderer.level.SurfaceRenderer;
import net.labymod.api.client.gfx.imgui.window.DocumentHandler;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.client.gui.screen.activity.ActivityController;
import net.labymod.api.client.gui.lss.property.PropertyRegistry;
import net.labymod.api.client.render.vertex.shard.shards.OutputRenderShard;
import net.labymod.api.client.render.statistics.FrameTimer;
import net.labymod.api.client.entity.player.CameraLockController;
import net.labymod.api.client.component.serializer.gson.GsonComponentSerializer;
import net.labymod.api.modloader.ModLoaderRegistry;
import net.labymod.api.adventure.LegacyChatFormatting;
import net.labymod.api.reference.Reference;
import net.labymod.api.reference.ReferenceStorageAccessor;

public class ReferenceStorage implements ReferenceStorageAccessor
{
    private final Reference<LegacyChatFormatting> legacyChatFormattingReference;
    private final Reference<ModLoaderRegistry> modLoaderRegistryReference;
    private final Reference<GsonComponentSerializer> gsonComponentSerializerReference;
    private final Reference<CameraLockController> cameraLockControllerReference;
    private final Reference<FrameTimer> frameTimerReference;
    private final Reference<OutputRenderShard> outputRenderShardReference;
    private final Reference<PropertyRegistry> propertyRegistryReference;
    private final Reference<ActivityController> activityControllerReference;
    private final Reference<WidgetConverterRegistry> widgetConverterRegistryReference;
    private final Reference<DocumentHandler> documentHandlerReference;
    private final Reference<SurfaceRenderer> surfaceRendererReference;
    
    public ReferenceStorage() {
        this.legacyChatFormattingReference = new SingletonReference<LegacyChatFormatting>(LegacyChatFormatting.class, () -> new LegacyChatFormatting());
        this.modLoaderRegistryReference = new SingletonReference<ModLoaderRegistry>(ModLoaderRegistry.class, () -> new ModLoaderRegistry());
        this.gsonComponentSerializerReference = new SingletonReference<GsonComponentSerializer>(GsonComponentSerializer.class, () -> new GsonComponentSerializer());
        this.cameraLockControllerReference = new SingletonReference<CameraLockController>(CameraLockController.class, () -> new CameraLockController(this.eventBus()));
        this.frameTimerReference = new SingletonReference<FrameTimer>(FrameTimer.class, () -> new FrameTimer());
        this.outputRenderShardReference = new SingletonReference<OutputRenderShard>(OutputRenderShard.class, () -> new ItemEntityOutputRenderShard(this.outputRenderShardOutputRender("item_entity_output_render")));
        this.propertyRegistryReference = new SingletonReference<PropertyRegistry>(PropertyRegistry.class, () -> new PropertyRegistry(this.eventBus()));
        this.activityControllerReference = new SingletonReference<ActivityController>(ActivityController.class, () -> new ActivityController());
        this.widgetConverterRegistryReference = new SingletonReference<WidgetConverterRegistry>(WidgetConverterRegistry.class, () -> new WidgetConverterRegistry());
        this.documentHandlerReference = new SingletonReference<DocumentHandler>(DocumentHandler.class, () -> new DocumentHandler());
        this.surfaceRendererReference = new SingletonReference<SurfaceRenderer>(SurfaceRenderer.class, () -> new SurfaceRenderer(this.labyAPI(), this.gfxRenderPipeline()));
    }
    
    @NotNull
    public SubscribeMethodResolver subscribeMethodResolver() {
        return null;
    }
    
    @NotNull
    public RenderFirstPersonItemInHandEvent.AnimationTypeMapper renderFirstPersonItemInHandEventAnimationTypeMapper() {
        return null;
    }
    
    @NotNull
    public ScreenTreeTopRegistry screenTreeTopRegistry() {
        return null;
    }
    
    @NotNull
    public EventBus eventBus() {
        return null;
    }
    
    @NotNull
    public NotificationController notificationController() {
        return null;
    }
    
    @NotNull
    public AnnouncementService announcementService() {
        return null;
    }
    
    @NotNull
    public TokenStorage tokenStorage() {
        return null;
    }
    
    @NotNull
    public LabyConnect labyConnect() {
        return null;
    }
    
    @NotNull
    public AttachmentParser attachmentParser() {
        return null;
    }
    
    @NotNull
    public MappingService mappingService() {
        return null;
    }
    
    @NotNull
    public MixinRemapperInjector mixinRemapperInjector() {
        return null;
    }
    
    @NotNull
    public WebResolver webResolver() {
        return null;
    }
    
    @NotNull
    public GameMathMapper gameMathMapper() {
        return null;
    }
    
    @NotNull
    public JsonFileCache.Factory jsonFileCacheFactory() {
        return null;
    }
    
    @NotNull
    public FileDialogs fileDialogs() {
        return null;
    }
    
    @NotNull
    public FunctionMemoizeStorage functionMemoizeStorage() {
        return null;
    }
    
    @NotNull
    public Logging.Factory loggingFactory() {
        return null;
    }
    
    @NotNull
    public MarkdownParser markdownParser() {
        return null;
    }
    
    @NotNull
    public MojangModelService mojangModelService() {
        return null;
    }
    
    @NotNull
    public MojangTextureService mojangTextureService() {
        return null;
    }
    
    @NotNull
    public LegacyConfigConverter legacyConfigConverter() {
        return null;
    }
    
    @NotNull
    public SwitchableHandlerRegistry switchableHandlerRegistry() {
        return null;
    }
    
    @NotNull
    public WidgetRegistry widgetRegistry() {
        return null;
    }
    
    @NotNull
    public GeneralChatGlobalSettingHandler generalChatGlobalSettingHandler() {
        return null;
    }
    
    @NotNull
    public RevisionRegistry revisionRegistry() {
        return null;
    }
    
    @NotNull
    public PermissionRegistry permissionRegistry() {
        return null;
    }
    
    @NotNull
    public GroupService groupService() {
        return null;
    }
    
    @NotNull
    public GameUserService gameUserService() {
        return null;
    }
    
    @NotNull
    public Internationalization internationalization() {
        return null;
    }
    
    @NotNull
    public LegacyChatFormatting legacyChatFormatting() {
        return (LegacyChatFormatting)this.legacyChatFormattingReference.get();
    }
    
    @NotNull
    public ModLoaderRegistry modLoaderRegistry() {
        return (ModLoaderRegistry)this.modLoaderRegistryReference.get();
    }
    
    @NotNull
    public IntegratedServer integratedServer() {
        return null;
    }
    
    @NotNull
    public LabyNetController labyNetController() {
        return null;
    }
    
    @NotNull
    public AddonIntegrationService addonIntegrationService() {
        return null;
    }
    
    @NotNull
    public LabyProtocolApi labyProtocolApi() {
        return null;
    }
    
    @NotNull
    public LabyModProtocolService labyModProtocolService() {
        return null;
    }
    
    @NotNull
    public MinecraftLauncherFactory minecraftLauncherFactory() {
        return null;
    }
    
    @NotNull
    public LauncherService launcherService() {
        return null;
    }
    
    @NotNull
    public GsonComponentSerializer gsonComponentSerializer() {
        return (GsonComponentSerializer)this.gsonComponentSerializerReference.get();
    }
    
    @NotNull
    public ComponentService componentService() {
        return null;
    }
    
    @Nullable
    public NumberFormatMapper getNumberFormatMapper() {
        return null;
    }
    
    @NotNull
    public SystemInfo systemInfo() {
        return null;
    }
    
    @NotNull
    public SoundService soundService() {
        return null;
    }
    
    @NotNull
    public ServerController serverController() {
        return null;
    }
    
    @NotNull
    public LanServerDetector lanServerDetector() {
        return null;
    }
    
    @NotNull
    public PayloadRegistry payloadRegistry() {
        return null;
    }
    
    @NotNull
    public ServerPinger serverPinger() {
        return null;
    }
    
    @NotNull
    public PublicServerListService publicServerListService() {
        return null;
    }
    
    @NotNull
    public ServerAddressResolver serverAddressResolver() {
        return null;
    }
    
    @NotNull
    public ResourceLocationFactory resourceLocationFactory() {
        return null;
    }
    
    @NotNull
    public MinecraftSounds minecraftSounds() {
        return null;
    }
    
    @NotNull
    public Resources resources() {
        return null;
    }
    
    @NotNull
    public GameImageTexture.Factory gameImageTextureFactory() {
        return null;
    }
    
    @NotNull
    public TextureRepository textureRepository() {
        return null;
    }
    
    @NotNull
    public GameImageProvider gameImageProvider() {
        return null;
    }
    
    @NotNull
    public AsynchronousTextureUploader asynchronousTextureUploader() {
        return null;
    }
    
    @NotNull
    public ResourcesReloadWatcher resourcesReloadWatcher() {
        return null;
    }
    
    @NotNull
    public ResourcePack.Factory resourcePackFactory() {
        return null;
    }
    
    @NotNull
    public ResourcePackScanner resourcePackScanner() {
        return null;
    }
    
    @NotNull
    public ResourcePackRepository resourcePackRepository() {
        return null;
    }
    
    @NotNull
    public ResourceTransformerRegistry resourceTransformerRegistry() {
        return null;
    }
    
    @NotNull
    public ResourceTransformer resourceTransformer(final String key) {
        return null;
    }
    
    @NotNull
    public AnimatedResourceLocation.Builder animatedResourceLocationBuilder() {
        return null;
    }
    
    @NotNull
    public CommandService commandService() {
        return null;
    }
    
    @NotNull
    public ChatController chatController() {
        return null;
    }
    
    @NotNull
    public ChatMessage.Builder chatMessageBuilder() {
        return null;
    }
    
    @NotNull
    public AdvancedChatController advancedChatController() {
        return null;
    }
    
    @NotNull
    public DynamicChatFilterService dynamicChatFilterService() {
        return null;
    }
    
    @NotNull
    public FilterChatService filterChatService() {
        return null;
    }
    
    @NotNull
    public ChatInputRegistry chatInputRegistry() {
        return null;
    }
    
    @NotNull
    public ChatProvider chatProvider() {
        return null;
    }
    
    @NotNull
    public ChatSymbolRegistry chatSymbolRegistry() {
        return null;
    }
    
    @NotNull
    public AutoTextService autoTextService() {
        return null;
    }
    
    @NotNull
    public ChatExecutor chatExecutor() {
        return null;
    }
    
    @NotNull
    public BlockColorProvider blockColorProvider() {
        return null;
    }
    
    @NotNull
    public Blocks blocks() {
        return null;
    }
    
    @NotNull
    public CanvasRegistry canvasRegistry() {
        return null;
    }
    
    @NotNull
    public CanvasFactory canvasFactory() {
        return null;
    }
    
    @NotNull
    public WorldObjectRegistry worldObjectRegistry() {
        return null;
    }
    
    @NotNull
    public HitResultController hitResultController() {
        return null;
    }
    
    @NotNull
    public ClientWorld clientWorld() {
        return null;
    }
    
    @NotNull
    public BossBarRegistry bossBarRegistry() {
        return null;
    }
    
    @NotNull
    public LightingLayerMapper lightingLayerMapper() {
        return null;
    }
    
    @NotNull
    public ItemStackFactory itemStackFactory() {
        return null;
    }
    
    @NotNull
    public SignObjectRegistry signObjectRegistry() {
        return null;
    }
    
    @NotNull
    public ZoomController zoomController() {
        return null;
    }
    
    @NotNull
    public InteractionMenuRegistry interactionMenuRegistry() {
        return null;
    }
    
    @NotNull
    public CameraLockController cameraLockController() {
        return (CameraLockController)this.cameraLockControllerReference.get();
    }
    
    @NotNull
    public BadgeRegistry badgeRegistry() {
        return null;
    }
    
    @NotNull
    public TagRegistry tagRegistry() {
        return null;
    }
    
    @NotNull
    public EntityPoseMapper entityPoseMapper() {
        return null;
    }
    
    @NotNull
    public MinecraftAuthenticator minecraftAuthenticator() {
        return null;
    }
    
    @NotNull
    public SessionAccessor sessionAccessor() {
        return null;
    }
    
    @NotNull
    public MinecraftServices minecraftServices() {
        return null;
    }
    
    @NotNull
    public ParticleController particleController() {
        return null;
    }
    
    @NotNull
    public GameTickProvider gameTickProvider() {
        return null;
    }
    
    @NotNull
    public CrashReportAppenderIterable crashReportAppenderIterable() {
        return null;
    }
    
    @NotNull
    public GameCrashReport.Factory gameCrashReportFactory() {
        return null;
    }
    
    @NotNull
    public RenderPipeline renderPipeline() {
        return null;
    }
    
    @NotNull
    public ModelService modelService() {
        return null;
    }
    
    @NotNull
    public ModelRenderer modelRenderer() {
        return null;
    }
    
    @NotNull
    public ModelUploader modelUploader() {
        return null;
    }
    
    @NotNull
    public StatusIconRenderer statusIconRenderer() {
        return null;
    }
    
    @NotNull
    public FrameTimer frameTimer() {
        return (FrameTimer)this.frameTimerReference.get();
    }
    
    @NotNull
    public RenderConstants renderConstants() {
        return null;
    }
    
    @NotNull
    public ExperienceBarRenderer experienceBarRenderer() {
        return null;
    }
    
    @NotNull
    public HotbarRenderer hotbarRenderer() {
        return null;
    }
    
    @NotNull
    public OutputRenderShard outputRenderShard() {
        return (OutputRenderShard)this.outputRenderShardReference.get();
    }
    
    @NotNull
    public OutputRenderShard.OutputRender outputRenderShardOutputRender(final String key) {
        return null;
    }
    
    @NotNull
    public ViewOffsetZLayer viewOffsetZLayer() {
        return null;
    }
    
    @NotNull
    public OldVertexFormatRegistry oldVertexFormatRegistry() {
        return null;
    }
    
    @NotNull
    public EmptyStack emptyStack() {
        return null;
    }
    
    @NotNull
    public StackProviderFactory stackProviderFactory() {
        return null;
    }
    
    @NotNull
    public MojangShaderRegistry mojangShaderRegistry() {
        return null;
    }
    
    @NotNull
    public ShaderInstance shaderInstance(final String key) {
        return null;
    }
    
    @NotNull
    public ShaderProvider shaderProvider() {
        return null;
    }
    
    @NotNull
    public LineRenderContext lineRenderContext() {
        return null;
    }
    
    @NotNull
    public RectangleRenderContext rectangleRenderContext() {
        return null;
    }
    
    @NotNull
    public ResourceRenderContext resourceRenderContext() {
        return null;
    }
    
    @NotNull
    public RenderContexts renderContexts() {
        return null;
    }
    
    @NotNull
    public TriangleRenderContext triangleRenderContext() {
        return null;
    }
    
    @NotNull
    public HeartRenderer heartRenderer() {
        return null;
    }
    
    @NotNull
    public PlayerHeadRenderer playerHeadRenderer() {
        return null;
    }
    
    @NotNull
    public ResourceRenderer resourceRenderer() {
        return null;
    }
    
    @NotNull
    public RectangleRenderer rectangleRenderer() {
        return null;
    }
    
    @NotNull
    public HoverBackgroundEffect hoverBackgroundEffect(final String key) {
        return null;
    }
    
    @NotNull
    public HoverBackgroundEffectRenderer hoverBackgroundEffectRenderer() {
        return null;
    }
    
    @NotNull
    public CircleRenderer circleRenderer() {
        return null;
    }
    
    @NotNull
    public VanillaWindowBuilder vanillaWindowBuilder() {
        return null;
    }
    
    @NotNull
    public RoundedGeometryBuilder roundedGeometryBuilder() {
        return null;
    }
    
    @NotNull
    public BatchResourceRenderer batchResourceRenderer() {
        return null;
    }
    
    @NotNull
    public BatchRectangleRenderer batchRectangleRenderer() {
        return null;
    }
    
    @NotNull
    public TriangleRenderer triangleRenderer() {
        return null;
    }
    
    @NotNull
    public BlurRenderer blurRenderer() {
        return null;
    }
    
    @NotNull
    public TextRendererProvider textRendererProvider() {
        return null;
    }
    
    @NotNull
    public TextRenderer textRenderer(final String key) {
        return null;
    }
    
    @NotNull
    public StringFormatter stringFormatter() {
        return null;
    }
    
    @NotNull
    public GlyphProvider glyphProvider(final String key) {
        return null;
    }
    
    @NotNull
    public ComponentMapper componentMapper() {
        return null;
    }
    
    @NotNull
    public ComponentRendererBuilder componentRendererBuilder() {
        return null;
    }
    
    @NotNull
    public TextColorStripper textColorStripper() {
        return null;
    }
    
    @NotNull
    public ComponentRenderer componentRenderer() {
        return null;
    }
    
    @NotNull
    public PlayerHeartRenderer playerHeartRenderer() {
        return null;
    }
    
    @NotNull
    public GlInformation glInformation() {
        return null;
    }
    
    @NotNull
    public GlStateBridge glStateBridge() {
        return null;
    }
    
    @NotNull
    public PropertyRegistry propertyRegistry() {
        return (PropertyRegistry)this.propertyRegistryReference.get();
    }
    
    @NotNull
    public LinkMetaLoader linkMetaLoader() {
        return null;
    }
    
    @NotNull
    public StyleSheetLoader styleSheetLoader() {
        return null;
    }
    
    @NotNull
    public LssInjector lssInjector() {
        return null;
    }
    
    @NotNull
    public TypeParser typeParser() {
        return null;
    }
    
    @NotNull
    public WidgetModifier widgetModifier() {
        return null;
    }
    
    @NotNull
    public PseudoClassRegistry pseudoClassRegistry() {
        return null;
    }
    
    @NotNull
    public PostProcessor postProcessor() {
        return null;
    }
    
    @NotNull
    public ElementParser elementParser() {
        return null;
    }
    
    @NotNull
    public FunctionRegistry functionRegistry() {
        return null;
    }
    
    @NotNull
    public StyleHelper styleHelper() {
        return null;
    }
    
    @NotNull
    public PingIconRegistry pingIconRegistry() {
        return null;
    }
    
    @NotNull
    public HudWidgetCategoryRegistry hudWidgetCategoryRegistry() {
        return null;
    }
    
    @NotNull
    public HudWidgetRegistry hudWidgetRegistry() {
        return null;
    }
    
    @NotNull
    public NavigationRegistry navigationRegistry() {
        return null;
    }
    
    @NotNull
    public TooltipService tooltipService() {
        return null;
    }
    
    @NotNull
    public EmbedFactory embedFactory() {
        return null;
    }
    
    @NotNull
    public OverlayRegistry overlayRegistry() {
        return null;
    }
    
    @NotNull
    public IngameActivityOverlay ingameActivityOverlay() {
        return null;
    }
    
    @NotNull
    public ChatAccessor chatAccessor() {
        return null;
    }
    
    @NotNull
    public ActivityController activityController() {
        return (ActivityController)this.activityControllerReference.get();
    }
    
    @NotNull
    public ScreenWrapper.Factory screenWrapperFactory() {
        return null;
    }
    
    @NotNull
    public HotkeyService hotkeyService() {
        return null;
    }
    
    @NotNull
    public KeyMapper keyMapper() {
        return null;
    }
    
    @NotNull
    public GameScreenRegistry gameScreenRegistry() {
        return null;
    }
    
    @NotNull
    public ScreenCustomFontStack screenCustomFontStack() {
        return null;
    }
    
    @NotNull
    public ThemeRendererParser themeRendererParser() {
        return null;
    }
    
    @NotNull
    public ThemeService themeService() {
        return null;
    }
    
    @NotNull
    public ThemeFileFinder themeFileFinder() {
        return null;
    }
    
    @NotNull
    public ScreenOverlayHandler screenOverlayHandler() {
        return null;
    }
    
    @NotNull
    public WidgetConverterRegistry widgetConverterRegistry() {
        return (WidgetConverterRegistry)this.widgetConverterRegistryReference.get();
    }
    
    @NotNull
    public OverlappingTranslator overlappingTranslator() {
        return null;
    }
    
    @NotNull
    public ScreenWindowManagement screenWindowManagement() {
        return null;
    }
    
    @NotNull
    public ScreenService screenService() {
        return null;
    }
    
    @NotNull
    public ImGuiTypeProvider imGuiTypeProvider() {
        return null;
    }
    
    @NotNull
    public ImGuiAccessor imGuiAccessor() {
        return null;
    }
    
    @NotNull
    public ControlEntryRegistry controlEntryRegistry() {
        return null;
    }
    
    @NotNull
    public DocumentHandler documentHandler() {
        return (DocumentHandler)this.documentHandlerReference.get();
    }
    
    @NotNull
    public VertexFormatRegistry vertexFormatRegistry() {
        return null;
    }
    
    @NotNull
    public VertexFormat.Builder vertexFormatBuilder() {
        return null;
    }
    
    @NotNull
    public ShaderProgramManager shaderProgramManager() {
        return null;
    }
    
    @NotNull
    public ShaderProgram.Factory shaderProgramFactory() {
        return null;
    }
    
    @NotNull
    public LightmapTexture lightmapTexture() {
        return null;
    }
    
    @NotNull
    public RenderEnvironmentContext renderEnvironmentContext() {
        return null;
    }
    
    @NotNull
    public Blaze3DShaderUniformPipeline blaze3DShaderUniformPipeline() {
        return null;
    }
    
    @NotNull
    public FrameContextRegistry frameContextRegistry() {
        return null;
    }
    
    @NotNull
    public SurfaceRenderer surfaceRenderer() {
        return (SurfaceRenderer)this.surfaceRendererReference.get();
    }
    
    @NotNull
    public ShaderPipeline shaderPipeline() {
        return null;
    }
    
    @NotNull
    public Blaze3DGlStatePipeline blaze3DGlStatePipeline() {
        return null;
    }
    
    @NotNull
    public MinecraftAtlases minecraftAtlases() {
        return null;
    }
    
    @NotNull
    public AtlasRegistry atlasRegistry() {
        return null;
    }
    
    @NotNull
    public GFXRenderPipeline gfxRenderPipeline() {
        return null;
    }
    
    @NotNull
    public StandardBlaze3DRenderTypes standardBlaze3DRenderTypes() {
        return null;
    }
    
    @NotNull
    public GFXBridge gfxBridge(final String key) {
        return null;
    }
    
    @NotNull
    public BufferObject.Factory bufferObjectFactory() {
        return null;
    }
    
    @NotNull
    public VertexArrayObject.Factory vertexArrayObjectFactory() {
        return null;
    }
    
    @NotNull
    public OptiFine optiFine() {
        return null;
    }
    
    @NotNull
    public ThirdPartyService thirdPartyService() {
        return null;
    }
    
    @NotNull
    public DiscordApp discordApp() {
        return null;
    }
    
    @NotNull
    public SentryService sentryService() {
        return null;
    }
    
    @NotNull
    public NBTFactory nbtFactory() {
        return null;
    }
    
    @NotNull
    public AccountService accountService() {
        return null;
    }
    
    @NotNull
    public LabyAPI labyAPI() {
        return null;
    }
}
