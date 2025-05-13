// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.rplace;

import net.labymod.api.client.world.block.Block;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonMessage;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.util.math.position.Position;
import net.labymod.core.client.world.rplace.art.ColoredBlock;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.client.render.ItemStackRenderer;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.Textures;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import net.labymod.api.client.gfx.pipeline.renderer.primitive.ResourceRenderer;
import net.labymod.api.util.math.MathHelper;
import java.util.Iterator;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.client.world.rplace.art.PixelArt;
import net.labymod.api.util.KeyValue;
import net.labymod.api.event.client.render.world.RenderWorldEvent;
import net.labymod.api.event.client.render.world.RenderBlockSelectionBoxEvent;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.event.client.render.ScreenRenderEvent;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.client.world.item.ItemStack;
import java.util.ConcurrentModificationException;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.client.network.server.ServerLobbyEvent;
import net.labymod.api.LabyAPI;
import net.labymod.api.Laby;
import net.labymod.core.main.LabyMod;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.gfx.pipeline.renderer.batch.Batchable;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.RenderPipeline;
import javax.inject.Singleton;

@Singleton
public class RPlaceListener
{
    private final RPlaceRegistry registry;
    private final RenderPipeline legacyPipeline;
    private final RectangleRenderer rectangleRenderer;
    private final GFXRenderPipeline renderPipeline;
    private final Minecraft minecraft;
    private final GFXBridge gfx;
    private final RenderEnvironmentContext context;
    private final Batchable batchable;
    private final ComponentRenderer componentRenderer;
    private final Window window;
    private final LabyConnect labyConnect;
    private int lastTargetBlockX;
    private int lastTargetBlockY;
    private int lastTargetBlockZ;
    private boolean dirty;
    private long timeLastDirty;
    private long timeLastTargetBlock;
    private long timeLastBlockChanged;
    private long timeLastWrongBlock;
    private boolean isSearchingForBlockInInventory;
    
    public RPlaceListener() {
        this.registry = LabyMod.references().rPlaceRegistry();
        final LabyAPI labyAPI = Laby.labyAPI();
        this.legacyPipeline = labyAPI.renderPipeline();
        this.rectangleRenderer = this.legacyPipeline.rectangleRenderer();
        this.renderPipeline = labyAPI.gfxRenderPipeline();
        this.minecraft = labyAPI.minecraft();
        this.gfx = this.renderPipeline.gfx();
        this.context = this.renderPipeline.renderEnvironmentContext();
        this.batchable = this.renderPipeline.renderBuffers().singleBatchable();
        this.componentRenderer = this.legacyPipeline.componentRenderer();
        this.window = this.minecraft.minecraftWindow();
        this.labyConnect = labyAPI.labyConnect();
    }
    
    @Subscribe
    public void onServerLobby(final ServerLobbyEvent event) {
        this.registry.setCurrentLobby(event.serverData(), event.getLobbyName());
    }
    
    @Subscribe
    public void onServerDisconnect(final ServerDisconnectEvent event) {
        this.registry.setCurrentLobby(null, null);
        this.registry.clear(true);
    }
    
