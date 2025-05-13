// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.generated;

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
import net.labymod.api.util.math.GameMathMapper;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.core.client.gfx.DevelopmentGFXBridge;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.DefaultImGuiAccessor;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.type.DefaultImGuiTypeProvider;
import net.labymod.core.client.render.font.text.msdf.MSDFGlyphProvider;
import net.labymod.core.client.render.font.text.msdf.MSDFTextRenderer;
import net.labymod.core.client.render.draw.hover.VanillaHoverBackgroundEffect;
import net.labymod.core.client.render.draw.hover.FancyHoverBackgroundEffect;
import net.labymod.core.client.render.draw.shader.CircleShaderInstance;
import net.labymod.core.client.render.draw.shader.FrameBufferObjectShaderInstance;
import net.labymod.core.client.render.draw.shader.FrameBufferObjectStencilShaderInstance;
import net.labymod.core.client.render.shader.program.RenderPhase3DEnvironmentTextureShaderInstance;
import net.labymod.core.client.resources.transform.transformer.customhitcolor.DamageOverlayRenderTypeArmorCutoutNoCullJsonResourceTransformer;
import net.labymod.core.client.resources.transform.transformer.customhitcolor.DamageOverlayRenderTypeArmorCutoutNoCullFragmentShaderResourceTransformer;
import net.labymod.core.client.resources.transform.transformer.customhitcolor.DamageOverlayRenderTypeArmorCutoutNoCullVertexShaderResourceTransformer;
import net.labymod.api.reference.SingletonReference;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.LWJGL3FileDialogs;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.imgui.ImGuiAccessor;
import net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider;
import net.labymod.api.client.render.font.text.glyph.GlyphProvider;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.client.render.shader.program.ShaderInstance;
import net.labymod.api.client.resources.transform.ResourceTransformer;
import net.labymod.api.util.FileDialogs;
import net.labymod.api.reference.Reference;
import net.labymod.api.reference.ReferenceStorageAccessor;
import net.labymod.core.generated.DefaultReferenceStorage;

public class ReferenceStorage extends DefaultReferenceStorage implements ReferenceStorageAccessor
{
    private final Reference<FileDialogs> fileDialogsReference;
    private final Reference<ResourceTransformer> resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_vertex_shader;
    private final Reference<ResourceTransformer> resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_fragment_shader;
    private final Reference<ResourceTransformer> resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_json;
    private final Reference<ShaderInstance> shaderInstanceReferencerenderphase_3d_environment_texture;
    private final Reference<ShaderInstance> shaderInstanceReferenceframe_buffer_object_stencil_shader_instance;
    private final Reference<ShaderInstance> shaderInstanceReferenceframe_buffer_object_shader_instance;
    private final Reference<ShaderInstance> shaderInstanceReferencecircle_shader_instance;
    private final Reference<HoverBackgroundEffect> hoverBackgroundEffectReferencefancy_hover_effect;
    private final Reference<HoverBackgroundEffect> hoverBackgroundEffectReferencevanilla_hover_effect;
    private final Reference<TextRenderer> textRendererReferencemsdf_text_renderer;
    private final Reference<GlyphProvider> glyphProviderReferencemsdf_glyph_provider;
    private final Reference<ImGuiTypeProvider> imGuiTypeProviderReference;
    private final Reference<ImGuiAccessor> imGuiAccessorReference;
    private final Reference<GFXBridge> gfxBridgeReferencegfx_bridge_development;
    
