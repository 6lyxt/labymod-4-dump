// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.generated;

import net.labymod.core.client.font.text.FontSetGlyphProvider;
import net.labymod.core.client.util.buffersource.BufferSourceGui;
import net.labymod.api.client.world.block.Blocks;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.v1_21_4.client.gui.background.VersionedPanoramaRenderer;
import net.labymod.v1_21_4.client.session.user.VersionedUserFactory;
import net.labymod.v1_21_4.client.multiplayer.server.VersionedClientNetworkPacketListener;
import net.labymod.v1_21_4.client.chat.VersionedChatModifier;
import net.labymod.v1_21_4.client.resources.texture.VersionedRefreshableTextureFactory;
import net.labymod.v1_21_4.nbt.VersionedNBTFactory;
import net.labymod.v1_21_4.client.gfx.VersionedGFXBridge;
import net.labymod.core.client.gfx.DevelopmentGFXBridge;
import net.labymod.v1_21_4.client.gfx.pipeline.blaze3d.program.VersionedStandardBlaze3DRenderTypes;
import net.labymod.v1_21_4.client.gfx.pipeline.texture.atlas.VersionedMinecraftAtlases;
import net.labymod.v1_21_4.client.gfx.pipeline.VersionedBlaze3DGlStatePipeline;
import net.labymod.v1_21_4.client.gfx.pipeline.VersionedBlaze3DShaderUniformPipeline;
import net.labymod.v1_21_4.client.gfx.texture.VersionedLightmapTexture;
import net.labymod.v1_21_4.client.gui.screen.key.mapper.VersionedKeyMapper;
import net.labymod.v1_21_4.client.gui.screen.VersionedScreenWrapperFactory;
import net.labymod.v1_21_4.client.render.gl.VersionedGlStateBridge;
import net.labymod.v1_21_4.client.render.gl.VersionedGlInformation;
import net.labymod.v1_21_4.client.renderer.VersionedPlayerHeartRenderer;
import net.labymod.api.reference.DynamicReference;
import net.labymod.v1_21_4.client.font.component.VersionedComponentMapper;
import net.labymod.core.client.render.font.text.msdf.MSDFGlyphProvider;
import net.labymod.v1_21_4.client.font.text.VersionedStringFormatter;
import net.labymod.v1_21_4.client.font.text.vanilla.VanillaTextRenderer;
import net.labymod.core.client.render.font.text.msdf.MSDFTextRenderer;
import net.labymod.core.client.render.draw.hover.VanillaHoverBackgroundEffect;
import net.labymod.core.client.render.draw.hover.FancyHoverBackgroundEffect;
import net.labymod.core.client.render.draw.shader.CircleShaderInstance;
import net.labymod.core.client.render.draw.shader.FrameBufferObjectShaderInstance;
import net.labymod.core.client.render.draw.shader.FrameBufferObjectStencilShaderInstance;
import net.labymod.core.client.render.shader.program.RenderPhase3DEnvironmentTextureShaderInstance;
import net.labymod.v1_21_4.client.render.matrix.VersionedStackProviderFactory;
import net.labymod.v1_21_4.client.render.system.shards.layering.VersionedViewOffsetZLayer;
import net.labymod.v1_21_4.client.render.system.shards.output.ItemEntityOutputRender;
import net.labymod.v1_21_4.client.render.VersionedRenderConstants;
import net.labymod.v1_21_4.client.render.VersionedRenderPipeline;
import net.labymod.v1_21_4.client.crash.VersionedGameCrashReportFactory;
import net.labymod.v1_21_4.client.particle.VersionedParticleController;
import net.labymod.v1_21_4.client.session.VersionedSessionAccessor;
import net.labymod.v1_21_4.client.session.VersionedMinecraftAuthenticator;
import net.labymod.v1_21_4.client.entity.VersionedEntityPoseMapper;
import net.labymod.v1_21_4.client.world.item.VersionedItemStackFactory;
import net.labymod.v1_21_4.client.world.lighting.VersionedLightingLayerMapper;
import net.labymod.v1_21_4.client.world.VersionedBossBarRegistry;
import net.labymod.v1_21_4.client.world.VersionedClientWorld;
import net.labymod.v1_21_4.client.world.phys.hit.VersionedHitResultController;
import net.labymod.v1_21_4.client.world.block.VersionedBlockColorProvider;
import net.labymod.v1_21_4.client.chat.VersionedChatExecutor;
import net.labymod.core.client.resources.transform.transformer.customhitcolor.DamageOverlayRenderTypeArmorCutoutNoCullJsonResourceTransformer;
import net.labymod.core.client.resources.transform.transformer.customhitcolor.DamageOverlayRenderTypeArmorCutoutNoCullFragmentShaderResourceTransformer;
import net.labymod.core.client.resources.transform.transformer.customhitcolor.DamageOverlayRenderTypeArmorCutoutNoCullVertexShaderResourceTransformer;
import net.labymod.v1_21_4.client.resources.pack.VersionedResourcePackRepository;
import net.labymod.v1_21_4.client.resources.pack.VersionedResourcePackScanner;
import net.labymod.v1_21_4.client.resources.texture.NativeGameImageProvider;
import net.labymod.v1_21_4.client.resources.texture.VersionedTextureRepository;
import net.labymod.v1_21_4.client.resources.texture.VersionedGameImageTextureFactory;
import net.labymod.v1_21_4.client.resources.sound.VersionedMinecraftSounds;
import net.labymod.v1_21_4.client.resources.VersionedResourceLocationFactory;
import net.labymod.v1_21_4.server.VersionedServerAddressResolver;
import net.labymod.v1_21_4.client.multiplayer.server.VersionedServerController;
import net.labymod.v1_21_4.client.util.VersionedSystemInfo;
import net.labymod.api.reference.NullableSingletonReference;
import net.labymod.v1_21_4.client.component.format.numbers.VersionedNumberFormatMapper;
import net.labymod.v1_21_4.client.component.VersionedComponentService;
import net.labymod.v1_21_4.server.VersionedIntegratedServer;
import net.labymod.v1_21_4.mojang.texture.VersionedMojangTextureService;
import net.labymod.v1_21_4.client.util.math.VersionedGameMathMapper;
import net.labymod.api.reference.SingletonReference;
import net.labymod.v1_21_4.client.render.item.VersionedAnimationTypeMapper;
import net.labymod.core.client.gui.background.panorama.PanoramaRenderer;
import net.labymod.core.client.session.UserFactory;
import net.labymod.core.client.multiplayer.ClientNetworkPacketListener;
import net.labymod.core.client.chat.InternalChatModifier;
import net.labymod.core.client.resources.texture.concurrent.RefreshableTextureFactory;
import net.labymod.api.nbt.NBTFactory;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.StandardBlaze3DRenderTypes;
import net.labymod.api.client.gfx.pipeline.texture.atlas.MinecraftAtlases;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.client.gfx.pipeline.Blaze3DShaderUniformPipeline;
import net.labymod.api.client.gfx.texture.LightmapTexture;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.render.gl.GlStateBridge;
import net.labymod.api.client.render.gl.GlInformation;
import net.labymod.api.client.render.PlayerHeartRenderer;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.render.font.text.glyph.GlyphProvider;
import net.labymod.api.client.render.font.text.StringFormatter;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.client.render.shader.program.ShaderInstance;
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
import net.labymod.api.client.world.block.BlockColorProvider;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.resources.transform.ResourceTransformer;
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
import net.labymod.api.client.component.format.numbers.NumberFormatMapper;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.server.IntegratedServer;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.util.math.GameMathMapper;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.reference.Reference;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.api.reference.ReferenceStorageAccessor;
import net.labymod.gfx_lwjgl3.generated.ReferenceStorage;