    @Subscribe
    public void onTick(final GameTickEvent event) {
        if (!this.registry.isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        try {
            final long timePassed = TimeUtil.getMillis() - this.timeLastBlockChanged;
            if (!this.dirty || timePassed < 50L) {
                return;
            }
            this.dirty = false;
            final ItemStack targetItemStack = RPlaceUtils.getRequiredItemStack(this.lastTargetBlockX, this.lastTargetBlockY, this.lastTargetBlockZ);
            if (targetItemStack != null) {
                RPlaceUtils.selectSlot(targetItemStack);
                this.timeLastBlockChanged = TimeUtil.getMillis();
            }
        }
        catch (final Throwable t) {
            t.printStackTrace();
            if (!(t instanceof ConcurrentModificationException)) {
                this.registry.disableOnError();
            }
        }
    }
    
    @Subscribe
    public void onScreenDisplay(final ScreenDisplayEvent event) {
        if (!this.registry.isEnabled()) {
            return;
        }
        try {
            final long timePassed = TimeUtil.getMillis() - this.timeLastTargetBlock;
            if (timePassed > 1000L) {
                this.isSearchingForBlockInInventory = false;
                return;
            }
            final ScreenInstance screen = event.getScreen();
            if (!NamedScreen.CREATIVE_INVENTORY.isScreen(screen)) {
                this.isSearchingForBlockInInventory = false;
                if (screen == null) {
                    this.dirty = true;
                }
                return;
            }
            final ItemStack targetItemStack = RPlaceUtils.getRequiredItemStack(this.lastTargetBlockX, this.lastTargetBlockY, this.lastTargetBlockZ);
            if (targetItemStack != null) {
                this.isSearchingForBlockInInventory = true;
            }
        }
        catch (final Throwable t) {
            t.printStackTrace();
            if (!(t instanceof ConcurrentModificationException)) {
                this.registry.disableOnError();
            }
        }
    }
    
    @Subscribe
    public void onScreenRender(final ScreenRenderEvent event) {
        if (!this.registry.isEnabled() || event.phase() != Phase.POST) {
            return;
        }
        try {
            if (this.isSearchingForBlockInInventory) {
                final ScreenWrapper screen = this.window.currentScreen();
                if (!NamedScreen.CREATIVE_INVENTORY.isScreen(screen)) {
                    this.isSearchingForBlockInInventory = false;
                    return;
                }
                final Bounds bounds = this.window.bounds();
                this.componentRenderer.builder().pos(bounds.getCenterX(), bounds.getBottom() - 15.0f).text(Component.translatable("labymod.command.command.rplaceoverlay.pickHint", NamedTextColor.GREEN)).centered(true).render(event.stack());
            }
        }
        catch (final Throwable t) {
            t.printStackTrace();
            if (!(t instanceof ConcurrentModificationException)) {
                this.registry.disableOnError();
            }
        }
    }
    
    @Subscribe
    public void onRenderBlockSelectionBox(final RenderBlockSelectionBoxEvent event) {
        if (!this.registry.isEnabled()) {
            return;
        }
        this.timeLastTargetBlock = TimeUtil.getMillis();
        if (this.lastTargetBlockX == event.getX() && this.lastTargetBlockY == event.getY() && this.lastTargetBlockZ == event.getZ()) {
            return;
        }
        this.lastTargetBlockX = event.getX();
        this.lastTargetBlockY = event.getY();
        this.lastTargetBlockZ = event.getZ();
        this.dirty = true;
        this.timeLastDirty = TimeUtil.getMillis();
    }
    
    @Subscribe
    public void renderWorld(final RenderWorldEvent event) {
        if (!this.registry.isEnabled()) {
            return;
        }
        try {
            final Stack stack = event.stack();
            final MinecraftCamera camera = event.camera();
            final float partialTicks = event.getPartialTicks();
            for (final KeyValue<PixelArt> entry : this.registry.getElements()) {
                final PixelArt art = entry.getValue();
                if (art.getResourceLocation() == null) {
                    continue;
                }
                this.renderPixelArt(stack, camera, partialTicks, art);
            }
        }
        catch (final Throwable t) {
            t.printStackTrace();
            if (!(t instanceof ConcurrentModificationException)) {
                this.registry.disableOnError();
            }
        }
    }
    
    private void renderPixelArt(final Stack stack, final MinecraftCamera camera, final float partialTicks, final PixelArt art) {
        final ClientPlayer clientPlayer = this.minecraft.getClientPlayer();
        if (clientPlayer == null) {
            return;
        }
        final ClientWorld world = this.minecraft.clientWorld();
        final ItemStackRenderer itemStackRenderer = this.minecraft.itemStackRenderer();
        final int originX = art.getToX();
        final int originY = art.getY() + 1;
        final int originZ = art.getToZ();
        final float yaw = -MathHelper.lerp(clientPlayer.getRotationYaw(), clientPlayer.getPreviousRotationYaw(), partialTicks);
        stack.push();
        this.gfx.storeBlaze3DStates();
        final int prevLightning = this.context.getPackedLight();
        this.context.setPackedLight(15728880);
        this.gfx.enableBlend();
        this.gfx.defaultBlend();
        this.gfx.disableLighting();
        this.gfx.enableDepth();
        this.gfx.depthFunc(515);
        final DoubleVector3 cameraPosition = camera.position();
        stack.translate(-cameraPosition.getX(), -cameraPosition.getY(), -cameraPosition.getZ());
        final boolean far = cameraPosition.getY() > this.registry.getMapY() + 24;
        final float zFightOffset = far ? 0.1f : 0.02f;
        stack.push();
        stack.translate((float)originX, originY + zFightOffset, (float)originZ);
        stack.rotate(90.0f, 1.0f, 0.0f, 0.0f);
        final float prevAlpha = this.legacyPipeline.getAlpha();
        this.legacyPipeline.setAlpha(0.2f);
        final ResourceRenderer resourceRenderer = this.batchable.getRenderer(ResourceRenderer.class);
        this.batchable.begin(RenderPrograms.getDefaultTexture(art.getResourceLocation()));
        resourceRenderer.draw(0.0f, 0.0f, (float)art.getWidth(), (float)art.getHeight(), 0.0f, 0.0f, 256.0f, 256.0f, 256.0f, 256.0f);
        this.renderPipeline.setModelViewMatrix(stack.getProvider().getPosition());
        this.batchable.end();
        this.legacyPipeline.setAlpha(prevAlpha);
        stack.pop();
        final long timeFadeOut = TimeUtil.getMillis() - this.timeLastTargetBlock;
        final boolean isTagVisible = timeFadeOut < 1000L;
        final float rot = TimeUtil.getMillis() % 360000L / 10.0f;
        ColoredBlock targetBlock = null;
        boolean isCorrectItemInHand = false;
        for (int phase = 0; phase < 2; ++phase) {
            BatchRectangleRenderer batch = null;
            if (phase == 0) {
                batch = this.rectangleRenderer.beginBatch(stack);
            }
            final Position position = clientPlayer.position();
            final Position prevPosition = clientPlayer.previousPosition();
            double targetX = MathHelper.lerp(position.getX(), prevPosition.getX(), partialTicks);
            final double targetY = MathHelper.lerp(position.getY(), prevPosition.getY(), partialTicks);
            double targetZ = MathHelper.lerp(position.getZ(), prevPosition.getZ(), partialTicks);
            if (phase == 1) {
                targetX += Math.sin(Math.toRadians(yaw)) * 2.0;
                targetZ += Math.cos(Math.toRadians(yaw)) * 2.0;
            }
            final int radius = (phase == 1) ? 4 : 40;
            for (int x = 0; x < art.getWidth(); ++x) {
                for (int y = 0; y < art.getHeight(); ++y) {
                    final int blockPosX = originX + x;
                    final int blockPosY = originY;
                    final int blockPosZ = originZ + y;
                    final double distanceToCamera = Math.pow(blockPosX - targetX, 2.0) + Math.pow(blockPosY - targetY, 2.0) + Math.pow(blockPosZ - targetZ, 2.0);
                    if (distanceToCamera <= radius * radius) {
                        final float progress = (float)Math.clamp(1.0 - distanceToCamera / (radius * radius), 0.0, 1.0);
                        if (progress > 0.0f) {
                            final float scale = 0.027f * progress;
                            final ColoredBlock block = art.getBlockAt(x, y);
                            if (block != null) {
                                final BlockState blockState = world.getBlockState(blockPosX, blockPosY - 1, blockPosZ);
                                if (blockState == null || !RPlaceUtils.equalsItem(blockState.block(), block.getItemStack())) {
                                    final boolean isTargetBlock = this.lastTargetBlockX == blockPosX && this.lastTargetBlockZ == blockPosZ;
                                    if (isTargetBlock) {
                                        targetBlock = block;
                                        final Inventory inventory = clientPlayer.inventory();
                                        final ItemStack itemInHand = inventory.itemStackAt(inventory.getSelectedIndex());
                                        isCorrectItemInHand = RPlaceUtils.equalsItem(itemInHand, targetBlock.getItemStack());
                                    }
                                    stack.push();
                                    stack.translate((float)blockPosX, (float)blockPosY, (float)blockPosZ);
                                    if (phase == 0) {
                                        final boolean isGreen = isTargetBlock && isCorrectItemInHand && isTagVisible;
                                        final int borderColor = (isGreen ? 65280 : 16711680) | Math.min((int)(progress * 255.0f), 255) << 24;
                                        final float thickness = 0.2f;
                                        stack.push();
                                        stack.translate(0.0f, zFightOffset + 0.01f, 0.0f);
                                        stack.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                                        batch.pos(thickness, thickness, 1.0f - thickness, 1.0f - thickness).color(0).outline(borderColor & 0xFFFFF, borderColor, thickness).build();
                                        stack.pop();
                                    }
                                    if (phase == 1) {
                                        stack.translate(0.5, 0.25, 0.5);
                                        stack.rotate(rot, 0.0f, 1.0f, 0.0f);
                                        stack.scale(scale, -scale, scale);
                                        stack.translate(-8.0f, -8.0f, -150.0f);
                                        itemStackRenderer.renderItemStack(stack, block.getItemStack(), 0, 0);
                                    }
                                    stack.pop();
                                }
                            }
                        }
                    }
                }
            }
            if (phase == 0) {
                batch.upload();
            }
        }
        if (targetBlock != null && isTagVisible) {
            stack.push();
            stack.translate(this.lastTargetBlockX + 0.5, originY + 0.25, this.lastTargetBlockZ + 0.5);
            this.gfx.enableDepth();
            this.gfx.depthFunc(515);
            final Component displayName = targetBlock.getItemStack().getDisplayName();
            final RenderableComponent renderableComponent = RenderableComponent.of(displayName);
            final long timeFadeIn = TimeUtil.getMillis() - this.timeLastDirty;
            double progress2 = Math.clamp(timeFadeIn / 250.0, 0.0, 1.0);
            progress2 = CubicBezier.BOUNCE.curve(progress2);
            progress2 *= Math.clamp(3.0 - timeFadeOut / 200.0, 0.0, 1.0);
            final int opacity = (int)Math.min(255.0, progress2 * 255.0);
            final float tagScale = (float)(0.029999999329447746 * progress2);
            final float padding = 0.5f;
            final float width = renderableComponent.getWidth();
            final float height = renderableComponent.getHeight();
            final long timePassedCheck = TimeUtil.getMillis() - this.timeLastWrongBlock;
            double progressCheck = Math.clamp(timePassedCheck / 250.0, 0.0, 1.0);
            progressCheck = CubicBezier.BOUNCE.curve(progressCheck);
            stack.rotate(180.0f - camera.getYaw(), 0.0f, 1.0f, 0.0f);
            stack.translate(0.0f, 0.5f, 0.0f);
            stack.rotate(-camera.getPitch(), 1.0f, 0.0f, 0.0f);
            stack.scale(tagScale, -tagScale, tagScale);
            this.rectangleRenderer.renderRectangle(stack, (float)(-width / 2.0f - progressCheck), (float)(-height / 2.0f - progressCheck), (float)(width / 2.0f + progressCheck), (float)(height / 2.0f + progressCheck), isCorrectItemInHand ? (0xFF00 | Math.min((int)(progressCheck * 80.0), 80) << 24) : (0xFFFFFF | Math.min(opacity, 80) << 24));
            stack.translate(0.0, 0.0, 0.01);
            this.rectangleRenderer.renderRectangle(stack, -width / 2.0f - padding, -height / 2.0f - padding, width / 2.0f + padding, height / 2.0f + padding, 0x202020 | Math.min(opacity, 128) << 24);
            stack.translate(0.0, 0.0, 0.01);
            this.componentRenderer.builder().text(renderableComponent).pos(-width / 2.0f, -height / 2.0f).color(0xFFFFFF | opacity << 24).drawMode(TextDrawMode.POLYGON_OFFSET).shadow(true).render(stack);
            if (isCorrectItemInHand) {
                final float checkScale = (float)(8.0 * progressCheck);
                Textures.SpriteCommon.GREEN_CHECKED.render(stack, width / 2.0f - checkScale / 2.0f, -checkScale / 2.0f + height / 2.0f, checkScale);
            }
            else {
                this.timeLastWrongBlock = TimeUtil.getMillis();
            }
            this.gfx.depthFunc(515);
            stack.pop();
        }
        this.context.setPackedLight(prevLightning);
        this.gfx.restoreBlaze3DStates();
        stack.pop();
    }
    
    @Subscribe
    public void onClientPlayerInteract(final ClientPlayerInteractEvent event) {
        if (!this.registry.isEnabled()) {
            return;
        }
        try {
            final ClientWorld world = this.minecraft.clientWorld();
            if (world == null) {
                return;
            }
            final ClientPlayer clientPlayer = this.minecraft.getClientPlayer();
            if (clientPlayer == null) {
                return;
            }
            if (event.type() == ClientPlayerInteractEvent.InteractionType.PICK_BLOCK) {
                final PixelArt art = RPlaceUtils.getPixelArtAt(this.lastTargetBlockX, this.lastTargetBlockY, this.lastTargetBlockZ);
                if (art != null) {
                    final ItemStack expectedItem = RPlaceUtils.getRequiredItemStack(art, this.lastTargetBlockX, this.lastTargetBlockZ);
                    event.setCancelled(true);
                    if (expectedItem != null) {
                        RPlaceUtils.pickItem(expectedItem);
                    }
                }
            }
            if (event.type() == ClientPlayerInteractEvent.InteractionType.INTERACT || event.type() == ClientPlayerInteractEvent.InteractionType.ATTACK) {
                final LabyConnectSession session = this.labyConnect.getSession();
                if (session == null) {
                    return;
                }
                final long timePassed = TimeUtil.getMillis() - this.timeLastTargetBlock;
                if (this.timeLastDirty == 0L || timePassed > 200L) {
                    return;
                }
                final ItemStack itemInHand = clientPlayer.inventory().itemStackAt(clientPlayer.inventory().getSelectedIndex());
                if (itemInHand.isAir()) {
                    return;
                }
                final Block currentBlock = world.getBlockState(this.lastTargetBlockX, this.lastTargetBlockY, this.lastTargetBlockZ).block();
                final JsonObject payload = new JsonObject();
                payload.addProperty("action", "place");
                payload.addProperty("x", (Number)this.lastTargetBlockX);
                payload.addProperty("z", (Number)this.lastTargetBlockZ);
                payload.addProperty("from", currentBlock.id().toString());
                payload.addProperty("to", itemInHand.getIdentifier().toString());
                final PixelArt art2 = RPlaceUtils.getPixelArtAt(this.lastTargetBlockX, this.lastTargetBlockY, this.lastTargetBlockZ);
                if (art2 != null) {
                    final ItemStack expectedItem2 = RPlaceUtils.getRequiredItemStack(art2, this.lastTargetBlockX, this.lastTargetBlockZ);
                    final String expectedIdentifier = (expectedItem2 == null) ? null : expectedItem2.getIdentifier().toString();
                    final JsonObject overlay = new JsonObject();
                    overlay.addProperty("expected", expectedIdentifier);
                    overlay.addProperty("official", Boolean.valueOf(art2.isLabyConnect()));
                    payload.add("overlay", (JsonElement)overlay);
                }
                if (!this.registry.isOnTestServer()) {
                    ((DefaultLabyConnect)this.labyConnect).sendPacket(new PacketAddonMessage("rplace", (JsonElement)payload));
                }
            }
        }
        catch (final Throwable t) {
            t.printStackTrace();
        }
    }
}
