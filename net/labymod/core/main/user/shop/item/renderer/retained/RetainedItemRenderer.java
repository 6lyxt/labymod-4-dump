// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.renderer.retained;

import net.labymod.api.client.gfx.shader.uniform.Uniform1I;
import net.labymod.api.client.gfx.shader.uniform.Uniform2I;
import net.labymod.api.client.gfx.target.RenderTarget;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.core.main.profiler.RenderProfiler;
import net.labymod.api.client.gfx.pipeline.program.RenderParameter;
import net.labymod.api.client.gfx.pipeline.program.DynamicRenderProgram;
import net.labymod.api.client.gfx.pipeline.util.MatrixUtil;
import java.util.Comparator;
import net.labymod.api.client.gfx.shader.uniform.UniformMatrix4;
import net.labymod.api.client.gfx.pipeline.renderer.immediate.ImmediateDrawPhase;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import java.util.Objects;
import net.labymod.api.util.Disposable;
import net.labymod.api.client.gfx.pipeline.program.RenderParameters;
import net.labymod.api.Textures;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.gfx.pipeline.renderer.shadow.ShadowRenderPassContext;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.world.RenderWorldEvent;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.gfx.shader.uniform.UniformBone;
import net.labymod.core.main.user.shop.item.ItemRendererContext;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;
import net.labymod.api.client.gfx.pipeline.buffer.BufferState;
import net.labymod.api.client.render.model.ModelUploader;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.gfx.buffer.BufferUsage;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.core.main.user.shop.item.AbstractItem;
import java.util.Iterator;
import net.labymod.core.main.user.GameUserData;
import net.labymod.core.main.user.GameUserItem;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.api.util.ThreadSafe;
import java.util.HashMap;
import java.util.ArrayList;
import net.labymod.core.client.render.model.DefaultModelRenderer;
import net.labymod.api.Laby;
import net.labymod.api.user.shop.RenderMode;
import net.labymod.api.LabyAPI;
import net.labymod.api.user.GameUser;
import net.labymod.core.util.ArrayIndex;
import java.util.UUID;
import java.util.Map;
import java.util.List;
import net.labymod.api.client.render.model.ModelRenderer;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.pipeline.program.parameters.TexturingRenderParameter;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.main.user.shop.item.renderer.ItemRenderer;

public class RetainedItemRenderer extends ItemRenderer
{
    protected static final Logging LOGGER;
    private static final int SPARKLER_ID = 601;
    private static final TexturingRenderParameter DEFAULT_TEXTURING_RENDER_PARAMETER;
    private static final DynamicRenderableItem DYNAMIC_RENDERABLE_ITEM;
    private final GFXRenderPipeline renderPipeline;
    private final ModelRenderer modelRenderer;
    private final List<ModelRenderedBuffer> modelRenderedBuffers;
    private final Map<UUID, ArrayIndex<ModelRenderedBufferHolder>> buffers;
    private GameUser currentUser;
    private ArrayIndex<ModelRenderedBufferHolder> currentUserBuffers;
    private ModelRenderedBufferHolder currentRenderedBuffer;
    
    public RetainedItemRenderer(final LabyAPI labyAPI) {
        super(labyAPI, RenderMode.RETAINED);
        this.renderPipeline = Laby.references().gfxRenderPipeline();
        this.modelRenderer = new DefaultModelRenderer();
        this.modelRenderedBuffers = new ArrayList<ModelRenderedBuffer>();
        this.buffers = new HashMap<UUID, ArrayIndex<ModelRenderedBufferHolder>>();
        labyAPI.eventBus().registerListener(this);
    }
    