@AutoService(value = ReferenceStorageAccessor.class, versionSpecific = true)
public class VersionedReferenceStorage extends ReferenceStorage implements ReferenceStorageAccessor
{
    private final Reference<RenderFirstPersonItemInHandEvent.AnimationTypeMapper> renderFirstPersonItemInHandEventAnimationTypeMapperReference;
    private final Reference<GameMathMapper> gameMathMapperReference;
    private final Reference<MojangTextureService> mojangTextureServiceReference;
    private final Reference<IntegratedServer> integratedServerReference;
    private final Reference<ComponentService> componentServiceReference;
    private final Reference<NumberFormatMapper> numberFormatMapperReference;
    private final Reference<SystemInfo> systemInfoReference;
    private final Reference<ServerController> serverControllerReference;
    private final Reference<ServerAddressResolver> serverAddressResolverReference;
    private final Reference<ResourceLocationFactory> resourceLocationFactoryReference;
    private final Reference<MinecraftSounds> minecraftSoundsReference;
    private final Reference<GameImageTexture.Factory> gameImageTextureFactoryReference;
    private final Reference<TextureRepository> textureRepositoryReference;
    private final Reference<GameImageProvider> gameImageProviderReference;
    private final Reference<ResourcePackScanner> resourcePackScannerReference;
    private final Reference<ResourcePackRepository> resourcePackRepositoryReference;
    private final Reference<ResourceTransformer> resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_vertex_shader;
    private final Reference<ResourceTransformer> resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_fragment_shader;
    private final Reference<ResourceTransformer> resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_json;
    private final Reference<ChatExecutor> chatExecutorReference;
    private final Reference<BlockColorProvider> blockColorProviderReference;
    private final Reference<HitResultController> hitResultControllerReference;
    private final Reference<ClientWorld> clientWorldReference;
    private final Reference<BossBarRegistry> bossBarRegistryReference;
    private final Reference<LightingLayerMapper> lightingLayerMapperReference;
    private final Reference<ItemStackFactory> itemStackFactoryReference;
    private final Reference<EntityPoseMapper> entityPoseMapperReference;
    private final Reference<MinecraftAuthenticator> minecraftAuthenticatorReference;
    private final Reference<SessionAccessor> sessionAccessorReference;
    private final Reference<ParticleController> particleControllerReference;
    private final Reference<GameCrashReport.Factory> gameCrashReportFactoryReference;
    private final Reference<RenderPipeline> renderPipelineReference;
    private final Reference<RenderConstants> renderConstantsReference;
    private final Reference<OutputRenderShard.OutputRender> outputRenderShardOutputRenderReferenceitem_entity_output_render;
    private final Reference<ViewOffsetZLayer> viewOffsetZLayerReference;
    private final Reference<StackProviderFactory> stackProviderFactoryReference;
    private final Reference<ShaderInstance> shaderInstanceReferencerenderphase_3d_environment_texture;
    private final Reference<ShaderInstance> shaderInstanceReferenceframe_buffer_object_stencil_shader_instance;
    private final Reference<ShaderInstance> shaderInstanceReferenceframe_buffer_object_shader_instance;
    private final Reference<ShaderInstance> shaderInstanceReferencecircle_shader_instance;
    private final Reference<HoverBackgroundEffect> hoverBackgroundEffectReferencefancy_hover_effect;
    private final Reference<HoverBackgroundEffect> hoverBackgroundEffectReferencevanilla_hover_effect;
    private final Reference<TextRenderer> textRendererReferencemsdf_text_renderer;
    private final Reference<TextRenderer> textRendererReferencevanilla_text_renderer;
    private final Reference<StringFormatter> stringFormatterReference;
    private final Reference<GlyphProvider> glyphProviderReferencemsdf_glyph_provider;
    private final Reference<ComponentMapper> componentMapperReference;
    private final Reference<PlayerHeartRenderer> playerHeartRendererReference;
    private final Reference<GlInformation> glInformationReference;
    private final Reference<GlStateBridge> glStateBridgeReference;
    private final Reference<ScreenWrapper.Factory> screenWrapperFactoryReference;
    private final Reference<KeyMapper> keyMapperReference;
    private final Reference<LightmapTexture> lightmapTextureReference;
    private final Reference<Blaze3DShaderUniformPipeline> blaze3DShaderUniformPipelineReference;
    private final Reference<Blaze3DGlStatePipeline> blaze3DGlStatePipelineReference;
    private final Reference<MinecraftAtlases> minecraftAtlasesReference;
    private final Reference<StandardBlaze3DRenderTypes> standardBlaze3DRenderTypesReference;
    private final Reference<GFXBridge> gfxBridgeReferencegfx_bridge_development;
    private final Reference<GFXBridge> gfxBridgeReferencegfx_bridge_production;
    private final Reference<NBTFactory> nbtFactoryReference;
    private final Reference<RefreshableTextureFactory> refreshableTextureFactoryReference;
    private final Reference<InternalChatModifier> internalChatModifierReference;
    private final Reference<ClientNetworkPacketListener> clientNetworkPacketListenerReference;
    private final Reference<UserFactory> userFactoryReference;
    private final Reference<PanoramaRenderer> panoramaRendererReference;
    