    public ReferenceStorage() {
        this.fileDialogsReference = new SingletonReference<FileDialogs>(FileDialogs.class, () -> new LWJGL3FileDialogs());
        this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_vertex_shader = new SingletonReference<ResourceTransformer>(ResourceTransformer.class, () -> new DamageOverlayRenderTypeArmorCutoutNoCullVertexShaderResourceTransformer());
        this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_fragment_shader = new SingletonReference<ResourceTransformer>(ResourceTransformer.class, () -> new DamageOverlayRenderTypeArmorCutoutNoCullFragmentShaderResourceTransformer());
        this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_json = new SingletonReference<ResourceTransformer>(ResourceTransformer.class, () -> new DamageOverlayRenderTypeArmorCutoutNoCullJsonResourceTransformer());
        this.shaderInstanceReferencerenderphase_3d_environment_texture = new SingletonReference<ShaderInstance>(ShaderInstance.class, () -> new RenderPhase3DEnvironmentTextureShaderInstance(this.shaderProvider()));
        this.shaderInstanceReferenceframe_buffer_object_stencil_shader_instance = new SingletonReference<ShaderInstance>(ShaderInstance.class, () -> new FrameBufferObjectStencilShaderInstance(this.shaderProvider()));
        this.shaderInstanceReferenceframe_buffer_object_shader_instance = new SingletonReference<ShaderInstance>(ShaderInstance.class, () -> new FrameBufferObjectShaderInstance(this.shaderProvider()));
        this.shaderInstanceReferencecircle_shader_instance = new SingletonReference<ShaderInstance>(ShaderInstance.class, () -> new CircleShaderInstance(this.shaderProvider()));
        this.hoverBackgroundEffectReferencefancy_hover_effect = new SingletonReference<HoverBackgroundEffect>(HoverBackgroundEffect.class, () -> new FancyHoverBackgroundEffect(this.labyAPI()));
        this.hoverBackgroundEffectReferencevanilla_hover_effect = new SingletonReference<HoverBackgroundEffect>(HoverBackgroundEffect.class, () -> new VanillaHoverBackgroundEffect(this.labyAPI()));
        this.textRendererReferencemsdf_text_renderer = new SingletonReference<TextRenderer>(TextRenderer.class, () -> new MSDFTextRenderer(this.glyphProvider("msdf_glyph_provider"), this.msdfResourceProvider(), this.stringFormatter()));
        this.glyphProviderReferencemsdf_glyph_provider = new SingletonReference<GlyphProvider>(GlyphProvider.class, () -> new MSDFGlyphProvider(this.msdfResourceProvider()));
        this.imGuiTypeProviderReference = new SingletonReference<ImGuiTypeProvider>(ImGuiTypeProvider.class, () -> new DefaultImGuiTypeProvider());
        this.imGuiAccessorReference = new SingletonReference<ImGuiAccessor>(ImGuiAccessor.class, () -> new DefaultImGuiAccessor(this.textureRepository()));
        this.gfxBridgeReferencegfx_bridge_development = new SingletonReference<GFXBridge>(GFXBridge.class, () -> new DevelopmentGFXBridge(this.blaze3DGlStatePipeline()));
    }
    
    @NotNull
    @Override
    public RenderFirstPersonItemInHandEvent.AnimationTypeMapper renderFirstPersonItemInHandEventAnimationTypeMapper() {
        return null;
    }
    
    @NotNull
    @Override
    public GameMathMapper gameMathMapper() {
        return null;
    }
    
    @NotNull
    @Override
    public FileDialogs fileDialogs() {
        return (FileDialogs)this.fileDialogsReference.get();
    }
    
    @NotNull
    @Override
    public MojangTextureService mojangTextureService() {
        return null;
    }
    
    @NotNull
    @Override
    public IntegratedServer integratedServer() {
        return null;
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
    public ServerController serverController() {
        return null;
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
    public ParticleController particleController() {
        return null;
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
    public RenderConstants renderConstants() {
        return null;
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
    public StackProviderFactory stackProviderFactory() {
        return null;
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
    public ScreenWrapper.Factory screenWrapperFactory() {
        return null;
    }
    
    @NotNull
    @Override
    public KeyMapper keyMapper() {
        return null;
    }
    
    @NotNull
    @Override
    public ImGuiTypeProvider imGuiTypeProvider() {
        return (ImGuiTypeProvider)this.imGuiTypeProviderReference.get();
    }
    
    @NotNull
    @Override
    public ImGuiAccessor imGuiAccessor() {
        return (ImGuiAccessor)this.imGuiAccessorReference.get();
    }
    
    @NotNull
    @Override
    public LightmapTexture lightmapTexture() {
        return null;
    }
    
    @NotNull
    @Override
    public Blaze3DShaderUniformPipeline blaze3DShaderUniformPipeline() {
        return null;
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
    public NBTFactory nbtFactory() {
        return null;
    }
    
    @NotNull
    @Override
    public BufferSourceGui bufferSourceGui() {
        return null;
    }
    
    @NotNull
    @Override
    public RefreshableTextureFactory refreshableTextureFactory() {
        return null;
    }
    
    @NotNull
    @Override
    public InternalChatModifier internalChatModifier() {
        return null;
    }
    
    @NotNull
    @Override
    public ClientNetworkPacketListener clientNetworkPacketListener() {
        return null;
    }
    
    @NotNull
    @Override
    public UserFactory userFactory() {
        return null;
    }
    
    @NotNull
    @Override
    public FontSetGlyphProvider fontSetGlyphProvider() {
        return null;
    }
    
    @NotNull
    @Override
    public PanoramaRenderer panoramaRenderer() {
        return null;
    }
}