    @Override
    public void begin(final GameUser user) {
        this.currentUser = user;
        this.currentUserBuffers = this.buffers.computeIfAbsent(user.getUniqueId(), l -> {
            this.currentUser.addDisposeListener(() -> ThreadSafe.executeOnRenderThread(() -> this.invalidateBuffers(user.getUniqueId())));
            int newCapacity = 128;
            if (this.currentUser instanceof final DefaultGameUser defaultGameUser) {
                final GameUserData userData = defaultGameUser.getUserData();
                userData.getItems().iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final GameUserItem userItem = iterator.next();
                    final AbstractItem item = userItem.item();
                    final int listId = item.getListId();
                    if (listId > newCapacity) {
                        newCapacity = listId;
                    }
                }
            }
            ++newCapacity;
            return new ArrayIndex(newCapacity, ModelRenderedBufferHolder[]::new);
        });
    }
    
    @Override
    public void end(final GameUser user) {
        this.currentUser = null;
    }
    
    public ModelRenderedBuffer getRenderedBuffer(final RenderProgram program) {
        for (final ModelRenderedBuffer buffer : this.modelRenderedBuffers) {
            if (buffer.hasSameRenderProgram(program)) {
                return buffer;
            }
        }
        return null;
    }
    
    @Override
    public void apply(final Player player, final PlayerModel playerModel, final ItemMetadata metadata, final float partialTicks, final AbstractItem item, final ResourceLocation resourceLocation) {
        if (this.currentUserBuffers == null) {
            return;
        }
        final Model model = item.getModel();
        final int listId = item.getListId();
        boolean bufferChanged = false;
        this.currentRenderedBuffer = this.currentUserBuffers.get(listId);
        if (this.currentRenderedBuffer != null && !this.currentRenderedBuffer.isDisposed()) {
            this.currentRenderedBuffer.setTextureLocation(resourceLocation);
            if (this.currentRenderedBuffer.getModel() == model && !metadata.isChanged()) {
                return;
            }
            bufferChanged = true;
        }
        item.applyEffects(player, playerModel, metadata, metadata.isRightSide(), GeometryEffect.Type.BUFFER_CREATION);
        if (model == null) {
            return;
        }
        final ModelUploader modelUploader = this.modelRenderer.modelUploader();
        final net.labymod.api.client.gfx.pipeline.buffer.BufferState bufferState = modelUploader.model(model).shaderBasedAnimation().upload(RenderPrograms.getItemProgram(null));
        if (bufferState == null) {
            return;
        }
        if (bufferChanged) {
            this.currentRenderedBuffer.updateBuffer(model, bufferState);
            return;
        }
        final RenderedBuffer renderedBuffer = bufferState.upload(BufferUsage.STATIC_DRAW);
        (this.currentRenderedBuffer = new ModelRenderedBufferHolder(item, renderedBuffer)).setTextureLocation(resourceLocation);
        if (listId > this.currentUserBuffers.size() - 1) {
            this.currentUserBuffers.grow(listId + 1);
        }
        this.currentUserBuffers.set(listId, this.currentRenderedBuffer);
    }
    
    @Override
    public void render(final AbstractItem item, final ItemRendererContext context) {
        if (this.currentRenderedBuffer == null) {
            return;
        }
        final Model model = this.currentRenderedBuffer.getModel();
        final RenderProgram renderProgram = this.currentRenderedBuffer.getRenderedBuffer().renderProgram();
        ModelRenderedBuffer buffer = this.getRenderedBuffer(renderProgram);
        if (buffer == null) {
            buffer = new ModelRenderedBuffer(renderProgram);
            this.modelRenderedBuffers.add(buffer);
        }
        if (item.getIdentifier() == 601 && item.getPosition() != AbstractItem.Position.LEFT) {
            return;
        }
        final ShaderProgram shaderProgram = renderProgram.shaderProgram();
        final UniformBone boneMatrices = shaderProgram.getUniform("BoneMatrices");
        if (boneMatrices != null) {
            model.applyAnimation(boneMatrices);
        }
        if (context.isDirect()) {
            RetainedItemRenderer.DYNAMIC_RENDERABLE_ITEM.setRenderedBuffer(this.currentRenderedBuffer.renderedBuffer);
            RetainedItemRenderer.DYNAMIC_RENDERABLE_ITEM.setModel(model);
            RetainedItemRenderer.DYNAMIC_RENDERABLE_ITEM.setItemRenderEnvironment(context, modelViewMatrix -> {
                if (context.isShadowRenderPass()) {
                    return this.applyMatrix(context.renderEnvironmentContext().shadowRenderPassContext(), modelViewMatrix, 0);
                }
                else if (PlatformEnvironment.isNoShader() || !context.isScreenContext()) {
                    return modelViewMatrix;
                }
                else {
                    return modelViewMatrix;
                }
            }, projectionMatrix -> {
                if (context.isShadowRenderPass()) {
                    return this.applyMatrix(context.renderEnvironmentContext().shadowRenderPassContext(), projectionMatrix, 1);
                }
                else {
                    return projectionMatrix;
                }
            });
            RetainedItemRenderer.DYNAMIC_RENDERABLE_ITEM.render();
        }
        else {
            buffer.addRenderedBuffer(this.currentRenderedBuffer, item.getPosition(), model.getTextureLocation(), context, this.currentUser);
        }
        if (boneMatrices != null) {
            boneMatrices.resetBoneMatrix();
            model.reset();
        }
    }
    
    @Override
    public void finish() {
        this.currentRenderedBuffer = null;
    }
    
    @Subscribe
    public void onEntityRenderPass(final RenderWorldEvent event) {
        for (final ModelRenderedBuffer buffer : this.modelRenderedBuffers) {
            buffer.render();
        }
    }
    
    private void invalidateBuffers(final UUID uniqueId) {
        final ArrayIndex<ModelRenderedBufferHolder> userBuffers = this.buffers.remove(uniqueId);
        if (userBuffers != null) {
            userBuffers.forEach(bufferPair -> {
                if (bufferPair != null) {
                    bufferPair.dispose();
                }
                return;
            });
        }
        for (final ModelRenderedBuffer renderedBuffer : this.modelRenderedBuffers) {
            final Iterator<BufferState> buffers = renderedBuffer.buffers.iterator();
            while (buffers.hasNext()) {
                final BufferState bufferState = buffers.next();
                if (!bufferState.getUniqueId().equals(uniqueId)) {
                    continue;
                }
                buffers.remove();
            }
        }
    }
    
    @Deprecated
    private FloatMatrix4 applyMatrix(final ShadowRenderPassContext context, final FloatMatrix4 noneShadowMatrix, final int type) {
        switch (type) {
            case 0: {
                final FloatMatrix4 shadowModelViewMatrix = context.getShadowModelViewMatrix();
                return (shadowModelViewMatrix == null) ? noneShadowMatrix : shadowModelViewMatrix;
            }
            case 1: {
                final FloatMatrix4 shadowProjectionMatrix = context.getShadowProjectionMatrix();
                return (shadowProjectionMatrix == null) ? noneShadowMatrix : shadowProjectionMatrix;
            }
            default: {
                return noneShadowMatrix;
            }
        }
    }
    
    static {
        LOGGER = Logging.create(RetainedItemRenderer.class);
        DEFAULT_TEXTURING_RENDER_PARAMETER = RenderParameters.createTextureParameter(Textures.EMPTY);
        DYNAMIC_RENDERABLE_ITEM = new DynamicRenderableItem();
    }
    
    @Deprecated
    public static class ModelRenderedBufferHolder implements Disposable
    {
        private final AbstractItem item;
        private final RenderedBuffer renderedBuffer;
        private Model model;
        private boolean disposed;
        
        public ModelRenderedBufferHolder(final AbstractItem item, final RenderedBuffer renderedBuffer) {
            this.item = item;
            this.model = item.getModel();
            this.renderedBuffer = renderedBuffer;
            this.item.addDisposeListener(this::dispose);
        }
        
        public Model getModel() {
            return this.model;
        }
        
        public RenderedBuffer getRenderedBuffer() {
            return this.renderedBuffer;
        }
        
        public void setTextureLocation(final ResourceLocation location) {
            this.model.setTextureLocation(location);
        }
        
        public void setDisposed(final boolean disposed) {
            this.disposed = disposed;
        }
        
        @Override
        public void dispose() {
            this.renderedBuffer.dispose();
            this.disposed = true;
        }
        
        @Override
        public boolean isDisposed() {
            return this.disposed;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final ModelRenderedBufferHolder that = (ModelRenderedBufferHolder)o;
            return Objects.equals(this.item, that.item);
        }
        
        @Override
        public int hashCode() {
            return (this.item != null) ? this.item.hashCode() : 0;
        }
        
        public void updateBuffer(final Model model, final net.labymod.api.client.gfx.pipeline.buffer.BufferState bufferState) {
            this.model = model;
            this.renderedBuffer.upload(bufferState);
            bufferState.release();
        }
    }
    
    @Deprecated
    public static class ModelRenderedBuffer
    {
        private final GFXRenderPipeline renderPipeline;
        private final RenderProgram renderProgram;
        private final List<BufferState> buffers;
        private boolean resort;
        
        public ModelRenderedBuffer(final RenderProgram renderProgram) {
            this.renderPipeline = Laby.references().gfxRenderPipeline();
            this.renderProgram = renderProgram;
            this.buffers = new ArrayList<BufferState>();
        }
        
        public void addRenderedBuffer(final ModelRenderedBufferHolder buffer, final AbstractItem.Position position, final ResourceLocation resourceLocation, final ItemRendererContext context, final GameUser user) {
            final Iterator<BufferState> iterator = this.buffers.iterator();
            while (iterator.hasNext()) {
                final BufferState state = iterator.next();
                if (state.isDeleted()) {
                    iterator.remove();
                }
                else {
                    final ModelRenderedBufferHolder holder = state.getBuffer();
                    if (holder.isDisposed()) {
                        iterator.remove();
                    }
                    else if (holder.getRenderedBuffer().isDisposed()) {
                        iterator.remove();
                    }
                    else {
                        if (Objects.equals(buffer, holder) && position == state.getPosition()) {
                            boolean shouldResort = state.setLocation(resourceLocation);
                            if (resourceLocation instanceof AnimatedResourceLocation.AnimatedFrameResourceLocation) {
                                shouldResort = false;
                            }
                            this.resort |= shouldResort;
                            state.setShouldRender(true);
                            state.updateBoneMatrices();
                            state.updateContext(context);
                            return;
                        }
                        continue;
                    }
                }
            }
            this.buffers.add(new BufferState(buffer, resourceLocation, position, context, user.getUniqueId()));
            this.resort = true;
        }
        
        public void render() {
            if (this.buffers.isEmpty()) {
                return;
            }
            final GFXBridge gfx = Laby.gfx();
            RenderTarget renderTarget = null;
            if (ImmediateDrawPhase.RENDER_TARGET_RENDER_PASS) {
                renderTarget = Laby.labyAPI().minecraft().mainTarget();
                renderTarget.bind(true);
            }
            final ShaderProgram shaderProgram = this.renderProgram.shaderProgram();
            final UniformMatrix4 projectionMatrixUniform = shaderProgram.getUniform("ProjectionMatrix");
            FloatMatrix4 projectionMatrix = null;
            if (this.resort) {
                this.buffers.sort(Comparator.comparing(o -> o.getLocation().toString()));
                this.resort = false;
            }
            boolean hasRenderProgram = false;
            ResourceLocation lastLocation = null;
            for (final BufferState buffer : this.buffers) {
                if (!buffer.isShouldRender()) {
                    continue;
                }
                if (!hasRenderProgram) {
                    gfx.bindRenderProgram(this.renderProgram);
                    hasRenderProgram = true;
                }
                final FloatMatrix4 bufferProjectionMatrix = buffer.getProjectionMatrix();
                if (!Objects.equals(projectionMatrix, bufferProjectionMatrix)) {
                    projectionMatrix = bufferProjectionMatrix;
                    projectionMatrixUniform.setAndUpload(projectionMatrix);
                }
                final UniformMatrix4 modelViewMatrixUniform = shaderProgram.getUniform("ModelViewMatrix");
                final FloatMatrix4 modelViewMatrix = buffer.getModelViewMatrix();
                modelViewMatrixUniform.setAndUpload(MatrixUtil.calculateModelViewMatrix(modelViewMatrix));
                if (this.renderProgram instanceof DynamicRenderProgram) {
                    final ResourceLocation location = buffer.getLocation();
                    if (!Objects.equals(lastLocation, location)) {
                        lastLocation = location;
                        RetainedItemRenderer.DEFAULT_TEXTURING_RENDER_PARAMETER.setLocation(location);
                        ((DynamicRenderProgram)this.renderProgram).applyDynamicParameter(RetainedItemRenderer.DEFAULT_TEXTURING_RENDER_PARAMETER);
                        RenderProfiler.increaseCosmeticTextureBindingCalls();
                    }
                }
                gfx.glPushDebugGroup(2, buffer.getLocation().toString());
                buffer.render();
                gfx.glPopDebugGroup();
            }
            if (hasRenderProgram) {
                gfx.unbindRenderProgram();
            }
            if (renderTarget != null) {
                renderTarget.unbind();
            }
        }
        
        public boolean hasSameRenderProgram(final RenderProgram renderProgram) {
            return this.renderProgram == renderProgram || this.renderProgram.equals(renderProgram);
        }
    }
    
    @Deprecated
    public static class BufferState
    {
        private final UUID uniqueId;
        private final ModelRenderedBufferHolder buffer;
        private final FloatMatrix4 modelViewMatrix;
        private final FloatMatrix4 projectionMatrix;
        private final AbstractItem.Position position;
        private UniformBone boneMatricesUniform;
        private ResourceLocation location;
        private boolean cameraRelativePosition;
        private float[] boneMatrices;
        private boolean shouldRender;
        private int packedLightCoords;
        private boolean deleted;
        
        public BufferState(final ModelRenderedBufferHolder buffer, final ResourceLocation location, final AbstractItem.Position position, final ItemRendererContext context, final UUID uniqueId) {
            this.buffer = buffer;
            this.location = location;
            this.position = position;
            this.uniqueId = uniqueId;
            this.modelViewMatrix = new FloatMatrix4().identity();
            this.projectionMatrix = new FloatMatrix4().identity();
            this.updateContext(context);
            this.updateBoneMatrices();
            this.shouldRender = true;
        }
        
        public boolean isShouldRender() {
            return this.shouldRender;
        }
        
        public void setShouldRender(final boolean render) {
            this.shouldRender = render;
        }
        
        public UUID getUniqueId() {
            return this.uniqueId;
        }
        
        public void render() {
            final RenderedBuffer renderedBuffer = this.buffer.getRenderedBuffer();
            if (renderedBuffer.isDisposed() && !this.deleted) {
                RetainedItemRenderer.LOGGER.warn("Buffer with resource location \"{}\" could not be drawn because it was deleted.", this.location);
                this.deleted = true;
                return;
            }
            final RenderProgram renderProgram = renderedBuffer.renderProgram();
            final ShaderProgram shaderProgram = renderProgram.shaderProgram();
            final Uniform2I lightCoords = shaderProgram.getUniform("LightCoords");
            if (lightCoords != null) {
                lightCoords.set(this.packedLightCoords & 0xFFFF, this.packedLightCoords >> 16 & 0xFFFF);
                lightCoords.upload();
            }
            final UniformBone boneMatrices = this.getBoneMatricesUniform();
            if (boneMatrices != null) {
                boneMatrices.store(this.boneMatrices);
                boneMatrices.upload();
            }
            final GFXRenderPipeline renderPipeline = Laby.labyAPI().gfxRenderPipeline();
            final Uniform1I environmentContext = shaderProgram.getUniform("EnvironmentContext");
            if (environmentContext != null) {
                environmentContext.set(renderPipeline.renderEnvironmentContext().isScreenContext());
                environmentContext.upload();
            }
            renderPipeline.gfx().blaze3DGlStatePipeline().shaderUniformPipeline().light().setupShaderLights(shaderProgram);
            final RenderedBuffer renderedBuffer2 = renderedBuffer;
            final RenderedBuffer obj = renderedBuffer;
            Objects.requireNonNull(obj);
            renderedBuffer2.useVertexArray(obj::draw);
            this.shouldRender = false;
        }
        
        public ModelRenderedBufferHolder getBuffer() {
            return this.buffer;
        }
        
        public AbstractItem.Position getPosition() {
            return this.position;
        }
        
        public ResourceLocation getLocation() {
            return this.location;
        }
        
        public void updateBoneMatrices() {
            this.boneMatrices = this.getBoneMatricesUniform().read(this.boneMatrices);
        }
        
        private UniformBone getBoneMatricesUniform() {
            final ShaderProgram shader = this.buffer.getRenderedBuffer().renderProgram().shaderProgram();
            if (this.boneMatricesUniform == null) {
                this.boneMatricesUniform = shader.getUniform("BoneMatrices");
            }
            return this.boneMatricesUniform;
        }
        
        public boolean setLocation(final ResourceLocation location) {
            final boolean sameResource = this.location == location;
            this.location = location;
            return !sameResource;
        }
        
        public void updateContext(final ItemRendererContext context) {
            this.modelViewMatrix.set(context.modelViewMatrix());
            this.projectionMatrix.set(context.projectionMatrix());
            this.packedLightCoords = context.getPackedLightCoords();
        }
        
        public FloatMatrix4 getModelViewMatrix() {
            return this.modelViewMatrix;
        }
        
        public FloatMatrix4 getProjectionMatrix() {
            return this.projectionMatrix;
        }
        
        public boolean isDeleted() {
            return this.deleted;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final BufferState that = (BufferState)o;
            return Objects.equals(this.buffer, that.buffer);
        }
        
        @Override
        public int hashCode() {
            return (this.buffer != null) ? this.buffer.hashCode() : 0;
        }
    }
}