    public VersionedReferenceStorage() {
        this.renderFirstPersonItemInHandEventAnimationTypeMapperReference = new SingletonReference<RenderFirstPersonItemInHandEvent.AnimationTypeMapper>(RenderFirstPersonItemInHandEvent.AnimationTypeMapper.class, () -> new VersionedAnimationTypeMapper());
        this.gameMathMapperReference = new SingletonReference<GameMathMapper>(GameMathMapper.class, () -> new VersionedGameMathMapper());
        this.mojangTextureServiceReference = new SingletonReference<MojangTextureService>(MojangTextureService.class, () -> new VersionedMojangTextureService(this.labyAPI()));
        this.integratedServerReference = new SingletonReference<IntegratedServer>(IntegratedServer.class, () -> new VersionedIntegratedServer());
        this.componentServiceReference = new SingletonReference<ComponentService>(ComponentService.class, () -> new VersionedComponentService());
        this.numberFormatMapperReference = new NullableSingletonReference<NumberFormatMapper>(NumberFormatMapper.class, () -> new VersionedNumberFormatMapper(this.componentMapper()));
        this.systemInfoReference = new SingletonReference<SystemInfo>(SystemInfo.class, () -> new VersionedSystemInfo());
        this.serverControllerReference = new SingletonReference<ServerController>(ServerController.class, () -> new VersionedServerController(this.labyAPI(), this.labyModProtocolService(), this.permissionRegistry(), this.resourceLocationFactory(), this.componentMapper()));
        this.serverAddressResolverReference = new SingletonReference<ServerAddressResolver>(ServerAddressResolver.class, () -> new VersionedServerAddressResolver());
        this.resourceLocationFactoryReference = new SingletonReference<ResourceLocationFactory>(ResourceLocationFactory.class, () -> new VersionedResourceLocationFactory());
        this.minecraftSoundsReference = new SingletonReference<MinecraftSounds>(MinecraftSounds.class, () -> new VersionedMinecraftSounds());
        this.gameImageTextureFactoryReference = new SingletonReference<GameImageTexture.Factory>(GameImageTexture.Factory.class, () -> new VersionedGameImageTextureFactory());
        this.textureRepositoryReference = new SingletonReference<TextureRepository>(TextureRepository.class, () -> new VersionedTextureRepository(this.labyAPI(), this.gameImageTextureFactory(), this.gameImageProvider()));
        this.gameImageProviderReference = new SingletonReference<GameImageProvider>(GameImageProvider.class, () -> new NativeGameImageProvider());
        this.resourcePackScannerReference = new SingletonReference<ResourcePackScanner>(ResourcePackScanner.class, () -> new VersionedResourcePackScanner());
        this.resourcePackRepositoryReference = new SingletonReference<ResourcePackRepository>(ResourcePackRepository.class, () -> new VersionedResourcePackRepository());
        this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_vertex_shader = new SingletonReference<ResourceTransformer>(ResourceTransformer.class, () -> new DamageOverlayRenderTypeArmorCutoutNoCullVertexShaderResourceTransformer());
        this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_fragment_shader = new SingletonReference<ResourceTransformer>(ResourceTransformer.class, () -> new DamageOverlayRenderTypeArmorCutoutNoCullFragmentShaderResourceTransformer());
        this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_json = new SingletonReference<ResourceTransformer>(ResourceTransformer.class, () -> new DamageOverlayRenderTypeArmorCutoutNoCullJsonResourceTransformer());
        this.chatExecutorReference = new SingletonReference<ChatExecutor>(ChatExecutor.class, () -> new VersionedChatExecutor(this.componentMapper()));
        this.blockColorProviderReference = new SingletonReference<BlockColorProvider>(BlockColorProvider.class, () -> new VersionedBlockColorProvider());
        this.hitResultControllerReference = new SingletonReference<HitResultController>(HitResultController.class, () -> new VersionedHitResultController());
        this.clientWorldReference = new SingletonReference<ClientWorld>(ClientWorld.class, () -> new VersionedClientWorld(this.resourceLocationFactory()));
        this.bossBarRegistryReference = new SingletonReference<BossBarRegistry>(BossBarRegistry.class, () -> new VersionedBossBarRegistry(this.labyAPI()));
        this.lightingLayerMapperReference = new SingletonReference<LightingLayerMapper>(LightingLayerMapper.class, () -> new VersionedLightingLayerMapper());
        this.itemStackFactoryReference = new SingletonReference<ItemStackFactory>(ItemStackFactory.class, () -> new VersionedItemStackFactory());
        this.entityPoseMapperReference = new SingletonReference<EntityPoseMapper>(EntityPoseMapper.class, () -> new VersionedEntityPoseMapper());
        this.minecraftAuthenticatorReference = new SingletonReference<MinecraftAuthenticator>(MinecraftAuthenticator.class, () -> new VersionedMinecraftAuthenticator());
        this.sessionAccessorReference = new SingletonReference<SessionAccessor>(SessionAccessor.class, () -> new VersionedSessionAccessor(this.userFactory()));
        this.particleControllerReference = new SingletonReference<ParticleController>(ParticleController.class, () -> new VersionedParticleController());
        this.gameCrashReportFactoryReference = new SingletonReference<GameCrashReport.Factory>(GameCrashReport.Factory.class, () -> new VersionedGameCrashReportFactory());
        this.renderPipelineReference = new SingletonReference<RenderPipeline>(RenderPipeline.class, () -> new VersionedRenderPipeline());
        this.renderConstantsReference = new SingletonReference<RenderConstants>(RenderConstants.class, () -> new VersionedRenderConstants());
        this.outputRenderShardOutputRenderReferenceitem_entity_output_render = new SingletonReference<OutputRenderShard.OutputRender>(OutputRenderShard.OutputRender.class, () -> new ItemEntityOutputRender());
        this.viewOffsetZLayerReference = new SingletonReference<ViewOffsetZLayer>(ViewOffsetZLayer.class, () -> new VersionedViewOffsetZLayer());
        this.stackProviderFactoryReference = new SingletonReference<StackProviderFactory>(StackProviderFactory.class, () -> new VersionedStackProviderFactory());
        this.shaderInstanceReferencerenderphase_3d_environment_texture = new SingletonReference<ShaderInstance>(ShaderInstance.class, () -> new RenderPhase3DEnvironmentTextureShaderInstance(this.shaderProvider()));
        this.shaderInstanceReferenceframe_buffer_object_stencil_shader_instance = new SingletonReference<ShaderInstance>(ShaderInstance.class, () -> new FrameBufferObjectStencilShaderInstance(this.shaderProvider()));
        this.shaderInstanceReferenceframe_buffer_object_shader_instance = new SingletonReference<ShaderInstance>(ShaderInstance.class, () -> new FrameBufferObjectShaderInstance(this.shaderProvider()));
        this.shaderInstanceReferencecircle_shader_instance = new SingletonReference<ShaderInstance>(ShaderInstance.class, () -> new CircleShaderInstance(this.shaderProvider()));
        this.hoverBackgroundEffectReferencefancy_hover_effect = new SingletonReference<HoverBackgroundEffect>(HoverBackgroundEffect.class, () -> new FancyHoverBackgroundEffect(this.labyAPI()));
        this.hoverBackgroundEffectReferencevanilla_hover_effect = new SingletonReference<HoverBackgroundEffect>(HoverBackgroundEffect.class, () -> new VanillaHoverBackgroundEffect(this.labyAPI()));
        this.textRendererReferencemsdf_text_renderer = new SingletonReference<TextRenderer>(TextRenderer.class, () -> new MSDFTextRenderer(this.glyphProvider("msdf_glyph_provider"), this.msdfResourceProvider(), this.stringFormatter()));
        this.textRendererReferencevanilla_text_renderer = new SingletonReference<TextRenderer>(TextRenderer.class, () -> new VanillaTextRenderer());
        this.stringFormatterReference = new SingletonReference<StringFormatter>(StringFormatter.class, () -> new VersionedStringFormatter());
        this.glyphProviderReferencemsdf_glyph_provider = new SingletonReference<GlyphProvider>(GlyphProvider.class, () -> new MSDFGlyphProvider(this.msdfResourceProvider()));
        this.componentMapperReference = new DynamicReference<ComponentMapper>(ComponentMapper.class, () -> new VersionedComponentMapper());
        this.playerHeartRendererReference = new DynamicReference<PlayerHeartRenderer>(PlayerHeartRenderer.class, () -> new VersionedPlayerHeartRenderer(this.labyAPI(), this.eventBus(), this.resourceRenderer()));
        this.glInformationReference = new SingletonReference<GlInformation>(GlInformation.class, () -> new VersionedGlInformation());
        this.glStateBridgeReference = new SingletonReference<GlStateBridge>(GlStateBridge.class, () -> new VersionedGlStateBridge());
        this.screenWrapperFactoryReference = new SingletonReference<ScreenWrapper.Factory>(ScreenWrapper.Factory.class, () -> new VersionedScreenWrapperFactory());
        this.keyMapperReference = new SingletonReference<KeyMapper>(KeyMapper.class, () -> new VersionedKeyMapper());
        this.lightmapTextureReference = new SingletonReference<LightmapTexture>(LightmapTexture.class, () -> new VersionedLightmapTexture());
        this.blaze3DShaderUniformPipelineReference = new SingletonReference<Blaze3DShaderUniformPipeline>(Blaze3DShaderUniformPipeline.class, () -> new VersionedBlaze3DShaderUniformPipeline());
        this.blaze3DGlStatePipelineReference = new SingletonReference<Blaze3DGlStatePipeline>(Blaze3DGlStatePipeline.class, () -> new VersionedBlaze3DGlStatePipeline());
        this.minecraftAtlasesReference = new SingletonReference<MinecraftAtlases>(MinecraftAtlases.class, () -> new VersionedMinecraftAtlases());
        this.standardBlaze3DRenderTypesReference = new SingletonReference<StandardBlaze3DRenderTypes>(StandardBlaze3DRenderTypes.class, () -> new VersionedStandardBlaze3DRenderTypes());
        this.gfxBridgeReferencegfx_bridge_development = new SingletonReference<GFXBridge>(GFXBridge.class, () -> new DevelopmentGFXBridge(this.blaze3DGlStatePipeline()));
        this.gfxBridgeReferencegfx_bridge_production = new SingletonReference<GFXBridge>(GFXBridge.class, () -> new VersionedGFXBridge(this.blaze3DGlStatePipeline()));
        this.nbtFactoryReference = new DynamicReference<NBTFactory>(NBTFactory.class, () -> new VersionedNBTFactory());
        this.refreshableTextureFactoryReference = new DynamicReference<RefreshableTextureFactory>(RefreshableTextureFactory.class, () -> new VersionedRefreshableTextureFactory());
        this.internalChatModifierReference = new SingletonReference<InternalChatModifier>(InternalChatModifier.class, () -> new VersionedChatModifier(this.componentMapper()));
        this.clientNetworkPacketListenerReference = new SingletonReference<ClientNetworkPacketListener>(ClientNetworkPacketListener.class, () -> new VersionedClientNetworkPacketListener(this.serverController(), this.oldAnimationRegistry()));
        this.userFactoryReference = new SingletonReference<UserFactory>(UserFactory.class, () -> new VersionedUserFactory());
        this.panoramaRendererReference = new SingletonReference<PanoramaRenderer>(PanoramaRenderer.class, () -> new VersionedPanoramaRenderer(this.labyAPI()));
    }
    
    @NotNull
    @Override
    public RenderFirstPersonItemInHandEvent.AnimationTypeMapper renderFirstPersonItemInHandEventAnimationTypeMapper() {
        return (RenderFirstPersonItemInHandEvent.AnimationTypeMapper)this.renderFirstPersonItemInHandEventAnimationTypeMapperReference.get();
    }
    
    @NotNull
    @Override
    public GameMathMapper gameMathMapper() {
        return (GameMathMapper)this.gameMathMapperReference.get();
    }
    
    @NotNull
    @Override
    public MojangTextureService mojangTextureService() {
        return (MojangTextureService)this.mojangTextureServiceReference.get();
    }
    
    @NotNull
    @Override
    public IntegratedServer integratedServer() {
        return (IntegratedServer)this.integratedServerReference.get();
    }
    
    @NotNull
    @Override
    public ComponentService componentService() {
        return (ComponentService)this.componentServiceReference.get();
    }
    
    @Nullable
    @Override
    public NumberFormatMapper getNumberFormatMapper() {
        return (NumberFormatMapper)this.numberFormatMapperReference.get();
    }
    
    @NotNull
    @Override
    public SystemInfo systemInfo() {
        return (SystemInfo)this.systemInfoReference.get();
    }
    
    @NotNull
    @Override
    public ServerController serverController() {
        return (ServerController)this.serverControllerReference.get();
    }
    
    @NotNull
    @Override
    public ServerAddressResolver serverAddressResolver() {
        return (ServerAddressResolver)this.serverAddressResolverReference.get();
    }
    
    @NotNull
    @Override
    public ResourceLocationFactory resourceLocationFactory() {
        return (ResourceLocationFactory)this.resourceLocationFactoryReference.get();
    }
    
    @NotNull
    @Override
    public MinecraftSounds minecraftSounds() {
        return (MinecraftSounds)this.minecraftSoundsReference.get();
    }
    
    @NotNull
    @Override
    public GameImageTexture.Factory gameImageTextureFactory() {
        return (GameImageTexture.Factory)this.gameImageTextureFactoryReference.get();
    }
    
    @NotNull
    @Override
    public TextureRepository textureRepository() {
        return (TextureRepository)this.textureRepositoryReference.get();
    }
    
    @NotNull
    @Override
    public GameImageProvider gameImageProvider() {
        return (GameImageProvider)this.gameImageProviderReference.get();
    }
    
    @NotNull
    @Override
    public ResourcePackScanner resourcePackScanner() {
        return (ResourcePackScanner)this.resourcePackScannerReference.get();
    }
    
    @NotNull
    @Override
    public ResourcePackRepository resourcePackRepository() {
        return (ResourcePackRepository)this.resourcePackRepositoryReference.get();
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
    public ChatExecutor chatExecutor() {
        return (ChatExecutor)this.chatExecutorReference.get();
    }
    
    @NotNull
    @Override
    public BlockColorProvider blockColorProvider() {
        return (BlockColorProvider)this.blockColorProviderReference.get();
    }
    
    @NotNull
    @Override
    public Blocks blocks() {
        return null;
    }
    
    @NotNull
    @Override
    public HitResultController hitResultController() {
        return (HitResultController)this.hitResultControllerReference.get();
    }
    
    @NotNull
    @Override
    public ClientWorld clientWorld() {
        return (ClientWorld)this.clientWorldReference.get();
    }
    
    @NotNull
    @Override
    public BossBarRegistry bossBarRegistry() {
        return (BossBarRegistry)this.bossBarRegistryReference.get();
    }
    
    @NotNull
    @Override
    public LightingLayerMapper lightingLayerMapper() {
        return (LightingLayerMapper)this.lightingLayerMapperReference.get();
    }
    
    @NotNull
    @Override
    public ItemStackFactory itemStackFactory() {
        return (ItemStackFactory)this.itemStackFactoryReference.get();
    }
    
    @NotNull
    @Override
    public EntityPoseMapper entityPoseMapper() {
        return (EntityPoseMapper)this.entityPoseMapperReference.get();
    }
    
    @NotNull
    @Override
    public MinecraftAuthenticator minecraftAuthenticator() {
        return (MinecraftAuthenticator)this.minecraftAuthenticatorReference.get();
    }
    
    @NotNull
    @Override
    public SessionAccessor sessionAccessor() {
        return (SessionAccessor)this.sessionAccessorReference.get();
    }
    
    @NotNull
    @Override
    public ParticleController particleController() {
        return (ParticleController)this.particleControllerReference.get();
    }
    
    @NotNull
    @Override
    public GameCrashReport.Factory gameCrashReportFactory() {
        return (GameCrashReport.Factory)this.gameCrashReportFactoryReference.get();
    }
    
    @NotNull
    @Override
    public RenderPipeline renderPipeline() {
        return (RenderPipeline)this.renderPipelineReference.get();
    }
    
    @NotNull
    @Override
    public RenderConstants renderConstants() {
        return (RenderConstants)this.renderConstantsReference.get();
    }
    
    @NotNull
    @Override
    public OutputRenderShard.OutputRender outputRenderShardOutputRender(final String key) {
        if (key.equals("item_entity_output_render")) {
            return (OutputRenderShard.OutputRender)this.outputRenderShardOutputRenderReferenceitem_entity_output_render.get();
        }
        return null;
    }
    
    @NotNull
    @Override
    public ViewOffsetZLayer viewOffsetZLayer() {
        return (ViewOffsetZLayer)this.viewOffsetZLayerReference.get();
    }
    
    @NotNull
    @Override
    public StackProviderFactory stackProviderFactory() {
        return (StackProviderFactory)this.stackProviderFactoryReference.get();
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
    public TextRenderer textRenderer(final String key) {
        if (key.equals("msdf_text_renderer")) {
            return (TextRenderer)this.textRendererReferencemsdf_text_renderer.get();
        }
        if (key.equals("vanilla_text_renderer")) {
            return (TextRenderer)this.textRendererReferencevanilla_text_renderer.get();
        }
        return null;
    }
    
    @NotNull
    @Override
    public StringFormatter stringFormatter() {
        return (StringFormatter)this.stringFormatterReference.get();
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
        return (ComponentMapper)this.componentMapperReference.get();
    }
    
    @NotNull
    @Override
    public PlayerHeartRenderer playerHeartRenderer() {
        return (PlayerHeartRenderer)this.playerHeartRendererReference.get();
    }
    
    @NotNull
    @Override
    public GlInformation glInformation() {
        return (GlInformation)this.glInformationReference.get();
    }
    
    @NotNull
    @Override
    public GlStateBridge glStateBridge() {
        return (GlStateBridge)this.glStateBridgeReference.get();
    }
    
    @NotNull
    @Override
    public ScreenWrapper.Factory screenWrapperFactory() {
        return (ScreenWrapper.Factory)this.screenWrapperFactoryReference.get();
    }
    
    @NotNull
    @Override
    public KeyMapper keyMapper() {
        return (KeyMapper)this.keyMapperReference.get();
    }
    
    @NotNull
    @Override
    public LightmapTexture lightmapTexture() {
        return (LightmapTexture)this.lightmapTextureReference.get();
    }
    
    @NotNull
    @Override
    public Blaze3DShaderUniformPipeline blaze3DShaderUniformPipeline() {
        return (Blaze3DShaderUniformPipeline)this.blaze3DShaderUniformPipelineReference.get();
    }
    
    @NotNull
    @Override
    public Blaze3DGlStatePipeline blaze3DGlStatePipeline() {
        return (Blaze3DGlStatePipeline)this.blaze3DGlStatePipelineReference.get();
    }
    
    @NotNull
    @Override
    public MinecraftAtlases minecraftAtlases() {
        return (MinecraftAtlases)this.minecraftAtlasesReference.get();
    }
    
    @NotNull
    @Override
    public StandardBlaze3DRenderTypes standardBlaze3DRenderTypes() {
        return (StandardBlaze3DRenderTypes)this.standardBlaze3DRenderTypesReference.get();
    }
    
    @NotNull
    @Override
    public GFXBridge gfxBridge(final String key) {
        if (key.equals("gfx_bridge_development")) {
            return (GFXBridge)this.gfxBridgeReferencegfx_bridge_development.get();
        }
        if (key.equals("gfx_bridge_production")) {
            return (GFXBridge)this.gfxBridgeReferencegfx_bridge_production.get();
        }
        return null;
    }
    
    @NotNull
    @Override
    public NBTFactory nbtFactory() {
        return (NBTFactory)this.nbtFactoryReference.get();
    }
    
    @NotNull
    @Override
    public BufferSourceGui bufferSourceGui() {
        return null;
    }
    
    @NotNull
    @Override
    public RefreshableTextureFactory refreshableTextureFactory() {
        return (RefreshableTextureFactory)this.refreshableTextureFactoryReference.get();
    }
    
    @NotNull
    @Override
    public InternalChatModifier internalChatModifier() {
        return (InternalChatModifier)this.internalChatModifierReference.get();
    }
    
    @NotNull
    @Override
    public ClientNetworkPacketListener clientNetworkPacketListener() {
        return (ClientNetworkPacketListener)this.clientNetworkPacketListenerReference.get();
    }
    
    @NotNull
    @Override
    public UserFactory userFactory() {
        return (UserFactory)this.userFactoryReference.get();
    }
    
    @NotNull
    @Override
    public FontSetGlyphProvider fontSetGlyphProvider() {
        return null;
    }
    
    @NotNull
    @Override
    public PanoramaRenderer panoramaRenderer() {
        return (PanoramaRenderer)this.panoramaRendererReference.get();
    }
    
    @Override
    public boolean isVersion() {
        return true;
    }
}
